package service.app.worker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import service.app.dataservice.LruDataService;
import service.app.util.TypeGetter;

@Service
public class WorkerService {
	
	@Autowired
	public LruDataService lds;
	
	@Autowired
	public TypeGetter tg;
	
}
