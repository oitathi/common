package com.b2wdigital.fazemu.domain;

import java.util.Date;

public class InterfaceEvento {

    private Long idEvento;
    private String idSistema;
    private Long idMetodo;
    private String tipoServico;
    private Long idDocFiscal;
    private String observacao;
    private String situacao;
    private String usuarioRegistro;
    private Date dataHoraRegistro;
    private String usuario;
    private Date dataHora;
    //Chave Acesso da DocumentoFiscal (Web)
    private String chaveAcessoEnviada;

    public Long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public String getIdSistema() {
        return idSistema;
    }

    public void setIdSistema(String idSistema) {
        this.idSistema = idSistema;
    }

    public Long getIdMetodo() {
        return idMetodo;
    }

    public void setIdMetodo(Long idMetodo) {
        this.idMetodo = idMetodo;
    }

    public String getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(String tipoServico) {
        this.tipoServico = tipoServico;
    }

    public Long getIdDocFiscal() {
        return idDocFiscal;
    }

    public void setIdDocFiscal(Long idDocFiscal) {
        this.idDocFiscal = idDocFiscal;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public Date getDataHoraRegistro() {
        return dataHoraRegistro;
    }

    public void setDataHoraRegistro(Date dataHoraRegistro) {
        this.dataHoraRegistro = dataHoraRegistro;
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

    public String getChaveAcessoEnviada() {
        return chaveAcessoEnviada;
    }

    public void setChaveAcessoEnviada(String chaveAcessoEnviada) {
        this.chaveAcessoEnviada = chaveAcessoEnviada;
    }

}
