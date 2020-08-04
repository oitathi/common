package com.b2wdigital.fazemu.business.repository;

import java.util.List;
import java.util.Map;

import com.b2wdigital.fazemu.domain.TipoEmissao;

/**
 * Tipo Emissao Repository.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
public interface TipoEmissaoRepository {

    List<TipoEmissao> listAll();

    List<TipoEmissao> listAtivos();

    List<TipoEmissao> listAtivosByIdEstado(Long idEstado);
    
    List<TipoEmissao> listByIdEstado(Map<String, String> parameters)throws Exception;

    TipoEmissao findById(Long id);

    TipoEmissao findByIdEstado(Long idEstado);

}
