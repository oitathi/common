package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.LoteEvento;

@Component("loteEventoRowMapper")
public class LoteEventoRowMapper extends AbstractRowMapper implements RowMapper<LoteEvento> {

    public static final String ID_EVENTO    = "LOEV_ID_EVENTO";
    public static final String ID_LOTE      = "LOEV_ID_LOTE";
    public static final String ID_PONTO     = "LOEV_ID_PONTO";
    public static final String ID_XML       = "LOEV_ID_XML";
    public static final String OBSERVACAO   = "LOEV_OBS";
    public static final String USUARIO      = "LOEV_USUARIO";
    public static final String DATAHORA     = "LOEV_DATAHORA";

    @Override
    public LoteEvento mapRow(ResultSet rs, int row) throws SQLException {
        LoteEvento result = new LoteEvento();
        result.setIdEvento(rs.getLong(ID_EVENTO));
        result.setIdLote(rs.getLong(ID_LOTE));
        result.setIdPonto(rs.getString(ID_PONTO));
        result.setIdXml(rs.getLong(ID_XML));
        result.setObservacao(rs.getString(OBSERVACAO));
        result.setUsuario(rs.getString(USUARIO));
        result.setDataHora(rs.getTimestamp(DATAHORA));

        return result;
    }

}
