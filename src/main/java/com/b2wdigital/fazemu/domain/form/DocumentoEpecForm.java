package com.b2wdigital.fazemu.domain.form;

import java.text.ParseException;

import com.b2wdigital.fazemu.domain.DocumentoEpec;
import com.b2wdigital.fazemu.exception.FazemuServiceException;
import com.b2wdigital.fazemu.utils.DateUtils;

import lombok.Data;


@Data
public class DocumentoEpecForm {
		
	private String idDocumentoFiscal;
	private String idEstado;
	private String situacao;
	private String observacao; 
	private String dataHoraRegInicio;
	private String dataHora;
	
	
	public DocumentoEpecForm() {
		
	}
	
	public DocumentoEpecForm (DocumentoEpec doc)  {
		this.idDocumentoFiscal = String.valueOf(doc.getIdDocumentoFiscal());
		this.idEstado = String.valueOf(doc.getIdEstado());
		this.situacao = doc.getSituacao();
		this.observacao = doc.getObservacao();
		try {
			this.dataHoraRegInicio = DateUtils.convertDateToString(doc.getDataHoraReg());
			this.dataHora = DateUtils.convertDateToString(doc.getDataHora());
		} catch (ParseException e) {
			throw new FazemuServiceException("Erro conversão de data");
		}
	}
	
	

}
