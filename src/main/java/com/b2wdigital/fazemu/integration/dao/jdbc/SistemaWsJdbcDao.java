package com.b2wdigital.fazemu.integration.dao.jdbc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.b2wdigital.fazemu.business.repository.SistemaWsRepository;
import com.b2wdigital.fazemu.domain.SistemaWs;
import com.b2wdigital.fazemu.exception.FazemuDAOException;

@Repository
@CacheConfig(cacheManager = "cacheManager", cacheNames = "TTL5", keyGenerator = "keyGenerator")
public class SistemaWsJdbcDao extends AbstractDao implements SistemaWsRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(SistemaWsJdbcDao.class);

    @Autowired
    @Qualifier("sistemaWsRowMapper")
    private RowMapper<SistemaWs> sistemaWsRowMapper;

    @Override
    @Cacheable
    public List<SistemaWs> listAll() {
        try {
            LOGGER.debug("listAll");

            StringBuilder query = new StringBuilder("select siws.* ")
                    .append("  from sistema_ws siws ")
                    .append(" order by siws.siws_id_sistema, siws.siws_id_metodo ");

            return namedParameterJdbcOperations.query(query.toString(), (SqlParameterSource) null, sistemaWsRowMapper);

        } catch (DataAccessException e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public List<SistemaWs> listByTipoDocumentoFiscalAndIdSistemaAtivoAndTipoServico(String tipoDocumentoFiscal, String idSistema, String tipoServico) {
        try {
            LOGGER.debug("listByIdSistemaAtivoAndTipoServico");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("tipoDocumentoFiscal", tipoDocumentoFiscal)
                    .addValue("idSistema", idSistema)
                    .addValue("tipoServico", tipoServico);

            StringBuilder query = new StringBuilder("select siws.* ")
                    .append("  from sistema_ws siws ")
                    .append(" where siws.siws_situacao = 'A' ")
                    .append("   and siws.siws_tp_doc_fiscal = :tipoDocumentoFiscal ")
                    .append("   and siws.siws_id_sistema = :idSistema ")
                    .append("   and siws.siws_tp_servico = :tipoServico ");

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, sistemaWsRowMapper);

        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Sistema WS nao encontrado com o id Sistema {} Tipo Servico {} ", idSistema, tipoServico);
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

}
