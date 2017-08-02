package service.app.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import service.app.dao.SetDao;
import service.app.model.AllTypesItem;
import service.app.model.UserInfo;
import service.app.tramodel.RequestData;

@Service
public class SetService {
	
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
			e.printStackTrace();
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
