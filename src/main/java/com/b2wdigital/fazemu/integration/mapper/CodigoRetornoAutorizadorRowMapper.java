package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.CodigoRetornoAutorizador;

/**
 *
 * @author dailton.almeida
 */
@Component
public class CodigoRetornoAutorizadorRowMapper extends AbstractRowMapper implements RowMapper<CodigoRetornoAutorizador> {
    public static final String ID                       = "CRAU_ID_COD";
    public static final String TIPO_DOCUMENTO_FISCAL    = "CRAU_TP_DOC";
    public static final String DESCRICAO                = "CRAU_DESCRICAO";
    public static final String SITUACAO_AUTORIZADOR     = "CRAU_SIT_AUTORIZ";
    public static final String USUARIO                  = "CRAU_USUARIO";
    public static final String DATAHORA                 = "CRAU_DATAHORA";

    @Override
    public CodigoRetornoAutorizador mapRow(ResultSet rs, int i) throws SQLException {
        CodigoRetornoAutorizador result = CodigoRetornoAutorizador.build(rs.getInt(ID));
        
        result.setTipoDocumentoFiscal(rs.getString(TIPO_DOCUMENTO_FISCAL));
        result.setDescricao(rs.getString(DESCRICAO));
        result.setSituacaoAutorizador(rs.getString(SITUACAO_AUTORIZADOR));
        result.setUsuario(rs.getString(USUARIO));
        result.setDataHora(rs.getTimestamp(DATAHORA));

        return result;
    }
    
}
