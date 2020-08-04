package com.b2wdigital.fazemu.business.repository;

import java.util.List;

import com.b2wdigital.fazemu.domain.SistemaWs;

/**
 * SistemaWs Repository.
 *
 * @author Marcelo Oliveira {marcelo.doliveira@b2wdigital.com}
 * @version 1.0
 */
public interface SistemaWsRepository {

    List<SistemaWs> listAll();

    List<SistemaWs> listByTipoDocumentoFiscalAndIdSistemaAtivoAndTipoServico(String tipoDocumentoFiscal, String idSistema, String tipoServico);
}
