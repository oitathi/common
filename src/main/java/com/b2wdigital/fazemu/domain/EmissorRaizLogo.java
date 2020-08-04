package com.b2wdigital.fazemu.domain;

import java.util.Date;

/**
 *
 * @author dailton.almeida
 */
public class EmissorRaizLogo {

    private Long idEmissorRaiz;
    private String idLogo;
    private String usuario;
    private Date dataHora;
    private byte[] logo;

    public Long getIdEmissorRaiz() {
        return idEmissorRaiz;
    }

    public void setIdEmissorRaiz(Long idEmissorRaiz) {
        this.idEmissorRaiz = idEmissorRaiz;
    }

    public String getIdLogo() {
        return idLogo;
    }

    public void setIdLogo(String idLogo) {
        this.idLogo = idLogo;
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

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }
}
