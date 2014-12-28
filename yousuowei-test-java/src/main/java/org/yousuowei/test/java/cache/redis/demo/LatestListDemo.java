package org.yousuowei.test.java.cache.redis.demo;

import java.util.List;

/**
 * 1．在主页中显示最新的项目列表。
 * 
 * Redis使用的是常驻内存的缓存，速度非常快。LPUSH用来插入一个内容ID，作为关键字存储在列表头部。
 * LTRIM用来限制列表中的项目数最多为5000。如果用户需要的检索的数据量超越这个缓存容量，这时才需要把请求发送到数据库。
 */
public class LatestListDemo extends BaseDemo {

	String key = "latest.news";

	/**
	 * 添加最新评论
	 * 
	 * @param news
	 * @return
	 */
	public long addLatestNews(String news) {
		return jedis.lpush(key, news);
	}

	/**
	 * 获取指定条数最新评论
	 * 
	 * @param num
	 * @return
	 */
	public List<String> getLatestNews(int num) {
		return jedis.lrange(key, 0, num);
	}
}
