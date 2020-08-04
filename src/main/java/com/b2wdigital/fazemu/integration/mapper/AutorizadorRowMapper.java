package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.Autorizador;

/**
 * Autorizador RowMapper.
 * 
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
@Component("autorizadorRowMapper")
public class AutorizadorRowMapper extends AbstractRowMapper implements RowMapper<Autorizador> {
	
	protected static final String ID                    = "AUTR_ID_AUTORIZADOR";
	protected static final String CODIGO_EXTERNO        = "AUTR_COD_EXT";
	protected static final String TIPO_DOCUMENTO_FISCAL = "AUTR_TP_DOC_FISCAL";
	protected static final String NOME                  = "AUTR_NOME";
	protected static final String SITUACAO              = "AUTR_SITUACAO";
	protected static final String USUARIO               = "AUTR_USUARIO";
	protected static final String DATAHORA              = "AUTR_DATAHORA";

    @Override
    public Autorizador mapRow(ResultSet rs, int row) throws SQLException {
    	Autorizador result = new Autorizador();
        result.setId(rs.getLong(ID));
        result.setCodigoExterno(rs.getString(CODIGO_EXTERNO));
        result.setTipoDocumentoFiscal(rs.getString(TIPO_DOCUMENTO_FISCAL));
        result.setNome(rs.getString(NOME));
        result.setSituacao(rs.getString(SITUACAO));
        result.setUsuario(rs.getString(USUARIO));
        result.setDataHora(rs.getTimestamp(DATAHORA));

        return result;
    }

}
