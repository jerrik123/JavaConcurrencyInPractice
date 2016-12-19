package net.jcip.ext.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureTest {

	public static void main(String[] args) throws InterruptedException {

		long start = System.currentTimeMillis();
		ExecutorService executor = Executors.newFixedThreadPool(2);

		Future<String> future = executor.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				TimeUnit.SECONDS.sleep(5);
				return "hello";
			}
		});

		executor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		TimeUnit.SECONDS.sleep(3);

		try {
			// String result = future.get();
			String result = future.get(2L, TimeUnit.SECONDS);
			System.out.println("result: " + result);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		} finally {
			executor.shutdown();
		}

		long end = System.currentTimeMillis();
		System.out.println("总共耗时: " + (end - start));
	}

}
