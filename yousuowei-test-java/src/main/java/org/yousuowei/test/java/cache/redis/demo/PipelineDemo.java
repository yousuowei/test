package org.yousuowei.test.java.cache.redis.demo;


import org.junit.Test;

import redis.clients.jedis.Pipeline;

/**
 * 通过pipeline方式将client端命令一起发出，redis
 * server会处理完多条命令后，将结果一起打包返回client,从而节省大量的网络延迟开销。
 * 
 * @author jie
 * 
 */
public class PipelineDemo extends BaseDemo {

	@Test
	public void testPipeline() {
		String key = "pipeline.set";
		Pipeline p = jedis.pipelined();
		p.sadd(key, "pipeline0");
		p.sadd(key, "pipeline1");
		p.srem(key, "pipeline0");
		p.sadd(key, "pipeline0");
		p.smembers(key);

		// 无返回值执行命令
		// p.sync();
		// 有返回值执行命令
		for (Object demp : p.syncAndReturnAll()) {
			System.out.println(demp);
		}
	}
}
