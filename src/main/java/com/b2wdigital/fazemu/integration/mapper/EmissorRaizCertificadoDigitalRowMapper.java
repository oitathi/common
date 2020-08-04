package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.EmissorRaizCertificadoDigital;

@Component("emissorRaizCertificadoDigitalRowMapper")
public class EmissorRaizCertificadoDigitalRowMapper extends AbstractRowMapper implements RowMapper<EmissorRaizCertificadoDigital> {
    
	public static final String ID                   = "EDIG_ID_CDIGITAL";   
	public static final String ID_EMISSOR_RAIZ      = "EDIG_ID_EMISSOR_RAIZ"; 
	public static final String DATA_VIGENCIA_INICIO = "EDIG_DT_VIG_INI";
	public static final String DATA_VIGENCIA_FIM    = "EDIG_DT_VIG_FIM";
	public static final String USUARIO              = "EDIG_USUARIO"; 
	public static final String DATAHORA             = "EDIG_DATAHORA";
	public static final String CERTIFICADO          = "EDIG_CDIGITAL";
	public static final String SENHA                = "EDIG_SENHA";

	@Override
	public EmissorRaizCertificadoDigital mapRow(ResultSet rs, int row) throws SQLException {
		EmissorRaizCertificadoDigital result = new EmissorRaizCertificadoDigital();
		result.setId(rs.getLong(ID));
		result.setIdEmissorRaiz(rs.getLong(ID_EMISSOR_RAIZ));
		result.setDataVigenciaInicio(rs.getTimestamp(DATA_VIGENCIA_INICIO));
		result.setDataVigenciaFim(rs.getTimestamp(DATA_VIGENCIA_FIM));
		result.setUsuario(rs.getString(USUARIO));
		result.setDataHora(rs.getTimestamp(DATAHORA));
		result.setCertificadoBytes(rs.getBytes(CERTIFICADO));
		result.setSenha(rs.getString(SENHA));
		return result;
	}
}
