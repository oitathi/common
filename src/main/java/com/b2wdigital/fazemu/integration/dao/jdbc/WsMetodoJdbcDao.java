package com.b2wdigital.fazemu.integration.dao.jdbc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.b2wdigital.fazemu.business.repository.WsMetodoRepository;
import com.b2wdigital.fazemu.domain.WsMetodo;
import com.b2wdigital.fazemu.exception.FazemuDAOException;

@Repository
@CacheConfig(cacheManager = "cacheManager", cacheNames = "TTL5", keyGenerator = "keyGenerator")
public class WsMetodoJdbcDao extends AbstractDao implements WsMetodoRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(WsMetodoJdbcDao.class);

    @Autowired
    @Qualifier("wsMetodoRowMapper")
    private RowMapper<WsMetodo> wsMetodoRowMapper;

    @Override
    @Cacheable
    public List<WsMetodo> listAll() {
        try {
            LOGGER.debug("listAll");

            StringBuilder query = new StringBuilder("select wsme.* ")
                    .append("  from ws_metodo wsme ")
                    .append(" order by wsme.wsme_id_metodo ");

            return namedParameterJdbcOperations.query(query.toString(), (SqlParameterSource) null, wsMetodoRowMapper);

        } catch (DataAccessException e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public WsMetodo findById(Long id) {
        try {
            LOGGER.debug("findById");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);

            StringBuilder query = new StringBuilder("select wsme.* ")
                    .append("  from ws_metodo wsme ")
                    .append(" where wsme_id_metodo = :id ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, wsMetodoRowMapper);

        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("WS Metodo nao encontrado com o id Metodo {}", id);
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

}
