package service.app.util;

import service.app.tramodel.RequestData;
import service.app.tramodel.RoleType;

public class CacheNameTools {

	public static String getResultCacheName(RequestData rd){
		StringBuffer sb = new StringBuffer();
		sb.append(rd.getTimeRange());
		sb.append("_"+rd.getPlace1());
		sb.append("_"+rd.getPlace2());
		sb.append("_"+rd.getTranType());
		sb.append("_"+rd.getCityType());
		if(RoleType.ROLE_ADMIN.equals(rd.getRoleType())||
				RoleType.ROLE_TRAFFIC.equals(rd.getRoleType()))
			sb.append("_"+RoleType.ROLE_TRAFFIC);
		else if(RoleType.ROLE_ENTERPEICE.equals(rd.getRoleType()))
			sb.append("_"+rd.getRoleName());
		else 
			sb.append("_"+rd.getRoleType());

		return sb.toString();
	}
	
	public static String getTYMCacheName(String service,String YM){
		return service+"_"+YM;
	}
	
	
	
}
