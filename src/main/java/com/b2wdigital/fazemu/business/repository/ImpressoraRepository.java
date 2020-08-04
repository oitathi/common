package com.b2wdigital.fazemu.business.repository;

import java.util.List;

import com.b2wdigital.fazemu.domain.Impressora;

/**
 * Impressora Repository.
 *
 * @author Marcelo Oliveira {marcelo.doliveira@b2wdigital.com}
 * @version 1.0
 */
public interface ImpressoraRepository {

    List<Impressora> listAll();

    List<Impressora> listByFiltros(String nome, String local, String ip, String marca, String modelo, String situacao);

    void insert(Impressora impr);

    int update(Impressora impressora);

    Impressora findById(Long id);
}
