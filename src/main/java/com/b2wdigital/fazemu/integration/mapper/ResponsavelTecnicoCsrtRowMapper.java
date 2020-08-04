package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.ResponsavelTecnicoCsrt;

/**
 * Responsavel Tecnico RowMapper.
 * 
 * @author Marcelo Oliveira {marcelo.doliveira@b2wdigital.com}
 * @version 1.0
 */
@Component("responsavelTecnicoCsrtRowMapper")
public class ResponsavelTecnicoCsrtRowMapper extends AbstractRowMapper implements RowMapper<ResponsavelTecnicoCsrt> {
	
    public static final String ID               = "RTCS_ID_RESP_TECNICO_CSRT";
    public static final String ID_RESP_TECNICO  = "RTCS_ID_RESP_TECNICO";
    public static final String ID_ESTADO        = "RTCS_ID_ESTADO";
    public static final String ID_CSRT          = "RTCS_ID_CSRT";
    public static final String CSRT             = "RTCS_CSRT";
    public static final String SITUACAO         = "RTCS_SITUACAO";
    public static final String USUARIO          = "RTCS_USUARIO";
    public static final String DATAHORA         = "RTCS_DATAHORA";

    @Override
    public ResponsavelTecnicoCsrt mapRow(ResultSet rs, int row) throws SQLException {
    	ResponsavelTecnicoCsrt result = new ResponsavelTecnicoCsrt();
        result.setId(rs.getLong(ID));
        result.setIdResponsavelTecnico(rs.getLong(ID_RESP_TECNICO));
        result.setIdEstado(rs.getLong(ID_ESTADO));
        result.setIdCsrt(rs.getLong(ID_CSRT));
        result.setCsrt(rs.getString(CSRT));
        result.setSituacao(rs.getString(SITUACAO));
        result.setUsuario(rs.getString(USUARIO));
        result.setDataHora(rs.getTimestamp(DATAHORA));
        return result;
    }
}
