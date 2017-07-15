package service.app.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import service.app.dao.BaseDao;

@Service
public class BaseService {
	
	@Autowired
	BaseDao baseDao;
	
	@Transactional
	public void addUser(){
		baseDao.addUser("ttt", 12);
		baseDao.addUser("tt2t", 12);
		baseDao.addUser("ttt2", 12);
	}
	
}
