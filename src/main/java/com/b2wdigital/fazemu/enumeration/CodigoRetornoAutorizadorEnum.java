package com.b2wdigital.fazemu.enumeration;

import org.apache.commons.lang3.StringUtils;

/**
 * Codigo Retorno Autorizador Enum.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
public enum CodigoRetornoAutorizadorEnum {

    AUTORIZADO_FINALIZADO("AF", "Autorizado Finalizado"),
    REJEITADO_FINALIZADO("RF", "Rejeitado Finalizado"),
    REJEITADO_TRATAMENTO("RT", "Rejeitado Tratamento"),
    EM_PROCESSO("EP", "Em Processo");

    private final String codigo;
    private final String descricao;

    CodigoRetornoAutorizadorEnum(String codigo, String descricao) {
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
    public static final CodigoRetornoAutorizadorEnum getByCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }

        for (CodigoRetornoAutorizadorEnum e : values()) {
            if (StringUtils.equalsIgnoreCase(StringUtils.trim(codigo), e.getCodigo())) {
                return e;
            }
        }
        return null;
    }

}
