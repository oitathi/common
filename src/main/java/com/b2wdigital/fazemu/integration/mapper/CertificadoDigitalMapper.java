package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.b2wdigital.fazemu.domain.form.EmissorRaizCertificadoDigitalForm;
import com.b2wdigital.fazemu.enumeration.EmissorRaizCertificadoDigitalEnum;
import com.b2wdigital.fazemu.integration.dao.jdbc.DocumentoFiscalJdbcDao;
import com.b2wdigital.fazemu.utils.DateUtils;

public class CertificadoDigitalMapper implements RowMapper<EmissorRaizCertificadoDigitalForm> {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoFiscalJdbcDao.class);

	
	private final String ID = "EDIG_ID_CDIGITAL"; 
	private final String ID_EMISSOR_RAIZ = "EDIG_ID_EMISSOR_RAIZ"; 
	private final String NOME = "EMRA_NOME";
	private final String DATA_VIGENCIA_INICIO = "EDIG_DT_VIG_INI";
	private final String DATA_VIGENCIA_FIM = "EDIG_DT_VIG_FIM";
	
	private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
	
	@Override
	public EmissorRaizCertificadoDigitalForm mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		EmissorRaizCertificadoDigitalForm result = new EmissorRaizCertificadoDigitalForm();
		result.setId(String.valueOf(rs.getLong(ID)));
		result.setIdEmissorRaiz(String.valueOf(rs.getLong(ID_EMISSOR_RAIZ)));
		result.setNomeEmissorRaiz(rs.getString(NOME));
		result.setDataVigenciaInicio(DATE_FORMAT.format(rs.getTimestamp(DATA_VIGENCIA_INICIO)));
		result.setDataVigenciaFim(DATE_FORMAT.format(rs.getTimestamp(DATA_VIGENCIA_FIM)));
		result.setSituacao(checkSituacao(rs.getTimestamp(DATA_VIGENCIA_FIM)));
		return result;
	}
	
	private String checkSituacao(Date dataFim){

		try {
			DateTime dataFimVigencia = new DateTime(dataFim);
			DateTime dataHoje = new DateTime();
			
			int  dif = Days.daysBetween(dataFimVigencia, dataHoje).getDays();
	
	        if (dif > 0) {
	        	return EmissorRaizCertificadoDigitalEnum.EXPIRADO.getCodigo();
	        }
	        
	        if (dif <= 0 && dif >= -90) {
	        	return  EmissorRaizCertificadoDigitalEnum.A_EXPIRAR.getCodigo();
	         
	        }
	        return EmissorRaizCertificadoDigitalEnum.VALIDO.getCodigo();
	        
		}catch(Exception e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar getXmlAutorizadoByChaveAcesso", e);
			return "Falha ao calcular";
		}
          
    }
	
	
	

}
