package com.b2wdigital.fazemu.domain;

import java.util.Date;

public class Impressora {

    private Long id;
    private String nome;
    private String local;
    private String ip;
    private String porta;
    private String marca;
    private String modelo;
    private String situacao;
    private String usuario;
    private Date dataHora;

    public static Impressora build(String nome, String local, String ip, String porta, String marca, String modelo, String situacao, String usuario) {
        Impressora result = new Impressora();
        result.setNome(nome);
        result.setLocal(local);
        result.setIp(ip);
        result.setPorta(porta);
        result.setMarca(marca);
        result.setModelo(modelo);
        result.setSituacao(situacao);
        result.setUsuario(usuario);
        return result;
    }

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

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPorta() {
        return porta;
    }

    public void setPorta(String porta) {
        this.porta = porta;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
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
