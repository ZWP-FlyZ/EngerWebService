package service.app.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import service.app.tramodel.MyAdd;
import service.app.tramodel.TypeData;

public class TwoDecMap<K, V extends MyAdd> {
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
	
	
	public int sizeX(){
		return mMap.size();
	}
	
	
	@SuppressWarnings("unchecked")
	public TwoDecMap<K,V> add(TwoDecMap<K,V> ad){
		if(ad==null||ad.sizeX()==0)
				return this;
		Map<K,V> tmpY = null;
		Map<K, V> tmpAdY = null;
		V  tmpV  = null;;
		for(K adKeyX:ad.getXset()){
			tmpY = mMap.get(adKeyX);
			tmpAdY = ad.mMap.get(adKeyX);
			if(tmpY==null){
				this.mMap.put(adKeyX, ad.getYMap(adKeyX));
				continue;
			}
			for(K adKeyY:tmpAdY.keySet()){
				tmpV = tmpY.get(adKeyY);
				if(tmpV==null){
					this.mMap.get(adKeyX).put(adKeyY, tmpAdY.get(adKeyY));
					continue;
				}
				this.mMap.get(adKeyX).put(adKeyY, (V) tmpV.add(tmpAdY.get(adKeyY)));
			}
		}
		return this;
	}
		

}
