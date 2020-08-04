package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Abstract RowMapper.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
public class AbstractRowMapper {

    protected Integer getIntOrNull(ResultSet rs, String columnLabel) throws SQLException {
        int aux = rs.getInt(columnLabel);
        return rs.wasNull() ? null : aux;
    }

    protected Long getLongOrNull(ResultSet rs, String columnLabel) throws SQLException {
        long aux = rs.getLong(columnLabel);
        return rs.wasNull() ? null : aux;
    }

}
