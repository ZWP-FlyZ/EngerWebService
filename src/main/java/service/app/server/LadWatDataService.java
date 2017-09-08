package service.app.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import service.app.dao.BusTranDao;
import service.app.dao.OceanGdsDao;
import service.app.dao.OceanPassDao;
import service.app.dao.PortProDao;
import service.app.dao.RiverTranDao;
import service.app.dao.RoadGdsDao;
import service.app.dao.RoadPassDao;
import service.app.dao.TaxiTranDao;
import service.app.model.AllSimData;
import service.app.model.BusTranData;
import service.app.model.OceanGoodsData;
import service.app.model.OceanPassData;
import service.app.model.PortProData;
import service.app.model.RiverTranData;
import service.app.model.RoadGoodsData;
import service.app.model.RoadPassData;
import service.app.model.TaxiTranData;
import service.app.tramodel.RequestData;
import service.app.tramodel.RoleType;
import service.app.tramodel.TypeData;
import service.app.tramodel.items.CitTypOtherItem;
import service.app.tramodel.items.EngTypOtherItem;
import service.app.tramodel.items.TraTypOtherItem;
import service.app.util.TimeTools;
import service.app.util.TwoDecMap;
import service.app.util.TypeGetter;

@Service
public class LadWatDataService {
	@Autowired
	RoadGdsDao roadGdsDao;
	@Autowired
	RoadPassDao roadPassDao;
	@Autowired
	BusTranDao busTranDao;
	@Autowired
	TaxiTranDao taxiTranDao;
	
