package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.DocumentoClob;

/**
 * Documento Clob RowMapper.
 * 
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
@Component("documentoClobRowMapper")
public class DocumentoClobRowMapper extends AbstractRowMapper implements RowMapper<DocumentoClob> {

	protected static final String ID        = "DOCL_ID_CLOB";    
	protected static final String CLOB      = "DOCL_CLOB";
	protected static final String USUARIO   = "DOCL_USUARIO"; 
	protected static final String DATAHORA  = "DOCL_DATAHORA";
	
    @Override
    public DocumentoClob mapRow(ResultSet rs, int row) throws SQLException {
    	DocumentoClob result = new DocumentoClob();
        result.setId(rs.getLong(ID));
        result.setClob(rs.getString(CLOB));
        result.setUsuario(rs.getString(USUARIO));
        result.setDataHora(rs.getTimestamp(DATAHORA));
        
        return result;

    }

}
