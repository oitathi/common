package com.b2wdigital.fazemu.integration.dao.jdbc;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

/**
 * Abstract Dao.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
@Repository
public abstract class AbstractDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDao.class);

    @Autowired
    @Qualifier("namedParameterJdbcOperations")
    protected NamedParameterJdbcOperations namedParameterJdbcOperations;

    public Integer nextVal(String sequence) {
        LOGGER.debug("nextVal for sequence {}", sequence);

        StringBuilder query = new StringBuilder("select " + sequence + ".nextval ")
                .append(" from dual ");

        return namedParameterJdbcOperations.queryForObject(query.toString(), new HashMap<String, String>(), Integer.class);

    }

}
