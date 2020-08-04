package com.b2wdigital.fazemu.business.repository;

import com.b2wdigital.fazemu.domain.EmissorRaizLogo;

/**
 *
 * @author dailton.almeida
 */
public interface EmissorRaizLogoRepository {

    EmissorRaizLogo findByIdEmissorRaizAndIdLogo(Long idEmissorRaiz, String idLogo);
}
