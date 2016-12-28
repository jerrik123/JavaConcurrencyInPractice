package net.jcip.ext.lock;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockDemo {

	final static ReadWriteLock lock = new ReentrantReadWriteLock();

	final static Lock readLock = lock.readLock();
	final static Lock writeLock = lock.writeLock();
	final static Random random = new Random();

	public static void main(String[] args) {
		for (int i = 0; i < 200; i++) {
			if (i % 2 == 0) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							readLock.lock();
							System.out.println("读线程()..." + Thread.currentThread().getId());
							TimeUnit.SECONDS.sleep(random.nextInt(10));
						} catch (Exception e) {
							Thread.currentThread().interrupt();
						} finally {
							readLock.unlock();
						}
					}
				}).start();
			} else {
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							writeLock.lock();
							System.out.println("写线程()..." + Thread.currentThread().getId());
							TimeUnit.SECONDS.sleep(random.nextInt(5));
						} catch (Exception e) {
							Thread.currentThread().interrupt();
						} finally {
							writeLock.unlock();
						}
					}
				}).start();
			}
		}
	}

}
