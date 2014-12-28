package org.yousuowei.test.java.cache.redis.learn.dataType;


import org.yousuowei.test.java.cache.redis.learn.util.JedisHandler;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;

/**
 * 
 * @author jie
 * 
 */
public class JedisList {
	static JedisHandler jedisPool = new JedisHandler();
	static Jedis jedis;

	/**
	 * 操作链表
	 */
	public static void testList() {
		jedis = jedisPool.getJedis();
		String key = "myList";
		String value = "value";
		String newValue = "newValue";
		int index = 0;
		// LINSERT key BEFORE|AFTER pivot value在列表中的另一个元素之前或之后插入一个元素
		jedis.linsert(key, LIST_POSITION.AFTER, newValue, value);
		// LPUSH key value [value ...]从队到左边入队一个元素
		jedis.lpush(key, value);
		// LPUSHX key value当队列存在时，从队到左边入队一个元素
		jedis.lpushx(key, newValue);
		// LSET key index value设置队列里面一个元素的值
		jedis.lset(key, index, newValue);
		
		// LRANGE key start stop从列表中获取指定返回的元素
		jedis.lrange(key, 0, jedis.llen(key));
		// LINDEX key index获取一个元素，通过其索引列表
		jedis.lindex(value, index);
		// LLEN key获得队列(List)的长度
		jedis.llen(key);
		// LPOP key从队列的左边出队一个元素
		jedis.lpop(key);

		// LREM key count value从列表中删除元素
		jedis.lrem(key, index, newValue);
		// LTRIM key start stop修剪到指定范围内的清单
		jedis.ltrim(key, index, index);
	}

	public static void showList(String key) {
		jedis = jedisPool.getJedis();
		System.out.println("=======show list" + key + " start========");
		for (String demp : jedis.lrange(key, 0, -1)) {
			System.out.println(demp);
		}
		System.out.println("=======show hash" + key + " end========");
	}

	public static void addList(String key) {
		jedis = jedisPool.getJedis();
		// System.out.println(jedis.lpop(key));
		for (int i = 0; i < 5; i++) {
			jedis.lpush(key, "list_" + i);
		}
	}

	public static void main(String[] args) {
		String key = "myList";
		addList(key);
		showList(key);
	}

}
