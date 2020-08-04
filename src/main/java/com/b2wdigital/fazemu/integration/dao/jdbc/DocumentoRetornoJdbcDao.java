package com.b2wdigital.fazemu.integration.dao.jdbc;

import java.util.List;
import java.util.Date;

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

import com.b2wdigital.fazemu.business.repository.DocumentoRetornoRepository;
import com.b2wdigital.fazemu.domain.DocumentoRetorno;
import com.b2wdigital.fazemu.enumeration.TipoServicoEnum;
import com.b2wdigital.fazemu.exception.FazemuDAOException;

/**
 *
 * @author dailton.almeida
 */
@Repository
public class DocumentoRetornoJdbcDao extends AbstractDao implements DocumentoRetornoRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoRetornoJdbcDao.class);

    @Autowired
    @Qualifier("documentoRetornoRowMapper")
    private RowMapper<DocumentoRetorno> documentoRetornoRowMapper;

    @Override
    public DocumentoRetorno findByIdDocFiscalAndTpServicoAndTpEvento(Long idDocFiscal, String tipoServico, Long tipoEvento) {
        try {
            LOGGER.debug("findByIdDocFiscalAndTpServicoAndTpEvento");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idDocFiscal", idDocFiscal)
                    .addValue("tipoServico", tipoServico)
                    .addValue("tipoEvento", tipoEvento);

            StringBuilder query = new StringBuilder("select dret.* ")
                    .append("  from documento_retorno dret ")
                    .append(" where dret.dret_id_doc_fiscal = :idDocFiscal ")
                    .append("   and dret.dret_tp_servico = :tipoServico ");

            if (tipoEvento != null && tipoEvento != 0) {
                query.append(" and dret.dret_tp_evento = nvl(:tipoEvento, dret.dret_tp_evento) ")
                        .append("   and rownum = 1 ");
            }

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, documentoRetornoRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            return null;
        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar findByIdDocFiscalAndTpServicoAndTpEvento ", e);
            throw new FazemuDAOException(e.getMessage(), e)
                    .addContextValue("idDocFiscal", idDocFiscal)
                    .addContextValue("tipoServico", tipoServico)
                    .addContextValue("tipoEvento", tipoEvento);
        }
    }

    @Override
    public List<DocumentoRetorno> findByIdDocFiscal(Long idDocFiscal) {
        try {
            LOGGER.debug("findByIdDocFiscal");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idDocFiscal", idDocFiscal);

            StringBuilder query = new StringBuilder("select dret.* ")
                    .append("  from documento_retorno dret ")
                    .append(" where dret.dret_id_doc_fiscal = :idDocFiscal ")
                    .append(" order by dret.dret_datahora_reg ");

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, documentoRetornoRowMapper);

        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar findByIdDocFiscal", e);
            throw new FazemuDAOException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar findByIdDocFiscal", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public int insert(Long idDocFiscal, String tipoServico, Long tipoEvento, Long idXml, String usuarioReg, String usuario) {
        try {
            LOGGER.info("insert idDocFiscal {}", idDocFiscal);
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idDocFiscal", idDocFiscal)
                    .addValue("tipoServico", tipoServico)
                    .addValue("tipoEvento", tipoEvento)
                    .addValue("idXml", idXml)
                    .addValue("usuarioReg", usuarioReg)
                    .addValue("usuario", usuario);

            StringBuilder sql = new StringBuilder(" insert into documento_retorno (dret_id_doc_fiscal, dret_tp_servico, dret_tp_evento, dret_id_xml, dret_usuario_reg, dret_datahora_reg, dret_usuario, dret_datahora) ")
                    .append(" values (:idDocFiscal, :tipoServico, :tipoEvento, :idXml, :usuarioReg, sysdate, :usuario, sysdate) ");

            return namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);

        } catch (DuplicateKeyException e) {
            LOGGER.error(e.getMessage(), e);
            return update(idDocFiscal, tipoServico, tipoEvento, idXml, usuario);
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            return update(idDocFiscal, tipoServico, tipoEvento, idXml, usuario);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public int update(Long idDocFiscal, String tipoServico, Long tipoEvento, Long idXml, String usuario) {
        try {
            LOGGER.info("update idDocFiscal {}", idDocFiscal);
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idDocFiscal", idDocFiscal)
                    .addValue("tipoServico", tipoServico)
                    .addValue("tipoEvento", tipoEvento)
                    .addValue("idXml", idXml)
                    .addValue("usuario", usuario);

            StringBuilder sql = new StringBuilder("update documento_retorno ")
                    .append("   set dret_id_xml = :idXml, dret_usuario = :usuario, dret_datahora = sysdate ")
                    .append(" where dret_id_doc_fiscal = :idDocFiscal and dret_tp_servico = :tipoServico ");

            if (tipoEvento != null) {
                sql.append(" and dret_tp_evento = :tipoEvento ");
            }

            return namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);

        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public List<DocumentoRetorno> listByDateIntervalAndNotExistsDocl(String tipoDocumentoFiscal, Date dataHoraInicio, Date dataHoraFim, String excludeList) {
        try {
            LOGGER.debug("DocumentoRetornoJdbcDao: listByDateIntervalAndNotExistsDocl");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("tipoDocumentoFiscal", tipoDocumentoFiscal)
                    .addValue("dataHoraInicio", dataHoraInicio)
                    .addValue("dataHoraFim", dataHoraFim);

            StringBuilder query = new StringBuilder()
                    .append("select dret.* ")
                    .append("  from documento_retorno dret ")
                    .append(" where dret.dret_datahora     <= :dataHoraInicio ")
                    .append("   and dret.dret_datahora     >= :dataHoraFim ")
                    .append("   and dret.dret_tp_servico   <> '" + TipoServicoEnum.INUTILIZACAO.getTipoRetorno() + "' ") //SEFAZ não disponibiliza o retorno
                    .append("   and not exists (select 1 ")
                    .append("                     from documento_clob docl ")
                    .append("                    where docl.docl_id_clob = dret.dret_id_xml) ")
                    .append("   and rownum < 100 ");

            if (excludeList != null) {
                query.append("  and dret.dret_id_doc_fiscal not in (" + excludeList + ") ");
            }

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, documentoRetornoRowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("DocumentoRetornoJdbcDao: listByDateIntervalAndNotExistsDocl {} ", e);
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e)
                    .addContextValue("tipoDocumentoFiscal", tipoDocumentoFiscal)
                    .addContextValue("dataHoraInicio", dataHoraInicio)
                    .addContextValue("dataHoraFim", dataHoraFim);
        }
    }

    @Override
    public List<DocumentoRetorno> listByDataHoraInicioAndNotExistsXML(Date dataHoraInicio, String excludeList) {
        try {
            LOGGER.debug("listByDataHoraInicioAndNotExistsXML");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("dataHoraInicio", dataHoraInicio);

            StringBuilder query = new StringBuilder()
                    .append("select dret.* ")
                    .append("  from documento_retorno dret ")
                    .append(" where dret.dret_datahora   <= :dataHoraInicio ")
                    .append("   and dret.dret_datahora   >= sysdate - 90 ")
                    .append("   and dret.dret_tp_servico  = '" + TipoServicoEnum.MANIFESTACAO.getTipoRetorno() + "' ")
                    .append("   and not exists (select 1 ")
                    .append("                     from documento_retorno dore")
                    .append("                    where dore.dret_id_doc_fiscal = dret.dret_id_doc_fiscal ")
                    .append("                      and dore.dret_tp_servico    = '" + TipoServicoEnum.AUTORIZACAO.getTipoRetorno() + "') ")
                    .append("   and rownum < 100 ");

            if (excludeList != null) {
                query.append("  and dret.dret_id_doc_fiscal not in (" + excludeList + ") ");
            }

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, documentoRetornoRowMapper);
        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar listByDataHoraInicioAndNotExistsXML", e);
            throw new FazemuDAOException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar listByDataHoraInicioAndNotExistsXML", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    //Storage
    @Override
    public int updateURL(Long idDocFiscal, String tipoServico, Long tipoEvento, String url, String usuario) {
        try {
            LOGGER.info("updateURL idDocFiscal {} tipoServico {} url {}", idDocFiscal, tipoServico, url);
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idDocFiscal", idDocFiscal)
                    .addValue("tipoServico", tipoServico)
                    .addValue("tipoEvento", tipoEvento)
                    .addValue("url", url)
                    .addValue("usuario", usuario);

            StringBuilder sql = new StringBuilder("update documento_retorno ")
                    .append("   set dret_url = :url, dret_usuario = :usuario, dret_datahora = sysdate ")
                    .append(" where dret_id_doc_fiscal = :idDocFiscal and dret_tp_servico = :tipoServico ");

            if (tipoEvento != null && tipoEvento != 0) {
                sql.append(" and dret_tp_evento = :tipoEvento ");
            }

            return namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);

        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public List<DocumentoRetorno> listByDateHoraInicio(Integer quantidadeDias, String excludeList) {
        try {
            LOGGER.debug("DocumentoRetornoJdbcDao: listByDateHoraInicio");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("quantidadeDias", quantidadeDias);

            StringBuilder query = new StringBuilder()
                    .append("select dret.* ")
                    .append("  from documento_retorno dret ")
                    .append(" where dret.dret_url is null ")
                    .append("   and dret.dret_datahora <= sysdate - :quantidadeDias ")
                    .append("   and rownum <= 100 ");

            if (excludeList != null) {
                query.append("  and dret.dret_id_doc_fiscal not in (" + excludeList + ") ");
            }

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, documentoRetornoRowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("DocumentoRetornoJdbcDao: listByDateHoraInicio - EmptyResultDataAccessException {} ", e);
            return null;
        } catch (DataAccessException e) {
            LOGGER.info("DocumentoRetornoJdbcDao: listByDateHoraInicio - DataAccessException {} ", e);
            return null;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

}
