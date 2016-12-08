package net.jcip.ext.counter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
	private AtomicInteger atomicI = new AtomicInteger(0);
	private int i = 0;

	public static void main(String[] args) {
		final Counter cas = new Counter();
		List<Thread> ts = new ArrayList<Thread>(600);
		long start = System.currentTimeMillis();
		for (int j = 0; j < 100; j++) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < 10000; i++) {
						cas.count();
						cas.safeCount();
					}
				}
			});
			ts.add(t);
		}

		for (Thread t : ts) {
			t.start();
		}
		// 等待所有线程执行完成
		for (Thread t : ts) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("unsafe: " + cas.i);
		System.out.println("safe: " + cas.atomicI.get());
		System.out.println("Cost time: " + (System.currentTimeMillis() - start));
	}

	/**
	 * output: unsafe: 984522 
	 * safe: 1000000 
	 * Cost time: 233
	 */

	/**
	 * 使用CAS实现线程安全计数器
	 */
	private void safeCount() {
		for (;;) {
			int index = atomicI.get();
			boolean success = atomicI.compareAndSet(index, ++index);
			if (success) {
				break;
			}
		}
	}

	/**
	 * 非线程安全计数器
	 */
	private void count() {
		i++;
	}
}
