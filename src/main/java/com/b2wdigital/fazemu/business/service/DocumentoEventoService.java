package com.b2wdigital.fazemu.business.service;

import java.util.List;

import com.b2wdigital.fazemu.domain.DocumentoEvento;

public interface DocumentoEventoService {

    List<DocumentoEvento> listByIdDocFiscal(Long idDocFiscal);

    DocumentoEvento findByIdDocFiscalAndSituacaoAutorizacao(Long idDocFiscal, String situacaoAutorizacao);

    DocumentoEvento findMaxEventoByIdDocFiscalAndTpServico(Long idDocFiscal, String tipoServico);

    void insert(DocumentoEvento documentoEvento);
}
