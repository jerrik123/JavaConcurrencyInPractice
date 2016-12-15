package net.jcip.ext.concurrent;

import java.util.HashMap;
import java.util.Map;

/**
 * 并发条件下 HashMap死循环
 * @author Jerrik
 *
 */
public class HashMapTest {

	public static void main(String[] args) {
		final Map<String,String> resultMap = new HashMap<String,String>();
		
		for(int i=0;i<10000;i++){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					for(int i=0;i<100000;i++){
						resultMap.put("key_" + i, "value_" + i);
					}
				}
			}).start();
		}
	}

}
