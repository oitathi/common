package com.b2wdigital.fazemu.domain;

import java.util.Date;

public class ResponsavelTecnicoCsrt {

    private Long id;
    private Long idResponsavelTecnico;
    private Long idEstado;
    private Long idCsrt;
    private String csrt;
    private String situacao;
    private String usuario;
    private Date dataHora;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdResponsavelTecnico() {
        return idResponsavelTecnico;
    }

    public void setIdResponsavelTecnico(Long idResponsavelTecnico) {
        this.idResponsavelTecnico = idResponsavelTecnico;
    }

    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    public Long getIdCsrt() {
        return idCsrt;
    }

    public void setIdCsrt(Long idCsrt) {
        this.idCsrt = idCsrt;
    }

    public String getCsrt() {
        return csrt;
    }

    public void setCsrt(String csrt) {
        this.csrt = csrt;
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
