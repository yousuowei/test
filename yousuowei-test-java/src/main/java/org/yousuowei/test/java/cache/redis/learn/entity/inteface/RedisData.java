package org.yousuowei.test.java.cache.redis.learn.entity.inteface;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记entity是否进行缓存
 * 
 * @author jie
 * 
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE })
public @interface RedisData {
	public static final String DEFAULT = "default";

	public enum CacheType {
		STRING, HASH
	};

	CacheType cacheType() default CacheType.STRING;

	// 默认为ClassName.id（主键唯一值）
	String key() default DEFAULT;

}
