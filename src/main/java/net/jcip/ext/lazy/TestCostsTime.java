package net.jcip.ext.lazy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.jcip.ext.lazy.safe.SafeLazyInitRace;

public class TestCostsTime {
	public static void main(String[] args) {
		/*long start = System.currentTimeMillis();
		getSafeObject();
		long end = System.currentTimeMillis();
		System.out.println("safe costs time: " + (end - start));*/
		
		
		long start02 = System.currentTimeMillis();
		getUnSafeObject();
		long end02 = System.currentTimeMillis();
		System.out.println("unsafe costs time: " + (end02 - start02));
	}

	//102 ms
	private static void getSafeObject() {
		final SafeLazyInitRace lazyInitRace = new SafeLazyInitRace();
		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i = 0; i < 100000; i++) {
			executor.execute(new Runnable() {
				public void run() {
					lazyInitRace.getInstance();
				}
			});
		}
		executor.shutdown();
	}

	//97 ms
	private static void getUnSafeObject() {
		final UnsafeLazyInitRace lazyInitRace = new UnsafeLazyInitRace();
		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i = 0; i < 100000; i++) {
			executor.execute(new Runnable() {
				public void run() {
					lazyInitRace.getInstance();
				}
			});
		}
		executor.shutdown();
	}

}
