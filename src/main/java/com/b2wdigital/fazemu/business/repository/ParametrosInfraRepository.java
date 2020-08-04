package com.b2wdigital.fazemu.business.repository;

import java.util.List;
import java.util.Map;

import com.b2wdigital.fazemu.domain.ParametrosInfra;

/**
 *
 * @author dailton.almeida
 */
public interface ParametrosInfraRepository {
    
    //PAIB
    static final String PAIB_TRUSTSTORE                                 = "TRUSTSTORE";
    static final String PAIN_TRUSTSTORE_PASSWORD                        = "TRUSTSTORE_PASSWORD";
    
    //PAIN
    static final String PAIN_USUARIO_DEFAULT                            = "USUARIO_DEFAULT";

    static final String PAIN_TIPO_AMBIENTE                              = "SEFAZ_TP_AMB";
    static final String PAIN_QTD_MAX_DOC_LOTE                           = "SEFAZ_QT_MAX_DOC_LOTE";
    static final String PAIN_QTD_MAX_DOC_LOTE_EPEC                      = "SEFAZ_EPEC_QT_MAX_DOC_LOTE_EPEC";
    static final String PAIN_QTD_MAX_DOC_LOTE_CANCELAMENTO              = "SEFAZ_CANCELAMENTO_QT_MAX_DOC_LOTE";
    static final String PAIN_TAMANHO_MAXIMO_LOTE                        = "SEFAZ_TAMANHO_MAX_LOTE";
    static final String PAIN_TEMPO_ESPERA_FECHAMENTO_LOTE               = "SEFAZ_TEMPO_ESPERA_FECHAMENTO_LOTE";
    static final String PAIN_TEMPO_ESPERA_CONSULTA_RECIBO               = "SEFAZ_INTERVALO_MIN_CONSULTA_RECIBO_LOTE";
    static final String PAIN_TEMPO_REENVIO_LOTES_FECHADOS               = "SEFAZ_TEMPO_REENVIO_LOTES_FECHADOS";
    static final String PAIN_TP_EMISSAO                                 = "SEFAZ_TP_EMISSAO";
    static final String PAIN_TEMPO_CONSULTA_LOTE                        = "SEFAZ_TEMPO_CONSULTA_LOTE";
    static final String PAIN_DANFE_AVISO_EPEC                           = "SEFAZ_DANFE_AVISO_EPEC";
    static final String PAIN_DANFE_AVISO_SVC                            = "SEFAZ_DANFE_AVISO_SVC";

    static final String PAIN_DOCUMENTOS_PROCESSADOS_EM_PARALELO         = "DOCUMENTOS_PROCESSADOS_EM_PARALELO";
    static final String PAIN_ESTADOS_PROCESSADOS_COM_ERROS_LOTE         = "ESTADOS_PROCESSADOS_COM_ERROS_LOTE";

    static final String PAIN_TEMPO_MIN_CONTINGENCIA_INEV                = "TEMPO_ULT_ALT_MIN_CONTINGENCIA_INEV";
    static final String PAIN_TEMPO_MAX_CONTINGENCIA_INEV                = "TEMPO_REG_MAX_CONTINGENCIA_INEV";
    static final String PAIN_TEMPO_MIN_LOTES_ENVIADOS_PARADOS           = "TEMPO_MIN_RECONSTRUIR_LOTES_ENVIADOS_PARADOS";
    static final String PAIN_TEMPO_MAX_LOTES_ENVIADOS_PARADOS           = "TEMPO_MAX_RECONSTRUIR_LOTES_ENVIADOS_PARADOS";
    static final String PAIN_TEMPO_MIN_DOCUMENTOS_REJEITADOS            = "TEMPO_MIN_CONSULTAR_DOCUMENTOS_REJEITADOS";
    static final String PAIN_TEMPO_MAX_DOCUMENTOS_REJEITADOS            = "TEMPO_MAX_CONSULTAR_DOCUMENTOS_REJEITADOS";
    static final String PAIN_TEMPO_MIN_EMITIR_DOCUMENTOS_PARADOS        = "TEMPO_MIN_EMITIR_DOCUMENTOS_PARADOS";
    static final String PAIN_TEMPO_MAX_EMITIR_DOCUMENTOS_PARADOS        = "TEMPO_MAX_EMITIR_DOCUMENTOS_PARADOS";
    static final String PAIN_TEMPO_MIN_ENVIAR_EPEC_AUTORIZADOR_NORMAL   = "TEMPO_MIN_ENVIAR_EPEC_AUTORIZADOR_NORMAL";
    static final String PAIN_TEMPO_MIN_DOWNLOAD_XML_MANIFESTADO         = "TEMPO_MIN_DOWNLOAD_XML_MANIFESTADO";
    static final String PAIN_INTERVALO_CONSUMO_INDEVIDO                 = "TEMPO_INTERVALO_CONSUMO_INDEVIDO";
    static final String PAIN_EXPIRACAO_CERTIFICADO_DIGITAL              = "TEMPO_EXPIRACAO_CERTIFICADO_DIGITAL";

