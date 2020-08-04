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

import com.b2wdigital.fazemu.business.repository.ResponsavelTecnicoCsrtRepository;
import com.b2wdigital.fazemu.domain.ResponsavelTecnicoCsrt;
import com.b2wdigital.fazemu.exception.FazemuDAOException;

@Repository
@CacheConfig(cacheManager = "cacheManager", cacheNames = "TTL5", keyGenerator = "keyGenerator")
public class ResponsavelTecnicoCsrtJdbcDao extends AbstractDao implements ResponsavelTecnicoCsrtRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponsavelTecnicoCsrtJdbcDao.class);

    @Autowired
    @Qualifier("responsavelTecnicoCsrtRowMapper")
    private RowMapper<ResponsavelTecnicoCsrt> responsavelTecnicoCsrtRowMapper;

    @Override
    @Cacheable
    public List<ResponsavelTecnicoCsrt> listAll() {
        try {
            LOGGER.debug("listAll");

            StringBuilder query = new StringBuilder("select rtcs.* ")
                    .append("  from resp_tecnico_csrt rtcs ")
                    .append(" order by rtcs.rtcs_id_csrt ");

            return namedParameterJdbcOperations.query(query.toString(), (SqlParameterSource) null, responsavelTecnicoCsrtRowMapper);

        } catch (DataAccessException e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public ResponsavelTecnicoCsrt findByIdEstado(Integer idEstado) {
        try {
            LOGGER.debug("findByIdEstado");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idEstado", idEstado);

            StringBuilder query = new StringBuilder("select rtcs.* ")
                    .append("  from resp_tecnico_csrt rtcs ")
                    .append(" order by rtcs.rtcs_id_estado = :idEstado ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, responsavelTecnicoCsrtRowMapper);

        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Responsavel Tecnico nao encontrado com o id Estado {}", idEstado);
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

}
