package org.yousuowei.test.java.cache.redis.learn.util;

import java.lang.annotation.Annotation;

import org.yousuowei.test.java.cache.redis.learn.entity.inteface.RedisData;


import redis.clients.jedis.Jedis;

/**
 * 
 * @author jie
 * 
 */
public class DataUtil {

	public int addObject(Jedis jedis, Object obj) {
		Class<? extends Object> objClass = obj.getClass();
		Annotation classAnnotation = objClass.getAnnotation(RedisData.class);
		if (null == classAnnotation) {
			return 0;
		}
		return 1;
	}

}
