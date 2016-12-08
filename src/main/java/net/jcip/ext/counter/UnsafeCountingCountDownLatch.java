package net.jcip.ext.counter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UnsafeCountingCountDownLatch {
	private static long counter = 0;

	private static final int THREAD_NUM = 20000;

	private static CountDownLatch latch = new CountDownLatch(THREAD_NUM);

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newCachedThreadPool();

		for (int i = 0; i < THREAD_NUM; i++) {
			executor.execute(new Runnable() {
				public void run() {
					counter++;
					latch.countDown();
				}
			});
		}

		executor.shutdown();
		latch.await();
		System.out.println("counter: " + counter);
	}

}
