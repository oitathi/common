package com.b2wdigital.fazemu.domain;

import java.util.Date;

import com.b2wdigital.fazemu.domain.form.ResponsavelTecnicoForm;
import com.b2wdigital.fazemu.utils.MaskHandler;

public class ResponsavelTecnico {

    private Long idResponsavelTecnico;
    private Long idEmissorRaiz;
    private Long cnpj;
    private String contato;
    private String email;
    private Long telefone;
    private String situacao;
    private String usuario;
    private Date dataHora;

    public static ResponsavelTecnico build(ResponsavelTecnicoForm form) {
        ResponsavelTecnico result = new ResponsavelTecnico();
        result.setIdEmissorRaiz(MaskHandler.strToLong(form.getIdEmissorRaiz()));
        result.setCnpj(MaskHandler.strToLong(form.getCnpj()));
        result.setContato(form.getContato());
        result.setEmail(form.getEmail());
        result.setTelefone(MaskHandler.strToLong(form.getTelefone()));
        result.setSituacao(form.getSituacao());
        result.setUsuario(form.getUsuario());
        return result;
    }

    public Long getIdResponsavelTecnico() {
        return idResponsavelTecnico;
    }

    public void setIdResponsavelTecnico(Long idResponsavelTecnico) {
        this.idResponsavelTecnico = idResponsavelTecnico;
    }

    public Long getIdEmissorRaiz() {
        return idEmissorRaiz;
    }

    public void setIdEmissorRaiz(Long idEmissorRaiz) {
        this.idEmissorRaiz = idEmissorRaiz;
    }

    public Long getCnpj() {
        return cnpj;
    }

    public void setCnpj(Long cnpj) {
        this.cnpj = cnpj;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getTelefone() {
        return telefone;
    }

    public void setTelefone(Long telefone) {
        this.telefone = telefone;
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
