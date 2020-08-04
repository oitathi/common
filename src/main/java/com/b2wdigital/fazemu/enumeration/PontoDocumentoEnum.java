package com.b2wdigital.fazemu.enumeration;

import org.apache.commons.lang3.StringUtils;

/**
 * Ponto Documento Enum.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
public enum PontoDocumentoEnum {

    RECEBIDO_XML_RAW("DRAW", "Documento Raw Recebido"),
    RECEBIDO_XML_SIGNED("DASS", "Documento Assinado Recebido"),
    RECEBIDO_XML_LAYOUT("DLAY", "Documento de Layout Recebido"),
    RECEBIDO_XML_EPEC("DEPEC", "Documento Epec Recebido"),
    RECEBIDO_XML_MANIFESTACAO("DAUTM", "Documento Fiscal Criado via Manifestação"),
    RECIBO("DRECI", "Documento de Recibo"),
    CONSULTA_RECIBO("DCORE", "Documento Em Consulta de Recibo"),
    PROCESSADO_LAYOUT("DPLAY", "Documento de Layout Processado"),
    PROCESSADO("DPROC", "Documento Processado"),
    REENVIO_NORMAL_APOS_EPEC("DENVI", "Documento Enviado para Normal Após Epec"),
    CANCELAMENTO("DCANC", "Documento Cancelamento Recebido"),
    CARTA_CORRECAO("DCCOR", "Documento Carta de Correção Recebida"),
    MANIFESTACAO("DMANF", "Documento Manifestação Recebido"),
    RESUMO_NFE("DRES", "Documento de Resumo NFe"),
    INUTILIZACAO("DINUT", "Documento Inutilização Recebido"),
    ERRO_EMITIR_DOCUMENTO("ERRE", "Erro ao Emitir Documento"),
    ERRO_CONSULTAR_DOCUMENTO("ERRR", "Erro ao Consultar Recibo Documento"),
    ERRO_CANCELAR_DOCUMENTO("ERRC", "Erro ao Emitir Documento");

    private final String codigo;
    private final String descricao;

    PontoDocumentoEnum(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    /**
     * get by Codigo
     *
     * @param codigo
     * @return
     */
    public static final PontoDocumentoEnum getByCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }

        for (PontoDocumentoEnum e : values()) {
            if (StringUtils.equalsIgnoreCase(StringUtils.trim(codigo), e.getCodigo())) {
                return e;
            }
        }
        return null;
    }

}
