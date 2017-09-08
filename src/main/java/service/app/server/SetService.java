package service.app.server;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import service.app.dao.SetDao;
import service.app.model.AllTypesItem;
import service.app.model.UserInfo;
import service.app.tramodel.RequestData;
import service.app.util.MyEncode;
import service.app.util.TimeTools;

@Service
public class SetService {
	
	
	
	
	private final static Logger logger = LoggerFactory.getLogger(SetService.class) ;
	@Autowired
	SetDao sd;
	
	public List<UserInfo> getUsers(){
		return sd.getUsers();
	}
	
	public boolean isContarinUsername(String username){
		
		return sd.isContainUser(username)==1;
	}
	
	public boolean regUser(RequestData data){
		try {
			data.setPassword( MyEncode.encode(data.getPassword()));
			data.setUpAuth(TimeTools.getNow());
//			System.err.println(data.getPlace1());
//			System.err.println(data.getPlace2());
			sd.reguser(data);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean delUser(String username){
		boolean is = false;
		try {
			is = (sd.deluser(username)==1);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return is;
	}
	
	public boolean editUser(RequestData data){

		try {
			 sd.edituser(data);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}
	
	public boolean setPassword(RequestData data){
		try {
			data.setPassword(MyEncode.encode(data.getPassword()));
			sd.setpass(data);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}
	
	
	public List<AllTypesItem> getAllTypes(){
		return sd.getalltypes();
	}
	
	public AllTypesItem getAllTypeByTypeName(String typeName){
		return sd.gettypebyname(typeName);
	}
	
	public boolean setAllType(String typeName,String typeS){
		try {
			 sd.settypebyname(typeName, typeS);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	

}
