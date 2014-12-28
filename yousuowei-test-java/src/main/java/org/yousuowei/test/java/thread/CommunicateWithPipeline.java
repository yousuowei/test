package org.yousuowei.test.java.thread;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import org.yousuowei.test.java.common.StreamUtils;


/**
 * 管道通信: 1，建立输出管道，输入管道 2，线程管理输入输出管道
 * 原理：输入输出管道建立连接pos.connect(pis)，这样连接的输出管道输出时输入管道就会读取
 * 
 * @author jie
 * 
 */
public class CommunicateWithPipeline {
	public static void main(String[] args) {
		// 创建管道输出流
		PipedOutputStream pos = new PipedOutputStream();
		// 创建管道输入流
		PipedInputStream pis = new PipedInputStream();
		try {
			// 将管道输入流与输出流连接 此过程也可通过重载的构造函数来实现
			pos.connect(pis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 创建生产者线程
		Producer p = new Producer(pos);
		// 创建消费者线程
		Consumer c = new Consumer(pis);
		// 启动线程
		p.start();
		c.start();
	}

	/**
	 * 输出管道线程
	 * 
	 * @author jie
	 * 
	 */
	public static class Producer extends Thread {
		PipedOutputStream pos;

		public Producer(PipedOutputStream pos) {
			this.pos = pos;
		}

		@Override
		public void run() {
			try {
				String in = null;
				while (true) {
					in = StreamUtils.getSystemIn();
					if (null != in) {
						pos.write(in.getBytes());
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 输入管道线程
	 * 
	 * @author jie
	 * 
	 */
	public static class Consumer extends Thread {
		PipedInputStream pis;

		public Consumer(PipedInputStream pis) {
			this.pis = pis;
		}

		@Override
		public void run() {
			while (true) {
				try {
					byte[] out = new byte[256];
					pis.read(out);
					System.out.println(new String(out));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

}
