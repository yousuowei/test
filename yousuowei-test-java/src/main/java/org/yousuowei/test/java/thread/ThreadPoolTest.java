package org.yousuowei.test.java.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;

import org.junit.Test;

/**
 * 
 * @ClassName: ThreadPoolTest
 * @Description: TODO
 * @author: jie
 * @date: 2014-4-24 下午4:44:25
 */
public class ThreadPoolTest {

	static ExecutorService executor = Executors.newSingleThreadExecutor();
	private int NUM_THREAD = 10;

	@Test
	public void runTest() {
		// 生成所有测试线程
		TestRunnable[] test = new TestRunnable[NUM_THREAD];
		long start = System.currentTimeMillis();
		for (int i = 0; i < test.length; i++) {
			test[i] = new Runner(i);
		}
		// 生成测试线程运行器
		MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(test);
		// 运行测试线程
		try {
			mttr.runTestRunnables();
		} catch (Throwable e) {
		}
		long used = System.currentTimeMillis() - start;
		System.out.printf("%s 调用花费 %s milli-seconds.\n", NUM_THREAD, used);
	}

	public static void main(String[] args) {
		int n = 0;
		try {
			for (; n < 10; n++) {
				executor.execute(new Runner(n));
			}
		} catch (Exception e) {
			System.out.println(e + "");
		}
	}

	private static class Runner extends TestRunnable {
		int i = 0;

		public Runner(int i) {
			this.i = i;
		}

		@Override
		public void runTest() throws Throwable {
			// try {
			System.out.println("ok:" + i);
			// Thread.sleep(3000);
			// } catch (InterruptedException e) {
			// System.out.println("ok" + e);
			// }
		}

	}

}
