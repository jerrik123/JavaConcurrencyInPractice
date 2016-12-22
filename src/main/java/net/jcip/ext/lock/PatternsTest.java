package net.jcip.ext.lock;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternsTest {

	public static final Pattern PATTERN = Pattern.compile("<img\\s+(?:[^>]*)src\\s*=\\s*([^>]+)/>");

	public static void main(String[] args) {

		String str = "hello world <p><img src=\"http://tnz168-filestore-public.img-cn-shenzhen.aliyuncs.com/23e29b3b-1b3e-4f01-b227-c58a2131e3c0.jpg\" alt=\"\" />"
				+ "<img src=\"http://tnz168-filestore-public.img-cn-shenzhen.aliyuncs.com/ccc4ec05-09ea-425c-a779-e536984711b0.jpg\" alt=\"\" /></p><p>"
				+ "<br />" + "</p>";

		Matcher matcher = PATTERN.matcher(str);

		if (matcher.find()) {
			System.out.println(matcher.group(0));
		}

	}
}
