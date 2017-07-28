package service.app.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import service.app.dao.BusTranDao;
import service.app.dao.RoadGdsDao;
import service.app.dao.RoadPassDao;
import service.app.dao.TaxiTranDao;
import service.app.model.BusTranData;
import service.app.model.RoadGoodsData;
import service.app.model.RoadPassData;
import service.app.model.TaxiTranData;
import service.app.tramodel.RequestData;
import service.app.tramodel.RoleType;
import service.app.tramodel.TypeData;
import service.app.tramodel.items.BaseTypOtherItem;
import service.app.tramodel.items.CarTypOtherItem;
import service.app.tramodel.items.EngTypOtherItem;
import service.app.tramodel.items.EntTypOtherItem;
import service.app.util.TimeTools;
import service.app.util.TwoDecMap;
import service.app.util.TypeGetter;

@Service
public class LandDataService {

	
	@Autowired
	RoadGdsDao roadGdsDao;
	@Autowired
	RoadPassDao roadPassDao;
	@Autowired
	BusTranDao busTranDao;
	@Autowired
	TaxiTranDao taxiTranDao;
	
	
	@Autowired
	TypeGetter tg;
	

public  Map<String,Object> getRoadPassTypOther(RequestData rd){
		

		String[] times = 
				TimeTools.sqlitTimeRange(rd.getTimeRange());
		String[] places = new String[2];
		String enterprice=null;
		int k = 0x0;
		
		List<RoadPassData> allData =  new ArrayList<>();
		
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
		
		if( (k&0x1)!=0 ){
			List<RoadPassData> l = 
					roadPassDao.getRoadPassAll(times[0], times[1], enterprice, places[0], places[1]);
			if(l!=null)
				allData.addAll(l);
		}
		if( (k&0x2)!=0 ){

		}
		
		List<EngTypOtherItem> engTypeOther = new ArrayList<>();
		List<EntTypOtherItem> entTypeOther = new ArrayList<>();
		List<BaseTypOtherItem> disTypOther = new ArrayList<>();
		List<CarTypOtherItem> carTypOther = new ArrayList<>();
		
		TwoDecMap<String,TypeData> engMonthMap = new TwoDecMap<>();
		TwoDecMap<String,TypeData> engSitMap = new TwoDecMap<>();
		TwoDecMap<String,TypeData> entMap = new TwoDecMap<>();
		TwoDecMap<String,TypeData> disMap = new TwoDecMap<>();
		TwoDecMap<String,TypeData>  carTypeMap= new TwoDecMap<>();
		
		String tmp = null;
		TypeData td = null;

		for(RoadPassData d:allData){
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

		}
		
		
		EngTypOtherItem etoi = null;
		for(String et:engMonthMap.getXset()){
			etoi = new EngTypOtherItem();
			etoi.setBaseTyp(et);
			etoi.setEngTypMo(new ArrayList<TypeData>(engMonthMap.getYMap(et).values()));
			etoi.setEngTypSs(new ArrayList<TypeData>(engSitMap.getYMap(et).values()));
			engTypeOther.add(etoi);
		}
		
		EntTypOtherItem ettoi = null;
		for(String et:entMap.getXset()){
			ettoi = new EntTypOtherItem();
			ettoi.setBaseTyp(et);
			td = entMap.get(et, et);
			ettoi.setBaseTypDatOfAllEng(td.getTypDatOfAllEng());
			ettoi.setBaseTypDatOfAllLen(td.getTypDatOfAllLen());
			entTypeOther.add(ettoi);
		}
		
		BaseTypOtherItem dtoi = null;
		for(String et:disMap.getXset()){
			dtoi = new BaseTypOtherItem();
			dtoi.setBaseTyp(et);
			td = disMap.get(et, et);
			dtoi.setBaseTypDatOfAllEng(td.getTypDatOfAllEng());
			dtoi.setBaseTypDatOfAllLen(td.getTypDatOfAllLen());
			disTypOther.add(dtoi);
		}
		
		CarTypOtherItem cttoi = null;
		for(String et:carTypeMap.getXset()){
			cttoi = new CarTypOtherItem();
			cttoi.setBaseTyp(et);
			td = carTypeMap.get(et, et);
			cttoi.setBaseTypDatOfAllEng(td.getTypDatOfAllEng());
			cttoi.setBaseTypDatOfAllLen(td.getTypDatOfAllLen());
			carTypOther.add(cttoi);
		}
				
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("engTypeOther",engTypeOther);
		map.put("entTypeOther",entTypeOther);
		map.put("disTypOther",disTypOther);
		map.put("carTypOther",carTypOther);
		return map;
	}

public  Map<String,Object> getRoadGoodsTypOther(RequestData rd){
	

	String[] times = 
			TimeTools.sqlitTimeRange(rd.getTimeRange());
	String[] places = new String[2];
	String enterprice=null;
	int k = 0x0;
	
	List<RoadGoodsData> allData =  new ArrayList<>();
	
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
	
	if( (k&0x1)!=0 ){
		List<RoadGoodsData> l = 
				roadGdsDao.getRoadGdsAll(times[0], times[1], enterprice, places[0], places[1]);
		if(l!=null)
			allData.addAll(l);
	}
	if( (k&0x2)!=0 ){

	}
	
	List<EngTypOtherItem> engTypeOther = new ArrayList<>();
	List<EntTypOtherItem> entTypeOther = new ArrayList<>();
	List<CarTypOtherItem> carTypOther = new ArrayList<>();
	
	TwoDecMap<String,TypeData> engMonthMap = new TwoDecMap<>();
	TwoDecMap<String,TypeData> engTonMap = new TwoDecMap<>();
	TwoDecMap<String,TypeData> entMap = new TwoDecMap<>();
	TwoDecMap<String,TypeData> carTonMap = new TwoDecMap<>();
	
	String tmp = null;

	TypeData td = null;

	for(RoadGoodsData d:allData){
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
	
	
	EngTypOtherItem etoi = null;
	for(String et:engMonthMap.getXset()){
		etoi = new EngTypOtherItem();
		etoi.setBaseTyp(et);
		etoi.setEngTypMo(new ArrayList<TypeData>(engMonthMap.getYMap(et).values()));
		etoi.setEngTypWs(new ArrayList<TypeData>(engTonMap.getYMap(et).values()));
		engTypeOther.add(etoi);
	}
	
	EntTypOtherItem ettoi = null;
	for(String et:entMap.getXset()){
		ettoi = new EntTypOtherItem();
		ettoi.setBaseTyp(et);
		td = entMap.get(et, et);
		ettoi.setBaseTypDatOfAllEng(td.getTypDatOfAllEng());
		ettoi.setBaseTypDatOfAllLen(td.getTypDatOfAllLen());
		entTypeOther.add(ettoi);
	}
	
	CarTypOtherItem ctoi = null;
	for(String et:carTonMap.getXset()){
		ctoi = new CarTypOtherItem();
		ctoi.setBaseTyp(et);
		ctoi.setCarTypWs(new ArrayList<TypeData>(carTonMap.getYMap(et).values()));
		carTypOther.add(ctoi);
	}
	
	Map<String ,Object> map = new HashMap<String ,Object>();
	map.put("engTypeOther",engTypeOther);
	map.put("entTypeOther",entTypeOther);
	map.put("carTypOther",carTypOther);
	return map;
}

public  Map<String,Object> getBusTranTypOther(RequestData rd){
	

	String[] times = 
			TimeTools.sqlitTimeRange(rd.getTimeRange());
	String[] places = new String[2];
	String enterprice=null;
	int k = 0x0;
	
	List<BusTranData> allData =  new ArrayList<>();
	
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
	
	if( (k&0x1)!=0 ){
		List<BusTranData> l = 
				busTranDao.getBusTranAll(times[0], times[1], enterprice, places[0], places[1]);
		if(l!=null)
			allData.addAll(l);
	}
	if( (k&0x2)!=0 ){

	}
	
	List<EngTypOtherItem> engTypeOther = new ArrayList<>();
	TwoDecMap<String,TypeData> monthMap = new TwoDecMap<>();
	TwoDecMap<String,TypeData> clsMap = new TwoDecMap<>();

	String tmp = null;
	TypeData td = null;
	EngTypOtherItem etoi = null;
	for(BusTranData d:allData){
		//month
		tmp = TimeTools.getYearMonth(d.getInTime());
		if(tmp!=null){
			td = monthMap.get(d.getFuelType(),tmp);
			if(td==null)
			{
				td = new TypeData();
				td.setType(tmp);
			}
			td.addEng(d.getFuelCsption());
			td.addLen(d.getGoTurn());
			monthMap.put(d.getFuelType(),tmp,td);
		}

		
		//cl
		tmp = tg.getBusTranCarLenType(d.getCarLength());
		if(tmp!=null){
			td = clsMap.get(d.getFuelType(),tmp);
			if(td==null)
			{
				td = new TypeData();
				td.setType(tmp);
			}
			td.addEng(d.getFuelCsption());
			td.addLen(d.getGoTurn());
			clsMap.put(d.getFuelType(),tmp,td);
		}

	}
	
	
	for(String et:monthMap.getXset()){
		etoi = new EngTypOtherItem();
		etoi.setBaseTyp(et);
		etoi.setEngTypMo(new ArrayList<TypeData>(monthMap.getYMap(et).values()));
		etoi.setEngTypCLs(new ArrayList<TypeData>(clsMap.getYMap(et).values()));
		engTypeOther.add(etoi);
	}
	
	Map<String ,Object> map = new HashMap<String ,Object>();
	map.put("engTypeOther",engTypeOther);
	return map;
}

public  Map<String,Object> getTaxiTranTypOther(RequestData rd){
	

	String[] times = 
			TimeTools.sqlitTimeRange(rd.getTimeRange());
	String[] places = new String[2];
	String enterprice=null;
	int k = 0x0;
	
	List<TaxiTranData> allData =  new ArrayList<>();
	
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
	
	if( (k&0x1)!=0 ){
		List<TaxiTranData> l = 
				taxiTranDao.getTaxiTranAll(times[0], times[1], enterprice, places[0], places[1]);
		if(l!=null)
			allData.addAll(l);
	}
	if( (k&0x2)!=0 ){

	}
	
	List<EngTypOtherItem> engTypeOther = new ArrayList<>();
	TwoDecMap<String,TypeData> monthMap = new TwoDecMap<>();
	TwoDecMap<String,TypeData> dpMap = new TwoDecMap<>();

	String tmp = null;
	TypeData td = null;
	EngTypOtherItem etoi = null;
	for(TaxiTranData d:allData){
		//month
		tmp = TimeTools.getYearMonth(d.getInTime());
		if(tmp!=null){
			td = monthMap.get(d.getFuelType(),tmp);
			if(td==null)
			{
				td = new TypeData();
				td.setType(tmp);
			}
			td.addEng(d.getFuelCsption());
			td.addLen(d.getGoTurn());
			monthMap.put(d.getFuelType(),tmp,td);
		}

		
		//sitCOt
		tmp = tg.getTaxiTranDpType(d.getDpCot());
		if(tmp!=null){
			td = dpMap.get(d.getFuelType(),tmp);
			if(td==null)
			{
				td = new TypeData();
				td.setType(tmp);
			}
			td.addEng(d.getFuelCsption());
			td.addLen(d.getGoTurn());
			dpMap.put(d.getFuelType(),tmp,td);
		}

	}
	
	
	for(String et:monthMap.getXset()){
		etoi = new EngTypOtherItem();
		etoi.setBaseTyp(et);
		etoi.setEngTypMo(new ArrayList<TypeData>(monthMap.getYMap(et).values()));
		etoi.setEngTypPs(new ArrayList<TypeData>(dpMap.getYMap(et).values()));
		engTypeOther.add(etoi);
	}
	
	Map<String ,Object> map = new HashMap<String ,Object>();
	map.put("engTypeOther",engTypeOther);
	return map;
}

	
	
	
}
