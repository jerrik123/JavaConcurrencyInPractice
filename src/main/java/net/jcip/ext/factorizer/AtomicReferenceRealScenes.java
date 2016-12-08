package net.jcip.ext.factorizer;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceRealScenes {

	public static void main(String[] args) {
		// useAtomicReference();

		notUseAtomicReference();
	}

	private static void useAtomicReference() {
		final AtomicReference<Integer> money = new AtomicReference<Integer>(19);
		for (int i = 0; i < 100; i++) {
			new Thread() {
				public void run() {
					while (true) {
						Integer m = money.get();
						if (m < 20) {
							if (money.compareAndSet(m, m + 20)) {
								System.out.println("余额小于20元，充值成功，余额:" + money.get() + "元");
								break;
							}
						} else {
							// System.out.println("余额大于20元，无需充值");
							break;
						}
					}
				}
			}.start();
		}
	}

	private static void notUseAtomicReference() {
		final Integer[] money = { 19 };
		for (int i = 0; i < 100000; i++) {
			new Thread() {
				public void run() {
					while (true) {
						if (money[0] < 20) {
							money[0] = money[0] + 20;
							System.out.println("余额小于20元，充值成功，余额:" + money[0] + "元");
							break;
						} else {
							// System.out.println("余额大于20元，无需充值");
							break;
						}
					}
				}
			}.start();
		}
	}

}
