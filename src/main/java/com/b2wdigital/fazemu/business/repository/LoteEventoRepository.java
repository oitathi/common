package com.b2wdigital.fazemu.business.repository;

import java.util.Date;
import java.util.List;

import com.b2wdigital.fazemu.domain.LoteEvento;

/**
 *
 * @author dailton.almeida
 */
public interface LoteEventoRepository {

    int insert(Long idLote, String idPonto, String usuario, Long idXml, String observacao);

    Long countByEstadoAndPeriodo(Long idEstado, Date periodo);

    List<LoteEvento> listByEstado(Long idEstado);

    int delete(Long idEvento);

    List<LoteEvento> listByIdLote(Long idLote);
}
