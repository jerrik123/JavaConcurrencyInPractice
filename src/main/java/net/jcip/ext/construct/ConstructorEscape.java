package net.jcip.ext.construct;

/**
 * 构造函数溢出 在构造函数中定义一个线程,由于构造还没有完成,线程就能读到还未构造完成的类
 * 
 * @author Jerrik
 *
 */
public class ConstructorEscape {

	/**
	 * 针对32位jvm,有可能index没有初始化成功,Thread读到一个失效值
	 */
	public final double index;

	public final boolean flag;

	public ConstructorEscape(double index, boolean flag) {
		this.index = index;
		this.flag = flag;
		
		//在构造函数中发布一个内部类,内部类持有一个外部类的引用,导致外部类溢出
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("获取到的index: " + ConstructorEscape.this.index + " flag: "
						+ ConstructorEscape.this.flag);
			}
		}).start();
	}

	public static void main(String[] args) {
		new ConstructorEscape(3, true);
	}

}
