package net.jcip.ext.lock.oschinablog;

public class Child extends Father {
	public static void main(String[] args) {
		Child child = new Child();
		child.doSomething();
	}

	public synchronized void doSomething() {
		System.out.println("child.doSomething()");
		doAnotherThing(); // 调用自己类中其他的synchronized方法

	}

	private synchronized void doAnotherThing() {
		super.doSomething(); // 调用父类的synchronized方法
		System.out.println("child.doAnotherThing()");
	}
}

class Father {
	public synchronized void doSomething() {
		System.out.println("father.doSomething()");
	}
}