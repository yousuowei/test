package org.yousuowei.test.java.thread;

/**
 * 共享变量通信
 * 
 * @author jie
 * 
 */
public class CommunicateWithShareData {

	static Integer num = 0;

	public static void main(String[] args) {
		int i = 0;
		while (i < 1000) {
			PlusThread.getThread().start();
			ReduceThread.getThread().start();
			i++;
		}
	}

	/**
	 * 加线程
	 * 
	 * @author jie
	 * 
	 */
	public static class PlusThread extends Thread {
		@Override
		public void run() {
			synchronized (num) {
				num++;
				System.out.println(num);
			}
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public static PlusThread getThread() {
			return new PlusThread();
		}

	}

	/**
	 * 减线程
	 * 
	 * @author jie
	 * 
	 */
	public static class ReduceThread extends Thread {
		@Override
		public void run() {
			synchronized (num) {
				num--;
				System.out.println(num);
			}
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public static ReduceThread getThread() {
			return new ReduceThread();
		}
	}

}
