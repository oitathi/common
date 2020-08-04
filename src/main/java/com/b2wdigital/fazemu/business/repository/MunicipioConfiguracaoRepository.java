package com.b2wdigital.fazemu.business.repository;

import java.util.List;

import com.b2wdigital.fazemu.domain.MunicipioConfiguracao;

/**
 * Municipio Configuracao Repository.
 *
 * @author Marcelo Oliveira {marcelo.doliveira@b2wdigital.com}
 * @version 1.0
 */
public interface MunicipioConfiguracaoRepository {

    List<MunicipioConfiguracao> listAll();

    MunicipioConfiguracao findByTipoDocumentoFiscalAndIdMunicipio(String tipoDocumentoFiscal, Long idMunicipio);

    MunicipioConfiguracao findByTipoDocumentoFiscalAndSiglaIbge(String tipoDocumentoFiscal, Long codigoIbge);

    public void update(MunicipioConfiguracao municipioConfiguracao);
}
