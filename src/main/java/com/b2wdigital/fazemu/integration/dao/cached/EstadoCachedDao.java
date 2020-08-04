package com.b2wdigital.fazemu.integration.dao.cached;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.b2wdigital.fazemu.business.repository.EstadoRepository;
import com.b2wdigital.fazemu.domain.Estado;
import com.b2wdigital.fazemu.exception.FazemuDAOException;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 *
 * @author dailton.almeida
 */
//@Repository("estadoRepository")
//@Primary
public class EstadoCachedDao implements EstadoRepository {

    private final EstadoRepository delegateRepository;
    private final LoadingCache<Boolean, List<Estado>> estadoLoadingCache;

    @Autowired
    public EstadoCachedDao(@Qualifier("estadoJdbcDao") EstadoRepository delegateRepository) {
        this(delegateRepository, 5L, TimeUnit.MINUTES);
    }

    //usar nos testes unitarios
    public EstadoCachedDao(EstadoRepository delegateRepository, long duration, TimeUnit unit) {
        this.delegateRepository = delegateRepository;
        this.estadoLoadingCache = CacheBuilder.newBuilder()
                .maximumSize(1)
                .expireAfterWrite(duration, unit)
                .build(new CacheLoader<Boolean, List<Estado>>() {
                    @Override
                    public List<Estado> load(Boolean dummy) throws Exception {
                        return delegateRepository.listAll();
                    }
                });
    }

    @Override
    public List<Estado> listAll() {
        try {
            return estadoLoadingCache.get(Boolean.TRUE); //tanto faz true ou false; o importante eh ser uma chave nao nula
        } catch (ExecutionException ex) {
            throw new FazemuDAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public List<Estado> listByAtivo() {
        try {
            return estadoLoadingCache.get(Boolean.TRUE); //tanto faz true ou false; o importante eh ser uma chave nao nula
        } catch (ExecutionException ex) {
            throw new FazemuDAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Estado findById(Long id) {
        return this.listAll()
                .stream()
                .filter(estado -> estado.getId().equals(id))
                .findFirst() //optional
                .orElse(null) //null se nao encontrar
                ;
    }

    @Override
    public Estado findByCodigoIbge(Integer codigoIbge) {
        return this.listAll()
                .stream()
                .filter(estado -> estado.getCodigoIbge().equals(codigoIbge))
                .findFirst() //optional
                .orElse(null) //null se nao encontrar
                ;
    }

    @Override
    public Integer getTipoEmissaoByCodigoIbge(Integer codigoIbge) {
        return delegateRepository.getTipoEmissaoByCodigoIbge(codigoIbge);
    }

}
