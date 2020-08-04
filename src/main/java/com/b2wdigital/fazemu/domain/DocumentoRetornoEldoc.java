package com.b2wdigital.fazemu.domain;

import java.util.Date;

import com.b2wdigital.fazemu.utils.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
public class DocumentoRetornoEldoc {

	private Long idDocumentoFiscal;
	private String tipoServico;
	private String tipoEvento;
	private String xml;
	private String url;
    private String usuarioReg;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date dataHoraReg;
    private String usuario;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date dataHora;
    private Long numeroSequencialArquivoNFe;

}
