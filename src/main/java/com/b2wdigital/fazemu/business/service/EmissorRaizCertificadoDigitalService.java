package com.b2wdigital.fazemu.business.service;

import java.util.List;
import java.util.Map;

import com.b2wdigital.fazemu.domain.form.EmissorRaizCertificadoDigitalForm;

/**
 * EmissorRaizCertificadoDigital Service.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
public interface EmissorRaizCertificadoDigitalService {

    List<EmissorRaizCertificadoDigitalForm> listByFiltros(Map<String, String> filtros);

    List<EmissorRaizCertificadoDigitalForm> listByDataFimVigencia();

    void insert(EmissorRaizCertificadoDigitalForm form);

    void update(EmissorRaizCertificadoDigitalForm form);

}
