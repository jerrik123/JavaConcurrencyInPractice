package net.jcip.ext.lock;

/**
 * 可重入锁
 * @author Jerrik
 *
 */
public class ReetrantLockTest {
	public static void main(String[] args) {
		Father father = new Son();
		father.show();
	}
}
