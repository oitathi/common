package com.b2wdigital.fazemu.domain.form;

/**
 *
 * @author marcelo.doliveira
 */
public class EstadoTipoEmissaoForm extends AbstractForm {

    private String idEstado;
    private String tipoEmissao;
    private String dataInicioEmissao;
    private String dataFimEmissao;
    private String justificativa;
    private String usuario;
    private String dataHora;

    public String getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(String idEstado) {
        this.idEstado = idEstado;
    }

    public String getTipoEmissao() {
        return tipoEmissao;
    }

    public void setTipoEmissao(String tipoEmissao) {
        this.tipoEmissao = tipoEmissao;
    }

    public String getDataInicioEmissao() {
        return dataInicioEmissao;
    }

    public void setDataInicioEmissao(String dataInicioEmissao) {
        this.dataInicioEmissao = dataInicioEmissao;
    }

    public String getDataFimEmissao() {
        return dataFimEmissao;
    }

    public void setDataFimEmissao(String dataFimEmissao) {
        this.dataFimEmissao = dataFimEmissao;
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

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

}
