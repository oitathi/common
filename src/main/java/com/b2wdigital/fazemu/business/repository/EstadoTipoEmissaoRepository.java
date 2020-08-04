package com.b2wdigital.fazemu.business.repository;

import java.util.Date;
import java.util.List;

import com.b2wdigital.fazemu.domain.EstadoTipoEmissao;

/**
 * Estado Tipo Emissao Repository.
 *
 * @author Marcelo Oliveira {marcelo.doliveira@b2wdigital.com}
 * @version 1.0
 */
public interface EstadoTipoEmissaoRepository {

    List<EstadoTipoEmissao> listAll();

    List<EstadoTipoEmissao> listByEstadoAndTipoEmissao(Long idEstado, Integer tipoEmissao);

    List<EstadoTipoEmissao> listByIdEstado(Long idEstado);

    List<EstadoTipoEmissao> listByFiltros(Long idEstado, Date dataHoraInicio, Date dataFim, Integer tipoEmissao);

    EstadoTipoEmissao findByTipoEmissao(Long tipoEmissao);

    EstadoTipoEmissao findByCodigoIBGETipoEmissaoAndDataHora(Integer codigoIbge, Integer tipoEmissao, Date dataHora);

    List<EstadoTipoEmissao> listByTipoEmissaoAtivo();

    void insert(EstadoTipoEmissao este);

    int update(EstadoTipoEmissao estadoTipoEmissao);
}
