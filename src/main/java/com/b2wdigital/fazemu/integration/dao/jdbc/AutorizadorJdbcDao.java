package com.b2wdigital.fazemu.integration.dao.jdbc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.b2wdigital.fazemu.business.repository.AutorizadorRepository;
import com.b2wdigital.fazemu.domain.Autorizador;
import com.b2wdigital.fazemu.exception.FazemuDAOException;

/**
 * Autorizador Dao.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
@Repository
public class AutorizadorJdbcDao extends AbstractDao implements AutorizadorRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutorizadorJdbcDao.class);

    private static final String KEY_ID = "id";
    private static final String KEY_CODIGO_EXTERNO = "codigoExterno";
    private static final String KEY_SITUACAO = "situacao";
    private static final String SITUACAO_ATIVA = "A";

    @Autowired
    @Qualifier("autorizadorRowMapper")
    private RowMapper<Autorizador> autorizadorRowMapper;

    @Override
    public List<Autorizador> listAtivosByTipoDocumentoFiscal(String tipoDocumentoFiscal) {
        try {
            LOGGER.info("listAtivos");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(KEY_SITUACAO, SITUACAO_ATIVA)
                    .addValue("tipoDocumentoFiscal", tipoDocumentoFiscal);

            StringBuilder query = new StringBuilder("select autr.* ")
                    .append("  from autorizador autr ")
                    .append(" where autr.autr_tp_doc_fiscal = :tipoDocumentoFiscal ")
                    .append("   and autr.autr_situacao = :situacao ")
                    .append(" order by autr.autr_id_autorizador ");

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, autorizadorRowMapper);

        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar listAtivos", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public Autorizador findById(Long id) {
        try {
            LOGGER.debug("findById");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(KEY_ID, id);

            StringBuilder query = new StringBuilder("select autr.* ")
                    .append("  from autorizador autr ")
                    .append(" where autr.autr_id_autorizador = :id ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, autorizadorRowMapper);

        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar findById", e);
            throw new FazemuDAOException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar findById", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public Autorizador findByTipoDocumentoFiscalAndCodigoExterno(String tipoDocumentoFiscal, String codigoExterno) {
        try {
            LOGGER.debug("findByCodigoExterno");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("tipoDocumentoFiscal", tipoDocumentoFiscal)
                    .addValue(KEY_CODIGO_EXTERNO, codigoExterno);

            StringBuilder query = new StringBuilder("select autr.* ")
                    .append("  from autorizador autr ")
                    .append(" where autr.autr_tp_doc_fiscal = :tipoDocumentoFiscal ")
                    .append("   and autr.autr_cod_ext = :codigoExterno ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, autorizadorRowMapper);

        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar findByCodigoExterno", e);
            throw new FazemuDAOException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar findByCodigoExterno", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

}
