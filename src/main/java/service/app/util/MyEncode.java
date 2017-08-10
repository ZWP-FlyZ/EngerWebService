package service.app.util;

import org.apache.tomcat.util.codec.binary.Base64;

public class MyEncode {
	public static String encode(String s){
		if(s==null) return null;
		String tmp = MD5Util.string2MD5(s);
		byte[] bs = Base64.encodeBase64(tmp.getBytes());
		return new String(bs).replace("=", "");
	}
}
