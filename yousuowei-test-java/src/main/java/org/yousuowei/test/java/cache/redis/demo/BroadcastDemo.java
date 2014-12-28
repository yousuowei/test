package org.yousuowei.test.java.cache.redis.demo;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * redis 广播 ：对slave 的子节点有效 subscribe:new.book 直接匹配, psubscribe:new.* 可使用正则匹配
 * 
 * @author jie
 * 
 */
public class BroadcastDemo {

	Jedis server;
	Jedis clientA;
	Jedis clientB;

	private final String SERVER_IP = "192.168.51.158";
	private final int SERVER_PORT = 6379;
	private final String CHANNEL_P = "channel.*";
	private final String CHANNEL_BOOK = "channel.book";

	public void init() {
		server = new Jedis(SERVER_IP, SERVER_PORT);
		clientA = new Jedis(SERVER_IP, SERVER_PORT);
		clientB = new Jedis(SERVER_IP, SERVER_PORT);
	}

	/**
	 * 订阅信息
	 */
	public void subscribe() {
		Thread pA = new Thread(new Runnable() {
			public void run() {
				clientA.psubscribe(new PubSubClient(), CHANNEL_P);
			}
		});
		Thread pB = new Thread(new Runnable() {
			public void run() {
				clientB.subscribe(new PubSubClient(), CHANNEL_BOOK);
			}
		});
		pA.start();
		pB.start();
	}

	/**
	 * 发布信息
	 */
	public void publish() {
		String channel = "channel.book";
		String content = "show book";
		server.publish(channel, content);
		System.out.println("publish===channel:" + channel + "_content:"
				+ content);
		content = "show news";
		channel = "channel.news";
		server.publish(channel, content);
		System.out.println("publish===channel:" + channel + "_content:"
				+ content);
		server.disconnect();
	}

	/**
	 * 测试订阅和发布信息
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testPubSub() throws InterruptedException {
		init();
		subscribe();
		Thread.sleep(1000);
		publish();
	}

	private class PubSubClient extends JedisPubSub {

		@Override
		public void onMessage(String channel, String message) {
			System.out.println(this.toString() + "channel:" + channel
					+ "_message:" + message);
		}

		@Override
		public void onPMessage(String pattern, String channel, String message) {
			System.out.println(this.toString() + "channel:" + channel
					+ "_message:" + message);

		}

		@Override
		public void onSubscribe(String channel, int subscribedChannels) {
			System.out.println(this.toString() + "channel:" + channel
					+ "_subscribedChannels:" + subscribedChannels);

		}

		@Override
		public void onUnsubscribe(String channel, int subscribedChannels) {
			System.out.println(this.toString() + "channel:" + channel
					+ "_subscribedChannels:" + subscribedChannels);

		}

		@Override
		public void onPUnsubscribe(String pattern, int subscribedChannels) {
			System.out.println(this.toString() + "pattern:" + pattern
					+ "_subscribedChannels:" + subscribedChannels);

		}

		@Override
		public void onPSubscribe(String pattern, int subscribedChannels) {
			System.out.println(this.toString() + "pattern:" + pattern
					+ "_subscribedChannels:" + subscribedChannels);
		}
	}

}
