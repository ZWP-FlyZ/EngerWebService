package service.app.nosql;

import org.springframework.data.annotation.Id;

public class RespResult {
	
	@Id
	public String id;
	
	public String name;
	public Object response;

	public RespResult(String name,Object response){
		this.id=""+name.hashCode();
		this.name = name;
		this.response = response;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "id=["+id+"] name=["+name+"] ";
	}
	
	
	
}
