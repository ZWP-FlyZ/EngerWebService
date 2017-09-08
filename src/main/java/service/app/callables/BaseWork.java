package service.app.callables;

import java.util.concurrent.Callable;

import service.app.worker.WorkerService;

public abstract class BaseWork<I,O> implements Callable<O> {
	
	protected I data;
	protected WorkerService ws;
	
	public BaseWork(I data){
		this.data = data;
	}

	public WorkerService getWs() {
		return ws;
	}

	public void setWs(WorkerService ws) {
		this.ws = ws;
	}
}
