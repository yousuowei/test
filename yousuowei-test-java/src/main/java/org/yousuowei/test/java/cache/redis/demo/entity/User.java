package org.yousuowei.test.java.cache.redis.demo.entity;

import java.util.List;
import java.util.Set;

public class User {
	private long id;
	private String userName;
	private int age;
	private String password;

	private Set<String> followers;
	private List<String> comments;

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(long id, String userName, String password) {
		this.setId(id);
		this.setUserName(userName);
		this.setPassword(password);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
