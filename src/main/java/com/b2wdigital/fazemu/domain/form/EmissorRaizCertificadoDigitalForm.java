package com.b2wdigital.fazemu.domain.form;

import java.util.Date;

public class EmissorRaizCertificadoDigitalForm extends AbstractForm implements  Comparable<EmissorRaizCertificadoDigitalForm>{

    private String id;
    private String idEmissorRaiz;
    private String nomeEmissorRaiz;
    private String dataVigenciaInicio;
    private String dataVigenciaFim;
    private String dataExpiracaoCertificado;
    private String badge;
    private String situacao;
    private String senha;
    private String usuario;
    private Date dataHora;

    private byte[] fileInput;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdEmissorRaiz() {
        return idEmissorRaiz;
    }

    public void setIdEmissorRaiz(String idEmissorRaiz) {
        this.idEmissorRaiz = idEmissorRaiz;
    }

    public String getNomeEmissorRaiz() {
        return nomeEmissorRaiz;
    }

    public void setNomeEmissorRaiz(String nomeEmissorRaiz) {
        this.nomeEmissorRaiz = nomeEmissorRaiz;
    }

    public String getDataVigenciaInicio() {
        return dataVigenciaInicio;
    }

    public void setDataVigenciaInicio(String dataVigenciaInicio) {
        this.dataVigenciaInicio = dataVigenciaInicio;
    }

    public String getDataVigenciaFim() {
        return dataVigenciaFim;
    }

    public void setDataVigenciaFim(String dataVigenciaFim) {
        this.dataVigenciaFim = dataVigenciaFim;
    }

    public String getDataExpiracaoCertificado() {
        return dataExpiracaoCertificado;
    }

    public void setDataExpiracaoCertificado(String dataExpiracaoCertificado) {
        this.dataExpiracaoCertificado = dataExpiracaoCertificado;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public byte[] getFileInput() {
        return fileInput;
    }

    public void setFileInput(byte[] fileInput) {
        this.fileInput = fileInput;
    }

	@Override
	public int compareTo(EmissorRaizCertificadoDigitalForm o) {
		return this.getNomeEmissorRaiz().compareTo(o.getNomeEmissorRaiz());
	}

	
	
	

}
