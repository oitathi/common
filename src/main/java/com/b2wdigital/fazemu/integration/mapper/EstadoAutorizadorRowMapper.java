package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.EstadoAutorizador;

/**
 * Estado Autorizador RowMapper.
 *
 * @author Marcelo Oliveira {marcelo.doliveira@b2wdigital.com}
 * @version 1.0
 */

@Component("estadoAutorizadorRowMapper")
public class EstadoAutorizadorRowMapper extends AbstractRowMapper implements RowMapper<EstadoAutorizador> {

    public static final String ID_ESTADO                = "ESAU_ID_ESTADO";
    public static final String TIPO_DOCUMENTO_FISCAL    = "ESAU_TP_DOC_FISCAL";
    public static final String TIPO_EMISSAO             = "ESAU_TP_EMISSAO";
    public static final String ID_AUTORIZADOR           = "ESAU_ID_AUTORIZADOR";
    public static final String SITUACAO                 = "ESAU_SITUACAO";
    public static final String USUARIO                  = "ESAU_USUARIO";
    public static final String DATAHORA                 = "ESAU_DATAHORA";

    @Override
    public EstadoAutorizador mapRow(ResultSet rs, int row) throws SQLException {
        EstadoAutorizador result = new EstadoAutorizador();
        result.setIdEstado(rs.getLong(ID_ESTADO));
        result.setTipoDocumentoFiscal(rs.getString(TIPO_DOCUMENTO_FISCAL));
        result.setTipoEmissao(rs.getLong(TIPO_EMISSAO));
        result.setIdAutorizador(rs.getLong(ID_AUTORIZADOR));
        result.setSituacao(rs.getString(SITUACAO));
        result.setUsuario(rs.getString(USUARIO));
        result.setDataHora(rs.getTimestamp(DATAHORA));
        return result;
    }
}
