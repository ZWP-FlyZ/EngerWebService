package service.app.controller;

import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;

import service.app.server.BaseService;
import service.app.timwin.TimWinMap;
import service.app.util.TokenCreater;

@Controller
public class TestController extends BaseController implements InitializingBean{

	@Autowired
	BaseService bs;
	
	@Autowired
	TimWinMap tokens;
	
	
	private final AtomicLong cot = new AtomicLong();

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
	
	@RequestMapping("/index.json")
	@ResponseBody
	public String testIndex(HttpServletRequest request,String username){
		
		String token =null;
		for(Cookie c:request.getCookies())
			if(c.getName().equals("token"))
				token = c.getValue();
		if(token==null||!tokens.isContainName(username))
			return "redirect:/reLogIn.json";
		tokens.repushIfContain(username, token);
		
		return token;
	}
	
	@RequestMapping(value ="/login.json")
	@ResponseBody
	public TestData testLogIn(HttpServletRequest request,HttpServletResponse response,String username,String password){
		TestData d = new TestData();
		d.username = "hello" + username;
		d.password = password+" "+cot.incrementAndGet();
		d.token = TokenCreater.getToken(username+"");
		System.err.println("username="+username+" password =" +password);

		tokens.pushData(username, d.token);
			
		Cookie cookie = new Cookie("token", d.token);
		cookie.setMaxAge(3600);
		response.addCookie(cookie);
		//bs.addUser();
		return d;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		tokens.setmWinSize(4);
		tokens.setmUniTimeLen(30*1000);
		tokens.start();
	}
	
	
	
	
	

}
