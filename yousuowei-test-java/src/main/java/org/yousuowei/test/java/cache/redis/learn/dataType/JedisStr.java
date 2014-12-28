package org.yousuowei.test.java.cache.redis.learn.dataType;

import redis.clients.jedis.Jedis;

public class JedisStr {
	// 连接server
	private static final String SERVER_IP = "192.168.102.128";
	private static final int SERVER_PORT = 6379;
	private static Jedis jedis = new Jedis(SERVER_IP, SERVER_PORT);

	/**
	 * 操作String
	 */
	public static void testStr() {
		String key = "str";
		String value = "abc ccc";
		String newValue = "ok";
		// 存储一个key 对应的value字符串
		jedis.set(key, value);
		// 获取key对应值
		jedis.get(key);
		// SETRANGE key offset valueOverwrite :从指定位置开始替换成新值
		jedis.setrange(key, 4, newValue);
	}

}
