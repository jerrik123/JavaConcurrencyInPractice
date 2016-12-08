package net.jcip.ext.lazy.safe;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import net.jcip.annotations.NotThreadSafe;
import net.jcip.ext.lazy.ExpensiveObjects;

/**
 * LazyInitRace
 *
 * Race condition in lazy initialization
 *
 * @author Brian Goetz and Tim Peierls
 */

@NotThreadSafe
public class SafeLazyInitRace {
	private static  ExpensiveObjects instance = null;

	public ExpensiveObjects getInstance(){
		if (instance == null) {
			synchronized (this) {
				if (instance == null) {
					instance = new ExpensiveObjects();
				}
			}
		}
		return instance;
	}
}

