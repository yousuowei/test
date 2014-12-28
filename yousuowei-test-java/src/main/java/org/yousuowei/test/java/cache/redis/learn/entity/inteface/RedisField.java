package org.yousuowei.test.java.cache.redis.learn.entity.inteface;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD })
public @interface RedisField {
	public static final String DEFAULT = "default";

	public enum CacheType {
		LIST, SET, SORTSET
	};

	CacheType cacheType() default CacheType.LIST;

	// 默认为当前标记属性值
	String score() default DEFAULT;

	// 默认为当前主键值
	String value() default DEFAULT;

}
