package com.b2wdigital.fazemu.domain;

import java.util.Date;

public class Lote {

    private Long id;
    private String tipoDocumentoFiscal;
    private Long tipoEmissao;
    private Long idEmissorRaiz;
    private Long idEstado;
    private Long idMunicipio;
    private String servico;
    private String versao;
    private String url;
    private String idPonto;
    private Date dataUltimaConsulta;
    private Long reciboAutorizacao;
    private Integer situacaoAutorizacao;
    private String situacao;
    private String usuarioReg;
    private Date dataHoraReg;
    private String usuario;
    private Date dataHora;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoDocumentoFiscal() {
        return tipoDocumentoFiscal;
    }

    public void setTipoDocumentoFiscal(String tipoDocumentoFiscal) {
        this.tipoDocumentoFiscal = tipoDocumentoFiscal;
    }

    public Long getTipoEmissao() {
        return tipoEmissao;
    }

    public void setTipoEmissao(Long tipoEmissao) {
        this.tipoEmissao = tipoEmissao;
    }

    public Long getIdEmissorRaiz() {
        return idEmissorRaiz;
    }

    public void setIdEmissorRaiz(Long idEmissorRaiz) {
        this.idEmissorRaiz = idEmissorRaiz;
    }

    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    public Long getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Long idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIdPonto() {
        return idPonto;
    }

    public void setIdPonto(String idPonto) {
        this.idPonto = idPonto;
    }

    public Date getDataUltimaConsulta() {
        return dataUltimaConsulta;
    }

    public void setDataUltimaConsulta(Date dataUltimaConsulta) {
        this.dataUltimaConsulta = dataUltimaConsulta;
    }

    public Long getReciboAutorizacao() {
        return reciboAutorizacao;
    }

    public void setReciboAutorizacao(Long reciboAutorizacao) {
        this.reciboAutorizacao = reciboAutorizacao;
    }

    public Integer getSituacaoAutorizacao() {
        return situacaoAutorizacao;
    }

    public void setSituacaoAutorizacao(Integer situacaoAutorizacao) {
        this.situacaoAutorizacao = situacaoAutorizacao;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getUsuarioReg() {
        return usuarioReg;
    }

    public void setUsuarioReg(String usuarioReg) {
        this.usuarioReg = usuarioReg;
    }

    public Date getDataHoraReg() {
        return dataHoraReg;
    }

    public void setDataHoraReg(Date dataHoraReg) {
        this.dataHoraReg = dataHoraReg;
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
