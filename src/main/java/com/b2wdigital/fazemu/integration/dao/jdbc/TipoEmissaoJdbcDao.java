package com.b2wdigital.fazemu.integration.dao.jdbc;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
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

import com.b2wdigital.fazemu.business.repository.TipoEmissaoRepository;
import com.b2wdigital.fazemu.domain.TipoEmissao;
import com.b2wdigital.fazemu.enumeration.TipoEmissaoEnum;
import com.b2wdigital.fazemu.exception.FazemuDAOException;

@Repository
@CacheConfig(cacheManager = "cacheManager", cacheNames = "TTL5", keyGenerator = "keyGenerator")
public class TipoEmissaoJdbcDao extends AbstractDao implements TipoEmissaoRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TipoEmissaoJdbcDao.class);
    private static final String KEY_ID_ESTADO = "idEstado";
    private static final String KEY_SITUACAO = "situacao";
    private static final String SITUACAO_ATIVA = "A";

    @Autowired
    @Qualifier("tipoEmissaoRowMapper")
    private RowMapper<TipoEmissao> tipoEmissaoRowMapper;

    @Override
    @Cacheable
    public TipoEmissao findById(Long id) {
        try {
            LOGGER.debug("findById");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);

            StringBuilder query = new StringBuilder("select tpem.* ")
                    .append("  from tipo_emissao tpem ")
                    .append(" where tpem.tpem_tp_emissao = :id ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, tipoEmissaoRowMapper);

        } catch (EmptyResultDataAccessException erdae) {
            LOGGER.error("Tipo de Emissao nao encontrado com o codigo {}", id);
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar findById", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public TipoEmissao findByIdEstado(Long idEstado) {
        try {
            LOGGER.debug("findByIdEstado");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(KEY_ID_ESTADO, idEstado)
                    .addValue(KEY_SITUACAO, SITUACAO_ATIVA);

            StringBuilder query = new StringBuilder("select tpem.* ")
                    .append("  from tipo_emissao tpem ")
                    .append("     , estado_tp_emissao este ")
                    .append(" where tpem.tpem_tp_emissao = este.este_tp_emissao ")
                    .append("   and tpem.tpem_situacao = :situacao ")
                    .append("   and este.este_id_estado = :idEstado ")
                    .append("   and sysdate ")
                    .append("between este.este_dt_ini ")
                    .append("   and este.este_dt_fim ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, tipoEmissaoRowMapper);

        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    @Cacheable
    public List<TipoEmissao> listAll() {
        try {
            LOGGER.debug("listAll");

            StringBuilder query = new StringBuilder("select tpem.* ")
                    .append("  from tipo_emissao tpem ")
                    .append(" order by tpem.tpem_nome ");

            return namedParameterJdbcOperations.query(query.toString(), (SqlParameterSource) null, tipoEmissaoRowMapper);

        } catch (DataAccessException e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    @Cacheable
    public List<TipoEmissao> listAtivos() {
        try {
            LOGGER.debug("listAtivos");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(KEY_SITUACAO, SITUACAO_ATIVA);

            StringBuilder query = new StringBuilder("select tpem.* ")
                    .append("  from tipo_emissao tpem ")
                    .append(" where tpem.tpem_situacao = :situacao ");

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, tipoEmissaoRowMapper);

        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar listAtivos", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    @Cacheable
    public List<TipoEmissao> listAtivosByIdEstado(Long idEstado) {
        try {
            LOGGER.debug("listAtivosByIdEstado {}", idEstado);

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(KEY_ID_ESTADO, idEstado)
                    .addValue(KEY_SITUACAO, SITUACAO_ATIVA);

            StringBuilder query = new StringBuilder("select tpem.* ")
                    .append("  from tipo_emissao tpem ")
                    .append("     , estado_autorizador esau ")
                    .append(" where tpem.tpem_tp_emissao = esau.esau_tp_emissao ")
                    .append("   and tpem.tpem_situacao   = :situacao ")
                    .append("   and esau.esau_situacao   = :situacao ")
                    .append("   and esau.esau_id_estado  = :idEstado ")
                    .append(" order by tpem.tpem_tp_emissao ");

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, tipoEmissaoRowMapper);

        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar listAtivos", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public List<TipoEmissao> listByIdEstado(Map<String, String> parameters) throws Exception {
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(parameters);

            StringBuilder query = new StringBuilder()
                    .append("select tpem.* ")
                    .append("  from estado_autorizador esau ")
                    .append("  , tipo_emissao tpem ")
                    .append("  where tpem.tpem_tp_emissao = esau_tp_emissao ")
                    .append("  and esau.esau_tp_emissao <> '" + TipoEmissaoEnum.NORMAL.getCodigo() + "' ")
                    .append("  and esau.esau_id_estado = :idEstado ");

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, tipoEmissaoRowMapper);
        } catch (Exception e) {
            LOGGER.error("Erro de acesso aos dados", ExceptionUtils.getStackTrace(e),ExceptionUtils.getStackTrace(e) );
            throw e;
        }
    }

}
