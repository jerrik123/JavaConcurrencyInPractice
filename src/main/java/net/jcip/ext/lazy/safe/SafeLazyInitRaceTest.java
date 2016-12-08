package net.jcip.ext.lazy.safe;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SafeLazyInitRaceTest {
	private static final int THREAD_NUM = 100;

	public static void main(String[] args) throws IOException {
		final SafeLazyInitRace lazyInitRace = new SafeLazyInitRace();

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
