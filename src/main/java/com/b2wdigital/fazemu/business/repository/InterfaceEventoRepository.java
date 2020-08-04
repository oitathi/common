package com.b2wdigital.fazemu.business.repository;

import java.util.Date;
import java.util.List;

import com.b2wdigital.fazemu.domain.InterfaceEvento;

/**
 * InterfaceEvento Repository.
 *
 * @author Marcelo Oliveira {marcelo.doliveira@b2wdigital.com}
 * @version 1.0
 */
public interface InterfaceEventoRepository {

    List<InterfaceEvento> listByFiltros(String idSistema, Long idMetodo, String chaveAcesso, Date dataHoraRegistroInicio, Date dataHoraRegistroFim, String situacao, Long quantidadeRegistros);

    Long insert(InterfaceEvento ie);

    InterfaceEvento findById(Long id);

    int update(Long idInterfaceEvento, String observacao, String situacao, String usuario);

    int updateDataHora(Long idInterfaceEvento);

    int deleteByIdDocFiscalAndDataHoraInicio(Long idDocFiscal);

    List<InterfaceEvento> listByDateInterval(Date dataHoraRegistroInicio, Date dataHoraRegistroFim, String excludeList);

}
