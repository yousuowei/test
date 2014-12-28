package org.yousuowei.test.java.ip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IpTest {

	public static void main(String args[]) {
		test();
		long time = System.currentTimeMillis();
//		 test1();
		long time1 = System.currentTimeMillis();
		System.out.println("**************");
		System.out.println(time1 - time);
	}

	static IPSeeker ipServer = IPSeeker.getInstance();

	public static void test1() {
		int a, b, c, d;
		int i = 0;
		StringBuilder ip = new StringBuilder();
		String tag = ".";
		while (i < 1000) {
			a = (int) (Math.random() * 255);
			b = (int) (Math.random() * 255);
			c = (int) (Math.random() * 255);
			d = (int) (Math.random() * 255);
			ip.append(a);
			ip.append(tag);
			ip.append(b);
			ip.append(tag);
			ip.append(c);
			ip.append(tag);
			ip.append(d);
			System.out.println("====================================");
			System.out.println("ip:" + ip);
			System.out.println(ipServer.getIPInfo(ip.toString()).toString());
			ip.delete(0, ip.length());
			i++;
		}

	}

	public static void test() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = "1.1.0.255";
		try {
			while (null != (str = br.readLine())) {
				String address;
				String country;
				String area;
				System.out.println("readString 方法的输入：" + str);
				address = ipServer.getAddress(str);
				country = ipServer.getCountry(str);
				area = ipServer.getArea(str);
				System.out.println("======================================");
				System.out.println("地址：" + address);
				System.out.println("国家：" + country + " 地区：" + area);
				System.out.println("国家：" + address + " 省份：" + country + " 城市："
						+ area);
				System.out.println("--------------------------------------");
				System.out.println(ipServer.getIPInfo(str).toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
