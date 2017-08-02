package service.app.tramodel.response;

import java.util.List;

import service.app.model.AllTypesItem;
import service.app.model.UserInfo;

public class SetResponse extends BaseResponse {
	
	List<UserInfo> users;
	List<AllTypesItem> dicts;
	public List<UserInfo> getUsers() {
		return users;
	}
	public void setUsers(List<UserInfo> users) {
		this.users = users;
	}
	public List<AllTypesItem> getDicts() {
		return dicts;
	}
	public void setDicts(List<AllTypesItem> dicts) {
		this.dicts = dicts;
	}
}
