package com.b2wdigital.fazemu.service.impl;

import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.b2wdigital.fazemu.business.repository.DocumentoRetornoRepository;
import com.b2wdigital.fazemu.business.service.DocumentoRetornoService;
import com.b2wdigital.fazemu.domain.DocumentoFiscal;
import com.b2wdigital.fazemu.domain.DocumentoRetorno;

/**
 * Documento Retorno Service.
 *
 * @author Marcelo Oliveira {marcelo.doliveira@b2wdigital.com}
 * @version 1.0
 */
@Service
public class DocumentoRetornoServiceImpl implements DocumentoRetornoService {

    @Autowired
    private DocumentoRetornoRepository documentoRetornoRepository;

    @Override
    public DocumentoRetorno findByIdDocFiscalAndTpServicoAndTpEvento(Long idDocFiscal, String tipoServico, Long tipoEvento) {
        return documentoRetornoRepository.findByIdDocFiscalAndTpServicoAndTpEvento(idDocFiscal, tipoServico, tipoEvento);
    }

    @Override
    public List<DocumentoRetorno> findByIdDocFiscal(Long idDocFiscal) {
        return documentoRetornoRepository.findByIdDocFiscal(idDocFiscal);
    }

    @Override
    public int insert(Long idDocFiscal, String tipoServico, Long tipoEvento, Long idXml, String usuarioReg, String usuario) {
        return documentoRetornoRepository.insert(idDocFiscal, tipoServico, tipoEvento, idXml, usuarioReg, usuario);
    }

    @Override
    public int update(Long idDocFiscal, String tipoServico, Long tipoEvento, Long idXml, String usuario) {
        return documentoRetornoRepository.update(idDocFiscal, tipoServico, tipoEvento, idXml, usuario);
    }

    @Override
    public List<DocumentoRetorno> listByDateIntervalAndNotExistsDocl(String tipoDocumentoFiscal, Date dataHoraInicio, Date dataHoraFim, String excludeList) {
        return documentoRetornoRepository.listByDateIntervalAndNotExistsDocl(tipoDocumentoFiscal, dataHoraInicio, dataHoraFim, excludeList);
    }

    @Override
    public List<DocumentoRetorno> listByDataHoraInicioAndNotExistsXML(Date dataHoraInicio, String excludeList) {
        return documentoRetornoRepository.listByDataHoraInicioAndNotExistsXML(dataHoraInicio, excludeList);
    }

    // Storage
    @Override
    public int updateURL(Long idDocFiscal, String tipoServico, Long tipoEvento, String url, String usuario) {
        return documentoRetornoRepository.updateURL(idDocFiscal, tipoServico, tipoEvento, url, usuario);
    }

}
