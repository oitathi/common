package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.EmissorRaiz;

/**
 * Emissor Raiz RowMapper.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
@Component("emissorRaizRowMapper")
public class EmissorRaizRowMapper extends AbstractRowMapper implements RowMapper<EmissorRaiz> {
    
    protected static final String ID            = "EMRA_ID_EMISSOR_RAIZ";
    protected static final String NOME          = "EMRA_NOME";
    protected static final String SITUACAO      = "EMRA_SITUACAO";
    protected static final String USUARIO       = "EMRA_USUARIO";
    protected static final String DATAHORA      = "EMRA_DATAHORA";
    protected static final String ID_IMPRESSORA = "EMRA_ID_IMPRESSORA";
    
    @Override
    public EmissorRaiz mapRow(ResultSet rs, int row) throws SQLException {
        EmissorRaiz result = new EmissorRaiz();
        result.setId(rs.getLong(ID));
        result.setNome(rs.getString(NOME));
        result.setSituacao(rs.getString(SITUACAO));
        result.setUsuario(rs.getString(USUARIO));
        result.setDataHora(rs.getTimestamp(DATAHORA));
        result.setIdImpressora(rs.getLong(ID_IMPRESSORA));
        return result;
    }

}
