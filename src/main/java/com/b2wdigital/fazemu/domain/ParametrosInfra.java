package com.b2wdigital.fazemu.domain;

import java.util.Date;

public class ParametrosInfra {

    private String idParametro;
    private String tipoDocumentoFiscal;
    private String valor;
    private String descricao;
    private String tipo;
    private String usuario;
    private Date dataHora;

    public static ParametrosInfra build(String idParametro, String tipoDocumentoFiscal, String valor, String descricao, String tipo, String usuario) {
        ParametrosInfra result = new ParametrosInfra();
        result.setIdParametro(idParametro);
        result.setTipoDocumentoFiscal(tipoDocumentoFiscal);
        result.setValor(valor);
        result.setDescricao(descricao);
        result.setTipo(tipo);
        result.setUsuario(usuario);
        return result;
    }

    public String getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(String idParametro) {
        this.idParametro = idParametro;
    }

    public String getTipoDocumentoFiscal() {
        return tipoDocumentoFiscal;
    }

    public void setTipoDocumentoFiscal(String tipoDocumentoFiscal) {
        this.tipoDocumentoFiscal = tipoDocumentoFiscal;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
