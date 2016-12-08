package net.jcip.ext.lock.oschinablog;

import java.util.ArrayList;

public class ReentrancyTest {
	static public int i = 1;
	public int count;

	public ReentrancyTest() {
		super();
	}

	public static void main(String[] args) {
		int threadNum = 10; // 设置10个线程同时执行
		// 每个线程都关联同一个ReentrancyTest对象
		ReentrancyTest reentrancyTest = new ReentrancyTest();
		ArrayList<MyThread> threadList = new ArrayList<MyThread>();
		// 为10个线程赋值同一个ReentrancyTest对象的引用
		for (int i = 0; i < threadNum; i++) {
			MyThread myThread = new MyThread();
			myThread.reentrancyTest = reentrancyTest;
			threadList.add(myThread);
		}
		// 启动10个线程
		for (int i = 0; i < threadNum; i++) {
			new Thread((MyThread) threadList.get(i)).start();
		}
	}

	//如果不加synchronized 则是抢占式的
	public synchronized void doSomething() {
		// 随机产生一个睡眠时间
		int sleep = (int) (Math.random() * 500);
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		count = i++;
		System.out.println("第" + count + "个线程:" + Thread.currentThread().getName() + "进入到doSomething()执行代码--睡眠" + sleep
				+ "毫秒");
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + "线程执行doSomething()完毕,睡眠时间：" + sleep
				+ ",已进入doSomething执行代码的线程总数为:" + count);
	}
}

class MyThread extends Thread {
	public ReentrancyTest reentrancyTest;

	public MyThread() {
		super();
	}

	@Override
	public void run() {
		reentrancyTest.doSomething();
		super.run();
	}
}
