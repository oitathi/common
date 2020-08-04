package com.b2wdigital.fazemu.integration.dao.cached;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.b2wdigital.fazemu.business.repository.UrlRepository;
import com.b2wdigital.fazemu.domain.UrlKey;
import com.b2wdigital.fazemu.exception.FazemuDAOException;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 *
 * @author dailton.almeida
 */
@Repository("urlRepository")
@Primary
public class UrlCachedDao implements UrlRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UrlCachedDao.class);
//    private final UrlRepository delegateRepository;
    private final LoadingCache<UrlKey, Optional<String>> urlLoadingCache;

    @Autowired
    public UrlCachedDao(@Qualifier("urlJdbcDao") UrlRepository delegateRepository) {
        this(delegateRepository, 5L, TimeUnit.MINUTES);
    }

    //usar nos testes unitarios
    public UrlCachedDao(UrlRepository delegateRepository, long duration, TimeUnit unit) {
//        this.delegateRepository = delegateRepository;
        this.urlLoadingCache = CacheBuilder.newBuilder()
                .maximumSize(200)
                .expireAfterWrite(duration, unit)
                .build(new CacheLoader<UrlKey, Optional<String>>() {
                    @Override
                    public Optional<String> load(UrlKey key) throws Exception {
                        try {
                            LOGGER.debug("{} nao encontrada no cache", key);
                            String url = delegateRepository.getUrl(key.getCodigoIBGE(), key.getIdTipoEmissao(), key.getIdServico(), key.getVersao());
                            return Optional.ofNullable(url);
                        } catch (FazemuDAOException e) {
                            return Optional.empty();
                        }
                    }
                });
    }

    @Override
    public String getUrl(Integer codigoIBGE, Integer idTipoEmissao, String idServico, String versao) {
        try {
            UrlKey key = UrlKey.build(codigoIBGE, idTipoEmissao, idServico, versao);
            Optional<String> opt = urlLoadingCache.get(key);
            return opt.orElse(null);
        } catch (ExecutionException ex) {
            throw new FazemuDAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public String getUrlNormal(Integer codigoIBGE, String idServico, String versao) {
        try {
            UrlKey key = UrlKey.build(codigoIBGE, 1, idServico, versao);
            Optional<String> opt = urlLoadingCache.get(key);
            return opt.orElse(null);
        } catch (ExecutionException ex) {
            throw new FazemuDAOException(ex.getMessage(), ex);
        }
    }

}
