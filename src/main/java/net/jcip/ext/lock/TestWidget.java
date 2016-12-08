package net.jcip.ext.lock;

/**
 * 证明子类调用复写了父类的方法时， 除了获得自身类锁的同时还会获得父类的锁
 * 
 * @author Jerrik
 */
public class TestWidget {

	public static void main(String[] args) throws InterruptedException {
		final LoggingWidget loggingWidget = new LoggingWidget();
		Thread th1 = new Thread("thread-1") {
			@Override
			public void run() {
				System.out.println(super.getName() + ":start\r\n");
				loggingWidget.doSometing();// 休眠5秒 然后再调用父类的方法
			}
		};
		Thread th2 = new Thread("thread-2") {
			@Override
			public void run() {
				System.out.println(super.getName() + ":start\r\n");
				/** 为了说明子类复写父类方法后，调用时也持有父类锁 */
				//线程1先执行,先持有锁。线程2必须得等线程1执行完才能执行doAnother
				loggingWidget.doAnother();
				
				/** 证明了内置锁对那些没有加synchronized修饰符的方法是不起作用的 */
				// widget.doNother();
				
				/** 为了说明子类复写父类方法后，调用时也持有父类锁，也持有自己本类的锁 */
				// widget.doMyLike();
				
				/** 这是两个线程，这是需要等待的，并不是继承的关系，不是重入，重入是发生在一个线程中的 */
				// widget.doSometing();
			}
		};
		th1.start();
		Thread.sleep(1000);
		th2.start();
	}
}

/**
thread-1:start

loggingwidget doSometing begin...休眠5秒()
thread-2:start

loggingwidget doSometing end...
widget ... doSometing...
widget... doAnother thing...
loggingwidget super.doSometing last
widget... doAnother thing...
 */
class LoggingWidget extends Widget {
	@Override
	public synchronized void doSometing() {
		try {
			System.out.println("loggingwidget doSometing begin...休眠5秒()");
			Thread.sleep(5000);
			System.out.println("loggingwidget doSometing end...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//可重入
		super.doSometing();
		doAnother();
		System.out.println("loggingwidget super.doSometing last");
	}

	public synchronized void doMyLike() {
		System.out.println("loggingwidget do my like...");
	}
}

class Widget {
	public synchronized void doSometing() {
		System.out.println("widget ... doSometing...");
	}

	public synchronized void doAnother() {
		System.out.println("widget... doAnother thing...");
	}

	public void doNothing() {
		System.out.println("widget... doNothing...");
	}
}
