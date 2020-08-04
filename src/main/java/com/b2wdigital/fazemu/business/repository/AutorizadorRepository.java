package com.b2wdigital.fazemu.business.repository;

import java.util.List;

import com.b2wdigital.fazemu.domain.Autorizador;

/**
 * Autorizador Repository.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
public interface AutorizadorRepository {

    List<Autorizador> listAtivosByTipoDocumentoFiscal(String tipoDocumentoFiscal);

    Autorizador findById(Long id);

    Autorizador findByTipoDocumentoFiscalAndCodigoExterno(String tipoDocumentoFiscal, String codigoExterno);
}
