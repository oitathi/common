package com.b2wdigital.fazemu.business.repository;

import java.util.List;

import com.b2wdigital.fazemu.domain.WsMetodo;

/**
 * WsMetodo Repository.
 *
 * @author Marcelo Oliveira {marcelo.doliveira@b2wdigital.com}
 * @version 1.0
 */
public interface WsMetodoRepository {

    List<WsMetodo> listAll();

    WsMetodo findById(Long id);
}
