package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.EstadoConfiguracao;

/**
 * EstadoConfiguracao RowMapper.
 *
 * @author Marcelo Oliveira {marcelo.doliveira@b2wdigital.com}
 * @version 1.0
 */
@Component("estadoConfiguracaoRowMapper")
public class EstadoConfiguracaoRowMapper extends AbstractRowMapper implements RowMapper<EstadoConfiguracao> {

    public static final String ID_ESTADO                = "ESCO_ID_ESTADO";
    public static final String TIPO_DOCUMENTO_FISCAL    = "ESCO_TP_DOC_FISCAL";
    public static final String IN_ATIVO                 = "ESCO_IN_ATIVO";
    public static final String IN_RESP_TECNICO          = "ESCO_IN_RESP_TECNICO";
    public static final String IN_CSRT                  = "ESCO_IN_CSRT";
    public static final String IN_EPEC_AUTOMATICO       = "ESCO_IN_EPEC_AUTOMATICO";
    public static final String QTDE_MIN_REG             = "ESCO_QTDE_MIN_REG";
    public static final String PERIODO                  = "ESCO_PERIODO";
    public static final String PERIODO_EPEC             = "ESCO_PERIODO_EPEC";
    public static final String USUARIO                  = "ESCO_USUARIO";
    public static final String DATAHORA                 = "ESCO_DATAHORA";

    @Override
    public EstadoConfiguracao mapRow(ResultSet rs, int row) throws SQLException {
        EstadoConfiguracao result = new EstadoConfiguracao();
        result.setIdEstado(rs.getLong(ID_ESTADO));
        result.setTipoDocumentoFiscal(rs.getString(TIPO_DOCUMENTO_FISCAL));
        result.setInAtivo(rs.getString(IN_ATIVO));
        result.setInResponsavelTecnico(rs.getString(IN_RESP_TECNICO));
        result.setInCSRT(rs.getString(IN_CSRT));
        result.setInEPECAutomatico(rs.getString(IN_EPEC_AUTOMATICO));
        result.setQuantidadeMinimaRegistros(rs.getLong(QTDE_MIN_REG));
        result.setPeriodo(rs.getLong(PERIODO));
        result.setPeriodoEPEC(rs.getLong(PERIODO_EPEC));
        result.setUsuario(rs.getString(USUARIO));
        result.setDataHora(rs.getTimestamp(DATAHORA));
        return result;
    }
}
