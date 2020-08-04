package com.b2wdigital.fazemu.domain.form;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author dailton.almeida
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InutilizacaoForm {

    private String chaveAcesso;
    private Integer codigoIbge;
    private Integer ano;
    private Long idEmissor;
    private Integer modelo;
    private Long serieDocumentoFiscal;
    private Long numeroNFInicial;
    private Long numeroNFFinal;
    private String justificativa;
    private String usuario;
    private String mensagemRetorno;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String getChaveAcesso() {
        return chaveAcesso;
    }

    public void setChaveAcesso(String chaveAcesso) {
        this.chaveAcesso = chaveAcesso;
    }

    public Integer getCodigoIbge() {
        return codigoIbge;
    }

    public void setCodigoIbge(Integer codigoIbge) {
        this.codigoIbge = codigoIbge;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Long getIdEmissor() {
        return idEmissor;
    }

    public void setIdEmissor(Long idEmissor) {
        this.idEmissor = idEmissor;
    }

    public Integer getModelo() {
        return modelo;
    }

    public void setModelo(Integer modelo) {
        this.modelo = modelo;
    }

    public Long getSerieDocumentoFiscal() {
        return serieDocumentoFiscal;
    }

    public void setSerieDocumentoFiscal(Long serieDocumentoFiscal) {
        this.serieDocumentoFiscal = serieDocumentoFiscal;
    }

    public Long getNumeroNFInicial() {
        return numeroNFInicial;
    }

    public void setNumeroNFInicial(Long numeroNFInicial) {
        this.numeroNFInicial = numeroNFInicial;
    }

    public Long getNumeroNFFinal() {
        return numeroNFFinal;
    }

    public void setNumeroNFFinal(Long numeroNFFinal) {
        this.numeroNFFinal = numeroNFFinal;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getMensagemRetorno() {
        return mensagemRetorno;
    }

    public void setMensagemRetorno(String mensagemRetorno) {
        this.mensagemRetorno = mensagemRetorno;
    }
}
