package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.AutorizadorServico;

/**
 * Autorizador Servico RowMapper.
 * 
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
@Component("autorizadorServicoRowMapper")
public class AutorizadorServicoRowMapper extends AbstractRowMapper implements RowMapper<AutorizadorServico> {
	
	protected static final String ID_AUTORIZADOR    = "AUSE_ID_AUTORIZADOR";   
	protected static final String ID_SERVICO        = "AUSE_ID_SERVICO"; 
	protected static final String VERSAO            = "AUSE_VERSAO";
	protected static final String URL               = "AUSE_URL";
	protected static final String SITUACAO          = "AUSE_SITUACAO";
	protected static final String USUARIO           = "AUSE_USUARIO"; 
	protected static final String DATAHORA          = "AUSE_DATAHORA";

    @Override
    public AutorizadorServico mapRow(ResultSet rs, int row) throws SQLException {
    	AutorizadorServico result = new AutorizadorServico();
        result.setIdAutorizador(rs.getLong(ID_AUTORIZADOR));
        result.setIdServico(rs.getString(ID_SERVICO));
        result.setVersao(rs.getString(VERSAO));
        result.setUrl(rs.getString(URL));
        result.setSituacao(rs.getString(SITUACAO));
        result.setUsuario(rs.getString(USUARIO));
        result.setDataHora(rs.getTimestamp(DATAHORA));

        return result;
    }

}
