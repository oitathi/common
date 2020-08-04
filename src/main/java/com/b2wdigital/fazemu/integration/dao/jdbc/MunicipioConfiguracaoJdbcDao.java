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

import com.b2wdigital.fazemu.business.repository.MunicipioConfiguracaoRepository;
import com.b2wdigital.fazemu.domain.MunicipioConfiguracao;
import com.b2wdigital.fazemu.exception.FazemuDAOException;

@Repository
public class MunicipioConfiguracaoJdbcDao extends AbstractDao implements MunicipioConfiguracaoRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(MunicipioConfiguracaoJdbcDao.class);

    @Autowired
    @Qualifier("municipioConfiguracaoRowMapper")
    private RowMapper<MunicipioConfiguracao> municipioConfiguracaoRowMapper;

    @Override
    public List<MunicipioConfiguracao> listAll() {
        try {
            LOGGER.debug("listAll");

            StringBuilder query = new StringBuilder("select muco.* ")
                    .append("  from municipio_configuracao muco ")
                    .append(" order by muco.muco_id_municipio ");

            return namedParameterJdbcOperations.query(query.toString(), (SqlParameterSource) null, municipioConfiguracaoRowMapper);

        } catch (DataAccessException e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public MunicipioConfiguracao findByTipoDocumentoFiscalAndIdMunicipio(String tipoDocumentoFiscal, Long idMunicipio) {
        try {
            LOGGER.debug("findByIdMunicipio");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("tipoDocumentoFiscal", tipoDocumentoFiscal)
                    .addValue("idMunicipio", idMunicipio);

            StringBuilder query = new StringBuilder("select muco.* ")
                    .append("  from municipio_configuracao muco ")
                    .append(" where muco.muco_tp_doc_fiscal = :tipoDocumentoFiscal ")
                    .append("   and muco.muco_id_municipio = :idMunicipio ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, municipioConfiguracaoRowMapper);

        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Municipio Configuracao nao encontrado com o id Municipio {}", idMunicipio);
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public MunicipioConfiguracao findByTipoDocumentoFiscalAndSiglaIbge(String tipoDocumentoFiscal, Long codigoIbge) {
        try {
            LOGGER.debug("findBySiglaIbge");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("tipoDocumentoFiscal", tipoDocumentoFiscal)
                    .addValue("codigoIbge", codigoIbge);

            StringBuilder query = new StringBuilder("select muco.* ")
                    .append("  from municipio_configuracao muco ")
                    .append("     , municipio muni ")
                    .append(" where muco.muco_id_municipio = muni.muni_id_municipio ")
                    .append("   and muco.muco_tp_doc_fiscal = :tipoDocumentoFiscal ")
                    .append("   and muni.muni_cod_ibge  = :codigoIbge ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, municipioConfiguracaoRowMapper);

        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Municipio Configuracao nao encontrado com o codigo ibge {}", codigoIbge);
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public void update(MunicipioConfiguracao municipioConfiguracao) {
        try {
            LOGGER.debug("update");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                    .addValue("idMunicipio", municipioConfiguracao.getIdMunicipio())
                    .addValue("tipoDocumentoFiscal", municipioConfiguracao.getTipoDocumentoFiscal())
                    .addValue("inAtivo", municipioConfiguracao.getInAtivo())
                    .addValue("inLote", municipioConfiguracao.getInLote())
                    .addValue("usuario", municipioConfiguracao.getUsuario());

            StringBuilder sql = new StringBuilder("update municipio_configuracao ")
                    .append("   set muco_in_ativo = :inAtivo ")
                    .append("     , muco_in_lote = :inLote ")
                    .append("     , muco_usuario = :usuario ")
                    .append("     , muco_datahora = sysdate ")
                    .append(" where muco_id_municipio = :idMunicipio ")
                    .append("   and muco_tp_doc_fiscal = :tipoDocumentoFiscal ");

            namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

}
