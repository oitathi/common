package com.b2wdigital.fazemu.business.repository;

import java.util.List;

import com.b2wdigital.fazemu.domain.EstadoConfiguracao;

/**
 * Estado Configuracao Repository.
 *
 * @author Marcelo Oliveira {marcelo.doliveira@b2wdigital.com}
 * @version 1.0
 */
public interface EstadoConfiguracaoRepository {

    List<EstadoConfiguracao> listAll();

    EstadoConfiguracao findByTipoDocumentoFiscalAndIdEstado(String tipoDocumentoFiscal, Long idEstado);

    EstadoConfiguracao findByTipoDocumentoFiscalAndSiglaIbge(String tipoDocumentoFiscal, Long codigoIbge);

    public void update(EstadoConfiguracao estadoConfiguracao);
}
