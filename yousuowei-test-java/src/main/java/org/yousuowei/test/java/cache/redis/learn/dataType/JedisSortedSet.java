package org.yousuowei.test.java.cache.redis.learn.dataType;

import redis.clients.jedis.Jedis;

import org.yousuowei.test.java.cache.redis.learn.util.JedisHandler;

/**
 * Redis sorted set的使用场景与set类似，区别是set不是自动有序的，而sorted
 * set可以通过用户额外提供一个优先级(score)的参数来为成员排序
 * ，并且是插入有序的，即自动排序。当你需要一个有序的并且不重复的集合列表，那么可以选择sorted set数据结构，比如twitter 的public
 * timeline可以以发表时间作为score来存储，这样获取时就是自动按时间排好序的。
 * 
 * @author jie
 * 
 */
public class JedisSortedSet {

	static JedisHandler jedisPool = new JedisHandler();

	/**
	 * 操作hash
	 */
	public static void testHash() {
		Jedis jedis = jedisPool.getJedis();
		String key = "sortedSet";
		String otherKey = "sortedSet1";
		String dempKey = "dempSrotedSet";
		String value = "value";
		int core = 1;

		// ZADD key score member [score member
		// ...]添加到有序set的一个或多个成员，或更新的分数，如果它已经存在
		jedis.zadd(key, core, value);
		// ZINCRBY key increment member 增量的一名成员在排序设置的评分
		jedis.zincrby(key, 1, value);
		// ZREM key member [member ...] 从排序的集合中删除一个或多个成员
		// ZREMRANGEBYRANK key start stop 在排序设置的所有成员在给定的索引中删除
		// ZREMRANGEBYSCORE key min max 删除一个排序的设置在给定的分数所有成员

		// ZCARD key 获取一个排序的集合中的成员数量
		jedis.zcard(key);
		// ZCOUNT key min max 给定值范围内的成员数与分数排序
		jedis.zcount(key, core, core + 1);

		// ZINTERSTORE destination numkeys key [key ...] [WEIGHTS weight [weight
		// ...]] [AGGREGATE SUM|MIN|MAX] 相交多个排序集，导致排序的设置存储在一个新的关键
		jedis.zinterstore(dempKey, key, otherKey);

		// ZRANGE key start stop [WITHSCORES] 返回的成员在排序设置的范围，由指数
		jedis.zrange(key, 0, -1);
		// ZRANGEBYSCORE key min max [WITHSCORES] [LIMIT offset count]
		// 返回的成员在排序设置的范围，由得分
		jedis.zrangeByScore(key, 1, 4, 0, -1);
		// ZRANK key member 确定在排序集合成员的索引
		// ZSCORE key member 获取成员在排序设置相关的比分

		// ZREVRANGE key start stop [WITHSCORES] 在排序的设置返回的成员范围，通过索引，下令从分数高到低
		// ZREVRANGEBYSCORE key max min [WITHSCORES] [LIMIT offset count]
		// 返回的成员在排序设置的范围，由得分，下令从分数高到低
		// ZREVRANK key member 确定指数在排序集的成员，下令从分数高到低

		// ZUNIONSTORE destination numkeys key [key ...] [WEIGHTS weight [weight
		// ...]] [AGGREGATE SUM|MIN|MAX] 添加多个排序集和导致排序的设置存储在一个新的关键
	}

	/**
	 * 添加数据
	 */
}
