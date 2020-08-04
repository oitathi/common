package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.MunicipioConfiguracao;

/**
 * Municipio Configuracao RowMapper.
 *
 * @author Marcelo Oliveira {marcelo.doliveira@b2wdigital.com}
 * @version 1.0
 */

@Component("municipioConfiguracaoRowMapper")
public class MunicipioConfiguracaoRowMapper extends AbstractRowMapper implements RowMapper<MunicipioConfiguracao> {

    public static final String ID_MUNICIPIO             = "MUCO_ID_MUNICIPIO";
    public static final String TIPO_DOCUMENTO_FISCAL    = "MUCO_TP_DOC_FISCAL";
    public static final String IN_ATIVO                 = "MUCO_IN_ATIVO";
    public static final String IN_LOTE                  = "MUCO_IN_LOTE";
    public static final String USUARIO                  = "MUCO_USUARIO";
    public static final String DATAHORA                 = "MUCO_DATAHORA";

    @Override
    public MunicipioConfiguracao mapRow(ResultSet rs, int row) throws SQLException {
        MunicipioConfiguracao result = new MunicipioConfiguracao();
        result.setIdMunicipio(rs.getLong(ID_MUNICIPIO));
        result.setTipoDocumentoFiscal(rs.getString(TIPO_DOCUMENTO_FISCAL));
        result.setInAtivo(rs.getString(IN_ATIVO));
        result.setInLote(rs.getString(IN_LOTE));
        result.setUsuario(rs.getString(USUARIO));
        result.setDataHora(rs.getTimestamp(DATAHORA));
        return result;
    }
}
