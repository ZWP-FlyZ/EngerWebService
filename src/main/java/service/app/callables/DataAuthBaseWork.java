package service.app.callables;

import java.util.List;

import service.app.model.AllSimData;
import service.app.tramodel.RequestData;
import service.app.tramodel.TypeData;
import service.app.util.TwoDecMap;

public abstract class DataAuthBaseWork extends BaseWork<RequestData, List<TwoDecMap<String,? extends TypeData>>> {

	public DataAuthBaseWork(RequestData data) {
		super(data);
		// TODO Auto-generated constructor stub
	}
	
	protected boolean checkDataAuth(AllSimData d,RequestData data,int flag){
		
		if(data.getRoleName()!=null&&flag==1&&!data.getRoleName().equals("")&&
						!data.getRoleName().equals(d.getCompanyId()))
				return false;

		if(data.getPlace1()!=null&&!data.getPlace1().equals("")&&
				!data.getPlace1().equals(d.getPlace1()))
				return false;
		
		if(data.getPlace2()!=null&&!data.getPlace2().equals("")&&
				!data.getPlace2().equals(d.getPlace2()))
				return false;
		return true;
		
	}

}
