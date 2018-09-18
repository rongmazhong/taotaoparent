package com.taotao.order.jedis;

public interface JedisClient {

	/**
	 * 加入redis
	 * @param key key
	 * @param value value
	 * @return
	 */
	String set(String key, String value);

	String get(String key);
	Boolean exists(String key);
	Long expire(String key, int seconds);
	Long ttl(String key);
	Long incr(String key);
	Long hset(String key, String field, String value);
	String hget(String key, String field);
	Long hdel(String key, String... field);

    /**
     * 删除键
	 * @param key 键
	 * @return long
	 */
	Long del(String key);

}
