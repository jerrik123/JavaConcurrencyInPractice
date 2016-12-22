package net.jcip.ext.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockTest {
	final static ReentrantLock lock = new ReentrantLock();

	static long counter = 0;

	public static void main(String[] args) throws InterruptedException {

		ExecutorService executor = Executors.newCachedThreadPool();

		for (int i = 0; i < 1000; i++) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					if (lock.tryLock()) {
						try {
							counter++;
							System.out.println(lock.getHoldCount());
						} finally {
							lock.unlock();
						}
					} else {
						System.out.println("没有获得锁");
					}
				}
			});
		}

		TimeUnit.SECONDS.sleep(5);
		System.out.println(counter);

		executor.shutdown();
	}

}
