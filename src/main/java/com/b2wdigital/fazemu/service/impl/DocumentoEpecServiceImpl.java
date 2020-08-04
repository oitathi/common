package com.b2wdigital.fazemu.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.b2wdigital.fazemu.business.repository.DocumentoEpecRepository;
import com.b2wdigital.fazemu.business.service.DocumentoEpecService;
import com.b2wdigital.fazemu.domain.DocumentoEpec;
import com.b2wdigital.fazemu.domain.form.DocumentoEpecForm;
import com.b2wdigital.fazemu.enumeration.SituacaoEnum;

/**
 * Documento EPEC Service.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
@Service
public class DocumentoEpecServiceImpl implements DocumentoEpecService {

    @Autowired
    private DocumentoEpecRepository documentoEpecRepository;

    @Autowired
    private MessageSource ms;

    private final Locale locale = LocaleContextHolder.getLocale();

    @Override
    public List<DocumentoEpec> listBySituacaoAndIdEstadoAndDataInicio(String tipoDocumentoFiscal, SituacaoEnum situacao, Long idEstado, Date dataHoraInicio, String excludeList) {
        return documentoEpecRepository.listBySituacaoAndIdEstadoAndDataInicio(tipoDocumentoFiscal, situacao, idEstado, dataHoraInicio, excludeList);
    }

    @Override
    public List<DocumentoEpecForm> listByFiltros(Map<String, String> parameters) throws Exception {
        List<DocumentoEpec> listaDocumentoEpec = documentoEpecRepository.listByFiltros(parameters);
        if (listaDocumentoEpec == null || listaDocumentoEpec.isEmpty()) {
            throw new Exception(ms.getMessage("documentoEpec.not.found", null, locale));
        }

        List<DocumentoEpecForm> listaDocumentoEpecForm = new ArrayList<>();
        listaDocumentoEpec.forEach(de -> listaDocumentoEpecForm.add(new DocumentoEpecForm(de)));
        return listaDocumentoEpecForm;
    }

    @Override
    public void insert(DocumentoEpec documentoEpec) {
        documentoEpecRepository.insert(documentoEpec);
    }

    @Override
    public int updateSituacao(Long idDocumentoFiscal, SituacaoEnum situacao) {
        return documentoEpecRepository.updateSituacao(idDocumentoFiscal, situacao);
    }

    @Override
    public DocumentoEpec findByIdDocFiscal(Long idDocFiscal) {
        return documentoEpecRepository.findByIdDocFiscal(idDocFiscal);
    }

    @Override
    public DocumentoEpec findByIdDocFiscalAndSituacao(Long idDocFiscal, SituacaoEnum situacao) {
        return documentoEpecRepository.findByIdDocFiscalAndSituacao(idDocFiscal, situacao);
    }

}
