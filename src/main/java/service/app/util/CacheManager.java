package service.app.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;


@Component
public class CacheManager implements InitializingBean{
	
	private final  MyLRU<String, String> mTrYeMoCache = new MyLRU<>(8*3*12);//最多保存8种能耗3年数据
	private final  MyLRU<String, String> mTrYeMoDaCache = new MyLRU<>(32);//最多保存32个   月数据
	
	private final  MyLRU<String, String> mRelTimTrYeMoDaCache = new MyLRU<>(5*10);//最多保存5中能耗 10个      一天内实时数据
	private final  MyLRU<String, String> mRelTimTrYeMoDaHoCache = new MyLRU<>(25);//最多保存5中能耗 10个      一天内实时数据
	
	private final MyLRU<String,String>  mFrontLRU= new MyLRU<>(100*1000);

	private final ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
	
	private final Logger logger = LoggerFactory.getLogger(CacheManager.class);
	
	
	private ReadWriteLock rwl1 = new ReentrantReadWriteLock();
	private ReadWriteLock rwl2 = new ReentrantReadWriteLock();
	private ReadWriteLock rwl3 = new ReentrantReadWriteLock();
	private ReadWriteLock rwl4 = new ReentrantReadWriteLock();
	private ReadWriteLock rwl5 = new ReentrantReadWriteLock();
	
	public String mTryemocacheGet(String k){
		rwl1.readLock().lock();
		String t=null;
		try{
			 t = mTrYeMoCache.get(k);
		}finally {
			rwl1.readLock().unlock();
		}
		return t;
	}
	
	public String mTryemocacheAdd(String k,String v){
		rwl1.writeLock().lock();
		String t = null;
		try{
			t = mTrYeMoCache.add(k,v);
		}finally{
			rwl1.writeLock().unlock();
		}
		return t;
	}
	
	public String mTrYeMoDaCacheGet(String k){
		rwl2.readLock().lock();
		String t = null;
		try{
			t = mTrYeMoDaCache.get(k);
		}finally{
			rwl2.readLock().unlock();
		}
		return t;
	}
	
	public String mTrYeMoDaCacheAdd(String k,String v){
		rwl2.writeLock().lock();
		String t = null;
		try{
			 t = mTrYeMoDaCache.add(k,v);
		}finally{
		rwl2.writeLock().unlock();
		}
		return t;
	}
	
	public String mRelTimTrYeMoDaCacheGet(String k){
		rwl3.readLock().lock();
		String t = null;
		try{
			t = mRelTimTrYeMoDaCache.get(k);
		}finally{
				rwl3.readLock().unlock();
		}
		
		return t;
	}
	
	public String mRelTimTrYeMoDaCacheAdd(String k,String v){
		rwl3.writeLock().lock();
		String t = null;
		try{
			 t = mRelTimTrYeMoDaCache.add(k,v);
		}finally{
			rwl3.writeLock().unlock();
		}
		
		
		return t;
	}
	
	public String mRelTimTrYeMoDaHoCacheGet(String k){
		rwl4.readLock().lock();
		String t = null;
		try{
			t = mRelTimTrYeMoDaHoCache.get(k);
		}finally{
			rwl4.readLock().unlock();
		}
		
		return t;
	}
	
	public String mRelTimTrYeMoDaHoCacheAdd(String k,String v){
		rwl4.writeLock().lock();
		String t = null;
		try{
			t = mRelTimTrYeMoDaHoCache.add(k,v);
		}finally{
			rwl4.writeLock().unlock();
		}
		return t;
	}
	
	public String mFrontCacheGet(String k){
		rwl5.readLock().lock();
		String t = null;
		try{
			t = mFrontLRU.get(k);
		}finally{
			rwl5.readLock().unlock();
		}
		return t;
	}
	
	public String mFrontCacheAdd(String k,String v){
		rwl5.writeLock().lock();
		String t = null;
		try{
			t = mFrontLRU.add(k,v);
		}finally{
			rwl5.writeLock().unlock();
		}
		return t;
	}
	

	public void cleanFrontCache(){
		rwl5.writeLock().lock();
		try{
			mFrontLRU.clear();
		}finally{
			rwl5.writeLock().unlock();
		}
	}
	
	public void cleanBackCache(){
		
		rwl1.writeLock().lock();
		rwl2.writeLock().lock();
		rwl3.writeLock().lock();
		rwl4.writeLock().lock();
		
		try{
			mTrYeMoCache.clear();
			mTrYeMoDaCache.clear();
			mRelTimTrYeMoDaCache.clear();
			mRelTimTrYeMoDaHoCache.clear();
		}finally{
			rwl1.writeLock().unlock();
			rwl2.writeLock().unlock();
			rwl3.writeLock().unlock();
			rwl4.writeLock().unlock();
		}	
	}

	
	

	
	Runnable runable = new Runnable(){
		@Override
		public void run() {
			try {
				logger.debug("clear cache");
				cleanFrontCache();
				cleanBackCache();
			} catch (Exception e) {
				logger.error("CacheManager clear Cache err",e);
			}
		}
	};

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		pool.scheduleAtFixedRate(runable, 2,2, TimeUnit.DAYS);
	}
		
}
