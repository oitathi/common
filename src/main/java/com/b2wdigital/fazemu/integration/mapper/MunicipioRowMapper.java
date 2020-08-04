package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.Municipio;

/**
 * Municipio RowMapper.
 * 
 * @author Marcelo Oliveira {marcelo.doliveira@b2wdigital.com}
 * @version 1.0
 */
@Component("municipioRowMapper")
public class MunicipioRowMapper extends AbstractRowMapper implements RowMapper<Municipio> {
	
    public static final String ID_MUNICIPIO     = "MUNI_ID_MUNICIPIO";
    public static final String ID_ESTADO        = "MUNI_ID_ESTADO";
    public static final String NOME             = "MUNI_NOME";
    public static final String COD_IBGE         = "MUNI_COD_IBGE";
    public static final String USUARIO          = "MUNI_USUARIO";
    public static final String DATAHORA         = "MUNI_DATAHORA";

    @Override
    public Municipio mapRow(ResultSet rs, int row) throws SQLException {
    	Municipio result = new Municipio();
        result.setIdMunicipio(rs.getLong(ID_MUNICIPIO));
        result.setIdEstado(rs.getLong(ID_ESTADO));
        result.setNome(rs.getString(NOME));
        result.setCodigoIbge(rs.getInt(COD_IBGE));
        result.setUsuario(rs.getString(USUARIO));
        result.setDataHora(rs.getTimestamp(DATAHORA));
        return result;
    }
}
