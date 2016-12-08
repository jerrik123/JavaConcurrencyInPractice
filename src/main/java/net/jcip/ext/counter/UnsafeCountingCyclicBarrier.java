package net.jcip.ext.counter;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class UnsafeCountingCyclicBarrier {
	private static long counter = 0;

	private static final int THREAD_NUM = 10000;

	// private static CountDownLatch latch = new CountDownLatch(THREAD_NUM);

	private static CyclicBarrier barrier = new CyclicBarrier(THREAD_NUM);

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newCachedThreadPool();

		for (int i = 0; i < THREAD_NUM; i++) {
			executor.execute(new Runnable() {
				public void run() {
					counter++;
					try {
						barrier.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						e.printStackTrace();
					}
				}
			});
		}

		executor.shutdown();
		
		//TimeUnit.SECONDS.sleep(4);
		System.out.println("counter: " + counter);
	}

}
