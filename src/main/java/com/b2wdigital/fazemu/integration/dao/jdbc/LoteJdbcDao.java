package com.b2wdigital.fazemu.integration.dao.jdbc;

import java.util.Date;
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

import com.b2wdigital.fazemu.business.repository.LoteRepository;
import com.b2wdigital.fazemu.domain.Lote;
import com.b2wdigital.fazemu.enumeration.PontoLoteEnum;
import com.b2wdigital.fazemu.enumeration.SituacaoLoteEnum;
import com.b2wdigital.fazemu.exception.FazemuDAOException;

/**
 *
 * @author dailton.almeida
 */
@Repository
public class LoteJdbcDao extends AbstractDao implements LoteRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoteJdbcDao.class);

    public static final String KEY_ID = "id";
    public static final String KEY_SITUACAO = "situacao";
    public static final String KEY_USUARIO = "usuario";
    public static final String KEY_ID_PONTO = "idPonto";

    @Autowired
    @Qualifier("loteRowMapper")
    private RowMapper<Lote> loteRowMapper;

    @Override
    public Lote findById(Long id) {
        LOGGER.debug("LoteJdbcDao: findById");
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(KEY_ID, id);

            StringBuilder query = new StringBuilder("select lote.* ")
                    .append("  from lote lote ")
                    .append(" where lote.lote_id_lote = :id ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, loteRowMapper);

        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Lote nao encontrado com o id " + id);
            return null;
        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar findById", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public Long emitirIdLote() {
        try {
            StringBuilder query = new StringBuilder("select seq_lote.nextval ")
                    .append("  from dual ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), (SqlParameterSource) null, Long.class);

        } catch (DataAccessException dae) {
            throw new FazemuDAOException(dae.getMessage(), dae);
        }
    }

    @Override
    public int criarLote(Lote lote) {
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(KEY_ID, lote.getId())
                    .addValue("tipoDocumentoFiscal", lote.getTipoDocumentoFiscal())
                    .addValue("tipoEmissao", lote.getTipoEmissao())
                    .addValue("idEmissorRaiz", lote.getIdEmissorRaiz())
                    .addValue("versao", lote.getVersao())
                    .addValue(KEY_SITUACAO, lote.getSituacao())
                    .addValue(KEY_ID_PONTO, lote.getIdPonto())
                    .addValue("idEstado", lote.getIdEstado())
                    .addValue("idMunicipio", lote.getIdMunicipio())
                    .addValue("servico", lote.getServico())
                    .addValue("usuarioReg", lote.getUsuarioReg())
                    .addValue(KEY_USUARIO, lote.getUsuario());

            StringBuilder sql = new StringBuilder("insert into lote (lote_id_lote, lote_tp_doc_fiscal, lote_tp_emissao, lote_id_emissor_raiz, lote_versao, lote_situacao, lote_id_ponto, lote_id_estado, lote_id_municipio, lote_tp_servico, lote_usuario_reg, lote_datahora_reg, lote_usuario, lote_datahora) ")
                    .append("values (:id, :tipoDocumentoFiscal, :tipoEmissao, :idEmissorRaiz, :versao, :situacao, :idPonto, :idEstado, :idMunicipio, :servico, :usuarioReg, sysdate, :usuario, sysdate)");

            return namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);

        } catch (DataAccessException dae) {
            throw new FazemuDAOException(dae.getMessage(), dae);
        }
    }

    @Override
    public int fecharLote(Long idLote, String url, String usuario) {
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(KEY_ID, idLote)
                    .addValue("url", url)
                    .addValue(KEY_SITUACAO, SituacaoLoteEnum.FECHADO.getCodigo())
                    .addValue(KEY_ID_PONTO, PontoLoteEnum.FECHADO.getCodigo())
                    .addValue(KEY_USUARIO, usuario);

            StringBuilder sql = new StringBuilder("update lote ")
                    .append("   set lote_url = :url, lote_situacao = :situacao, lote_id_ponto = :idPonto, lote_usuario = :usuario, lote_datahora = sysdate ")
                    .append(" where lote_id_lote = :id ");

            return namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);

        } catch (DataAccessException dae) {
            throw new FazemuDAOException(dae.getMessage(), dae);
        }
    }

    @Override
    public int enviarLote(Long idLote, Long reciboAutorizacao, Integer situacaoAutorizacao, String usuario) {
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(KEY_ID, idLote)
                    .addValue("reciboAutorizacao", reciboAutorizacao)
                    .addValue("situacaoAutorizacao", situacaoAutorizacao)
                    .addValue(KEY_SITUACAO, SituacaoLoteEnum.ENVIADO.getCodigo())
                    .addValue(KEY_ID_PONTO, PontoLoteEnum.ENVIADO.getCodigo())
                    .addValue(KEY_USUARIO, usuario);

            StringBuilder sql = new StringBuilder("update lote ")
                    .append("   set lote_recibo_autor = :reciboAutorizacao, lote_situacao_autor = :situacaoAutorizacao, lote_situacao = :situacao, lote_id_ponto = :idPonto, lote_usuario = :usuario, lote_datahora = sysdate ")
                    .append(" where lote_id_lote = :id ");

            return namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);

        } catch (DataAccessException dae) {
            throw new FazemuDAOException(dae.getMessage(), dae);
        }
    }

    @Override
    public int finalizarLote(Long idLote, Integer situacaoAutorizacao, String usuario) {
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(KEY_ID, idLote)
                    .addValue("situacaoAutorizacao", situacaoAutorizacao)
                    .addValue(KEY_SITUACAO, SituacaoLoteEnum.LIQUIDADO.getCodigo())
                    .addValue(KEY_ID_PONTO, PontoLoteEnum.LIQUIDADO.getCodigo())
                    .addValue(KEY_USUARIO, usuario);

            StringBuilder sql = new StringBuilder("update lote ")
                    .append("   set lote_situacao_autor = :situacaoAutorizacao, lote_situacao = :situacao, lote_id_ponto = :idPonto, lote_usuario = :usuario, lote_datahora = sysdate ")
                    .append(" where lote_id_lote = :id ");

            return namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);

        } catch (DataAccessException dae) {
            throw new FazemuDAOException(dae.getMessage(), dae);
        }
    }

    @Override
    public int cancelarLote(Long idLote, String usuario) {
        LOGGER.info("cancelarLote lote {} ", idLote);
        return atualizarSituacaoLote(idLote, SituacaoLoteEnum.CANCELADO.getCodigo(), usuario, PontoLoteEnum.CANCELADO.getCodigo());
    }

    @Override
    public int reabrirLote(Long idLote, String usuario) {
        return atualizarSituacaoLote(idLote, SituacaoLoteEnum.ABERTO.getCodigo(), usuario, PontoLoteEnum.ABERTO.getCodigo());
    }

    protected int atualizarSituacaoLote(Long idLote, String situacao, String usuario, String idPonto) {
        LOGGER.info("atualizarSituacaoLote lote {} situacao {} ponto {}", idLote, situacao, idPonto);
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(KEY_ID, idLote)
                    .addValue(KEY_SITUACAO, situacao)
                    .addValue(KEY_ID_PONTO, idPonto)
                    .addValue(KEY_USUARIO, usuario);

            StringBuilder sql = new StringBuilder("update lote ")
                    .append("   set lote_situacao = :situacao, lote_id_ponto = :idPonto, lote_usuario = :usuario, lote_datahora = sysdate ")
                    .append(" where lote_id_lote = :id ");

            return namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);

        } catch (DataAccessException dae) {
            throw new FazemuDAOException(dae.getMessage(), dae);
        }
    }

    @Override
    public int atualizarDataUltimaConsulta(Long idLote, Date dataUltimaConsulta, String usuario) {
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(KEY_ID, idLote)
                    .addValue("dataUltimaConsulta", dataUltimaConsulta)
                    .addValue(KEY_USUARIO, usuario);

            StringBuilder sql = new StringBuilder("update lote ")
                    .append("   set lote_dt_ult_consulta = :dataUltimaConsulta, lote_usuario = :usuario, lote_datahora = sysdate ")
                    .append(" where lote_id_lote = :id ");

            return namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);

        } catch (DataAccessException dae) {
            throw new FazemuDAOException(dae.getMessage(), dae);
        }
    }

    @Override
    public List<Lote> obterLotesPorSituacao(String situacao) {
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(KEY_SITUACAO, situacao);

            StringBuilder query = new StringBuilder("select * ")
                    .append("  from lote ")
                    .append(" where lote_situacao = :situacao");

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, loteRowMapper);
        } catch (DataAccessException e) {
            LOGGER.error("Erro ao tentar executar obterLotesPorSituacao", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public Integer countSituacaoAutorizacaoById(Long idLote) {
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(KEY_ID, idLote);

            StringBuilder query = new StringBuilder("select count(1) ")
                    .append("  from lote ")
                    .append(" where lote_situacao_autor is not null ")
                    .append("   and lote_id_lote = :id ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, Integer.class);

        } catch (Exception e) {
            LOGGER.error("Erro countSituacaoAutorizacaoById ", e);
            return 0;
        }
    }

    @Override
    public int updateLoteSituacaoAutorizacao(Long idLote, Integer situacaoAutorizacao, String usuario) {
        try {
            LOGGER.info("updateLoteSituacaoAutorizacao idLote {} | situacaoAutorizacao {} ", idLote, situacaoAutorizacao);
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(KEY_ID, idLote)
                    .addValue("situacaoAutorizacao", situacaoAutorizacao)
                    .addValue(KEY_USUARIO, usuario);

            StringBuilder sql = new StringBuilder("update lote ")
                    .append("   set lote_situacao_autor = :situacaoAutorizacao, lote_usuario = :usuario, lote_datahora = sysdate ")
                    .append(" where lote_id_lote = :id ");

            return namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);

        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar updateLoteSituacaoAutorizacao situacaoAutorizacao ", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public int delete(Long idLote) {
        try {
            LOGGER.info("delete idLote {} ", idLote);
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(KEY_ID, idLote);

            StringBuilder sql = new StringBuilder("delete lote ")
                    .append(" where lote_id_lote = :id ");

            return namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);

        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar delete ", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public List<Lote> listByIdDocFiscalAndSituacao(Long idDocFiscal, SituacaoLoteEnum situacao) {
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                    .addValue(KEY_SITUACAO, situacao.getCodigo())
                    .addValue("idDocFiscal", idDocFiscal);

            StringBuilder query = new StringBuilder(" select lote.* ")
                    .append("  from documento_lote dole")
                    .append("     , lote lote ")
                    .append(" where dole.dole_id_lote = lote.lote_id_lote ")
                    .append("   and dole.dole_id_doc_fiscal = :idDocFiscal ")
                    .append("   and lote.lote_situacao = :situacao ");

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, loteRowMapper);

        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("LoteJdbcDao: listByIdDocFiscalAndSituacao {} ", e);
            return null;
        } catch (DataAccessException e) {
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public List<Lote> listByDateIntervalAndSituacao(Date dataHoraRegistroInicio, Date dataHoraRegistroFim, SituacaoLoteEnum situacao) {
        try {
            LOGGER.debug("LoteJdbcDao: listByDateIntervalAndSituacao");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                    .addValue("dataHoraRegistroInicio", dataHoraRegistroInicio)
                    .addValue("dataHoraRegistroFim", dataHoraRegistroFim)
                    .addValue("situacao", situacao.getCodigo());

            StringBuilder query = new StringBuilder("select lote.* ")
                    .append("  from lote lote ")
                    .append(" where lote.lote_datahora_reg <= :dataHoraRegistroInicio ")
                    .append("   and lote.lote_datahora_reg >= :dataHoraRegistroFim ")
                    .append("   and lote.lote_situacao      = :situacao ");

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, loteRowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("LoteJdbcDao: listByDateIntervalAndSituacao {} ", e);
            return null;
        } catch (DataAccessException e) {
            throw new FazemuDAOException(e.getMessage(), e);
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public List<Lote> listByDateIntervalAndSituacaoAndIdEstado(Date dataHoraRegistroInicio, Date dataHoraRegistroFim, SituacaoLoteEnum situacao, Long idEstado) {
        try {
            LOGGER.debug("LoteJdbcDao: listByDateIntervalAndSituacaoAndIdEstado");

            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                    .addValue("dataHoraRegistroInicio", dataHoraRegistroInicio)
                    .addValue("dataHoraRegistroFim", dataHoraRegistroFim)
                    .addValue("situacao", situacao.getCodigo())
                    .addValue("idEstado", idEstado);

            StringBuilder query = new StringBuilder("select lote.* ")
                    .append("  from lote lote ")
                    .append(" where lote.lote_datahora_reg <= :dataHoraRegistroInicio ")
                    .append("   and lote.lote_datahora_reg >= :dataHoraRegistroFim ")
                    .append("   and lote.lote_situacao      = :situacao ")
                    .append("   and lote.lote_id_estado     = :idEstado ");

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, loteRowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("LoteJdbcDao: listByDateIntervalAndSituacaoAndIdEstado {} ", e);
            return null;
        } catch (DataAccessException e) {
            throw new FazemuDAOException(e.getMessage(), e);
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public List<Lote> listByIdDocFiscal(Long idDocFiscal) {
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                    .addValue("idDocFiscal", idDocFiscal);

            StringBuilder query = new StringBuilder(" select lote.* ")
                    .append("  from documento_lote dole")
                    .append("     , lote lote ")
                    .append(" where dole.dole_id_lote       = lote.lote_id_lote ")
                    .append("   and dole.dole_id_doc_fiscal = :idDocFiscal ");

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, loteRowMapper);

        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("LoteJdbcDao: listByIdDocFiscal {} ", e);
            return null;
        } catch (DataAccessException e) {
            return null;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

}
