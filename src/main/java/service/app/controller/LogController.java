package service.app.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.app.model.UserInfo;
import service.app.server.LogService;
import service.app.timwin.TimWinMap;
import service.app.tramodel.ErrCode;
import service.app.tramodel.LogInResponse;
import service.app.tramodel.RequestData;
import service.app.util.TokenCreater;

@Controller
public class LogController extends BaseController implements InitializingBean{

	
	private int COOKIE_TIME = 180;
	
	@Autowired
	LogService bs;
	
	@Autowired
	TimWinMap tokenMap;
	
	@RequestMapping("/login.json")
	@ResponseBody
	public LogInResponse login(HttpServletResponse response,RequestData data){
		LogInResponse resp = new LogInResponse();
		UserInfo userInfo = bs.getLogInData(data.getUsername());
		response.setHeader("Access-Control-Allow-Origin", "*");
		if(userInfo==null||
				!userInfo.getPassword().equals(data.getPassword())) 
			resp.setErrCode(ErrCode.LOGIN_ERR_INFO);//用户名或密码错误
		else
			{
				resp.setErrCode(ErrCode.LOGIN_OK);//登录成功
				userInfo.setPassword(null);
				
				resp.setUserInfo(userInfo);
				
				String token = TokenCreater.getToken(data.getUsername());
				Cookie cooToken = new Cookie("eToken", token);
				Cookie cooUsername = new Cookie("username", data.getUsername());
				cooToken.setMaxAge(COOKIE_TIME);
				cooUsername.setMaxAge(COOKIE_TIME);
				response.addCookie(cooUsername);
				response.addCookie(cooToken);
				resp.setToken(token);

				
				//tokenMap.pushData(username, token);
			}
		return resp;
	}
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		tokenMap.setmWinSize(4);
		tokenMap.setmUniTimeLen(180*1000);
		tokenMap.start();
	}
	
	
	
	
}
