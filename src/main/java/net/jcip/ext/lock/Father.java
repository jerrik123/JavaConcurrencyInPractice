package net.jcip.ext.lock;

public class Father {

	public synchronized void show(){
		System.out.println("father show()");
	}
}
