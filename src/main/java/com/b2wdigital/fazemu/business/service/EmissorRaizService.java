package com.b2wdigital.fazemu.business.service;

import java.util.List;
import java.util.Map;

import com.b2wdigital.fazemu.domain.EmissorRaiz;
import com.b2wdigital.fazemu.domain.form.EmissorRaizForm;

/**
 * Emissor Raiz Service.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
public interface EmissorRaizService {

    List<EmissorRaizForm> toForm(List<EmissorRaiz> emissoresRaiz);

    List<EmissorRaiz> listByFiltros(Map<String, String> filtros);

    void insert(EmissorRaiz emissorRaiz);

    void update(EmissorRaiz emissorRaiz);

}
