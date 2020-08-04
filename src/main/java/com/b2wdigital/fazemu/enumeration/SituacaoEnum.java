package com.b2wdigital.fazemu.enumeration;

import org.apache.commons.lang3.StringUtils;

/**
 * Situacao Enum.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
public enum SituacaoEnum {

    ABERTO("A", "Aberto"),
    ERRO("E", "Erro"),
    CANCELADO("C", "Cancelado"),
    LIQUIDADO("L", "Liquidado"),
    ENVIADO("V", "Enviado");

    private final String codigo;
    private final String descricao;

    SituacaoEnum(String codigo, String descricao) {
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
    public static final SituacaoEnum getByCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }

        for (SituacaoEnum e : values()) {
            if (StringUtils.equalsIgnoreCase(StringUtils.trim(codigo), e.getCodigo())) {
                return e;
            }
        }
        return null;
    }

}
