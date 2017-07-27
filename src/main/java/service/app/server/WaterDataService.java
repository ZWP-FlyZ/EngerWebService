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
import service.app.tramodel.EngTypOtherItem;
import service.app.tramodel.EntTypOtherItem;
import service.app.tramodel.RequestData;
import service.app.tramodel.RoleType;
import service.app.tramodel.TypeData;
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
		TwoDecMap<String,TypeData> monthMap = new TwoDecMap<>();
		TwoDecMap<String,TypeData> tonMap = new TwoDecMap<>();
		TwoDecMap<String,TypeData> entSMap = new TwoDecMap<>();
		
		String tmp = null;
		TypeData td = null;
		EngTypOtherItem etoi = null;
		for(RiverTranData d:allData){
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
			tmp = tg.getRiverTranTonType(d.getTonnage());
			if(tmp!=null){
				td = tonMap.get(d.getFuelType(),tmp);
				if(td==null)
				{
					td = new TypeData();
					td.setType(tmp);
				}
				td.addEng(d.getFuelCsption());
				td.addLen(d.getGoTurn());
				tonMap.put(d.getFuelType(),tmp,td);
			}

			
			//EntSize
			tmp = tg.getRiverTranEntSType(d.getEntS());
			if(tmp!=null){
				td = entSMap.get(d.getFuelType(),tmp);
				if(td==null)
				{
					td = new TypeData();
					td.setType(tmp);
				}
				td.addEng(d.getFuelCsption());
				td.addLen(d.getGoTurn());
				entSMap.put(d.getFuelType(),tmp,td);
			}
		}
		
		
		for(String et:monthMap.getXset()){
			etoi = new EngTypOtherItem();
			etoi.setBaseTyp(et);
			etoi.setEngTypMo(new ArrayList<TypeData>(monthMap.getYMap(et).values()));
			etoi.setEngTypSs(new ArrayList<TypeData>(tonMap.getYMap(et).values()));
			etoi.setEngTypEs(new ArrayList<TypeData>(entSMap.getYMap(et).values()));
			engTypeOther.add(etoi);
		}
		
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("engTypeOther",engTypeOther);
		return map;
	}
	
	public Map<String ,Object> getOceanGoodsTypeOther(RequestData rd){
		String[] times = 
				TimeTools.sqlitTimeRange(rd.getTimeRange());
		String[] places = new String[2];
		String enterprice=null;
		int k = 0x0;
		
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
		TwoDecMap<String,TypeData> monthMap = new TwoDecMap<>();
		TwoDecMap<String,TypeData> tonMap = new TwoDecMap<>();
		TwoDecMap<String,TypeData> entSMap = new TwoDecMap<>();
		TwoDecMap<String,TypeData> tranDisMap = new TwoDecMap<>();
		String tmp = null;
		TypeData td = null;
		EngTypOtherItem etoi = null;
		for(OceanGoodsData d:allData){
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

			
			//tonnage
			tmp = tg.getOceanGoodsTonType(d.getTonnage());
			if(tmp!=null){
				td = tonMap.get(d.getFuelType(),tmp);
				if(td==null)
				{
					td = new TypeData();
					td.setType(tmp);
				}
				td.addEng(d.getFuelCsption());
				td.addLen(d.getGoTurn());
				tonMap.put(d.getFuelType(),tmp,td);
			}

			
			//EntSize
			tmp = tg.getOceanGoodsEntSType(d.getEntS());
			if(tmp!=null){
				td = entSMap.get(d.getFuelType(),tmp);
				if(td==null)
				{
					td = new TypeData();
					td.setType(tmp);
				}
				td.addEng(d.getFuelCsption());
				td.addLen(d.getGoTurn());
				entSMap.put(d.getFuelType(),tmp,td);
			}
			
			//tranDIs
			tmp = tg.getOceanGoodsTranDisType(d.getTranDis());
			if(tmp!=null){
				td = tranDisMap.get(d.getFuelType(),tmp);
				if(td==null)
				{
					td = new TypeData();
					td.setType(tmp);
				}
				td.addEng(d.getFuelCsption());
				td.addLen(d.getGoTurn());
				tranDisMap.put(d.getFuelType(),tmp,td);
			}
			
		}
		
		
		for(String et:monthMap.getXset()){
			etoi = new EngTypOtherItem();
			etoi.setBaseTyp(et);
			etoi.setEngTypMo(new ArrayList<TypeData>(monthMap.getYMap(et).values()));
			etoi.setEngTypSs(new ArrayList<TypeData>(tonMap.getYMap(et).values()));
			etoi.setEngTypEs(new ArrayList<TypeData>(entSMap.getYMap(et).values()));
			etoi.setEngTypLs(new ArrayList<TypeData>(tranDisMap.getYMap(et).values()));
			engTypeOther.add(etoi);
		}
		
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("engTypeOther",engTypeOther);
		return map;
		
		
	
	}
	
	public Map<String ,Object> getOceanPassTypeOther(RequestData rd){
		String[] times = 
				TimeTools.sqlitTimeRange(rd.getTimeRange());
		String[] places = new String[2];
		String enterprice=null;
		int k = 0x0;
		
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
		TwoDecMap<String,TypeData> monthMap = new TwoDecMap<>();
		TwoDecMap<String,TypeData> sitSizeMap = new TwoDecMap<>();
		TwoDecMap<String,TypeData> entSMap = new TwoDecMap<>();
		TwoDecMap<String,TypeData> tranDisMap = new TwoDecMap<>();
		String tmp = null;
		TypeData td = null;
		EngTypOtherItem etoi = null;
		for(OceanPassData d:allData){
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

			
			//sitcot
			tmp = tg.getOceanPassSitSizeType(d.getSitCot());
			if(tmp!=null){
				td = sitSizeMap.get(d.getFuelType(),tmp);
				if(td==null)
				{
					td = new TypeData();
					td.setType(tmp);
				}
				td.addEng(d.getFuelCsption());
				td.addLen(d.getGoTurn());
				sitSizeMap.put(d.getFuelType(),tmp,td);
			}

			
			//EntSize
			tmp = tg.getOceanPassEntSType(d.getEntS());
			if(tmp!=null){
				td = entSMap.get(d.getFuelType(),tmp);
				if(td==null)
				{
					td = new TypeData();
					td.setType(tmp);
				}
				td.addEng(d.getFuelCsption());
				td.addLen(d.getGoTurn());
				entSMap.put(d.getFuelType(),tmp,td);
			}
			
			//tranDIs
			tmp = tg.getOceanPassTranDisType(d.getTranDis());
			if(tmp!=null){
				td = tranDisMap.get(d.getFuelType(),tmp);
				if(td==null)
				{
					td = new TypeData();
					td.setType(tmp);
				}
				td.addEng(d.getFuelCsption());
				td.addLen(d.getGoTurn());
				tranDisMap.put(d.getFuelType(),tmp,td);
			}
			
		}
		
		
		for(String et:monthMap.getXset()){
			etoi = new EngTypOtherItem();
			etoi.setBaseTyp(et);
			etoi.setEngTypMo(new ArrayList<TypeData>(monthMap.getYMap(et).values()));
			etoi.setEngTypSs(new ArrayList<TypeData>(sitSizeMap.getYMap(et).values()));
			etoi.setEngTypEs(new ArrayList<TypeData>(entSMap.getYMap(et).values()));
			etoi.setEngTypLs(new ArrayList<TypeData>(tranDisMap.getYMap(et).values()));
			engTypeOther.add(etoi);
		}
		
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("engTypeOther",engTypeOther);
		return map;
		
	}
	
	public Map<String ,Object> getPortProductTypeOther(RequestData rd){
		String[] times = 
				TimeTools.sqlitTimeRange(rd.getTimeRange());
		String[] places = new String[2];
		String enterprice=null;
		int k = 0x0;
		
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
		TwoDecMap<String,TypeData> entMonthMap = new TwoDecMap<>();
		
		String tmp = null;
		String tmp2 = null;
		TypeData td = null;
		
		for(PortProData d:allData){
			//month
			tmp = TimeTools.getYearMonth(d.getInTime());
			if(tmp!=null){
				td = engMonthMap.get(TypeGetter.CHAI_YOU,tmp);//柴油
				if(td==null)
				{
					td = new TypeData();
					td.setType(tmp);
				}
				td.addEng(d.getDiesel());
				td.addLen(d.getProTask());
				engMonthMap.put(TypeGetter.CHAI_YOU,tmp,td);
				
				td = engMonthMap.get(TypeGetter.QI_YOU,tmp);
				if(td==null)
				{
					td = new TypeData();
					td.setType(tmp);
				}
				td.addEng(d.getGasoline());
				td.addLen(d.getProTask());
				engMonthMap.put(TypeGetter.QI_YOU,tmp,td);
				
				td = engMonthMap.get(TypeGetter.MEI_YOU,tmp);
				if(td==null)
				{
					td = new TypeData();
					td.setType(tmp);
				}
				td.addEng(d.getCoal());
				td.addLen(d.getProTask());
				engMonthMap.put(TypeGetter.MEI_YOU,tmp,td);
				
				td = engMonthMap.get(TypeGetter.DIAN_LI,tmp);
				if(td==null)
				{
					td = new TypeData();
					td.setType(tmp);
				}
				td.addEng(d.getPower());
				td.addLen(d.getProTask());
				engMonthMap.put(TypeGetter.DIAN_LI,tmp,td);
				
				td = engMonthMap.get(TypeGetter.QI_TA,tmp);
				if(td==null)
				{
					td = new TypeData();
					td.setType(tmp);
				}
				td.addEng(d.getOther());
				td.addLen(d.getProTask());
				engMonthMap.put(TypeGetter.QI_TA,tmp,td);
				
				tmp2 = tg.getPortProEntSType(d.getProTask());//企业 规模 每月数据
				td = entMonthMap.get(tmp2, tmp);
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
				entMonthMap.put(tmp2, tmp, td);
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
		for(String et:entMonthMap.getXset()){
			ettoi = new EntTypOtherItem();
			ettoi.setBaseTyp(et);
			ettoi.setEntTypMo(new ArrayList<TypeData>(entMonthMap.getYMap(et).values()));
			entTypeOther.add(ettoi);
		}
		
		
		
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("engTypeOther",engTypeOther);
		map.put("entTypeOther",entTypeOther);
		return map;
	}
	
	
}
