package net.jcip.ext.interrupt;

import java.util.concurrent.TimeUnit;

public class ThreadSleepInterrupt {

	public static void main(String[] args) throws InterruptedException {
		final Thread threadA = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("before call: " + Thread.currentThread().isInterrupted());
					TimeUnit.SECONDS.sleep(5);
					for(int i=0;i<10;i++){
						System.out.println(i);
					}
					// The <i>interrupted status</i> of the
				    // current thread is cleared when this exception is thrown.
				} catch (InterruptedException e) {
					System.out.println("sleep 收到中断请求() " + e.getMessage());
					/**
					 * 当抛出中断异常,当前线程的中断状态就已经被清除
					 */
					System.out.println("throw exception call: " + Thread.currentThread().isInterrupted());//false
					
					//响应中断的两种方式
					//1.传递异常
					//2.恢复中断状态
					Thread.currentThread().interrupt();//恢复中断状态
					System.out.println("after interrupt call: " + Thread.currentThread().isInterrupted());//true
				}
			}
		});
		
		threadA.start();
		System.out.println("interrupted1: " + threadA.interrupted());
		System.out.println("interrupted2: " + threadA.interrupted());
		TimeUnit.SECONDS.sleep(1);
		System.out.println("main线程发出中断指令");
		threadA.interrupt();
		
		TimeUnit.SECONDS.sleep(2);
		System.out.println("isInterrupted: " + threadA.isInterrupted());
	}

}
