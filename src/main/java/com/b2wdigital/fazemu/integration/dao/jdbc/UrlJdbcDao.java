package com.b2wdigital.fazemu.integration.dao.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.b2wdigital.fazemu.business.repository.UrlRepository;
import com.b2wdigital.fazemu.exception.FazemuDAOException;

/**
 *
 * @author dailton.almeida
 */
@Repository
public class UrlJdbcDao extends AbstractDao implements UrlRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UrlJdbcDao.class);

    private static final String SITUACAO_ATIVA = "A";

    @Override
    public String getUrl(Integer codigoIBGE, Integer idTipoEmissao, String idServico, String versao) {
        LOGGER.debug("getUrl");

        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("codigoIBGE", codigoIBGE)
                    .addValue("idTipoEmissao", idTipoEmissao)
                    .addValue("idServico", idServico)
                    .addValue("versao", versao)
                    .addValue("situacao", SITUACAO_ATIVA);

            StringBuilder query = new StringBuilder("select ause.ause_url ")
                    .append("  from estado esta ")
                    .append("     , estado_autorizador esau ")
                    .append("     , autorizador_servico ause ")
                    .append(" where ause.ause_id_autorizador = esau.esau_id_autorizador ")
                    .append("   and esau.esau_id_estado      = esta.esta_id_estado ")
                    .append("   and esau.esau_tp_emissao     = :idTipoEmissao ")
                    .append("   and esta.esta_cod_ibge       = :codigoIBGE ")
                    .append("   and ause.ause_id_servico     = :idServico ")
                    .append("   and ause.ause_versao         = :versao ")
                    .append("   and esau.esau_situacao       = :situacao ")
                    .append("   and ause.ause_situacao       = :situacao ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, String.class);
        } catch (EmptyResultDataAccessException dae) {
            return getUrlNormal(codigoIBGE, idServico, versao);
        } catch (DataAccessException dae) {
            throw new FazemuDAOException(dae.getMessage(), dae);
        }
    }

    @Override
    public String getUrlNormal(Integer codigoIBGE, String idServico, String versao) {
        LOGGER.debug("getUrlNormal");

        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("codigoIBGE", codigoIBGE)
                    .addValue("idServico", idServico)
                    .addValue("versao", versao)
                    .addValue("situacao", SITUACAO_ATIVA);

            StringBuilder query = new StringBuilder("select ause.ause_url ")
                    .append("  from estado esta ")
                    .append("     , estado_autorizador esau ")
                    .append("     , autorizador_servico ause ")
                    .append(" where ause.ause_id_autorizador = esau.esau_id_autorizador ")
                    .append("   and esau.esau_id_estado      = esta.esta_id_estado ")
                    .append("   and esau.esau_tp_emissao     = 1 ")
                    .append("   and esta.esta_cod_ibge       = :codigoIBGE ")
                    .append("   and ause.ause_id_servico     = :idServico ")
                    .append("   and ause.ause_versao         = :versao ")
                    .append("   and esau.esau_situacao       = :situacao ")
                    .append("   and ause.ause_situacao       = :situacao ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, String.class);
        } catch (EmptyResultDataAccessException dae) {
            return null;
        } catch (DataAccessException dae) {
            throw new FazemuDAOException(dae.getMessage(), dae);
        }
    }

}
