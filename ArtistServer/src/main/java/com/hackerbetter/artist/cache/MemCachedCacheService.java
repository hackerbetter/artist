package com.hackerbetter.artist.cache;

import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

public class MemCachedCacheService implements CacheService {
	private static final Logger logger=LoggerFactory.getLogger(MemCachedCacheService.class);

	MemcachedClient memcachedClient;
	
	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	public <T> void  set(String key, T t) {
		try {
			boolean success = memcachedClient.set(key, 0, t);
			if(!success) {
			    logger.error("memcache set not success, key:" + key + ", value:" +t.toString());
			}
		} catch (TimeoutException e) {
			logger.error("memcache set操作异常",e);
		} catch (InterruptedException e) {
			logger.error("memcache set操作异常",e);
		} catch (MemcachedException e) {
			logger.error("memcache set操作异常",e);
		}
	}

	public <T> void set(String key, Integer exp, T t) {
		if (exp == null) {
			exp = 0;
		}
		try {
			memcachedClient.set(key, exp, t);
		} catch (TimeoutException e) {
			logger.error("memcache set操作异常",e);
		} catch (InterruptedException e) {
			logger.error("memcache set操作异常",e);
		} catch (MemcachedException e) {
			logger.error("memcache set操作异常",e);
		}
	}

	public <T> T get(String key) {
		T t = null;
		try {
			t = memcachedClient.get(key);
		} catch (TimeoutException e) {
			logger.error("memcache get操作异常",e);
		} catch (InterruptedException e) {
			logger.error("memcache get操作异常",e);
		} catch (MemcachedException e) {
			logger.error("memcache get操作异常",e);
		}
		return t;
	}

	public <T> void checkToSet(String key, T t) {
		T temp = null;
		try {
			temp = memcachedClient.get(key);
		} catch (TimeoutException e) {
			logger.error("memcache checkToSet操作异常",e);
		} catch (InterruptedException e) {
			logger.error("memcache checkToSet操作异常",e);
		} catch (MemcachedException e) {
			logger.error("memcache checkToSet操作异常",e);
		}
		if (temp == null) {
			set(key, t);
		}
	}

	public void delete(String key) {
		try {
			memcachedClient.deleteWithNoReply(key);
		} catch (InterruptedException e) {
			logger.error("memcache delete操作异常",e);
		} catch (MemcachedException e) {
			logger.error("memcache delete操作异常",e);
		}
	}

	public void flushAll() {
		try {
			memcachedClient.flushAll();
		} catch (TimeoutException e) {
			logger.error("memcache flushAll操作异常",e);
		} catch (InterruptedException e) {
			logger.error("memcache flushAll操作异常",e);
		} catch (MemcachedException e) {
			logger.error("memcache flushAll操作异常",e);
		}
	}
}
