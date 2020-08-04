package com.b2wdigital.fazemu.integration.dao.jdbc;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.b2wdigital.fazemu.business.repository.DocumentoFiscalToStorageRepository;
import com.b2wdigital.fazemu.domain.DocumentoFiscalToStorage;
import com.b2wdigital.fazemu.enumeration.TipoServicoEnum;
import com.b2wdigital.fazemu.exception.FazemuDAOException;
import com.b2wdigital.fazemu.utils.FazemuUtils;

@Repository
public class DocumentoFiscalToStorageJdbcDao extends AbstractDao implements DocumentoFiscalToStorageRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoFiscalToStorageJdbcDao.class);

    @Autowired
    @Qualifier("documentoFiscalToStorageRowMapper")
    private RowMapper<DocumentoFiscalToStorage> documentoFiscalToStorageRowMapper;

    @Override
    public List<DocumentoFiscalToStorage> listToStorageByDateInterval(Date dataHoraInicio, Date dataHoraFim, String excludeList) {
        try {
            LOGGER.debug("DocumentoFiscalToStorageJdbcDao: listToStorageByDateInterval");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("tipoDocumentoFiscal", FazemuUtils.TIPO_DOCUMENTO_FISCAL_NFE.toUpperCase())
                    .addValue("dataHoraInicio", dataHoraInicio)
                    .addValue("dataHoraFim", dataHoraFim);

            StringBuilder query = new StringBuilder()
                    .append(" select docu.docu_id_doc_fiscal ")
                    .append("      , docu.docu_tp_doc_fiscal ")
                    .append("      , docu.docu_id_emissor ")
                    .append("      , docu.docu_id_destinatario ")
                    .append("      , docu.docu_nro_doc ")
                    .append("      , docu.docu_serie_doc ")
                    .append("      , docu.docu_ano ")
                    .append("      , docu.docu_dt_emissao_doc ")
                    .append("      , docu.docu_id_estado ")
                    .append("      , docu.docu_versao ")
                    .append("      , docu.docu_chave_acesso ")
                    .append("      , docu.docu_chave_acesso_env ")
                    .append("      , docu.docu_tp_emissao ")
                    .append("      , docu.docu_datahora_reg ")
                    .append("      , dret.dret_tp_servico ")
                    .append("      , dret.dret_tp_evento ")
                    .append("      , dret.dret_datahora_reg ")
                    .append("      , docl.docl_clob ")
                    .append("   from documento_fiscal docu ")
                    .append("      , documento_retorno dret ")
                    .append("      , documento_clob docl ")
                    .append("  where docu.docu_id_doc_fiscal  = dret.dret_id_doc_fiscal ")
                    .append("    and dret.dret_id_xml         = docl.docl_id_clob ")
                    .append("    and dret.dret_url            is null ")
                    .append("    and docu.docu_tp_doc_fiscal  = :tipoDocumentoFiscal ")
                    .append("    and docu.docu_datahora_reg  >= :dataHoraInicio ")
                    .append("    and docu.docu_datahora_reg  <= :dataHoraFim ")
                    .append("    and rownum <= 100 ");

            if (excludeList != null) {
                query.append("  and docu.docu_id_doc_fiscal not in (" + excludeList + ") ");
            }

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, documentoFiscalToStorageRowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("DocumentoFiscalToStorageJdbcDao: listToStorageByDateInterval {} ", e);
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e)
                    .addContextValue("dataHoraInicio", dataHoraInicio)
                    .addContextValue("dataHoraFim", dataHoraFim);
        }
    }

    @Override
    public DocumentoFiscalToStorage findToStorageByIdDocFiscalAndTipoServicoAndTipoEvento(Long idDocFiscal, String tipoServico, Long tipoEvento) {
        try {
            LOGGER.debug("DocumentoFiscalToStorageJdbcDao: findToStorageByIdDocFiscalAndTipoServicoAndTipoEvento");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idDocFiscal", idDocFiscal)
                    .addValue("tipoServico", tipoServico)
                    .addValue("tipoEvento", tipoEvento);

            StringBuilder query = new StringBuilder()
                    .append(" select docu.docu_id_doc_fiscal ")
                    .append("      , docu.docu_tp_doc_fiscal ")
                    .append("      , docu.docu_id_emissor ")
                    .append("      , docu.docu_id_destinatario ")
                    .append("      , docu.docu_nro_doc ")
                    .append("      , docu.docu_serie_doc ")
                    .append("      , docu.docu_ano ")
                    .append("      , docu.docu_dt_emissao_doc ")
                    .append("      , docu.docu_id_estado ")
                    .append("      , docu.docu_versao ")
                    .append("      , docu.docu_chave_acesso ")
                    .append("      , docu.docu_chave_acesso_env ")
                    .append("      , docu.docu_tp_emissao ")
                    .append("      , docu.docu_datahora_reg ")
                    .append("      , dret.dret_tp_servico ")
                    .append("      , dret.dret_tp_evento ")
                    .append("      , dret.dret_datahora_reg ")
                    .append("      , docl.docl_clob ")
                    .append("   from documento_fiscal docu ")
                    .append("      , documento_retorno dret ")
                    .append("      , documento_clob docl ")
                    .append("  where docu.docu_id_doc_fiscal = dret.dret_id_doc_fiscal ")
                    .append("    and dret.dret_id_xml        = docl.docl_id_clob ")
                    .append("    and docu.docu_id_doc_fiscal = :idDocFiscal ")
                    .append("    and dret.dret_tp_servico    = :tipoServico ");

            if (tipoEvento != null && tipoEvento != 0) {
                query.append(" and dret.dret_tp_evento = nvl(:tipoEvento, dret.dret_tp_evento) ")
                        .append("   and rownum = 1 ");
            }

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, documentoFiscalToStorageRowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("DocumentoFiscalToStorageJdbcDao: findToStorageByIdDocFiscalAndTipoServicoAndTipoEvento {} ", e);
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e)
                    .addContextValue("idDocFiscal", idDocFiscal)
                    .addContextValue("tipoServico", tipoServico)
                    .addContextValue("tipoEvento", tipoEvento);
        }
    }

    @Override
    public List<DocumentoFiscalToStorage> listToStorageByIdDocFiscalAndTipoServico(Long idDocFiscal, TipoServicoEnum tipoServico) {
        try {
            LOGGER.debug("DocumentoFiscalToStorageJdbcDao: listToStorageByIdDocFiscalAndTipoServico");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idDocFiscal", idDocFiscal)
                    .addValue("tipoServico", tipoServico.getTipoRetorno());

            StringBuilder query = new StringBuilder()
                    .append(" select docu.docu_id_doc_fiscal, ")
                    .append("      , docu.docu_tp_doc_fiscal ")
                    .append("      , docu.docu_id_emissor, ")
                    .append("      , docu.docu_id_destinatario, ")
                    .append("      , docu.docu_nro_doc, ")
                    .append("      , docu.docu_serie_doc, ")
                    .append("      , docu.docu_ano, ")
                    .append("      , docu.docu_dt_emissao_doc, ")
                    .append("      , docu.docu_id_estado, ")
                    .append("      , docu.docu_versao, ")
                    .append("      , docu.docu_chave_acesso, ")
                    .append("      , docu.docu_chave_acesso_env, ")
                    .append("      , docu.docu_tp_emissao, ")
                    .append("      , docu.docu_datahora_reg, ")
                    .append("      , dret.dret_tp_servico, ")
                    .append("      , dret.dret_tp_evento ")
                    .append("      , dret.dret_datahora_reg, ")
                    .append("      , docl.docl_clob ")
                    .append("   from documento_fiscal docu ")
                    .append("      , documento_retorno dret ")
                    .append("      , documento_clob docl ")
                    .append("  where docu.docu_id_doc_fiscal = dret.dret_id_doc_fiscal ")
                    .append("    and dret.dret_id_xml        = docl.docl_id_clob ")
                    .append("    and docu.docu_id_doc_fiscal = :idDocFiscal ")
                    .append("    and dret.dret_tp_servico    = :tipoServico ");

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, documentoFiscalToStorageRowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("DocumentoFiscalToStorageJdbcDao: listToStorageByIdDocFiscalAndTipoServico {} ", e);
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e)
                    .addContextValue("idDocFiscal", idDocFiscal)
                    .addContextValue("tipoServico", tipoServico);
        }
    }

}
