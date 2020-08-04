package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.DocumentoRetorno;

@Component("documentoRetornoRowMapper")
public class DocumentoRetornoRowMapper extends AbstractRowMapper implements RowMapper<DocumentoRetorno> {
    
    protected static final String ID_DOC_FISCAL = "DRET_ID_DOC_FISCAL";
    protected static final String TIPO_SERVICO  = "DRET_TP_SERVICO";
    protected static final String TIPO_EVENTO   = "DRET_TP_EVENTO";
    protected static final String ID_XML        = "DRET_ID_XML";
    protected static final String URL           = "DRET_URL";
    protected static final String USUARIO_REG   = "DRET_USUARIO_REG";
    protected static final String DATAHORA_REG  = "DRET_DATAHORA_REG";    
    protected static final String USUARIO       = "DRET_USUARIO"; 
    protected static final String DATAHORA      = "DRET_DATAHORA";

    @Override
    public DocumentoRetorno mapRow(ResultSet rs, int row) throws SQLException {
        DocumentoRetorno documentoRetorno = new DocumentoRetorno();
        documentoRetorno.setIdDocumentoFiscal(rs.getLong(ID_DOC_FISCAL));
        documentoRetorno.setTipoServico(rs.getString(TIPO_SERVICO));
        documentoRetorno.setTipoEvento(rs.getLong(TIPO_EVENTO));
        documentoRetorno.setIdXml(rs.getLong(ID_XML));
        documentoRetorno.setUrl(rs.getString(URL));
        documentoRetorno.setUsuarioReg(rs.getString(USUARIO_REG));
        documentoRetorno.setDataHoraReg(rs.getTimestamp(DATAHORA_REG));
        documentoRetorno.setUsuario(rs.getString(USUARIO));
        documentoRetorno.setDataHora(rs.getTimestamp(DATAHORA));
        return documentoRetorno;
    }

}
