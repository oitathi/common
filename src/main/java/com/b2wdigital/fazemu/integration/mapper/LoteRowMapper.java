package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.Lote;

@Component("loteRowMapper")
public class LoteRowMapper extends AbstractRowMapper implements RowMapper<Lote> {
    
    public static final String ID                       = "LOTE_ID_LOTE";
    public static final String TIPO_DOCUMENTO_FISCAL    = "LOTE_TP_DOC_FISCAL";
    public static final String TIPO_EMISSAO             = "LOTE_TP_EMISSAO";
    public static final String ID_EMISSOR_RAIZ          = "LOTE_ID_EMISSOR_RAIZ";
    public static final String ID_ESTADO                = "LOTE_ID_ESTADO";
    public static final String ID_MUNICIPIO             = "LOTE_ID_MUNICIPIO";
    public static final String TP_SERVICO               = "LOTE_TP_SERVICO";
    public static final String VERSAO                   = "LOTE_VERSAO";
    public static final String URL                      = "LOTE_URL";
    public static final String ID_PONTO                 = "LOTE_ID_PONTO";
    public static final String DATA_ULTIMA_CONSULTA     = "LOTE_DT_ULT_CONSULTA";
    public static final String RECIBO_AUTORIZADOR       = "LOTE_RECIBO_AUTOR";
    public static final String SITUACAO_AUTORIZADOR     = "LOTE_SITUACAO_AUTOR";
    public static final String SITUACAO                 = "LOTE_SITUACAO";
    public static final String USUARIO_REG              = "LOTE_USUARIO_REG";
    public static final String DATAHORA_REG             = "LOTE_DATAHORA_REG";
    public static final String USUARIO                  = "LOTE_USUARIO";
    public static final String DATAHORA                 = "LOTE_DATAHORA";

    @Override
    public Lote mapRow(ResultSet rs, int row) throws SQLException {
        Lote result = new Lote();
        result.setId(rs.getLong(ID));
        result.setTipoDocumentoFiscal(rs.getString(TIPO_DOCUMENTO_FISCAL));
        result.setTipoEmissao(rs.getLong(TIPO_EMISSAO));
        result.setIdEmissorRaiz(rs.getLong(ID_EMISSOR_RAIZ));
        result.setIdEstado(rs.getLong(ID_ESTADO));
        result.setIdMunicipio(rs.getLong(ID_MUNICIPIO));
        result.setServico(rs.getString(TP_SERVICO));
        result.setVersao(rs.getString(VERSAO));
        result.setUrl(rs.getString(URL));
        result.setIdPonto(rs.getString(ID_PONTO));
        result.setDataUltimaConsulta(rs.getTimestamp(DATA_ULTIMA_CONSULTA));
        result.setReciboAutorizacao(getLongOrNull(rs, RECIBO_AUTORIZADOR));
        result.setSituacaoAutorizacao(getIntOrNull(rs, SITUACAO_AUTORIZADOR));
        result.setSituacao(rs.getString(SITUACAO));
        result.setUsuarioReg(rs.getString(USUARIO_REG));
        result.setDataHoraReg(rs.getTimestamp(DATAHORA_REG));
        result.setUsuario(rs.getString(USUARIO));
        result.setDataHora(rs.getTimestamp(DATAHORA));

        return result;
    }

}
