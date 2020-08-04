package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.EmissorRaizFilial;

/**
 * Emissor Raiz Filial RowMapper.
 *
 * @author Marcelo Oliveira {marcelo.doliveira@b2wdigital.com}
 * @version 1.0
 */
@Component("emissorRaizFilialRowMapper")
public class EmissorRaizFilialRowMapper extends AbstractRowMapper implements RowMapper<EmissorRaizFilial> {

    protected static final String ID_EMISSOR_RAIZ       = "EFIL_ID_EMISSOR_RAIZ";
    protected static final String ID_FILIAL             = "EFIL_ID_FILIAL";
    protected static final String NOME                  = "EFIL_NOME";
    protected static final String IN_CONSULTA_DOC       = "EFIL_IN_CONSULTA_DOC";
    protected static final String ULTIMO_NSU            = "EFIL_ULT_NSU";
    protected static final String CHAVE_AUTENTICACAO    = "EFIL_CHAVE_AUTENTICACAO";
    protected static final String USUARIO               = "EFIL_USUARIO";
    protected static final String DATAHORA              = "EFIL_DATAHORA";

    @Override
    public EmissorRaizFilial mapRow(ResultSet rs, int row) throws SQLException {
        EmissorRaizFilial result = new EmissorRaizFilial();
        result.setIdEmissorRaiz(rs.getLong(ID_EMISSOR_RAIZ));
        result.setIdFilial(rs.getLong(ID_FILIAL));
        result.setNome(rs.getString(NOME));
        result.setInConsultaDocumento(rs.getString(IN_CONSULTA_DOC));
        result.setUltimoNSU(rs.getString(ULTIMO_NSU));
        result.setChaveAutenticacao(rs.getString(CHAVE_AUTENTICACAO));
        result.setUsuario(rs.getString(USUARIO));
        result.setDataHora(rs.getTimestamp(DATAHORA));
        return result;
    }

}
