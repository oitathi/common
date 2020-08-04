package com.b2wdigital.fazemu.domain;

import java.util.Date;

public class AutorizadorServico {

    private Long idAutorizador;
    private String idServico;
    private String versao;
    private String url;
    private String situacao;
    private String usuario;
    private Date dataHora;

    public Long getIdAutorizador() {
        return idAutorizador;
    }

    public void setIdAutorizador(Long idAutorizador) {
        this.idAutorizador = idAutorizador;
    }

    public String getIdServico() {
        return idServico;
    }

    public void setIdServico(String idServico) {
        this.idServico = idServico;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
