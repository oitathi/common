package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.InterfaceEvento;

/**
 * InterfaceEvento RowMapper.
 * 
 * @author Marcelo Oliveira {marcelo.doliveira@b2wdigital.com}
 * @version 1.0
 */

@Component("interfaceEventoRowMapper")
public class InterfaceEventoRowMapper extends AbstractRowMapper implements RowMapper<InterfaceEvento> {
	
    public static final String ID_EVENTO            = "INEV_ID_EVENTO";
    public static final String ID_SISTEMA           = "INEV_ID_SISTEMA";
    public static final String ID_METODO            = "INEV_ID_METODO";
    public static final String ID_DOC_FISCAL        = "INEV_ID_DOC_FISCAL";
    public static final String TIPO_SERVICO         = "INEV_TP_SERVICO";
    public static final String OBSERVACAO           = "INEV_OBS";
    public static final String SITUACAO             = "INEV_SITUACAO";
    public static final String USUARIO_REGISTRO     = "INEV_USUARIO_REG";
    public static final String DATAHORA_REGISTRO    = "INEV_DATAHORA_REG";
    public static final String USUARIO              = "INEV_USUARIO";
    public static final String DATAHORA             = "INEV_DATAHORA";
    public static final String CHAVE_ACESSO_ENVIADA = "DOCU_CHAVE_ACESSO_ENV";

    @Override
    public InterfaceEvento mapRow(ResultSet rs, int row) throws SQLException {
    	InterfaceEvento result = new InterfaceEvento();
        result.setIdEvento(rs.getLong(ID_EVENTO));
        result.setIdSistema(rs.getString(ID_SISTEMA));
        result.setIdMetodo(rs.getLong(ID_METODO));
        result.setTipoServico(rs.getString(TIPO_SERVICO));
        result.setIdDocFiscal(rs.getLong(ID_DOC_FISCAL));
        result.setObservacao(rs.getString(OBSERVACAO));
        result.setSituacao(rs.getString(SITUACAO));
        result.setUsuarioRegistro(rs.getString(USUARIO_REGISTRO));
        result.setDataHoraRegistro(rs.getTimestamp(DATAHORA_REGISTRO));
        result.setUsuario(rs.getString(USUARIO));
        result.setDataHora(rs.getTimestamp(DATAHORA));
        result.setChaveAcessoEnviada(rs.getString(CHAVE_ACESSO_ENVIADA));
        return result;
    }
}
