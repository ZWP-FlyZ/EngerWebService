package service.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.app.model.UserInfo;
import service.app.server.SetService;
import service.app.tramodel.ErrCode;
import service.app.tramodel.RequestData;
import service.app.tramodel.RoleType;
import service.app.tramodel.response.SetResponse;
import service.app.util.TypeGetter;

@Controller
public class SetController extends BaseController {
	
	@Autowired
	SetService ss;
	
	@Autowired
	TypeGetter tg;
	
	
	
	
	@RequestMapping("/getusers.json")
	@ResponseBody
	public SetResponse getUsers(HttpServletResponse response,RequestData data){
		List<UserInfo> infoes = new ArrayList<UserInfo>();
		List<UserInfo> tmps = ss.getUsers();
		if(tmps!=null)
			for(UserInfo u:tmps){
				u.setPassword(null);
				infoes.add(u);
			}
				
		
		SetResponse sr = new SetResponse();
		sr.setErrCode(ErrCode.SETTING_OK);
		sr.setUsers(infoes);
		return sr;
	}
	
	@RequestMapping("/reguser.json")
	@ResponseBody
	public SetResponse regUser(HttpServletResponse response,RequestData data){
		SetResponse sr = new SetResponse();
		
		data.setPassword("123456");
		data.setRoleType(RoleType.ROLE_TRAFFIC);
		data.setName("zwp");
			
		if(data.getUsername()==null||
					ss.isContarinUsername(data.getUsername())){
			sr.setErrCode(ErrCode.SETTING_REG_USER_ERR);
		}else if(ss.regUser(data))
			sr.setErrCode(ErrCode.SETTING_OK);
		else
			sr.setErrCode(ErrCode.SETTING_ERR);
		
		return sr;
	}
	
	
	@RequestMapping("/deluser.json")
	@ResponseBody
	public SetResponse delUser(HttpServletResponse response,RequestData data){
		SetResponse sr = new SetResponse();
			
		if(data.getUsername()==null||
					!ss.isContarinUsername(data.getUsername())){
			sr.setErrCode(ErrCode.SETTING_ERR);
		}else if(ss.delUser(data.getUsername()))
			sr.setErrCode(ErrCode.SETTING_OK);
		else
			sr.setErrCode(ErrCode.SETTING_ERR);
		
		return sr;
	}
	
	@RequestMapping("/edituser.json")
	@ResponseBody
	public SetResponse editUser(HttpServletResponse response,RequestData data){
		SetResponse sr = new SetResponse();
		data.setName("zwp1");
			
		if(data.getUsername()==null||
					!ss.isContarinUsername(data.getUsername())){
			sr.setErrCode(ErrCode.SETTING_ERR);
		}else if(ss.editUser(data))
			sr.setErrCode(ErrCode.SETTING_OK);
		else
			sr.setErrCode(ErrCode.SETTING_ERR);
		
		return sr;
	}
	
	
	
}
