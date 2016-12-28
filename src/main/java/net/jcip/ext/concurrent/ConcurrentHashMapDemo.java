package net.jcip.ext.concurrent;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapDemo {

	public static void main(String[] args) {
		ConcurrentHashMap<String, String> concurrentMap = new ConcurrentHashMap<String, String>();
		for(int i=0;i<100;i++){
			concurrentMap.put("hello_" + i, "world_" + i);
		}
		
		concurrentMap.put("hello_1", "another");
		
		System.out.println(concurrentMap);
	}

}
