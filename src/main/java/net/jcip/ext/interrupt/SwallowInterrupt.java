package net.jcip.ext.interrupt;

import java.util.concurrent.TimeUnit;

public class SwallowInterrupt {
	public static void main(String[] args) throws InterruptedException {
		Thread threadA = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e) {
					System.out.println("throw exception: " + Thread.currentThread().isInterrupted());//false
					Thread.currentThread().interrupt();
					System.out.println(Thread.currentThread().isInterrupted());//true
				}
				//如果加上后面这段空转代码,线程还是处于RUNNABLE状态,main线程中isInterrupted()为true
				/*long start = System.currentTimeMillis();
				while(System.currentTimeMillis()-start<5000){
					
				}*/
			}
		});
		threadA.start();
		threadA.interrupt();

		TimeUnit.SECONDS.sleep(2);
		System.out.println("state: " + threadA.getState() + " isInterrupted: " + threadA.isInterrupted());
	}
	/**
throw exception: false
true
state: RUNNABLE isInterrupted: true
	 */

}
