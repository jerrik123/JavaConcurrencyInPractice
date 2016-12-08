package net.jcip.ext.longdouble;

/**
 * 模拟 long和double的非原子操作
 * 针对32位jvm long double是非原子操作
 * @author Jerrik
 *
 */
public class LongDoubleTest implements Runnable {
	private static long test = 0;

	private final long val;

	public LongDoubleTest(long val) {
		this.val = val;
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			test = val; // 两个线程都试图将自己的私有变量val赋值给类私有静态变量test
		}
	}

	public static void main(String[] args) {
		Thread t1 = new Thread(new LongDoubleTest(-1));
		Thread t2 = new Thread(new LongDoubleTest(0));

		System.out.println(Long.toBinaryString(-1));
		System.out.println(pad(Long.toBinaryString(0), 64));

		t1.start();
		t2.start();

		long val;
		while ((val = test) == -1 || val == 0) {
			// 如果静态成员test的值是-1或0，说明两个线程操作没有交叉
		}

		System.out.println(pad(Long.toBinaryString(val), 64));
		System.out.println(val);

		t1.interrupt();
		t2.interrupt();
	}

	// prepend 0s to the string to make it the target length
	private static String pad(String s, int targetLength) {
		int n = targetLength - s.length();
		for (int x = 0; x < n; x++) {
			s = "0" + s;
		}
		return s;
	}
}