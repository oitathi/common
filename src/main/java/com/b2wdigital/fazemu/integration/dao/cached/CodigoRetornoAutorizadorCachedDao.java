package com.b2wdigital.fazemu.integration.dao.cached;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.b2wdigital.fazemu.business.repository.CodigoRetornoAutorizadorRepository;
import com.b2wdigital.fazemu.domain.CodigoRetornoAutorizador;
import com.b2wdigital.fazemu.exception.FazemuDAOException;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 *
 * @author dailton.almeida
 */
//@Repository("codigoRetornoAutorizadorRepository")
//@Primary
public class CodigoRetornoAutorizadorCachedDao implements CodigoRetornoAutorizadorRepository {

    private final CodigoRetornoAutorizadorRepository delegateRepository;
    private final LoadingCache<Integer, CodigoRetornoAutorizador> crauLoadingCache;

    @Autowired
    public CodigoRetornoAutorizadorCachedDao(@Qualifier("codigoRetornoAutorizadorJdbcDao") CodigoRetornoAutorizadorRepository delegateRepository) {
        this(delegateRepository, 2L, TimeUnit.MINUTES);
    }

    //usar nos testes unitarios
    public CodigoRetornoAutorizadorCachedDao(CodigoRetornoAutorizadorRepository delegateRepository, long duration, TimeUnit unit) {
        this.delegateRepository = delegateRepository;
        this.crauLoadingCache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(duration, unit)
                .build(new CacheLoader<Integer, CodigoRetornoAutorizador>() {
                    @Override
                    public CodigoRetornoAutorizador load(Integer cStat) throws Exception {
                        return delegateRepository.findById(cStat);
                    }
                });
    }

    @Override
    public List<CodigoRetornoAutorizador> listAll() {
        return delegateRepository.listAll();
    }

    @Override
    public CodigoRetornoAutorizador findById(Integer cStat) {
        try {
            return crauLoadingCache.get(cStat);
        } catch (ExecutionException ex) {
            throw new FazemuDAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public String getSituacaoAutorizacaoById(Integer cStat) {
        CodigoRetornoAutorizador result = this.findById(cStat);
        return result == null ? null : result.getSituacaoAutorizador();
    }

    @Override
    public void insert(CodigoRetornoAutorizador codigoRetornoAutorizador) {
        delegateRepository.insert(codigoRetornoAutorizador);

    }

    @Override
    public int update(CodigoRetornoAutorizador codigoRetornoAutorizador) {
        return delegateRepository.update(codigoRetornoAutorizador);
    }

}
