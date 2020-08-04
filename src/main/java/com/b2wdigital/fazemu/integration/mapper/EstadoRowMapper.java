package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.Estado;

/**
 * Estado RowMapper.
 * 
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
@Component("estadoRowMapper")
public class EstadoRowMapper extends AbstractRowMapper implements RowMapper<Estado> {
	
    public static final String ID       = "ESTA_ID_ESTADO";
    public static final String SIGLA    = "ESTA_ID_UF";
    public static final String NOME     = "ESTA_NOME";
    public static final String COD_IBGE = "ESTA_COD_IBGE";
    public static final String USUARIO  = "ESTA_USUARIO";
    public static final String DATAHORA = "ESTA_DATAHORA";

    @Override
    public Estado mapRow(ResultSet rs, int row) throws SQLException {
    	Estado result = new Estado();
        result.setId(rs.getLong(ID));
        result.setSigla(rs.getString(SIGLA));
        result.setNome(rs.getString(NOME));
        result.setCodigoIbge(rs.getInt(COD_IBGE));
        result.setUsuario(rs.getString(USUARIO));
        result.setDataHora(rs.getTimestamp(DATAHORA));
        return result;
    }
}
