package net.jcip.ext.blocking;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 测试BlockingQueue的几个重要方法 add() :
 * 把anObject加到BlockingQueue里,即如果BlockingQueue可以容纳,则返回true,否则抛出异常
 * 
 * offer() :
 * 表示如果可能的话,将anObject加到BlockingQueue里,即如果BlockingQueue可以容纳,则返回true,否则返回false.
 * 
 * put() : 把anObject加到BlockingQueue里,如果BlockQueue没有空间,
 * 则调用此方法的线程被阻断直到BlockingQueue里面有空间再继续.
 * 
 * take() :
 * 取走BlockingQueue里排在首位的对象,若BlockingQueue为空,阻断进入等待状态直到Blocking有新的对象被加入为止
 * 
 * poll() : 取走BlockingQueue里排在首位的对象,若不能立即取出,则可以等time参数规定的时间,取不到时返回null 
 * 
 * peek() : 
 * 
 * @author Jerrik
 *
 */
public class BlockingQueueMethodTest {
	
	public static void main(String[] args) throws InterruptedException {
		// putAndTakeFromArrayBlockingQueue();
		// addFromArrayBlockingQueue();
		// pollFromArrayBlockingQueue();
		//peekFromArrayBlockingQueue();
	}

	private static void peekFromArrayBlockingQueue() throws InterruptedException {
		System.out.println("peekFromArrayBlockingQueue begin()");
		final BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(16);
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 1; i < 3; i++) {
					System.out.println("是否插入成功: " + blockingQueue.offer(i));// 如果插入成功,返回true.插入失败,返回false
				}
				System.out.println("入队完成...");
			}
		}).start();
		
		TimeUnit.SECONDS.sleep(1);
		
		System.out.println("result1: " + blockingQueue.peek());
		System.out.println("result2: " + blockingQueue.peek());
	}

	// 注意 BlockingQueue.size() 返回的是当前队列中实际存在的值个数,而不是数组长度
	private static void pollFromArrayBlockingQueue() throws InterruptedException {
		System.out.println("pollFromArrayBlockingQueue begin()");
		final BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(16);
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 1; i < 2; i++) {
					try {
						TimeUnit.SECONDS.sleep(2);
						blockingQueue.put(i);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("入队完成...");
			}
		}).start();

		// Integer result = blockingQueue.poll();//取出排在首位的元素,如果取不到则返回null
		Integer result = blockingQueue.poll(1, TimeUnit.SECONDS);// 等1秒,如果取不到数据则直接返回null
		System.out.println("result: " + result);
	}

	private static void offerFromArrayBlockingQueue() throws InterruptedException {
		System.out.println("offerFromArrayBlockingQueue begin()");
		final BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(16);
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < blockingQueue.size() + 1; i++) {
					System.out.println("是否插入成功: " + blockingQueue.offer(i));// 如果插入成功,返回true.插入失败,返回false
				}
				System.out.println("入队完成...");
			}
		}).start();
	}

	private static void addFromArrayBlockingQueue() throws InterruptedException {
		System.out.println("addFromArrayBlockingQueue begin()");
		final BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(16);
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < blockingQueue.size() + 1; i++) {
					blockingQueue.add(i);// 如果队列已满,add方法将抛出异常
				}
				System.out.println("入队完成...");
			}
		}).start();
	}

	private static void putAndTakeFromArrayBlockingQueue() throws InterruptedException {
		System.out.println("putAndTakeFromArrayBlockingQueue begin()");
		final BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(16);
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < blockingQueue.size() + 1; i++) {
					try {
						blockingQueue.put(i);// 如果队列已满,put方法就阻塞
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("入队完成...");
			}
		}).start();

		TimeUnit.SECONDS.sleep(2);
		Integer result = blockingQueue.take();// 如果队列已空,take方法将阻塞
		System.out.println("result: " + result);
	}
}
