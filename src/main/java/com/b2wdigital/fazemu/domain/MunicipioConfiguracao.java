package com.b2wdigital.fazemu.domain;

import java.util.Date;

public class MunicipioConfiguracao {

    private Long idMunicipio;
    private String tipoDocumentoFiscal;
    private String inAtivo;
    private String inLote;
    private String usuario;
    private Date dataHora;

    public static MunicipioConfiguracao build(String tipoDocumentoFiscal, String inAtivo, String inLote, String usuario) {
        MunicipioConfiguracao result = new MunicipioConfiguracao();
        result.setTipoDocumentoFiscal(tipoDocumentoFiscal);
        result.setInAtivo(inAtivo);
        result.setInLote(inLote);
        result.setUsuario(usuario);
        return result;
    }

    public Long getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Long idMunicipio) {
        this.idMunicipio = idMunicipio;
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

    public String getInLote() {
        return inLote;
    }

    public void setInLote(String inLote) {
        this.inLote = inLote;
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
