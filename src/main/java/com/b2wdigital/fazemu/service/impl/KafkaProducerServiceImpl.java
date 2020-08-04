package com.b2wdigital.fazemu.service.impl;

import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.b2wdigital.fazemu.business.repository.InterfaceEventoRepository;
import com.b2wdigital.fazemu.business.repository.ParametrosInfraRepository;
import com.b2wdigital.fazemu.business.repository.SistemaWsRepository;
import com.b2wdigital.fazemu.business.repository.WsMetodoRepository;
import com.b2wdigital.fazemu.business.service.KafkaProducerService;
import com.b2wdigital.fazemu.domain.CallbackMessage;
import com.b2wdigital.fazemu.domain.DocumentoFiscal;
import com.b2wdigital.fazemu.domain.InterfaceEvento;
import com.b2wdigital.fazemu.domain.KafkaConfigurationVO;
import com.b2wdigital.fazemu.domain.SistemaWs;
import com.b2wdigital.fazemu.domain.WsMetodo;
import com.b2wdigital.fazemu.enumeration.InterfaceEventoEnum;
import com.b2wdigital.fazemu.enumeration.TipoServicoEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Kafka Producer Service Impl.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerServiceImpl.class);

    @Value("${main.topic.name}")
    private String[] topicName;

    @Autowired
    private KafkaConfigurationVO configuration;

    @Autowired
    private SistemaWsRepository sistemaWsRepository;

    @Autowired
    private WsMetodoRepository wsMetodoRepository;

    @Autowired
    private InterfaceEventoRepository interfaceEventoRepository;

    @Autowired
    private ParametrosInfraRepository parametrosInfraRepository;

    @Override
    public void invokeCallback(DocumentoFiscal docu, String xmlProcessado, TipoServicoEnum tipoServicoEnum) throws Exception {
        LOGGER.info("invokeCallback docu {} xmlProcessado {}", docu.getId());
        List<SistemaWs> listaSistemaWs = sistemaWsRepository.listByTipoDocumentoFiscalAndIdSistemaAtivoAndTipoServico(docu.getTipoDocumentoFiscal(), docu.getIdSistema(), tipoServicoEnum.getTipoRetorno());
        if (listaSistemaWs.size() > 0) {

            listaSistemaWs.forEach((sistema) -> {
                WsMetodo metodo = wsMetodoRepository.findById(sistema.getIdMetodo());
                if (metodo != null) {
                    try {
                        Long idInterfaceEvento = interfaceEventoRepository.insert(buildInterfaceEvento(docu, sistema, metodo, tipoServicoEnum));
                        LOGGER.info("Buscando interfaceEvento id {}", idInterfaceEvento);

                        String message = buildKafkaMessage(metodo, xmlProcessado, idInterfaceEvento);
                        //LOGGER.info("JSON MESSAGE {} {} ", docu.getId(), message);

                        produceMessageToTopic(message, null);
                        LOGGER.info("Produzindo kafka message para documento fiscal {}", docu.getId());

                    } catch (Exception e) {
                        LOGGER.error("Não foi capaz de produzir mensagem no Kafka para o docu {}", docu.getId());
                    }
                }
            });
        }
    }

    protected InterfaceEvento buildInterfaceEvento(DocumentoFiscal docu, SistemaWs sistema, WsMetodo metodo, TipoServicoEnum tipoServicoEnum) {
        String usuario = parametrosInfraRepository.getAsString(null, ParametrosInfraRepository.PAIN_USUARIO_DEFAULT);

        InterfaceEvento ie = new InterfaceEvento();
        ie.setIdSistema(sistema.getIdSistema());
        ie.setIdMetodo(metodo.getId());
        ie.setTipoServico(tipoServicoEnum.getTipoRetorno());
        ie.setIdDocFiscal(docu.getId());
        ie.setObservacao(null);
        ie.setSituacao(InterfaceEventoEnum.ABERTO.getCodigo());
        ie.setUsuarioRegistro(usuario);
        ie.setUsuario(usuario);

        return ie;
    }

    @Override
    public String buildKafkaMessage(WsMetodo wsMetodo, String xmlProcessado, Long idInterfaceEvento) throws JsonProcessingException {
        CallbackMessage callback = new CallbackMessage();
        callback.setWsMetodo(wsMetodo);
        callback.setXml(xmlProcessado);
        callback.setIdIntefaceEvento(idInterfaceEvento);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(callback);
    }

    @Override
    public void produceMessageToTopic(String message, Integer attempt) throws Exception {
        produceMessageToTopic(topicName, message, attempt);
    }

    @Override
    public void produceMessageToTopic(String[] topics, String message, Integer attempt) throws Exception {

        Properties producerProperties = configuration.getProducerProperties();
        producerProperties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, UUID.randomUUID().toString());

        for (String topic : topicName) {
            Producer<String, String> producer = new KafkaProducer<>(producerProperties);
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
            record.headers().add("ATTEMPT", attempt != null ? attempt.toString().getBytes() : null);

            producer.initTransactions();

            try {
                producer.beginTransaction();
                Future<RecordMetadata> result = producer.send(record);
                RecordMetadata recordMetadata = result.get();
                LOGGER.info("Mensagem enviada com sucesso: Topic = " + recordMetadata.topic() + " Partition = " + recordMetadata.partition() + " Offset = " + recordMetadata.offset());
                producer.commitTransaction();
            } catch (ProducerFencedException e) {
                producer.close();
                LOGGER.error("Erro ao postar mensagem no topico", e);
                throw e;
            } catch (KafkaException e) {
                producer.abortTransaction();
                LOGGER.error("Erro ao postar mensagem no topico", e);
                throw e;
            } catch (Exception e) {
                LOGGER.error("Erro ao postar mensagem no topico", e);
                throw e;
            } finally {
                producer.close();
            }

        }
    }

}
