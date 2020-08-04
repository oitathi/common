package com.b2wdigital.fazemu.integration.dao.jdbc;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.b2wdigital.fazemu.business.repository.EmissorRaizFilialRepository;
import com.b2wdigital.fazemu.domain.EmissorRaizFilial;
import com.b2wdigital.fazemu.exception.FazemuDAOException;
import com.b2wdigital.fazemu.integration.mapper.EmissorRaizFilialRowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

@Repository
public class EmissorRaizFilialJdbcDao extends AbstractDao implements EmissorRaizFilialRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmissorRaizCertificadoDigitalJdbcDao.class);

    @Autowired
    @Qualifier("emissorRaizFilialRowMapper")
    private RowMapper<EmissorRaizFilial> emissorRaizFilialRowMapper;

    @Override
    public EmissorRaizFilial findByIdFilial(Long idFilial) {
        LOGGER.debug("EmissorRaizFilialJdbcDao: findByIdFilial");
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idFilial", idFilial);

            StringBuilder query = new StringBuilder("select efil.* ")
                    .append("  from emissor_raiz_filial efil ")
                    .append(" where efil.efil_id_filial = :idFilial ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, emissorRaizFilialRowMapper);
        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar findByIdFilial", e);
            throw new FazemuDAOException(e.getMessage(), e)
                    .addContextValue("idFilial", idFilial);
        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar findByIdFilial", e);
            throw new FazemuDAOException(e.getMessage(), e)
                    .addContextValue("idFilial", idFilial);
        }
    }

    @Override
    public List<EmissorRaizFilial> listByInConsultaDocumento() {
        try {
            LOGGER.debug("listByInConsultaDocumento");

            StringBuilder query = new StringBuilder("select efil.* ")
                    .append("  from emissor_raiz_filial efil ")
                    .append(" where efil.efil_in_consulta_doc = 'S' ")
                    .append(" order by efil.efil_id_filial ");

            return namedParameterJdbcOperations.query(query.toString(), (SqlParameterSource) null, emissorRaizFilialRowMapper);
        } catch (DataAccessException e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public List<EmissorRaizFilial> listByFiltros(Map<String, String> filtros)throws Exception {
        String query = buildQuery(filtros);
        LOGGER.debug("EmissorRaizJdbcDao: listByFiltros");
        List<EmissorRaizFilial> lista = namedParameterJdbcOperations.query(query, new EmissorRaizFilialRowMapper());
        return lista;
    }

    public String buildQuery(Map<String, String> filtros) {
        String query = "SELECT efil.* FROM emissor_raiz_filial efil WHERE 1=1";

        String filtroIdEmissorRaiz = filtros.get("idEmissorRaiz");
        String filtroIdFilial = filtros.get("idFilial");
        String filtroNome = filtros.get("nome");
        String filtroInConsultaDocumento = filtros.get("inConsultaDocumento");
        String filtroUltimoNSU = filtros.get("ultimoNSU");
        String filtroChaveAutenticacao = filtros.get("chaveAutenticacao");
        String filtroUsuario = filtros.get("usuario");
        String filtroDataHora = filtros.get("dataHora");

        if (StringUtils.isNotBlank(filtroIdEmissorRaiz)) {
            filtroIdEmissorRaiz = " AND efil_id_emissor_raiz = " + filtroIdEmissorRaiz;
        } else {
            filtroIdEmissorRaiz = "";
        }

        if (StringUtils.isNotBlank(filtroIdFilial)) {
            filtroIdFilial = " AND efil_id_filial = " + filtroIdFilial;
        } else {
            filtroIdFilial = "";
        }

        if (StringUtils.isNotBlank(filtroNome)) {
            filtroNome = " AND efil_nome LIKE '" + filtroNome + "'";
        } else {
            filtroNome = "";
        }

        if (StringUtils.isNotBlank(filtroInConsultaDocumento)) {
            filtroInConsultaDocumento = " AND efil_in_consulta_doc LIKE '" + filtroInConsultaDocumento + "'";
        } else {
            filtroInConsultaDocumento = "";
        }

        if (StringUtils.isNotBlank(filtroUltimoNSU)) {
            filtroUltimoNSU = " AND efil_ult_nsu LIKE '" + filtroUltimoNSU + "'";
        } else {
            filtroUltimoNSU = "";
        }

        if (StringUtils.isNotBlank(filtroChaveAutenticacao)) {
            filtroChaveAutenticacao = " AND efil_chave_atenticacao LIKE '" + filtroChaveAutenticacao + "'";
        } else {
            filtroChaveAutenticacao = "";
        }

        if (StringUtils.isNotBlank(filtroUsuario)) {
            filtroUsuario = " AND efil_usuario LIKE '" + filtroUsuario + "'";
        } else {
            filtroUsuario = "";
        }

        if (StringUtils.isNotBlank(filtroDataHora)) {
            //TODO
            filtroDataHora = "";
        } else {
            filtroDataHora = "";
        }

        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append(query)
                .append(filtroIdEmissorRaiz)
                .append(filtroIdFilial)
                .append(filtroNome)
                .append(filtroInConsultaDocumento)
                .append(filtroUltimoNSU)
                .append(filtroChaveAutenticacao)
                .append(filtroUsuario)
                .append(filtroDataHora);

        return sqlQuery.toString();
    }

    @Override
    public int updateUltimoNSU(Long idFilial, String ultimoNSU, String usuario) {
        try {
            LOGGER.debug("update");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idFilial", idFilial)
                    .addValue("ultimoNSU", ultimoNSU)
                    .addValue("usuario", usuario);

            StringBuilder sql = new StringBuilder("update emissor_raiz_filial  ")
                    .append("   set efil_ult_nsu = :ultimoNSU , efil_usuario = :usuario, efil_datahora = sysdate ")
                    .append(" where efil_id_filial = :idFilial");

            return namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);

        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

	@Override
	public long insert(EmissorRaizFilial emissorFilial) throws Exception {
		LOGGER.debug("EmissorRaizFilialJdbcDao: insert");
		
		 MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
	        .addValue("idEmissorRaiz", emissorFilial.getIdEmissorRaiz())
	        .addValue("idFilial", emissorFilial.getIdFilial())
	        .addValue("nome", emissorFilial.getNome())
	        .addValue("inConsultaDocumento", emissorFilial.getInConsultaDocumento())
	        .addValue("chaveAutenticacao", emissorFilial.getChaveAutenticacao())
	        .addValue("usuario", emissorFilial.getUsuario());
		 
		 StringBuilder sql = new StringBuilder("insert into emissor_raiz_filial(efil_id_emissor_raiz, efil_id_filial, efil_nome, efil_in_consulta_doc, efil_chave_autenticacao, efil_usuario, efil_datahora)")
       		  .append(" values (:idEmissorRaiz, :idFilial, :nome, :inConsultaDocumento, :chaveAutenticacao, :usuario, sysdate)");
		 
		return namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);
	}

	@Override
	public long update(EmissorRaizFilial emissorFilial) throws Exception {
		LOGGER.debug("EmissorRaizFilialJdbcDao: update");
		
		 MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
	        .addValue("nome", emissorFilial.getNome())
	        .addValue("inConsultaDocumento", emissorFilial.getInConsultaDocumento())
	        .addValue("chaveAutenticacao", emissorFilial.getChaveAutenticacao())
		 	.addValue("usuario", emissorFilial.getUsuario())
		 	.addValue("idEmissorRaiz", emissorFilial.getIdEmissorRaiz())
	        .addValue("idFilial", emissorFilial.getIdFilial());
		 
		 StringBuilder sql = new StringBuilder("update emissor_raiz_filial ")
                 .append(" set efil_nome = :nome, efil_in_consulta_doc = :inConsultaDocumento, efil_chave_autenticacao = :chaveAutenticacao, efil_usuario = :usuario, efil_datahora = sysdate ")
                 .append(" where efil_id_emissor_raiz = :idEmissorRaiz ")
                 .append(" and efil_id_filial = :idFilial");
		 
		return namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);
	}

}
