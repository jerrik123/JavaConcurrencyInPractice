package net.jcip.ext.lock;

public class Father {
	
	public volatile boolean flag = false;

	public synchronized void show(){
		System.out.println("father show()");
	}
	
}
