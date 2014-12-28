package org.yousuowei.test.java.cache.redis.demo;

import java.util.Map;

import org.yousuowei.test.java.cache.redis.demo.entity.User;
import org.yousuowei.test.java.cache.redis.learn.util.DataConvert;


/**
 * 存储对象类型数据 ：存储用户信息
 * 
 * @author jie
 * 
 */
public class ObjectDemo extends BaseDemo {

	/**
	 * 添加用户： 俩种方式存储 1，user_name_id：nameValue,user_age_id:ageValue
	 * 2,user<user_name_id:nameValue,user_age_id:ageValue>
	 */
	public void addUser() {
		User user = new User();
		user.setId(0);
		user.setUserName("som");
		user.setAge(22);
		Map<String, String> userMap = DataConvert.convertObject(user, "id");
		jedis.hmset("user", userMap);

		for (String key : userMap.keySet()) {
			jedis.set(key, userMap.get(key));
		}

	}
}
