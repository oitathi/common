package com.b2wdigital.fazemu.enumeration;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

/**
 * Interface Evento Enum.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
public enum InterfaceEventoEnum {

    ABERTO("A", "Aberto"),
    ERRO("E", "Erro"),
    LIQUIDADO("L", "Liquidado"),
    CANCELADO("C", "Cancelado");

    private final String codigo;
    private final String descricao;

    InterfaceEventoEnum(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static final InterfaceEventoEnum getByCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }

        for (InterfaceEventoEnum e : values()) {
            if (StringUtils.equalsIgnoreCase(StringUtils.trim(codigo), e.getCodigo())) {
                return e;
            }
        }
        return null;
    }

    public static String[] codigos() {
        return Arrays.stream(InterfaceEventoEnum.values())
                .map(InterfaceEventoEnum::getCodigo)
                .toArray(String[]::new);
    }

    public static String[] descricoes() {
        return Arrays.stream(InterfaceEventoEnum.values())
                .map(InterfaceEventoEnum::getDescricao)
                .toArray(String[]::new);
    }
}
