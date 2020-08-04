package com.b2wdigital.fazemu.integration.dao.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.b2wdigital.fazemu.business.repository.AutorizadorServicoRepository;
import com.b2wdigital.fazemu.domain.AutorizadorServico;
import com.b2wdigital.fazemu.exception.FazemuDAOException;

/**
 * Autorizador Servico Dao.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
@Repository
public class AutorizadorServicoJdbcDao extends AbstractDao implements AutorizadorServicoRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutorizadorServicoJdbcDao.class);

    @Autowired
    @Qualifier("autorizadorServicoRowMapper")
    private RowMapper<AutorizadorServico> autorizadorServicoRowMapper;

    public AutorizadorServico findByIdAutorizadorAndIdServico(Long IdAutorizador, String idServico) {
        LOGGER.debug("AutorizadorServicoJdbcDao: findByIdAutorizadorAndIdServico");

        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idAutorizador", IdAutorizador)
                    .addValue("idServico", idServico.toUpperCase());

            StringBuilder query = new StringBuilder("select ause.* ")
                    .append("  from autorizador_servico ause ")
                    .append(" where ause.ause_id_autorizador = :idAutorizador ")
                    .append("   and upper(ause.ause_id_servico) = :idServico ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, autorizadorServicoRowMapper);

        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar findByIdAutorizadorAndIdServico", e);
            throw new FazemuDAOException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar findByIdAutorizadorAndIdServico", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

}
