package com.b2wdigital.fazemu.business.repository;

import java.util.List;
import java.util.Map;

import com.b2wdigital.fazemu.domain.ResponsavelTecnico;

/**
 * Responsavel Tecnico Repository.
 *
 * @author Marcelo Oliveira {marcelo.doliveira@b2wdigital.com}
 * @version 1.0
 */
public interface ResponsavelTecnicoRepository {

    List<ResponsavelTecnico> listAll();

    ResponsavelTecnico findByIdEmissorRaiz(Long raizCnpjEmitente);

    List<ResponsavelTecnico> listByFiltros(Map<String, String> parameters);

    void insert(ResponsavelTecnico responsavelTecnico);

    void update(ResponsavelTecnico responsavelTecnico);
}
