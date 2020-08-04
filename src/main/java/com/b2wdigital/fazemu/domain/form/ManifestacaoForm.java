package com.b2wdigital.fazemu.domain.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ManifestacaoForm extends Observable {
	
	@NotBlank(message = "{chaveDeAcesso.not.empty}")
	private String chaveAcesso;
	
	@NotBlank(message = "{usuario.not.empty}")
	private String usuario;
	
	private String justificativa;
	
	@NotBlank(message = "{tpEvento.not.empty}")
	private String tpEvento;
	
	private boolean success;
	
	private List<String> retorno = new ArrayList<String>();
	
	
	public void adicionaRetorno(String retorno) {
		this.retorno.add(retorno);
		setChanged();
		notifyObservers();
	}
	
	
	
	

}
