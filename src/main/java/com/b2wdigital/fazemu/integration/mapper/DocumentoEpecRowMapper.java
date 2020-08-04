package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.DocumentoEpec;

/**
 * Documento Epec RowMapper
 * 
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
@Component("documentoEpecRowMapper")
public class DocumentoEpecRowMapper extends AbstractRowMapper implements RowMapper<DocumentoEpec> {
	
	
	public static final String ID_DOC_FISCAL		= "DPEC_ID_DOC_FISCAL";
	public static final String ID_ESTADO			= "DPEC_ID_ESTADO";
	public static final String TIPO_DOCUMENTO_FISCAL	= "DPEC_TP_DOC_FISCAL";
	public static final String SITUACAO			= "DPEC_SITUACAO";
	public static final String OBSERVACAO			= "DPEC_OBS";
	public static final String USUARIO_REG			= "DPEC_USUARIO_REG";
	public static final String DATAHORA_REG			= "DPEC_DATAHORA_REG";
	public static final String USUARIO			= "DPEC_USUARIO";
	public static final String DATAHORA			= "DPEC_DATAHORA";         


    @Override
    public DocumentoEpec mapRow(ResultSet rs, int row) throws SQLException {
    	DocumentoEpec result = new DocumentoEpec();
        result.setIdDocumentoFiscal(rs.getLong(ID_DOC_FISCAL));
        result.setIdEstado(rs.getLong(ID_ESTADO));
        result.setTipoDocumentoFiscal(rs.getString(TIPO_DOCUMENTO_FISCAL));
        result.setSituacao(rs.getString(SITUACAO));
        result.setObservacao(rs.getString(OBSERVACAO));
        result.setUsuarioReg(rs.getString(USUARIO_REG));
        result.setDataHoraReg(rs.getTimestamp(DATAHORA_REG));
        result.setUsuario(rs.getString(USUARIO));
        result.setDataHora(rs.getTimestamp(DATAHORA));
        
        return result;
    }

}