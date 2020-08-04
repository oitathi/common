package com.b2wdigital.fazemu.business.repository;

import java.util.List;

import com.b2wdigital.fazemu.domain.DocumentoRetornoEldoc;

public interface DocumentoRetornoEldocRepository {

	List<DocumentoRetornoEldoc> findByIdDocFiscal(Long idDocFiscal);

	int insert(DocumentoRetornoEldoc dretEldoc);

}
