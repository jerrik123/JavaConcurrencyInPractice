package net.jcip.ext.counter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class SafeCounting {
	private static AtomicLong counter = new AtomicLong(0);

	private static final int THREAD_NUM = 8000;

	private static CountDownLatch latch = new CountDownLatch(THREAD_NUM);

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newCachedThreadPool();

		for (int i = 0; i < THREAD_NUM; i++) {
			executor.execute(new Runnable() {
				public void run() {
					counter.incrementAndGet();
					latch.countDown();
				}
			});
		}

		latch.await();
		System.out.println("counter: " + counter.get());

		executor.shutdown();
	}

}
