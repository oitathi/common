package com.b2wdigital.fazemu.integration.dao.cached;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.b2wdigital.fazemu.business.repository.TipoEmissaoRepository;
import com.b2wdigital.fazemu.domain.TipoEmissao;
import com.b2wdigital.fazemu.exception.FazemuDAOException;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 *
 * @author dailton.almeida
 */
//@Repository("tipoEmissaoRepository")
//@Primary
public class TipoEmissaoCachedDao implements TipoEmissaoRepository {

    private final TipoEmissaoRepository delegateRepository;
    private final LoadingCache<Boolean, List<TipoEmissao>> tipoEmissaoLoadingCache;

    @Autowired
    public TipoEmissaoCachedDao(@Qualifier("tipoEmissaoJdbcDao") TipoEmissaoRepository delegateRepository) {
        this(delegateRepository, 5L, TimeUnit.MINUTES);
    }

    //usar nos testes unitarios
    public TipoEmissaoCachedDao(TipoEmissaoRepository delegateRepository, long duration, TimeUnit unit) {
        this.delegateRepository = delegateRepository;
        this.tipoEmissaoLoadingCache = CacheBuilder.newBuilder()
                .maximumSize(1)
                .expireAfterWrite(duration, unit)
                .build(new CacheLoader<Boolean, List<TipoEmissao>>() {
                    @Override
                    public List<TipoEmissao> load(Boolean dummy) throws Exception {
                        return delegateRepository.listAtivos();
                    }
                });
    }

    @Override
    public List<TipoEmissao> listAll() {
        try {
            return tipoEmissaoLoadingCache.get(Boolean.TRUE); //tanto faz true ou false; o importante eh ser uma chave nao nula
        } catch (ExecutionException ex) {
            throw new FazemuDAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public List<TipoEmissao> listAtivos() {
        try {
            return tipoEmissaoLoadingCache.get(Boolean.TRUE); //tanto faz true ou false; o importante eh ser uma chave nao nula
        } catch (ExecutionException ex) {
            throw new FazemuDAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public TipoEmissao findById(Long id) {
        return this.listAtivos()
                .stream()
                .filter(estado -> estado.getId().equals(id))
                .findFirst() //optional
                .orElse(null) //null se nao existe
                ;
    }

    @Override
    public List<TipoEmissao> listAtivosByIdEstado(Long idEstado) {
        return delegateRepository.listAtivosByIdEstado(idEstado);
    }

    @Override
    public TipoEmissao findByIdEstado(Long idEstado) {
        return delegateRepository.findByIdEstado(idEstado);
    }

	@Override
	public List<TipoEmissao> listByIdEstado(Map<String, String> parameters) throws Exception{
		 return delegateRepository.listByIdEstado(parameters);
	}

}
