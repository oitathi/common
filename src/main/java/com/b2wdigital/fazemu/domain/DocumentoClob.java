package com.b2wdigital.fazemu.domain;

import java.util.Date;

public class DocumentoClob {

    private Long id;
    private String clob;
    private String usuario;
    private Date dataHora;

    public static DocumentoClob build(Long id, String clob, String usuario) {
        DocumentoClob result = new DocumentoClob();
        result.id = id;
        result.clob = clob;
        result.usuario = usuario;
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClob() {
        return clob;
    }

    public void setClob(String clob) {
        this.clob = clob;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

}