    static final String PAIN_SCHEDULED_CANCELAR_LOTES_PERDIDOS          = "SCHEDULED_CANCELAR_LOTES_PERDIDOS";
    static final String PAIN_SCHEDULED_ENVIAR_EPEC_AUTORIZADOR_NORMAL   = "SCHEDULED_ENVIAR_EPEC_AUTORIZADOR_NORMAL";
    static final String PAIN_SCHEDULED_ENTRAR_EPEC_AUTOMATICO           = "SCHEDULED_ENTRAR_EPEC_AUTOMATICO";
    static final String PAIN_SCHEDULED_ENVIAR_CALLBACK_CONTINGENCIA     = "SCHEDULED_ENVIAR_CALLBACK_CONTINGENCIA";
    static final String PAIN_SCHEDULED_EMITIR_LOTE_DOCUMENTOS_PARADOS   = "SCHEDULED_EMITIR_LOTE_DOCUMENTOS_PARADOS";
    static final String PAIN_SCHEDULED_EMITIR_MANIFESTACOES_PARADAS     = "SCHEDULED_EMITIR_MANIFESTACOES_PARADAS";
    static final String PAIN_SCHEDULED_DISTRIBUICAO_DOCUMENTOS 		= "SCHEDULED_DISTRIBUICAO_DOCUMENTOS";
    static final String PAIN_SCHEDULED_DOWNLOAD_XML_MANIFESTADO         = "SCHEDULED_DOWNLOAD_XML_MANIFESTADO";
    static final String PAIN_SCHEDULED_GERAR_CALLBACK_FALTANTE          = "SCHEDULED_GERAR_CALLBACK_FALTANTE";
    static final String PAIN_SCHEDULED_GERAR_XML_FALTANTE               = "SCHEDULED_GERAR_XML_FALTANTE";
    static final String PAIN_SCHEDULED_GERAR_EVENTO_FALTANTE            = "SCHEDULED_GERAR_EVENTO_FALTANTE";
    static final String PAIN_SCHEDULED_LIMPAR_REGISTROS                 = "SCHEDULED_LIMPAR_REGISTROS";
    
    //NFSe
    static final String PAIN_TEMPO_MIN_ENVIAR_DOCUMENTOS_REJEITADOS     = "TEMPO_MIN_ENVIAR_DOCUMENTOS_REJEITADOS";
    static final String PAIN_SCHEDULED_ENVIAR_DOCUMENTOS_REJEITADOS     = "SCHEDULED_ENVIAR_DOCUMENTOS_REJEITADOS";
    static final String PAIN_TEMPO_MIN_CONSULTAR_RECIBO_FALTANTE        = "TEMPO_MIN_CONSULTAR_RECIBO_FALTANTE";
    static final String PAIN_SCHEDULED_CONSULTAR_RECIBO_FALTANTE        = "SCHEDULED_CONSULTAR_RECIBO_FALTANTE";
    static final String PAIN_TEMPO_MIN_ENVIAR_DOCUMENTOS_PARADOS        = "TEMPO_MIN_ENVIAR_DOCUMENTOS_PARADOS";
    static final String PAIN_SCHEDULED_ENVIAR_DOCUMENTOS_PARADOS        = "SCHEDULED_ENVIAR_DOCUMENTOS_PARADOS";
    
    //Storage
    static final String PAIN_SCHEDULED_STORAGE_DOCUMENTOS 		= "SCHEDULED_STORAGE_DOCUMENTOS";
    static final String PAIN_SCHEDULED_STORAGE_DOCUMENTOS_ELDOC		= "SCHEDULED_STORAGE_DOCUMENTOS_ELDOC";
    

    List<ParametrosInfra> listAll();

    ParametrosInfra findByTipoDocumentoFiscalAndIdParametro(String tipoDocumentoFiscal, String idParametro);

    int update(ParametrosInfra parametro);

    String getAsString(String tipoDocumentoFiscal, String key);

    String getAsString(String tipoDocumentoFiscal, String key, String defaultValue);

    Integer getAsInteger(String tipoDocumentoFiscal, String key);

    Integer getAsInteger(String tipoDocumentoFiscal, String key, Integer defaultValue);

    byte[] getAsByteArray(String tipoDocumentoFiscal, String key);

    byte[] getAsByteArray(String tipoDocumentoFiscal, String key, byte[] defaultValue);

    Map<String, String> getAllAsMap();

}
