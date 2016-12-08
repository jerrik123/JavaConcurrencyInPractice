package net.jcip.ext.javap;

public class ConstDemo {

	public static void main(String[] args) {
		/*
		 * int i = -1; int i1 = -2; int i2 = 0; int i3 = 1; int i4 = 2;
		 * System.out.println(i); System.out.println(i1);
		 */

		/*
		 * String str1 = new String("测试"); String str2 = "测试";
		 * System.out.println(str1 == str2); System.out.println(str1.intern() ==
		 * str2);
		 */
		
		String str = new String("hello world");
		System.out.println(str == str.intern());
		/*String s0 = "kvill";
		String s1 = "kvill";*/
		//String s2 = "kv" + "ill";
		//System.out.println(s0 == s1);
		//System.out.println(s0 == s2);

		/*
		 * String str1 = new StringBuffer("测试").append("1").toString();
		 * System.out.println(str1.intern()==str1);//true
		 * 
		 * System.out.println("==============");
		 * 
		 * String str2 = new StringBuffer("测试").toString();
		 * System.out.println(str2.intern()==str1);//false
		 * 
		 * System.out.println("==============");
		 * 
		 * String str3 = "测试"; System.out.println(str3.intern()==str3);//true
		 */}

}
