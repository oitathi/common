package com.b2wdigital.fazemu.enumeration;

/**
 * RecepcaoEventoEnum.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
public enum RecepcaoEventoEnum {

    CANCELAMENTO(110111, "Cancelamento"),
    CARTA_CORRECAO(110110, "Carta de Correção"),
    MANIFESTACAO_CONFIRMACAO_OPERACAO(210200, "Confirmacao da Operacao"),
    MANIFESTACAO_CIENCIA_OPERACAO(210210, "Ciencia da Operacao"),
    MANIFESTACAO_DESCONHECIMENTO_OPERACAO(210220, "Desconhecimento da Operacao"),
    MANIFESTACAO_OPERACAO_NAO_REALIZADA(210240, "Operacao nao Realizada"),
    EPEC(110140, "Epec");

    private final Integer codigoEvento;
    private final String descricao;

    RecepcaoEventoEnum(Integer codigoEvento, String descricao) {
        this.codigoEvento = codigoEvento;
        this.descricao = descricao;
    }

    public Integer getCodigoEvento() {
        return codigoEvento;
    }

    public String getDescricao() {
        return descricao;
    }

    /**
     * get by Codigo
     *
     * @param codigoEvento
     * @return
     */
    public static final RecepcaoEventoEnum getByCodigoEvento(Integer codigoEvento) {
        if (codigoEvento == null) {
            return null;
        }

        for (RecepcaoEventoEnum e : values()) {
            if (e.getCodigoEvento().equals(codigoEvento)) {
                return e;
            }
        }
        return null;
    }

}
