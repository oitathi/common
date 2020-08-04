package com.b2wdigital.fazemu.domain;

import java.util.Date;

import com.b2wdigital.fazemu.utils.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class DocumentoEvento {

    private Long id;
    private Long idDocumentoFiscal;
    private String idPonto;
    private String tipoServico;
    private String situacaoAutorizador;
    private Long idXml;
    private String usuario;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date dataHora;

    public static DocumentoEvento build(Long id, Long idDocumentoFiscal, String idPonto, String tipoServico, String situacaoAutorizador, Long idXml, String usuario) {
        DocumentoEvento result = new DocumentoEvento();
        result.setId(id);
        result.setIdDocumentoFiscal(idDocumentoFiscal);
        result.setIdPonto(idPonto);
        result.setTipoServico(tipoServico);
        result.setSituacaoAutorizador(situacaoAutorizador);
        result.setIdXml(idXml);
        result.setUsuario(usuario);
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdDocumentoFiscal() {
        return idDocumentoFiscal;
    }

    public void setIdDocumentoFiscal(Long idDocumentoFiscal) {
        this.idDocumentoFiscal = idDocumentoFiscal;
    }

    public String getIdPonto() {
        return idPonto;
    }

    public void setIdPonto(String idPonto) {
        this.idPonto = idPonto;
    }

    public String getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(String tipoServico) {
        this.tipoServico = tipoServico;
    }

    public String getSituacaoAutorizador() {
        return situacaoAutorizador;
    }

    public void setSituacaoAutorizador(String situacaoAutorizador) {
        this.situacaoAutorizador = situacaoAutorizador;
    }

    public Long getIdXml() {
        return idXml;
    }

    public void setIdXml(Long idXml) {
        this.idXml = idXml;
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
