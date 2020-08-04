package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.EmissorRaizLogo;

@Component("emissorRaizLogoRowMapper")
public class EmissorRaizLogoRowMapper implements RowMapper<EmissorRaizLogo> {
    
    protected static final String ID_EMISSOR_RAIZ = "ELOG_ID_EMISSOR_RAIZ";
    protected static final String ID_LOGO         = "ELOG_ID_LOGO";
    protected static final String USUARIO         = "ELOG_USUARIO";
    protected static final String DATAHORA        = "ELOG_DATAHORA";
    protected static final String LOGO            = "ELOG_LOGO";

    @Override
    public EmissorRaizLogo mapRow(ResultSet rs, int rowNum) throws SQLException {
        EmissorRaizLogo result = new EmissorRaizLogo();
        result.setIdEmissorRaiz(rs.getLong(ID_EMISSOR_RAIZ));
        result.setIdLogo(rs.getString(ID_LOGO));
        result.setUsuario(rs.getString(USUARIO));
        result.setDataHora(rs.getTimestamp(DATAHORA));
        result.setLogo(rs.getBytes(LOGO));
        return result;
    }
    
}
