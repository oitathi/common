package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.EstadoTipoEmissao;

/**
 * Estado Tp Emissao RowMapper.
 * 
 * @author Marcelo Oliveira {marcelo.doliveira@b2wdigital.com}
 * @version 1.0
 */
@Component("estadoTipoEmissaoRowMapper")
public class EstadoTipoEmissaoRowMapper extends AbstractRowMapper implements RowMapper<EstadoTipoEmissao> {
	
    public static final String ID_ESTADO      = "ESTE_ID_ESTADO";
    public static final String DATA_INICIO    = "ESTE_DT_INI";
    public static final String DATA_FIM       = "ESTE_DT_FIM";
    public static final String TIPO_EMISSAO   = "ESTE_TP_EMISSAO";
    public static final String JUSTIFICATIVA  = "ESTE_JUSTIFICATIVA";
    public static final String USUARIO        = "ESTE_USUARIO";
    public static final String DATAHORA       = "ESTE_DATAHORA";

    @Override
    public EstadoTipoEmissao mapRow(ResultSet rs, int row) throws SQLException {
    	EstadoTipoEmissao result = new EstadoTipoEmissao();
        result.setIdEstado(rs.getLong(ID_ESTADO));
        result.setDataInicio(rs.getTimestamp(DATA_INICIO));
        result.setDataFim(rs.getTimestamp(DATA_FIM));
        result.setTipoEmissao(rs.getLong(TIPO_EMISSAO));
        result.setJustificativa(rs.getString(JUSTIFICATIVA));
        result.setUsuario(rs.getString(USUARIO));
        result.setDataHora(rs.getTimestamp(DATAHORA));
        return result;
    }
}
