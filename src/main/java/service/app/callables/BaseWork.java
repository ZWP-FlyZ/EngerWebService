package service.app.callables;

import java.util.concurrent.Callable;

public abstract class BaseWork<I,O> implements Callable<O> {
	
	protected I data;
	
	public BaseWork(I data){
		this.data = data;
	}

}
