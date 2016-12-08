package net.jcip.ext.lazy;

import net.jcip.annotations.*;

/**
 * LazyInitRace
 *
 * Race condition in lazy initialization
 *
 * @author Brian Goetz and Tim Peierls
 */

@NotThreadSafe
public class UnsafeLazyInitRace {
	private static ExpensiveObjects instance = null;

	public ExpensiveObjects getInstance() {
		if (instance == null)
			instance = new ExpensiveObjects();
		return instance;
	}
}
