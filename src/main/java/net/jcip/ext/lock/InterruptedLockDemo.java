package net.jcip.ext.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 模拟被中断的锁实现
 * 
 * @author Jerrik
 */
public class InterruptedLockDemo {

	public static void main(String[] args) throws InterruptedException {

		final ReentrantLock lock = new ReentrantLock();

		Thread waitThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					lock.lockInterruptibly();
					System.out.println("获取锁()...");

					TimeUnit.SECONDS.sleep(5);

					System.out.println("休眠后 继续执行 ");
				} catch (InterruptedException e) {
					System.out.println("waitThread响应中断()");
				} finally {
					lock.unlock();
				}
			}
		});

		waitThread.start();

		TimeUnit.SECONDS.sleep(1);
		waitThread.interrupt();

	}

}
