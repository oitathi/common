package com.b2wdigital.fazemu.business.service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis Operations Service.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
public interface RedisOperationsService {

    void addToSet(String key, String str);

    void addToSet(String key, Long number);

    void removeFromSet(String key, String str);

    void removeFromSet(String key, Long id);

    Set<Object> members(String key);

    Set<Object> difference(String key1, String key2);

    void expiresKey(String key, Long timeout, TimeUnit timeUnit);

    Set<String> allKeys();
    
    void expiresKeyFromPattern(String pattern);

    Object getKeyValue(String key);

    void setKeyValue(String key, Object value);

    void setKeyValue(String key, Object value, Long timeout, TimeUnit timeUnit);

}
