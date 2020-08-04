package com.b2wdigital.fazemu.domain;

import java.util.Date;

import com.b2wdigital.fazemu.utils.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
public class DocumentoFiscal {

    private Long id;
    private String tipoDocumentoFiscal;
    private Long idEmissor;
    private Long idDestinatario;
    private Long numeroDocumentoFiscal;
    private Long serieDocumentoFiscal;
    private Integer anoDocumentoFiscal;
    private Long numeroDocumentoFiscalExterno;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date dataHoraEmissao;
    private Long idEstado;
    private Long idMunicipio;
    private String versao;
    private String chaveAcesso;
    private String chaveAcessoEnviada;
    private Long tipoEmissao;
    private String idPonto;
    private String situacaoAutorizador;
    private String situacaoDocumento;
    private String situacao;
    private String idSistema;
    private String usuarioReg;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date dataHoraReg;
    private String usuario;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date dataHora;
    
    
    private String manifestado;// usado apenas para select de manifestacao
    private Date dataHoraManifestacao; //usado apenas para select de manifestacao



}
