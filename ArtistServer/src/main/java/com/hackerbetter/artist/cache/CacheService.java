package com.hackerbetter.artist.cache;

public interface CacheService {

	public abstract <T> void set(String key, T t);

	public abstract <T> void set(String key, Integer exp, T t);

	public abstract <T> T get(String key);

	public abstract <T> void checkToSet(String key, T t);

	public abstract void delete(String key);

    public abstract long incr(String key, long delta);

    public abstract long decr(String key, long delta);

    public abstract void flushAll();

}