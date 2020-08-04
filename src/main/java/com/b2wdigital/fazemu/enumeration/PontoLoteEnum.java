package com.b2wdigital.fazemu.enumeration;

import org.apache.commons.lang3.StringUtils;

/**
 * Ponto Lote Enum.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
public enum PontoLoteEnum {

    ABERTO("LABE", "Aberto"),
    ADICAO("LADI", "Adi\u00E7\u00E3o"), //adicao de um documento fiscal a esse lote
    FECHADO("LFEC", "Fechado"),
    ENVIADO("LENV", "Enviado"),
    RECEBIDO("LREC", "Recebido"),
    CANCELADO("LCAN", "Cancelado"),
    LIQUIDADO("LLIQ", "Liquidado"),
    ERRO_FECHAR_LOTE("ERRF", "Erro ao Fechar Enviar Lote"),
    ERRO_CONSULTAR_LOTE("ERRC", "Erro ao Consultar Lote");

    private final String codigo;
    private final String descricao;

    PontoLoteEnum(String codigo, String descricao) {
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
    public static final PontoLoteEnum getByCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }

        for (PontoLoteEnum e : values()) {
            if (StringUtils.equalsIgnoreCase(StringUtils.trim(codigo), e.getCodigo())) {
                return e;
            }
        }
        return null;
    }

}
