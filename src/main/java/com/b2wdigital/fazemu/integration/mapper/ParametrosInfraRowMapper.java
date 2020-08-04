package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.ParametrosInfra;

/**
 * Parametros Infra RowMapper.
 * 
 * @author Marcelo Oliveira {marcelo.doliveira@b2wdigital.com}
 * @version 1.0
 */
@Component("parametrosInfraRowMapper")
public class ParametrosInfraRowMapper extends AbstractRowMapper implements RowMapper<ParametrosInfra> {
	
    public static final String ID_PARAMETRO             = "PAIN_ID_PARAMETRO";
    public static final String TIPO_DOCUMENTO_FISCAL    = "PAIN_TP_DOC_FISCAL";
    public static final String VALOR                    = "PAIN_VALOR";
    public static final String DESCRICAO                = "PAIN_DESCRICAO";
    public static final String TIPO                     = "PAIN_TIPO";
    public static final String USUARIO                  = "PAIN_USUARIO";
    public static final String DATAHORA                 = "PAIN_DATAHORA";

    @Override
    public ParametrosInfra mapRow(ResultSet rs, int row) throws SQLException {
    	ParametrosInfra result = new ParametrosInfra();
        result.setIdParametro(rs.getString(ID_PARAMETRO));
        result.setTipoDocumentoFiscal(rs.getString(TIPO_DOCUMENTO_FISCAL));
        result.setValor(rs.getString(VALOR));
        result.setDescricao(rs.getString(DESCRICAO));
        result.setTipo(rs.getString(TIPO));
        result.setUsuario(rs.getString(USUARIO));
        result.setDataHora(rs.getTimestamp(DATAHORA));
        return result;
    }
}
