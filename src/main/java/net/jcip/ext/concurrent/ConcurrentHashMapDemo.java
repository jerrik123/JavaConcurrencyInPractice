package net.jcip.ext.concurrent;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapDemo {

	public static void main(String[] args) {
		ConcurrentHashMap<String, String> concurrentMap = new ConcurrentHashMap<String, String>();
		concurrentMap.put("hello", "world");
		System.out.println(concurrentMap);
	}

}
