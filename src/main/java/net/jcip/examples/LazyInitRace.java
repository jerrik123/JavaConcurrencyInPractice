package net.jcip.examples;

import net.jcip.annotations.*;

/**
 * LazyInitRace
 *
 * Race condition in lazy initialization
 *
 * @author Brian Goetz and Tim Peierls
 */

@NotThreadSafe
public class LazyInitRace {
	private static ExpensiveObject instance = null;

	public ExpensiveObject getInstance() {
		if (instance == null)
			instance = new ExpensiveObject();
		return instance;
	}
}

class ExpensiveObject {
	public ExpensiveObject() {
		System.out.println("初始化()");
	}
}
