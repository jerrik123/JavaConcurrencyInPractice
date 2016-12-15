package net.jcip.ext;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ThreadDemo {
	static ThreadLocal<Integer> container = new ThreadLocal<Integer>() {
		protected Integer initialValue() {
			return 1;
		};
	};

	public static void main(String[] args) throws InterruptedException, IOException {
		System.out.println(Thread.currentThread());
		Thread threadA = new Thread(new Runnable() {
			@Override
			public void run() {
				container.set(3);
				System.out.println("threadA: " + container.get());
			}
		});

		threadA.start();

		TimeUnit.SECONDS.sleep(2);

		System.out.println("main: " + container.get());

		System.in.read();
	}

}
