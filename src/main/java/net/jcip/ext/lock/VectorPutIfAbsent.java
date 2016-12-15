package net.jcip.ext.lock;

import java.util.Arrays;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

/**
 * Vector虽然是线程安全的, 但是这种先检查后执行的操作 存在竞态条件
 * 
 * @author Jerrik
 */
public class VectorPutIfAbsent {
	static String[] str = new String[] { "SUN", "GOOGLE", "Oracle", "IBM", "ALI", "TENCENT", "BAIDU", "SUN1",
			"GOOGLE1", "Oracle1", "IBM1", "ALI1", "TENCENT1", "BAIDU1", "SUN2", "GOOGLE2", "Oracle2", "IBM2", "ALI2",
			"TENCENT2", "BAIDU2", "SUN3", "GOOGLE3", "Oracle3", "IBM3", "ALI3", "TENCENT3", "BAIDU3", "SUN4",
			"GOOGLE4", "Oracle4", "IBM4", "ALI4", "TENCENT4", "BAIDU4" };

	static final int THREAD_NUM = 20000;

	public static void main(String[] args) throws InterruptedException {
		final Vector<String> container = new Vector<String>();

		final CountDownLatch latch = new CountDownLatch(THREAD_NUM);

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					latch.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for (int i = 0; i < str.length; i++) {
					//putIfAbsent 存在竞态条件   如果A线程判断字符串a不存在容器中,准备调用add进行添加的时候,
					//还没add完成,b线程刚好运行到字符串a,由于之前a线程还没有添加完成,所以b线程会添加第二次
					// synchronized (this) {
					if (!container.contains(str[i])) {
						container.add(str[i]);
					}
					// }

				}
			}
		};

		for (int i = 0; i < THREAD_NUM; i++) {
			new Thread(runnable).start();
			latch.countDown();
		}

		System.out.println("等待所有线程准备好()");

		System.out.println("size: " + container.size() + " = " + Arrays.toString(container.toArray()));
	}

}
