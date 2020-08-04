package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.SistemaWs;

/**
 * SistemaWs RowMapper.
 * 
 * @author Marcelo Oliveira {marcelo.doliveira@b2wdigital.com}
 * @version 1.0
 */

@Component("sistemaWsRowMapper")
public class SistemaWsRowMapper extends AbstractRowMapper implements RowMapper<SistemaWs> {
	
    public static final String ID_SISTEMA               = "SIWS_ID_SISTEMA";
    public static final String TIPO_DOCUMENTO_FISCAL    = "SIWS_TP_DOC_FISCAL";
    public static final String ID_METODO                = "SIWS_ID_METODO";
    public static final String TIPO_SERVICO             = "SIWS_TP_SERVICO";
    public static final String SITUACAO                 = "SIWS_SITUACAO";
    public static final String USUARIO                  = "SIWS_USUARIO";
    public static final String DATAHORA                 = "SIWS_DATAHORA";

    @Override
    public SistemaWs mapRow(ResultSet rs, int row) throws SQLException {
    	SistemaWs result = new SistemaWs();
        result.setIdSistema(rs.getString(ID_SISTEMA));
        result.setTipoDocumentoFiscal(rs.getString(TIPO_DOCUMENTO_FISCAL));
        result.setIdMetodo(rs.getLong(ID_METODO));
        result.setTipoServico(rs.getString(TIPO_SERVICO));
        result.setSituacao(rs.getString(SITUACAO));
        result.setUsuario(rs.getString(USUARIO));
        result.setDataHora(rs.getTimestamp(DATAHORA));
        return result;
    }
}
