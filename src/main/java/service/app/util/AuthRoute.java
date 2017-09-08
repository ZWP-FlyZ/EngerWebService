package service.app.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.InitializingBean;


public class AuthRoute implements InitializingBean{

	private String adminRoute;
	private String userTrafficRoute;
	private String userLandRoute;
	private String userWaterRoute;
	private String userEnterpriceRoute;
	
	
	
	public String getAdminRoute() {
		return adminRoute;
	}

	public String getUserTrafficRoute() {
		return userTrafficRoute;
	}


	public String getUserLandRoute() {
		return userLandRoute;
	}


	public String getUserWaterRoute() {
		return userWaterRoute;
	}
	

	public String getUserEnterpriceRoute() {
		return userEnterpriceRoute;
	}

	private String getJsonFromFile(String fileName){
        String encoding = "ISO-8859-1";  
        File file = new File(fileName);  
        Long filelength = file.length();  
        byte[] filecontent = new byte[filelength.intValue()];  
        try {  
            FileInputStream in = new FileInputStream(file);  
            in.read(filecontent);  
            in.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        try {  
            return new String(filecontent, encoding);  
        } catch (UnsupportedEncodingException e) {  
            System.err.println("The OS does not support " + encoding);  
            e.printStackTrace();  
            return null;  
        } 
		
	}



	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		adminRoute = getJsonFromFile("src/main/java/service/app/json/AdminRoute.json");
		System.err.println(adminRoute);
	}
	
}
