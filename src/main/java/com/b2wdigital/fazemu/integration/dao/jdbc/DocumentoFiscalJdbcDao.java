package com.b2wdigital.fazemu.integration.dao.jdbc;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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

import com.b2wdigital.fazemu.business.repository.DocumentoFiscalRepository;
import com.b2wdigital.fazemu.business.repository.ParametrosInfraRepository;
import com.b2wdigital.fazemu.domain.DocumentoFiscal;
import com.b2wdigital.fazemu.enumeration.PontoDocumentoEnum;
import com.b2wdigital.fazemu.enumeration.SituacaoDocumentoEnum;
import com.b2wdigital.fazemu.enumeration.SituacaoEnum;
import com.b2wdigital.fazemu.exception.FazemuDAOException;
import com.b2wdigital.fazemu.integration.mapper.DocumentoFiscalManifRowMapper;
import com.b2wdigital.fazemu.utils.DateUtils;

/**
 *
 * @author dailton.almeida
 */
@Repository
public class DocumentoFiscalJdbcDao extends AbstractDao implements DocumentoFiscalRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoFiscalJdbcDao.class);

    public static final String KEY_ID = "id";
    public static final String KEY_ID_LOTE = "idLote";
    public static final String KEY_CHAVE_ACESSO = "chaveAcesso";

    @Autowired
    @Qualifier("documentoFiscalRowMapper")
    private RowMapper<DocumentoFiscal> documentoFiscalRowMapper;

    @Autowired
    private ParametrosInfraRepository parametrosInfraRepository;

    @Override
    public DocumentoFiscal findById(Long id) {
        LOGGER.debug("DocumentoFiscalJdbcDao: findById");
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(KEY_ID, id);

            StringBuilder query = new StringBuilder("select docu.* ")
                    .append("  from documento_fiscal docu ")
                    .append(" where docu_id_doc_fiscal = :id ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, documentoFiscalRowMapper);
        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar findById", e);
            throw new FazemuDAOException(e.getMessage(), e)
                    .addContextValue(KEY_ID, id);
        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar findById", e);
            throw new FazemuDAOException(e.getMessage(), e)
                    .addContextValue(KEY_ID, id);
        }
    }

    @Override
    public DocumentoFiscal findByChaveAcesso(String chaveAcesso) {
        LOGGER.debug("DocumentoFiscalJdbcDao: findByChaveAcesso + findByChaveAcessoEnviada");
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(KEY_CHAVE_ACESSO, chaveAcesso);

            StringBuilder query = new StringBuilder("select docu.* ")
                    .append("  from documento_fiscal docu ")
                    .append(" where docu.docu_chave_acesso_env = :chaveAcesso ")
                    .append("    or docu.docu_chave_acesso     = :chaveAcesso ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, documentoFiscalRowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("Documento Fiscal nao encontrado com a chave de acesso " + chaveAcesso);
            return null;
        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar findByChaveAcesso + findByChaveAcessoEnviada ", e);
            throw new FazemuDAOException(e.getMessage(), e)
                    .addContextValue(KEY_CHAVE_ACESSO, chaveAcesso);
        }
    }

    @Override
    public DocumentoFiscal findByDadosDocumentoFiscal(String tipoDocumentoFiscal, Long idEmissor, Long numeroDocumentoFiscal, Long serieDocumentoFiscal, Integer anoDocumentoFiscal, Long idEstado) {
        LOGGER.debug("DocumentoFiscalJdbcDao: findByDadosDocumentoFiscal");
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("tipoDocumentoFiscal", tipoDocumentoFiscal)
                    .addValue("idEmissor", idEmissor)
                    .addValue("numeroDocumentoFiscal", numeroDocumentoFiscal)
                    .addValue("serieDocumentoFiscal", serieDocumentoFiscal)
                    .addValue("anoDocumentoFiscal", anoDocumentoFiscal)
                    .addValue("idEstado", idEstado);

            StringBuilder query = new StringBuilder("select docu.* ")
                    .append("  from documento_fiscal docu ")
                    .append(" where docu_tp_doc_fiscal = :tipoDocumentoFiscal")
                    .append("   and docu_id_emissor = :idEmissor ")
                    .append("   and docu_nro_doc    = :numeroDocumentoFiscal ")
                    .append("   and docu_serie_doc  = :serieDocumentoFiscal ")
                    .append("   and docu_ano        = :anoDocumentoFiscal ")
                    .append("   and docu_id_estado  = :idEstado ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, documentoFiscalRowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Documento Fiscal nao encontrado com tipoDocumentoFiscal {} idEmissor {} numeroDocumentoFiscal {} serieDocumentoFiscal {} anoDocumentoFiscal {} idEstado {}", tipoDocumentoFiscal, idEmissor, numeroDocumentoFiscal, serieDocumentoFiscal, anoDocumentoFiscal, idEstado);
            return null;
        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar findByChaveAcesso", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public Long insert(DocumentoFiscal documentoFiscal) {
        LOGGER.debug("DocumentoFiscalJdbcDao: insert");

        Integer sequence = nextVal("SEQ_DOCU");

        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                    .addValue("id", sequence)
                    .addValue("tipoDocumentoFiscal", documentoFiscal.getTipoDocumentoFiscal())
                    .addValue("idEmissor", documentoFiscal.getIdEmissor())
                    .addValue("idDestinatario", documentoFiscal.getIdDestinatario())
                    .addValue("numeroDocumentoFiscal", documentoFiscal.getNumeroDocumentoFiscal())
                    .addValue("serieDocumentoFiscal", documentoFiscal.getSerieDocumentoFiscal())
                    .addValue("ano", documentoFiscal.getAnoDocumentoFiscal())
                    .addValue("numeroDocumentoFiscalExterno", documentoFiscal.getNumeroDocumentoFiscalExterno())
                    .addValue("dataEmissao", documentoFiscal.getDataHoraEmissao())
                    .addValue("idEstado", documentoFiscal.getIdEstado())
                    .addValue("idMunicipio", documentoFiscal.getIdMunicipio())
                    .addValue("versao", documentoFiscal.getVersao())
                    .addValue("chaveAcesso", documentoFiscal.getChaveAcesso())
                    .addValue("chaveAcessoEnviada", documentoFiscal.getChaveAcessoEnviada())
                    .addValue("tipoEmissao", documentoFiscal.getTipoEmissao())
                    .addValue("idPonto", documentoFiscal.getIdPonto())
                    .addValue("situacaoAutorizador", documentoFiscal.getSituacaoAutorizador())
                    .addValue("situacaoDocumento", documentoFiscal.getSituacaoDocumento())
                    .addValue("situacao", documentoFiscal.getSituacao())
                    .addValue("idSistema", documentoFiscal.getIdSistema())
                    .addValue("usuarioReg", documentoFiscal.getUsuarioReg())
                    .addValue("usuario", documentoFiscal.getUsuario());

            StringBuilder sql = new StringBuilder("insert into documento_fiscal (docu_id_doc_fiscal, docu_tp_doc_fiscal, docu_id_emissor, docu_id_destinatario, docu_nro_doc, docu_serie_doc, docu_ano, ")
                    .append(" docu_nro_doc_ext, docu_dt_emissao_doc, docu_id_estado, docu_id_municipio, docu_versao, docu_chave_acesso, docu_chave_acesso_env, docu_tp_emissao, docu_id_ponto, docu_situacao_autor, ")
                    .append(" docu_situacao_doc, docu_situacao, docu_id_sistema, docu_usuario_reg, docu_datahora_reg, docu_usuario, docu_datahora) ")
                    .append(" values (:id, :tipoDocumentoFiscal, :idEmissor, :idDestinatario, :numeroDocumentoFiscal, :serieDocumentoFiscal, :ano, ")
                    .append(" :numeroDocumentoFiscalExterno, :dataEmissao, :idEstado, :idMunicipio, :versao, :chaveAcesso, :chaveAcessoEnviada, :tipoEmissao, :idPonto, :situacaoAutorizador, ")
                    .append(" :situacaoDocumento, :situacao, :idSistema, :usuarioReg, sysdate, :usuario, sysdate) ");

            namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);

        } catch (DuplicateKeyException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FazemuDAOException(e.getMessage(), e);
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FazemuDAOException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new FazemuDAOException(e.getMessage(), e);
        }

        return sequence.longValue();

    }

    @Override
    public String getXmlByIdLoteAndIdDocFiscal(Long idLote, Long idDocFiscal) {
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(KEY_ID_LOTE, idLote)
                    .addValue("idDocFiscal", idDocFiscal);

            StringBuilder query = new StringBuilder("select docl.docl_clob ")
                    .append("  from documento_clob docl ")
                    .append("     , documento_lote dole ")
                    .append(" where docl_id_clob = dole_id_xml ")
                    .append("   and dole_id_doc_fiscal = :idDocFiscal ")
                    .append("   and dole_id_lote = :idLote ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, String.class);
        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar getXmlByIdLoteAndIdDocFiscal idLote {} idDocFiscal {}", idLote, idDocFiscal, e);
            throw new FazemuDAOException(e.getMessage(), e)
                    .addContextValue(KEY_ID_LOTE, idLote)
                    .addContextValue("idDocFiscal", idDocFiscal);
        }
    }

    @Override
    public int updatePontoAndSituacaoAndCstatAndTipoEmissaoAndSituacaoDocumento(Long idDocFiscal, String cStat, PontoDocumentoEnum pontoDocumento, SituacaoEnum situacao, Long tipoEmissao, String situacaoDocumento) {
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(KEY_ID, idDocFiscal)
                    .addValue("idPonto", pontoDocumento.getCodigo())
                    .addValue("cStat", cStat)
                    .addValue("tipoEmissao", tipoEmissao)
                    .addValue("situacaoDocumento", situacaoDocumento)
                    .addValue("situacao", situacao.getCodigo())
                    .addValue("usuario", parametrosInfraRepository.getAsString(null, ParametrosInfraRepository.PAIN_USUARIO_DEFAULT));

            StringBuilder sql = new StringBuilder("update documento_fiscal ")
                    .append("   set docu_id_ponto = :idPonto, docu_situacao_autor = :cStat, docu_situacao = :situacao, docu_usuario = :usuario, docu_datahora = sysdate ");

            if (tipoEmissao != null) {
                sql.append(" , docu_tp_emissao = :tipoEmissao ");
            }
            if (situacaoDocumento != null) {
                sql.append(" , docu_situacao_doc = :situacaoDocumento ");
            }

            sql.append(" where docu_id_doc_fiscal = :id");

            return namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);
        } catch (DataAccessException dae) {
            throw new FazemuDAOException(dae.getMessage(), dae)
                    .addContextValue(KEY_ID, idDocFiscal)
                    .addContextValue("pontoDocumento", pontoDocumento)
                    .addContextValue("cStat", cStat)
                    .addContextValue("tipoEmissao", tipoEmissao)
                    .addContextValue("situacaoDocumento", situacaoDocumento)
                    .addContextValue("situacao", situacao);
        }
    }

    @Override
    public int updatePontoAndChaveAcessoEnviadaAndTipoEmissaoAndSituacao(Long idDocFiscal, PontoDocumentoEnum pontoDocumento, String chaveAcessoEnviada, Long tipoEmissao, SituacaoEnum situacao) {
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(KEY_ID, idDocFiscal)
                    .addValue("idPonto", pontoDocumento.getCodigo())
                    .addValue("chaveAcessoEnviada", chaveAcessoEnviada)
                    .addValue("tipoEmissao", tipoEmissao)
                    .addValue("situacao", situacao.getCodigo())
                    .addValue("usuario", parametrosInfraRepository.getAsString(null, ParametrosInfraRepository.PAIN_USUARIO_DEFAULT));

            StringBuilder sql = new StringBuilder("update documento_fiscal ")
                    .append("   set docu_id_ponto = :idPonto, docu_chave_acesso_env = :chaveAcessoEnviada, docu_tp_emissao = :tipoEmissao, docu_situacao = :situacao, docu_usuario = :usuario, docu_datahora = sysdate ")
                    .append(" where docu_id_doc_fiscal = :id");

            return namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);
        } catch (DataAccessException dae) {
            throw new FazemuDAOException(dae.getMessage(), dae)
                    .addContextValue(KEY_ID, idDocFiscal)
                    .addContextValue("pontoDocumento", pontoDocumento)
                    .addContextValue("chaveAcessoEnviada", chaveAcessoEnviada)
                    .addContextValue("tipoEmissao", tipoEmissao)
                    .addContextValue("situacao", situacao);
        }
    }

    @Override
    public int updateNumeroDocumentoFiscalAndDataEmissaoAndChaveAcessoAndPontoAndSituacaoDocumentoAndSituacao(Long idDocFiscal, Long numeroDocumentoFiscal, Date dataEmissao, String chaveAcesso, PontoDocumentoEnum pontoDocumento, String situacaoDocumento, SituacaoEnum situacao) {
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(KEY_ID, idDocFiscal)
                    .addValue("numeroDocumentoFiscal", numeroDocumentoFiscal)
                    .addValue("dataEmissao", dataEmissao)
                    .addValue("chaveAcesso", chaveAcesso)
                    .addValue("idPonto", pontoDocumento.getCodigo())
                    .addValue("situacaoDocumento", situacaoDocumento)
                    .addValue("situacao", situacao.getCodigo())
                    .addValue("usuario", parametrosInfraRepository.getAsString(null, ParametrosInfraRepository.PAIN_USUARIO_DEFAULT));

            StringBuilder sql = new StringBuilder("update documento_fiscal ")
                    .append("   set docu_nro_doc = :numeroDocumentoFiscal, docu_dt_emissao_doc = :dataEmissao, docu_chave_acesso = :chaveAcesso, docu_chave_acesso_env = :chaveAcesso, docu_id_ponto = :idPonto, docu_situacao = :situacao, docu_usuario = :usuario, docu_datahora = sysdate ");

            if (situacaoDocumento != null) {
                sql.append(" , docu_situacao_doc = :situacaoDocumento ");
            }

            sql.append(" where docu_id_doc_fiscal = :id");

            return namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);
        } catch (DataAccessException dae) {
            throw new FazemuDAOException(dae.getMessage(), dae)
                    .addContextValue(KEY_ID, idDocFiscal)
                    .addContextValue("pontoDocumento", pontoDocumento)
                    .addContextValue("numeroDocumentoFiscal", numeroDocumentoFiscal)
                    .addContextValue("situacaoDocumento", situacaoDocumento)
                    .addContextValue("situacao", situacao);
        }
    }

    @Override
    public List<DocumentoFiscal> listByFiltros(Map<String, String> parameters) throws Exception {

        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(parameters);

        StringBuilder query = new StringBuilder()
                .append("select docu.* ")
                .append("     , nvl((select nvl(to_char(dret_tp_evento), 'S') ")
                .append("              from (select dret_tp_evento ")
                .append("                      from documento_retorno ")
                .append("                     where dret_id_doc_fiscal = docu_id_doc_fiscal ")
                .append("                       and dret_tp_servico    = 'MANF' ")
                .append("                     order by dret_datahora_reg desc) ")
                .append("             where rownum = 1), 'N') in_manif ")
                .append("     , (select dret_datahora ")
                .append("              from (select dret_datahora ")
                .append("                      from documento_retorno ")
                .append("                     where dret_id_doc_fiscal = docu_id_doc_fiscal ")
                .append("                       and dret_tp_servico    = 'MANF' ")
                .append("                     order by dret_datahora_reg desc) ")
                .append("             where rownum = 1) datahora_manif ")
                .append(" from (select * ")
                .append("         from documento_fiscal ")
                .append("        where docu_tp_doc_fiscal = :tipoDocumentoFiscal ")
                .append("          and exists (select 1 ")
                .append("                        from emissor_raiz emra")
                .append("                       where emra.emra_id_emissor_raiz = substr(lpad(docu_id_destinatario, 14, 0), 0, 8)) ");

        if (StringUtils.isNotBlank(parameters.get("idEmissor"))) {
            query.append(" and docu_id_emissor = :idEmissor ");
        }
        if (StringUtils.isNotBlank(parameters.get("idDestinatario"))) {
            query.append(" and docu_id_destinatario = :idDestinatario ");
        }
        if (StringUtils.isNotBlank(parameters.get("idEstado"))) {
            query.append(" and docu_id_estado = :idEstado ");
        }
        if (StringUtils.isNotBlank(parameters.get("chaveAcesso"))) {
            query.append(" and (docu_chave_acesso = nvl(:chaveAcesso, docu_chave_acesso) ")
                    .append(" or  docu_chave_acesso_env = nvl(:chaveAcesso, docu_chave_acesso_env)) ");
        }
        if (StringUtils.isNotBlank(parameters.get("numeroDocumentoFiscal"))) {
            query.append(" and docu_nro_doc = :numeroDocumentoFiscal ");
        }
        if (StringUtils.isNotBlank(parameters.get("numeroInicialDocumentoFiscal"))) {
            query.append("   and docu_nro_doc >= :numeroInicialDocumentoFiscal ");
        }
        if (StringUtils.isNotBlank(parameters.get("numeroFinalDocumentoFiscal"))) {
            query.append(" and docu_nro_doc <= :numeroFinalDocumentoFiscal ");
        }
        if (StringUtils.isNotBlank(parameters.get("serieDocumentoFiscal"))) {
            query.append(" and docu_serie_doc = :serieDocumentoFiscal ");
        }
        if (StringUtils.isNotBlank(parameters.get("tipoEmissao"))) {
            query.append(" and docu_tp_emissao = :tipoEmissao ");
        }
        if (StringUtils.isNotBlank(parameters.get("situacaoDocumento"))) {
            query.append(" and docu_situacao_doc = :situacaoDocumento ");
        }
        if (StringUtils.isNotBlank(parameters.get("situacao"))) {
            query.append(" and docu_situacao = :situacao ");
        }
        if (StringUtils.isNotBlank(parameters.get("dataHoraRegistroInicio"))) {
            Date dataHoraRegistroInicio = DateUtils.iso8601ToDate(parameters.get("dataHoraRegistroInicio"));
            sqlParameterSource.addValue("dataHoraRegistroInicio", dataHoraRegistroInicio);
            query.append(" and docu_datahora_reg >= :dataHoraRegistroInicio ");
        }
        if (StringUtils.isNotBlank(parameters.get("dataHoraRegistroFim"))) {
            Date dataHoraRegistroFim = DateUtils.iso8601ToDate(parameters.get("dataHoraRegistroFim"));
            sqlParameterSource.addValue("dataHoraRegistroFim", dataHoraRegistroFim);
            query.append(" and docu_datahora_reg <= :dataHoraRegistroFim ");
        }

        if (StringUtils.isNotBlank(parameters.get("manifestadoFilter"))) {
            query.append(" and nvl((select nvl(to_char(dret_tp_evento), 'S') ")
                    .append("         from (select dret_tp_evento ")
                    .append("                 from documento_retorno ")
                    .append("                where dret_id_doc_fiscal = docu_id_doc_fiscal ")
                    .append("                  and dret_tp_servico    = 'MANF' ")
                    .append("                order by dret_datahora_reg desc) ")
                    .append("        where rownum = 1), 'N') = :manifestadoFilter");
        }

        query.append("         order by docu_id_doc_fiscal desc) docu")
                .append(" where rownum <= nvl(:quantidadeRegistros, 50) ");

        return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, new DocumentoFiscalManifRowMapper());
    }

    @Override
    public List<DocumentoFiscal> listByFiltros(String tipoDocumentoFiscal,
            Long idEmissor,
            Long idDestinatario,
            Long idEstado,
            Long idMunicipio,
            String chaveAcesso,
            Long numeroDocumentoFiscal,
            Long numeroInicialDocumentoFiscal,
            Long numeroFinalDocumentoFiscal,
            Long serieDocumentoFiscal,
            Long numeroDocumentoFiscalExterno,
            Integer tipoEmissao,
            String situacaoDocumento,
            String situacao,
            Date dataHoraRegistroInicio,
            Date dataHoraRegistroFim,
            Long quantidadeRegistros) {
        try {
            LOGGER.debug("DocumentoFiscalJdbcDao: listByFiltros");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("tipoDocumentoFiscal", tipoDocumentoFiscal)
                    .addValue("idEmissor", idEmissor)
                    .addValue("idDestinatario", idDestinatario)
                    .addValue("idEstado", idEstado)
                    .addValue("idMunicipio", idMunicipio)
                    .addValue("chaveAcesso", chaveAcesso)
                    .addValue("numeroDocumentoFiscal", numeroDocumentoFiscal)
                    .addValue("numeroInicialDocumentoFiscal", numeroInicialDocumentoFiscal)
                    .addValue("numeroFinalDocumentoFiscal", numeroFinalDocumentoFiscal)
                    .addValue("serieDocumentoFiscal", serieDocumentoFiscal)
                    .addValue("numeroDocumentoFiscalExterno", numeroDocumentoFiscalExterno)
                    .addValue("tipoEmissao", tipoEmissao)
                    .addValue("situacaoDocumento", situacaoDocumento)
                    .addValue("situacao", situacao)
                    .addValue("dataHoraRegistroInicio", dataHoraRegistroInicio)
                    .addValue("dataHoraRegistroFim", dataHoraRegistroFim)
                    .addValue("quantidadeRegistros", quantidadeRegistros);

            StringBuilder query = new StringBuilder("select docu.* ")
                    .append("  from (select * ")
                    .append("          from documento_fiscal ")
                    .append("         where docu_tp_doc_fiscal = :tipoDocumentoFiscal ")
                    .append("           and exists (select 1 ")
                    .append("                         from emissor_raiz emra")
                    .append("                        where emra.emra_id_emissor_raiz = substr(lpad(docu_id_emissor, 14, 0), 0, 8)) ");

            if (idEmissor != null) {
                query.append("   and docu_id_emissor          = :idEmissor ");
            }
            if (idDestinatario != null) {
                query.append("   and docu_id_destinatario    = :idDestinatario ");
            }
            if (idEstado != null) {
                query.append("   and docu_id_estado          = :idEstado ");
            }
            if (idMunicipio != null) {
                query.append("   and docu_id_municipio       = :idMunicipio ");
            }
            if (chaveAcesso != null) {
                query.append("   and (docu_chave_acesso_env       = nvl(:chaveAcesso, docu_chave_acesso_env) ")
                        .append("    or  docu_chave_acesso   = nvl(:chaveAcesso, docu_chave_acesso)) ");
            }
            if (numeroDocumentoFiscal != null) {
                query.append("   and docu_nro_doc             = :numeroDocumentoFiscal ");
            }
            if (numeroInicialDocumentoFiscal != null) {
                query.append("   and docu_nro_doc            >= :numeroInicialDocumentoFiscal ");
            }
            if (numeroFinalDocumentoFiscal != null) {
                query.append("   and docu_nro_doc            <= :numeroFinalDocumentoFiscal ");
            }
            if (serieDocumentoFiscal != null) {
                query.append("   and docu_serie_doc           = :serieDocumentoFiscal ");
            }
            if (numeroDocumentoFiscalExterno != null) {
                query.append("   and docu_nro_doc_ext         = :numeroDocumentoFiscalExterno ");
            }
            if (tipoEmissao != null) {
                query.append("   and docu_tp_emissao         = :tipoEmissao ");
            }
            if (situacaoDocumento != null) {
                query.append("   and docu_situacao_doc        = :situacaoDocumento ");
            }
            if (situacao != null) {
                query.append("   and docu_situacao            = :situacao ");
            }
            if (dataHoraRegistroInicio != null) {
                query.append("   and docu_datahora_reg     >= :dataHoraRegistroInicio ");
            }
            if (dataHoraRegistroFim != null) {
                query.append("   and docu_datahora_reg     <= :dataHoraRegistroFim ");
            }
            query.append("         order by docu_id_doc_fiscal desc) docu")
                    .append(" where rownum <= nvl(:quantidadeRegistros, 50) ");

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, documentoFiscalRowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("DocumentoFiscalJdbcDao: listByFiltros {} ", e);
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public List<DocumentoFiscal> listByDateIntervalAndIdEstadoAndSituacao(String tipoDocumentoFiscal, Date dataHoraInicio, Date dataHoraFim, Long idEstado, SituacaoEnum situacao, String excludeList) {
        try {
            LOGGER.debug("DocumentoFiscalJdbcDao: listByDateIntervalAndIdEstadoAndSituacao");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("tipoDocumentoFiscal", tipoDocumentoFiscal)
                    .addValue("dataHoraInicio", dataHoraInicio)
                    .addValue("dataHoraFim", dataHoraFim)
                    .addValue("idEstado", idEstado)
                    .addValue("situacao", situacao.getCodigo());

            StringBuilder query = new StringBuilder()
                    .append("select docu.* ")
                    .append("  from documento_fiscal docu ")
                    .append(" where docu.docu_tp_doc_fiscal = :tipoDocumentoFiscal ")
                    .append("   and docu.docu_datahora     <= :dataHoraInicio ")
                    .append("   and docu.docu_datahora     >= :dataHoraFim ")
                    .append("   and docu.docu_id_estado     = :idEstado ")
                    .append("   and docu.docu_situacao      = :situacao ")
                    .append("   and rownum                 <= 100 ");

            if (excludeList != null) {
                query.append("  and docu.docu_id_doc_fiscal not in (" + excludeList + ") ");
            }

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, documentoFiscalRowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("DocumentoFiscalJdbcDao: listByDateIntervalAndIdEstadoAndSituacao {} ", e);
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e)
                    .addContextValue("tipoDocumentoFiscal", tipoDocumentoFiscal)
                    .addContextValue("dataHoraInicio", dataHoraInicio)
                    .addContextValue("dataHoraFim", dataHoraFim)
                    .addContextValue("idEstado", idEstado)
                    .addContextValue("situacao", situacao);
        }
    }

    @Override
    public List<DocumentoFiscal> listByDateIntervalAndIdEstadoAndSituacaoAutorizacao(String tipoDocumentoFiscal, Date dataHoraInicio, Date dataHoraFim, Long idEstado, String excludeList) {
        try {
            LOGGER.debug("DocumentoFiscalJdbcDao: listByDateIntervalAndIdEstadoAndSituacaoAutorizacao");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("tipoDocumentoFiscal", tipoDocumentoFiscal)
                    .addValue("dataHoraInicio", dataHoraInicio)
                    .addValue("dataHoraFim", dataHoraFim)
                    .addValue("idEstado", idEstado);

            StringBuilder query = new StringBuilder()
                    .append("select docu.* ")
                    .append("  from documento_fiscal docu ")
                    .append(" where docu.docu_tp_doc_fiscal   = :tipoDocumentoFiscal ")
                    .append("   and docu.docu_datahora       <= :dataHoraInicio ")
                    .append("   and docu.docu_datahora       >= :dataHoraFim ")
                    .append("   and docu.docu_id_estado       = :idEstado ")
                    .append("   and (docu.docu_situacao_autor = 204 ") //Duplicidade - TODO Verificar melhor maneira de incluir essa condicao
                    .append("    or  docu.docu_situacao_autor = 999 ") //Erro desconhecido - TODO Verificar melhor maneira de incluir essa condicao
                    .append("    or  docu.docu_situacao_autor = 109 ") //Sistema paralisado sem previsão - TODO Verificar melhor maneira de incluir essa condicao
                    .append("    or  docu.docu_situacao_autor = 142) ")//EPEC Bloqueado - TODO Verificar melhor maneira de incluir essa condicao
                    .append("   and rownum                   <= 100 ");

            if (excludeList != null) {
                query.append("  and docu.docu_id_doc_fiscal not in (" + excludeList + ") ");
            }

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, documentoFiscalRowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("DocumentoFiscalJdbcDao: listByDateIntervalAndIdEstadoAndSituacaoAutorizacao {} ", e);
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e)
                    .addContextValue("tipoDocumentoFiscal", tipoDocumentoFiscal)
                    .addContextValue("dataHoraInicio", dataHoraInicio)
                    .addContextValue("dataHoraFim", dataHoraFim)
                    .addContextValue("idEstado", idEstado);
        }
    }

    @Override
    public String getSituacaoDocumentoByIdDocFiscal(Long idDocFiscal) {
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idDocFiscal", idDocFiscal);

            StringBuilder query = new StringBuilder("select docu_situacao_doc ")
                    .append("  from documento_fiscal docu ")
                    .append(" where docu_id_doc_fiscal = :idDocFiscal ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e)
                    .addContextValue("idDocFiscal", idDocFiscal);
        }
    }

    @Override
    public int updateCstat(Long idDocFiscal, String cStat) {
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(KEY_ID, idDocFiscal)
                    .addValue("cStat", cStat)
                    .addValue("usuario", parametrosInfraRepository.getAsString(null, ParametrosInfraRepository.PAIN_USUARIO_DEFAULT));

            StringBuilder sql = new StringBuilder("update documento_fiscal ")
                    .append("   set docu_situacao_autor = :cStat, docu_usuario = :usuario, docu_datahora = sysdate")
                    .append(" where docu_id_doc_fiscal = :id");

            return namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);
        } catch (DataAccessException dae) {
            throw new FazemuDAOException(dae.getMessage(), dae)
                    .addContextValue(KEY_ID, idDocFiscal)
                    .addContextValue("cStat", cStat);
        }
    }

    @Override
    public int updateDataHora(Long idDocFiscal, Date dataHora) {
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(KEY_ID, idDocFiscal)
                    .addValue("dataHora", dataHora)
                    .addValue("usuario", parametrosInfraRepository.getAsString(null, ParametrosInfraRepository.PAIN_USUARIO_DEFAULT));

            StringBuilder sql = new StringBuilder("update documento_fiscal ")
                    .append("   set docu_usuario = :usuario")
                    .append("     , docu_datahora = :dataHora")
                    .append(" where docu_id_doc_fiscal = :id");

            return namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);
        } catch (DataAccessException dae) {
            throw new FazemuDAOException(dae.getMessage(), dae)
                    .addContextValue(KEY_ID, idDocFiscal)
                    .addContextValue("dataHora", dataHora);
        }
    }

    @Override
    public String getXMLResumoNFeByChaveAcesso(String chaveAcesso) {
        try {
            StringBuilder query = new StringBuilder("select docl.docl_clob ")
                    .append("  from documento_fiscal docu ")
                    .append("     , documento_clob docl ")
                    .append(" where (docu.docu_chave_acesso_env = :chaveAcesso ")
                    .append("    or  docu.docu_chave_acesso = :chaveAcesso )")
                    .append("   and docl.docl_id_clob = (select max(doev.doev_id_xml) ")
                    .append("                              from documento_evento doev ")
                    .append("                             where doev.doev_id_doc_fiscal = docu.docu_id_doc_fiscal ")
                    .append("                               and doev.doev_id_ponto = '" + PontoDocumentoEnum.RESUMO_NFE.getCodigo() + "') ");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(KEY_CHAVE_ACESSO, chaveAcesso);
            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, String.class);
        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar findByIdEmissorAndNumeroDocumentoFiscalExterno ", e);
            throw new FazemuDAOException(e.getMessage(), e).addContextValue(KEY_CHAVE_ACESSO, chaveAcesso);
        }
    }

    @Override
    public List<DocumentoFiscal> listByDateIntervalAndSituacaoAndNotExistsInev(String tipoDocumentoFiscal, Date dataHoraInicio, Date dataHoraFim, SituacaoEnum situacao, String excludeList) {
        try {
            LOGGER.debug("DocumentoFiscalJdbcDao: listByDateIntervalAndSituacaoAndNotExistsInev");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("tipoDocumentoFiscal", tipoDocumentoFiscal)
                    .addValue("dataHoraInicio", dataHoraInicio)
                    .addValue("dataHoraFim", dataHoraFim)
                    .addValue("situacao", situacao.getCodigo());

            StringBuilder query = new StringBuilder()
                    .append(" select docu.* ")
                    .append("  from documento_fiscal docu ")
                    .append(" where docu.docu_tp_doc_fiscal = :tipoDocumentoFiscal ")
                    .append("   and docu.docu_datahora     <= :dataHoraInicio ")
                    .append("   and docu.docu_datahora     >= :dataHoraFim ")
                    .append("   and docu.docu_situacao      = :situacao ")
                    .append("   and docu.docu_id_sistema is not null ")
                    .append("   and exists (select 1 ")
                    .append("                 from emissor_raiz emra")
                    .append("                where emra.emra_id_emissor_raiz = substr(lpad(docu.docu_id_emissor, 14, 0), 0, 8)) ")
                    .append("   and not exists (select 1 ")
                    .append("                     from interface_evento inev ")
                    .append("                    where inev.inev_id_doc_fiscal = docu.docu_id_doc_fiscal) ")
                    .append("   and rownum                 <= 100 ");

            if (excludeList != null) {
                query.append("  and docu.docu_id_doc_fiscal not in (" + excludeList + ") ");
            }

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, documentoFiscalRowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("DocumentoFiscalJdbcDao: listByDateIntervalAndSituacaoAndNotExistsInev {} ", e);
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e)
                    .addContextValue("tipoDocumentoFiscal", tipoDocumentoFiscal)
                    .addContextValue("dataHoraInicio", dataHoraInicio)
                    .addContextValue("dataHoraFim", dataHoraFim)
                    .addContextValue("situacao", situacao);
        }
    }

    @Override
    public List<DocumentoFiscal> listByDateIntervalAndSituacaoAndNotExistsDoev(String tipoDocumentoFiscal, Date dataHoraInicio, Date dataHoraFim, SituacaoEnum situacao, String excludeList) {
        try {
            LOGGER.debug("DocumentoFiscalJdbcDao: listByDateIntervalAndSituacaoAndNotExistsDoev");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("tipoDocumentoFiscal", tipoDocumentoFiscal)
                    .addValue("dataHoraInicio", dataHoraInicio)
                    .addValue("dataHoraFim", dataHoraFim)
                    .addValue("situacao", situacao.getCodigo());

            StringBuilder query = new StringBuilder()
                    .append(" select docu.* ")
                    .append("  from documento_fiscal docu ")
                    .append(" where docu.docu_tp_doc_fiscal  = :tipoDocumentoFiscal ")
                    .append("   and docu.docu_datahora      <= :dataHoraInicio ")
                    .append("   and docu.docu_datahora      >= :dataHoraFim ")
                    .append("   and docu.docu_situacao       = :situacao ")
                    .append("   and docu.docu_situacao_autor = 100 ")
                    .append("   and exists (select 1 ")
                    .append("                 from emissor_raiz emra")
                    .append("                where emra.emra_id_emissor_raiz = substr(lpad(docu.docu_id_emissor, 14, 0), 0, 8)) ")
                    .append("   and not exists (select 1 ")
                    .append("                     from documento_evento doev ")
                    .append("                    where doev.doev_id_doc_fiscal = docu.docu_id_doc_fiscal ")
                    .append("                      and doev.doev_situacao_autor is not null) ")
                    .append("   and rownum                 <= 100 ");

            if (excludeList != null) {
                query.append("  and docu.docu_id_doc_fiscal not in (" + excludeList + ") ");
            }

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, documentoFiscalRowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("DocumentoFiscalJdbcDao: listByDateIntervalAndSituacaoAndNotExistsDoev {} ", e);
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e)
                    .addContextValue("tipoDocumentoFiscal", tipoDocumentoFiscal)
                    .addContextValue("dataHoraInicio", dataHoraInicio)
                    .addContextValue("dataHoraFim", dataHoraFim)
                    .addContextValue("situacao", situacao);
        }
    }

    //NFSe
    @Override
    public DocumentoFiscal findByIdEmissorAndNumeroDocumentoFiscalExterno(String tipoDocumentoFiscal, Long idEmissor, Long numeroDocumentoFiscalExterno) {
        LOGGER.debug("DocumentoFiscalJdbcDao: findByIdEmissorAndNumeroDocumentoFiscalExterno");
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("tipoDocumentoFiscal", tipoDocumentoFiscal)
                    .addValue("idEmissor", idEmissor)
                    .addValue("numeroDocumentoFiscalExterno", numeroDocumentoFiscalExterno);

            StringBuilder query = new StringBuilder("select docu.* ")
                    .append("  from documento_fiscal docu ")
                    .append(" where docu.docu_tp_doc_fiscal = :tipoDocumentoFiscal ")
                    .append("   and docu.docu_id_emissor = :idEmissor ")
                    .append("   and docu.docu_nro_doc_ext = :numeroDocumentoFiscalExterno ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, documentoFiscalRowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("Documento Fiscal nao encontrado com idEmissor {} e numeroDocumentoFiscalExterno {}", idEmissor, numeroDocumentoFiscalExterno);
            return null;
        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar findByIdEmissorAndNumeroDocumentoFiscalExterno ", e);
            throw new FazemuDAOException(e.getMessage(), e)
                    .addContextValue(KEY_CHAVE_ACESSO, numeroDocumentoFiscalExterno);
        }
    }

    @Override
    public List<DocumentoFiscal> listByIdMunicipioAndSituacaoDocumentoAndSituacaoAutorizacaoAndDataInicio(String tipoDocumentoFiscal, Long idMunicipio, SituacaoDocumentoEnum situacaoDocumento, String situacaoAutorizacao, Date dataHoraInicio, String excludeList) {
        try {
            LOGGER.debug("listByIdMunicipioAndSituacaoDocumentoAndSituacaoAutorizacaoAndDataInicio");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("tipoDocumentoFiscal", tipoDocumentoFiscal)
                    .addValue("idMunicipio", idMunicipio)
                    .addValue("situacaoDocumento", situacaoDocumento.getCodigo())
                    .addValue("situacaoAutorizacao", situacaoAutorizacao)
                    .addValue("dataHoraInicio", dataHoraInicio);

            StringBuilder query = new StringBuilder("select docu.* ")
                    .append("  from documento_fiscal docu ")
                    .append(" where docu.docu_tp_doc_fiscal  = :tipoDocumentoFiscal ")
                    .append("   and docu.docu_id_municipio   = :idMunicipio ")
                    .append("   and docu.docu_situacao_doc   = :situacaoDocumento ")
                    .append("   and docu.docu_situacao_autor = :situacaoAutorizacao ")
                    .append("   and docu.docu_datahora      <= :dataHoraInicio ")
                    .append("   and rownum < 100 ");

            if (excludeList != null) {
                query.append("  and docu.docu_id_doc_fiscal not in (" + excludeList + ") ");
            }

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, documentoFiscalRowMapper);
        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar listByIdMunicipioAndSituacaoDocumentoAndSituacaoAutorizacaoAndDataInicio", e);
            throw new FazemuDAOException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar listByIdMunicipioAndSituacaoDocumentoAndSituacaoAutorizacaoAndDataInicio", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public List<DocumentoFiscal> listByIdMunicipioAndIdPontoAndDataInicio(String tipoDocumentoFiscal, Long idMunicipio, Date dataHoraInicio, String excludeList) {
        try {
            LOGGER.debug("listByIdMunicipioAndIdPontoAndDataInicio");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("tipoDocumentoFiscal", tipoDocumentoFiscal)
                    .addValue("idMunicipio", idMunicipio)
                    .addValue("dataHoraInicio", dataHoraInicio);

            StringBuilder query = new StringBuilder("select docu.* ")
                    .append("  from documento_fiscal docu ")
                    .append(" where docu.docu_tp_doc_fiscal  = :tipoDocumentoFiscal ")
                    .append("   and docu.docu_id_municipio   = :idMunicipio ")
                    .append("   and (docu.docu_id_ponto       = '" + PontoDocumentoEnum.RECIBO.getCodigo() + "' ")
                    .append("    or  docu.docu_id_ponto       = '" + PontoDocumentoEnum.ERRO_CONSULTAR_DOCUMENTO.getCodigo() + "') ")
                    .append("   and docu.docu_datahora      <= :dataHoraInicio ");

            if (excludeList != null) {
                query.append("  and docu.docu_id_doc_fiscal not in (" + excludeList + ") ");
            }

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, documentoFiscalRowMapper);
        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar listByIdMunicipioAndIdPontoAndDataInicio", e);
            throw new FazemuDAOException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar listByIdMunicipioAndIdPontoAndDataInicio", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public List<DocumentoFiscal> listByDataInicioAndIdMunicipioAndSituacao(String tipoDocumentoFiscal, Date dataHoraInicio, Long idMunicipio, SituacaoEnum situacao, String excludeList) {
        try {
            LOGGER.debug("DocumentoFiscalJdbcDao: listByDataInicioAndIdMunicipioAndSituacao");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("tipoDocumentoFiscal", tipoDocumentoFiscal)
                    .addValue("dataHoraInicio", dataHoraInicio)
                    .addValue("idMunicipio", idMunicipio)
                    .addValue("situacao", situacao.getCodigo());

            StringBuilder query = new StringBuilder()
                    .append("select docu.* ")
                    .append("  from documento_fiscal docu ")
                    .append(" where docu.docu_tp_doc_fiscal  = :tipoDocumentoFiscal ")
                    .append("   and docu.docu_datahora      <= :dataHoraInicio ")
                    .append("   and docu.docu_id_municipio   = :idMunicipio ")
                    .append("   and docu.docu_situacao       = :situacao ")
                    .append("   and docu.docu_situacao_autor is null ")
                    .append("   and docu.docu_situacao_doc  <> '" + SituacaoDocumentoEnum.REJEITADO.getCodigo() + "' ")
                    .append("   and (docu.docu_id_ponto     <> '" + PontoDocumentoEnum.RECIBO.getCodigo() + "' ")
                    .append("    or  docu.docu_id_ponto     <> '" + PontoDocumentoEnum.ERRO_CONSULTAR_DOCUMENTO.getCodigo() + "') ")
                    .append("   and rownum < 150 ");

            if (excludeList != null) {
                query.append("  and docu.docu_id_doc_fiscal not in (" + excludeList + ") ");
            }

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, documentoFiscalRowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("DocumentoFiscalJdbcDao: listByDataInicioAndIdMunicipioAndSituacao {} ", e);
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e)
                    .addContextValue("tipoDocumentoFiscal", tipoDocumentoFiscal)
                    .addContextValue("dataHoraInicio", dataHoraInicio)
                    .addContextValue("idMunicipio", idMunicipio)
                    .addContextValue("situacao", situacao);
        }
    }

}
