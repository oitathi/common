package com.b2wdigital.fazemu.business.repository;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.b2wdigital.fazemu.domain.ResumoLote;

/**
 *
 * @author dailton.almeida
 */
public interface CacheLoteRepository {

    void atualizarLote(ResumoLote lote);

    //movimentacoes de status
    void abrirLote(ResumoLote lote);

    void abrirLote(ResumoLote lote, String chaveLotesAbertos);

    void adicionarAoLote(ResumoLote lote);

    void fecharLote(Long idLote);

    void enviarLote(Long idLote); //no contexto do cache, "enviar" lote eh somente marcar como enviado

    void consultandoLote(Long idLote); //no contexto do cache, "consultando" lote eh somente marcar como consultando

    void desconsultandoLote(Long idLote); //no contexto do cache, "desconsultando" lote eh somente remarcar como enviado

    void finalizarLote(Long idLote);

    void finalizarFechadosLote(Long idLote);

    void cancelarLoteFechado(Long idLote);

    void cancelarLoteConsultando(Long idLote);

    void removerLoteFinalizado(Long idLote);

    void removerLoteCancelado(Long idLote);

    void removerLoteAberto(Long idLote);

    void removerLoteFechado(Long idLote);

    void removerLoteEnviado(Long idLote);

    void adicionarLoteEnviado(Long idLote);

    void moverLote(Long idLote, String setOrigem, String setDestino);

    //consultas
    Set<Object> obterLotesAbertos();

    Set<Object> obterLotesFechados();

    Set<Object> obterLotesEnviados();

    Set<Object> obterLotesConsultando();

    Set<Object> obterLotesFinalizados();

    Set<Object> obterLotesCancelados();

    Set<Object> obterLotesErro();

    ResumoLote consultarLote(Long idLote);

    //contingencias - construindo cache a partir do banco
    void sobreporLotesAbertos(String chaveTemporaria);

    void sobreporLotesEnviados(String chaveTemporaria);

    Boolean setIfAbsent(String key, Object obj, long duration, TimeUnit timeUnit);

    Boolean deleteKey(String key);

}
