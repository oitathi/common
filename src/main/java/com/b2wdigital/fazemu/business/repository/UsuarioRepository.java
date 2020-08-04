package com.b2wdigital.fazemu.business.repository;

import com.b2wdigital.fazemu.domain.Usuario;

/**
 *
 * @author dailton.almeida
 */
public interface UsuarioRepository {

    Usuario findById(String idUsuario);
}
