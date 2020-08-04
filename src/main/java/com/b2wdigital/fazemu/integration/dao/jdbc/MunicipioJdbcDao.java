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

import com.b2wdigital.fazemu.business.repository.MunicipioRepository;
import com.b2wdigital.fazemu.domain.Municipio;
import com.b2wdigital.fazemu.exception.FazemuDAOException;

@Repository
@CacheConfig(cacheManager = "cacheManager", cacheNames = "TTL5", keyGenerator = "keyGenerator")
public class MunicipioJdbcDao extends AbstractDao implements MunicipioRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(MunicipioJdbcDao.class);

    @Autowired
    @Qualifier("municipioRowMapper")
    private RowMapper<Municipio> municipioRowMapper;

    @Override
    @Cacheable
    public List<Municipio> listAll() {
        try {
            LOGGER.debug("listAll");

            StringBuilder query = new StringBuilder("select muni.* ")
                    .append("  from municipio muni ")
                    .append(" order by muni_id_municipio ");

            return namedParameterJdbcOperations.query(query.toString(), (SqlParameterSource) null, municipioRowMapper);

        } catch (DataAccessException e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    @Cacheable
    public List<Municipio> listByAtivo() {
        try {
            LOGGER.debug("listByAtivo");

            StringBuilder query = new StringBuilder("select muni.* ")
                    .append("  from municipio muni ")
                    .append("     , municipio_configuracao muco ")
                    .append(" where muco.muco_id_municipio = muni.muni_id_municipio ")
                    .append("   and muco.muco_in_ativo = 'S' ")
                    .append(" order by muni.muni_nome ");

            return namedParameterJdbcOperations.query(query.toString(), (SqlParameterSource) null, municipioRowMapper);
        } catch (DataAccessException e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public Municipio findById(Long id) {
        try {
            LOGGER.debug("findById");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idMunicipio", id);

            StringBuilder query = new StringBuilder("select muni.* ")
                    .append(" from municipio muni ")
                    .append(" where muni.muni_id_municipio = :idMunicipio ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, municipioRowMapper);

        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Municipio nao encontrado com o id {}", id);
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    @Cacheable(cacheNames = "TTL5")
    public Municipio findByCodigoIbge(Integer codigoIbge) {
        try {
            LOGGER.debug("findByCodigoIbge");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("codigoIbge", codigoIbge);

            StringBuilder query = new StringBuilder("select muni.* ")
                    .append("  from municipio muni ")
                    .append(" where muni.muni_cod_ibge = :codigoIbge ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, municipioRowMapper);

        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Municipio nao encontrado com o codigo Ibge {}", codigoIbge);
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public Municipio findByUFAndNome(String idUF, String nomeMunicipio) {
        try {
            LOGGER.debug("findByUFAndNome");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idUF", idUF)
                    .addValue("nomeMunicipio", nomeMunicipio);

            StringBuilder query = new StringBuilder("select muni.* ")
                    .append("  from municipio muni ")
                    .append("     , estado esta ")
                    .append(" where muni.muni_id_estado = esta.esta_id_estado")
                    .append("   and upper(esta.esta_id_uf) = upper(:idUF) ")
                    .append("   and upper(muni.muni_nome) = upper(:nomeMunicipio) ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, municipioRowMapper);

        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Municipio nao encontrado com a idUF {} e nomeMunicipio {}", idUF, nomeMunicipio);
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

}
