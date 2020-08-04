package com.b2wdigital.fazemu.business.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.b2wdigital.fazemu.domain.DocumentoEpec;
import com.b2wdigital.fazemu.enumeration.SituacaoEnum;

/**
 * Documento Epec Repository.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
public interface DocumentoEpecRepository {

    List<DocumentoEpec> listBySituacaoAndIdEstadoAndDataInicio(String tipoDocumentoFiscal, SituacaoEnum situacao, Long idEstado, Date dataHoraInicio, String excludeList);

    List<DocumentoEpec> listByFiltros(Map<String, String> parameters) throws Exception;

    DocumentoEpec findByIdDocFiscal(Long idDocFiscal);

    int insert(DocumentoEpec documentoEpec);

    int updateSituacao(Long idDocumentoFiscal, SituacaoEnum situacao);

    DocumentoEpec findByIdDocFiscalAndSituacao(Long idDocFiscal, SituacaoEnum situacao);

}
