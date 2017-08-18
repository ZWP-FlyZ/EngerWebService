package service.app.server.n;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import service.app.callables.BaseWork;
import service.app.callables.RoadPassDataWork;
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
import service.app.worker.CalWorker;

@Service("landServiceN")
public class LandDataService {

	
	@Autowired
	CalWorker cw;
	
	private final static Logger logger = LoggerFactory.getLogger(IndexService.class);
	
	@SuppressWarnings("unchecked")
	public  Map<String,Object> getRoadPassTypOther(RequestData rd){
		

	
		List<BaseWork<RequestData,
				List<TwoDecMap<String, ? extends TypeData>>>> works = new ArrayList<>();
		
		List<String> YMs = TimeTools.getYMlist(rd.getTimeRange());
		RequestData tmp= null;
		List<List<TwoDecMap<String, ? extends TypeData>>> results = null;
	
		

		List<EngTypOtherItem> engTypeOther = new ArrayList<>();
		List<EntTypOtherItem> entTypeOther = new ArrayList<>();
		List<BaseTypOtherItem> disTypOther = new ArrayList<>();
		List<CarTypOtherItem> carTypOther = new ArrayList<>();
		
		
		if(YMs.size()==0) throw new NullPointerException("年月列表为空");
		
		YMs.set(YMs.size()-1, TimeTools.sqlitTimeRange(rd.getTimeRange())[1]);
		for(int i=0;i<YMs.size()-1;i++){
			tmp = rd.clone();
			if(tmp==null) throw new NullPointerException("克隆失败！");
			tmp.setTargeDay(YMs.get(i)+"-15");
			works.add(new RoadPassDataWork(tmp));
		}
		tmp = rd.clone();
		if(tmp==null) throw new NullPointerException("克隆失败！");
		tmp.setTargeDay(YMs.get(YMs.size()-1));
		works.add(new RoadPassDataWork(tmp));
		
		results = cw.submitWorkList(works);//wait for finish
		
		List<TwoDecMap<String, ? extends TypeData>> one = results.get(0);
		List<TwoDecMap<String, ? extends TypeData>> two = null;
		TwoDecMap<String,   TypeData> om = null;
		TwoDecMap<String,   TypeData> tm = null;
		for(int i=1;i<results.size();i++){
			two = results.get(i);
			for(int j=0;j<one.size();j++){
				om = (TwoDecMap<String, TypeData>) one.get(j);
				tm = (TwoDecMap<String, TypeData>) two.get(j);
				one.set(j, tm.add(om));
			}
		}	
		
		if(one.size()>0){
			om = (TwoDecMap<String, TypeData>) one.get(0);
			tm = (TwoDecMap<String, TypeData>) one.get(1);
			
			EngTypOtherItem etoi = null;
			for(String et:om.getXset()){
				etoi = new EngTypOtherItem();
				etoi.setBaseTyp(et);
				etoi.setEngTypMo(new ArrayList<TypeData>(om.getYMap(et).values()));
				etoi.setEngTypSs(new ArrayList<TypeData>(tm.getYMap(et).values()));
				engTypeOther.add(etoi);
			}
			
			om = (TwoDecMap<String, TypeData>) one.get(2);
			EntTypOtherItem ettoi = null;
			TypeData td = null;
			for(String et:om.getXset()){
				ettoi = new EntTypOtherItem();
				ettoi.setBaseTyp(et);
				td = om.get(et, et);
				ettoi.setBaseTypDatOfAllEng(td.getTypDatOfAllEng());
				ettoi.setBaseTypDatOfAllLen(td.getTypDatOfAllLen());
				entTypeOther.add(ettoi);
			}
			
			om = (TwoDecMap<String, TypeData>) one.get(3);
			BaseTypOtherItem dtoi = null;
			for(String et:om.getXset()){
				dtoi = new BaseTypOtherItem();
				dtoi.setBaseTyp(et);
				td = om.get(et, et);
				dtoi.setBaseTypDatOfAllEng(td.getTypDatOfAllEng());
				dtoi.setBaseTypDatOfAllLen(td.getTypDatOfAllLen());
				disTypOther.add(dtoi);
			}
			
			om = (TwoDecMap<String, TypeData>) one.get(4);
			CarTypOtherItem cttoi = null;
			for(String et:om.getXset()){
				cttoi = new CarTypOtherItem();
				cttoi.setBaseTyp(et);
				td = om.get(et, et);
				cttoi.setBaseTypDatOfAllEng(td.getTypDatOfAllEng());
				cttoi.setBaseTypDatOfAllLen(td.getTypDatOfAllLen());
				carTypOther.add(cttoi);
			}
		}// end if 
		
		

		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("engTypeOther",engTypeOther);
		map.put("entTypeOther",entTypeOther);
		map.put("disTypOther",disTypOther);
		map.put("carTypOther",carTypOther);
		return map;
	}

//public  Map<String,Object> getRoadGoodsTypOther(RequestData rd){
//	
//
//	String[] times = 
//			TimeTools.sqlitTimeRange(rd.getTimeRange());
//	String[] places = new String[2];
//	String enterprice=null;
//	int k = 0x0;
//	
//	if(rd.getPlace1()==null)
//		rd.setPlace1("%%");
//	if(rd.getPlace2()==null)
//		rd.setPlace2("%%");
//	List<RoadGoodsData> allData =  new ArrayList<>();
//	
//	places[0] = rd.getPlace1();
//	places[1] = rd.getPlace2();
//	if(rd.getRoleType().equals(RoleType.ROLE_TRAFFIC)){
//		enterprice = "%%";
//		k=0x3;
//	}else if(rd.getRoleType().equals(RoleType.ROLE_ENTERPEICE)){
//		enterprice = rd.getRoleName();
//		k = 0x3;
//	}else if(rd.getRoleType().equals(RoleType.ROLE_LAND)){
//		enterprice = "%%";
//		k=0x1;
//	}else if(rd.getRoleType().equals(RoleType.ROLE_WATER)){
//		enterprice = "%%";
//		k=0x2;
//	}
//	
//	if(rd.getRoleType().equals(RoleType.ROLE_ADMIN)) {
//		k = 0x3;
//		enterprice = "%%";
//	}
//	
//	if( (k&0x1)!=0 ){
//		List<RoadGoodsData> l = 
//				roadGdsDao.getRoadGdsAll(times[0], times[1], enterprice, places[0], places[1]);
//		if(l!=null)
//			allData.addAll(l);
//	}
//	if( (k&0x2)!=0 ){
//
//	}
//	
//	List<EngTypOtherItem> engTypeOther = new ArrayList<>();
//	List<EntTypOtherItem> entTypeOther = new ArrayList<>();
//	List<CarTypOtherItem> carTypOther = new ArrayList<>();
//	
//	TwoDecMap<String,TypeData> engMonthMap = new TwoDecMap<>();
//	TwoDecMap<String,TypeData> engTonMap = new TwoDecMap<>();
//	TwoDecMap<String,TypeData> entMap = new TwoDecMap<>();
//	TwoDecMap<String,TypeData> carTonMap = new TwoDecMap<>();
//	
//	String tmp = null;
//
//	TypeData td = null;
//
//	for(RoadGoodsData d:allData){
//		//month
//		tmp = TimeTools.getYearMonth(d.getInTime());
//		if(tmp!=null){
//			td = engMonthMap.get(d.getFuelType(),tmp);
//			if(td==null)
//			{
//				td = new TypeData();
//				td.setType(tmp);
//			}
//			td.addEng(d.getFuelCsption());
//			td.addLen(d.getGoTurn());
//			engMonthMap.put(d.getFuelType(),tmp,td);
//		}
//		
//		//EntSize
//		tmp = tg.getRoadGoodsEntSizeType(d.getEntS());
//		if(tmp!=null){
//			td = entMap.get(tmp,tmp);
//			if(td==null)
//			{
//				td = new TypeData();
//				td.setType(tmp);
//			}
//			td.addEng(d.getFuelCsption());
//			td.addLen(d.getGoTurn());
//			entMap.put(tmp,tmp,td);
//		}
//		
//		//ws
//		tmp = tg.getRoadGoodsTonType(d.getTonnage());
//		if(tmp!=null){
//			td = engTonMap.get(d.getFuelType(),tmp);
//			if(td==null)
//			{
//				td = new TypeData();
//				td.setType(tmp);
//			}
//			td.addEng(d.getFuelCsption());
//			td.addLen(d.getGoTurn());
//			engTonMap.put(d.getFuelType(),tmp,td);
//		}
//		
//		
//		// car - tonnage
//		if(tmp!=null){
//			td = carTonMap.get(d.getCarType(),tmp);
//			if(td==null)
//			{
//				td = new TypeData();
//				td.setType(tmp);
//			}
//			td.addEng(d.getFuelCsption());
//			td.addLen(d.getGoTurn());
//			carTonMap.put(d.getCarType(),tmp,td);
//		}
//
//	}
//	
//	
//	EngTypOtherItem etoi = null;
//	for(String et:engMonthMap.getXset()){
//		etoi = new EngTypOtherItem();
//		etoi.setBaseTyp(et);
//		etoi.setEngTypMo(new ArrayList<TypeData>(engMonthMap.getYMap(et).values()));
//		etoi.setEngTypWs(new ArrayList<TypeData>(engTonMap.getYMap(et).values()));
//		engTypeOther.add(etoi);
//	}
//	
//	EntTypOtherItem ettoi = null;
//	for(String et:entMap.getXset()){
//		ettoi = new EntTypOtherItem();
//		ettoi.setBaseTyp(et);
//		td = entMap.get(et, et);
//		ettoi.setBaseTypDatOfAllEng(td.getTypDatOfAllEng());
//		ettoi.setBaseTypDatOfAllLen(td.getTypDatOfAllLen());
//		entTypeOther.add(ettoi);
//	}
//	
//	CarTypOtherItem ctoi = null;
//	for(String et:carTonMap.getXset()){
//		ctoi = new CarTypOtherItem();
//		ctoi.setBaseTyp(et);
//		ctoi.setCarTypWs(new ArrayList<TypeData>(carTonMap.getYMap(et).values()));
//		carTypOther.add(ctoi);
//	}
//	
//	Map<String ,Object> map = new HashMap<String ,Object>();
//	map.put("engTypeOther",engTypeOther);
//	map.put("entTypeOther",entTypeOther);
//	map.put("carTypOther",carTypOther);
//	return map;
//}
//
//public  Map<String,Object> getBusTranTypOther(RequestData rd){
//	
//
//	String[] times = 
//			TimeTools.sqlitTimeRange(rd.getTimeRange());
//	String[] places = new String[2];
//	String enterprice=null;
//	int k = 0x0;
//	
//	if(rd.getPlace1()==null)
//		rd.setPlace1("%%");
//	if(rd.getPlace2()==null)
//		rd.setPlace2("%%");
//	
//	List<BusTranData> allData =  new ArrayList<>();
//	
//	places[0] = rd.getPlace1();
//	places[1] = rd.getPlace2();
//	if(rd.getRoleType().equals(RoleType.ROLE_TRAFFIC)){
//		enterprice = "%%";
//		k=0x3;
//	}else if(rd.getRoleType().equals(RoleType.ROLE_ENTERPEICE)){
//		enterprice = rd.getRoleName();
//		k = 0x3;
//	}else if(rd.getRoleType().equals(RoleType.ROLE_LAND)){
//		enterprice = "%%";
//		k=0x1;
//	}else if(rd.getRoleType().equals(RoleType.ROLE_WATER)){
//		enterprice = "%%";
//		k=0x2;
//	}
//	
//	if(rd.getRoleType().equals(RoleType.ROLE_ADMIN)) {
//		k = 0x3;
//		enterprice = "%%";
//	}
//	
//	if( (k&0x1)!=0 ){
//		List<BusTranData> l = 
//				busTranDao.getBusTranAll(times[0], times[1], enterprice, places[0], places[1]);
//		if(l!=null)
//			allData.addAll(l);
//	}
//	if( (k&0x2)!=0 ){
//
//	}
//	
//	List<EngTypOtherItem> engTypeOther = new ArrayList<>();
//	TwoDecMap<String,TypeData> monthMap = new TwoDecMap<>();
//	TwoDecMap<String,TypeData> clsMap = new TwoDecMap<>();
//
//	String tmp = null;
//	TypeData td = null;
//	EngTypOtherItem etoi = null;
//	for(BusTranData d:allData){
//		//month
//		tmp = TimeTools.getYearMonth(d.getInTime());
//		if(tmp!=null){
//			td = monthMap.get(d.getFuelType(),tmp);
//			if(td==null)
//			{
//				td = new TypeData();
//				td.setType(tmp);
//			}
//			td.addEng(d.getFuelCsption());
//			td.addLen(d.getGoTurn());
//			monthMap.put(d.getFuelType(),tmp,td);
//		}
//
//		
//		//cl
//		tmp = tg.getBusTranCarLenType(d.getCarLength());
//		if(tmp!=null){
//			td = clsMap.get(d.getFuelType(),tmp);
//			if(td==null)
//			{
//				td = new TypeData();
//				td.setType(tmp);
//			}
//			td.addEng(d.getFuelCsption());
//			td.addLen(d.getGoTurn());
//			clsMap.put(d.getFuelType(),tmp,td);
//		}
//
//	}
//	
//	
//	for(String et:monthMap.getXset()){
//		etoi = new EngTypOtherItem();
//		etoi.setBaseTyp(et);
//		etoi.setEngTypMo(new ArrayList<TypeData>(monthMap.getYMap(et).values()));
//		etoi.setEngTypCLs(new ArrayList<TypeData>(clsMap.getYMap(et).values()));
//		engTypeOther.add(etoi);
//	}
//	
//	Map<String ,Object> map = new HashMap<String ,Object>();
//	map.put("engTypeOther",engTypeOther);
//	return map;
//}
//
//public  Map<String,Object> getTaxiTranTypOther(RequestData rd){
//	
//
//	String[] times = 
//			TimeTools.sqlitTimeRange(rd.getTimeRange());
//	String[] places = new String[2];
//	String enterprice=null;
//	int k = 0x0;
//	
//	if(rd.getPlace1()==null)
//		rd.setPlace1("%%");
//	if(rd.getPlace2()==null)
//		rd.setPlace2("%%");
//	
//	List<TaxiTranData> allData =  new ArrayList<>();
//	
//	places[0] = rd.getPlace1();
//	places[1] = rd.getPlace2();
//	if(rd.getRoleType().equals(RoleType.ROLE_TRAFFIC)){
//		enterprice = "%%";
//		k=0x3;
//	}else if(rd.getRoleType().equals(RoleType.ROLE_ENTERPEICE)){
//		enterprice = rd.getRoleName();
//		k = 0x3;
//	}else if(rd.getRoleType().equals(RoleType.ROLE_LAND)){
//		enterprice = "%%";
//		k=0x1;
//	}else if(rd.getRoleType().equals(RoleType.ROLE_WATER)){
//		enterprice = "%%";
//		k=0x2;
//	}
//	
//	if(rd.getRoleType().equals(RoleType.ROLE_ADMIN)) {
//		k = 0x3;
//		enterprice = "%%";
//	}
//	
//	if( (k&0x1)!=0 ){
//		List<TaxiTranData> l = 
//				taxiTranDao.getTaxiTranAll(times[0], times[1], enterprice, places[0], places[1]);
//		if(l!=null)
//			allData.addAll(l);
//	}
//	if( (k&0x2)!=0 ){
//
//	}
//	
//	List<EngTypOtherItem> engTypeOther = new ArrayList<>();
//	TwoDecMap<String,TypeData> monthMap = new TwoDecMap<>();
//	TwoDecMap<String,TypeData> dpMap = new TwoDecMap<>();
//
//	String tmp = null;
//	TypeData td = null;
//	EngTypOtherItem etoi = null;
//	for(TaxiTranData d:allData){
//		//month
//		tmp = TimeTools.getYearMonth(d.getInTime());
//		if(tmp!=null){
//			td = monthMap.get(d.getFuelType(),tmp);
//			if(td==null)
//			{
//				td = new TypeData();
//				td.setType(tmp);
//			}
//			td.addEng(d.getFuelCsption());
//			td.addLen(d.getGoTurn());
//			monthMap.put(d.getFuelType(),tmp,td);
//		}
//
//		
//		//sitCOt
//		tmp = tg.getTaxiTranDpType(d.getDpCot());
//		if(tmp!=null){
//			td = dpMap.get(d.getFuelType(),tmp);
//			if(td==null)
//			{
//				td = new TypeData();
//				td.setType(tmp);
//			}
//			td.addEng(d.getFuelCsption());
//			td.addLen(d.getGoTurn());
//			dpMap.put(d.getFuelType(),tmp,td);
//		}
//
//	}
//	
//	
//	for(String et:monthMap.getXset()){
//		etoi = new EngTypOtherItem();
//		etoi.setBaseTyp(et);
//		etoi.setEngTypMo(new ArrayList<TypeData>(monthMap.getYMap(et).values()));
//		etoi.setEngTypPs(new ArrayList<TypeData>(dpMap.getYMap(et).values()));
//		engTypeOther.add(etoi);
//	}
//	
//	Map<String ,Object> map = new HashMap<String ,Object>();
//	map.put("engTypeOther",engTypeOther);
//	return map;
//}

	
	
	
}
