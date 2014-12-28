package org.yousuowei.test.java.cache.redis.learn.dataType;

import redis.clients.jedis.Jedis;

import org.yousuowei.test.java.cache.redis.learn.util.JedisHandler;

public class JedisSet {
	static JedisHandler jedisPool = new JedisHandler();
	static Jedis jedis;

	/**
	 * 操作链表
	 */
	public static void testSet() {
		jedis = jedisPool.getJedis();
		String key = "myList";
		String otherKey = "myList1";
		String dempKey = "dempList";
		String value = "value";
		String newValue = "newValue";
		int index = 0;
		// SADD key member [member ...]添加一个或者多个元素到集合(set)里
		jedis.sadd(key, value);
		// SCARD key 获取集合里面的元素数量
		jedis.scard(key);
		// SDIFF key [key ...]获得队列不存在的元素
		jedis.sdiff(key, otherKey);
		// SDIFFSTORE destination key [key ...] 获得队列不存在的元素，并存储在一个关键的结果集
		jedis.sdiffstore(dempKey, key, otherKey);
		// SINTER key [key ...] 获得两个集合的交集
		jedis.sinter(key, otherKey);
		// SINTERSTORE destination key [key ...] 获得两个集合的交集，并存储在一个关键的结果集
		jedis.sinterstore(dempKey, key, otherKey);
		// SISMEMBER key member 确定一个给定的值是一个集合的成员
		jedis.sismember(key, newValue);
		// SMEMBERS key 获取集合里面的所有key
		jedis.smembers(key);
		// SMOVE source destination member 移动集合里面的一个key到另一个集合
		jedis.smove(key, otherKey, value);
		// SPOP key 删除并获取一个集合里面的元素
		jedis.spop(key);
		// SRANDMEMBER key [count] 从集合里面随机获取一个key
		jedis.srandmember(key);
		// SREM key member [member ...] 从集合里删除一个或多个key
		jedis.srem(key, value, newValue);
		// SUNION key [key ...] 添加多个set元素
		jedis.sunion(key, otherKey);
		// SUNIONSTORE destination key [key ...] 合并set元素，并将结果存入新的set里面
		jedis.sunionstore(dempKey, key, otherKey);
	}
}
