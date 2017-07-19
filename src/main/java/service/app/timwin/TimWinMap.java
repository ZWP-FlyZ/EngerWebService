package service.app.timwin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;


@Component
public class TimWinMap implements InitializingBean {
	
	private int mWinSize;
	private int mUniTimeLen;
	private int mWinSizeP;
	private boolean mRun = false;
	private final EdgeData flag= new EdgeData();
	private Thread mThread;
	
	private Lock lock = new ReentrantLock();

	
	public TimWinMap(){

	};
	public TimWinMap(int winSize,int unitTimeLenMs){
		this.mWinSize = winSize;
		this.mWinSizeP  = winSize+1;
		this.mUniTimeLen = unitTimeLenMs;
		if(mWinSize<1||unitTimeLenMs<100) 
		{
			this.mWinSize =1;
			this.mWinSizeP = 2;
			this.mUniTimeLen = 1000;
		} // end 
	}
	

	
	public void start(){
		if(mRun) return;
		for(int i=0;i< mWinSizeP ;i++)
			flag.dataList.add(new HashMap<String,Object>());
		mThread = new Thread(mRunable);
		mRun = true;
		mThread.start();
	}
	
	Runnable mRunable = new Runnable(){
		@Override
		public void run() {
			long in ;
			
			while(true){
				
				try {
					Thread.sleep(mUniTimeLen);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//System.err.println(Thread.currentThread().getName());
				lock.lock();

				in = flag.pushIndex;
				in++;
				flag.pushIndex = in;
				if(flag.dataList.get((int)(in%mWinSizeP)).size()>0)
					flag.dataList.get((int)(in%mWinSizeP)).clear();
				System.err.println("Thread "+Thread.currentThread().getName()+"  index = " + in + " dataList = " +showAll(flag.dataList));

				lock.unlock();
			}// endless loop
		}
		
	};
	
	class EdgeData{
		long pushIndex = 0;
		final List<Map<String,Object>> dataList = new ArrayList<>();
	}
	
	
	public boolean pushData(String name,Object data){
		if(mRun==false)
			return false;
		
		boolean f = false;

			lock.lock();
			for(Map<String,Object> m:flag.dataList){
				if(m.remove(name)!=null) break;
			}
			flag.dataList.get((int)(flag.pushIndex%mWinSizeP)).put(name, data);
			f = true;
			//System.err.println(name+"   " + data.toString());
			System.err.println("Thread:"+Thread.currentThread().getName()+showAll(flag.dataList));
			showAll(flag.dataList);
			lock.unlock();
		return f;
	}
	
	
	public boolean repushIfContain(String name,Object data){
		if(mRun==false)
			return false;
		
		boolean f = false;
			lock.lock();

			for(Map<String,Object> m:flag.dataList){
				if(m.remove(name)!=null) {
					f = true;
					break;
				}
			}
			if(f)
				flag.dataList.get((int)(flag.pushIndex%mWinSizeP)).put(name, data);
			lock.unlock();			
		return f;
	}
	
	

	
	public  boolean isContainName(String name){
		if(mRun==false)
			return false;
		boolean isC = false;

			lock.lock();

			for(Map<String,Object> m:flag.dataList){
				if(m.containsKey(name))
					return true;
			}

			lock.unlock();
		
		return isC;
	}
	
	public  boolean isContainData(Object data){
		if(mRun==false)
			return false;
		boolean isC = false;

			lock.lock();

			for(Map<String,Object> m:flag.dataList){
				if(m.containsValue(data))
					return true;
			}

			lock.unlock();
		
		return isC;
	}
	
	private String  showAll(List<Map<String,Object>> ds){
		StringBuffer sb = new StringBuffer();
		sb.append("{ ");
		for(Map<String,Object> m:ds){
			sb.append("[ ");
			for(Object o:m.values())
				sb.append(o.toString()+",");
			sb.append("]");
		}
		sb.append("}");
		return sb.toString();
	}
	
	public int getmWinSize() {
		return mWinSize;
	}
	public void setmWinSize(int mWinSize) {
		if(mRun) return ;
		this.mWinSizeP = mWinSize+1;
		this.mWinSize = mWinSize;
	}
	public int getmUniTimeLen() {
		
		return mUniTimeLen;
	}
	public void setmUniTimeLen(int mUniTimeLen) {
		if(mRun) return ;
		this.mUniTimeLen = mUniTimeLen;
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		if(mRun) return ;
		this.mWinSize =1;
		this.mWinSizeP = 2;
		this.mUniTimeLen = 1000;
	}
	
	

		
}
