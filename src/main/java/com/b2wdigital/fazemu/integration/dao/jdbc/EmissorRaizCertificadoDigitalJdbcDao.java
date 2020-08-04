package com.b2wdigital.fazemu.integration.dao.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.b2wdigital.fazemu.business.repository.EmissorRaizCertificadoDigitalRepository;
import com.b2wdigital.fazemu.domain.EmissorRaizCertificadoDigital;
import com.b2wdigital.fazemu.domain.form.EmissorRaizCertificadoDigitalForm;
import com.b2wdigital.fazemu.exception.FazemuDAOException;
import com.b2wdigital.fazemu.integration.mapper.CertificadoDigitalMapper;

@Repository
public class EmissorRaizCertificadoDigitalJdbcDao extends AbstractDao implements EmissorRaizCertificadoDigitalRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmissorRaizCertificadoDigitalJdbcDao.class);

    @Autowired
    @Qualifier("emissorRaizCertificadoDigitalRowMapper")
    private RowMapper<EmissorRaizCertificadoDigital> emissorRaizCertificadoDigitalRowMapper;
    
   

    @Override
    public EmissorRaizCertificadoDigital findByIdEmissorRaiz(Long idEmissorRaiz) {
        try {
            LOGGER.debug("findByIdEmissorRaiz");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idEmissorRaiz", idEmissorRaiz);

            StringBuilder query = new StringBuilder("select edig.* ")
                    .append(" from emissor_raiz_cdigital edig ")
                    .append(" where edig.edig_id_emissor_raiz = :idEmissorRaiz ")
                    .append("   and sysdate ")
                    .append(" between edig.edig_dt_vig_ini ")
                    .append("   and edig.edig_dt_vig_fim ")
                    .append("   and rownum = 1 ");

            EmissorRaizCertificadoDigital emissorRaizCertificadoDigital = namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, emissorRaizCertificadoDigitalRowMapper);

//            if (emissorRaizCertificadoDigital != null && StringUtils.isNotBlank(emissorRaizCertificadoDigital.getSenha())) {
//                emissorRaizCertificadoDigital.setSenha(new String(Base64.getDecoder().decode(emissorRaizCertificadoDigital.getSenha())));
//            }

            return emissorRaizCertificadoDigital;
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("EmissorRaizCertificadoDigital nao encontrado com o id " + idEmissorRaiz);
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("Erro ao tentar executar findByIdEmissorRaiz", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public List<EmissorRaizCertificadoDigitalForm> listByDataFimVigencia() {
        try {
            LOGGER.debug("EmissorRaizCertificadoDigitalJdbcDao: listByDataFimVigencia");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();

            StringBuilder query = new StringBuilder("select edig.* ")
                    .append(" from emissor_raiz_cdigital edig ")
                    .append(" where edig.edig_dt_vig_fim - sysdate < 90 ");

            
            List<EmissorRaizCertificadoDigital> lista =  namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, emissorRaizCertificadoDigitalRowMapper);
            lista = excluiCertificadosExpirados(lista);
            
            List<EmissorRaizCertificadoDigitalForm> result = new ArrayList<EmissorRaizCertificadoDigitalForm>();
            lista.forEach(ercd -> result.add(new ModelMapper().map(ercd,EmissorRaizCertificadoDigitalForm.class)));
            return result;
            
        } catch (DataAccessException e) {
            throw new FazemuDAOException(e.getMessage(), e);
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }
    
    private List<EmissorRaizCertificadoDigital> excluiCertificadosExpirados(List<EmissorRaizCertificadoDigital> todosCertificados){
    	List<EmissorRaizCertificadoDigital> result = new ArrayList<EmissorRaizCertificadoDigital>();
    	Date now = new Date();
    	
    	for(EmissorRaizCertificadoDigital ercd: todosCertificados) {
    		if (ercd.getDataVigenciaFim().equals(now) || ercd.getDataVigenciaFim().after(now)) {
    			result.add(ercd);
            }
    	}
    	return result;
    }

    @Override
    public void insert(EmissorRaizCertificadoDigital emissorRaizCertificadoDigital) {
        LOGGER.debug("EmissorRaizCertificadoDigitalJdbcDao: insert");

        Integer id = nextVal("SEQ_EDIG");

        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                    .addValue("id", id)
                    .addValue("idEmissor", emissorRaizCertificadoDigital.getIdEmissorRaiz())
                    .addValue("dataVigenciaInicio", emissorRaizCertificadoDigital.getDataVigenciaInicio())
                    .addValue("dataVigenciaFim", emissorRaizCertificadoDigital.getDataVigenciaFim())
                    .addValue("certificado", emissorRaizCertificadoDigital.getCertificadoBytes())
                    .addValue("senha", emissorRaizCertificadoDigital.getSenha())
                    .addValue("usuario", emissorRaizCertificadoDigital.getUsuario());

            StringBuilder sql = new StringBuilder("insert into emissor_raiz_cdigital(edig_id_cdigital, edig_id_emissor_raiz, edig_dt_vig_ini, edig_dt_vig_fim, edig_cdigital, edig_senha, edig_usuario, edig_datahora)")
                    .append(" values (:id, :idEmissor, :dataVigenciaInicio, :dataVigenciaFim, :certificado, :senha, :usuario, sysdate)");

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
    public int update(Long id, Date dataVigenciaFim, String usuario) {
        try {
            LOGGER.debug("EmissorRaizCertificadoDigitalJdbcDao: update");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id)
                    .addValue("dataVigenciaFim", dataVigenciaFim)
                    .addValue("usuario", usuario);

            StringBuilder sql = new StringBuilder(" update emissor_raiz_cdigital ")
                    .append(" set edig_dt_vig_fim = :dataVigenciaFim, edig_usuario = :usuario, edig_datahora = sysdate ")
                    .append(" where edig_id_cdigital = :id ");

            return namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);

        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

	@Override
	public List<EmissorRaizCertificadoDigitalForm> listByFiltros(Map<String, String> filtros) {
		try {
			String query = buildQuery(filtros);
			LOGGER.debug("EmissorRaizJdbcDao: listByFiltros");
			List<EmissorRaizCertificadoDigitalForm> lista = namedParameterJdbcOperations.query(query,new CertificadoDigitalMapper());
			
			if(filtros.containsKey("situacaoVigencia")) {
				lista = filtraPorSituacao(lista, filtros.get("situacaoVigencia"));
			}
			
			return lista;
			
		} catch (EmptyResultDataAccessException e) {
			LOGGER.info("EmissorRaizCertificadoDigitalJdbcDao: listByFiltros {} ", e);
			return null;
		}catch (Exception e) {
			LOGGER.info("EmissorRaizCertificadoDigitalJdbcDao: listByFiltros {} ", e);
			throw new FazemuDAOException(e.getMessage(), e);
		}
	}
	
	private List<EmissorRaizCertificadoDigitalForm> filtraPorSituacao(List<EmissorRaizCertificadoDigitalForm> lista, String situacao){
		return  lista.stream()
				.filter(line -> line.getSituacao().equalsIgnoreCase(situacao))
				.collect(Collectors.toList());
	}
	
	private String buildQuery(Map<String,String> filtro) throws Exception {
		if(filtro.size()< 3) { //apenas filtra por nome e situacao
			String query = "select edig.*, emra.*  from emissor_raiz_cdigital edig "
					+ "join emissor_raiz emra "
					+ "on edig_id_emissor_raiz = emra_id_emissor_raiz "
					+ "where 1 = 1";
			
			String filtroNomeEmissor= filtro.get("nomeEmissor");
	    	String filtroSituacao = filtro.get("situacao");
	    	
	    	if(StringUtils.isNotBlank(filtroNomeEmissor)) {
	    		filtroNomeEmissor = " and upper(emra.emra_nome) like '%" + filtroNomeEmissor + "%'";
	    	}else {
	    		filtroNomeEmissor = "";
	    	}
	    	
	    	if(StringUtils.isNotBlank(filtroSituacao)) {
	    		filtroSituacao = " and upper(emra.emra_situacao) like '%" + filtroSituacao + "%'";
	    	}else {
	    		filtroSituacao = "";
	    	}
	    	
	    	
	    	 StringBuilder sql = new StringBuilder(query)
	    			 .append(filtroNomeEmissor)
	    			 .append(filtroSituacao);
	    	 
	    	 return sql.toString();
		}
		throw new Exception("Filtros acima do permitido");
	}

}
