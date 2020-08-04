package com.b2wdigital.fazemu.business.repository;

import java.util.List;
import java.util.Date;

import com.b2wdigital.fazemu.domain.DocumentoRetorno;

public interface DocumentoRetornoRepository {

    DocumentoRetorno findByIdDocFiscalAndTpServicoAndTpEvento(Long idDocFiscal, String tipoServico, Long tipoEvento);

    List<DocumentoRetorno> findByIdDocFiscal(Long idDocFiscal);

    int insert(Long idDocFiscal, String tipoServico, Long tipoEvento, Long idXml, String usuarioReg, String usuario);

    int update(Long idDocFiscal, String tipoServico, Long tipoEvento, Long idXml, String usuario);

    List<DocumentoRetorno> listByDateIntervalAndNotExistsDocl(String tipoDocumentoFiscal, Date dataHoraInicio, Date dataHoraFim, String excludeList);

    List<DocumentoRetorno> listByDataHoraInicioAndNotExistsXML(Date dataHoraInicio, String excludeList);

    //Storge
    int updateURL(Long idDocFiscal, String tipoServico, Long tipoEvento, String url, String usuario);

    List<DocumentoRetorno> listByDateHoraInicio(Integer quantidadeDias, String excludeList);
}
