package com.b2wdigital.fazemu.business.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.b2wdigital.fazemu.domain.DocumentoFiscal;

import com.b2wdigital.fazemu.enumeration.PontoDocumentoEnum;
import com.b2wdigital.fazemu.enumeration.SituacaoDocumentoEnum;
import com.b2wdigital.fazemu.enumeration.SituacaoEnum;

public interface DocumentoFiscalRepository {

    DocumentoFiscal findById(Long id);

    DocumentoFiscal findByChaveAcesso(String chaveAcesso);

    DocumentoFiscal findByDadosDocumentoFiscal(String tipoDocumentoFiscal, Long idEmissor, Long numeroDocumentoFiscal, Long serieDocumentoFiscal, Integer anoDocumentoFiscal, Long idEstado);

    Long insert(DocumentoFiscal documentoFiscal);

    String getXmlByIdLoteAndIdDocFiscal(Long idLote, Long idDocFiscal);

    int updatePontoAndSituacaoAndCstatAndTipoEmissaoAndSituacaoDocumento(Long idDocFiscal, String cStat, PontoDocumentoEnum pontoDocumento, SituacaoEnum situacao, Long tipoEmissao, String situacaoDocumento);

    int updatePontoAndChaveAcessoEnviadaAndTipoEmissaoAndSituacao(Long idDocFiscal, PontoDocumentoEnum pontoDocumento, String chaveAcessoEnviada, Long tipoEmissao, SituacaoEnum situacao);

    int updateNumeroDocumentoFiscalAndDataEmissaoAndChaveAcessoAndPontoAndSituacaoDocumentoAndSituacao(Long idDocFiscal, Long numeroDocumentoFiscal, Date dataEmissao, String chaveAcesso, PontoDocumentoEnum pontoDocumento, String situacaoDocumento, SituacaoEnum situacao);

    List<DocumentoFiscal> listByFiltros(String tipoDocumentoFiscal,
            Long idEmissor,
            Long idDestinatario,
            Long idEstado,
            Long idMunicipio,
            String chaveAcesso,
            Long numeroDocumentoFiscal,
            Long numeroInicialDocumentoFiscal,
            Long numeroFinalDocumentoFiscal,
            Long serieDocumentoFiscal,
            Long numeroDocumentoFiscalExterno,
            Integer tipoEmissao,
            String situacaoDocumento,
            String situacao,
            Date dataHoraRegistroInicio,
            Date dataHoraRegistroFim,
            Long quantidadeRegistros);

    List<DocumentoFiscal> listByFiltros(Map<String, String> parameters) throws Exception;

    List<DocumentoFiscal> listByDateIntervalAndIdEstadoAndSituacao(String tipoDocumentoFiscal, Date dataHoraInicio, Date dataHoraFim, Long idEstado, SituacaoEnum situacao, String excludeList);

    List<DocumentoFiscal> listByDateIntervalAndIdEstadoAndSituacaoAutorizacao(String tipoDocumentoFiscal, Date dataHoraInicio, Date dataHoraFim, Long idEstado, String excludeList);

    String getSituacaoDocumentoByIdDocFiscal(Long idDocFiscal);

    int updateCstat(Long idDocFiscal, String cStat);

    int updateDataHora(Long idDocFiscal, Date dataHora);

    String getXMLResumoNFeByChaveAcesso(String chaveAcesso);

    List<DocumentoFiscal> listByDateIntervalAndSituacaoAndNotExistsInev(String tipoDocumentoFiscal, Date dataHoraInicio, Date dataHoraFim, SituacaoEnum situacao, String excludeList);

    List<DocumentoFiscal> listByDateIntervalAndSituacaoAndNotExistsDoev(String tipoDocumentoFiscal, Date dataHoraInicio, Date dataHoraFim, SituacaoEnum situacao, String excludeList);

    //NFSe
    DocumentoFiscal findByIdEmissorAndNumeroDocumentoFiscalExterno(String tipoDocumentoFiscal, Long idEmissor, Long numeroDocumentoFiscalExterno);

    List<DocumentoFiscal> listByIdMunicipioAndSituacaoDocumentoAndSituacaoAutorizacaoAndDataInicio(String tipoDocumentoFiscal, Long idMunicipio, SituacaoDocumentoEnum situacaoDocumento, String situacaoAutorizacao, Date dataHoraInicio, String excludeList);

    List<DocumentoFiscal> listByIdMunicipioAndIdPontoAndDataInicio(String tipoDocumentoFiscal, Long idMunicipio, Date dataHoraInicio, String excludeList);

    List<DocumentoFiscal> listByDataInicioAndIdMunicipioAndSituacao(String tipoDocumentoFiscal, Date dataHoraInicio, Long idMunicipio, SituacaoEnum situacao, String excludeList);

}
