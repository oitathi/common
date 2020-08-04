package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.Impressora;

/**
 * Impressora RowMapper.
 * 
 * @author Marcelo Oliveira {marcelo.doliveira@b2wdigital.com}
 * @version 1.0
 */
@Component("impressoraRowMapper")
public class ImpressoraRowMapper extends AbstractRowMapper implements RowMapper<Impressora> {
	
    public static final String ID_IMPRESSORA    = "IMPR_ID_IMPRESSORA";
    public static final String NOME             = "IMPR_NOME";
    public static final String LOCAL            = "IMPR_LOCAL";
    public static final String IP               = "IMPR_IP";
    public static final String PORTA            = "IMPR_PORTA";
    public static final String MARCA            = "IMPR_MARCA";
    public static final String MODELO           = "IMPR_MODELO";
    public static final String SITUACAO         = "IMPR_SITUACAO";
    public static final String USUARIO          = "IMPR_USUARIO";
    public static final String DATAHORA         = "IMPR_DATAHORA";

    @Override
    public Impressora mapRow(ResultSet rs, int row) throws SQLException {
    	Impressora result = new Impressora();
        result.setId(rs.getLong(ID_IMPRESSORA));
        result.setNome(rs.getString(NOME));
        result.setLocal(rs.getString(LOCAL));
        result.setIp(rs.getString(IP));
        result.setPorta(rs.getString(PORTA));
        result.setMarca(rs.getString(MARCA));
        result.setModelo(rs.getString(MODELO));
        result.setSituacao(rs.getString(SITUACAO));
        result.setUsuario(rs.getString(USUARIO));
        result.setDataHora(rs.getTimestamp(DATAHORA));
        return result;
    }
}
