package com.b2wdigital.fazemu.integration.dao.jdbc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.b2wdigital.fazemu.business.repository.CodigoRetornoAutorizadorRepository;
import com.b2wdigital.fazemu.domain.CodigoRetornoAutorizador;
import com.b2wdigital.fazemu.exception.FazemuDAOException;

/**
 *
 * @author dailton.almeida
 */
@Repository
@CacheConfig(cacheManager = "cacheManager", cacheNames = "TTL5", keyGenerator = "keyGenerator")
public class CodigoRetornoAutorizadorJdbcDao extends AbstractDao implements CodigoRetornoAutorizadorRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodigoRetornoAutorizadorJdbcDao.class);

    @Autowired
    private RowMapper<CodigoRetornoAutorizador> codigoRetornoAutorizadorRowMapper;

    @Override
    @Cacheable
    public List<CodigoRetornoAutorizador> listAll() {
        try {
            LOGGER.info("listAll");

            StringBuilder query = new StringBuilder("select crau.* ")
                    .append("  from codigo_ret_autorizador crau ")
                    .append(" order by crau.crau_id_cod ");

            return namedParameterJdbcOperations.query(query.toString(), (MapSqlParameterSource) null, codigoRetornoAutorizadorRowMapper);

        } catch (DataAccessException dae) {
            throw new FazemuDAOException(dae.getMessage(), dae);
        }
    }

    @Override
    @Cacheable
    public CodigoRetornoAutorizador findById(Integer cStat) {
        try {
            LOGGER.debug("findById");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("cStat", cStat);

            StringBuilder query = new StringBuilder("select crau.* ")
                    .append("  from codigo_ret_autorizador crau ")
                    .append(" where crau.crau_id_cod = :cStat ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, codigoRetornoAutorizadorRowMapper);

        } catch (EmptyResultDataAccessException erdae) {
            return null;
        } catch (DataAccessException dae) {
            throw new FazemuDAOException(dae.getMessage(), dae);
        }
    }

    @Override
    @Cacheable
    public String getSituacaoAutorizacaoById(Integer cStat) {
        try {
            LOGGER.debug("getSituacaoAutorizacaoById");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("cStat", cStat);

            StringBuilder query = new StringBuilder("select crau.crau_sit_autoriz ")
                    .append("  from codigo_ret_autorizador crau ")
                    .append(" where crau.crau_id_cod = :cStat ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, String.class);

        } catch (EmptyResultDataAccessException erdae) {
            return null;
        } catch (DataAccessException dae) {
            throw new FazemuDAOException(dae.getMessage(), dae);
        }
    }

    @Override
    public void insert(CodigoRetornoAutorizador codigoRetornoAutorizador) {
        LOGGER.debug("CodigoRetornoAutorizadorJdbcDao: insert");
    }

    @Override
    public int update(CodigoRetornoAutorizador codigoRetornoAutorizador) {
        return 0;
    }

}
