package org.yousuowei.test.java.cache.redis.demo;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

/**
 * jedis支持事务
 * 
 * @author jie
 * 
 */
public class TransactionDemo extends BaseDemo {

	@SuppressWarnings("unused")
	@Test
	public void testTransaction() {
		// 获取事务
		Transaction t = jedis.multi();
		t.set("user.name", "name");
		Response<String> result = t.get("user.name");
		t.zadd("transaction.Set", 1, "barowitch");
		t.zadd("transaction.Set", 0, "barinsky");
		t.zadd("transaction.Set", 0, "barikoviev");
		Response<Set<String>> set = t.zrange("foo", 0, -1);
		// // 事务执行
		// t.exec();
		List<Object> allResults = t.exec(); // 可以立刻获取全部结果
		for (Object demp : allResults) {
			System.out.println(demp);
		}

		// 必须在事务调用了exec()方法后才能获取结果，否则异常
		String userName = result.get();
		Set<String> setStr = set.get();
	}
}
