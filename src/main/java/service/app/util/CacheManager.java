package service.app.util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Component;

@Component
public class CacheManager {
	
	private final  MyLRU<String, String> mTrYeMoCache = new MyLRU<>(8*3*12);//最多保存8种能耗3年数据
	private final  MyLRU<String, String> mTrYeMoDaCache = new MyLRU<>(32);//最多保存32个   月数据
	
	private final  MyLRU<String, String> mRelTimTrYeMoDaCache = new MyLRU<>(5*10);//最多保存5中能耗 10个      一天内实时数据
	private final  MyLRU<String, String> mRelTimTrYeMoDaHoCache = new MyLRU<>(25);//最多保存5中能耗 10个      一天内实时数据
	
	private final MyLRU<String,String>  mFrontLRU= new MyLRU<>(100*1000);

	private Lock lock = new ReentrantLock();
	
	public String mTryemocacheGet(String k){
		lock.lock();
		String t = mTrYeMoCache.get(k);
		lock.unlock();
		return t;
	}
	
	public String mTryemocacheAdd(String k,String v){
		lock.lock();
		String t = mTrYeMoCache.add(k,v);
		lock.unlock();
		return t;
	}
	
	public String mTrYeMoDaCacheGet(String k){
		lock.lock();
		String t = mTrYeMoDaCache.get(k);
		lock.unlock();
		return t;
	}
	
	public String mTrYeMoDaCacheAdd(String k,String v){
		lock.lock();
		String t = mTrYeMoDaCache.add(k,v);
		lock.unlock();
		return t;
	}
	
	public String mRelTimTrYeMoDaCacheGet(String k){
		lock.lock();
		String t = mRelTimTrYeMoDaCache.get(k);
		lock.unlock();
		return t;
	}
	
	public String mRelTimTrYeMoDaCacheAdd(String k,String v){
		lock.lock();
		String t = mRelTimTrYeMoDaCache.add(k,v);
		lock.unlock();
		return t;
	}
	
	public String mRelTimTrYeMoDaHoCacheGet(String k){
		lock.lock();
		String t = mRelTimTrYeMoDaHoCache.get(k);
		lock.unlock();
		return t;
	}
	
	public String mRelTimTrYeMoDaHoCacheAdd(String k,String v){
		lock.lock();
		String t = mRelTimTrYeMoDaHoCache.add(k,v);
		lock.unlock();
		return t;
	}
	
	public String mFrontCacheGet(String k){
		lock.lock();
		String t = mFrontLRU.get(k);
		lock.unlock();
		return t;
	}
	
	public String mFrontCacheAdd(String k,String v){
		lock.lock();
		String t = mFrontLRU.add(k,v);
		lock.unlock();
		return t;
	}
	

	public void cleanFrontCache(){
		lock.lock();
		mFrontLRU.clear();
		lock.unlock();
	}
	
	public void cleanBackCache(){
		lock.lock();
		mTrYeMoCache.clear();
		mTrYeMoDaCache.clear();
		mRelTimTrYeMoDaCache.clear();
		mRelTimTrYeMoDaHoCache.clear();
		lock.unlock();
	}
	
	
	
}
