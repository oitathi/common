package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.DocumentoEvento;

/**
 * Documento Evento RowMapper.
 * 
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
@Component("documentoEventoRowMapper")
public class DocumentoEventoRowMapper extends AbstractRowMapper implements RowMapper<DocumentoEvento> {
	
	public static final String ID                   = "DOEV_ID_EVENTO";    
	public static final String ID_DOCUMENTO_FISCAL  = "DOEV_ID_DOC_FISCAL";
	public static final String ID_PONTO             = "DOEV_ID_PONTO";
	public static final String TIPO_SERVICO         = "DOEV_TP_SERVICO";  
	public static final String SITUACAO_AUTORIZADOR = "DOEV_SITUACAO_AUTOR";
	public static final String ID_XML               = "DOEV_ID_XML";    
	public static final String USUARIO              = "DOEV_USUARIO"; 
	public static final String DATAHORA             = "DOEV_DATAHORA";

    @Override
    public DocumentoEvento mapRow(ResultSet rs, int row) throws SQLException {
    	DocumentoEvento result = new DocumentoEvento();
        result.setId(rs.getLong(ID));
        result.setIdDocumentoFiscal(rs.getLong(ID_DOCUMENTO_FISCAL));
        result.setIdPonto(rs.getString(ID_PONTO));
        result.setTipoServico(rs.getString(TIPO_SERVICO));
        result.setSituacaoAutorizador(rs.getString(SITUACAO_AUTORIZADOR));
        result.setIdXml(rs.getLong(ID_XML));
        result.setUsuario(rs.getString(USUARIO));
        result.setDataHora(rs.getTimestamp(DATAHORA));
        
        return result;  

    }

}
