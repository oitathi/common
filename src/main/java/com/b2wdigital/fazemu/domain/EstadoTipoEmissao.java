package com.b2wdigital.fazemu.domain;

import java.util.Date;

public class EstadoTipoEmissao {

    private Long idEstado;
    private Date dataInicio;
    private Date dataFim;
    private Long tipoEmissao;
    private String justificativa;
    private String usuario;
    private Date dataHora;

    public static EstadoTipoEmissao build(Long idEstado, Date dataInicio, Date dataFim, Long tipoEmissao, String justificativa, String usuario) {
        EstadoTipoEmissao result = new EstadoTipoEmissao();
        result.setIdEstado(idEstado);
        result.setDataInicio(dataInicio);
        result.setDataFim(dataFim);
        result.setTipoEmissao(tipoEmissao);
        result.setJustificativa(justificativa);
        result.setUsuario(usuario);
        return result;
    }

    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Long getTipoEmissao() {
        return tipoEmissao;
    }

    public void setTipoEmissao(Long tipoEmissao) {
        this.tipoEmissao = tipoEmissao;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
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
