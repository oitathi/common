package com.b2wdigital.fazemu.integration.dao.redis;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.SetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.b2wdigital.fazemu.business.repository.CacheLoteRepository;
import com.b2wdigital.fazemu.domain.ResumoLote;
import com.b2wdigital.fazemu.exception.FazemuDAOException;
import com.b2wdigital.fazemu.utils.LoteUtils;

/**
 *
 * @author dailton.almeida
 */
@Repository
public class CacheLoteRedisDao implements CacheLoteRepository, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheLoteRedisDao.class);
    private static final String CHAVE_LOTES_ABERTOS = "lotesAbertos";
    private static final String CHAVE_LOTES_FECHADOS = "lotesFechados";
    private static final String CHAVE_LOTES_ENVIADOS = "lotesEnviados";
    private static final String CHAVE_LOTES_CONSULTANDO = "lotesConsultando";
    private static final String CHAVE_LOTES_FINALIZADOS = "lotesFinalizados";
    private static final String CHAVE_LOTES_CANCELADOS = "lotesCancelados";
    private static final String CHAVE_LOTES_ERRO = "lotesErro";

    @Autowired
    private RedisOperations<String, Object> redisOperations;

    //"injetado" no afterPropertiesSet
    private ValueOperations<String, Object> valueOperations;
    private SetOperations<String, Object> setOperations;

    @Override
    public void afterPropertiesSet() throws Exception {
        valueOperations = redisOperations.opsForValue();
        setOperations = redisOperations.opsForSet();
    }

    @Override
    public void atualizarLote(ResumoLote lote) { //usado para atualizar informacoes do lote no fluxo, como por exemplo o recibo
        try {
            String keyLote = LoteUtils.getLoteIdRedis(lote.getIdLote());
            valueOperations.set(keyLote, lote);
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public void abrirLote(ResumoLote lote) {
        this.abrirLote(lote, CHAVE_LOTES_ABERTOS);
    }

    @Override
    public void abrirLote(ResumoLote lote, String chaveLotesAbertos) {
        try {
            String keyLote = LoteUtils.getLoteIdRedis(lote.getIdLote());
            ResumoLote result = (ResumoLote) valueOperations.get(keyLote);
            if (result == null) {
                valueOperations.set(keyLote, lote, 2L, TimeUnit.HOURS);
                setOperations.add(chaveLotesAbertos, lote.getIdLote());
            } else {
                throw new FazemuDAOException("NAO PODE ABRIR LOTE EXISTENTE: " + lote);
            }
        } catch (FazemuDAOException e) {
            throw e;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public void adicionarAoLote(ResumoLote lote) {
        try {
            String keyLote = LoteUtils.getLoteIdRedis(lote.getIdLote());
            ResumoLote result = (ResumoLote) valueOperations.get(keyLote);
            if (result == null) {
                throw new FazemuDAOException("NAO PODE ADICIONAR DOCUMENTO A LOTE INEXISTENTE");
            } else {
                valueOperations.set(keyLote, lote);
            }
        } catch (FazemuDAOException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    // metodos que mudam o lote de status
    @Override
    public void fecharLote(Long idLote) {
        moverLote(idLote, CHAVE_LOTES_ABERTOS, CHAVE_LOTES_FECHADOS);
    }

    @Override
    public void enviarLote(Long idLote) {
        moverLote(idLote, CHAVE_LOTES_FECHADOS, CHAVE_LOTES_ENVIADOS);
    }

    @Override
    public void consultandoLote(Long idLote) {
        moverLote(idLote, CHAVE_LOTES_ENVIADOS, CHAVE_LOTES_CONSULTANDO);
    }

    @Override
    public void desconsultandoLote(Long idLote) {
        moverLote(idLote, CHAVE_LOTES_CONSULTANDO, CHAVE_LOTES_ENVIADOS);
    }

    @Override
    public void finalizarLote(Long idLote) {
        moverLote(idLote, CHAVE_LOTES_CONSULTANDO, CHAVE_LOTES_FINALIZADOS);
    }

    @Override
    public void finalizarFechadosLote(Long idLote) {
        moverLote(idLote, CHAVE_LOTES_FECHADOS, CHAVE_LOTES_FINALIZADOS);
    }

    @Override
    public void cancelarLoteFechado(Long idLote) {
        moverLote(idLote, CHAVE_LOTES_FECHADOS, CHAVE_LOTES_CANCELADOS);
    }

    @Override
    public void cancelarLoteConsultando(Long idLote) {
        moverLote(idLote, CHAVE_LOTES_CONSULTANDO, CHAVE_LOTES_CANCELADOS);
    }

    public void moverLote(Long idLote, String setOrigem, String setDestino) {
        LOGGER.info("Movendo lote {} de {} para {}", idLote, setOrigem, setDestino);
        try {
            setOperations.move(setOrigem, idLote, setDestino);
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    // removendo lote
    @Override
    public void removerLoteAberto(Long idLote) {
        apagarLote(idLote, CHAVE_LOTES_ABERTOS);
    }

    @Override
    public void removerLoteFechado(Long idLote) {
        apagarLote(idLote, CHAVE_LOTES_FECHADOS);
    }

    @Override
    public void removerLoteEnviado(Long idLote) {
        apagarLote(idLote, CHAVE_LOTES_ENVIADOS);
    }

    @Override
    public void removerLoteFinalizado(Long idLote) {
        apagarLote(idLote, CHAVE_LOTES_FINALIZADOS);
    }

    @Override
    public void removerLoteCancelado(Long idLote) {
        apagarLote(idLote, CHAVE_LOTES_CANCELADOS);
    }

    protected void apagarLote(Long idLote, String set) {
        LOGGER.info("apagarLote idLote {} do set {}", idLote, set);
        try {
            String keyLote = LoteUtils.getLoteIdRedis(idLote);
            this.deleteKey(keyLote);
            setOperations.remove(set, idLote);
        } catch (Exception e) {
            LOGGER.error("ERRO ao apagarLote idLote {} do set {}", idLote, set);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public void adicionarLoteEnviado(Long idLote) {
        try {
            setOperations.add(CHAVE_LOTES_ENVIADOS, idLote);
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    // consultas de lote
    @Override
    public ResumoLote consultarLote(Long idLote) {
        try {
            String keyLote = LoteUtils.getLoteIdRedis(idLote);
            ResumoLote result = (ResumoLote) valueOperations.get(keyLote);
            LOGGER.debug("result {}", result);
            return result;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public Set<Object> obterLotesAbertos() {
        return setOperations.members(CHAVE_LOTES_ABERTOS);
    }

    @Override
    public Set<Object> obterLotesFechados() {
        return setOperations.members(CHAVE_LOTES_FECHADOS);
    }

    @Override
    public Set<Object> obterLotesEnviados() {
        return setOperations.members(CHAVE_LOTES_ENVIADOS);
    }

    @Override
    public Set<Object> obterLotesConsultando() {
        return setOperations.members(CHAVE_LOTES_CONSULTANDO);
    }

    @Override
    public Set<Object> obterLotesFinalizados() {
        return setOperations.members(CHAVE_LOTES_FINALIZADOS);
    }

    @Override
    public Set<Object> obterLotesCancelados() {
        return setOperations.members(CHAVE_LOTES_CANCELADOS);
    }

    @Override
    public Set<Object> obterLotesErro() {
        return setOperations.members(CHAVE_LOTES_ERRO);
    }

    @Override
    public void sobreporLotesAbertos(String chaveTemporaria) {
        moverTodos(chaveTemporaria, CHAVE_LOTES_ABERTOS);
    }

    @Override
    public void sobreporLotesEnviados(String chaveTemporaria) {
        moverTodos(chaveTemporaria, CHAVE_LOTES_ENVIADOS);
    }

    protected void moverTodos(String chaveTemporaria, String chaveFinal) {
        try {
            SetUtils.emptyIfNull(setOperations.members(chaveTemporaria))
                    .stream()
                    .forEach(o -> setOperations.move(chaveTemporaria, o, chaveFinal));
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    public Boolean setIfAbsent(String key, Object obj, long duration, TimeUnit timeUnit) {
        return valueOperations.setIfAbsent(key, obj, duration, timeUnit);
    }

    public Boolean deleteKey(String key) {
        return redisOperations.delete(key);
    }

}
