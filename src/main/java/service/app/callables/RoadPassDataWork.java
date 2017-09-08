package service.app.callables;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.app.dataservice.LruDataService;
import service.app.model.RoadPassData;
import service.app.tramodel.RequestData;
import service.app.tramodel.RoleType;
import service.app.tramodel.TypeData;
import service.app.util.TimeTools;
import service.app.util.TwoDecMap;
import service.app.util.TypeGetter;

public class RoadPassDataWork extends DataAuthBaseWork {

	
	private final static Logger logger = LoggerFactory.getLogger(RoadPassDataWork.class);
	
	public RoadPassDataWork(RequestData data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TwoDecMap<String, ? extends TypeData>> call() throws Exception {
		
		logger.debug("IN RoadPassDataWork data=["+data.getAllSimMessage()+"] targe=["+data.getTargeDay()+"]");
		List<TwoDecMap<String, ? extends TypeData>> maps = new ArrayList<>();
		if(data==null) return maps;
		
		LruDataService lds = ws.lds;
		TypeGetter tg = ws.tg;
		String tgd = data.getTargeDay();
		String[] nowBefMon  = TimeTools.getNowBeforeYM();///[yyyy-mm-dd,yyyy-mm]
		if(TimeTools.compareYMD(nowBefMon[0], tgd)<0) return maps;//时间超过当前
		String tmpYM = TimeTools.getYearMonth(tgd);
		
		String[] tranTypes = null;
		List<RoadPassData> allData = new ArrayList<>();

		int flag = 0;
		if(data.getRoleType().equals(RoleType.ROLE_TRAFFIC)){
			tranTypes = new String[1];
			tranTypes[0] = TypeGetter.TT_LAND_PASS;
		}else if(data.getRoleType().equals(RoleType.ROLE_ENTERPEICE)){
			flag = 1;
			tranTypes = new String[1];
			tranTypes[0] = TypeGetter.TT_LAND_PASS;
		}else if(data.getRoleType().equals(RoleType.ROLE_LAND)){
			tranTypes = new String[1];
			tranTypes[0] = TypeGetter.TT_LAND_PASS;
		}else if(data.getRoleType().equals(RoleType.ROLE_WATER)){
			tranTypes = new String[0];
		}else if(data.getRoleType().equals(RoleType.ROLE_ADMIN)) {
			tranTypes = new String[1];
			tranTypes[0] = TypeGetter.TT_LAND_PASS;
		}
		
		if(tranTypes.length==0) return maps;//排除港行
		
		try {
			if(TimeTools.getYearMonth(nowBefMon[0]).equals(tmpYM)){//本月
				for(String tt:tranTypes)
					allData.addAll((List<RoadPassData>)lds.getTYMCacheDataNow(tt, tgd));
			}
//			else if(nowBefMon[1].equals(tmpYM)){//上月
//				
//			}
			else{//上月一起				
				for(String tt:tranTypes)
					allData.addAll((List<RoadPassData>)lds.getTYMCacheDataBefore(tt, tmpYM));
			}
			
			TwoDecMap<String,TypeData> engMonthMap = new TwoDecMap<>();
			TwoDecMap<String,TypeData> engSitMap = new TwoDecMap<>();
			TwoDecMap<String,TypeData> entMap = new TwoDecMap<>();
			TwoDecMap<String,TypeData> disMap = new TwoDecMap<>();
			TwoDecMap<String,TypeData>  carTypeMap= new TwoDecMap<>();
			
			String tmp = null;
			TypeData td = null;
			
			for(RoadPassData d:allData){
				if(!checkDataAuth(d,data,flag)) continue;
				
				//month
				tmp = TimeTools.getYearMonth(d.getInTime());
				if(tmp!=null){
					td = engMonthMap.get(d.getFuelType(),tmp);
					if(td==null)
					{
						td = new TypeData();
						td.setType(tmp);
					}
					td.addEng(d.getFuelCsption());
					td.addLen(d.getGoTurn());
					engMonthMap.put(d.getFuelType(),tmp,td);
				}

				//sitCOt
				tmp = tg.getRoadPassSitCotType(d.getSitCot());
				if(tmp!=null){
					td = engSitMap.get(d.getFuelType(),tmp);
					if(td==null)
					{
						td = new TypeData();
						td.setType(tmp);
					}
					td.addEng(d.getFuelCsption());
					td.addLen(d.getGoTurn());
					engSitMap.put(d.getFuelType(),tmp,td);
				}

				//EntSize
				tmp = tg.getRoadPassEntSizeType(d.getEntS());
				if(tmp!=null){
					td = entMap.get(tmp,tmp);
					if(td==null)
					{
						td = new TypeData();
						td.setType(tmp);
					}
					td.addEng(d.getFuelCsption());
					td.addLen(d.getGoTurn());
					entMap.put(tmp,tmp,td);
				}

				//TranDis
				tmp = tg.getRoadPassDisType(d.getTranDis());
				if(tmp!=null){
					td = disMap.get(tmp,tmp);
					if(td==null)
					{
						td = new TypeData();
						td.setType(tmp);
					}
					td.addEng(d.getFuelCsption());
					td.addLen(d.getGoTurn());
					disMap.put(tmp,tmp,td);
				}

				tmp = d.getCarType();
				if(tmp!=null){
					td = carTypeMap.get(tmp,tmp);
					if(td==null)
					{
						td = new TypeData();
						td.setType(tmp);
					}
					td.addEng(d.getFuelCsption());
					td.addLen(d.getGoTurn());
					carTypeMap.put(tmp,tmp,td);
				}

			}// end for
			
			maps.add(engMonthMap);
			maps.add(engSitMap);
			maps.add(entMap);
			maps.add(disMap);
			maps.add(carTypeMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			maps.clear();
			logger.error(e.toString());
		}
		
		return maps;
	}

}
