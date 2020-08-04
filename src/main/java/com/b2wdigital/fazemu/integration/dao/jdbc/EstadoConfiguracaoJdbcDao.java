package com.b2wdigital.fazemu.integration.dao.jdbc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.b2wdigital.fazemu.business.repository.EstadoConfiguracaoRepository;
import com.b2wdigital.fazemu.domain.EstadoConfiguracao;
import com.b2wdigital.fazemu.exception.FazemuDAOException;

@Repository
public class EstadoConfiguracaoJdbcDao extends AbstractDao implements EstadoConfiguracaoRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(EstadoConfiguracaoJdbcDao.class);

    @Autowired
    @Qualifier("estadoConfiguracaoRowMapper")
    private RowMapper<EstadoConfiguracao> estadoConfiguracaoRowMapper;

    @Override
    public List<EstadoConfiguracao> listAll() {
        try {
            LOGGER.debug("listAll");

            StringBuilder query = new StringBuilder("select esco.* ")
                    .append("  from estado_configuracao esco ")
                    .append(" order by esco.esco_id_estado ");

            return namedParameterJdbcOperations.query(query.toString(), (SqlParameterSource) null, estadoConfiguracaoRowMapper);

        } catch (DataAccessException e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public EstadoConfiguracao findByTipoDocumentoFiscalAndIdEstado(String tipoDocumentoFiscal, Long idEstado) {
        try {
            LOGGER.debug("findByIdEstado");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("tipoDocumentoFiscal", tipoDocumentoFiscal)
                    .addValue("idEstado", idEstado);

            StringBuilder query = new StringBuilder("select esco.* ")
                    .append("  from estado_configuracao esco ")
                    .append(" where esco.esco_tp_doc_fiscal = :tipoDocumentoFiscal ")
                    .append("   and esco.esco_id_estado = :idEstado ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, estadoConfiguracaoRowMapper);

        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Estado Configuracao nao encontrado com o id Estado {}", idEstado);
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public EstadoConfiguracao findByTipoDocumentoFiscalAndSiglaIbge(String tipoDocumentoFiscal, Long codigoIbge) {
        try {
            LOGGER.debug("findBySiglaIbge");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("tipoDocumentoFiscal", tipoDocumentoFiscal)
                    .addValue("codigoIbge", codigoIbge);

            StringBuilder query = new StringBuilder("select esco.* ")
                    .append("  from estado_configuracao esco ")
                    .append("     , estado esta ")
                    .append(" where esco.esco_tp_doc_fiscal = :tipoDocumentoFiscal ")
                    .append("   and esco.esco_id_estado = esta.esta_id_estado ")
                    .append("   and esta.esta_cod_ibge  = :codigoIbge ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, estadoConfiguracaoRowMapper);

        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Estado Configuracao nao encontrado com o codigo ibge {}", codigoIbge);
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public void update(EstadoConfiguracao estadoConfiguracao) {
        try {
            LOGGER.debug("update");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                    .addValue("idEstado", estadoConfiguracao.getIdEstado())
                    .addValue("tipoDocumentoFiscal", estadoConfiguracao.getTipoDocumentoFiscal())
                    .addValue("inAtivo", estadoConfiguracao.getInAtivo())
                    .addValue("inResponsavelTecnico", estadoConfiguracao.getInResponsavelTecnico())
                    .addValue("inCSRT", estadoConfiguracao.getInCSRT())
                    .addValue("inEPECAutomatico", estadoConfiguracao.getInEPECAutomatico())
                    .addValue("quantidadeMinimaRegistros", estadoConfiguracao.getQuantidadeMinimaRegistros())
                    .addValue("periodo", estadoConfiguracao.getPeriodo())
                    .addValue("periodoEPEC", estadoConfiguracao.getPeriodoEPEC())
                    .addValue("usuario", estadoConfiguracao.getUsuario());

            StringBuilder sql = new StringBuilder("update estado_configuracao ")
                    .append("   set esco_in_ativo = :inAtivo ")
                    .append("     , esco_in_resp_tecnico = :inResponsavelTecnico ")
                    .append("     , esco_in_csrt = :inCSRT ")
                    .append("     , esco_in_epec_automatico = :inEPECAutomatico ")
                    .append("     , esco_qtde_min_reg = :quantidadeMinimaRegistros ")
                    .append("     , esco_periodo = :periodo ")
                    .append("     , esco_periodo_epec = :periodoEPEC ")
                    .append("     , esco_usuario = :usuario ")
                    .append("     , esco_datahora = sysdate ")
                    .append(" where esco_id_estado = :idEstado ")
                    .append("   and esco_tp_doc_fiscal = :tipoDocumentoFiscal ");

            namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

}
