package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.DocumentoLote;

@Component("documentoLoteRowMapper")
public class DocumentoLoteRowMapper extends AbstractRowMapper implements RowMapper<DocumentoLote> {
	
    protected static final String ID_DOCUMENTO_FISCAL   = "DOLE_ID_DOC_FISCAL";
    protected static final String ID_XML                = "DOLE_ID_XML";
    protected static final String ID_LOTE               = "DOLE_ID_LOTE";
    protected static final String USUARIO               = "DOLE_USUARIO";
    protected static final String DATAHORA              = "DOLE_DATAHORA";

    @Override
    public DocumentoLote mapRow(ResultSet rs, int row) throws SQLException {
    	DocumentoLote result = new DocumentoLote();
        result.setIdDocumentoFiscal(rs.getLong(ID_DOCUMENTO_FISCAL));
        result.setIdXml(rs.getLong(ID_XML));
        result.setIdLote(rs.getLong(ID_LOTE));
        result.setUsuario(rs.getString(USUARIO));
        result.setDataHora(rs.getTimestamp(DATAHORA));

        return result;
    }

}
