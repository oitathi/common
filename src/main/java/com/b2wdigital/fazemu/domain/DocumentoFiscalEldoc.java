package com.b2wdigital.fazemu.domain;

import java.util.Date;

import com.b2wdigital.fazemu.utils.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
public class DocumentoFiscalEldoc {

    private Long id;
    private String tipoDocumentoFiscal;
    private Long idEmissor;
    private Long idDestinatario;
    private Long numeroDocumentoFiscal;
    private Long serieDocumentoFiscal;
    private Integer anoDocumentoFiscal;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date dataHoraEmissao;
    private Long idEstado;
    private Long idMunicipio;
    private String versao;
    private String chaveAcesso;
    private String chaveAcessoEnviada;
    private Long tipoEmissao;
    private String usuarioReg;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date dataHoraReg;
    private String usuario;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date dataHora;
    
}
