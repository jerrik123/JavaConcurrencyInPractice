package net.jcip.ext.lock;

import java.util.concurrent.TimeUnit;

public class LockDemo {
	static class LockA {

	}

	public static void main(String[] args) {
		final LockA lock = new LockA();
		final LockA lock1 = new LockA();
		Thread threadA = new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (lock1) {
					System.out.println("thread A");
					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		});

		Thread threadB = new Thread(new Runnable() {

			@Override
			public void run() {
				synchronized (lock) {
					System.out.println("thread B");
					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});

		threadA.start();
		threadB.start();
	}

}
