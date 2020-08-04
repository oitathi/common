package com.b2wdigital.fazemu.domain;

import java.util.Date;

public class DocumentoLote {
    private Long idDocumentoFiscal;
    private Long idXml;
    private Long idLote;
    private String usuario;
    private Date dataHora;

    public static DocumentoLote build(Long idDocumentoFiscal, Long idXml, Long idLote, String usuario, Date dataHora) {
        DocumentoLote result = new DocumentoLote();
        result.idDocumentoFiscal = idDocumentoFiscal;
        result.idXml = idXml;
        result.idLote = idLote;
        result.usuario = usuario;
        return result;
    }

    public Long getIdDocumentoFiscal() {
        return idDocumentoFiscal;
    }

    public void setIdDocumentoFiscal(Long idDocumentoFiscal) {
        this.idDocumentoFiscal = idDocumentoFiscal;
    }

    public Long getIdXml() {
        return idXml;
    }

    public void setIdXml(Long idXml) {
        this.idXml = idXml;
    }

    public Long getIdLote() {
        return idLote;
    }

    public void setIdLote(Long idLote) {
        this.idLote = idLote;
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
