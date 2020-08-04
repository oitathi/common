package com.b2wdigital.fazemu.business.repository;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface RedisOperationsRepository {

	void addToSet(String key, String str);

	void addToSet(String key, Long number);

	Set<Object> members(String key);

	void removeFromSet(String key, String str);

	void removeFromSet(String key, Long number);

	Set<Object> difference(String key1, String key2);

	void expiresKey(String key, Long timeout, TimeUnit timeUnit);
        
	Set<String> allKeys();

	Set<String> allKeysByPattern(String pattern);

	Object getKeyValue(String key);
	
	void setKeyValue(String key, Object value);
	
	void setKeyValue(String key, Object value, Long timeout, TimeUnit timeUnit);
}
