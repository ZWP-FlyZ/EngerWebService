package service.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.app.model.AllTypesItem;
import service.app.model.UserInfo;
import service.app.server.LogService;
import service.app.server.SetService;
import service.app.tramodel.ErrCode;
import service.app.tramodel.RequestData;
import service.app.tramodel.response.SetResponse;
import service.app.util.MD5Util;
import service.app.util.MyEncode;
import service.app.util.TypeGetter;

@Controller
public class SetController  {
	
	@Autowired
	SetService ss;
	
	@Autowired
	LogService ls;
	
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
			
		if(data.getUsername()==null||
					!ss.isContarinUsername(data.getUsername())){
			sr.setErrCode(ErrCode.SETTING_ERR);
		}else if(ss.editUser(data))
			sr.setErrCode(ErrCode.SETTING_OK);
		else
			sr.setErrCode(ErrCode.SETTING_ERR);
		
		return sr;
	}
	
	@RequestMapping("/getdictlist.json")
	@ResponseBody
	public SetResponse getDict(HttpServletResponse response,RequestData data){
		List<AllTypesItem> infoes = new ArrayList<>();
		List<AllTypesItem> tmps = ss.getAllTypes();
		if(tmps!=null)
			infoes.addAll(tmps);
				
		SetResponse sr = new SetResponse();
		sr.setErrCode(ErrCode.SETTING_OK);
		sr.setDicts(infoes);
		
		return sr;
	}
	
	@RequestMapping("/setdict.json")
	@ResponseBody
	public SetResponse setDict(HttpServletResponse response,RequestData data){

	
		SetResponse sr = new SetResponse();
		sr.setErrCode(ErrCode.SETTING_ERR);
		if(data.getTypeName()==null){
			sr.setErrCode(ErrCode.SETTING_ERR);
		}else if(tg.setTypeAll(data.getTypeName(), data.getTypeS()))
				if(ss.setAllType(data.getTypeName(), data.getTypeS()))
					sr.setErrCode(ErrCode.SETTING_OK);

		return sr;
	}
	
	
	@RequestMapping("/passsetting.json")
	@ResponseBody
	public SetResponse setPassword(HttpServletResponse response,RequestData data){
		
		
		

		
		SetResponse sr = new SetResponse();
		sr.setErrCode(ErrCode.SETTING_ERR);
		UserInfo userInfo =ls.getLogInData(data.getUsername()) ;
		
		if(userInfo!=null && 
				userInfo.getPassword().equals(MyEncode.encode(data.getPassword()))){
			if( data.getPasswordN()==null)
				sr.setErrCode(ErrCode.SETTING_PASS_ERR);
			else {
				data.setPassword(data.getPasswordN());
				if(ss.setPassword(data))
					sr.setErrCode(ErrCode.SETTING_OK);
			}
		}else
			sr.setErrCode(ErrCode.SETTING_PASS_ERR);
		return sr;
	}
	
	
	
	
	
	
	
}
