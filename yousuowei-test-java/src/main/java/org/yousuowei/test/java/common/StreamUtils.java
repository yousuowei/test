package org.yousuowei.test.java.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StreamUtils {

	public static String getSystemIn() {
		String in = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		try {
			in = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return in;
	}

}
