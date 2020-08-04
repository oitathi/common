package com.b2wdigital.fazemu.integration.dao.jdbc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.b2wdigital.fazemu.business.repository.DocumentoRetornoEldocRepository;
import com.b2wdigital.fazemu.domain.DocumentoRetorno;
import com.b2wdigital.fazemu.domain.DocumentoRetornoEldoc;
import com.b2wdigital.fazemu.exception.FazemuDAOException;
import com.b2wdigital.fazemu.integration.mapper.DocumentoRetornoEldocRowMapper;

@Repository
public class DocumentoRetornoEldocJdbcDao extends AbstractDao implements DocumentoRetornoEldocRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoRetornoEldocJdbcDao.class);
    
    @Autowired
    @Qualifier("documentoRetornoEldocRowMapper")
    private RowMapper<DocumentoRetornoEldoc> documentoRetornoEldocRowMapper;

    @Override
    public int insert(DocumentoRetornoEldoc drel) {
        try {
            LOGGER.debug("insert idDocFiscal {}", drel.getIdDocumentoFiscal());
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idDocFiscal", drel.getIdDocumentoFiscal())
                    .addValue("tipoServico", drel.getTipoServico())
                    .addValue("tipoEvento", drel.getTipoEvento())
                    .addValue("clob", drel.getXml())
                    .addValue("url", drel.getUrl())
                    .addValue("usuarioReg", drel.getUsuarioReg())
                    .addValue("usuario", drel.getUsuario())
                    .addValue("numeroSequencialNFe", drel.getNumeroSequencialArquivoNFe());

            StringBuilder sql = new StringBuilder(" insert into dret_eldoc ")
                    .append(" (drel_id_doc_fiscal, drel_tp_servico, drel_tp_evento, drel_xml, drel_url, drel_usuario_reg, drel_datahora_reg, drel_usuario, drel_datahora, drel_nro_seq_nf) ")
                    .append(" values (:idDocFiscal, :tipoServico, :tipoEvento, :clob, :url, :usuarioReg, sysdate, :usuario, sysdate, :numeroSequencialNFe) ");

            return namedParameterJdbcOperations.update(sql.toString(), sqlParameterSource);

        } catch (DuplicateKeyException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new FazemuDAOException(e.getMessage(), e);
        }

        return 0;
    }

	@Override
	public List<DocumentoRetornoEldoc> findByIdDocFiscal(Long idDocFiscal) {
		try {
            LOGGER.debug("findByIdDocFiscal");
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("idDocFiscal", idDocFiscal);

            StringBuilder query = new StringBuilder("select dret.* ")
                    .append("  from dret_eldoc dret ")
                    .append(" where dret.dret_id_doc_fiscal = :idDocFiscal ")
                    .append(" order by dret.dret_datahora_reg ");

            return namedParameterJdbcOperations.query(query.toString(), sqlParameterSource, documentoRetornoEldocRowMapper);

        } catch (DataAccessException e) {
            LOGGER.error("Erro de acesso aos dados ao tentar executar findByIdDocFiscal", e);
            throw new FazemuDAOException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Erro ao tentar executar findByIdDocFiscal", e);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

}
