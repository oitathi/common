package com.b2wdigital.fazemu.business.repository;

import java.util.List;

import com.b2wdigital.fazemu.domain.CodigoRetornoAutorizador;

/**
 *
 * @author dailton.almeida
 */
public interface CodigoRetornoAutorizadorRepository {

    List<CodigoRetornoAutorizador> listAll();

    CodigoRetornoAutorizador findById(Integer cStat);

    String getSituacaoAutorizacaoById(Integer cStat);

    void insert(CodigoRetornoAutorizador codigoRetornoAutorizador);

    int update(CodigoRetornoAutorizador codigoRetornoAutorizador);
}
