package service.app.nosql;

import java.util.List;

import org.springframework.data.annotation.Id;

import service.app.model.AllSimData;

public class CacheData {
	
	@Id
	public  String id;
	
	public String name;
	public List<? extends AllSimData> data;
	

	public CacheData(String name,List<? extends AllSimData> data){
		this.id = ""+name.hashCode();
		this.name = name;
		this.data = data;
	}

	
	
	
}
