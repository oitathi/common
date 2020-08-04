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

import com.b2wdigital.fazemu.business.repository.EstadoRepository;
import com.b2wdigital.fazemu.domain.Estado;
import com.b2wdigital.fazemu.exception.FazemuDAOException;

@Repository
@CacheConfig(cacheManager = "cacheManager", cacheNames = "TTL5", keyGenerator = "keyGenerator")
public class EstadoJdbcDao extends AbstractDao implements EstadoRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(EstadoJdbcDao.class);

    @Autowired
    @Qualifier("estadoRowMapper")
    private RowMapper<Estado> estadoRowMapper;

    @Override
//    @Cacheable(key = "#root.methodName")
    @Cacheable
    public List<Estado> listAll() {
        try {
            LOGGER.debug("listAll");

            StringBuilder query = new StringBuilder("select esta.* ")
                    .append("  from estado esta ")
                    .append(" order by esta_id_uf ");

            return namedParameterJdbcOperations.query(query.toString(), (SqlParameterSource) null, estadoRowMapper);

        } catch (DataAccessException e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    @Cacheable
    public List<Estado> listByAtivo() {
        try {
            LOGGER.debug("listByAtivo");

            StringBuilder query = new StringBuilder("select esta.* ")
                    .append("  from estado esta ")
                    .append("     , estado_configuracao esco ")
                    .append(" where esco.esco_id_estado = esta.esta_id_estado ")
                    .append("   and esco.esco_in_ativo = 'S' ")
                    .append(" order by esta.esta_id_uf ");

            return namedParameterJdbcOperations.query(query.toString(), (SqlParameterSource) null, estadoRowMapper);
        } catch (DataAccessException e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public Estado findById(Long id) {
        try {
            LOGGER.debug("findById");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idEstado", id);

            StringBuilder query = new StringBuilder("select esta.* ")
                    .append("  from estado esta ")
                    .append(" where esta.esta_id_estado = :idEstado ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, estadoRowMapper);

        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Estado nao encontrado com o id {}", id);
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    @Cacheable(cacheNames = "TTL5")
    public Estado findByCodigoIbge(Integer codigoIbge) {
        try {
            LOGGER.debug("findByCodigoIbge");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("codigoIbge", codigoIbge);

            StringBuilder query = new StringBuilder("select esta.* ")
                    .append("  from estado esta ")
                    .append(" where esta.esta_cod_ibge = :codigoIbge ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, estadoRowMapper);

        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Estado nao encontrado com o codigo Ibge {}", codigoIbge);
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public Integer getTipoEmissaoByCodigoIbge(Integer codigoIbge) {
        try {
            LOGGER.debug("getTipoEmissaoByCodigoIbge");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("codigoIbge", codigoIbge);

            StringBuilder query = new StringBuilder("select este.este_tp_emissao ")
                    .append("  from estado_tp_emissao este ")
                    .append("     , estado esta ")
                    .append(" where este.este_id_estado = esta.esta_id_estado ")
                    .append("   and esta.esta_cod_ibge  = :codigoIbge ")
                    .append("   and sysdate ")
                    .append("between este.este_dt_ini ")
                    .append("   and este.este_dt_fim ")
                    .append("   and rownum = 1");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, Integer.class);

        } catch (EmptyResultDataAccessException e) {
            LOGGER.debug("getTipoEmissaoByCodigoIbgePain");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("codigoIbge", codigoIbge);

            StringBuilder query = new StringBuilder("select pain.pain_valor ")
                    .append("  from parametros_infra pain ")
                    .append(" where pain.pain_id_parametro = 'SEFAZ_TP_EMISSAO' ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, Integer.class);

        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

}
