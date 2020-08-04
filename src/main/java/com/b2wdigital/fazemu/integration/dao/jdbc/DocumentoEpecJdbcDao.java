package com.b2wdigital.fazemu.integration.dao.jdbc;

import java.text.MessageFormat;
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
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.b2wdigital.fazemu.business.repository.DocumentoEpecRepository;
import com.b2wdigital.fazemu.business.repository.ParametrosInfraRepository;
import com.b2wdigital.fazemu.domain.DocumentoEpec;
import com.b2wdigital.fazemu.enumeration.SituacaoEnum;
import com.b2wdigital.fazemu.exception.FazemuDAOException;

/**
 * Documento Epec Dao.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
@Repository
public class DocumentoEpecJdbcDao extends AbstractDao implements DocumentoEpecRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoEpecJdbcDao.class);

    @Autowired
    private ParametrosInfraRepository parametrosInfraRepository;

    @Autowired
    @Qualifier("documentoEpecRowMapper")
    private RowMapper<DocumentoEpec> documentoEpecRowMapper;

    @Override
    public List<DocumentoEpec> listBySituacaoAndIdEstadoAndDataInicio(String tipoDocumentoFiscal, SituacaoEnum situacao, Long idEstado, Date dataHoraInicio, String excludeList) {
        try {
            LOGGER.debug("listBySituacaoAndIdEstadoAndDataInicio");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("tipoDocumentoFiscal", tipoDocumentoFiscal)
                    .addValue("situacao", situacao.getCodigo())
                    .addValue("idEstado", idEstado)
                    .addValue("dataHoraInicio", dataHoraInicio);

            StringBuilder query = new StringBuilder("select dpec.* ")
                    .append("  from documento_epec dpec ")
                    .append(" where dpec.dpec_tp_doc_fiscal = :tipoDocumentoFiscal ")
                    .append("   and dpec.dpec_situacao  = :situacao ")
                    .append("   and dpec.dpec_id_estado = :idEstado ")
                    .append("   and dpec.dpec_datahora <= :dataHoraInicio ");

            if (excludeList != null) {
                query.append("  and dpec.dpec_id_doc_fiscal not in (" + excludeList + ") ");
            }

            query.append(" order by dpec_datahora ");

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, documentoEpecRowMapper);
        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar listBySituacaoAndIdEstadoAndDataInicio", e);
            throw new FazemuDAOException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar listBySituacaoAndIdEstadoAndDataInicio", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public DocumentoEpec findByIdDocFiscal(Long idDocFiscal) {
        try {
            LOGGER.debug("findByIdDocFiscal");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                    .addValue("idDocFiscal", idDocFiscal);

            StringBuilder query = new StringBuilder(" select dpec.* ")
                    .append("  from documento_epec dpec ")
                    .append(" where dpec.dpec_id_doc_fiscal = :idDocFiscal ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, documentoEpecRowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Documento Epec nao encontrado com o idDocFiscal " + idDocFiscal);
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar findByIdDocFiscal", e);
            throw new FazemuDAOException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar findByIdDocFiscal", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public int insert(DocumentoEpec documentoEpec) {
        try {
            LOGGER.debug("insert");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                    .addValue("idDocumentoFiscal", documentoEpec.getIdDocumentoFiscal())
                    .addValue("idEstado", documentoEpec.getIdEstado())
                    .addValue("tipoDocumentoFiscal", documentoEpec.getTipoDocumentoFiscal())
                    .addValue("situacao", documentoEpec.getSituacao())
                    .addValue("observacao", documentoEpec.getObservacao())
                    .addValue("usuarioReg", documentoEpec.getUsuarioReg())
                    .addValue("usuario", documentoEpec.getUsuario());

            StringBuilder sql = new StringBuilder("insert into documento_epec (dpec_id_doc_fiscal, dpec_id_estado, dpec_tp_doc_fiscal, dpec_situacao, dpec_obs, dpec_usuario_reg, dpec_datahora_reg, dpec_usuario, dpec_datahora) ")
                    .append(" values (:idDocumentoFiscal, :idEstado, :tipoDocumentoFiscal, :situacao, :observacao, :usuarioReg, sysdate, :usuario, sysdate) ");

            return namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);

        } catch (DuplicateKeyException e) {
            LOGGER.error(e.getMessage(), e);
            return updateSituacao(documentoEpec.getIdDocumentoFiscal(), SituacaoEnum.ABERTO);
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            return updateSituacao(documentoEpec.getIdDocumentoFiscal(), SituacaoEnum.ABERTO);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new FazemuDAOException(e.getMessage(), e);
        }

    }

    @Override
    public int updateSituacao(Long idDocumentoFiscal, SituacaoEnum situacao) {
        try {
            LOGGER.debug("updateSituacao");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                    .addValue("situacao", situacao.getCodigo())
                    .addValue("idDocumentoFiscal", idDocumentoFiscal)
                    .addValue("usuario", parametrosInfraRepository.getAsString(null, ParametrosInfraRepository.PAIN_USUARIO_DEFAULT));

            StringBuilder sql = new StringBuilder("update documento_epec ")
                    .append("   set dpec_situacao = :situacao ")
                    .append("     , dpec_usuario  = :usuario ")
                    .append("     , dpec_datahora = sysdate ")
                    .append(" where dpec_id_doc_fiscal = :idDocumentoFiscal ");

            return namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);
        } catch (DataAccessException dae) {
            throw new FazemuDAOException(dae.getMessage(), dae)
                    .addContextValue("situacao", situacao.getDescricao())
                    .addContextValue("idDocumentoFiscal", idDocumentoFiscal);
        }
    }

    @Override
    public DocumentoEpec findByIdDocFiscalAndSituacao(Long idDocFiscal, SituacaoEnum situacao) {
        try {
            LOGGER.debug("findByIdDocFiscalAndSituacao");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                    .addValue("idDocFiscal", idDocFiscal)
                    .addValue("situacao", situacao.getCodigo());

            StringBuilder query = new StringBuilder(" select dpec.* ")
                    .append("  from documento_epec dpec ")
                    .append(" where dpec.dpec_id_doc_fiscal = :idDocFiscal ")
                    .append("   and dpec.dpec_situacao      = :situacao ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, documentoEpecRowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Documento Epec nao encontrado com o idDocFiscal " + idDocFiscal);
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar findByIdDocFiscalAndSituacao", e);
            throw new FazemuDAOException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar findByIdDocFiscalAndSituacao", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public List<DocumentoEpec> listByFiltros(Map<String, String> parameters) throws Exception {
        try {
            String query = buildQuery(parameters);
            LOGGER.debug("DocumentoEpecJdbcDao: listByFiltros");
            List<DocumentoEpec> lista = namedParameterJdbcOperations.query(query, (SqlParameterSource) null, documentoEpecRowMapper);
            return lista;

        } catch (Exception e) {
            LOGGER.info("EmissorRaizJdbcDao: listByFiltros {} ", e);
            throw new Exception("Erro na consulta");
        }
    }

    public String buildQuery(Map<String, String> parameters) throws Exception {
        if (parameters.size() < 5) {
            String query = "select dpec.* from documento_epec dpec where 1 = 1";
            String idEstado = parameters.get("idEstado");
            String situacao = parameters.get("situacao");
            String dataHoraRegistroInicio = parameters.get("dataHoraRegInicio");
            String dataHoraRegistroFim = parameters.get("dataHoraRegFim");

            StringBuilder sql = new StringBuilder(query)
                    .append(buildQueryIdEstado(idEstado))
                    .append(buildQuerySituacao(situacao))
                    .append(buildQueryDates(dataHoraRegistroInicio, dataHoraRegistroFim));

            return sql.toString();
        }
        throw new Exception("Filtros acima do permitido");
    }

    private String buildQueryIdEstado(String idEstado) {
        if (StringUtils.isNotEmpty(idEstado)) {
            return " AND DPEC_ID_ESTADO = " + idEstado;
        }
        return "";
    }

    private String buildQuerySituacao(String situacao) {
        if (StringUtils.isNotEmpty(situacao)) {
            return " AND DPEC_SITUACAO = '" + situacao + "'";
        }
        return "";
    }

    private String buildQueryDates(String dataHoraRegistroInicio, String dataHoraRegistroFim) {
        StringBuilder dataHoraRegistroBuilder = new StringBuilder();
        String toDate = "TO_DATE({0} {1} {2}, #DD/MM/YYYY HH24:MI:SS#) ";

        if (StringUtils.isNotBlank(dataHoraRegistroInicio) || StringUtils.isNotEmpty(dataHoraRegistroFim)) {
            dataHoraRegistroBuilder.append(" AND DPEC_DATAHORA_REG");
        }

        if (StringUtils.isNotBlank(dataHoraRegistroInicio)) {
            dataHoraRegistroBuilder.append(" >= ")
                    .append(MessageFormat.format(toDate, "'", dataHoraRegistroInicio, "'"));
        }

        if (StringUtils.isNotBlank(dataHoraRegistroInicio) && StringUtils.isNotEmpty(dataHoraRegistroFim)) {
            dataHoraRegistroBuilder.append(" AND DPEC_DATAHORA_REG");
        }

        if (StringUtils.isNotBlank(dataHoraRegistroFim)) {
            dataHoraRegistroBuilder.append(" <= ")
                    .append(MessageFormat.format(toDate, "'", dataHoraRegistroFim, "'"));
        }

        return dataHoraRegistroBuilder.toString().replaceAll("#", "'");
    }

}
