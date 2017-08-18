package service.app.worker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import service.app.callables.BaseWork;

@Component
public class CalWorker {

	@Autowired
	private WorkerService ws;
	
	private final static ExecutorService threadPool 
								= Executors.newCachedThreadPool();
	private final static Logger logger = LoggerFactory.getLogger(CalWorker.class);
	
	public <I,O> O submitWork(BaseWork<I,O> work){
		try {
			work.setWs(ws);
			return  threadPool.submit(work).get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		}
	}
	
	public <I,O> List<O> submitWorkList(List<?extends BaseWork<I,O>> workList){
		List<Future<O>> fs = new ArrayList<>();
		List<O> result = new ArrayList<>();
		if(workList==null) return null;
		
		for(BaseWork<I,O> w:workList){
			w.setWs(ws);
			fs.add(threadPool.submit(w));
		}
		try {
				for(Future<O> f:fs)
					result.add(f.get());
			} catch (InterruptedException | ExecutionException e) {
				
				e.printStackTrace();
				result.clear();
				logger.error(e.getMessage());
			}
		return result;
	}
	
	
}
