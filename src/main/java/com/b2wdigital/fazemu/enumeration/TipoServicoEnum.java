package com.b2wdigital.fazemu.enumeration;

import org.apache.commons.lang3.StringUtils;

public enum TipoServicoEnum {
    
    AUTORIZACAO("AUTR", "Autoriza\u00E7\u00E3o"),
    CANCELAMENTO("CANC", "Cancelamento"),
    CARTA_CORRECAO("CCOR", "Carta de Corre\u00E7\u00E3o"),
    MANIFESTACAO("MANF", "Manifesta\u00E7\u00E3o"),
    INUTILIZACAO("INUT", "Inutiliza\u00E7\u00E3o");

    private final String tipoRetorno;
    private final String descricao;

    TipoServicoEnum(String tipoRetorno, String descricao) {
        this.tipoRetorno = tipoRetorno;
        this.descricao = descricao;
    }

    public String getTipoRetorno() {
        return tipoRetorno;
    }

    public String getDescricao() {
        return descricao;
    }

    /**
     * Get by Tipo Retorno
     * 
     * @param tipoRetorno
     * @return
     */
    public static final TipoServicoEnum getByTipoRetorno(String tipoRetorno) {
        if (tipoRetorno == null) {
            return null;
        }

        for (TipoServicoEnum e : values()) {
            if (StringUtils.equalsIgnoreCase(StringUtils.trim(tipoRetorno), e.tipoRetorno)) {
                return e;
            }
        }
        return null;
    }

}
