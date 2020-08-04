package com.b2wdigital.fazemu.domain;

import java.util.Date;

public class MunicipioAutorizador {

    private Long idMunicipio;
    private String tipoDocumentoFiscal;
    private Long tipoEmissao;
    private Long idAutorizador;
    private String situacao;
    private String usuario;
    private Date dataHora;

    public Long getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Long idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getTipoDocumentoFiscal() {
        return tipoDocumentoFiscal;
    }

    public void setTipoDocumentoFiscal(String tipoDocumentoFiscal) {
        this.tipoDocumentoFiscal = tipoDocumentoFiscal;
    }

    public Long getTipoEmissao() {
        return tipoEmissao;
    }

    public void setTipoEmissao(Long tipoEmissao) {
        this.tipoEmissao = tipoEmissao;
    }

    public Long getIdAutorizador() {
        return idAutorizador;
    }

    public void setIdAutorizador(Long idAutorizador) {
        this.idAutorizador = idAutorizador;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
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
