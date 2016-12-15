package net.jcip.ext.counter;

import java.util.concurrent.TimeUnit;

/**
 * volatile只能保证线程可见性,不能保证变量的原子性
 * 
 * 加锁 既能保证变量的原子性 又能保证可见性
 *
 */
public class UnsafeVolatileCounter {
	public static volatile long counter = 0;

	public static void getNext() {
		counter++;
	}

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 1000; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i < 1000; i++) {
						getNext();
					}
				}
			}).start();
		}

		TimeUnit.SECONDS.sleep(3);

		System.out.println(counter);
	}// output: 981652

}
