package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.DocumentoRetornoEldoc;

@Component("documentoRetornoEldocRowMapper")
public class DocumentoRetornoEldocRowMapper extends AbstractRowMapper implements RowMapper<DocumentoRetornoEldoc> {
    
    protected static final String ID_DOC_FISCAL = "DREL_ID_DOC_FISCAL";
    protected static final String TIPO_SERVICO  = "DREL_TP_SERVICO";
    protected static final String TIPO_EVENTO	= "DREL_TP_EVENTO";
    protected static final String XML 	 	    = "DREL_XML";
    protected static final String URL      		= "DREL_URL";
    protected static final String USUARIO_REG   = "DREL_USUARIO_REG";
    protected static final String DATAHORA_REG  = "DREL_DATAHORA_REG";    
    protected static final String USUARIO       = "DREL_USUARIO"; 
    protected static final String DATAHORA      = "DREL_DATAHORA";
    protected static final String NUM_SEQ_NF    = "DREL_NRO_SEQ_NF";

    @Override
    public DocumentoRetornoEldoc mapRow(ResultSet rs, int row) throws SQLException {
        DocumentoRetornoEldoc result = new DocumentoRetornoEldoc();
        result.setIdDocumentoFiscal(rs.getLong(ID_DOC_FISCAL));
        result.setTipoServico(rs.getString(TIPO_SERVICO));
        result.setTipoEvento(rs.getString(TIPO_EVENTO));
        result.setXml(rs.getString(XML));
        result.setUrl(rs.getString(URL));
        result.setUsuarioReg(rs.getString(USUARIO_REG));
        result.setDataHoraReg(rs.getTimestamp(DATAHORA_REG));
        result.setUsuario(rs.getString(USUARIO));
        result.setDataHora(rs.getTimestamp(DATAHORA));
        result.setNumeroSequencialArquivoNFe(rs.getLong(NUM_SEQ_NF));
        return result;
    }

}
