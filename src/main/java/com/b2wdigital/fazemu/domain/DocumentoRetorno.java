package com.b2wdigital.fazemu.domain;

import java.util.Date;

import lombok.Data;

@Data
public class DocumentoRetorno {

    private Long idDocumentoFiscal;
    private String tipoServico;
    private Long tipoEvento;
    private Long idXml;
    private String url;
    private String usuarioReg;
    private Date dataHoraReg;
    private String usuario;
    private Date dataHora;

}
