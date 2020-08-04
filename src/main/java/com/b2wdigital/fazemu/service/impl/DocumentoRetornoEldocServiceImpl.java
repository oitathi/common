package com.b2wdigital.fazemu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.b2wdigital.fazemu.business.repository.DocumentoRetornoEldocRepository;
import com.b2wdigital.fazemu.business.service.DocumentoRetornoEldocService;
import com.b2wdigital.fazemu.domain.DocumentoRetornoEldoc;

/**
 * Documento Retorno Service.
 *
 * @author Marcelo Oliveira {marcelo.doliveira@b2wdigital.com}
 * @version 1.0
 */
@Service
public class DocumentoRetornoEldocServiceImpl implements DocumentoRetornoEldocService {

	@Autowired
	private DocumentoRetornoEldocRepository documentoRetornoEldocRepository;
	
	@Override
	public List<DocumentoRetornoEldoc> findByIdDocFiscal(Long idDocFiscal) {
		return documentoRetornoEldocRepository.findByIdDocFiscal(idDocFiscal);
	}

    

}
