package service.app.callables;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.app.dataservice.LruDataService;
import service.app.model.RoadGoodsData;
import service.app.tramodel.RequestData;
import service.app.tramodel.RoleType;
import service.app.tramodel.TypeData;
import service.app.util.TimeTools;
import service.app.util.TwoDecMap;
import service.app.util.TypeGetter;

public class RoadGoodsDataWork extends DataAuthBaseWork {

	
	private final static Logger logger = LoggerFactory.getLogger(RoadGoodsDataWork.class);
	
	
	public RoadGoodsDataWork(RequestData data) {
		super(data);
		// TODO Auto-generated constructor stub
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TwoDecMap<String, ? extends TypeData>> call() throws Exception {

		logger.debug("IN RoadGoodsDataWork data=["+data.getAllSimMessage()+"] targe=["+data.getTargeDay()+"]");
		List<TwoDecMap<String, ? extends TypeData>> maps = new ArrayList<>();
		if(data==null) return maps;
		
		LruDataService lds = ws.lds;
		TypeGetter tg = ws.tg;
		String tgd = data.getTargeDay();
		String[] nowBefMon  = TimeTools.getNowBeforeYM();///[yyyy-mm-dd,yyyy-mm]
		if(TimeTools.compareYMD(nowBefMon[0], tgd)<0) return maps;//时间超过当前
		String tmpYM = TimeTools.getYearMonth(tgd);
		
		String[] tranTypes = null;
		List<RoadGoodsData> allData = new ArrayList<>();
		
		int flag = 0;
		if(data.getRoleType().equals(RoleType.ROLE_TRAFFIC)){
			tranTypes = new String[1];
			tranTypes[0] = TypeGetter.TT_LAND_GOODS;
		}else if(data.getRoleType().equals(RoleType.ROLE_ENTERPEICE)){
			flag = 1;
			tranTypes = new String[1];
			tranTypes[0] = TypeGetter.TT_LAND_GOODS;
		}else if(data.getRoleType().equals(RoleType.ROLE_LAND)){
			tranTypes = new String[1];
			tranTypes[0] = TypeGetter.TT_LAND_GOODS;
		}else if(data.getRoleType().equals(RoleType.ROLE_WATER)){
			tranTypes = new String[0];
		}else if(data.getRoleType().equals(RoleType.ROLE_ADMIN)) {
			tranTypes = new String[1];
			tranTypes[0] = TypeGetter.TT_LAND_GOODS;
		}
		
		
		if(tranTypes.length==0) return maps;//排除港行
		
		try {
			if(TimeTools.getYearMonth(nowBefMon[0]).equals(tmpYM)){//本月
				for(String tt:tranTypes)
					allData.addAll((List<RoadGoodsData>)lds.getTYMCacheDataNow(tt, tgd));
			}
//			else if(nowBefMon[1].equals(tmpYM)){//上月
//				
//			}
			else{//上月一起				
				for(String tt:tranTypes)
					allData.addAll((List<RoadGoodsData>)lds.getTYMCacheDataBefore(tt, tmpYM));
			}
			
			TwoDecMap<String,TypeData> engMonthMap = new TwoDecMap<>();
			TwoDecMap<String,TypeData> engTonMap = new TwoDecMap<>();
			TwoDecMap<String,TypeData> entMap = new TwoDecMap<>();
			TwoDecMap<String,TypeData> carTonMap = new TwoDecMap<>();
			
			String tmp = null;

			TypeData td = null;
			
			for(RoadGoodsData d:allData){
				if(!checkDataAuth(d,data,flag)) continue;
				
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
				
				//EntSize
				tmp = tg.getRoadGoodsEntSizeType(d.getEntS());
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
				
				//ws
				tmp = tg.getRoadGoodsTonType(d.getTonnage());
				if(tmp!=null){
					td = engTonMap.get(d.getFuelType(),tmp);
					if(td==null)
					{
						td = new TypeData();
						td.setType(tmp);
					}
					td.addEng(d.getFuelCsption());
					td.addLen(d.getGoTurn());
					engTonMap.put(d.getFuelType(),tmp,td);
				}
				
				
				// car - tonnage
				if(tmp!=null){
					td = carTonMap.get(d.getCarType(),tmp);
					if(td==null)
					{
						td = new TypeData();
						td.setType(tmp);
					}
					td.addEng(d.getFuelCsption());
					td.addLen(d.getGoTurn());
					carTonMap.put(d.getCarType(),tmp,td);
				}

			}
			

			maps.add(engMonthMap);
			maps.add(engTonMap);
			maps.add(entMap);
			maps.add(carTonMap);

		} catch (Exception e) {
			e.printStackTrace();
			maps.clear();
			logger.error(e.toString());
		}
		
		
		
		
		
		return maps;
	}

}
