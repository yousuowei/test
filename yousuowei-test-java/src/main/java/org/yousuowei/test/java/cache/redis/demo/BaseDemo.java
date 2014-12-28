package org.yousuowei.test.java.cache.redis.demo;

import redis.clients.jedis.Jedis;

public class BaseDemo {
	private final String SERVER_IP = "192.168.51.158";
	private final int SERVER_PORT = 6379;
	Jedis jedis;

	public BaseDemo() {
		jedis = new Jedis(SERVER_IP, SERVER_PORT);
	}

}
