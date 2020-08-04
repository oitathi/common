package com.b2wdigital.fazemu.domain;

import java.util.Date;

import lombok.Data;

@Data
public class EmissorRaiz {

    private Long id;
    private String nome;
    private String situacao;
    private String usuario;
    private Date dataHora;
    private Long idImpressora;

}
