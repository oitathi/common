package com.b2wdigital.fazemu.business.repository;

/**
 *
 * @author dailton.almeida
 */
public interface UrlRepository {

    String getUrl(Integer codigoIBGE, Integer idTipoEmissao, String idServico, String versao);

    String getUrlNormal(Integer codigoIBGE, String idServico, String versao);
}
