package com.b2wdigital.fazemu.domain.form;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class EmissorRaizForm extends AbstractForm {
	@NotBlank(message = "{emissor.not.empty}")
	private String id;
	
	@NotBlank(message = "{name.not.empty}")
	private String nomeEmissorRaiz;
	
	@NotBlank(message = "{situacao.not.empty}")
	private String situacao;
	
	private String usuario;
	
}