package com.b2wdigital.fazemu.domain;

import java.util.Date;

public class EstadoAutorizador {

    private Long idEstado;
    private String tipoDocumentoFiscal;
    private Long tipoEmissao;
    private Long idAutorizador;
    private String situacao;
    private String usuario;
    private Date dataHora;

    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
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