	@Autowired
	OceanGdsDao oceanGdsDao;
	@Autowired
	OceanPassDao oceanPassDao;
	@Autowired
	RiverTranDao riverTranDao;
	@Autowired
	PortProDao portProDao;
	
	
	public Map<String,Object> getPerDisEngTypOther(RequestData rd){
		
		String[] times = 
				TimeTools.sqlitTimeRange(rd.getTimeRange());
		String[] places = new String[2];
		String enterprice=null;
		int k = 0x0;
		
		if(rd.getPlace1()==null)
			rd.setPlace1("%%");
		if(rd.getPlace2()==null)
			rd.setPlace2("%%");
		
		places[0] = rd.getPlace1();
		places[1] = rd.getPlace2();
		if(rd.getRoleType().equals(RoleType.ROLE_TRAFFIC)){
			enterprice = "%%";
			k=0x3;
		}else if(rd.getRoleType().equals(RoleType.ROLE_ENTERPEICE)){
			enterprice = rd.getRoleName();
			k = 0x3;
		}else if(rd.getRoleType().equals(RoleType.ROLE_LAND)){
			enterprice = "%%";
			k=0x1;
		}else if(rd.getRoleType().equals(RoleType.ROLE_WATER)){
			enterprice = "%%";
			k=0x2;
		}
		
		if(rd.getRoleType().equals(RoleType.ROLE_ADMIN)) {
			k = 0x3;
			enterprice = "%%";
		}
		
		List<AllSimData> allData = new ArrayList<>();
		if( (k&0x1)!=0 ){// land
			List<RoadGoodsData> list1 = 
					roadGdsDao.getRoadGdsAll(times[0], times[1], enterprice, places[0], places[1]);
			if(list1!=null)
				allData.addAll(list1);
			List<RoadPassData> list2 = 
					roadPassDao.getRoadPassAll(times[0], times[1], enterprice, places[0], places[1]);
			if(list2!=null)
				allData.addAll(list2);
			List<BusTranData> list3 = 
					busTranDao.getBusTranAll(times[0], times[1], enterprice, places[0], places[1]);
			if(list3!=null)
				allData.addAll(list3);
			List<TaxiTranData> list4 = 
					taxiTranDao.getTaxiTranAll(times[0], times[1], enterprice, places[0], places[1]);
			if(list4!=null)
				allData.addAll(list4);
			
		}
		
		if( (k&0x2)!=0 ){//water
			
			List<RiverTranData> list1 = 
					riverTranDao.getRiverTranAll(times[0], times[1], enterprice, places[0], places[1]);
			if(list1!=null)
				allData.addAll(list1);
			List<OceanGoodsData> list2 = 
					oceanGdsDao.getOceanGdsAll(times[0], times[1], enterprice, places[0], places[1]);
			if(list2!=null)
				allData.addAll(list2);
			List<OceanPassData> list3 = 
					oceanPassDao.getOceanPassAll(times[0], times[1], enterprice, places[0], places[1]);
			if(list3!=null)
				allData.addAll(list3);

		}
		
		
		List<TraTypOtherItem> traTypeOther = new ArrayList<>();
		TwoDecMap<String,TypeData> traEngTypMap = new TwoDecMap<>();
		TypeData td = null;
		TraTypOtherItem ttoi = null;
		for(AllSimData d:allData){
			
			td = traEngTypMap.get(d.getTraType(),d.getFuelType());
			if(td==null)
			{
				td = new TypeData();
				td.setType(d.getFuelType());
			}
			
			td.addEng(d.getFuelCsption());
			td.addLen(d.getTranDis());
			traEngTypMap.put(d.getTraType(),d.getFuelType(),td);
		}
		
			
		for(String et:traEngTypMap.getXset()){
			ttoi = new TraTypOtherItem();
			ttoi.setBaseTyp(et);
			ttoi.setTraTypEt(new ArrayList<TypeData>(traEngTypMap.getYMap(et).values()));
			traTypeOther.add(ttoi);
		}	
		
		
	
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("traTypeOther",traTypeOther);
		
		return map;
	}
	public Map<String,Object> getEngTyp3YearTypOther(RequestData rd){
		
		String[] times = 
				TimeTools.sqlitTimeRange(rd.getTimeRange());
		String[] places = new String[2];
		String enterprice=null;
		int k = 0x0;
		if(rd.getPlace1()==null)
			rd.setPlace1("%%");
		if(rd.getPlace2()==null)
			rd.setPlace2("%%");
		places[0] = rd.getPlace1();
		places[1] = rd.getPlace2();
		if(rd.getRoleType().equals(RoleType.ROLE_TRAFFIC)){
			enterprice = "%%";
			k=0x3;
		}else if(rd.getRoleType().equals(RoleType.ROLE_ENTERPEICE)){
			enterprice = rd.getRoleName();
			k = 0x3;
		}else if(rd.getRoleType().equals(RoleType.ROLE_LAND)){
			enterprice = "%%";
			k=0x1;
		}else if(rd.getRoleType().equals(RoleType.ROLE_WATER)){
			enterprice = "%%";
			k=0x2;
		}
		
		if(rd.getRoleType().equals(RoleType.ROLE_ADMIN)) {
			k = 0x3;
			enterprice = "%%";
		}
		
		List<AllSimData> allData = new ArrayList<>();
		if( (k&0x1)!=0 ){// land
			List<RoadGoodsData> list1 = 
					roadGdsDao.getRoadGdsAll(times[0], times[1], enterprice, places[0], places[1]);
			if(list1!=null)
				allData.addAll(list1);
			List<RoadPassData> list2 = 
					roadPassDao.getRoadPassAll(times[0], times[1], enterprice, places[0], places[1]);
			if(list2!=null)
				allData.addAll(list2);
			List<BusTranData> list3 = 
					busTranDao.getBusTranAll(times[0], times[1], enterprice, places[0], places[1]);
			if(list3!=null)
				allData.addAll(list3);
			List<TaxiTranData> list4 = 
					taxiTranDao.getTaxiTranAll(times[0], times[1], enterprice, places[0], places[1]);
			if(list4!=null)
				allData.addAll(list4);
			
		}
		
		if( (k&0x2)!=0 ){//water
			
			List<RiverTranData> list1 = 
					riverTranDao.getRiverTranAll(times[0], times[1], enterprice, places[0], places[1]);
			if(list1!=null)
				allData.addAll(list1);
			List<OceanGoodsData> list2 = 
					oceanGdsDao.getOceanGdsAll(times[0], times[1], enterprice, places[0], places[1]);
			if(list2!=null)
				allData.addAll(list2);
			List<OceanPassData> list3 = 
					oceanPassDao.getOceanPassAll(times[0], times[1], enterprice, places[0], places[1]);
			if(list3!=null)
				allData.addAll(list3);

		}
		
		
		List<EngTypOtherItem> engTypeOther = new ArrayList<>();
		TwoDecMap<String,TypeData> engMonthMap = new TwoDecMap<>();
		TypeData td = null;
		String tmp;
		EngTypOtherItem ttoi = null;
		for(AllSimData d:allData){
			
			tmp = TimeTools.getYearMonth(d.getInTime());
			td = engMonthMap.get(d.getFuelType(),tmp);
			if(td==null)
			{
				td = new TypeData();
				td.setType(tmp);
			}
			
			td.addEng(d.getFuelCsption());
			//td.addLen(d.getGoTurn());
			engMonthMap.put(d.getFuelType(),tmp,td);
		}
		
		if( (k&0x2)!=0 ){// 处理港口企业数据
			List<PortProData> lp = 
					portProDao.getProtProAll(times[0], times[1], enterprice, places[0], places[1]);
	
			for(PortProData d:lp){
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
		}
		
		
		
		
		
		
		
			
		for(String et:engMonthMap.getXset()){
			ttoi = new EngTypOtherItem();
			ttoi.setBaseTyp(et);
			ttoi.setEngTypMo(new ArrayList<TypeData>(engMonthMap.getYMap(et).values()));
			engTypeOther.add(ttoi);
		}	
		
		
	
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("engTypeOther",engTypeOther);
		
		return map;
	}
	public Map<String,Object> getTraTypPerYearTypOther(RequestData rd){
		
		String[] times = 
				TimeTools.sqlitTimeRange(rd.getTimeRange());
		String[] places = new String[2];
		String enterprice=null;
		int k = 0x0;
		if(rd.getPlace1()==null)
			rd.setPlace1("%%");
		if(rd.getPlace2()==null)
			rd.setPlace2("%%");
		
		places[0] = rd.getPlace1();
		places[1] = rd.getPlace2();
		if(rd.getRoleType().equals(RoleType.ROLE_TRAFFIC)){
			enterprice = "%%";
			k=0x3;
		}else if(rd.getRoleType().equals(RoleType.ROLE_ENTERPEICE)){
			enterprice = rd.getRoleName();
			k = 0x3;
		}else if(rd.getRoleType().equals(RoleType.ROLE_LAND)){
			enterprice = "%%";
			k=0x1;
		}else if(rd.getRoleType().equals(RoleType.ROLE_WATER)){
			enterprice = "%%";
			k=0x2;
		}
		
		if(rd.getRoleType().equals(RoleType.ROLE_ADMIN)) {
			k = 0x3;
			enterprice = "%%";
		}
		
		List<AllSimData> allData = new ArrayList<>();
		if( (k&0x1)!=0 ){// land
			List<RoadGoodsData> list1 = 
					roadGdsDao.getRoadGdsAll(times[0], times[1], enterprice, places[0], places[1]);
			if(list1!=null)
				allData.addAll(list1);
			List<RoadPassData> list2 = 
					roadPassDao.getRoadPassAll(times[0], times[1], enterprice, places[0], places[1]);
			if(list2!=null)
				allData.addAll(list2);
			List<BusTranData> list3 = 
					busTranDao.getBusTranAll(times[0], times[1], enterprice, places[0], places[1]);
			if(list3!=null)
				allData.addAll(list3);
			List<TaxiTranData> list4 = 
					taxiTranDao.getTaxiTranAll(times[0], times[1], enterprice, places[0], places[1]);
			if(list4!=null)
				allData.addAll(list4);
			
		}
		
		if( (k&0x2)!=0 ){//water
			
			List<RiverTranData> list1 = 
					riverTranDao.getRiverTranAll(times[0], times[1], enterprice, places[0], places[1]);
			if(list1!=null)
				allData.addAll(list1);
			List<OceanGoodsData> list2 = 
					oceanGdsDao.getOceanGdsAll(times[0], times[1], enterprice, places[0], places[1]);
			if(list2!=null)
				allData.addAll(list2);
			List<OceanPassData> list3 = 
					oceanPassDao.getOceanPassAll(times[0], times[1], enterprice, places[0], places[1]);
			if(list3!=null)
				allData.addAll(list3);

		}
		
		
		List<TraTypOtherItem> traTypeOther = new ArrayList<>();
		TwoDecMap<String,TypeData> traMonthMap = new TwoDecMap<>();
		TypeData td = null;
		String tmp;
		TraTypOtherItem ttoi = null;
		for(AllSimData d:allData){
			
			tmp = TimeTools.getYearMonth(d.getInTime());
			td = traMonthMap.get(d.getTraType(),tmp);
			if(td==null)
			{
				td = new TypeData();
				td.setType(tmp);
			}
			
			td.addEng(d.getFuelCsption());
			td.addLen(d.getGoTurn());
			traMonthMap.put(d.getTraType(),tmp,td);
		}
		
			
		for(String et:traMonthMap.getXset()){
			ttoi = new TraTypOtherItem();
			ttoi.setBaseTyp(et);
			ttoi.setTraTypMo(new ArrayList<TypeData>(traMonthMap.getYMap(et).values()));
			traTypeOther.add(ttoi);
		}	
		
		
	
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("traTypeOther",traTypeOther);
		
		return map;
	}
	public Map<String,Object> getCitTranTypOther(RequestData rd){
		
		String[] times = 
				TimeTools.sqlitTimeRange(rd.getTimeRange());
		String[] places = new String[2];
		String enterprice=null;
		int k = 0x0;
		if(rd.getPlace1()==null)
			rd.setPlace1("%%");
		if(rd.getPlace2()==null)
			rd.setPlace2("%%");
		
		places[0] = rd.getPlace1();
		places[1] = rd.getPlace2();
		if(rd.getRoleType().equals(RoleType.ROLE_TRAFFIC)){
			enterprice = "%%";
			k=0x3;
		}else if(rd.getRoleType().equals(RoleType.ROLE_ENTERPEICE)){
			enterprice = rd.getRoleName();
			k = 0x3;
		}else if(rd.getRoleType().equals(RoleType.ROLE_LAND)){
			enterprice = "%%";
			k=0x1;
		}else if(rd.getRoleType().equals(RoleType.ROLE_WATER)){
			enterprice = "%%";
			k=0x2;
		}
		
		if(rd.getRoleType().equals(RoleType.ROLE_ADMIN)) {
			k = 0x3;
			enterprice = "%%";
		}
		
		List<AllSimData> allData = new ArrayList<>();
		if( (k&0x1)!=0 ){// land
			if(rd.getTranType().equals(TypeGetter.TT_LAND_GOODS)){
				List<RoadGoodsData> list1 = 
						roadGdsDao.getRoadGdsAll(times[0], times[1], enterprice, places[0], places[1]);
				if(list1!=null)
					allData.addAll(list1);
			}else if(rd.getTranType().equals(TypeGetter.TT_LAND_PASS)){
				List<RoadPassData> list2 = 
						roadPassDao.getRoadPassAll(times[0], times[1], enterprice, places[0], places[1]);
				if(list2!=null)
					allData.addAll(list2);
			}else if(rd.getTranType().equals(TypeGetter.TT_LAND_BUS)){
				List<BusTranData> list3 = 
						busTranDao.getBusTranAll(times[0], times[1], enterprice, places[0], places[1]);
				if(list3!=null)
					allData.addAll(list3);
			}else if(rd.getTranType().equals(TypeGetter.TT_LAND_TAXI)){
				List<TaxiTranData> list4 = 
						taxiTranDao.getTaxiTranAll(times[0], times[1], enterprice, places[0], places[1]);
				if(list4!=null)
					allData.addAll(list4);
			}		
		}
		
		if( (k&0x2)!=0 ){//water
			if(rd.getTranType().equals(TypeGetter.TT_WATER_RIVER)){
				List<RiverTranData> list1 = 
						riverTranDao.getRiverTranAll(times[0], times[1], enterprice, places[0], places[1]);
				if(list1!=null)
					allData.addAll(list1);
			}else if(rd.getTranType().equals(TypeGetter.TT_WATER_GOODS)){
				List<OceanGoodsData> list2 = 
						oceanGdsDao.getOceanGdsAll(times[0], times[1], enterprice, places[0], places[1]);
				if(list2!=null)
					allData.addAll(list2);
			}else if(rd.getTranType().equals(TypeGetter.TT_WATER_PASS)){
				List<OceanPassData> list3 = 
						oceanPassDao.getOceanPassAll(times[0], times[1], enterprice, places[0], places[1]);
				if(list3!=null)
					allData.addAll(list3);
			}
		}
		
		
		List<CitTypOtherItem> citTypeOther = new ArrayList<>();
		TwoDecMap<String,TypeData> citMap = new TwoDecMap<>();
		TypeData td = null;
		CitTypOtherItem ctoi = null;
		for(AllSimData d:allData){
			
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
		
			
		for(String et:citMap.getXset()){
			ctoi = new CitTypOtherItem();
			ctoi.setBaseTyp(et);
			ctoi.setBaseTypDatOfAllEng(citMap.get(et, et).getTypDatOfAllEng());
			citTypeOther.add(ctoi);
		}	
		
		
	
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("citTypeOther",citTypeOther);
		
		return map;
	}
	public Map<String,Object> getTranCitTypOther(RequestData rd){
		
		String[] times = 
				TimeTools.sqlitTimeRange(rd.getTimeRange());
		String[] places = new String[2];
		String enterprice=null;
		int k = 0x0;
		if(rd.getPlace1()==null)
			rd.setPlace1(rd.getCityType());
		if(rd.getPlace2()==null)
			rd.setPlace2("%%");
		
		places[0] = rd.getPlace1();
		places[1] = rd.getPlace2();
		if(rd.getRoleType().equals(RoleType.ROLE_TRAFFIC)){
			enterprice = "%%";
			k=0x3;
		}else if(rd.getRoleType().equals(RoleType.ROLE_ENTERPEICE)){
			enterprice = rd.getRoleName();
			k = 0x3;
		}else if(rd.getRoleType().equals(RoleType.ROLE_LAND)){
			enterprice = "%%";
			k=0x1;
		}else if(rd.getRoleType().equals(RoleType.ROLE_WATER)){
			enterprice = "%%";
			k=0x2;
		}
		
		if(rd.getRoleType().equals(RoleType.ROLE_ADMIN)) {
			k = 0x3;
			enterprice = "%%";
		}
		
		List<AllSimData> allData = new ArrayList<>();
		if( (k&0x1)!=0 ){// land
			List<RoadGoodsData> list1 = 
					roadGdsDao.getRoadGdsAll(times[0], times[1], enterprice, places[0], places[1]);
			if(list1!=null)
				allData.addAll(list1);
			List<RoadPassData> list2 = 
					roadPassDao.getRoadPassAll(times[0], times[1], enterprice, places[0], places[1]);
			if(list2!=null)
				allData.addAll(list2);
			List<BusTranData> list3 = 
					busTranDao.getBusTranAll(times[0], times[1], enterprice, places[0], places[1]);
			if(list3!=null)
				allData.addAll(list3);
			List<TaxiTranData> list4 = 
					taxiTranDao.getTaxiTranAll(times[0], times[1], enterprice, places[0], places[1]);
			if(list4!=null)
				allData.addAll(list4);
			
		}
		
		if( (k&0x2)!=0 ){//water
			
			List<RiverTranData> list1 = 
					riverTranDao.getRiverTranAll(times[0], times[1], enterprice, places[0], places[1]);
			if(list1!=null)
				allData.addAll(list1);
			List<OceanGoodsData> list2 = 
					oceanGdsDao.getOceanGdsAll(times[0], times[1], enterprice, places[0], places[1]);
			if(list2!=null)
				allData.addAll(list2);
			List<OceanPassData> list3 = 
					oceanPassDao.getOceanPassAll(times[0], times[1], enterprice, places[0], places[1]);
			if(list3!=null)
				allData.addAll(list3);

		}
		
		
		List<TraTypOtherItem> traTypeOther = new ArrayList<>();
		TwoDecMap<String,TypeData> traMap = new TwoDecMap<>();
		TypeData td = null;
		TraTypOtherItem ttoi = null;
		for(AllSimData d:allData){
			
			td = traMap.get(d.getTraType(),d.getTraType());
			if(td==null)
			{
				td = new TypeData();
			}
			
			td.addEng(d.getFuelCsption());
			//td.addLen(d.getGoTurn());
			traMap.put(d.getTraType(),d.getTraType(),td);
		}
		
			
		for(String et:traMap.getXset()){
			ttoi = new TraTypOtherItem();
			ttoi.setBaseTyp(et);
			ttoi.setBaseTypDatOfAllEng( traMap.get(et,et).getTypDatOfAllEng());
			traTypeOther.add(ttoi);
		}	
		
		
	
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("traTypeOther",traTypeOther);
		
		return map;
	}
	
	
	

	
	
}
