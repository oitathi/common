package com.b2wdigital.fazemu.business.repository;

import java.util.List;
import java.util.Map;

import com.b2wdigital.fazemu.domain.EmissorRaiz;

/**
 * Emissor Raiz Repository.
 * 
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
public interface EmissorRaizRepository {

	EmissorRaiz findEmissorRaizById (String id);
	
	List<EmissorRaiz> listByFiltros(Map<String,String> filtros);
		
	void insert(EmissorRaiz emissorRaiz);
	
	int update(EmissorRaiz emissorRaiz);
	

}
