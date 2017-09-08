package service.app.callables;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.app.dataservice.LruDataService;
import service.app.model.PortProData;
import service.app.tramodel.RequestData;
import service.app.tramodel.RoleType;
import service.app.tramodel.TypeData;
import service.app.util.TimeTools;
import service.app.util.TwoDecMap;
import service.app.util.TypeGetter;

public class PortProduceDataWork extends DataAuthBaseWork {

	
	private final static Logger logger = LoggerFactory.getLogger(PortProduceDataWork.class);
	
	public PortProduceDataWork(RequestData data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TwoDecMap<String, ? extends TypeData>> call() throws Exception {
		
		logger.debug("IN PortProduceDataWork data=["+data.getAllSimMessage()+"] targe=["+data.getTargeDay()+"]");
		List<TwoDecMap<String, ? extends TypeData>> maps = new ArrayList<>();
		if(data==null) return maps;
		
		LruDataService lds = ws.lds;
		TypeGetter tg = ws.tg;
		String tgd = data.getTargeDay();
		String[] nowBefMon  = TimeTools.getNowBeforeYM();///[yyyy-mm-dd,yyyy-mm]
		if(TimeTools.compareYMD(nowBefMon[0], tgd)<0) return maps;//时间超过当前
		String tmpYM = TimeTools.getYearMonth(tgd);
		
		String[] tranTypes = null;
		List<PortProData> allData = new ArrayList<>();
		
		int flag = 0;
		if(data.getRoleType().equals(RoleType.ROLE_TRAFFIC)){
			tranTypes = new String[1];
			tranTypes[0] = TypeGetter.TT_WATER_PORT;
		}else if(data.getRoleType().equals(RoleType.ROLE_ENTERPEICE)){
			flag = 1;
			tranTypes = new String[1];
			tranTypes[0] = TypeGetter.TT_WATER_PORT;
		}else if(data.getRoleType().equals(RoleType.ROLE_LAND)){
			tranTypes = new String[0];
		}else if(data.getRoleType().equals(RoleType.ROLE_WATER)){
			tranTypes = new String[1];
			tranTypes[0] = TypeGetter.TT_WATER_PORT;
		}else if(data.getRoleType().equals(RoleType.ROLE_ADMIN)) {
			tranTypes = new String[1];
			tranTypes[0] = TypeGetter.TT_WATER_PORT;
		}
		
		if(tranTypes.length==0) return maps;//排除运管
		
		try {
			if(TimeTools.getYearMonth(nowBefMon[0]).equals(tmpYM)){//本月
				for(String tt:tranTypes)
					allData.addAll((List<PortProData>)lds.getTYMCacheDataNow(tt, tgd));
			}
//			else if(nowBefMon[1].equals(tmpYM)){//上月
//				
//			}
			else{//上月一起				
				for(String tt:tranTypes)
					allData.addAll((List<PortProData>)lds.getTYMCacheDataBefore(tt, tmpYM));
			}
			
			TwoDecMap<String,TypeData> engMonthMap = new TwoDecMap<>();
			TwoDecMap<String,TypeData> entMap = new TwoDecMap<>();
			
			String tmp = null;
			TypeData td = null;
			
			for(PortProData d:allData){
				if(!checkDataAuth(d,data,flag)) continue;
				//month
				tmp = TimeTools.getYearMonth(d.getInTime());
				if(tmp!=null){
					td = engMonthMap.get(TypeGetter.FT_CHAI_YOU,tmp);//柴油
					if(td==null)
					{
						td = new TypeData();
						td.setType(tmp);
					}
					td.addEng(d.getDiesel());
					td.addLen(d.getProTask());
					engMonthMap.put(TypeGetter.FT_CHAI_YOU,tmp,td);
					
					td = engMonthMap.get(TypeGetter.FT_QI_YOU,tmp);
					if(td==null)
					{
						td = new TypeData();
						td.setType(tmp);
					}
					td.addEng(d.getGasoline());
					td.addLen(d.getProTask());
					engMonthMap.put(TypeGetter.FT_QI_YOU,tmp,td);
					
					td = engMonthMap.get(TypeGetter.FT_MEI_YOU,tmp);
					if(td==null)
					{
						td = new TypeData();
						td.setType(tmp);
					}
					td.addEng(d.getCoal());
					td.addLen(d.getProTask());
					engMonthMap.put(TypeGetter.FT_MEI_YOU,tmp,td);
					
					td = engMonthMap.get(TypeGetter.FT_DIAN_NENG,tmp);
					if(td==null)
					{
						td = new TypeData();
						td.setType(tmp);
					}
					td.addEng(d.getPower());
					td.addLen(d.getProTask());
					engMonthMap.put(TypeGetter.FT_DIAN_NENG,tmp,td);
					
					td = engMonthMap.get(TypeGetter.FT_QI_TA,tmp);
					if(td==null)
					{
						td = new TypeData();
						td.setType(tmp);
					}
					td.addEng(d.getOther());
					td.addLen(d.getProTask());
					engMonthMap.put(TypeGetter.FT_QI_TA,tmp,td);
					
				}
				
				tmp = tg.getPortProEntSType(d.getThroughput());//企业 规模 每月数据
				if(tmp!=null){
					td = entMap.get(tmp, tmp);
					if(td==null)
					{
						td = new TypeData();
						td.setType(tmp);
					}
					td.addEng(d.getDiesel());
					td.addEng(d.getGasoline());
					td.addEng(d.getPower());
					td.addEng(d.getCoal());
					td.addEng(d.getOther());
					td.addLen(d.getProTask());
					entMap.put(tmp, tmp, td);
				}			
			}
			
			maps.add(engMonthMap);
			maps.add(entMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			maps.clear();
			logger.error(e.toString());
		}
		
		return maps;
	}
	


}
