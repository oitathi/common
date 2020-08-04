package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.MunicipioAutorizador;

/**
 * Municipio Autorizador RowMapper.
 *
 * @author Marcelo Oliveira {marcelo.doliveira@b2wdigital.com}
 * @version 1.0
 */

@Component("municipioAutorizadorRowMapper")
public class MunicipioAutorizadorRowMapper extends AbstractRowMapper implements RowMapper<MunicipioAutorizador> {

    public static final String ID_MUNICIPIO             = "MAUT_ID_MUNICIPIO";
    public static final String TIPO_DOCUMENTO_FISCAL    = "MAUT_TP_DOC_FISCAL";
    public static final String TIPO_EMISSAO             = "MAUT_TP_EMISSAO";
    public static final String ID_AUTORIZADOR           = "MAUT_ID_AUTORIZADOR";
    public static final String SITUACAO                 = "MAUT_SITUACAO";
    public static final String USUARIO                  = "MAUT_USUARIO";
    public static final String DATAHORA                 = "MAUT_DATAHORA";

    @Override
    public MunicipioAutorizador mapRow(ResultSet rs, int row) throws SQLException {
        MunicipioAutorizador result = new MunicipioAutorizador();
        result.setIdMunicipio(rs.getLong(ID_MUNICIPIO));
        result.setTipoDocumentoFiscal(rs.getString(TIPO_DOCUMENTO_FISCAL));
        result.setTipoEmissao(rs.getLong(TIPO_EMISSAO));
        result.setIdAutorizador(rs.getLong(ID_AUTORIZADOR));
        result.setSituacao(rs.getString(SITUACAO));
        result.setUsuario(rs.getString(USUARIO));
        result.setDataHora(rs.getTimestamp(DATAHORA));
        return result;
    }
}
