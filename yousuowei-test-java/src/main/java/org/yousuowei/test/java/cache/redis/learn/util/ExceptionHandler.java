package org.yousuowei.test.java.cache.redis.learn.util;

public class ExceptionHandler {

	public static void handleExceptions(Exception e) {
		handleExceptions(e, null);
	}

	public static void handleExceptions(Exception e, String message) {
		StringBuffer out = new StringBuffer();
		if (null != e) {
			out.append(e);
		}
		if (null != message) {
			out.append(":" + message);
		}
		System.out.println(out.toString());

	}
}
