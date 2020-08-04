package com.b2wdigital.fazemu.integration.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.b2wdigital.fazemu.domain.DocumentoFiscal;

public class DocumentoFiscalManifRowMapper extends DocumentoFiscalRowMapper {
	
	private final String IN_MANIF = "IN_MANIF";
	private final String DATA_HORA_MANIF = "DATAHORA_MANIF";
	
	@Override
    public DocumentoFiscal mapRow(ResultSet rs, int row) throws SQLException {
		DocumentoFiscal result = super.mapRow(rs, row);
		result.setManifestado(rs.getString(IN_MANIF));
		result.setDataHoraManifestacao(rs.getTimestamp(DATA_HORA_MANIF));
		return result;
		
	}

}
