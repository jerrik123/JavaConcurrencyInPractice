package net.jcip.ext.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest2 {

	public static void main(String[] args) throws InterruptedException {
		final ReentrantLock lock = new ReentrantLock();

		final Condition condition1 = lock.newCondition();
		final Condition condition2 = lock.newCondition();

		new Thread(new Runnable() {
			@Override
			public void run() {
				lock.lock();
				System.out.println("另起一个线程: " + Thread.currentThread().getName());

				try {
					System.out.println("await condition1");
					condition1.await();
					System.out.println("===========");
					condition2.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("停止阻塞");
				lock.unlock();
			}
		}, "Another").start();

		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		lock.lock();
		System.out.println("condition1 signalAll()");
		condition1.signalAll();
		TimeUnit.SECONDS.sleep(2);
		System.out.println("condition2 signalAll()...");
		condition2.signalAll();
		lock.unlock();

	}

}
