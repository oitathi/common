package com.b2wdigital.fazemu.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author dailton.almeida
 */
public class ResumoDocumentoFiscal {

    private Long idDocFiscal;
    private String tipoDocumentoFiscal; //NFE ou NFSE
    private Long idEmissor;
    private Integer uf;                 //codigo do IBGE - UF
    private Long idMunicipio;           // IdMunicipio
    private Integer tipoEmissao;        //ex: 1 "normal"
    private String versao;              // ex: "v4.0"
    private Long idXML;
    private int tamanhoXML = 0;

    public static ResumoDocumentoFiscal build(Long idDocFiscal, String tipoDocumentoFiscal, Long idEmissor, Integer uf, Long idMunicipio, Integer tipoEmissao, String versao, Long idXML, int tamanhoXML) {
        ResumoDocumentoFiscal result = new ResumoDocumentoFiscal();
        result.idDocFiscal = idDocFiscal;
        result.tipoDocumentoFiscal = tipoDocumentoFiscal;
        result.idEmissor = idEmissor;
        result.uf = uf;
        result.idMunicipio = idMunicipio;
        result.tipoEmissao = tipoEmissao;
        result.versao = versao;
        result.idXML = idXML;
        result.tamanhoXML = tamanhoXML;
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public Long getIdDocFiscal() {
        return idDocFiscal;
    }

    public String getTipoDocumentoFiscal() {
        return tipoDocumentoFiscal;
    }

    public Long getIdEmissor() {
        return idEmissor;
    }

    public Integer getUf() {
        return uf;
    }

    public Long getIdMunicipio() {
        return idMunicipio;
    }

    public Integer getTipoEmissao() {
        return tipoEmissao;
    }

    public String getVersao() {
        return versao;
    }

    public Long getIdXML() {
        return idXML;
    }

    public int getTamanhoXML() {
        return tamanhoXML;
    }

}
