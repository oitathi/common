package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.TipoEmissao;

/**
 * Tipo Emissao RowMapper.
 * 
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
@Component("tipoEmissaoRowMapper")
public class TipoEmissaoRowMapper extends AbstractRowMapper implements RowMapper<TipoEmissao> {
	
	public static final String ID                  = "TPEM_TP_EMISSAO";
	public static final String NOME                = "TPEM_NOME"; 
	public static final String INDICADOR_IMPRESSAO = "TPEM_IN_IMPRESSAO";
	public static final String SITUACAO            = "TPEM_SITUACAO";
	public static final String USUARIO             = "TPEM_USUARIO"; 
	public static final String DATAHORA            = "TPEM_DATAHORA";

    @Override
    public TipoEmissao mapRow(ResultSet rs, int row) throws SQLException {
    	TipoEmissao result = new TipoEmissao();
        result.setId(rs.getLong(ID));
        result.setNome(rs.getString(NOME));
        result.setIndicadorImpressao(rs.getString(INDICADOR_IMPRESSAO));
        result.setSituacao(rs.getString(SITUACAO));
        result.setUsuario(rs.getString(USUARIO));
        result.setDataHora(rs.getTimestamp(DATAHORA));

        return result;
    }

}
