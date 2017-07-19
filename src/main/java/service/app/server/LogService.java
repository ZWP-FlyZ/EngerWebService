package service.app.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import service.app.dao.BaseDao;
import service.app.model.UserInfo;

@Service
public class LogService {
	
	@Autowired
	BaseDao baseDao;
	
	@Transactional
	public void addUser(){
		baseDao.addUser("ttt", 12);
		baseDao.addUser("tt2t", 12);
		baseDao.addUser("ttt2", 12);
	}
	
	public UserInfo getLogInData(String username){
		UserInfo info = new UserInfo();
		info.setUsername("admin");
		info.setPassword("123456");
		return info;
	}
	
	
	
	
}
