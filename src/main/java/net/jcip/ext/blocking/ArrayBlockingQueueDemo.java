package net.jcip.ext.blocking;

import java.io.IOException;
import java.sql.Time;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ArrayBlockingQueueDemo {

	public static void main(String[] args) throws InterruptedException, IOException {
		final BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(5);
	
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					blockingQueue.put("hello");
					blockingQueue.put("world");
					blockingQueue.put("IBM");
					blockingQueue.put("OK");
					blockingQueue.put("SUN");
					blockingQueue.put("GOOGLE");
 				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		}).start();


		TimeUnit.SECONDS.sleep(1);
		Object[] arr = blockingQueue.toArray();
		System.out.println(Arrays.toString(arr));
		
		blockingQueue.take();
		
		TimeUnit.SECONDS.sleep(1);
		arr = blockingQueue.toArray();
		System.out.println(Arrays.toString(arr));
	}

}
