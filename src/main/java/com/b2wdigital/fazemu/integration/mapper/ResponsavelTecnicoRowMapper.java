package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.ResponsavelTecnico;

/**
 * Responsavel Tecnico RowMapper.
 * 
 * @author Marcelo Oliveira {marcelo.doliveira@b2wdigital.com}
 * @version 1.0
 */
@Component("responsavelTecnicoRowMapper")
public class ResponsavelTecnicoRowMapper extends AbstractRowMapper implements RowMapper<ResponsavelTecnico> {
	
    public static final String ID               = "RTEC_ID_RESP_TECNICO";
    public static final String ID_EMISSOR_RAIZ  = "RTEC_ID_EMISSOR_RAIZ";
    public static final String CNPJ             = "RTEC_CNPJ";
    public static final String CONTATO          = "RTEC_CONTATO";
    public static final String EMAIL            = "RTEC_EMAIL";
    public static final String TELEFONE         = "RTEC_TEL";
    public static final String SITUACAO         = "RTEC_SITUACAO";
    public static final String USUARIO          = "RTEC_USUARIO";
    public static final String DATAHORA         = "RTEC_DATAHORA";

    @Override
    public ResponsavelTecnico mapRow(ResultSet rs, int row) throws SQLException {
    	ResponsavelTecnico result = new ResponsavelTecnico();
        result.setIdResponsavelTecnico(rs.getLong(ID));
        result.setIdEmissorRaiz(rs.getLong(ID_EMISSOR_RAIZ));
        result.setCnpj(rs.getLong(CNPJ));
        result.setContato(rs.getString(CONTATO));
        result.setEmail(rs.getString(EMAIL));
        result.setTelefone(rs.getLong(TELEFONE));
        result.setSituacao(rs.getString(SITUACAO));
        result.setUsuario(rs.getString(USUARIO));
        result.setDataHora(rs.getTimestamp(DATAHORA));
        return result;
    }
    
}
