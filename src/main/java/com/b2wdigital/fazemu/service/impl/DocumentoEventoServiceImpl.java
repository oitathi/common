package com.b2wdigital.fazemu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.b2wdigital.fazemu.business.repository.DocumentoEventoRepository;
import com.b2wdigital.fazemu.business.service.DocumentoEventoService;
import com.b2wdigital.fazemu.domain.DocumentoEvento;

/**
 * Documento Evento Service.
 *
 * @author Marcelo Oliveira {marcelo.doliveira@b2wdigital.com}
 * @version 1.0
 */
@Service
public class DocumentoEventoServiceImpl implements DocumentoEventoService {

    @Autowired
    private DocumentoEventoRepository documentoEventoRepository;

    @Override
    public List<DocumentoEvento> listByIdDocFiscal(Long idDocFiscal) {
        return documentoEventoRepository.listByIdDocFiscal(idDocFiscal);
    }

    @Override
    public DocumentoEvento findByIdDocFiscalAndSituacaoAutorizacao(Long idDocFiscal, String situacaoAutorizacao) {
        return documentoEventoRepository.findByIdDocFiscalAndSituacaoAutorizacao(idDocFiscal, situacaoAutorizacao);
    }

    @Override
    public DocumentoEvento findMaxEventoByIdDocFiscalAndTpServico(Long idDocFiscal, String tipoServico) {
        return documentoEventoRepository.findMaxEventoByIdDocFiscalAndTpServico(idDocFiscal, tipoServico);
    }

    @Override
    public void insert(DocumentoEvento documentoEvento) {
        documentoEventoRepository.insert(documentoEvento);
    }

}
