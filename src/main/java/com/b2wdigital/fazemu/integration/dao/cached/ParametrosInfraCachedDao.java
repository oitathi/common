package com.b2wdigital.fazemu.integration.dao.cached;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.b2wdigital.fazemu.business.repository.ParametrosInfraRepository;
import com.b2wdigital.fazemu.domain.ParametrosInfra;
import com.b2wdigital.fazemu.exception.FazemuDAOException;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 *
 * @author dailton.almeida
 */
//@Repository("parametrosInfraRepository")
//@Primary
public class ParametrosInfraCachedDao implements ParametrosInfraRepository {

    private final ParametrosInfraRepository delegateRepository;
    private final LoadingCache<String, String> painLoadingCache;
    private final LoadingCache<Boolean, List<ParametrosInfra>> parametrosInfraLoadingCache;
    private final LoadingCache<String, byte[]> paibLoadingCache;

    @Autowired
    public ParametrosInfraCachedDao(@Qualifier("parametrosInfraJdbcDao") String tipoDocumentoFiscal, ParametrosInfraRepository delegateRepository) {
        this(tipoDocumentoFiscal, delegateRepository, 2L, TimeUnit.MINUTES);
    }

    //usar nos testes unitarios
    public ParametrosInfraCachedDao(String tipoDocumentoFiscal, ParametrosInfraRepository delegateRepository, long duration, TimeUnit unit) {
        this.delegateRepository = delegateRepository;
        this.painLoadingCache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(duration, unit)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        return delegateRepository.getAsString(tipoDocumentoFiscal, key);
                    }
                });
        this.parametrosInfraLoadingCache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(duration, unit)
                .build(new CacheLoader<Boolean, List<ParametrosInfra>>() {
                    @Override
                    public List<ParametrosInfra> load(Boolean dummy) throws Exception {
                        return delegateRepository.listAll();
                    }
                });
        this.paibLoadingCache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(duration, unit)
                .build(new CacheLoader<String, byte[]>() {
                    @Override
                    public byte[] load(String key) throws Exception {
                        return delegateRepository.getAsByteArray(tipoDocumentoFiscal, key);
                    }
                });
    }

    @Override
    public List<ParametrosInfra> listAll() {
        try {
            return parametrosInfraLoadingCache.get(Boolean.TRUE); //tanto faz true ou false; o importante eh ser uma chave nao nula
        } catch (ExecutionException ex) {
            throw new FazemuDAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public ParametrosInfra findByTipoDocumentoFiscalAndIdParametro(String tipoDocumentoFiscal, String idParametro) {
        return this.listAll()
                .stream()
                .filter(parametrosInfra -> parametrosInfra.getIdParametro().equals(idParametro) && parametrosInfra.getTipoDocumentoFiscal().equals(tipoDocumentoFiscal))
                .findFirst() //optional
                .orElse(null) //null se nao encontrar
                ;
    }

    @Override
    public String getAsString(String tipoDocumentoFiscal, String key) {
        try {
            return painLoadingCache.get(key);
        } catch (ExecutionException ex) {
            throw new FazemuDAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public String getAsString(String tipoDocumentoFiscal, String key, String defaultValue) {
        return StringUtils.defaultIfBlank(this.getAsString(tipoDocumentoFiscal, key), defaultValue);
    }

    @Override
    public Integer getAsInteger(String tipoDocumentoFiscal, String key) {
        return this.getAsInteger(tipoDocumentoFiscal, key, null);
    }

    @Override
    public Integer getAsInteger(String tipoDocumentoFiscal, String key, Integer defaultValue) {
        String strValue = this.getAsString(tipoDocumentoFiscal, key);
        return StringUtils.isBlank(strValue) ? defaultValue : Integer.valueOf(strValue);
    }

    @Override
    public Map<String, String> getAllAsMap() {
        return delegateRepository.getAllAsMap();
    }

    @Override
    public byte[] getAsByteArray(String tipoDocumentoFiscal, String key) {
        try {
            return paibLoadingCache.get(key);
        } catch (ExecutionException ex) {
            throw new FazemuDAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public byte[] getAsByteArray(String tipoDocumentoFiscal, String key, byte[] defaultValue) {
        return ObjectUtils.defaultIfNull(this.getAsByteArray(tipoDocumentoFiscal, key), defaultValue);
    }

    @Override
    public int update(ParametrosInfra parametro) {
        return 0;
    }

}
