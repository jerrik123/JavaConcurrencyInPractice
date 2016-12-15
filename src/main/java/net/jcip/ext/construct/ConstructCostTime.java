package net.jcip.ext.construct;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

public class ConstructCostTime {
	private String index;
	private String index2;
	private String index3;
	private String index4;
	private String index5;
	private String index6;

	private boolean flag;

	private int count;
	private int count1;
	private int count2;
	private int count3;
	private int count4;

	private double d1;
	private double d2;
	private double d3;
	private double d4;
	private double d5;
	private double d6;

	public ConstructCostTime() {
		for (int i = 0; i < 10; i++) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		}
	}

	public static void main(String[] args) {
		Stopwatch watch = Stopwatch.createStarted();
		new ConstructCostTime();
		watch.stop();
		System.out.println("cost time: " + watch.elapsed(TimeUnit.MILLISECONDS));
	}

}
