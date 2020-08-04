package com.b2wdigital.fazemu.domain.form;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class EmissorRaizFilialForm {
	
	@NotBlank(message = "{name.not.empty}")
	private String nome;
	
	@NotBlank(message = "{filial.not.empty}")
	private String filial;
	
	@NotBlank(message = "{consultaDocumento.not.empty}")
	private String inConsultaDocumento;
	
	@NotBlank(message = "{emissorRaiz.not.empty}")
	private String idEmissorRaiz;
	
	@NotBlank(message = "{chaveSeguranca.not.empty}")
	private String chaveAutenticacao;
	
	
	private String nomeEmissorRaiz;
	private String usuario;
	private String retorno;
	private boolean success;
	
	
	

}
