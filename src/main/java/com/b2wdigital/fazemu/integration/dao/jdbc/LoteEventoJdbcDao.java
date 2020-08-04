package com.b2wdigital.fazemu.integration.dao.jdbc;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.b2wdigital.fazemu.business.repository.LoteEventoRepository;
import com.b2wdigital.fazemu.domain.LoteEvento;
import com.b2wdigital.fazemu.enumeration.PontoLoteEnum;
import com.b2wdigital.fazemu.exception.FazemuDAOException;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 *
 * @author dailton.almeida
 */
@Repository
public class LoteEventoJdbcDao extends AbstractDao implements LoteEventoRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoteEventoJdbcDao.class);

    @Autowired
    @Qualifier("loteEventoRowMapper")
    private RowMapper<LoteEvento> loteEventoRowMapper;

    @Override
    public int insert(Long idLote, String idPonto, String usuario, Long idXml, String observacao) {
        try {
            LOGGER.debug("insert");

            Integer idEvento = super.nextVal("SEQ_LOEV");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idEvento", idEvento)
                    .addValue("idLote", idLote)
                    .addValue("idPonto", idPonto)
                    .addValue("usuario", usuario)
                    .addValue("idXml", idXml)
                    .addValue("observacao", observacao);

            StringBuilder sql = new StringBuilder("insert into lote_evento (loev_id_evento, loev_id_lote, loev_id_ponto, loev_id_xml, loev_obs, loev_usuario, loev_datahora)  ")
                    .append(" values (:idEvento, :idLote, :idPonto, :idXml, :observacao, :usuario, sysdate) ");

            namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);

            return idEvento;
        } catch (DataAccessException dae) {
            throw new FazemuDAOException(dae.getMessage(), dae);
        }
    }

    @Override
    public List<LoteEvento> listByEstado(Long idEstado) {
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idEstado", idEstado);

            StringBuilder query = new StringBuilder("select loev.* ")
                    .append("  from lote_evento doev ")
                    .append("     , lote lote ")
                    .append(" where lote.lote_id_lote = loev.loev_id_lote ")
                    .append("   and lote.lote_id_estado = :idEstado ");

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, loteEventoRowMapper);
        } catch (DataAccessException e) {
            LOGGER.error("Erro ao tentar executar listByEstado", e);
            throw new FazemuDAOException(e.getMessage(), e)
                    .addContextValue("idEstado", idEstado);
        }
    }

    @Override
    public Long countByEstadoAndPeriodo(Long idEstado, Date periodo) {
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idEstado", idEstado)
                    .addValue("periodo", periodo);

            StringBuilder query = new StringBuilder("select count(loev.loev_id_evento) ")
                    .append("  from lote_evento loev ")
                    .append("     , lote lote ")
                    .append(" where lote.lote_id_lote = loev.loev_id_lote ")
                    .append("   and lote.lote_id_estado = :idEstado ")
                    .append("   and loev.loev_datahora <= sysdate ")
                    .append("   and loev.loev_datahora >= :periodo ")
                    .append("   and (loev.loev_id_ponto = '" + PontoLoteEnum.ERRO_FECHAR_LOTE.getCodigo() + "' ")
                    .append("    or  loev.loev_id_ponto = '" + PontoLoteEnum.ERRO_CONSULTAR_LOTE.getCodigo() + "') ")
                    .append("   and (upper(loev.loev_obs) like '%HTTP %' ")
                    .append("    or  upper(loev.loev_obs) like '%HTTP: %' ")
                    .append("    or  upper(loev.loev_obs) like '%SOCKETEXCEPTION%') ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, Long.class);
        } catch (DataAccessException e) {
            LOGGER.error("Erro ao tentar executar countByEstadoAndPeriodo", e);
            throw new FazemuDAOException(e.getMessage(), e)
                    .addContextValue("idEstado", idEstado);
        }
    }

    @Override
    public int delete(Long idEvento) {
        try {
            LOGGER.info("delete idEvento {} ", idEvento);
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idEvento", idEvento);

            StringBuilder sql = new StringBuilder("delete lote_evento ")
                    .append(" where loev_id_evento = :idEvento ");

            return namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);

        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar delete ", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public List<LoteEvento> listByIdLote(Long idLote) {
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                    .addValue("idLote", idLote);

            StringBuilder query = new StringBuilder(" select loev.* ")
                    .append("  from lote_evento loev ")
                    .append(" where loev.loev_id_lote = :idLote ");

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, loteEventoRowMapper);

        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("LoteEventoJdbcDao: listByIdLote {} ", e);
            return null;
        } catch (DataAccessException e) {
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

}
