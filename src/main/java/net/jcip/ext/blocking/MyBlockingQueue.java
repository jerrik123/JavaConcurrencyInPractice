package net.jcip.ext.blocking;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyBlockingQueue<E> {

	private final E[] items;

	private ReentrantLock lock;

	/**
	 * 如果队列满,则full.await()
	 */
	private Condition full;

	/**
	 * 如果队列空,则empty.await()
	 */
	private Condition empty;

	/**
	 * 数组实际元素个数
	 */
	private int count;

	/**
	 * 取数脚标
	 */
	private int takeIndex;

	/**
	 * 存数脚标
	 */
	private int putIndex;

	public MyBlockingQueue() {
		this(10, false);
	}

	public MyBlockingQueue(int capacity, boolean fair) {
		items = (E[]) new Object[capacity];
		lock = new ReentrantLock(fair);
		full = lock.newCondition();
		empty = lock.newCondition();
	}

	public final void put(E e) throws InterruptedException {
		final ReentrantLock lock = this.lock;
		try {
			lock.lockInterruptibly();
			if (count == items.length) {
				full.await();
			}
			insert(e);
		} catch (InterruptedException ie) {
			throw ie;
		} finally {
			lock.unlock();
		}
	}

	

	public final E take() throws InterruptedException {
		final ReentrantLock lock = this.lock;
		try {
			lock.lockInterruptibly();
			if (count == 0) {
				empty.await();
			}
			E r  = extract();
			return r;
		} catch (InterruptedException ie) {
			throw ie;
		} finally {
			lock.unlock();
		}
	}
	
	private void insert(E e) {
		items[putIndex++] = e;
		count++;
	}

	private E extract() {
		E e = items[takeIndex++] ;
		return e;
	}

}
