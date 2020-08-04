package com.b2wdigital.fazemu.domain;

import java.util.Date;

import lombok.Data;

@Data
public class EmissorRaizFilial {

    private Long idEmissorRaiz;
    private Long idFilial;
    private String nome;
    private String inConsultaDocumento;
    private String ultimoNSU;
    private String chaveAutenticacao;
    private String usuario;
    private Date dataHora;

}
