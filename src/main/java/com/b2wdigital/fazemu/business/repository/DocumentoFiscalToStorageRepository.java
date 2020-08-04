package com.b2wdigital.fazemu.business.repository;

import java.util.Date;
import java.util.List;

import com.b2wdigital.fazemu.domain.DocumentoFiscalToStorage;
import com.b2wdigital.fazemu.enumeration.TipoServicoEnum;

public interface DocumentoFiscalToStorageRepository {

    List<DocumentoFiscalToStorage> listToStorageByDateInterval(Date dataHoraInicio, Date dataHoraFim, String excludeList);

    DocumentoFiscalToStorage findToStorageByIdDocFiscalAndTipoServicoAndTipoEvento(Long idDocFiscal, String tipoServico, Long tipoEvento);

    List<DocumentoFiscalToStorage> listToStorageByIdDocFiscalAndTipoServico(Long idDocFiscal, TipoServicoEnum tipoServico);

}
