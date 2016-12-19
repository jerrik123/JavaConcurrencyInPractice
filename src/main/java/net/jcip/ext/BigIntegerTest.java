package net.jcip.ext;

import java.math.BigInteger;

public class BigIntegerTest {

	public static void main(String[] args) {
		BigInteger num1 = new BigInteger("1111111111111111111111111111111111111111111111111111111111111");
		BigInteger num2 = new BigInteger("11111111111111111111111111111111211111111111111111111111111111");
		System.out.println("num1加上num2的结果为：" + num1.add(num2));
		System.out.println("num1减去num2的结果为：" + num1.subtract(num2));
		System.out.println("num1乘以num2的结果为：" + num1.multiply(num2));
		System.out.println("num1除以num2的商为：" + num1.divide(num2));
		System.out.println("num1除以num2的余数为：" + num1.mod(num2));
		System.out.println("num1对2取余（也就是num1是否为双数）的结果是：" + num1.mod(new BigInteger("2")));
	}
}
