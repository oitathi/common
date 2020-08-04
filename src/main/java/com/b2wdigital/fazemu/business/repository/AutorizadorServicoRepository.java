package com.b2wdigital.fazemu.business.repository;

import com.b2wdigital.fazemu.domain.AutorizadorServico;

/**
 * Autorizador Servico Repository.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
public interface AutorizadorServicoRepository {

    AutorizadorServico findByIdAutorizadorAndIdServico(Long IdAutorizador, String idServico);
}
