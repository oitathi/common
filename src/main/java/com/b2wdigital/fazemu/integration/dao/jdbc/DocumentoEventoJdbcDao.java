package com.b2wdigital.fazemu.integration.dao.jdbc;

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

import com.b2wdigital.fazemu.business.repository.DocumentoEventoRepository;
import com.b2wdigital.fazemu.domain.DocumentoEvento;
import com.b2wdigital.fazemu.enumeration.CodigoRetornoAutorizadorEnum;
import com.b2wdigital.fazemu.enumeration.PontoDocumentoEnum;
import com.b2wdigital.fazemu.exception.FazemuDAOException;

/**
 * Documento Evento Dao.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
@Repository
public class DocumentoEventoJdbcDao extends AbstractDao implements DocumentoEventoRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoEventoJdbcDao.class);

    @Autowired
    @Qualifier("documentoEventoRowMapper")
    private RowMapper<DocumentoEvento> documentoEventoRowMapper;

    @Override
    public DocumentoEvento findByIdEvento(Long idEvento) {
        try {
            LOGGER.debug("findByIdEvento");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idEvento", idEvento);

            StringBuilder query = new StringBuilder("select doev.* ")
                    .append("  from documento_evento doev ")
                    .append(" where doev.doev_id_evento = :idEvento ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, documentoEventoRowMapper);

        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar findByIdEvento", e);
            throw new FazemuDAOException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar findByIdEvento", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public List<DocumentoEvento> listByIdDocFiscal(Long idDocFiscal) {
        try {
            LOGGER.debug("listByIdDocFiscal");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idDocFiscal", idDocFiscal);

            StringBuilder query = new StringBuilder("select doev.* ")
                    .append("  from documento_evento doev ")
                    .append(" where doev.doev_id_doc_fiscal = :idDocFiscal ")
                    .append(" order by doev.doev_id_evento desc ");

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, documentoEventoRowMapper);

        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar listByIdDocFiscal", e);
            throw new FazemuDAOException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar listByIdDocFiscal", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public DocumentoEvento findByIdDocFiscalAndSituacaoAutorizacao(Long idDocFiscal, String situacaoAutorizacao) {
        try {
            LOGGER.debug("findByIdDocFiscalAndSituacaoAutorizacao");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idDocFiscal", idDocFiscal)
                    .addValue("situacaoAutorizacao", situacaoAutorizacao);

            StringBuilder query = new StringBuilder("select doev.* ")
                    .append("  from documento_evento doev ")
                    .append(" where doev.doev_id_doc_fiscal  = :idDocFiscal ")
                    .append("   and doev.doev_situacao_autor = :situacaoAutorizacao ")
                    .append("   and rownum = 1 ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, documentoEventoRowMapper);

        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            return null;
        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar findByIdDocFiscalAndSituacaoAutorizacao", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public DocumentoEvento findMaxEventoAprovadoByIdDocFiscal(Long idDocFiscal) {
        try {
            LOGGER.debug("findMaxEventoAprovadoByIdDocFiscal");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idDocFiscal", idDocFiscal);

            StringBuilder query = new StringBuilder("select * ")
                    .append("  from documento_evento ")
                    .append(" where doev_id_evento = (select max(doev.doev_id_evento) ")
                    .append("                           from documento_evento doev ")
                    .append("                              , codigo_ret_autorizador crau ")
                    .append("                          where crau.crau_id_cod       = doev.doev_situacao_autor ")
                    .append("                            and doev.doev_id_doc_fiscal = :idDocFiscal")
                    .append("                            and doev.doev_id_ponto      = '" + PontoDocumentoEnum.PROCESSADO.getCodigo() + "' ")
                    .append("                            and crau.crau_sit_autoriz   = '" + CodigoRetornoAutorizadorEnum.AUTORIZADO_FINALIZADO.getCodigo() + "') ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, documentoEventoRowMapper);

        } catch (EmptyResultDataAccessException e) {
            return findMaxEventoByIdDocFiscalAndTpServico(idDocFiscal, null);
        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar findMaxEventoAprovadoByIdDocFiscal", e);
            throw new FazemuDAOException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar findMaxEventoAprovadoByIdDocFiscal", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public DocumentoEvento findMaxEventoByIdDocFiscalAndTpServico(Long idDocFiscal, String tipoServico) {
        try {
            LOGGER.debug("findMaxEventoByIdDocFiscal");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idDocFiscal", idDocFiscal)
                    .addValue("tipoServico", tipoServico);

            StringBuilder query = new StringBuilder("select * ")
                    .append("  from documento_evento ")
                    .append(" where doev_id_evento = (select max(doev.doev_id_evento) ")
                    .append("                           from documento_evento doev ")
                    .append("                          where doev.doev_id_doc_fiscal = :idDocFiscal")
                    .append("                            and doev.doev_tp_servico    = :tipoServico ")
                    .append("                            and doev.doev_id_ponto      = '" + PontoDocumentoEnum.PROCESSADO.getCodigo() + "') ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, documentoEventoRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar findMaxEventoByIdDocFiscal", e);
            throw new FazemuDAOException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar findMaxEventoByIdDocFiscal", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public void insert(DocumentoEvento documentoEvento) {
        try {
            LOGGER.debug("insert");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                    .addValue("id", nextVal("SEQ_DOEV"))
                    .addValue("idDocumentoFiscal", documentoEvento.getIdDocumentoFiscal())
                    .addValue("idPonto", documentoEvento.getIdPonto())
                    .addValue("tipoServico", documentoEvento.getTipoServico())
                    .addValue("situacaoAutorizador", documentoEvento.getSituacaoAutorizador())
                    .addValue("idXml", documentoEvento.getIdXml())
                    .addValue("usuario", documentoEvento.getUsuario());

            StringBuilder sql = new StringBuilder("insert into documento_evento (doev_id_evento, doev_id_doc_fiscal, doev_id_ponto, doev_tp_servico, doev_situacao_autor, doev_id_xml, doev_usuario, doev_datahora) ")
                    .append("values (:id, :idDocumentoFiscal, :idPonto, :tipoServico, :situacaoAutorizador, :idXml, :usuario, sysdate)");

            namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);

        } catch (DuplicateKeyException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FazemuDAOException(e.getMessage(), e);
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FazemuDAOException(e.getMessage(), e);
        }

    }

}
