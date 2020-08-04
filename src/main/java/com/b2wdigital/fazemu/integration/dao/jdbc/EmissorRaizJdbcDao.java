package com.b2wdigital.fazemu.integration.dao.jdbc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.b2wdigital.fazemu.business.repository.EmissorRaizRepository;
import com.b2wdigital.fazemu.domain.EmissorRaiz;
import com.b2wdigital.fazemu.exception.FazemuDAOException;

/**
 * Emissor Raiz Dao.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
@Repository
@CacheConfig(cacheManager = "cacheManager", cacheNames = "TTL5", keyGenerator = "keyGenerator")
public class EmissorRaizJdbcDao extends AbstractDao implements EmissorRaizRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmissorRaizJdbcDao.class);

    @Autowired
    @Qualifier("emissorRaizRowMapper")
    private RowMapper<EmissorRaiz> emissorRaizRowMapper;

	@Override
	public EmissorRaiz findEmissorRaizById(String id) {
		HashMap<String, String> filtro = new HashMap<String, String>();
    	filtro.put("id", id);
        List<EmissorRaiz> listaEmissorRaiz = listByFiltros(filtro);
         
         if(CollectionUtils.isNotEmpty(listaEmissorRaiz)) {
        	 return listaEmissorRaiz.get(0);
         }
         
         return null;
	}

	

	@Override
	public List<EmissorRaiz> listByFiltros(Map<String, String> filtros) {
		try {
			String query = buildQuery(filtros);
			LOGGER.debug("EmissorRaizJdbcDao: listByFiltros");
			List<EmissorRaiz> lista =  namedParameterJdbcOperations.query(query, (SqlParameterSource) null, emissorRaizRowMapper);
			return lista;

		} catch (EmptyResultDataAccessException e) {
			LOGGER.info("EmissorRaizJdbcDao: listByFiltros {} ", e);
			return null;
		}catch (Exception e) {
			LOGGER.info("EmissorRaizJdbcDao: listByFiltros {} ", e);
			throw new FazemuDAOException(e.getMessage(), e);
		}

	}

	@Override
	public void insert(EmissorRaiz emissorRaiz) {
		LOGGER.debug("EmissorRaizJdbcDao: insert");
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("id", emissorRaiz.getId())
                .addValue("nome", emissorRaiz.getNome())
                .addValue("situacao", emissorRaiz.getSituacao())
                .addValue("usuario", emissorRaiz.getUsuario());
        
        StringBuilder sql = new StringBuilder("insert into emissor_raiz(emra_id_emissor_raiz, emra_nome, emra_id_impressora, emra_situacao, emra_usuario, emra_datahora)")
        		  .append(" values (:id, :nome, null, :situacao, :usuario, sysdate)");
        
        namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);

		
	}
	
	@Override
	public int update(EmissorRaiz emissorRaiz) {
	    try {
            LOGGER.debug("EmissorRaizJdbcDao: update");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", emissorRaiz.getId())
                    .addValue("nome", emissorRaiz.getNome())
                    .addValue("situacao", emissorRaiz.getSituacao())
                    .addValue("usuario", emissorRaiz.getUsuario());

            StringBuilder sql = new StringBuilder("update emissor_raiz ")
                    .append("   set emra_nome = :nome, emra_situacao = :situacao, emra_usuario = :usuario, emra_datahora = sysdate ")
                    .append(" where emra_id_emissor_raiz = :id ");

            return namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);

        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

	
	   private String buildQuery(Map<String,String> filtro) throws Exception {
	    	if(filtro.size() < 4) {
		    	String query = "select emra.* from emissor_raiz emra where 1 = 1";
		      	String filtroNomeEmissor= filtro.get("nomeEmissor");
		    	String filtroSituacao = filtro.get("situacao");
		    	String filtroId = filtro.get("id");
		    	
		    	if(StringUtils.isNotBlank(filtroNomeEmissor)) {
		    		filtroNomeEmissor = " and upper(emra.emra_nome) like '" + filtroNomeEmissor + "'";
		    	}else {
		    		filtroNomeEmissor = "";
		    	}
		    	
		    	if(StringUtils.isNotBlank(filtroSituacao)) {
		    		filtroSituacao = " and emra.emra_situacao = '" + filtroSituacao + "'" ;
		    	}else {
		    		filtroSituacao = "";
		    	}
		    	
		    	if(StringUtils.isNoneBlank(filtroId)) {
		    		filtroId = " and emra.emra_id_emissor_raiz = " + filtroId;
		    	}else {
		    		filtroId = "";
		    	}
		    	
		    	 StringBuilder sql = new StringBuilder(query)
		    			 .append(filtroNomeEmissor)
		    			 .append(filtroSituacao)
		    			 .append(filtroId)
		    			 .append(" order by emra.emra_id_emissor_raiz");
		    	 
		    	 return sql.toString();
	    	}
	    	throw new Exception("Filtros acima do permitido");
	    }
}
