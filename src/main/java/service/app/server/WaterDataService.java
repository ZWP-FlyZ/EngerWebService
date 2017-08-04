package service.app.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import service.app.dao.OceanGdsDao;
import service.app.dao.OceanPassDao;
import service.app.dao.PortProDao;
import service.app.dao.RiverTranDao;
import service.app.model.OceanGoodsData;
import service.app.model.OceanPassData;
import service.app.model.PortProData;
import service.app.model.RiverTranData;
import service.app.tramodel.RequestData;
import service.app.tramodel.RoleType;
import service.app.tramodel.TypeData;
import service.app.tramodel.items.BaseTypOtherItem;
import service.app.tramodel.items.EngTypOtherItem;
import service.app.tramodel.items.EntTypOtherItem;
import service.app.tramodel.items.WeiTypOtherItem;
import service.app.util.TimeTools;
import service.app.util.TwoDecMap;
import service.app.util.TypeGetter;

@Service
public class WaterDataService {

	@Autowired
	OceanGdsDao oceanGdsDao;
	@Autowired
	OceanPassDao oceanPassDao;
	@Autowired
	RiverTranDao riverTranDao;
	@Autowired
	PortProDao portProDao;
	
	@Autowired
	TypeGetter tg;
	
	
	public Map<String ,Object> getRiverTranTypeOther(RequestData rd){
		String[] times = 
				TimeTools.sqlitTimeRange(rd.getTimeRange());
		String[] places = new String[2];
		String enterprice=null;
		int k = 0x0;
		
		if(rd.getPlace1()==null)
			rd.setPlace1("%%");
		if(rd.getPlace2()==null)
			rd.setPlace2("%%");
		
		
		List<RiverTranData> allData =  new ArrayList<>();
		
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

		}
		if( (k&0x2)!=0 ){
			List<RiverTranData> l = 
					riverTranDao.getRiverTranAll(times[0], times[1], enterprice, places[0], places[1]);
			if(l!=null)
				allData.addAll(l);
		}
		
		List<EngTypOtherItem> engTypeOther = new ArrayList<>();
		List<EntTypOtherItem> entTypeOther = new ArrayList<>();
		List<WeiTypOtherItem> weiTypOther = new ArrayList<>();

		
		TwoDecMap<String,TypeData> engMonthMap = new TwoDecMap<>();
		TwoDecMap<String,TypeData> engTonMap = new TwoDecMap<>();
		TwoDecMap<String,TypeData> entMap = new TwoDecMap<>();
		TwoDecMap<String,TypeData> tonStMap = new TwoDecMap<>();
		String tmp = null;
		TypeData td = null;

