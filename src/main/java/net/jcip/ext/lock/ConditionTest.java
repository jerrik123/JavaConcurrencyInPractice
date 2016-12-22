package net.jcip.ext.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest {

	public static void main(String[] args) {
		final ReentrantLock lock = new ReentrantLock();

		final Condition condition = lock.newCondition();

		new Thread(new Runnable() {
			@Override
			public void run() {
				lock.lock();
				System.out.println("另起一个线程: " + Thread.currentThread().getName());

				try {
					condition.await();
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

		/*lock.lock();
		condition.signalAll();
		lock.unlock();*/

	}

}
