
package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.Usuario;

@Component("usuarioRowMapper")
public class UsuarioRowMapper extends AbstractRowMapper implements RowMapper<Usuario> {
    public static final String ID   = "USUA_ID_USUARIO";
    public static final String NOME = "USUA_NOME";

    @Override
    public Usuario mapRow(ResultSet rs, int row) throws SQLException {
    	Usuario result = new Usuario();
        result.setId(rs.getString(ID));
        result.setNome(rs.getString(NOME));
        return result;
    }
}
