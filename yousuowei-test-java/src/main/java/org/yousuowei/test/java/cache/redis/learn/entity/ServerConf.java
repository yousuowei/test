package org.yousuowei.test.java.cache.redis.learn.entity;

public class ServerConf {
	private String ip;
	private int port;

	public ServerConf(String ip, int port) {
		setIp(ip);
		setPort(port);
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
