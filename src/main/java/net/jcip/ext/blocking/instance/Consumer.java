package net.jcip.ext.blocking.instance;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 消费者
 */
public class Consumer implements Runnable {

	public Consumer(BlockingQueue<String> queue) {
		this.queue = queue;
	}

	public void run() {
		System.out.println("启动消费者线程-" + Thread.currentThread().getName());
		Random r = new Random();
		boolean isRunning = true;
		try {
			while (isRunning) {
				String data = queue.poll(2, TimeUnit.SECONDS);
				if (null != data) {
					System.out.println("【消费者线程正在消费数据】：" + data);
					Thread.sleep(r.nextInt(DEFAULT_RANGE_FOR_SLEEP));
				} else {
					// 超过2s还没数据，认为所有生产线程都已经退出，自动退出消费线程。
					isRunning = false;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		} finally {
			System.out.println("退出消费者线程！");
		}
	}

	private BlockingQueue<String> queue;
	private static final int DEFAULT_RANGE_FOR_SLEEP = 1000;
}