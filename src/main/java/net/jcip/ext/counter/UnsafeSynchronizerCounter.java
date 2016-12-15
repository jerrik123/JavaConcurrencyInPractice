package net.jcip.ext.counter;

/**
getNext()加了同步
cost time: 121016
counter: 1130052509

getNext()没有加同步
cost time: 5793
counter: 1497228448
 *
 */
public class UnsafeSynchronizerCounter {

	static int counter = 0;

	/**
	 * Synchronizer
	 * @return
	 */
	public  static int getNext() {
		counter++;
		return counter;
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < 100000; i++) {
						getNext();
					}
				}
			}).start();
		}
		long end = System.currentTimeMillis();
		System.out.println("cost time: " + (end - start));

		System.out.println("counter: " + counter);//5299
	}

}
