package service.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import service.app.model.AllTypesItem;
import service.app.model.UserInfo;
import service.app.server.LogService;
import service.app.server.SetService;
import service.app.tramodel.ErrCode;
import service.app.tramodel.RequestData;
import service.app.tramodel.response.SetResponse;
import service.app.util.CacheManager;
import service.app.util.FileStorageUtil;
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
	
	@Autowired
	FileStorageUtil fs;
	
	@Autowired
	CacheManager cm;
	
	private final static Logger logger = LoggerFactory.getLogger(SetController.class);
	private final static String suppout = "support";
	
	
	
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
				if(ss.setAllType(data.getTypeName(), data.getTypeS())){
						sr.setErrCode(ErrCode.SETTING_OK);
						cm.cleanBackCache();
				}
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
	
	
	@PostMapping(value="/helpupload" )
	@ResponseBody
	public SetResponse uploadHelpDoc(HttpServletResponse response,RequestData data,MultipartFile file){
		SetResponse sr = new SetResponse();
		sr.setErrCode(ErrCode.SETTING_ERR);
		System.err.println(data.getToken());
		
		try {
			fs.store(file);
			sr.setErrCode(ErrCode.SETTING_OK);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString());
		}
		return sr;
	}
	
	@GetMapping(value="/helpdownload/helpDocument.pdf" )
	@ResponseBody
	public ResponseEntity<Resource> downloadHelpDoc(HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		try {
			 Resource file = fs.loadAsResource();
		        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
		                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString());
		}
		return ResponseEntity.notFound().build();
	}
	
	
	
	@GetMapping("/getsupport")
	@ResponseBody
	public SetResponse getSupportMessage(HttpServletResponse response,RequestData data){
		
		SetResponse sr = new SetResponse();
		sr.setErrCode(ErrCode.SETTING_ERR);
		try {
			sr.setRoleName(fs.getMessageFrom(suppout, "1:2:3:4"));
			sr.setErrCode(ErrCode.SETTING_OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.toString());
		}
		return sr;
	}
	
	
	@GetMapping("/setsupport")
	@ResponseBody
	public SetResponse setSupportMessage(HttpServletResponse response,RequestData data){
		SetResponse sr = new SetResponse();
		sr.setErrCode(ErrCode.SETTING_ERR);
		try {
			if(fs.setMessageTo(suppout, data.getRoleName()))
				sr.setErrCode(ErrCode.SETTING_OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.toString());
		}
		return sr;
	}
	
	
	
	
	
	
	
	
}
