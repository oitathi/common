package com.b2wdigital.fazemu.integration.dao.jdbc;

import java.util.List;

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

import com.b2wdigital.fazemu.business.repository.ImpressoraRepository;
import com.b2wdigital.fazemu.domain.Impressora;
import com.b2wdigital.fazemu.exception.FazemuDAOException;

@Repository
public class ImpressoraJdbcDao extends AbstractDao implements ImpressoraRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImpressoraJdbcDao.class);

    @Autowired
    @Qualifier("impressoraRowMapper")
    private RowMapper<Impressora> impressoraRowMapper;

    @Override
    public List<Impressora> listAll() {
        try {
            LOGGER.debug("listAll");

            StringBuilder query = new StringBuilder("select impr.* ")
                    .append("  from impressora impr ")
                    .append(" order by impr_nome ");

            return namedParameterJdbcOperations.query(query.toString(), (SqlParameterSource) null, impressoraRowMapper);

        } catch (DataAccessException e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public List<Impressora> listByFiltros(String nome, String local, String ip, String marca, String modelo, String situacao) {
        try {
            LOGGER.debug("ImpressoraJdbcDao: listByFiltros");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("nome", "%" + nome + "%")
                    .addValue("local", "%" + local + "%")
                    .addValue("ip", "%" + ip + "%")
                    .addValue("marca", "%" + marca + "%")
                    .addValue("modelo", "%" + modelo + "%")
                    .addValue("situacao", situacao);

            StringBuilder query = new StringBuilder("select impr.* ")
                    .append("   from impressora impr ")
                    .append("  where impr_situacao = nvl(:situacao, impr_situacao) ");

            if (nome != null) {
                query.append("   and lower(impr_nome) like lower(:nome) ");
            }
            if (local != null) {
                query.append("   and lower(impr_local) like lower(:local) ");
            }
            if (ip != null) {
                query.append("   and impr_ip like :ip ");
            }
            if (marca != null) {
                query.append("   and lower(impr_marca) like lower(:marca) ");
            }
            if (modelo != null) {
                query.append("   and lower(impr_modelo) like lower(:modelo) ");
            }

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, impressoraRowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("impressoraJdbcDao: listByFiltros {} ", e);
            return null;
        } catch (DataAccessException e) {
            throw new FazemuDAOException(e.getMessage(), e);
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public void insert(Impressora impressora) {
        LOGGER.debug("ImpressoraJdbcDao: insert");

        Integer idImpressora = nextVal("SEQ_IMPR");

        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                    .addValue("idImpressora", idImpressora)
                    .addValue("nome", impressora.getNome())
                    .addValue("local", impressora.getLocal())
                    .addValue("ip", impressora.getIp())
                    .addValue("porta", impressora.getPorta())
                    .addValue("marca", impressora.getMarca())
                    .addValue("modelo", impressora.getModelo())
                    .addValue("usuario", impressora.getUsuario());

            StringBuilder sql = new StringBuilder("insert into impressora(impr_id_impressora, impr_nome, impr_local, impr_ip, impr_porta, impr_marca, impr_modelo, impr_situacao, impr_usuario, impr_datahora)")
                    .append(" values (:idImpressora, :nome, :local, :ip, :porta, :marca, :modelo, 'A', :usuario, sysdate)");

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
    public int update(Impressora impressora) {
        try {
            LOGGER.debug("ImpressoraJdbcDao: update");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idImpressora", impressora.getId())
                    .addValue("nome", impressora.getNome())
                    .addValue("local", impressora.getLocal())
                    .addValue("ip", impressora.getIp())
                    .addValue("porta", impressora.getPorta())
                    .addValue("marca", impressora.getMarca())
                    .addValue("modelo", impressora.getModelo())
                    .addValue("situacao", impressora.getSituacao())
                    .addValue("usuario", impressora.getUsuario());

            StringBuilder sql = new StringBuilder("update impressora  ")
                    .append("   set impr_nome = :nome, impr_local = :local, impr_ip = :ip, impr_porta = :porta, impr_marca = :marca, impr_modelo = :modelo, impr_situacao = :situacao, impr_usuario = :usuario, impr_datahora = sysdate ")
                    .append(" where impr_id_impressora = :idImpressora ");

            return namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);

        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public Impressora findById(Long id) {
        LOGGER.debug("ImpressoraJdbcDao: findById");
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);

            StringBuilder query = new StringBuilder("select impr.* ")
                    .append("  from impressora impr ")
                    .append(" where impr_id_impressora = :id ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, impressoraRowMapper);

        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar findById", e);
            throw new FazemuDAOException(e.getMessage(), e)
                    .addContextValue("id", id);
        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar findById", e);
            throw new FazemuDAOException(e.getMessage(), e)
                    .addContextValue("id", id);
        }
    }

}
