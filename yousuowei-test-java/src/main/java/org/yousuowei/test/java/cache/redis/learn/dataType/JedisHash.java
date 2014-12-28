package org.yousuowei.test.java.cache.redis.learn.dataType;

import java.util.HashMap;
import java.util.Map;

import org.yousuowei.test.java.cache.redis.learn.util.JedisHandler;


import redis.clients.jedis.Jedis;

public class JedisHash {
	/**
	 * 操作hash
	 */
	public static void testHash() {
		jedis = jedisPool.getJedis();
		String key = "key";
		String field = "key";
		String value = "value";

		// HDEL key field [field ...]删除一个或多个哈希域
		jedis.hdel(key, field);
		// HEXISTS key field判断给定域是否存在于哈希集中
		jedis.hexists(key, field);
		// HGET key field读取哈希域的的值
		jedis.hget(key, field);
		// HGETALL key从哈希集中读取全部的域和值
		jedis.hgetAll(key);
		// HINCRBY key field increment将哈希集中指定域的值增加给定的数字
		jedis.hincrBy(key, field, 0);
		// HINCRBYFLOAT key field increment将哈希集中指定域的值增加给定的浮点数
		// HKEYS key获取hash的所有字段
		jedis.hkeys(key);
		// HLEN key获取hash里所有字段的数量
		jedis.hlen(key);
		// HMGET key field [field ...]获取hash里面指定字段的值
		jedis.hmget(key, field);
		// HMSET key field value [field value ...]设置hash字段值
		Map<String, String> map = new HashMap<String, String>();
		map.put(field, value);
		jedis.hmset(key, map);
		// HSET key field value设置hash里面一个字段的值
		jedis.hsetnx(key, field, value);
		// HSETNX key field value设置hash的一个字段，只有当这个字段不存在时有效
		jedis.hsetnx(key, field, value);
		// HVALS key获得hash的所有值
		jedis.hvals(key);
	}

	static JedisHandler jedisPool = new JedisHandler();
	static Jedis jedis;

	public static void showHash(String key) {
		jedis = jedisPool.getJedis();
		System.out.println("=======show hash" + key + " start========");
		for (String demp : jedis.hkeys(key)) {
			System.out.println(demp + ":" + jedis.hget(key, demp));
		}
		System.out.println("=======show hash" + key + " end========");
	}

	public static void addHash(String key) {
		jedis = jedisPool.getJedis();
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < 5; i++) {
			map.put("field_" + i, "value_" + i);
		}
		jedis.hmset(key, map);
	}

	public static void main(String[] args) {
		String key = "myHash";
		addHash(key);
		showHash(key);
	}

}
