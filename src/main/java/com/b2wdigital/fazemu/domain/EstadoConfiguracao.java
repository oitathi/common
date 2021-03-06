package com.b2wdigital.fazemu.domain;

import java.util.Date;

public class EstadoConfiguracao {

    private Long idEstado;
    private String tipoDocumentoFiscal;
    private String inAtivo;
    private String inResponsavelTecnico;
    private String inCSRT;
    private String inEPECAutomatico;
    private Long quantidadeMinimaRegistros;
    private Long periodo;
    private Long periodoEPEC;
    private String usuario;
    private Date dataHora;

    public static EstadoConfiguracao build(String tipoDocumentoFiscal, String inAtivo, String inResponsavelTecnico, String inCSRT, String inEPECAutomatico, Long quantidadeMinimaRegistros, Long periodo, Long periodoEPEC, String usuario) {
        EstadoConfiguracao result = new EstadoConfiguracao();
        result.setTipoDocumentoFiscal(tipoDocumentoFiscal);
        result.setInAtivo(inAtivo);
        result.setInResponsavelTecnico(inResponsavelTecnico);
        result.setInCSRT(inCSRT);
        result.setInEPECAutomatico(inEPECAutomatico);
        result.setQuantidadeMinimaRegistros(quantidadeMinimaRegistros);
        result.setPeriodo(periodo);
        result.setPeriodoEPEC(periodoEPEC);
        result.setUsuario(usuario);
        return result;
    }

    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    public String getTipoDocumentoFiscal() {
        return tipoDocumentoFiscal;
    }

    public void setTipoDocumentoFiscal(String tipoDocumentoFiscal) {
        this.tipoDocumentoFiscal = tipoDocumentoFiscal;
    }

    public String getInAtivo() {
        return inAtivo;
    }

    public void setInAtivo(String inAtivo) {
        this.inAtivo = inAtivo;
    }

    public String getInResponsavelTecnico() {
        return inResponsavelTecnico;
    }

    public void setInResponsavelTecnico(String inResponsavelTecnico) {
        this.inResponsavelTecnico = inResponsavelTecnico;
    }

    public String getInCSRT() {
        return inCSRT;
    }

    public void setInCSRT(String inCSRT) {
        this.inCSRT = inCSRT;
    }

    public String getInEPECAutomatico() {
        return inEPECAutomatico;
    }

    public void setInEPECAutomatico(String inEPECAutomatico) {
        this.inEPECAutomatico = inEPECAutomatico;
    }

    public Long getQuantidadeMinimaRegistros() {
        return quantidadeMinimaRegistros;
    }

    public void setQuantidadeMinimaRegistros(Long quantidadeMinimaRegistros) {
        this.quantidadeMinimaRegistros = quantidadeMinimaRegistros;
    }

    public Long getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Long periodo) {
        this.periodo = periodo;
    }

    public Long getPeriodoEPEC() {
        return periodoEPEC;
    }

    public void setPeriodoEPEC(Long periodoEPEC) {
        this.periodoEPEC = periodoEPEC;
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
