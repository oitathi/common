package com.b2wdigital.fazemu.business.repository;

import java.util.List;

import com.b2wdigital.fazemu.domain.Estado;

/**
 * Estado Repository.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
public interface EstadoRepository {

    List<Estado> listAll();

    List<Estado> listByAtivo();

    Estado findById(Long id);

    Estado findByCodigoIbge(Integer codigoIbge);

    Integer getTipoEmissaoByCodigoIbge(Integer codigoIbge);
}
