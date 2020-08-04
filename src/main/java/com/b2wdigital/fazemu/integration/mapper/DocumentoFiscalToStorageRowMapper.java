package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.DocumentoFiscalToStorage;

/**
 * Documento Fiscal To Storage RowMapper
 * 
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
@Component("documentoFiscalToStorageRowMapper")
public class DocumentoFiscalToStorageRowMapper extends AbstractRowMapper implements RowMapper<DocumentoFiscalToStorage> {
	
	protected static final String ID_DOC_FISCAL        = "DOCU_ID_DOC_FISCAL";
	protected static final String ID_EMISSOR           = "DOCU_ID_EMISSOR";
	protected static final String ID_DESTINATARIO      = "DOCU_ID_DESTINATARIO";    
	protected static final String NRO_DOC_FISCAL       = "DOCU_NRO_DOC";    
	protected static final String SERIE_DOC_FISCAL     = "DOCU_SERIE_DOC";     
	protected static final String ANO_DOC_FISCAL       = "DOCU_ANO";	
	protected static final String DT_EMISSAO           = "DOCU_DT_EMISSAO_DOC";          
	protected static final String ID_ESTADO            = "DOCU_ID_ESTADO";     
	protected static final String VERSAO               = "DOCU_VERSAO";	
	protected static final String CHAVE_ACESSO         = "DOCU_CHAVE_ACESSO";
	protected static final String CHAVE_ACESSO_ENVIADA = "DOCU_CHAVE_ACESSO_ENV";
	protected static final String TIPO_EMISSAO         = "DOCU_TP_EMISSAO";
	protected static final String DOCU_DATAHORA_REG    = "DOCU_DATAHORA_REG";
	protected static final String TIPO_SERVICO         = "DRET_TP_SERVICO";
	protected static final String DRET_DATAHORA_REG    = "DRET_DATAHORA_REG";
	protected static final String CLOB                 = "DOCL_CLOB";

    @Override
    public DocumentoFiscalToStorage mapRow(ResultSet rs, int row) throws SQLException {
    	DocumentoFiscalToStorage result = new DocumentoFiscalToStorage();
        result.setIdDocumentoFiscal(rs.getLong(ID_DOC_FISCAL));
        result.setIdEmissor(rs.getLong(ID_EMISSOR));
        result.setIdDestinatario(rs.getLong(ID_DESTINATARIO));
        result.setNumeroDocumentoFiscal(rs.getLong(NRO_DOC_FISCAL));
        result.setSerieDocumentoFiscal(rs.getLong(SERIE_DOC_FISCAL));
        result.setAnoDocumentoFiscal(rs.getInt(ANO_DOC_FISCAL));
        result.setDataHoraEmissao(rs.getTimestamp(DT_EMISSAO));
        result.setIdEstado(rs.getLong(ID_ESTADO));
        result.setVersao(rs.getString(VERSAO));
        result.setChaveAcesso(rs.getString(CHAVE_ACESSO));
        result.setChaveAcessoEnviada(rs.getString(CHAVE_ACESSO_ENVIADA));
        result.setTipoEmissao(rs.getInt(TIPO_EMISSAO));
        result.setDocumentoFiscalDataHoraRegistro(rs.getTimestamp(DOCU_DATAHORA_REG));
        result.setTipoServico(rs.getString(TIPO_SERVICO));
        result.setDocumentoRetornoDataHoraRegistro(rs.getTimestamp(DRET_DATAHORA_REG));
        result.setClob(rs.getString(CLOB));
        return result;
    }

}