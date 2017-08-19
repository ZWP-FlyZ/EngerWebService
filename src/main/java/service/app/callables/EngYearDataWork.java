package service.app.callables;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.app.dataservice.LruDataService;
import service.app.model.AllSimData;
import service.app.model.PortProData;
import service.app.tramodel.RequestData;
import service.app.tramodel.RoleType;
import service.app.tramodel.TypeData;
import service.app.util.TimeTools;
import service.app.util.TwoDecMap;
import service.app.util.TypeGetter;

public class EngYearDataWork extends DataAuthBaseWork {

	
	private final static Logger logger = LoggerFactory.getLogger(EngYearDataWork.class);
	
	public EngYearDataWork(RequestData data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
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
		List<PortProData> portData = null;
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
		
		try {
			if(TimeTools.getYearMonth(nowBefMon[0]).equals(tmpYM)){//本月
				for(String tt:tranTypes)
					allData.addAll(lds.getTYMCacheDataNow(tt, tgd));
				if(!data.getRoleType().equals(RoleType.ROLE_LAND))
					portData= (List<PortProData>) lds.getTYMCacheDataNow(TypeGetter.TT_WATER_PORT, tgd);
			}
//			else if(nowBefMon[1].equals(tmpYM)){//上月
//				
//			}
			else{//上月一起				
				for(String tt:tranTypes)
					allData.addAll(lds.getTYMCacheDataBefore(tt, tmpYM));
				if(!data.getRoleType().equals(RoleType.ROLE_LAND))
					portData= (List<PortProData>) lds.getTYMCacheDataBefore(TypeGetter.TT_WATER_PORT, tmpYM);
			}
			
			TwoDecMap<String,TypeData> engMonthMap = new TwoDecMap<>();
			TypeData td = null;
			String tmp;
			for(AllSimData d:allData){
				if(!checkDataAuth(d,data,flag)) continue;
				tmp = TimeTools.getYearMonth(d.getInTime());
				td = engMonthMap.get(d.getFuelType(),tmp);
				if(td==null)
				{
					td = new TypeData();
					td.setType(tmp);
				}
				
				td.addEng(d.getFuelCsption());
				engMonthMap.put(d.getFuelType(),tmp,td);
			}
			
			if(portData!=null){
				for(PortProData d:portData){
					if(!checkDataAuth(d,data,flag)) continue;
					tmp = TimeTools.getYearMonth(d.getInTime());
					if(tmp!=null){
						td = engMonthMap.get(TypeGetter.FT_CHAI_YOU,tmp);//柴油
						if(td==null)
						{
							td = new TypeData();
							td.setType(tmp);
						}
						td.addEng(d.getDiesel());

						engMonthMap.put(TypeGetter.FT_CHAI_YOU,tmp,td);
						
						td = engMonthMap.get(TypeGetter.FT_QI_YOU,tmp);
						if(td==null)
						{
							td = new TypeData();
							td.setType(tmp);
						}
						td.addEng(d.getGasoline());

						engMonthMap.put(TypeGetter.FT_QI_YOU,tmp,td);
						
						td = engMonthMap.get(TypeGetter.FT_MEI_YOU,tmp);
						if(td==null)
						{
							td = new TypeData();
							td.setType(tmp);
						}
						td.addEng(d.getCoal());

						engMonthMap.put(TypeGetter.FT_MEI_YOU,tmp,td);
						
						td = engMonthMap.get(TypeGetter.FT_DIAN_NENG,tmp);
						if(td==null)
						{
							td = new TypeData();
							td.setType(tmp);
						}
						td.addEng(d.getPower());

						engMonthMap.put(TypeGetter.FT_DIAN_NENG,tmp,td);
						
						td = engMonthMap.get(TypeGetter.FT_QI_TA,tmp);
						if(td==null)
						{
							td = new TypeData();
							td.setType(tmp);
						}
						td.addEng(d.getOther());
						engMonthMap.put(TypeGetter.FT_QI_TA,tmp,td);
					}
				}
			}// end pp
			
			maps.add(engMonthMap);
			
		}  catch (Exception e) {
			e.printStackTrace();
			maps.clear();
			logger.error(e.toString());
		}
		return maps;
	}

}
