package com.b2wdigital.fazemu.business.service;

import java.util.List;

import com.b2wdigital.fazemu.domain.DocumentoRetornoEldoc;

public interface DocumentoRetornoEldocService {

    List<DocumentoRetornoEldoc> findByIdDocFiscal(Long idDocFiscal);

   

}
