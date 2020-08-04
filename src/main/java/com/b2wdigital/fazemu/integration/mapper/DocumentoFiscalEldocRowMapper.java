package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.DocumentoFiscalEldoc;

/**
 * Documento Fiscal EldocRowMapper
 * 
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
@Component("documentoFiscalEldocRowMapper")
public class DocumentoFiscalEldocRowMapper extends AbstractRowMapper implements RowMapper<DocumentoFiscalEldoc> {
	
	public static final String ID                       = "DOEL_ID_DOC_FISCAL";
	public static final String TIPO_DOCUMENTO_FISCAL    = "DOEL_TP_DOC_FISCAL";
	public static final String ID_EMISSOR               = "DOEL_ID_EMISSOR";    
	public static final String ID_DESTINATARIO          = "DOEL_ID_DESTINATARIO";    
	public static final String NRO_DOC_FISCAL           = "DOEL_NRO_DOC";    
	public static final String SERIE_DOC_FISCAL         = "DOEL_SERIE_DOC";     
	public static final String ANO_DOC_FISCAL           = "DOEL_ANO";
	public static final String DT_EMISSAO               = "DOEL_DT_EMISSAO_DOC";          
	public static final String ID_ESTADO                = "DOEL_ID_ESTADO";       
	public static final String ID_MUNICIPIO             = "DOEL_ID_MUNICIPIO";
	public static final String VERSAO                   = "DOEL_VERSAO";
	public static final String CHAVE_ACESSO             = "DOEL_CHAVE_ACESSO";
	public static final String CHAVE_ACESSO_ENVIADA     = "DOEL_CHAVE_ACESSO_ENV";
	public static final String TIPO_EMISSAO             = "DOEL_TP_EMISSAO";
	public static final String USUARIO_REG				= "DOEL_USUARIO_REG";
	public static final String DATAHORA_REG				= "DOEL_DATAHORA_REG";
	public static final String USUARIO					= "DOEL_USUARIO";
	public static final String DATAHORA					= "DOEL_DATAHORA";
	
	
    @Override
    public DocumentoFiscalEldoc mapRow(ResultSet rs, int row) throws SQLException {
    	DocumentoFiscalEldoc result = new DocumentoFiscalEldoc();
        result.setId(rs.getLong(ID));
        result.setTipoDocumentoFiscal(rs.getString(TIPO_DOCUMENTO_FISCAL));
        result.setIdEmissor(rs.getLong(ID_EMISSOR));
        result.setIdDestinatario(rs.getLong(ID_DESTINATARIO));
        result.setNumeroDocumentoFiscal(rs.getLong(NRO_DOC_FISCAL));
        result.setSerieDocumentoFiscal(rs.getLong(SERIE_DOC_FISCAL));
        result.setAnoDocumentoFiscal(rs.getInt(ANO_DOC_FISCAL));
        result.setDataHoraEmissao(rs.getTimestamp(DT_EMISSAO));
        result.setIdEstado(rs.getLong(ID_ESTADO));
        result.setIdMunicipio(rs.getLong(ID_MUNICIPIO));
        result.setVersao(rs.getString(VERSAO));
        result.setChaveAcesso(rs.getString(CHAVE_ACESSO));
        result.setChaveAcessoEnviada(rs.getString(CHAVE_ACESSO_ENVIADA));
        result.setTipoEmissao(rs.getLong(TIPO_EMISSAO));
        result.setUsuarioReg(rs.getString(USUARIO_REG));
        result.setDataHoraReg(rs.getTimestamp(DATAHORA_REG));
        result.setUsuario(rs.getString(USUARIO));
        result.setDataHora(rs.getTimestamp(DATAHORA));
        return result;
    }

}