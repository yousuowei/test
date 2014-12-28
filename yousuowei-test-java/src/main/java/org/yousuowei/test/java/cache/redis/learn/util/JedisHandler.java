package org.yousuowei.test.java.cache.redis.learn.util;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

public class JedisHandler {
	private static ShardedJedisPool shardedJedisPool;
	private static List<JedisShardInfo> jdsInfoList = new ArrayList<JedisShardInfo>();
	JedisPoolConfig config = new JedisPoolConfig();// Jedis池配置
	private static JedisPool jedisPool;

	/**
	 * 初始化连接池
	 * 
	 * @return
	 */
	private void initShardedJedisPool() {
		initClientConf();
		setConfList();

		shardedJedisPool = new ShardedJedisPool(config, jdsInfoList,
				Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);
	}

	/**
	 * 初始化连接池
	 * 
	 * @return
	 */
	private void initJedisPool() {
		initClientConf();
		String ip = "192.168.51.158";
		int port = 6379;
		jedisPool = new JedisPool(config, ip, port);
	}

	/**
	 * 获取连接池
	 * 
	 * @return
	 */
	public ShardedJedisPool getShardedJedisPool() {
		if (null == shardedJedisPool) {
			initShardedJedisPool();
		}
		return shardedJedisPool;
	}

	public JedisPool getJedisPool() {
		if (null == jedisPool) {
			initJedisPool();
		}
		return jedisPool;
	}

	public void setConfList() {
		// JedisShardInfo info;
		// for (ServerConf conf : initServerConf()) {
		// info = new JedisShardInfo(conf.getIp(), conf.getPort());
		// jdsInfoList.add(info);
		// }
	}

	public void initClientConf() {
//		config.setMaxActive(500);// 最大活动的对象个数
		config.setMaxIdle(1000 * 60);// 对象最大空闲时间
//		config.setMaxWait(1000 * 10);// 获取对象时最大等待时间
		config.setTestOnBorrow(true);
	}

	public ShardedJedis getShardedJedis() {
		return getShardedJedisPool().getResource();
	}

	public Jedis getJedis() {
		return getJedisPool().getResource();
	}
}
