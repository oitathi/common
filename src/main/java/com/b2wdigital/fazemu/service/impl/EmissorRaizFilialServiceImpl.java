package com.b2wdigital.fazemu.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.b2wdigital.fazemu.business.repository.EmissorRaizFilialRepository;
import com.b2wdigital.fazemu.business.service.EmissorRaizFilialService;
import com.b2wdigital.fazemu.domain.EmissorRaizFilial;

@Service
public class EmissorRaizFilialServiceImpl implements EmissorRaizFilialService {

    @Autowired
    private EmissorRaizFilialRepository emissorRaizFilialRepository;

    @Override
    public EmissorRaizFilial findByIdFilial(Long idFilial) {
        return emissorRaizFilialRepository.findByIdFilial(idFilial);
    }

    @Override
    public List<EmissorRaizFilial> listByInConsultaDocumento() {
        return emissorRaizFilialRepository.listByInConsultaDocumento();
    }

    @Override
    public List<EmissorRaizFilial> listByFiltros(Map<String, String> filtros) throws Exception {
        return emissorRaizFilialRepository.listByFiltros(filtros);
    }

    @Override
    public int updateUltimoNSU(Long idFilial, String ultimoNSU, String usuario) {
        return emissorRaizFilialRepository.updateUltimoNSU(idFilial, ultimoNSU, usuario);

    }

    @Override
    public long insert(EmissorRaizFilial emissorFilial) throws Exception {
        return emissorRaizFilialRepository.insert(emissorFilial);
    }

    @Override
    public long update(EmissorRaizFilial emissorFilial) throws Exception {
        return emissorRaizFilialRepository.update(emissorFilial);
    }

}
