package com.b2wdigital.fazemu.domain;

public class CallbackMessage {

    private WsMetodo wsMetodo;
    private String xml;
    private Long idIntefaceEvento;

    public WsMetodo getWsMetodo() {
        return wsMetodo;
    }

    public void setWsMetodo(WsMetodo wsMetodo) {
        this.wsMetodo = wsMetodo;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public Long getIdIntefaceEvento() {
        return idIntefaceEvento;
    }

    public void setIdIntefaceEvento(Long idIntefaceEvento) {
        this.idIntefaceEvento = idIntefaceEvento;
    }

}
