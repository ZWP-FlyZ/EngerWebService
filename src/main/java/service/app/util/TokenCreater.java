package service.app.util;

import java.util.Random;

import org.apache.tomcat.util.codec.binary.Base64;

public class TokenCreater {
	

	public static String getToken(String username){
		StringBuffer sb = new StringBuffer();
		sb.append(IDCreater.getUUID_RD());
		sb.append("_");
		sb.append(IDCreater.getUNIXTime());	
		for(char c:username.toCharArray()){
			sb.insert(random(0,sb.length()-1),c);
		}
		byte[] bs = Base64.encodeBase64(sb.toString().getBytes());
		return new String(bs).replace("=", "");
	}
	
    private static int random(int min, int max){
        Random r = new Random();
        int s = r.nextInt(max)%(max-min+1) + min;
        return s;
    }
	
}
