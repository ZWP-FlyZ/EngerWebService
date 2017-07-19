package service.app.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TwoDecMap<K,V> {
	private Map<K,Map<K,V>> mMap = null;
	
	public TwoDecMap(){
		mMap = new HashMap<>();
	}
	
	public boolean put(K x,K y,V value){
		if(x==null||y==null) throw new NullPointerException();
		if(!mMap.containsKey(x)){
			Map<K,V> m = new HashMap<>();
			mMap.put(x, m);
		}
		mMap.get(x).put(y, value);
		return true;
	}
	public V get(K x,K y){
	
		if(x==null||y==null)
			throw new NullPointerException();
		if(mMap.get(x)==null) 
			return null;
		return mMap.get(x).get(y);
	}
	
	public Set<K> getXset(){
		return mMap.keySet();
	}
	
	public Map<K,V>getYMap(K x){
		return mMap.get(x);
	}
	
	
	public V remove(K x,K y){
		if(x==null||y==null)
			throw new NullPointerException();
		if(mMap.get(x)==null) 
			throw new NullPointerException();
		return mMap.get(x).remove(y);
	}
	
	
	public void cleanAll(){
		mMap.clear();
	}
	public void cleanY(K x){
		if(x==null) return;
		if(mMap.get(x)==null) return ;
		mMap.get(x).clear();
	}
	

}
