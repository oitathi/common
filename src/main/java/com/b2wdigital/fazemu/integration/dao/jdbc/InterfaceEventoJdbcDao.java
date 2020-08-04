package com.b2wdigital.fazemu.integration.dao.jdbc;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.b2wdigital.fazemu.business.repository.InterfaceEventoRepository;
import com.b2wdigital.fazemu.domain.InterfaceEvento;
import com.b2wdigital.fazemu.enumeration.InterfaceEventoEnum;
import com.b2wdigital.fazemu.exception.FazemuDAOException;

@Repository
public class InterfaceEventoJdbcDao extends AbstractDao implements InterfaceEventoRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(InterfaceEventoJdbcDao.class);

    @Autowired
    @Qualifier("interfaceEventoRowMapper")
    private RowMapper<InterfaceEvento> interfaceEventoRowMapper;

    @Override
    public List<InterfaceEvento> listByFiltros(String idSistema, Long idMetodo, String chaveAcesso, Date dataHoraRegistroInicio, Date dataHoraRegistroFim, String situacao, Long quantidadeRegistros) {
        try {
            LOGGER.debug("InterfaceEventoJdbcDao: listByFiltros");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idSistema", idSistema)
                    .addValue("idMetodo", idMetodo)
                    .addValue("chaveAcesso", chaveAcesso)
                    .addValue("dataHoraRegistroInicio", dataHoraRegistroInicio)
                    .addValue("dataHoraRegistroFim", dataHoraRegistroFim)
                    .addValue("situacao", situacao)
                    .addValue("quantidadeRegistros", quantidadeRegistros);

            StringBuilder query = new StringBuilder("select inev.* ")
                    .append("  from (select inev.*, docu.docu_chave_acesso_env ")
                    .append("          from interface_evento inev, documento_fiscal docu ")
                    .append("         where docu.docu_id_doc_fiscal = inev.inev_id_doc_fiscal");

            if (idSistema != null) {
                query.append(" and inev.inev_id_sistema        = :idSistema ");
            }
            if (idMetodo != null) {
                query.append(" and inev.inev_id_metodo        = :idMetodo ");
            }
            if (chaveAcesso != null) {
                query.append(" and (docu.docu_chave_acesso_env     = :chaveAcesso ")
                        .append("    or  docu.docu_chave_acesso    = :chaveAcesso)");
            }
            if (situacao != null) {
                query.append(" and inev.inev_situacao          = :situacao ");
            }
            if (dataHoraRegistroInicio != null) {
                query.append(" and inev.inev_datahora_reg     >= :dataHoraRegistroInicio ");
            } else {
                query.append(" and inev.inev_datahora_reg     >= sysdate - 7 ");
            }
            if (dataHoraRegistroFim != null) {
                query.append(" and inev.inev_datahora_reg     <= :dataHoraRegistroFim ");
            }
            query.append(" order by inev.inev_id_evento desc) inev ")
                    .append(" where rownum                     <= nvl(:quantidadeRegistros, 50) ");

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, interfaceEventoRowMapper);
        } catch (DataAccessException e) {
            throw new FazemuDAOException(e.getMessage(), e);
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public InterfaceEvento findById(Long id) {
        LOGGER.debug("InterfaceEventoJdbcDao: findById");
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);

            StringBuilder query = new StringBuilder("select inev.*, '' docu_chave_acesso_env  ")
                    .append("  from interface_evento inev ")
                    .append(" where inev_id_evento = :id ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, interfaceEventoRowMapper);

        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar findById {}", id, e);
            throw new FazemuDAOException(e.getMessage(), e)
                    .addContextValue("id", id);
        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar findById {}", id, e);
            throw new FazemuDAOException(e.getMessage(), e)
                    .addContextValue("id", id);
        }
    }

    @Override
    public Long insert(InterfaceEvento ie) {
        LOGGER.debug("InterfaceEventoJdbcDao: insert");

        Integer sequence = nextVal("SEQ_INEV");

        try {
            LOGGER.debug("insert");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                    .addValue("id", sequence)
                    .addValue("idSistema", ie.getIdSistema())
                    .addValue("idMetodo", ie.getIdMetodo())
                    .addValue("tipoServico", ie.getTipoServico())
                    .addValue("idDocFiscal", ie.getIdDocFiscal())
                    .addValue("observacao", ie.getObservacao())
                    .addValue("situacao", ie.getSituacao())
                    .addValue("usuarioReg", ie.getUsuarioRegistro())
                    .addValue("usuario", ie.getUsuario());

            StringBuilder sql = new StringBuilder("insert into interface_evento(inev_id_evento, inev_id_sistema, inev_id_metodo, inev_tp_servico, inev_id_doc_fiscal, inev_obs, inev_situacao, inev_usuario_reg, inev_datahora_reg, inev_usuario, inev_datahora)")
                    .append(" values (:id, :idSistema, :idMetodo, :tipoServico, :idDocFiscal, :observacao, :situacao, :usuarioReg, sysdate, :usuario, sysdate)");

            namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);
        } catch (DuplicateKeyException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FazemuDAOException(e.getMessage(), e);
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FazemuDAOException(e.getMessage(), e);
        }

        return sequence.longValue();
    }

    @Override
    public int update(Long idInterfaceEvento, String observacao, String situacao, String usuario) {
        try {
            LOGGER.debug("update");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idInterfaceEvento", idInterfaceEvento)
                    .addValue("observacao", observacao)
                    .addValue("situacao", situacao)
                    .addValue("usuario", usuario);

            StringBuilder sql = new StringBuilder("update interface_evento ")
                    .append("   set inev_obs = substr(:observacao, 1, 3900), inev_situacao = :situacao, inev_usuario = :usuario, inev_datahora = sysdate ")
                    .append(" where inev_id_evento = :idInterfaceEvento ");

            return namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);

        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public int updateDataHora(Long idInterfaceEvento) {
        try {
            LOGGER.debug("update");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idInterfaceEvento", idInterfaceEvento);

            StringBuffer sql = new StringBuffer("update interface_evento ")
                    .append("   set inev_datahora = sysdate ")
                    .append(" where inev_id_evento = :idInterfaceEvento ");

            return namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);

        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public int deleteByIdDocFiscalAndDataHoraInicio(Long idDocFiscal) {
        try {
            LOGGER.info("delete deleteByIdDocFiscalAndDataHoraInicio {} ", idDocFiscal);
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idDocFiscal", idDocFiscal);

            StringBuilder sql = new StringBuilder("delete interface_evento ")
                    .append(" where inev_id_doc_fiscal = :idDocFiscal ")
                    .append("   and inev_datahora     <= sysdate - 90 ");

            return namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);

        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar deleteByIdDocFiscalAndDataHoraInicio ", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public List<InterfaceEvento> listByDateInterval(Date dataHoraRegistroInicio, Date dataHoraRegistroFim, String excludeList) {
        LOGGER.debug("InterfaceEventoJdbcDao: listByDateInterval");

        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("dataHoraRegistroInicio", dataHoraRegistroInicio)
                    .addValue("dataHoraRegistroFim", dataHoraRegistroFim);

            StringBuffer query = new StringBuffer("select inev.*, '' docu_chave_acesso_env ")
                    .append("  from interface_evento inev ")
                    .append(" where (inev_situacao = '" + InterfaceEventoEnum.ABERTO.getCodigo() + "' ")
                    .append("    or  inev_situacao = '" + InterfaceEventoEnum.ERRO.getCodigo() + "') ")
                    .append("   and inev_datahora_reg <= :dataHoraRegistroInicio ")
                    .append("   and inev_datahora_reg >= :dataHoraRegistroFim ");

            if (excludeList != null) {
                query.append("  and inev_id_evento not in (" + excludeList + ") ");
            }

            query.append(" order by inev.inev_situacao, inev.inev_datahora_reg ");

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, interfaceEventoRowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("Interface Evento nao encontrado para contingencia");
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

}
