package service.app.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import service.app.dao.RelTimeCarDao;
import service.app.dao.RelTimeShipDao;
import service.app.model.RelTimeData;
import service.app.model.RelTimeSelctParam;
import service.app.tramodel.RequestData;
import service.app.tramodel.RtTypeData;
import service.app.tramodel.items.EngTypOtherItem;
import service.app.util.TimeTools;
import service.app.util.TwoDecMap;
import service.app.util.TypeGetter;

@Service
public class RelTimeDataService {

	@Autowired
	RelTimeCarDao carDao;
	
	@Autowired
	RelTimeShipDao shipDao;
	
	
	
	private final static String TNAME_ROAD_PASS = "roadpassrel";
	private final static String TNAME_ROAD_GOODS = "roadgoodsrel";
	private final static String TNAME_BUS = "bustranrel";
	private final static String TNAME_TAXI = "taxitranrel";
	
	private final static String TNAME_RIVER = "rivertranrel";
	
	public Map<String,Object> getBusRelTimeData(RequestData rd){
		RelTimeSelctParam param = new RelTimeSelctParam();
		
		String [] ss = TimeTools.sqlitTimeRange(rd.getTimeRange());
		
		param.setStartTime(ss[0]);
		param.setEndTime(ss[1]);
		
		param.setCarId(checkNull(rd.getCarId()));
		param.setShipId(checkNull(rd.getShipId()));
		
		param.setCityType(checkNull(rd.getCityType()));
		param.setCompanyId(checkNull(rd.getCompanyId()));
		List<RelTimeData> list = null;
		switch(rd.getTranType()){
			case  TypeGetter.TT_LAND_PASS:
				list = carDao.getRelTimeData(TNAME_ROAD_PASS, param);
				break;
			case  TypeGetter.TT_LAND_GOODS:
				list = carDao.getRelTimeData(TNAME_ROAD_GOODS, param);
				break;
			case  TypeGetter.TT_LAND_BUS:
				list = carDao.getRelTimeData(TNAME_BUS, param);
				break;
			case  TypeGetter.TT_LAND_TAXI:
				list = carDao.getRelTimeData(TNAME_TAXI, param);
				break;
			case  TypeGetter.TT_WATER_RIVER:
				list = shipDao.getRelTimeData(TNAME_RIVER, param);
				break;
			default:
				list = new ArrayList<>();
				break;
		}
		
		List<EngTypOtherItem> engTypeOther = new ArrayList<>();
		
	
		TwoDecMap<String,RtTypeData> engTenMu = new TwoDecMap<>();
		
		String tmp = null;
		RtTypeData rtd = null;
		
		for(RelTimeData d:list){
			tmp = TimeTools.getTenMinute(d.getInTime());
			if(tmp!=null){
				rtd = engTenMu.get(d.getFuelType(),tmp);
				if(rtd==null)
				{
					rtd = new RtTypeData();
					rtd.setType(tmp);
				}
				rtd.addEng(d.getFuelCsption());
				rtd.addLen(d.getTranDis());
				rtd.addCoEng(d.getFuelCo());
				engTenMu.put(d.getFuelType(),tmp,rtd);
			}
		}
		
		EngTypOtherItem etoi = null;
		for(String et:engTenMu.getXset()){
			etoi = new EngTypOtherItem();
			etoi.setBaseTyp(et);
			etoi.setEngTypTMu(new ArrayList<RtTypeData>(engTenMu.getYMap(et).values()));
			engTypeOther.add(etoi);
		}
		
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("engTypeOther",engTypeOther);
		
		return map;
	}
	
	private String checkNull(String s){
		return s==null||"".equals(s)?"%":s;
	}
	
}
