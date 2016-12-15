package net.jcip.examples;

/**
 * NoVisibility
 * <p/>
 * Sharing variables without synchronization
 *
 * @author Brian Goetz and Tim Peierls
 */

public class NoVisibility {
	private static boolean ready = false;
	private static int number;

	private static class ReaderThread extends Thread {
		public void run() {
			System.out.println("run");
			while (!ready) {
				System.out.println("thread.yield()");
				Thread.yield();
			}
			System.out.println(number);
		}
	}

	public static void main(String[] args) {
		new ReaderThread().start();
		number = 42;
		ready = true;
	}
}
