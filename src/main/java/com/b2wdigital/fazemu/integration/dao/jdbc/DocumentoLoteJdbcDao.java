package com.b2wdigital.fazemu.integration.dao.jdbc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.b2wdigital.fazemu.business.repository.DocumentoLoteRepository;
import com.b2wdigital.fazemu.domain.DocumentoLote;
import com.b2wdigital.fazemu.exception.FazemuDAOException;

/**
 * Documento Lote Dao.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
@Repository
public class DocumentoLoteJdbcDao extends AbstractDao implements DocumentoLoteRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoLoteJdbcDao.class);

    public static final String KEY_ID_DOCUMENTO_FISCAL = "idDocumentoFiscal";
    public static final String KEY_ID_XML = "idXML";
    public static final String KEY_ID_LOTE = "idLote";
    public static final String KEY_USUARIO = "usuario";

    @Autowired
    @Qualifier("documentoLoteRowMapper")
    private RowMapper<DocumentoLote> documentoLoteRowMapper;

    @Override
    public DocumentoLote findByDocumentoFiscalAndLote(Long idDocumentoFiscal, Long idLote) {
        LOGGER.debug("DocumentoLoteJdbcDao: findByDocumentoFiscalAndLote");
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(KEY_ID_DOCUMENTO_FISCAL, idDocumentoFiscal)
                    .addValue(KEY_ID_LOTE, idLote);

            StringBuilder query = new StringBuilder("select dole.* ")
                    .append("  from documento_lote dole ")
                    .append(" where dole.dole_id_doc_fiscal = :idDocumentoFiscal ")
                    .append("   and dole.dole_id_lote       = :idLote ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, documentoLoteRowMapper);

        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar findByDocumentoFiscalAndLote", e);
            throw new FazemuDAOException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar findByDocumentoFiscalAndLote", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public int insert(DocumentoLote dole) {
        try {
            LOGGER.debug("DocumentoLoteJdbcDao: insert");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(KEY_ID_DOCUMENTO_FISCAL, dole.getIdDocumentoFiscal())
                    .addValue(KEY_ID_XML, dole.getIdXml())
                    .addValue(KEY_ID_LOTE, dole.getIdLote())
                    .addValue(KEY_USUARIO, dole.getUsuario());

            StringBuilder sql = new StringBuilder("insert into documento_lote (dole_id_doc_fiscal, dole_id_xml, dole_id_lote, dole_usuario, dole_datahora) ")
                    .append(" values (:idDocumentoFiscal, :idXML, :idLote, :usuario, sysdate) ");

            return namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);
        } catch (DuplicateKeyException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FazemuDAOException(e.getMessage(), e);
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public int delete(Long idDocFiscal) {
        try {
            LOGGER.info("delete idDocFiscal {} ", idDocFiscal);
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idDocFiscal", idDocFiscal);

            StringBuilder sql = new StringBuilder("delete documento_lote ")
                    .append(" where dole_id_doc_fiscal = :idDocFiscal ");

            return namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);

        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar delete ", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public List<Long> listByIdDocFiscal(Long idLote) {
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(KEY_ID_LOTE, idLote);

            StringBuilder query = new StringBuilder("select dole.dole_id_doc_fiscal ")
                    .append("  from documento_lote dole ")
                    .append(" where dole.dole_id_lote = :idLote ");

            return namedParameterJdbcOperations.queryForList(query.toString(), sqlParameterSource, Long.class);

        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar listByIdDocFiscal", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public List<DocumentoLote> listByDataHoraInicioAndExistsLote(String excludeList) {
        try {
            LOGGER.debug("listByDataHoraInicioAndExistsLote");

            StringBuilder query = new StringBuilder("select dole.* ")
                    .append("  from documento_lote dole ")
                    .append(" where dole.dole_datahora      <= sysdate - 90 ")
                    .append("   and exists (select 1 ")
                    .append("                 from lote ")
                    .append("                where lote_id_lote = dole.dole_id_lote) ")
                    .append("   and rownum < 500 ");

            if (excludeList != null) {
                query.append("  and dole.dole_id_doc_fiscal not in (" + excludeList + ") ");
            }

            return namedParameterJdbcOperations.query(query.toString(), documentoLoteRowMapper);
        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar listByDataHoraInicioAndExistsLote", e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar listByDataHoraInicioAndExistsLote", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

}
