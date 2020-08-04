package com.b2wdigital.fazemu.business.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.b2wdigital.fazemu.domain.EmissorRaizCertificadoDigital;
import com.b2wdigital.fazemu.domain.form.EmissorRaizCertificadoDigitalForm;

/**
 * Emissor Raiz Certificado Digital Repository.
 * 
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
public interface EmissorRaizCertificadoDigitalRepository {
	
	List<EmissorRaizCertificadoDigitalForm> listByFiltros(Map<String,String> filtros);
	
	EmissorRaizCertificadoDigital findByIdEmissorRaiz(Long idEmissorRaiz);
        
    List<EmissorRaizCertificadoDigitalForm> listByDataFimVigencia();
	
	void insert(EmissorRaizCertificadoDigital emissorRaizCertificadoDigital);
	
	int update(Long id, Date dataVigenciaFim, String usuario);
}
