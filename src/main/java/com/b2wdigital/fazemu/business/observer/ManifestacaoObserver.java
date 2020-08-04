package com.b2wdigital.fazemu.business.observer;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.b2wdigital.fazemu.domain.form.ManifestacaoForm;

public class ManifestacaoObserver implements Observer {
	
	Observable manifestacaoForm;
	List<String> retorno;
	
	public ManifestacaoObserver(Observable manifestacaoForm) {
		this.manifestacaoForm = manifestacaoForm;
		this.manifestacaoForm.addObserver(this);
	}

	@Override
	public void update(Observable manifestacaoFormSubject, Object arg) {
		if(manifestacaoFormSubject instanceof ManifestacaoForm) {
			ManifestacaoForm manifestacaoForm = (ManifestacaoForm) manifestacaoFormSubject;
			retorno = manifestacaoForm.getRetorno();
			if(retorno.size()>1) {
				manifestacaoForm.setRetorno(retorno.subList(0, 1));
			}
			
		}
		
	}

}
