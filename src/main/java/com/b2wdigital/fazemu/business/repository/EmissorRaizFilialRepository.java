package com.b2wdigital.fazemu.business.repository;

import java.util.List;
import java.util.Map;

import com.b2wdigital.fazemu.domain.EmissorRaizFilial;

public interface EmissorRaizFilialRepository {

    EmissorRaizFilial findByIdFilial(Long idFilial);
    
    List<EmissorRaizFilial> listByInConsultaDocumento();

    List<EmissorRaizFilial> listByFiltros(Map<String, String> filtros) throws Exception;

    int updateUltimoNSU(Long idFilial, String ultimoNSU, String usuario);

    long insert(EmissorRaizFilial emissorFilial) throws Exception;

    long update(EmissorRaizFilial emissorFilial) throws Exception;
}
