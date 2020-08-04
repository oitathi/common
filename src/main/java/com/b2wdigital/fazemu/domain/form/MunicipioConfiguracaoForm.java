package com.b2wdigital.fazemu.domain.form;

import java.util.Date;

/**
 *
 * @author marcelo.doliveira
 */
public class MunicipioConfiguracaoForm extends AbstractForm {

    private Long idMunicipio;
    private String tipoDocumentoFiscal;
    private String inAtivo;
    private String inLote;
    private String usuario;
    private Date dataHora;

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
