package net.jcip.ext.lazy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UnsafeLazyInitRaceTest {
	private static final int THREAD_NUM = 5000;

	public static void main(String[] args) {
		final UnsafeLazyInitRace lazyInitRace = new UnsafeLazyInitRace();

		ExecutorService executor = Executors.newCachedThreadPool();

		for (int i = 0; i < THREAD_NUM; i++) {
			executor.execute(new Runnable() {
				public void run() {
					lazyInitRace.getInstance();
				}
			});
		}

		executor.shutdown();
	}
}
