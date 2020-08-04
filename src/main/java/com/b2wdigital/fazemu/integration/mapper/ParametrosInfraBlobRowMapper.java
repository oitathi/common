package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.ParametrosInfraBlob;

/**
 * Parametros Infra Blob RowMapper.
 * 
 * @author Marcelo Oliveira {marcelo.doliveira@b2wdigital.com}
 * @version 1.0
 */

@Component("parametrosInfraBlobRowMapper")
public class ParametrosInfraBlobRowMapper extends AbstractRowMapper implements RowMapper<ParametrosInfraBlob> {
	
    public static final String ID_PARAMETRO             = "PAIB_ID_PARAMETRO";
    public static final String TIPO_DOCUMENTO_FISCAL    = "PAIB_TP_DOC_FISCAL";
    public static final String VALOR                    = "PAIB_VALOR";
    public static final String DESCRICAO                = "PAIB_DESCRICAO";
    public static final String USUARIO                  = "PAIB_USUARIO";
    public static final String DATAHORA                 = "PAIB_DATAHORA";

    @Override
    public ParametrosInfraBlob mapRow(ResultSet rs, int row) throws SQLException {
    	ParametrosInfraBlob result = new ParametrosInfraBlob();
        result.setIdParametro(rs.getString(ID_PARAMETRO));
        result.setTipoDocumentoFiscal(rs.getString(TIPO_DOCUMENTO_FISCAL));
        result.setValor(rs.getBytes(VALOR));
        result.setDescricao(rs.getString(DESCRICAO));
        result.setUsuario(rs.getString(USUARIO));
        result.setDataHora(rs.getTimestamp(DATAHORA));
        return result;
    }
}
