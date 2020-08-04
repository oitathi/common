package com.b2wdigital.fazemu.business.repository;

import java.util.List;

import com.b2wdigital.fazemu.domain.ResponsavelTecnicoCsrt;

/**
 * Responsavel Tecnico Csrt Repository
 *
 * @author Marcelo Oliveira {marcelo.doliveira@b2wdigital.com}
 * @version 1.0
 */
public interface ResponsavelTecnicoCsrtRepository {

    List<ResponsavelTecnicoCsrt> listAll();

    ResponsavelTecnicoCsrt findByIdEstado(Integer idEstado);
}
