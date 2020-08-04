package com.b2wdigital.fazemu.business.service;

import com.b2wdigital.fazemu.domain.DocumentoFiscal;
import com.b2wdigital.fazemu.domain.WsMetodo;
import com.b2wdigital.fazemu.enumeration.TipoServicoEnum;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface KafkaProducerService {
	
	String buildKafkaMessage(WsMetodo wsMetodo, String xmlProcessado, Long idInterfaceEvento) throws JsonProcessingException;
	
	void invokeCallback(DocumentoFiscal docu, String xmlProcessado, TipoServicoEnum tipoServicoEnum) throws Exception;
	
	void produceMessageToTopic(String message, Integer attempt) throws Exception;
	
	void produceMessageToTopic(String[] topics, String message, Integer attempt) throws Exception;
	
}
