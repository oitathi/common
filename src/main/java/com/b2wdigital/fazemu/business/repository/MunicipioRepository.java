package com.b2wdigital.fazemu.business.repository;

import java.util.List;

import com.b2wdigital.fazemu.domain.Municipio;

/**
 * Municipio Repository.
 *
 * @author Marcelo Oliveira {marcelo.doliveira@b2wdigital.com}
 * @version 1.0
 */
public interface MunicipioRepository {

    List<Municipio> listAll();

    List<Municipio> listByAtivo();

    Municipio findById(Long id);

    Municipio findByCodigoIbge(Integer codigoIbge);

    Municipio findByUFAndNome(String idUF, String nomeMunicipio);
}