		for(RiverTranData d:allData){
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
			tmp = tg.getRiverTranEntSType(d.getEntS());
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

			
			//tonnage
			tmp = tg.getRiverTranTonType(d.getTonnage());
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
			
			//tonnage - ship type
			if(tmp!=null){
				td = tonStMap.get(tmp,d.getShipType());
				if(td==null)
				{
					td = new TypeData();
					td.setType(d.getShipType());
				}
				td.addEng(d.getFuelCsption());
				td.addLen(d.getGoTurn());
				tonStMap.put(tmp,d.getShipType(),td);
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
		
		WeiTypOtherItem wtoi = null;
		for(String et:tonStMap.getXset()){
			wtoi = new WeiTypOtherItem();
			wtoi.setBaseTyp(et);
			wtoi.setWeiTypSt(new ArrayList<TypeData>(tonStMap.getYMap(et).values()));
			weiTypOther.add(wtoi);
		}
		
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("engTypeOther",engTypeOther);
		map.put("entTypeOther",entTypeOther);
		map.put("weiTypOther",weiTypOther);
		return map;
	}
	
	public Map<String ,Object> getOceanGoodsTypeOther(RequestData rd){
		String[] times = 
				TimeTools.sqlitTimeRange(rd.getTimeRange());
		String[] places = new String[2];
		String enterprice=null;
		int k = 0x0;
		
		if(rd.getPlace1()==null)
			rd.setPlace1("%%");
		if(rd.getPlace2()==null)
			rd.setPlace2("%%");
		
		List<OceanGoodsData> allData =  new ArrayList<>();
		
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

		}
		if( (k&0x2)!=0 ){
			List<OceanGoodsData> l = 
					oceanGdsDao.getOceanGdsAll(times[0], times[1], enterprice, places[0], places[1]);
			if(l!=null)
				allData.addAll(l);
		}
		
		List<EngTypOtherItem> engTypeOther = new ArrayList<>();
		List<EntTypOtherItem> entTypeOther = new ArrayList<>();
		List<WeiTypOtherItem> weiTypOther = new ArrayList<>();

		
		TwoDecMap<String,TypeData> engMonthMap = new TwoDecMap<>();
		TwoDecMap<String,TypeData> engTonMap = new TwoDecMap<>();
		TwoDecMap<String,TypeData> engDisMap = new TwoDecMap<>();
		
		TwoDecMap<String,TypeData> entMap = new TwoDecMap<>();
		TwoDecMap<String,TypeData> tonStMap = new TwoDecMap<>();
		
		String tmp = null;
		TypeData td = null;

		for(OceanGoodsData d:allData){
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
			tmp = tg.getOceanGoodsEntSType(d.getEntS());
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
			
			//tranDIs
			tmp = tg.getOceanGoodsTranDisType(d.getTranDis());
			if(tmp!=null){
				td = engDisMap.get(d.getFuelType(),tmp);
				if(td==null)
				{
					td = new TypeData();
					td.setType(tmp);
				}
				td.addEng(d.getFuelCsption());
				td.addLen(d.getGoTurn());
				engDisMap.put(d.getFuelType(),tmp,td);
			}

			
			//tonnage
			tmp = tg.getOceanGoodsTonType(d.getTonnage());
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

			//tonnage - ship type
			if(tmp!=null){
				td = tonStMap.get(tmp,d.getShipType());
				if(td==null)
				{
					td = new TypeData();
					td.setType(d.getShipType());
				}
				td.addEng(d.getFuelCsption());
				td.addLen(d.getGoTurn());
				tonStMap.put(tmp,d.getShipType(),td);
			}
					
		}
		
		EngTypOtherItem etoi = null;
		for(String et:engMonthMap.getXset()){
			etoi = new EngTypOtherItem();
			etoi.setBaseTyp(et);
			etoi.setEngTypMo(new ArrayList<TypeData>(engMonthMap.getYMap(et).values()));
			etoi.setEngTypWs(new ArrayList<TypeData>(engTonMap.getYMap(et).values()));
			etoi.setEngTypLs(new ArrayList<TypeData>(engDisMap.getYMap(et).values()));
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
		
		WeiTypOtherItem wtoi = null;
		for(String et:tonStMap.getXset()){
			wtoi = new WeiTypOtherItem();
			wtoi.setBaseTyp(et);
			wtoi.setWeiTypSt(new ArrayList<TypeData>(tonStMap.getYMap(et).values()));
			weiTypOther.add(wtoi);
		}
		
		
		
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("engTypeOther",engTypeOther);
		map.put("entTypeOther",entTypeOther);
		map.put("weiTypOther",weiTypOther);
		return map;
		
		
	
	}
	
	public Map<String ,Object> getOceanPassTypeOther(RequestData rd){
		String[] times = 
				TimeTools.sqlitTimeRange(rd.getTimeRange());
		String[] places = new String[2];
		String enterprice=null;
		int k = 0x0;
		
		if(rd.getPlace1()==null)
			rd.setPlace1("%%");
		if(rd.getPlace2()==null)
			rd.setPlace2("%%");
		
		List<OceanPassData> allData =  new ArrayList<>();
		
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

		}
		if( (k&0x2)!=0 ){
			List<OceanPassData> l = 
					oceanPassDao.getOceanPassAll(times[0], times[1], enterprice, places[0], places[1]);
			if(l!=null)
				allData.addAll(l);
		}
		
		List<EngTypOtherItem> engTypeOther = new ArrayList<>();
		List<EntTypOtherItem> entTypeOther = new ArrayList<>();
		List<BaseTypOtherItem> disTypOther = new ArrayList<>();

		
		TwoDecMap<String,TypeData> engMonthMap = new TwoDecMap<>();
		TwoDecMap<String,TypeData> engSitMap = new TwoDecMap<>();
		TwoDecMap<String,TypeData> entMap = new TwoDecMap<>();
		TwoDecMap<String,TypeData> disMap = new TwoDecMap<>();
		
		String tmp = null;
		TypeData td = null;
		EngTypOtherItem etoi = null;
		for(OceanPassData d:allData){
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

			
			//sitcot
			tmp = tg.getOceanPassSitSizeType(d.getSitCot());
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
			tmp = tg.getOceanPassEntSType(d.getEntS());
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
			tmp = tg.getOceanPassTranDisType(d.getTranDis());
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
			
		}
		
		
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
		
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("engTypeOther",engTypeOther);
		map.put("entTypeOther",entTypeOther);
		map.put("disTypOther",disTypOther);
		return map;
		
	}
	
	public Map<String ,Object> getPortProductTypeOther(RequestData rd){
		String[] times = 
				TimeTools.sqlitTimeRange(rd.getTimeRange());
		String[] places = new String[2];
		String enterprice=null;
		int k = 0x0;
		
		if(rd.getPlace1()==null)
			rd.setPlace1("%%");
		if(rd.getPlace2()==null)
			rd.setPlace2("%%");
		
		List<PortProData> allData =  new ArrayList<>();
		
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

		}
		if( (k&0x2)!=0 ){
			List<PortProData> l = 
					portProDao.getProtProAll(times[0], times[1], enterprice, places[0], places[1]);
			if(l!=null)
				allData.addAll(l);
		}
		
		List<EngTypOtherItem> engTypeOther = new ArrayList<>();
		List<EntTypOtherItem> entTypeOther = new ArrayList<>();
		TwoDecMap<String,TypeData> engMonthMap = new TwoDecMap<>();
		TwoDecMap<String,TypeData> entMap = new TwoDecMap<>();
		
		String tmp = null;
		TypeData td = null;
		
		for(PortProData d:allData){
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
		
		
		EngTypOtherItem etoi = null;
		EntTypOtherItem ettoi = null;
		for(String et:engMonthMap.getXset()){
			etoi = new EngTypOtherItem();
			etoi.setBaseTyp(et);
			etoi.setEngTypMo(new ArrayList<TypeData>(engMonthMap.getYMap(et).values()));
			engTypeOther.add(etoi);
		}
		for(String et:entMap.getXset()){
			ettoi = new EntTypOtherItem();
			ettoi.setBaseTyp(et);
			td = entMap.get(et, et);
			ettoi.setBaseTypDatOfAllEng(td.getTypDatOfAllEng());
			ettoi.setBaseTypDatOfAllLen(td.getTypDatOfAllLen());
			entTypeOther.add(ettoi);
		}
		
		
		
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("engTypeOther",engTypeOther);
		map.put("entTypeOther",entTypeOther);
		return map;
	}
	
	
}
