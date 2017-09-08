package service.app.callables;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.app.dataservice.LruDataService;
import service.app.model.AllSimData;
import service.app.tramodel.RequestData;
import service.app.tramodel.RoleType;
import service.app.tramodel.TypeData;
import service.app.util.TimeTools;
import service.app.util.TwoDecMap;
import service.app.util.TypeGetter;

public class CityTraDataWork extends DataAuthBaseWork {

	private final static Logger logger = LoggerFactory.getLogger(CityTraDataWork.class);
	
	public CityTraDataWork(RequestData data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<TwoDecMap<String, ? extends TypeData>> call() throws Exception {
		
		
		logger.debug("IN IndexDataWork data=["+data.getAllSimMessage()+"] targe=["+data.getTargeDay()+"]");
		List<TwoDecMap<String, ? extends TypeData>> maps = new ArrayList<>();
		if(data==null) return maps;
		
		LruDataService lds = ws.lds;
		String tgd = data.getTargeDay();
		String[] nowBefMon  = TimeTools.getNowBeforeYM();///[yyyy-mm-dd,yyyy-mm]
		if(TimeTools.compareYMD(nowBefMon[0], tgd)<0) return maps;//时间超过当前
		String tmpYM = TimeTools.getYearMonth(tgd);
		
		String[] tranTypes = null;
		List<AllSimData> allData = new ArrayList<>();
		int flag = 0;
		if(data.getRoleType().equals(RoleType.ROLE_TRAFFIC)){
			tranTypes = TypeGetter.ALL_TRA;
		}else if(data.getRoleType().equals(RoleType.ROLE_ENTERPEICE)){
			flag = 1;
			tranTypes = TypeGetter.ALL_TRA;
		}else if(data.getRoleType().equals(RoleType.ROLE_LAND)){
			tranTypes = TypeGetter.LAND_TRA;
		}else if(data.getRoleType().equals(RoleType.ROLE_WATER)){
			tranTypes = TypeGetter.WATER_TRA;
		}else if(data.getRoleType().equals(RoleType.ROLE_ADMIN)) {
			tranTypes = TypeGetter.ALL_TRA;
		}
		
		if(indexOf(tranTypes,data.getTranType())!=-1){
			tranTypes = new String[1];
			tranTypes[0]=data.getTranType();
		}else
			tranTypes = new String[0];
		
		if(tranTypes.length==0) return maps;//非角色内的数据获取
		
		try {
			
			if(TimeTools.getYearMonth(nowBefMon[0]).equals(tmpYM)){//本月
				for(String tt:tranTypes)
					allData.addAll(lds.getTYMCacheDataNow(tt, tgd));
				
			}
//			else if(nowBefMon[1].equals(tmpYM)){//上月
//				
//			}
			else{//上月一起				
				for(String tt:tranTypes)
					allData.addAll(lds.getTYMCacheDataBefore(tt, tmpYM));
			}
			
			TwoDecMap<String,TypeData> citMap = new TwoDecMap<>();
			TypeData td = null;
			for(AllSimData d:allData){
				if(!checkDataAuth(d,data,flag)) continue;
				td = citMap.get(d.getPlace1(),d.getPlace1());
				if(td==null)
				{
					td = new TypeData();
					//td.setType(tmp);
				}
				
				td.addEng(d.getFuelCsption());
				//td.addLen(d.getGoTurn());
				citMap.put(d.getPlace1(),d.getPlace1(),td);
			}
			maps.add(citMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			maps.clear();
			logger.error(e.toString());
		}
		return maps;
	}
	


	private <T> int indexOf(T[] ts,T t){
		if(ts==null||t==null) return -1;
		int i =0;
		for(T tt:ts){
			if(tt.equals(t)) return i;
			i++;
		}
		return -1;
	}
	

}
