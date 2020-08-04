package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.b2wdigital.fazemu.domain.WsMetodo;

/**
 * WsMetodo RowMapper.
 * 
 * @author Marcelo Oliveira {marcelo.doliveira@b2wdigital.com}
 * @version 1.0
 */

@Component("wsMetodoRowMapper")
public class WsMetodoRowMapper extends AbstractRowMapper implements RowMapper<WsMetodo> {
	
    public static final String ID 		= "WSME_ID_METODO";
    public static final String NOME		= "WSME_NOME";
    public static final String DESCRICAO	= "WSME_DESCRICAO";
    public static final String URL		= "WSME_URL";
    public static final String ACTION		= "WSME_ACTION";
    public static final String NAMESPACE	= "WSME_NAMESPACE";
    public static final String RESPONSE		= "WSME_RESPONSE";
    public static final String SOAP_ACTION	= "WSME_SOAP_ACTION";
    public static final String WS_NAME		= "WSME_WS_NAME";
    public static final String ENVELOPE_TAG	= "WSME_ENVELOPE_TAG";
    public static final String IN_SSL		= "WSME_IN_SSL";
    public static final String WALLET_PATH	= "WSME_WALLET_PATH";
    public static final String WALLET_PASSWORD	= "WSME_WALLET_PASSWORD";
    public static final String TP_AUTORIZACAO	= "WSME_TP_AUTORIZACAO";
    public static final String USERNAME		= "WSME_USERNAME";
    public static final String PASSWORD		= "WSME_PASSWORD";
    public static final String USUARIO		= "WSME_USUARIO";
    public static final String DATAHORA		= "WSME_DATAHORA";
    public static final String TIPO_RETORNO	= "WSME_TP_RETORNO";

    @Override
    public WsMetodo mapRow(ResultSet rs, int row) throws SQLException {
    	WsMetodo result = new WsMetodo();
        result.setId(rs.getLong(ID));
        result.setNome(rs.getString(NOME));
        result.setDescricao(rs.getString(DESCRICAO));
        result.setUrl(rs.getString(URL));
        result.setAction(rs.getString(ACTION));
        result.setNamespace(rs.getString(NAMESPACE));
        result.setResponse(rs.getString(RESPONSE));
        result.setSoapAction(rs.getString(SOAP_ACTION));
        result.setWsName(rs.getString(WS_NAME));
        result.setEnvelopeTag(rs.getString(ENVELOPE_TAG));
        result.setInSsl(rs.getString(IN_SSL));
        result.setWalletPath(rs.getString(WALLET_PATH));
        result.setWalletPassword(rs.getString(WALLET_PASSWORD));
        result.setTipoAutorizacao(rs.getString(TP_AUTORIZACAO));
        result.setUsername(rs.getString(USERNAME));
        result.setPassword(rs.getString(PASSWORD));
        result.setUsuario(rs.getString(USUARIO));
        result.setDataHora(rs.getTimestamp(DATAHORA));
        result.setTipoRetorno(rs.getString(TIPO_RETORNO));
        return result;
    }
    
}
