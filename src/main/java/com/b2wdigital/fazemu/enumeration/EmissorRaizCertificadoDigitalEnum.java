package com.b2wdigital.fazemu.enumeration;

import org.apache.commons.lang3.StringUtils;

/**
 * EmissorRaizCertificadoDigital Enum.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
public enum EmissorRaizCertificadoDigitalEnum {

    A_EXPIRAR("A", "A EXPIRAR", "warning"),
    EXPIRADO("E", "EXPIRADO", "danger"),
    VALIDO("V", "VÁLIDO", "success");

    private final String codigo;
    private final String descricao;
    private final String badge;

    EmissorRaizCertificadoDigitalEnum(String codigo, String descricao, String badge) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.badge = badge;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getBadge() {
        return badge;
    }

    public static final EmissorRaizCertificadoDigitalEnum getByCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }

        for (EmissorRaizCertificadoDigitalEnum e : values()) {
            if (StringUtils.equalsIgnoreCase(StringUtils.trim(codigo), e.getCodigo())) {
                return e;
            }
        }
        return null;
    }

}
