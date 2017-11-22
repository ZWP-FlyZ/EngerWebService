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

public class ReportDataWork extends DataAuthBaseWork {

	
	private final static Logger logger = LoggerFactory.getLogger(ReportDataWork.class);
	
	public ReportDataWork(RequestData data) {
		super(data);
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TwoDecMap<String, ? extends TypeData>> call() throws Exception {
		
		logger.debug("IN ReportDataWork data=["+data.getAllSimMessage()+"] targe=["+data.getTargeDay()+"]");
		List<TwoDecMap<String, ? extends TypeData>> maps = new ArrayList<>();
		if(data==null) return maps;
		
		LruDataService lds = ws.lds;
		TypeGetter tg = ws.tg;
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
			
			TwoDecMap<String,TypeData> monEngMap = new TwoDecMap<>();
			TwoDecMap<String,TypeData> monCitMap = new TwoDecMap<>();
			TwoDecMap<String,TypeData> monTraMap = new TwoDecMap<>();
			
			TwoDecMap<String,TypeData> monEngMapPP = new TwoDecMap<>();
			TwoDecMap<String,TypeData> monCitMapPP = new TwoDecMap<>();
			TwoDecMap<String,TypeData> monTraMapPP = new TwoDecMap<>();
			
			String tmp = null;
			TypeData td = null;
			for(AllSimData d:allData){
				if(!checkDataAuth(d,data,flag)) continue;
				tmp = TimeTools.getYearMonth(d.getInTime());
				
				td = monEngMap.get(tmp,d.getFuelType());
				if(td==null)
				{
					td = new TypeData();
					td.setType(d.getFuelType());
				}
				td.addEng(d.getFuelCsption());
				td.addLen(d.getGoTurn());
				monEngMap.put(tmp,d.getFuelType(),td);
				
				td = monCitMap.get(tmp,d.getPlace1());
				if(td==null)
				{
					td = new TypeData();
					td.setType(d.getPlace1());
				}
				td.addEng(d.getFuelCsption());
				td.addLen(d.getGoTurn());
				monCitMap.put(tmp,d.getPlace1(),td);
				
				td = monTraMap.get(tmp,d.getTraType());
				if(td==null)
				{
					td = new TypeData();
					td.setType(d.getTraType());
				}
				td.addEng(d.getFuelCsption());
				td.addLen(d.getGoTurn());
				monTraMap.put(tmp,d.getTraType(),td);
				
			}
			
			if(portData!=null){
				for(PortProData d:portData){
					if(!checkDataAuth(d,data,flag)) continue;
					tmp = TimeTools.getYearMonth(d.getInTime());
					if(tmp!=null){
						td = monEngMapPP.get(tmp,TypeGetter.FT_CHAI_YOU);//柴油
						if(td==null)
						{
							td = new TypeData();
							td.setType(TypeGetter.FT_CHAI_YOU);
						}
						td.addEng(d.getDiesel());
						td.addLen(d.getProTask());
						monEngMapPP.put(tmp,TypeGetter.FT_CHAI_YOU,td);
						
						td = monEngMapPP.get(tmp,TypeGetter.FT_QI_YOU);
						if(td==null)
						{
							td = new TypeData();
							td.setType(TypeGetter.FT_QI_YOU);
						}
						td.addEng(d.getGasoline());
						td.addLen(d.getProTask());
						monEngMapPP.put(tmp,TypeGetter.FT_QI_YOU,td);
						
						td = monEngMapPP.get(tmp,TypeGetter.FT_MEI_YOU);
						if(td==null)
						{
							td = new TypeData();
							td.setType(TypeGetter.FT_MEI_YOU);
						}
						td.addEng(d.getCoal());
						td.addLen(d.getProTask());
						monEngMapPP.put(tmp,TypeGetter.FT_MEI_YOU,td);
						
						td = monEngMapPP.get(tmp,TypeGetter.FT_DIAN_NENG);
						if(td==null)
						{
							td = new TypeData();
							td.setType(TypeGetter.FT_DIAN_NENG);
						}
						td.addEng(d.getPower());
						td.addLen(d.getProTask());
						monEngMapPP.put(tmp,TypeGetter.FT_DIAN_NENG,td);
						
						td = monEngMapPP.get(tmp,TypeGetter.FT_QI_TA);
						if(td==null)
						{
							td = new TypeData();
							td.setType(TypeGetter.FT_QI_TA);
						}
						td.addEng(d.getOther());
						td.addLen(d.getProTask());
						monEngMapPP.put(tmp,TypeGetter.FT_QI_TA,td);
						
						td = monCitMapPP.get(tmp,d.getPlace1());
						if(td==null)
						{
							td = new TypeData();
							td.setType(d.getPlace1());
						}
						td.addEng(d.getDiesel());
						td.addEng(d.getGasoline());
						td.addEng(d.getPower());
						td.addEng(d.getCoal());
						td.addEng(d.getOther());
						td.addLen(d.getProTask());
						monCitMapPP.put(tmp,d.getPlace1(),td);
						
						td = monTraMapPP.get(tmp,d.getTraType());
						if(td==null)
						{
							td = new TypeData();
							td.setType(d.getTraType());
						}
						td.addEng(d.getDiesel());
						td.addEng(d.getGasoline());
						td.addEng(d.getPower());
						td.addEng(d.getCoal());
						td.addEng(d.getOther());
						td.addLen(d.getProTask());
						monTraMapPP.put(tmp,d.getTraType(),td);
					}
				}
			}// end pp
						
			maps.add(monEngMap);
			maps.add(monCitMap);
			maps.add(monTraMap);
			
			maps.add(monEngMapPP);
			maps.add(monCitMapPP);
			maps.add(monTraMapPP);
				
		} catch (Exception e) {
			e.printStackTrace();
			maps.clear();
			logger.error(e.toString());
		}
		return maps;
		
	}

}
