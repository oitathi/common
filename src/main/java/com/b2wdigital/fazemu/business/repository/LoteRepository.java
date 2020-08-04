package com.b2wdigital.fazemu.business.repository;

import java.util.Date;
import java.util.List;

import com.b2wdigital.fazemu.domain.Lote;
import com.b2wdigital.fazemu.enumeration.SituacaoLoteEnum;

/**
 *
 * @author dailton.almeida
 */
public interface LoteRepository {

    Lote findById(Long id);

    Long emitirIdLote();

    int criarLote(Lote lote);

    int fecharLote(Long idLote, String url, String usuario);

    int enviarLote(Long idLote, Long reciboAutorizacao, Integer situacaoAutorizacao, String usuario);

    int cancelarLote(Long idLote, String usuario);

    int reabrirLote(Long idLote, String usuario);

    int finalizarLote(Long idLote, Integer situacaoAutorizacao, String usuario);

    int atualizarDataUltimaConsulta(Long idLote, Date dataUltimaConsulta, String usuario);

    List<Lote> obterLotesPorSituacao(String situacao);

    Integer countSituacaoAutorizacaoById(Long idLote);

    int updateLoteSituacaoAutorizacao(Long idLote, Integer situacaoAutorizacao, String usuario);

    int delete(Long idLote);

    List<Lote> listByIdDocFiscalAndSituacao(Long idDocFiscal, SituacaoLoteEnum situacao);

    List<Lote> listByDateIntervalAndSituacao(Date dataHoraRegistroInicio, Date dataHoraRegistroFim, SituacaoLoteEnum situacao);

    List<Lote> listByDateIntervalAndSituacaoAndIdEstado(Date dataHoraRegistroInicio, Date dataHoraRegistroFim, SituacaoLoteEnum situacao, Long idEstado);

    List<Lote> listByIdDocFiscal(Long idDocFiscal);
}
