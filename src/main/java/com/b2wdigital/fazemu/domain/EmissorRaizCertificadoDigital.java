package com.b2wdigital.fazemu.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class EmissorRaizCertificadoDigital {

    private Long id;
    private Long idEmissorRaiz;
    private Date dataVigenciaInicio;
    private Date dataVigenciaFim;
    private byte[] certificadoBytes;
    private String senha;
    private String usuario;
    private Date dataHora;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("idEmissorRaiz", idEmissorRaiz)
                .append("dataVigenciaInicio", dataVigenciaInicio)
                .append("dataVigenciaFim", dataVigenciaFim)
                .append("certificadoBytes", "<BLOB>") //intencionalmente nao impresso devido ao tamanho
                .append("senha", "********") //intencionalmente nao impresso
                .append("usuario", usuario)
                .append("dataHora", dataHora)
                .toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdEmissorRaiz() {
        return idEmissorRaiz;
    }

    public void setIdEmissorRaiz(Long idEmissorRaiz) {
        this.idEmissorRaiz = idEmissorRaiz;
    }

    public Date getDataVigenciaInicio() {
        return dataVigenciaInicio;
    }

    public void setDataVigenciaInicio(Date dataVigenciaInicio) {
        this.dataVigenciaInicio = dataVigenciaInicio;
    }

    public Date getDataVigenciaFim() {
        return dataVigenciaFim;
    }

    public void setDataVigenciaFim(Date dataVigenciaFim) {
        this.dataVigenciaFim = dataVigenciaFim;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public byte[] getCertificadoBytes() {
        return certificadoBytes;
    }

    public void setCertificadoBytes(byte[] certificadoBytes) {
        this.certificadoBytes = certificadoBytes;
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
