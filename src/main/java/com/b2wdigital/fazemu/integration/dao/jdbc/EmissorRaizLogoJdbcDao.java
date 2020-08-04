package com.b2wdigital.fazemu.integration.dao.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.b2wdigital.fazemu.business.repository.EmissorRaizLogoRepository;
import com.b2wdigital.fazemu.domain.EmissorRaizLogo;
import com.b2wdigital.fazemu.exception.FazemuDAOException;

/**
 *
 * @author dailton.almeida
 */
@Repository
public class EmissorRaizLogoJdbcDao extends AbstractDao implements EmissorRaizLogoRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmissorRaizLogoJdbcDao.class);

    @Autowired
    @Qualifier("emissorRaizLogoRowMapper")
    private RowMapper<EmissorRaizLogo> emissorRaizLogoRowMapper;

    @Override
    public EmissorRaizLogo findByIdEmissorRaizAndIdLogo(Long idEmissorRaiz, String idLogo) {
        try {
            LOGGER.debug("findByIdEmissorRaizAndIdLogo");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idEmissorRaiz", idEmissorRaiz)
                    .addValue("idLogo", idLogo);

            StringBuilder query = new StringBuilder("select elog.* ")
                    .append("  from emissor_raiz_logo elog ")
                    .append(" where elog_id_emissor_raiz = :idEmissorRaiz ")
                    .append("   and elog_id_logo = :idLogo ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, emissorRaizLogoRowMapper);

        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Logo nao encontrado com idEmissorRaiz " + idEmissorRaiz + " e idLogo " + idLogo);
            return null;
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }
}
