package com.b2wdigital.fazemu.business.repository;

import java.util.List;
import java.util.Map;

import com.b2wdigital.fazemu.domain.DocumentoFiscalEldoc;

public interface DocumentoFiscalEldocRepository {

    DocumentoFiscalEldoc findById(Long id);
    
    Long insert(DocumentoFiscalEldoc documentoFiscalEldoc);
    
    List<DocumentoFiscalEldoc> listByFiltros (Map<String, String> parameters)throws Exception; 
    
    DocumentoFiscalEldoc findByChaveAcesso(String chaveAcesso);

}
