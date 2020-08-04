package com.b2wdigital.fazemu.integration.dao.redis;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.b2wdigital.fazemu.business.repository.RedisOperationsRepository;
import com.b2wdigital.fazemu.exception.FazemuDAOException;

@Repository
public class RedisOperationsDao implements RedisOperationsRepository, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisOperationsDao.class);

    //NFe
    public static final String KEY_SEMAFORO_FECHAR_LOTES_ABERTOS                = "semaforo01";
    //public static final String KEY_SEMAFORO_CONSULTAR_RECIBOS                 = "semaforo02";
    public static final String KEY_SEMAFORO_REENVIAR_LOTES_FECHADOS             = "semaforo03";
    public static final String KEY_SEMAFORO_ENVIAR_EPEC_AUTORIZADOR_NORMAL      = "semaforo04";
    public static final String KEY_SEMAFORO_ENVIAR_CALLBACK_CONTINGENCIA        = "semaforo05";
    public static final String KEY_SEMAFORO_REEMITIR_LOTE_DOCUMENTOS_ABERTOS    = "semaforo06";
    public static final String KEY_SEMAFORO_RECONSTRUIR_LOTES_ENVIADOS_PARADOS  = "semaforo07";
    //public static final String KEY_SEMAFORO_CONSULTAR_DOCUMENTOS_REJEITADOS   = "semaforo08";
    public static final String KEY_SEMAFORO_ENTRAR_EPEC_AUTOMATICO              = "semaforo09";
    public static final String KEY_SEMAFORO_CANCELAR_LOTES_PERDIDOS             = "semaforo10";
    public static final String KEY_SEMAFORO_STORAGE_DOCUMENTOS                  = "semaforo11";
    public static final String KEY_SEMAFORO_GERAR_CALLBACK_FALTANTE             = "semaforo12";
    public static final String KEY_SEMAFORO_GERAR_XML_FALTANTE                  = "semaforo13";
    public static final String KEY_SEMAFORO_GERAR_EVENTO_FALTANTE               = "semaforo14";
    public static final String KEY_SEMAFORO_DOWNLOAD_XML_MANIFESTADO            = "semafoto15";
    public static final String KEY_SEMAFORO_LIMPAR_REGISTROS                    = "semaforo16";

    //NFSe
    public static final String KEY_SEMAFORO_ENVIAR_DOCUMENTOS_REJEITADOS        = "semaforo21";
    public static final String KEY_SEMAFORO_CONSULTAR_RECIBO_FALTANTE           = "semaforo22";
    public static final String KEY_SEMAFORO_ENVIAR_DOCUMENTOS_PARADOS           = "semaforo23";

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
    public void addToSet(String key, String str) {
        try {
            LOGGER.debug("add key {} str {} ", key, str);

            setOperations.add(key, str);
        } catch (FazemuDAOException e) {
            throw e;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public void addToSet(String key, Long number) {
        try {
            LOGGER.debug("add key {} number {} ", key, number);

            setOperations.add(key, number);
        } catch (FazemuDAOException e) {
            throw e;
        } catch (Exception e) {
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public Set<Object> members(String key) {
        return setOperations.members(key);
    }

    @Override
    public void removeFromSet(String key, String str) {
        try {
            LOGGER.debug("remove key {} str {} ", key, str);

            setOperations.remove(key, str);

        } catch (Exception e) {
            LOGGER.debug("Error remove key {} str {} ", key, str);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public void removeFromSet(String key, Long number) {
        try {
            LOGGER.debug("remove key {} number {} ", key, number);

            setOperations.remove(key, number);
        } catch (Exception e) {
            LOGGER.debug("Error remove key {} number {} ", key, number);
            throw new FazemuDAOException(e.getMessage(), e);
        }
    }

    @Override
    public Set<Object> difference(String key1, String key2) {
        LOGGER.debug("difference key1 {} key2 {} ", key1, key2);
        return setOperations.difference(key1, key2);
    }

    @Override
    public void expiresKey(String key, Long timeout, TimeUnit timeUnit) {
        LOGGER.debug("expiresKey key {} timeout {} timeUnit {} ", key, timeout, timeUnit);
        redisOperations.expire(key, timeout, timeUnit);
    }

    @Override
    public Set<String> allKeys() {
        LOGGER.debug("allKeys ");

        return redisOperations.keys("*");
    }

    @Override
    public Set<String> allKeysByPattern(String pattern) {
        LOGGER.debug("allKeysByPattern {} ", pattern);

        return redisOperations.keys(pattern);
    }

    @Override
    public Object getKeyValue(String key) {
        return valueOperations.get(key);
    }

    @Override
    public void setKeyValue(String key, Object value) {
        valueOperations.set(key, value);
    }

    @Override
    public void setKeyValue(String key, Object value, Long timeout, TimeUnit timeUnit) {
        valueOperations.set(key, value, timeout, timeUnit);
    }

}
