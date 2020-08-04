package com.b2wdigital.fazemu.business.repository;

import java.util.List;

import com.b2wdigital.fazemu.domain.DocumentoEvento;

/**
 * Documento Evento Repository.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
public interface DocumentoEventoRepository {

    DocumentoEvento findByIdEvento(Long idEvento);

    List<DocumentoEvento> listByIdDocFiscal(Long idDocFiscal);

    DocumentoEvento findByIdDocFiscalAndSituacaoAutorizacao(Long idDocFiscal, String situacaoAutorizacao);

    DocumentoEvento findMaxEventoAprovadoByIdDocFiscal(Long idDocFiscal);

    DocumentoEvento findMaxEventoByIdDocFiscalAndTpServico(Long idDocFiscal, String tipoServico);

    void insert(DocumentoEvento documentoEvento);

}
