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
import service.app.model.RiverTranData;
import service.app.model.RoadPassData;
import service.app.tramodel.EngTypOtherItem;
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
			etoi.setEngTyp(et);
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
			etoi.setEngTyp(et);
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
			etoi.setEngTyp(et);
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
		return null;
	}
	
	
}
