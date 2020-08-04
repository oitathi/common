package com.b2wdigital.fazemu.integration.dao.jdbc;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.b2wdigital.fazemu.business.repository.EstadoTipoEmissaoRepository;
import com.b2wdigital.fazemu.domain.EstadoTipoEmissao;
import com.b2wdigital.fazemu.exception.FazemuDAOException;

@Repository("EstadoTipoEmissaoJdbcDao") //resolve ambiguidade contra versao cacheada
@CacheConfig(cacheManager = "cacheManager", cacheNames = "TTL5", keyGenerator = "keyGenerator")
public class EstadoTipoEmissaoJdbcDao extends AbstractDao implements EstadoTipoEmissaoRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(EstadoTipoEmissaoJdbcDao.class);

    @Autowired
    @Qualifier("estadoTipoEmissaoRowMapper")
    private RowMapper<EstadoTipoEmissao> estadoTipoEmissaoRowMapper;

    @Override
    @Cacheable
    public List<EstadoTipoEmissao> listAll() {
        try {
            LOGGER.debug("listAll");

            StringBuilder query = new StringBuilder("select este.* ")
                    .append("  from estado_tp_emissao este ")
                    .append(" order by este_dt_ini desc ")
                    .append("     , este_dt_fim desc ");

            return namedParameterJdbcOperations.query(query.toString(), (SqlParameterSource) null, estadoTipoEmissaoRowMapper);

        } catch (DataAccessException e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public List<EstadoTipoEmissao> listByEstadoAndTipoEmissao(Long idEstado, Integer tipoEmissao) {
        try {
            LOGGER.debug("listByEstadoAndTipoEmissao");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idEstado", idEstado)
                    .addValue("tipoEmissao", tipoEmissao);

            StringBuilder query = new StringBuilder("select este.* ")
                    .append("     , esta.esta_id_uf ")
                    .append("     , tpem.tpem_nome ")
                    .append("  from estado_tp_emissao este ")
                    .append("     , estado esta ")
                    .append("     , tipo_emissao tpem ")
                    .append(" where este.este_id_estado  = esta.esta_id_estado ")
                    .append("   and tpem.tpem_tp_emissao = este.este_tp_emissao ");

            if (idEstado != null) {
                query.append("   and este.este_id_estado = :idEstado ");
            }

            if (tipoEmissao != null) {
                query.append("   and este.este_tp_emissao = :tipoEmissao ");
            }

            query.append(" order by este.este_id_estado ");

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, estadoTipoEmissaoRowMapper);

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<EstadoTipoEmissao> listByIdEstado(Long idEstado) {
        try {
            LOGGER.debug("listByIdEstado");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idEstado", idEstado);

            StringBuilder query = new StringBuilder("select este.* ")
                    .append("  from estado_tp_emissao este ")
                    .append(" where este_id_estado = :idEstado ");

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, estadoTipoEmissaoRowMapper);

        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Estado Tipo Emissao nao encontrado com o id Estado {}", idEstado);
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public EstadoTipoEmissao findByTipoEmissao(Long tipoEmissao) {
        try {
            LOGGER.debug("findByTipoEmissao");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("tipoEmissao", tipoEmissao);

            StringBuilder query = new StringBuilder("select este.* ")
                    .append("  from estado_tp_emissao este ")
                    .append(" where este_tp_emissao = :tipoEmissao ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, estadoTipoEmissaoRowMapper);

        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("Estado Tipo Emissao nao encontrado com o Tipo Emissao {} ", tipoEmissao);
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public EstadoTipoEmissao findByCodigoIBGETipoEmissaoAndDataHora(Integer codigoIbge, Integer tipoEmissao, Date dataHora) {
        try {
            LOGGER.debug("findByTipoEmissaoAndDataHora");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("codigoIbge", codigoIbge)
                    .addValue("tipoEmissao", tipoEmissao)
                    .addValue("dataHora", dataHora);

            StringBuilder query = new StringBuilder("select este.* ")
                    .append("  from estado_tp_emissao este ")
                    .append("     , estado esta ")
                    .append(" where este.este_id_estado = esta.esta_id_estado ")
                    .append("   and esta.esta_cod_ibge = :codigoIbge ")
                    .append("   and este_tp_emissao = :tipoEmissao ")
                    .append("   and este_dt_ini <= :dataHora ")
                    .append("   and este_dt_fim >= :dataHora ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, estadoTipoEmissaoRowMapper);

        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("Estado Tipo Emissao nao encontrado com o Tipo Emissao {} e Data/Hora {} ", tipoEmissao, dataHora);
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public List<EstadoTipoEmissao> listByFiltros(Long idEstado, Date dataHoraInicio, Date dataHoraFim, Integer tipoEmissao) {
        try {
            LOGGER.debug("ImpressoraJdbcDao: listByFiltros");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idEstado", idEstado)
                    .addValue("dataHoraInicio", dataHoraInicio)
                    .addValue("dataHoraFim", dataHoraFim)
                    .addValue("tipoEmissao", tipoEmissao);

            StringBuilder query = new StringBuilder("select este.* ")
                    .append("  from estado_tp_emissao este ")
                    .append(" where este.este_id_estado = nvl(:idEstado, este.este_id_estado) ");

            if (dataHoraInicio != null) {
                query.append("  and este.este_dt_ini     >= :dataHoraInicio ");
            }
            if (dataHoraFim != null) {
                query.append("  and este.este_dt_fim    <= :dataHoraFim ");
            }
            if (tipoEmissao != null) {
                query.append("  and este.este_tp_emissao = :tipoEmissao ");
            }
            query.append(" order by este.este_dt_ini desc ");

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, estadoTipoEmissaoRowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("estadoTipoEmissaoJdbcDao: listByFiltros {} ", e);
            return null;
        } catch (DataAccessException e) {
            throw new FazemuDAOException(e.getMessage(), e);
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public List<EstadoTipoEmissao> listByTipoEmissaoAtivo() {
        try {
            LOGGER.debug("EstadoTipoEmissaoJdbcDao: listByTipoEmissaoAtivo");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();

            StringBuilder query = new StringBuilder("select este.* ")
                    .append("  from estado_tp_emissao este ")
                    .append(" where sysdate ")
                    .append("between este.este_dt_ini ")
                    .append("   and este.este_dt_fim ");

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, estadoTipoEmissaoRowMapper);
        } catch (DataAccessException e) {
            throw new FazemuDAOException(e.getMessage(), e);
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public void insert(EstadoTipoEmissao este) {
        try {
            LOGGER.debug("insert");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                    .addValue("idEstado", este.getIdEstado())
                    .addValue("dataInicio", este.getDataInicio())
                    .addValue("dataFim", este.getDataFim())
                    .addValue("tipoEmissao", este.getTipoEmissao())
                    .addValue("justificativa", este.getJustificativa())
                    .addValue("usuario", este.getUsuario());

            StringBuilder sql = new StringBuilder("insert into estado_tp_emissao(este_id_estado, este_dt_ini, este_dt_fim, este_tp_emissao, este_justificativa, este_usuario, este_datahora)")
                    .append(" values (:idEstado, :dataInicio, :dataFim, :tipoEmissao, :justificativa, :usuario, sysdate)");

            namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);
        } catch (DuplicateKeyException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FazemuDAOException(e.getMessage(), e);
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public int update(EstadoTipoEmissao estadoTipoEmissao) {
        try {
            LOGGER.debug("update");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idEstado", estadoTipoEmissao.getIdEstado())
                    .addValue("dataInicio", estadoTipoEmissao.getDataInicio())
                    .addValue("dataFim", estadoTipoEmissao.getDataFim())
                    .addValue("tipoEmissao", estadoTipoEmissao.getTipoEmissao())
                    .addValue("justificativa", estadoTipoEmissao.getJustificativa())
                    .addValue("usuario", estadoTipoEmissao.getUsuario());

            StringBuilder sql = new StringBuilder("update estado_tp_emissao  ")
                    .append("   set este_dt_fim = :dataFim, este_justificativa = :justificativa, este_usuario = :usuario, este_datahora = sysdate ")
                    .append(" where este_id_estado = :idEstado and este_dt_ini = :dataInicio and este_tp_emissao = :tipoEmissao ");

            return namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);

        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }
}
