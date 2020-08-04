package com.b2wdigital.fazemu.business.repository;

import com.b2wdigital.fazemu.domain.DocumentoClob;
import com.b2wdigital.fazemu.enumeration.PontoDocumentoEnum;
import com.b2wdigital.fazemu.enumeration.TipoServicoEnum;

/**
 * Documento Clob Repository.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
public interface DocumentoClobRepository {

    DocumentoClob findById(Long id);

    DocumentoClob findByIdLoteAndIdDocFiscal(Long idLote, Long idDocFiscal);

    Long insert(DocumentoClob documentoClob);

    int delete(Long idClob);

    DocumentoClob getLastXmlSignedByIdDocFiscal(Long idDocFiscal);

    DocumentoClob getLastXmlByIdDocFiscalAndPontoDocumento(Long idDocFiscal, PontoDocumentoEnum pontoDocumento);

    String getXmlRetornoByIdDocFiscalAndTipoServico(Long idDocFiscal, TipoServicoEnum tipoServico);

    String getMaxXmlEventoByIdDocFiscalAndTipoServico(Long idDocFiscal, TipoServicoEnum tipoServico);

    public String getXmlCartaCorrecaoEnviadoByChaveAcesso(String chaveAcesso);

    Integer countEventosByChaveAcessoAndTipoServico(String chaveAcesso, String tipoServico);

}
