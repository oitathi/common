package com.b2wdigital.fazemu.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.b2wdigital.fazemu.business.repository.ParametrosInfraRepository;
import com.b2wdigital.fazemu.business.repository.RedisOperationsRepository;
import com.b2wdigital.fazemu.business.service.ParametrosInfraService;
import com.b2wdigital.fazemu.domain.ParametrosInfra;

/**
 * ParametrosInfra Service Impl.
 *
 * @author Marcelo Oliveira {marcelo.doliveira@b2wdigital.com}
 * @version 1.0
 */
@Service
public class ParametrosInfraServiceImpl implements ParametrosInfraService {

    @Autowired
    private ParametrosInfraRepository parametrosInfraRepository;

    @Autowired
    private RedisOperationsRepository redisOperationsRepository;

    @Override
    public List<ParametrosInfra> listAll() {
        return parametrosInfraRepository.listAll();
    }

    @Override
    public ParametrosInfra findByTipoDocumentoFiscalAndIdParametro(String tipoDocumentoFiscal, String idParametro) {
        return parametrosInfraRepository.findByTipoDocumentoFiscalAndIdParametro(tipoDocumentoFiscal, idParametro);
    }

    @Override
    public String getAsString(String tipoDocumentoFiscal, String key) {
        return parametrosInfraRepository.getAsString(tipoDocumentoFiscal, key);
    }

    @Override
    public void update(ParametrosInfra parametro) {
        parametrosInfraRepository.update(parametro);
        expireCache();
    }

    private void expireCache() {
        //Expira o cache manualmente
        redisOperationsRepository.expiresKey("ParametrosInfraJdbcDao::listAll", 1L, TimeUnit.MILLISECONDS);
    }

}
