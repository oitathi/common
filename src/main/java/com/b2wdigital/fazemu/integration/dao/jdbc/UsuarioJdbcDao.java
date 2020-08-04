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

import com.b2wdigital.fazemu.business.repository.UsuarioRepository;
import com.b2wdigital.fazemu.domain.Usuario;
import com.b2wdigital.fazemu.exception.FazemuDAOException;

/**
 *
 * @author dailton.almeida
 */
@Repository
public class UsuarioJdbcDao extends AbstractDao implements UsuarioRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioJdbcDao.class);

    @Autowired
    @Qualifier("usuarioRowMapper")
    private RowMapper<Usuario> usuarioRowMapper;

    @Override
    public Usuario findById(String id) {
        try {
            LOGGER.debug("findById");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);

            StringBuilder query = new StringBuilder("select usua.* ")
                    .append("  from adm_usuarios usua ")
                    .append(" where usua.usua_id_usuario = :id ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, usuarioRowMapper);

        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Usuario nao encontrado com o id {}", id);
            return null;
        } catch (DataAccessException e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }
}
