package com.b2wdigital.fazemu.integration.dao.jdbc;

import java.sql.Types;
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

import com.b2wdigital.fazemu.business.repository.DocumentoClobRepository;
import com.b2wdigital.fazemu.domain.DocumentoClob;
import com.b2wdigital.fazemu.enumeration.PontoDocumentoEnum;
import com.b2wdigital.fazemu.enumeration.SituacaoEnum;
import com.b2wdigital.fazemu.enumeration.TipoServicoEnum;
import com.b2wdigital.fazemu.exception.FazemuDAOException;

/**
 * Documento Clob Dao.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
@Repository
public class DocumentoClobJdbcDao extends AbstractDao implements DocumentoClobRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoClobJdbcDao.class);
    public static final String KEY_ID = "id";

    @Autowired
    @Qualifier("documentoClobRowMapper")
    private RowMapper<DocumentoClob> documentoClobRowMapper;

    @Override
    public DocumentoClob findById(Long id) {
        LOGGER.debug("DocumentoClobJdbcDao: findById");
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(KEY_ID, id);

            StringBuilder query = new StringBuilder("select docl.* ")
                    .append("  from documento_clob docl ")
                    .append(" where docl.docl_id_clob = :id ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, documentoClobRowMapper);

        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar findById", e);
            throw new FazemuDAOException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar findById", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public Long insert(DocumentoClob documentoClob) {
        LOGGER.debug("DocumentoClobJdbcDao: insert");

        Integer sequence = nextVal("SEQ_DOCL");

        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                    .addValue("id", sequence)
                    .addValue("clob", documentoClob.getClob(), Types.CLOB)
                    .addValue("usuario", documentoClob.getUsuario());

            StringBuilder sql = new StringBuilder("insert into documento_clob(docl_id_clob, docl_clob, docl_usuario, docl_datahora) ")
                    .append(" values(:id, :clob, :usuario, sysdate)");

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
    public int delete(Long idClob) {
        try {
            LOGGER.info("delete idClob {} ", idClob);
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idClob", idClob);

            StringBuilder sql = new StringBuilder("delete documento_clob ")
                    .append(" where docl_id_clob = :idClob ");

            return namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);

        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar delete ", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public DocumentoClob findByIdLoteAndIdDocFiscal(Long idLote, Long idDocFiscal) {
        LOGGER.debug("DocumentoClobJdbcDao: findByIdLoteAndIdDocFiscal");

        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idLote", idLote)
                    .addValue("idDocFiscal", idDocFiscal);

            StringBuilder query = new StringBuilder("select docl.* ")
                    .append("  from documento_clob docl ")
                    .append("     , documento_lote dole ")
                    .append(" where dole.dole_id_xml        = docl.docl_id_clob ")
                    .append("   and dole.dole_id_lote       = :idLote ")
                    .append("   and dole.dole_id_doc_fiscal = :idDocFiscal ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, documentoClobRowMapper);

        } catch (DataAccessException e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public DocumentoClob getLastXmlSignedByIdDocFiscal(Long idDocFiscal) {
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                    .addValue("idDocFiscal", idDocFiscal)
                    .addValue("idPonto", PontoDocumentoEnum.RECEBIDO_XML_SIGNED.getCodigo());

            StringBuilder query = new StringBuilder()
                    .append("select * ")
                    .append("  from (select docl.* ")
                    .append("          from documento_evento doev ")
                    .append("             , documento_clob docl ")
                    .append("         where doev.doev_id_xml        = docl.docl_id_clob ")
                    .append("           and doev.doev_id_doc_fiscal = :idDocFiscal ")
                    .append("           and doev.doev_id_ponto      = :idPonto ")
                    .append("      order by doev.doev_id_xml desc) ")
                    .append(" where rownum = 1 ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, documentoClobRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar getLastXmlSignedByIdDocFiscal", e);
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public DocumentoClob getLastXmlByIdDocFiscalAndPontoDocumento(Long idDocFiscal, PontoDocumentoEnum pontoDocumento) {
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                    .addValue("idDocFiscal", idDocFiscal)
                    .addValue("idPonto", pontoDocumento.getCodigo());

            StringBuilder query = new StringBuilder()
                    .append("select * ")
                    .append("  from (select docl.* ")
                    .append("          from documento_evento doev ")
                    .append("             , documento_clob docl ")
                    .append("         where doev.doev_id_xml        = docl.docl_id_clob ")
                    .append("           and doev.doev_id_doc_fiscal = :idDocFiscal ")
                    .append("           and doev.doev_id_ponto      = :idPonto ")
                    .append("      order by doev.doev_id_xml desc) ")
                    .append(" where rownum = 1 ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, documentoClobRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar getLastXmlByIdDocFiscalAndPontoDocumento", e);
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public String getXmlRetornoByIdDocFiscalAndTipoServico(Long idDocFiscal, TipoServicoEnum tipoServico) {
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idDocFiscal", idDocFiscal);

            StringBuilder query = new StringBuilder("select docl.docl_clob ")
                    .append("  from documento_retorno dret ")
                    .append("     , documento_clob    docl")
                    .append(" where docl.docl_id_clob       = dret.dret_id_xml ")
                    .append("   and dret.dret_id_doc_fiscal = :idDocFiscal ")
                    .append("   and dret.dret_tp_servico    = '" + tipoServico.getTipoRetorno() + "' ");

            if (TipoServicoEnum.MANIFESTACAO.equals(tipoServico)) {
                query.append("   and dret.dret_id_xml = (select max(dret_id_xml) ")
                        .append("                          from documento_retorno ")
                        .append("                         where dret_id_doc_fiscal = dret.dret_id_doc_fiscal ")
                        .append("                           and dret_tp_servico    = dret.dret_tp_servico) ");
            }

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, String.class);

        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar getXmlRetornoByIdDocFiscalAndTipoServico", e);
            throw new FazemuDAOException(e.getMessage(), e).addContextValue("idDocFiscal", idDocFiscal);
        }
    }

    @Override
    public String getMaxXmlEventoByIdDocFiscalAndTipoServico(Long idDocFiscal, TipoServicoEnum tipoServico) {
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idDocFiscal", idDocFiscal);

            StringBuilder query = new StringBuilder("select docl.docl_clob ")
                    .append("  from documento_evento doev ")
                    .append("     , documento_clob   docl")
                    .append(" where docl.docl_id_clob   = doev.doev_id_xml ")
                    .append("   and doev.doev_id_evento = (select max(doev_id_evento) ")
                    .append("                                from documento_evento ")
                    .append("                               where doev_id_doc_fiscal = :idDocFiscal ")
                    .append("                                 and doev_tp_servico    = '" + tipoServico.getTipoRetorno() + "' ")
                    .append("                                 and doev_id_ponto      = '" + PontoDocumentoEnum.PROCESSADO.getCodigo() + "') ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, String.class);

        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar getMaxXmlEventoByIdDocFiscalAndTipoServico", e);
            throw new FazemuDAOException(e.getMessage(), e).addContextValue("idDocFiscal", idDocFiscal);
        }
    }

    @Override
    public String getXmlCartaCorrecaoEnviadoByChaveAcesso(String chaveAcesso) {
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("chaveAcesso", chaveAcesso);

            StringBuilder query = new StringBuilder("select docl_clob ")
                    .append("  from documento_clob ")
                    .append(" where docl_id_clob = (select doev.doev_id_xml ")
                    .append("                         from documento_fiscal docu ")
                    .append("                            , documento_evento doev ")
                    .append("                        where doev.doev_id_doc_fiscal = docu.docu_id_doc_fiscal")
                    .append("                          and doev.doev_id_ponto      = '" + PontoDocumentoEnum.CARTA_CORRECAO.getCodigo() + "'")
                    .append("                          and doev.doev_id_evento     = (select max(deve.doev_id_evento) ")
                    .append("                                                           from documento_evento deve ")
                    .append("                                                          where deve.doev_id_doc_fiscal = doev.doev_id_doc_fiscal ")
                    .append("                                                            and deve.doev_id_ponto      = doev.doev_id_ponto) ")
                    .append("                          and (docu.docu_chave_acesso_env = :chaveAcesso ")
                    .append("                           or  docu.docu_chave_acesso     = :chaveAcesso) )");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, String.class);

        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar getXmlCartaCorrecaoEnviadoByChaveAcesso", e);
            throw new FazemuDAOException(e.getMessage(), e).addContextValue("chaveAcesso", chaveAcesso);
        }
    }

    @Override
    public Integer countEventosByChaveAcessoAndTipoServico(String chaveAcesso, String tipoServico) {
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("chaveAcesso", chaveAcesso)
                    .addValue("tipoServico", tipoServico);

            StringBuilder query = new StringBuilder(" select count(*) ")
                    .append("  from documento_fiscal docu ")
                    .append("     , documento_evento doev ")
                    .append(" where docu.docu_id_doc_fiscal     = doev.doev_id_doc_fiscal ")
                    .append("   and doev_tp_servico             = :tipoServico ")
                    .append("   and (docu.docu_chave_acesso_env = :chaveAcesso ")
                    .append("    or  docu.docu_chave_acesso     = :chaveAcesso) ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, Integer.class);

        } catch (Exception e) {
            LOGGER.error("Erro countSituacaoAutorizacaoById ", e);
            return 0;
        }
    }

    public List<DocumentoClob> listByDateIntervalToStorage(Date dataHoraInicio, Date dataHoraFim) {
        try {
            LOGGER.debug("DocumentoClobJdbcDao: listByDateIntervalToStorage");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                    .addValue("dataHoraInicio", dataHoraInicio)
                    .addValue("dataHoraFim", dataHoraFim);

            StringBuilder query = new StringBuilder()
                    .append(" select docu.* ")
                    .append(" from documento_fiscal docu ")
                    .append(" where docu.docu_datahora <= :dataHoraInicio ")
                    .append(" and docu.docu_datahora   >= :dataHoraFim ")
                    .append(" and docu.docu_situacao   = '" + SituacaoEnum.LIQUIDADO.getCodigo() + "' ")
                    .append(" order by docu.docu_datahora ");

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, documentoClobRowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("DocumentoFiscalJdbcDao: listByDateIntervalAndSituacaoAndIdEstadoOrCStat {} ", e);
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e)
                    .addContextValue("dataHoraInicio", dataHoraInicio)
                    .addContextValue("dataHoraFim", dataHoraFim);
        }
    }

}
