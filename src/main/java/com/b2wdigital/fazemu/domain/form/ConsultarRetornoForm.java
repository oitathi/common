package com.b2wdigital.fazemu.domain.form;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author dailton.almeida
 */
public class ConsultarRetornoForm {

    private String chaveAcesso;
    private String tipoDocumentoFiscal;
    private String tipoRetorno;
    private Long idEmissor;
    private Long numeroDocumentoFiscal;
    private Long serieDocumentoFiscal;
    private Integer anoDocumentoFiscal;
    private Integer codigoIBGE;

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (o == this) {
            return true;
        } else if (o instanceof ConsultarRetornoForm) {
            ConsultarRetornoForm other = (ConsultarRetornoForm) o;
            return new EqualsBuilder()
                    .append(this.chaveAcesso, other.chaveAcesso)
                    .append(this.tipoDocumentoFiscal, other.tipoDocumentoFiscal)
                    .append(this.tipoRetorno, other.tipoRetorno)
                    .append(this.idEmissor, other.idEmissor)
                    .append(this.numeroDocumentoFiscal, other.numeroDocumentoFiscal)
                    .append(this.serieDocumentoFiscal, other.serieDocumentoFiscal)
                    .append(this.anoDocumentoFiscal, other.anoDocumentoFiscal)
                    .append(this.codigoIBGE, other.codigoIBGE)
                    .isEquals();
        } else {
            return false;
        }
    }

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

    public String getTipoDocumentoFiscal() {
        return tipoDocumentoFiscal;
    }

    public void setTipoDocumentoFiscal(String tipoDocumentoFiscal) {
        this.tipoDocumentoFiscal = tipoDocumentoFiscal;
    }

    public String getTipoRetorno() {
        return tipoRetorno;
    }

    public void setTipoRetorno(String tipoRetorno) {
        this.tipoRetorno = tipoRetorno;
    }

    public Long getIdEmissor() {
        return idEmissor;
    }

    public void setIdEmissor(Long idEmissor) {
        this.idEmissor = idEmissor;
    }

    public Long getNumeroDocumentoFiscal() {
        return numeroDocumentoFiscal;
    }

    public void setNumeroDocumentoFiscal(Long numeroDocumentoFiscal) {
        this.numeroDocumentoFiscal = numeroDocumentoFiscal;
    }

    public Long getSerieDocumentoFiscal() {
        return serieDocumentoFiscal;
    }

    public void setSerieDocumentoFiscal(Long serieDocumentoFiscal) {
        this.serieDocumentoFiscal = serieDocumentoFiscal;
    }

    public Integer getAnoDocumentoFiscal() {
        return anoDocumentoFiscal;
    }

    public void setAnoDocumentoFiscal(Integer anoDocumentoFiscal) {
        this.anoDocumentoFiscal = anoDocumentoFiscal;
    }

    public Integer getCodigoIBGE() {
        return codigoIBGE;
    }

    public void setCodigoIBGE(Integer codigoIBGE) {
        this.codigoIBGE = codigoIBGE;
    }
}
