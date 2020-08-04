package com.b2wdigital.fazemu.domain;

import java.util.Date;

public class TipoEmissao {

    private Long id;
    private String nome;
    private String indicadorImpressao;
    private String situacao;
    private String usuario;
    private Date dataHora;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIndicadorImpressao() {
        return indicadorImpressao;
    }

    public void setIndicadorImpressao(String indicadorImpressao) {
        this.indicadorImpressao = indicadorImpressao;
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
