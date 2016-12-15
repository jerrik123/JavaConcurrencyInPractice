package net.jcip.ext;

import java.util.concurrent.TimeUnit;

public class StackOverflowErrorTest {

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 8; i++) {
			final int index = i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					if (index == 3) {
						throw new StackOverflowError("error");
					}
					System.out.println(Thread.currentThread().getName());
				}
			}).start();
		}
		
		TimeUnit.SECONDS.sleep(3);
		
		System.out.println("竟然没报错...");
	}

}
