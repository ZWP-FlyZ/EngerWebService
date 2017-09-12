package service.app.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.app.model.UserInfo;
import service.app.server.LogService;
import service.app.timwin.TimWinMap;
import service.app.tramodel.ErrCode;
import service.app.tramodel.RequestData;
import service.app.tramodel.response.BaseResponse;
import service.app.tramodel.response.LogInResponse;
import service.app.util.MyEncode;
import service.app.util.TokenCreater;

@Controller
public class LogController  implements InitializingBean{

	@Autowired
	TimWinMap tokenMap;
	
	@Autowired
	LogService bs;
	
	final static Logger logger = LoggerFactory.getLogger(LogController.class);
	
	@RequestMapping("/login.json")
	@ResponseBody
	public LogInResponse login(HttpServletResponse response,RequestData data){
		LogInResponse resp = new LogInResponse();
		response.setHeader("Access-Control-Allow-Origin", "*");
		resp.setErrCode(ErrCode.LOGIN_ERR_INFO);
		try {
			UserInfo userInfo = bs.getLogInData(data.getUsername());
			
			if(userInfo!=null&& userInfo.getPassword().equals(MyEncode.encode(data.getPassword()))){
				resp.setErrCode(ErrCode.LOGIN_OK);//登录成功
				userInfo.setPassword(null);
				resp.setUserInfo(userInfo);
				String token = TokenCreater.getToken(data.getUsername());
				resp.setToken(token);
				tokenMap.pushData(data.getUsername(), token);	
			}

		} catch (Exception e) {
			logger.error(e.toString(),e);
			// TODO: handle exception
		}
		
		return resp;
	}
	
	
	
	@RequestMapping("/logout.json")
	@ResponseBody
	public BaseResponse logout(HttpServletResponse response,RequestData data){
		response.setHeader("Access-Control-Allow-Origin", "*");
		BaseResponse br = new BaseResponse();
		if(tokenMap.remove(data.getUsername()))
			br.setErrCode(ErrCode.LOGOUT_OK);
		else
			br.setErrCode(ErrCode.LOGOUT_ERR);
		return br;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		tokenMap.setmWinSize(20);
		tokenMap.setmUniTimeLen(180*1000);
		tokenMap.start();
	}
	
	
	
	
}
