package net.jcip.ext.factorizer;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceTest2 {

	public static void main(String[] args) {
		String str1 = new String("first value referenced");
		String str2 = new String("first value referenced");

		AtomicReference<String> atomicReference = new AtomicReference<String>(str1);
		boolean flag = atomicReference.compareAndSet(str2, "helloworld");
		System.out.println("flag: " + flag + " ,atomic: " + atomicReference.get());
	}

}
