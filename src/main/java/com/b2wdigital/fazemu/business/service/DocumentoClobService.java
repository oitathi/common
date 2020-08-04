package com.b2wdigital.fazemu.business.service;

import com.b2wdigital.fazemu.domain.DocumentoClob;
import com.b2wdigital.fazemu.enumeration.PontoDocumentoEnum;
import com.b2wdigital.fazemu.enumeration.TipoServicoEnum;

public interface DocumentoClobService {

    DocumentoClob findById(Long id);

    Long insert(DocumentoClob clob);

    DocumentoClob getLastXmlSignedByIdDocFiscal(Long idDocFiscal);

    DocumentoClob getLastXmlByIdDocFiscalAndPontoDocumento(Long idDocFiscal, PontoDocumentoEnum pontoDocumento);

    String getXmlRetornoByIdDocFiscalAndTipoServico(Long idDocFiscal, TipoServicoEnum tipoServico);

    String getMaxXmlEventoByIdDocFiscalAndTipoServico(Long idDocFiscal, TipoServicoEnum tipoServico);
}
