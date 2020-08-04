package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.DocumentoFiscal;

/**
 * Documento Fiscal RowMapper
 * 
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
@Component("documentoFiscalRowMapper")
public class DocumentoFiscalRowMapper extends AbstractRowMapper implements RowMapper<DocumentoFiscal> {
	
	public static final String ID                       = "DOCU_ID_DOC_FISCAL";
	public static final String TIPO_DOCUMENTO_FISCAL    = "DOCU_TP_DOC_FISCAL";
	public static final String ID_EMISSOR               = "DOCU_ID_EMISSOR";    
	public static final String ID_DESTINATARIO          = "DOCU_ID_DESTINATARIO";    
	public static final String NRO_DOC_FISCAL           = "DOCU_NRO_DOC";    
	public static final String SERIE_DOC_FISCAL         = "DOCU_SERIE_DOC";     
	public static final String ANO_DOC_FISCAL           = "DOCU_ANO";
	public static final String NRO_DOC_FISCAL_EXT       = "DOCU_NRO_DOC_EXT";    
	public static final String DT_EMISSAO               = "DOCU_DT_EMISSAO_DOC";          
	public static final String ID_ESTADO                = "DOCU_ID_ESTADO";       
	public static final String ID_MUNICIPIO             = "DOCU_ID_MUNICIPIO";
	public static final String VERSAO                   = "DOCU_VERSAO";
	public static final String CHAVE_ACESSO             = "DOCU_CHAVE_ACESSO";
	public static final String CHAVE_ACESSO_ENVIADA     = "DOCU_CHAVE_ACESSO_ENV";
	public static final String TIPO_EMISSAO             = "DOCU_TP_EMISSAO";
	public static final String ID_PONTO                 = "DOCU_ID_PONTO";
	public static final String SITUACAO_AUTORIZADOR     = "DOCU_SITUACAO_AUTOR";
	public static final String SITUACAO_DOC             = "DOCU_SITUACAO_DOC";
	public static final String SITUACAO                 = "DOCU_SITUACAO";
	public static final String ID_SISTEMA               = "DOCU_ID_SISTEMA";
	public static final String USUARIO_REG              = "DOCU_USUARIO_REG";
	public static final String DATAHORA_REG             = "DOCU_DATAHORA_REG";    
	public static final String USUARIO                  = "DOCU_USUARIO"; 
	public static final String DATAHORA                 = "DOCU_DATAHORA";
	
    @Override
    public DocumentoFiscal mapRow(ResultSet rs, int row) throws SQLException {
    	DocumentoFiscal result = new DocumentoFiscal();
        result.setId(rs.getLong(ID));
        result.setTipoDocumentoFiscal(rs.getString(TIPO_DOCUMENTO_FISCAL));
        result.setIdEmissor(rs.getLong(ID_EMISSOR));
        result.setIdDestinatario(rs.getLong(ID_DESTINATARIO));
        result.setNumeroDocumentoFiscal(rs.getLong(NRO_DOC_FISCAL));
        result.setSerieDocumentoFiscal(rs.getLong(SERIE_DOC_FISCAL));
        result.setAnoDocumentoFiscal(rs.getInt(ANO_DOC_FISCAL));
        result.setNumeroDocumentoFiscalExterno(rs.getLong(NRO_DOC_FISCAL_EXT));
        result.setDataHoraEmissao(rs.getTimestamp(DT_EMISSAO));
        result.setIdEstado(rs.getLong(ID_ESTADO));
        result.setIdMunicipio(rs.getLong(ID_MUNICIPIO));
        result.setVersao(rs.getString(VERSAO));
        result.setChaveAcesso(rs.getString(CHAVE_ACESSO));
        result.setChaveAcessoEnviada(rs.getString(CHAVE_ACESSO_ENVIADA));
        result.setTipoEmissao(rs.getLong(TIPO_EMISSAO));
        result.setIdPonto(rs.getString(ID_PONTO));
        result.setSituacaoAutorizador(rs.getString(SITUACAO_AUTORIZADOR));
        result.setSituacaoDocumento(rs.getString(SITUACAO_DOC));
        result.setSituacao(rs.getString(SITUACAO));
        result.setIdSistema(rs.getString(ID_SISTEMA));
        result.setUsuarioReg(rs.getString(USUARIO_REG));
        result.setDataHoraReg(rs.getTimestamp(DATAHORA_REG));
        result.setUsuario(rs.getString(USUARIO));
        result.setDataHora(rs.getTimestamp(DATAHORA));
        return result;
    }

}