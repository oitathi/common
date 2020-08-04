package com.b2wdigital.fazemu.business.repository;

import java.util.List;

import com.b2wdigital.fazemu.domain.DocumentoLote;

/**
 * Documento Lote Repository.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
public interface DocumentoLoteRepository {

    DocumentoLote findByDocumentoFiscalAndLote(Long idDocumentoFiscal, Long idLote);

    int insert(DocumentoLote documentoLote);

    int delete(Long idDocFiscal);

    List<Long> listByIdDocFiscal(Long idLote);

    List<DocumentoLote> listByDataHoraInicioAndExistsLote(String excludeList);
}
