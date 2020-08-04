package com.b2wdigital.fazemu.domain;

import java.util.Date;

/**
 *
 * @author dailton.almeida
 */
public class CodigoRetornoAutorizador {
    private Integer id;
    private String tipoDocumentoFiscal;
    private String descricao;
    private String situacaoAutorizador;
    private String usuario;
    private Date dataHora;

    public static CodigoRetornoAutorizador build(Integer id) {
        CodigoRetornoAutorizador codigo = new CodigoRetornoAutorizador();
        codigo.setId(id);
        return codigo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
