package com.b2wdigital.fazemu.enumeration;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

/**
 * Situacao Impressora Enum.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
public enum SituacaoImpressoraEnum {

    ATIVO("A", "Ativo"),
    CANCELADO("C", "Cancelado");

    private final String codigo;
    private final String descricao;

    SituacaoImpressoraEnum(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static final SituacaoImpressoraEnum getByCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }

        for (SituacaoImpressoraEnum e : values()) {
            if (StringUtils.equalsIgnoreCase(StringUtils.trim(codigo), e.getCodigo())) {
                return e;
            }
        }
        return null;
    }

    public static String[] codigos() {
        return Arrays.stream(SituacaoImpressoraEnum.values())
                .map(SituacaoImpressoraEnum::getCodigo)
                .toArray(String[]::new);
    }

    public static String[] descricoes() {
        return Arrays.stream(SituacaoImpressoraEnum.values())
                .map(SituacaoImpressoraEnum::getDescricao)
                .toArray(String[]::new);
    }
}
