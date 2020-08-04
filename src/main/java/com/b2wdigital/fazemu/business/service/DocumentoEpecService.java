package com.b2wdigital.fazemu.business.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.b2wdigital.fazemu.domain.DocumentoEpec;
import com.b2wdigital.fazemu.domain.form.DocumentoEpecForm;
import com.b2wdigital.fazemu.enumeration.SituacaoEnum;

public interface DocumentoEpecService {

    List<DocumentoEpec> listBySituacaoAndIdEstadoAndDataInicio(String tipoDocumentoFiscal, SituacaoEnum situacao, Long idEstado, Date dataHoraInicio, String excludeList);

    List<DocumentoEpecForm> listByFiltros(Map<String, String> parameters) throws Exception;

    void insert(DocumentoEpec documentoEpec);

    int updateSituacao(Long idDocumentoFiscal, SituacaoEnum situacao);

    DocumentoEpec findByIdDocFiscal(Long idDocFiscal);

    DocumentoEpec findByIdDocFiscalAndSituacao(Long idDocFiscal, SituacaoEnum situacao);
}
