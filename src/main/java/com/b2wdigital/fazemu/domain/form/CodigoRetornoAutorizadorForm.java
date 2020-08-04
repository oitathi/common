package com.b2wdigital.fazemu.domain.form;

public class CodigoRetornoAutorizadorForm extends AbstractForm {

    private String id;
    private String tipoDocumentoFiscal;
    private String descricao;
    private String situacaoAutorizador;
    private String situacaoAutorizadorTXT;
    private String usuario;
    private String dataHora;

    public static CodigoRetornoAutorizadorForm build(String id) {
        CodigoRetornoAutorizadorForm codigo = new CodigoRetornoAutorizadorForm();
        codigo.setId(id);
        return codigo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipoDocumentoFiscal() {
        return tipoDocumentoFiscal;
    }

    public void setTipoDocumentoFiscal(String tipoDocumentoFiscal) {
        this.tipoDocumentoFiscal = tipoDocumentoFiscal;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSituacaoAutorizador() {
        return situacaoAutorizador;
    }

    public void setSituacaoAutorizador(String situacaoAutorizador) {
        this.situacaoAutorizador = situacaoAutorizador;
    }

    public String getSituacaoAutorizadorTXT() {
        return situacaoAutorizadorTXT;
    }

    public void setSituacaoAutorizadorTXT(String situacaoAutorizadorTXT) {
        this.situacaoAutorizadorTXT = situacaoAutorizadorTXT;
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
