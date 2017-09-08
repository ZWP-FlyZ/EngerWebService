package service.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.app.server.LogService;
import service.app.timwin.TimWinMap;



public class TestController  implements InitializingBean{

	@Autowired
	LogService bs;
	
	@Autowired
	TimWinMap tokens;
	
	

	class TestData{
		String username;
		String password;
		String token;
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		
	}
	
	@RequestMapping("/reLogIn.json")
	@ResponseBody
	public String testReDirect(){
		return "index";
	}
	
	@RequestMapping("/testindex.json")
	@ResponseBody
	public String testIndex(HttpServletRequest request,String username){
		

		return "testIndex";
	}
	
	@RequestMapping(value ="/testlogin.json")
	@ResponseBody
	public TestData testLogIn(HttpServletRequest request,HttpServletResponse response,String username,String password){

		return null;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub

	}
	
	
	
	
	

}
