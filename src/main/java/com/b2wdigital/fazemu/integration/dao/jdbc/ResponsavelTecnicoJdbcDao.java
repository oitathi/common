package com.b2wdigital.fazemu.integration.dao.jdbc;

import java.util.List;
import java.util.Map;

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

import com.b2wdigital.fazemu.business.repository.ResponsavelTecnicoRepository;
import com.b2wdigital.fazemu.domain.ResponsavelTecnico;
import com.b2wdigital.fazemu.exception.FazemuDAOException;

@Repository
public class ResponsavelTecnicoJdbcDao extends AbstractDao implements ResponsavelTecnicoRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponsavelTecnicoJdbcDao.class);

    @Autowired
    @Qualifier("responsavelTecnicoRowMapper")
    private RowMapper<ResponsavelTecnico> responsavelTecnicoRowMapper;

    @Override
    public List<ResponsavelTecnico> listAll() {
        try {
            LOGGER.debug("listAll");

            StringBuilder query = new StringBuilder("select rtec.* ")
                    .append("  from resp_tecnico rtec ");

            return namedParameterJdbcOperations.query(query.toString(), (SqlParameterSource) null, responsavelTecnicoRowMapper);

        } catch (DataAccessException e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public ResponsavelTecnico findByIdEmissorRaiz(Long raizCnpjEmitente) {
        try {
            LOGGER.debug("findByIdEmissorRaiz");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("raizCnpjEmitente", raizCnpjEmitente);

            StringBuilder query = new StringBuilder("select rtec.* ")
                    .append("  from resp_tecnico rtec ")
                    .append(" where rtec.rtec_id_emissor_raiz = :raizCnpjEmitente ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, responsavelTecnicoRowMapper);

        } catch (EmptyResultDataAccessException erdae) {
            LOGGER.error("Responsavel Tecnico nao encontrado com o raizCnpjEmitente {}", raizCnpjEmitente);
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar findByIdEmissorRaiz", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public List<ResponsavelTecnico> listByFiltros(Map<String, String> parameters) {
        try {
            LOGGER.debug("ResponsavelTecnicoJdbcDao: listByFiltros");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(parameters);

            StringBuilder query = new StringBuilder("select rtec.* from resp_tecnico rtec where 1=1");
                               
            if(parameters.get("idEmissor")!= null) {
                    query.append("	and rtec_id_emissor_raiz = nvl(:idEmissor, rtec_id_emissor_raiz) ");
            }

            if (parameters.get("situacao") != null) {
                query.append("	and rtec.rtec_situacao = :situacao ");
            }

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, responsavelTecnicoRowMapper);

        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("ResponsavelTecnicoJdbcDao: listByFiltros {} ", e);
            return null;
        } catch (DataAccessException e) {
            throw new FazemuDAOException(e.getMessage(), e);
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public void insert(ResponsavelTecnico responsavelTecnico) {
        LOGGER.debug("ResponsavelTecnicoJdbcDao: insert");

        Integer idResponsavelTecnico = nextVal("SEQ_RTEC");

        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                    .addValue("idResponsavelTecnico", idResponsavelTecnico)
                    .addValue("idEmissorRaiz", responsavelTecnico.getIdEmissorRaiz())
                    .addValue("cnpj", responsavelTecnico.getCnpj())
                    .addValue("contato", responsavelTecnico.getContato())
                    .addValue("email", responsavelTecnico.getEmail())
                    .addValue("telefone", responsavelTecnico.getTelefone())
                    .addValue("situacao", responsavelTecnico.getSituacao())
                    .addValue("usuario", responsavelTecnico.getUsuario());

            StringBuilder sql = new StringBuilder("insert into resp_tecnico(rtec_id_resp_tecnico, rtec_id_emissor_raiz, rtec_cnpj, rtec_contato, rtec_email, rtec_tel, rtec_situacao, rtec_usuario, rtec_datahora) ")
                    .append(" values (:idResponsavelTecnico, :idEmissorRaiz, :cnpj, :contato, :email, :telefone, :situacao, :usuario, sysdate)");

            namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);
        } catch (DuplicateKeyException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FazemuDAOException(e.getMessage(), e);
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public void update(ResponsavelTecnico responsavelTecnico) {
        try {
            LOGGER.debug("ResponsavelTecnicoJdbcDao: update");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                    .addValue("idResponsavelTecnico", responsavelTecnico.getIdResponsavelTecnico())
                    .addValue("idEmissorRaiz", responsavelTecnico.getIdEmissorRaiz())
                    .addValue("cnpj", responsavelTecnico.getCnpj())
                    .addValue("contato", responsavelTecnico.getContato())
                    .addValue("email", responsavelTecnico.getEmail())
                    .addValue("telefone", responsavelTecnico.getTelefone())
                    .addValue("situacao", responsavelTecnico.getSituacao())
                    .addValue("usuario", responsavelTecnico.getUsuario());

            StringBuilder sql = new StringBuilder("update resp_tecnico ")
                    .append(" set rtec_id_emissor_raiz = :idEmissorRaiz, rtec_cnpj = :cnpj, rtec_contato = :contato, rtec_email = :email, rtec_tel = :telefone, rtec_situacao = :situacao, rtec_usuario = :usuario, rtec_datahora = sysdate ")
                    .append(" where rtec_id_resp_tecnico = :idResponsavelTecnico ");

            namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

}
