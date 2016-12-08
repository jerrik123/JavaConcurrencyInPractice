package net.jcip.ext.lock;

public class Son extends Father {
	public synchronized void show(){
		System.out.println("Son show begin()");
		super.show();
		System.out.println("son show end()");
	}
}
