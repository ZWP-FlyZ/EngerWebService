package service.app;

import org.junit.Test;

import service.app.util.MD5Util;
import service.app.util.MyEncode;

public class EncodeTest {

	@Test
	public void test() {
		String s = "123456";
		System.err.println(MD5Util.string2MD5(s));
		System.err.println(MyEncode.encode(MD5Util.string2MD5(s)));
		System.err.println(MyEncode.encode(MD5Util.string2MD5(s)).length());
	}

}
