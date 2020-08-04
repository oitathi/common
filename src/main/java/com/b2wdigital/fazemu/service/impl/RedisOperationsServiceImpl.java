package com.b2wdigital.fazemu.service.impl;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.b2wdigital.fazemu.business.repository.RedisOperationsRepository;
import com.b2wdigital.fazemu.business.service.RedisOperationsService;

/**
 * Redis Operations Service.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
@Service
public class RedisOperationsServiceImpl implements RedisOperationsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisOperationsServiceImpl.class);

    @Autowired
    private RedisOperationsRepository redisOperationsRepository;

    @Override
    public void addToSet(String key, String str) {
        LOGGER.debug("add key {} str {}", key, str);
        redisOperationsRepository.addToSet(key, str);
    }

    @Override
    public void addToSet(String key, Long number) {
        LOGGER.debug("add key {} number {}", key, number);
        redisOperationsRepository.addToSet(key, number);
    }

    @Override
    public void removeFromSet(String key, String str) {
        redisOperationsRepository.removeFromSet(key, str);
    }

    @Override
    public void removeFromSet(String key, Long id) {
        redisOperationsRepository.removeFromSet(key, id);
    }

    @Override
    public Set<Object> members(String key) {
        return redisOperationsRepository.members(key);
    }

    @Override
    public Set<Object> difference(String key1, String key2) {
        return redisOperationsRepository.difference(key1, key2);
    }

    @Override
    public void expiresKey(String key, Long timeout, TimeUnit timeUnit) {
        redisOperationsRepository.expiresKey(key, timeout, timeUnit);
    }

    @Override
    public Set<String> allKeys() {
        return redisOperationsRepository.allKeys();
    }
    
    @Override
    public void expiresKeyFromPattern(String pattern) {
    	Set<String> keys = redisOperationsRepository.allKeysByPattern("*" + pattern + "*");
    	
    	if(!keys.isEmpty()) {
    		for (String key : keys) {
    			LOGGER.info("expiresKeyFromPattern - key {} ", key);
    			redisOperationsRepository.expiresKey(key, 1L, TimeUnit.MILLISECONDS);
			}
    	}
    }

    @Override
    public Object getKeyValue(String key) {
        return redisOperationsRepository.getKeyValue(key);
    }

    @Override
    public void setKeyValue(String key, Object value) {
        redisOperationsRepository.setKeyValue(key, value);
    }

    @Override
    public void setKeyValue(String key, Object value, Long timeout, TimeUnit timeUnit) {
        redisOperationsRepository.setKeyValue(key, value, timeout, timeUnit);
    }

}
