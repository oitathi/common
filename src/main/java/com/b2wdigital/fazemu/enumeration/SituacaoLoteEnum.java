package com.b2wdigital.fazemu.enumeration;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

/**
 * Situacao Lote Enum.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
public enum SituacaoLoteEnum {

    ABERTO("A", "Aberto"),
    FECHADO("F", "Fechado"),
    ERRO("E", "Erro"),
    ENVIADO("V", "Enviado"),
    LIQUIDADO("L", "Liquidado"),
    CANCELADO("C", "Cancelado");

    private final String codigo;
    private final String descricao;

    SituacaoLoteEnum(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static final SituacaoLoteEnum getByCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }

        for (SituacaoLoteEnum e : values()) {
            if (StringUtils.equalsIgnoreCase(StringUtils.trim(codigo), e.getCodigo())) {
                return e;
            }
        }
        return null;
    }

    public static String[] codigos() {
        return Arrays.stream(SituacaoLoteEnum.values())
                .map(SituacaoLoteEnum::getCodigo)
                .toArray(String[]::new);
    }

    public static String[] descricoes() {
        return Arrays.stream(SituacaoLoteEnum.values())
                .map(SituacaoLoteEnum::getDescricao)
                .toArray(String[]::new);
    }
}
