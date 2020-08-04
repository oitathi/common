package com.b2wdigital.fazemu.enumeration;

/**
 * Tipo Emissao Enum.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
public enum TipoEmissaoEnum {

    NORMAL(1, "Emissão Normal"),
    FORMULARIO_SEGURANCA(2, "Contingência Formulário Segurança"),
    EPEC(4, "Contingência EPEC"),
    FS_DA(5, "Contingência FS-DA"),
    SVC_AN(6, "Contingência SVC-AN"),
    SVC_RS(7, "Contingência SVC-RS");

    private final Integer codigo;
    private final String descricao;

    TipoEmissaoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    /**
     * get by Codigo
     *
     * @param codigo
     * @return
     */
    public static final TipoEmissaoEnum getByCodigo(Integer codigo) {
        if (codigo == null) {
            return null;
        }

        for (TipoEmissaoEnum e : values()) {
            if (e.getCodigo().equals(codigo)) {
                return e;
            }
        }
        return null;
    }

}
