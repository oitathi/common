package com.b2wdigital.fazemu.business.service;

import java.util.List;

import com.b2wdigital.fazemu.domain.ParametrosInfra;

public interface ParametrosInfraService {

    List<ParametrosInfra> listAll();

    ParametrosInfra findByTipoDocumentoFiscalAndIdParametro(String tipoDocumentoFiscal, String idParametro);

    void update(ParametrosInfra parametro);

    String getAsString(String tipoDocumentoFiscal, String key);
}
