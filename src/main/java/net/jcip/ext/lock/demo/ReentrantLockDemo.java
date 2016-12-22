package net.jcip.ext.lock.demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {

	public static void main(String[] args) {
		final ReentrantLock lock = new ReentrantLock();

		final Condition condition = lock.newCondition();

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					lock.lock();

					System.out.println("等待一个信号: " + this);

					condition.await();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}

				System.out.println("拿到一个信号: " + this);

				lock.unlock();
			}
		}, "waitThread");

		thread.start();

		Thread thread2 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					lock.lock();

					System.out.println("我拿到锁了");

					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}

				condition.signalAll();

				System.out.println("我发了一个信号");

				lock.unlock();
			}
		}, "signalThread");

		thread2.start();
	}
	/**
		等待一个信号: net.jcip.ext.lock.demo.ReentrantLockDemo$1@24c21495
		我拿到锁了
		我发了一个信号
		拿到一个信号: net.jcip.ext.lock.demo.ReentrantLockDemo$1@24c21495
	 */

}
