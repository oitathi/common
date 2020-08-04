package com.b2wdigital.fazemu.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.b2wdigital.fazemu.business.repository.EmissorRaizRepository;
import com.b2wdigital.fazemu.business.repository.RedisOperationsRepository;
import com.b2wdigital.fazemu.business.service.EmissorRaizService;
import com.b2wdigital.fazemu.domain.EmissorRaiz;
import com.b2wdigital.fazemu.domain.form.EmissorRaizForm;
import com.b2wdigital.fazemu.exception.FazemuServiceException;

/**
 * Emissor Raiz Service.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
@Service
public class EmissorRaizServiceImpl implements EmissorRaizService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmissorRaizServiceImpl.class);

    @Autowired
    private EmissorRaizRepository emissorRaizRepository;

    @Autowired
    private RedisOperationsRepository redisOperationsRepository;

    @Autowired
    private MessageSource ms;

    private final Locale locale = LocaleContextHolder.getLocale();

    @Override
    public void insert(EmissorRaiz emissorRaiz) {
        emissorRaizRepository.insert(emissorRaiz);
        expireCache();
    }

    @Override
    public void update(EmissorRaiz emissorRaiz) {
        emissorRaizRepository.update(emissorRaiz);
        expireCache();
    }

    @Override
    public List<EmissorRaiz> listByFiltros(Map<String, String> filtros) {
        LOGGER.debug("listaEmissorRaiz");

        List<EmissorRaiz> listaEmissorRaiz = emissorRaizRepository.listByFiltros(filtros);
        if (listaEmissorRaiz.isEmpty() || listaEmissorRaiz == null) {
            throw new FazemuServiceException(ms.getMessage("emissor.not.found", null, locale));
        }

        return listaEmissorRaiz;

    }

    @Override
    public List<EmissorRaizForm> toForm(List<EmissorRaiz> emissoresRaiz) {
        List<EmissorRaizForm> listForm = new ArrayList<>();
        emissoresRaiz.forEach(er -> listForm.add(new ModelMapper().map(er, EmissorRaizForm.class)));

        return listForm;
    }

    private void expireCache() {
        //Expira o cache manualmente
        redisOperationsRepository.expiresKey("EmissorRaizJdbcDao::listAll", 1L, TimeUnit.MILLISECONDS);
    }

}
