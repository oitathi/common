package com.b2wdigital.fazemu.integration.dao.jdbc;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;

import com.b2wdigital.fazemu.business.repository.ParametrosInfraRepository;
import com.b2wdigital.fazemu.domain.ParametrosInfra;
import com.b2wdigital.fazemu.exception.FazemuDAOException;
import com.b2wdigital.fazemu.integration.mapper.ParametrosInfraRowMapper;

/**
 *
 * @author dailton.almeida
 */
@Repository("parametrosInfraJdbcDao") //resolve ambiguidade contra versao cacheada
@CacheConfig(cacheManager = "cacheManager", cacheNames = "TTL2", keyGenerator = "keyGenerator")
public class ParametrosInfraJdbcDao extends AbstractDao implements ParametrosInfraRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParametrosInfraJdbcDao.class);
    private static final String KEY_ID_PARAMETRO = "idParametro";

    @Autowired
    private LobHandler lobHandler;

    @Autowired
    private ParametrosInfraRowMapper parametrosInfraRowMapper;

    @Override
    @Cacheable
    public List<ParametrosInfra> listAll() {
        try {
            LOGGER.debug("listAll");

            StringBuilder query = new StringBuilder("select pain.* ")
                    .append("  from parametros_infra pain ")
                    .append(" order by pain_id_parametro ");

            return namedParameterJdbcOperations.query(query.toString(), (SqlParameterSource) null, parametrosInfraRowMapper);

        } catch (DataAccessException e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public ParametrosInfra findByTipoDocumentoFiscalAndIdParametro(String tipoDocumentoFiscal, String idParametro) {
        try {
            LOGGER.debug("findByTipoDocumentoFiscalAndIdParametro");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("tipoDocumentoFiscal", tipoDocumentoFiscal)
                    .addValue("idParametro", idParametro);

            StringBuilder query = new StringBuilder("select pain.* ")
                    .append("  from parametros_infra pain ")
                    .append(" where pain.pain_tp_doc_fiscal = nvl(:tipoDocumentoFiscal, pain.pain_tp_doc_fiscal) ")
                    .append("   and pain.pain_id_parametro = :idParametro ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, parametrosInfraRowMapper);

        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("ParametrosInfra nao encontrado com o id {}", idParametro);
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    @Cacheable
    public String getAsString(String tipoDocumentoFiscal, String key) {
        return this.getAsString(tipoDocumentoFiscal, key, null);
    }

    @Override
    @Cacheable
    public String getAsString(String tipoDocumentoFiscal, String key, String defaultValue) {
        try {
            LOGGER.debug("getAsString {}", key);

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("tipoDocumentoFiscal", tipoDocumentoFiscal)
                    .addValue(KEY_ID_PARAMETRO, key);

            StringBuilder query = new StringBuilder("select pain.pain_valor ")
                    .append("  from parametros_infra pain ")
                    .append(" where pain.pain_tp_doc_fiscal = nvl(:tipoDocumentoFiscal, pain.pain_tp_doc_fiscal) ")
                    .append("   and pain.pain_id_parametro = :idParametro ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, String.class);

        } catch (EmptyResultDataAccessException erdae) {
            LOGGER.debug("Parametro {} inexistente; retornando valor default {}", key, defaultValue);
            return defaultValue;
        } catch (DataAccessException dae) {
            throw new FazemuDAOException(dae.getMessage(), dae);
        }
    }

    @Override
    @Cacheable
    public Integer getAsInteger(String tipoDocumentoFiscal, String key) {
        return this.getAsInteger(tipoDocumentoFiscal, key, null);
    }

    @Override
    @Cacheable
    public Integer getAsInteger(String tipoDocumentoFiscal, String key, Integer defaultValue) {
        try {
            LOGGER.debug("getAsInteger {}", key);

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("tipoDocumentoFiscal", tipoDocumentoFiscal)
                    .addValue(KEY_ID_PARAMETRO, key);

            StringBuilder query = new StringBuilder("select to_number(pain_valor) ")
                    .append("  from parametros_infra pain ")
                    .append(" where pain.pain_tp_doc_fiscal = nvl(:tipoDocumentoFiscal, pain.pain_tp_doc_fiscal) ")
                    .append("   and pain.pain_id_parametro = :idParametro ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, Integer.class);
        } catch (EmptyResultDataAccessException erdae) {
            LOGGER.info("Parametro {} inexistente; retornando valor default {}", key, defaultValue);
            return defaultValue;
        } catch (DataAccessException dae) {
            throw new FazemuDAOException(dae.getMessage(), dae);
        }
    }

    @Override
    @Cacheable
    public Map<String, String> getAllAsMap() {
        try {
            LOGGER.info("getAllAsMap");

            StringBuilder query = new StringBuilder("select pain.pain_id_parametro ")
                    .append("     , pain.pain_valor ")
                    .append("  from parametros_infra pain ");

            List<Map<String, Object>> mapList = namedParameterJdbcOperations.queryForList(query.toString(), (SqlParameterSource) null);

            return toParametrosInfraMap(mapList);
        } catch (DataAccessException dae) {
            throw new FazemuDAOException(dae.getMessage(), dae);
        }
    }

    protected Map<String, String> toParametrosInfraMap(List<Map<String, Object>> mapList) {
        Map<String, String> result = mapList
                .stream()
                .collect(Collectors.toMap(map -> (String) map.get("PAIN_ID_PARAMETRO"),
                        map -> (String) map.get("PAIN_VALOR")));
        LOGGER.debug("mapList {} result {}", mapList, result);
        return result;
    }

    @Override
    public byte[] getAsByteArray(String tipoDocumentoFiscal, String key) {
        return this.getAsByteArray(tipoDocumentoFiscal, key, null);
    }

    @Override
    public byte[] getAsByteArray(String tipoDocumentoFiscal, String key, byte[] defaultValue) {
        try {
            LOGGER.debug("getAsByteArray {}", key);

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("tipoDocumentoFiscal", tipoDocumentoFiscal)
                    .addValue(KEY_ID_PARAMETRO, key);

            StringBuilder query = new StringBuilder("select paib.paib_valor ")
                    .append("  from parametros_infra_blob paib ")
                    .append(" where paib.paib_tp_doc_fiscal = nvl(:tipoDocumentoFiscal, paib.paib_tp_doc_fiscal) ")
                    .append("   and paib.paib_id_parametro = :idParametro ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, (rs, rowNum) -> lobHandler.getBlobAsBytes(rs, "paib_valor"));

        } catch (EmptyResultDataAccessException erdae) {
            LOGGER.debug("Parametro blob {} inexistente; retornando valor default {}", defaultValue);
            return defaultValue;
        } catch (DataAccessException dae) {
            throw new FazemuDAOException(dae.getMessage(), dae);
        }
    }

    @Override
    public int update(ParametrosInfra parametro) {
        try {
            LOGGER.debug("ParametrosInfraJdbcDao: update");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idParametro", parametro.getIdParametro())
                    .addValue("tipoDocumentoFiscal", parametro.getTipoDocumentoFiscal())
                    .addValue("valor", parametro.getValor())
                    .addValue("descricao", parametro.getDescricao())
                    .addValue("usuario", parametro.getUsuario());

            StringBuilder sql = new StringBuilder("update parametros_infra ")
                    .append("   set pain_valor = :valor, pain_descricao = :descricao, pain_usuario = :usuario, pain_datahora = sysdate  ")
                    .append(" where pain_tp_doc_fiscal = nvl(:tipoDocumentoFiscal, pain.pain_tp_doc_fiscal) ")
                    .append("   and pain_id_parametro = :idParametro ");

            return namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);

        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

}
