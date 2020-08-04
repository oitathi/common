package com.b2wdigital.fazemu.enumeration;

import org.apache.commons.lang3.StringUtils;

/**
 * Situacao Documento Enum.
 *
 * @author Marcelo Oliveira {tmarcelo.doliveira@b2wdigital.com}
 * @version 1.0
 */
public enum SituacaoDocumentoEnum {

    AUTORIZADO("A", "Autorizado"),
    REJEITADO("R", "Rejeitado"),
    DENEGADO("D", "Denegado"),
    INUTILIZADO("I", "Inutilizado"),
    CANCELADO("C", "Cancelado"),
    ENVIADO("V", "Enviado"),
    PENDENTE("P", "Pendente");

    private final String codigo;
    private final String descricao;

    SituacaoDocumentoEnum(String codigo, String descricao) {
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
    public static final SituacaoDocumentoEnum getByCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }

        for (SituacaoDocumentoEnum e : values()) {
            if (StringUtils.equalsIgnoreCase(StringUtils.trim(codigo), e.getCodigo())) {
                return e;
            }
        }
        return null;
    }

}
