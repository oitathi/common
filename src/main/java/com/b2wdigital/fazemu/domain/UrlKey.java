package com.b2wdigital.fazemu.domain;

import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author dailton.almeida
 */
public class UrlKey {

    private Integer codigoIBGE;
    private Integer idTipoEmissao;
    private String idServico;
    private String versao;

    public static UrlKey build(Integer codigoIBGE, Integer idTipoEmissao, String idServico, String versao) {
        UrlKey key = new UrlKey();
        key.codigoIBGE = codigoIBGE;
        key.idTipoEmissao = idTipoEmissao;
        key.idServico = idServico;
        key.versao = versao;
        return key;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("codigoIBGE", codigoIBGE)
                .append("idTipoEmissao", idTipoEmissao)
                .append("idServico", idServico)
                .append("versao", versao)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (o == this) {
            return true;
        } else if (o instanceof UrlKey) {
            UrlKey other = (UrlKey) o;
            return new EqualsBuilder()
                    .append(this.codigoIBGE, other.codigoIBGE)
                    .append(this.idTipoEmissao, other.idTipoEmissao)
                    .append(this.idServico, other.idServico)
                    .append(this.versao, other.versao)
                    .isEquals();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoIBGE, idTipoEmissao, idServico, versao);
    }

    public Integer getCodigoIBGE() {
        return codigoIBGE;
    }

    public Integer getIdTipoEmissao() {
        return idTipoEmissao;
    }

    public String getIdServico() {
        return idServico;
    }

    public String getVersao() {
        return versao;
    }
}
