package net.jcip.ext.executor;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CompletionExecutorTest {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newFixedThreadPool(20);
		final CompletionService<String> completionService = new ExecutorCompletionService<String>(executor);

		final Random random = new Random();

		Callable<String> callable = new Callable<String>() {
			@Override
			public String call() throws Exception {
				TimeUnit.MILLISECONDS.sleep(random.nextInt(3000));
				return UUID.randomUUID().toString();
			}
		};

		for (int i = 0; i < 10; i++) {
			Future<String> future = completionService.submit(callable);
		}

		
		TimeUnit.SECONDS.sleep(1);
		
		executor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					while(true){
						Future<String> future = completionService.take();
						String result = future.get(1, TimeUnit.SECONDS);
						System.out.println("result: " + result);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				} catch (TimeoutException e) {
					e.printStackTrace();
				}
			}
		});
		
		TimeUnit.SECONDS.sleep(3);
		executor.shutdown();

	}

}
