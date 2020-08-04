package com.b2wdigital.fazemu.domain;

import java.util.Date;

import lombok.Data;

@Data
public class DocumentoFiscalToStorage {

    private Long idDocumentoFiscal;
    private String tipoDocumentoFiscal;
    private Long idEmissor;
    private Long idDestinatario;
    private Long numeroDocumentoFiscal;
    private Long serieDocumentoFiscal;
    private Integer anoDocumentoFiscal;
    private Date dataHoraEmissao;
    private Long idEstado;
    private String versao;
    private String chaveAcesso;
    private String chaveAcessoEnviada;
    private Integer tipoEmissao;
    private Date documentoFiscalDataHoraRegistro;
    private String tipoServico;
    private Long tipoEvento;
    private Date documentoRetornoDataHoraRegistro;
    private String clob;

}
