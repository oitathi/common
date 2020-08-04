package com.b2wdigital.fazemu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.b2wdigital.fazemu.business.repository.DocumentoClobRepository;
import com.b2wdigital.fazemu.domain.DocumentoClob;
import com.b2wdigital.fazemu.enumeration.PontoDocumentoEnum;
import com.b2wdigital.fazemu.enumeration.TipoServicoEnum;
import com.b2wdigital.fazemu.business.service.DocumentoClobService;

/**
 * Documento Clob Service.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
@Service
public class DocumentoClobServiceImpl implements DocumentoClobService {

    @Autowired
    private DocumentoClobRepository documentoClobRepository;

    @Override
    public DocumentoClob findById(Long id) {
        return documentoClobRepository.findById(id);
    }

    @Override
    public Long insert(DocumentoClob clob) {
        return documentoClobRepository.insert(clob);
    }

    @Override
    public DocumentoClob getLastXmlSignedByIdDocFiscal(Long idDocFiscal) {
        return documentoClobRepository.getLastXmlSignedByIdDocFiscal(idDocFiscal);
    }

    @Override
    public DocumentoClob getLastXmlByIdDocFiscalAndPontoDocumento(Long idDocFiscal, PontoDocumentoEnum pontoDocumento) {
        return documentoClobRepository.getLastXmlByIdDocFiscalAndPontoDocumento(idDocFiscal, pontoDocumento);
    }

    @Override
    public String getXmlRetornoByIdDocFiscalAndTipoServico(Long idDocFiscal, TipoServicoEnum tipoServico) {
        return documentoClobRepository.getXmlRetornoByIdDocFiscalAndTipoServico(idDocFiscal, tipoServico);
    }

    @Override
    public String getMaxXmlEventoByIdDocFiscalAndTipoServico(Long idDocFiscal, TipoServicoEnum tipoServico) {
        return documentoClobRepository.getMaxXmlEventoByIdDocFiscalAndTipoServico(idDocFiscal, tipoServico);
    }

}
