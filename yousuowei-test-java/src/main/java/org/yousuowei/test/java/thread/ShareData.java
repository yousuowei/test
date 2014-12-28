package org.yousuowei.test.java.thread;

public class ShareData {
	private static Integer num = 0;

	public static Integer getNumSyncObj() {
		synchronized (num) {
			return num;
		}
	}

	public static synchronized Integer getNumSyncMethod() {
		return num;
	}

	public static Integer addNum() {
		return num++;
	}

	public static void main(String[] args) {
		// testSyncMethod();
		testSyncObj();
	}

	public static void testSyncMethod() {
		int i = 1;
		while (i < 3) {
			// test sync method
			TestSyncMethodThread tMethod = new TestSyncMethodThread();
			TestAddNumThread tAdd = new TestAddNumThread();
			tMethod.start();
			tAdd.start();
			i++;
		}
	}

	public static void testSyncObj() {
		int i = 1;
		while (i < 3) {
			// test sync obj
			TestSyncObjThread tObj = new TestSyncObjThread();
			TestAddNumThread tAdd = new TestAddNumThread();
			tObj.start();
			tAdd.start();
			i++;
		}
	}

	private static class TestSyncMethodThread extends Thread {
		@Override
		public void run() {
			try {
				sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("method sync:" + getNumSyncObj());
		}
	}

	private static class TestSyncObjThread extends Thread {
		@Override
		public void run() {
			try {
				sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("method obj:" + getNumSyncObj());
		}
	}

	private static class TestAddNumThread extends Thread {
		@Override
		public void run() {
			System.out.println("add num:" + getNumSyncObj());
			addNum();
		}
	}

}
