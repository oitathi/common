package com.b2wdigital.fazemu.domain.form;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 *
 * @author marcelo.doliveira
 */
public class ResponsavelTecnicoForm extends AbstractForm {

    private long idResponsavelTecnico;
    
    @NotBlank(message = "{emissor.not.empty}")
    private String idEmissorRaiz;
    
    @NotBlank(message = "{cnpj.not.empty}")
    private String cnpj;
    
    @NotBlank(message = "{contato.not.empty}")
    private String contato;
    
    @Email(message = "{email.not.valid}")
    private String email;
    
    @NotBlank(message = "{telefone.not.empty}")
    private String telefone;
    
    @NotBlank(message = "{situacao.not.empty")
    private String situacao;
    private String usuario;
    private Date dataHora;

    public long getIdResponsavelTecnico() {
        return idResponsavelTecnico;
    }

    public String getIdEmissorRaiz() {
        return idEmissorRaiz;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getContato() {
        return contato;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getSituacao() {
        return situacao;
    }

    public String getUsuario() {
        return usuario;
    }

    public Date getDataHora() {
        return dataHora;
    }

}
