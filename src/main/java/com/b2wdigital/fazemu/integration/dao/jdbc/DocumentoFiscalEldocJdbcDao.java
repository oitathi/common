package com.b2wdigital.fazemu.integration.dao.jdbc;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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

import com.b2wdigital.fazemu.business.repository.DocumentoFiscalEldocRepository;
import com.b2wdigital.fazemu.domain.DocumentoFiscalEldoc;
import com.b2wdigital.fazemu.exception.FazemuDAOException;
import com.b2wdigital.fazemu.exception.NotFoundException;
import com.b2wdigital.fazemu.integration.mapper.DocumentoFiscalEldocRowMapper;
import com.b2wdigital.fazemu.utils.DateUtils;

@Repository
public class DocumentoFiscalEldocJdbcDao extends AbstractDao implements DocumentoFiscalEldocRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoFiscalEldocJdbcDao.class);

    public static final String KEY_ID = "id";

    @Autowired
    @Qualifier("documentoFiscalEldocRowMapper")
    private RowMapper<DocumentoFiscalEldoc> documentoFiscalEldocRowMapper;

    @Override
    public DocumentoFiscalEldoc findById(Long id) {
        LOGGER.debug("DocumentoFiscalEldocJdbcDao: findById");
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(KEY_ID, id);

            StringBuilder query = new StringBuilder("select doel.* ")
                    .append("  from docu_eldoc doel ")
                    .append(" where doel_id_doc_fiscal = :id ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, documentoFiscalEldocRowMapper);
        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar findById", e);
            throw new FazemuDAOException(e.getMessage(), e)
                    .addContextValue(KEY_ID, id);
        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar findById", e);
            throw new FazemuDAOException(e.getMessage(), e)
                    .addContextValue(KEY_ID, id);
        }
    }

    @Override
    public Long insert(DocumentoFiscalEldoc documentoFiscalEldoc) {
        LOGGER.debug("DocumentoFiscalEldocJdbcDao: insert");

        Integer sequence = nextVal("SEQ_DOEL");

        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                    .addValue("id", sequence)
                    .addValue("tipoDocumentoFiscal", documentoFiscalEldoc.getTipoDocumentoFiscal())
                    .addValue("idEmissor", documentoFiscalEldoc.getIdEmissor())
                    .addValue("idDestinatario", documentoFiscalEldoc.getIdDestinatario())
                    .addValue("numeroDocumentoFiscal", documentoFiscalEldoc.getNumeroDocumentoFiscal())
                    .addValue("serieDocumentoFiscal", documentoFiscalEldoc.getSerieDocumentoFiscal())
                    .addValue("anoDocumentoFiscal", documentoFiscalEldoc.getAnoDocumentoFiscal())
                    .addValue("dataHoraEmissao", documentoFiscalEldoc.getDataHoraEmissao())
                    .addValue("idEstado", documentoFiscalEldoc.getIdEstado())
                    .addValue("idMunicipio", documentoFiscalEldoc.getIdMunicipio())
                    .addValue("versao", documentoFiscalEldoc.getVersao())
                    .addValue("chaveAcesso", documentoFiscalEldoc.getChaveAcesso())
                    .addValue("chaveAcessoEnviada", documentoFiscalEldoc.getChaveAcessoEnviada())
                    .addValue("tipoEmissao", documentoFiscalEldoc.getTipoEmissao())
                    .addValue("usuarioReg", documentoFiscalEldoc.getUsuarioReg())
                    .addValue("usuario", documentoFiscalEldoc.getUsuario());

            StringBuilder sql = new StringBuilder("insert into docu_eldoc ")
                    .append(" (doel_id_doc_fiscal, doel_tp_doc_fiscal, doel_id_emissor, doel_id_destinatario, doel_nro_doc, doel_serie_doc, doel_ano, ")
                    .append("  doel_dt_emissao_doc, doel_id_estado, doel_id_municipio, doel_versao, doel_chave_acesso, doel_chave_acesso_env, doel_tp_emissao, ")
                    .append("  doel_usuario_reg, doel_datahora_reg, doel_usuario, doel_datahora) ")
                    .append(" values (:id, :tipoDocumentoFiscal, :idEmissor, :idDestinatario, :numeroDocumentoFiscal, :serieDocumentoFiscal, :anoDocumentoFiscal, ")
                    .append("  :dataHoraEmissao, :idEstado, :idMunicipio, :versao, :chaveAcesso, :chaveAcessoEnviada, :tipoEmissao, ")
                    .append("  :usuarioReg, sysdate, :usuario, sysdate) ");

            namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);

        } catch (DuplicateKeyException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FazemuDAOException(e.getMessage(), e);
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FazemuDAOException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new FazemuDAOException(e.getMessage(), e);
        }

        return sequence.longValue();

    }

	@Override
	public List<DocumentoFiscalEldoc> listByFiltros(Map<String, String> parameters)throws Exception {
		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(parameters);
		
		/*StringBuilder query = new StringBuilder()
                .append("select doel.* ")
                .append("     , nvl((select nvl(to_char(dret_tp_evento), 'S') ")
                .append("              from (select dret_tp_evento ")
                .append("                      from documento_retorno ")
                .append("                      where dret_id_doc_fiscal = doel_id_doc_fiscal ")
                .append("                      and dret_tp_servico = 'MANF' ")
                .append("                      order by dret_datahora_reg desc) ")
                .append("             where rownum = 1), 'N') in_manif ")
                .append("     , (select dret_datahora ")
                .append("              from (select dret_datahora ")
                .append("                      from documento_retorno ")
                .append("                      where dret_id_doc_fiscal = doel_id_doc_fiscal ")
                .append("                      and dret_tp_servico    = 'MANF' ")
                .append("                      order by dret_datahora_reg desc) ")
                .append("             where rownum = 1) datahora_manif ")
                .append(" from (select * ")
                .append("         from docu_eldoc ")
                .append("         where doel_tp_doc_fiscal = :tipoDocumentoFiscal ")
                .append("         and exists (select 1 ")
                .append("                        from emissor_raiz emra")
                .append("                        where emra.emra_id_emissor_raiz = substr(lpad(doel_id_destinatario, 14, 0), 0, 8)) ");
		
		if (StringUtils.isNotBlank(parameters.get("idEmissor"))) {
            query.append(" and doel_id_emissor = :idEmissor ");
        }
        if (StringUtils.isNotBlank(parameters.get("idDestinatario"))) {
            query.append(" and doel_id_destinatario = :idDestinatario ");
        }
        if (StringUtils.isNotBlank(parameters.get("idEstado"))) {
            query.append(" and doel_id_estado = :idEstado ");
        }
        if (StringUtils.isNotBlank(parameters.get("chaveAcesso"))) {
            query.append(" and (doel_chave_acesso = nvl(:chaveAcesso, doel_chave_acesso) ")
                    .append(" or  doel_chave_acesso_env = nvl(:chaveAcesso, doel_chave_acesso_env)) ");
        }
        if (StringUtils.isNotBlank(parameters.get("numeroDocumentoFiscal"))) {
            query.append(" and doel_nro_doc = :numeroDocumentoFiscal ");
        }
        if (StringUtils.isNotBlank(parameters.get("numeroInicialDocumentoFiscal"))) {
            query.append("   and doel_nro_doc >= :numeroInicialDocumentoFiscal ");
        }
        if (StringUtils.isNotBlank(parameters.get("numeroFinalDocumentoFiscal"))) {
            query.append(" and doel_nro_doc <= :numeroFinalDocumentoFiscal ");
        }
        if (StringUtils.isNotBlank(parameters.get("serieDocumentoFiscal"))) {
            query.append(" and doel_serie_doc = :serieDocumentoFiscal ");
        }
        if (StringUtils.isNotBlank(parameters.get("tipoEmissao"))) {
            query.append(" and doel_tp_emissao = :tipoEmissao ");
        }
        if (StringUtils.isNotBlank(parameters.get("situacao"))) {
            query.append(" and doel_situacao = :situacao ");
        }
        if (StringUtils.isNotBlank(parameters.get("dataHoraRegistroInicio"))) {
            Date dataHoraRegistroInicio = DateUtils.iso8601ToDate(parameters.get("dataHoraRegistroInicio"));
            sqlParameterSource.addValue("dataHoraRegistroInicio", dataHoraRegistroInicio);
            query.append(" and doel_datahora_reg >= :dataHoraRegistroInicio ");
        }
        if (StringUtils.isNotBlank(parameters.get("dataHoraRegistroFim"))) {
            Date dataHoraRegistroFim = DateUtils.iso8601ToDate(parameters.get("dataHoraRegistroFim"));
            sqlParameterSource.addValue("dataHoraRegistroFim", dataHoraRegistroFim);
            query.append(" and doel_datahora_reg <= :dataHoraRegistroFim ");
        }

        query.append(" order by doel_id_doc_fiscal desc) doel")
        .append(" where rownum <= nvl(:quantidadeRegistros, 50) ");*/
		
		
		//TESTE APAGAR!!! ATENÇÃO
		StringBuilder query = new StringBuilder("select * from docu_eldoc ");
        
        return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, new DocumentoFiscalEldocRowMapper());
		
	}

	@Override
	public DocumentoFiscalEldoc findByChaveAcesso(String chaveAcesso)  {
		LOGGER.debug("DocumentoFiscalEldocJdbcDao: findByChaveAcesso + findByChaveAcessoEnviada");
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("KEY_CHAVE_ACESSO", chaveAcesso);

            StringBuilder query = new StringBuilder("select docu.* ")
                    .append("  from docu_eldoc docu ")
                    .append(" where docu.docu_chave_acesso_env = :chaveAcesso ")
                    .append("    or docu.docu_chave_acesso     = :chaveAcesso ");

            return namedParameterJdbcOperations.queryForObject(query.toString(), sqlParameterSource, documentoFiscalEldocRowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("Documento Fiscal nao encontrado com a chave de acesso " + chaveAcesso);
            throw new NotFoundException("Chave " + chaveAcesso + " n\u00E3o encontrada.");
            
        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar findByChaveAcesso + findByChaveAcessoEnviada ", e);
            throw new FazemuDAOException(e.getMessage(), e).addContextValue("KEY_CHAVE_ACESSO", chaveAcesso);
        }
    }

}
