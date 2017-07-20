package service.app.server;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import service.app.dao.RoadPassDao;
import service.app.model.RoadPassData;
import service.app.tramodel.EngTypOtherItem;
import service.app.tramodel.RequestData;
import service.app.tramodel.RoleType;
import service.app.tramodel.TypeData;
import service.app.util.TimeTools;
import service.app.util.TwoDecMap;
import service.app.util.TypeGetter;

@Service
public class LandDataService {

	
	@Autowired
	RoadPassDao roadPassDao;
	
	@Autowired
	TypeGetter tg;
	
public  List<EngTypOtherItem> getRoadPassEngTypOther(RequestData rd){
		

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
			List<RoadPassData> l = 
					roadPassDao.getRoadPassAll(times[0], times[1], enterprice, places[0], places[1]);
			if(l!=null)
				allData.addAll(l);
		}
		
		List<EngTypOtherItem> engTypeOther = new ArrayList<>();
		TwoDecMap<String,TypeData> monthMap = new TwoDecMap<>();
		TwoDecMap<String,TypeData> sitSizwMap = new TwoDecMap<>();
		TwoDecMap<String,TypeData> entSMap = new TwoDecMap<>();
		TwoDecMap<String,TypeData> tranDisMap = new TwoDecMap<>();
		TwoDecMap<String,TypeData>  carTypeMap= new TwoDecMap<>();
		String tmp = null;
		TypeData td = null;
		EngTypOtherItem etoi = null;
		for(RoadPassData d:allData){
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
			tmp = tg.getRoadPassSitCotType(d.getSitCot());
			if(tmp!=null){
				td = sitSizwMap.get(d.getFuelType(),tmp);
				if(td==null)
				{
					td = new TypeData();
					td.setType(tmp);
				}
				td.addEng(d.getFuelCsption());
				td.addLen(d.getGoTurn());
				sitSizwMap.put(d.getFuelType(),tmp,td);
			}

			
			//EntSize
			tmp = tg.getRoadPassSitEntSizeType(d.getEntS());
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

			
			//TranDis
			tmp = tg.getRoadPassDisType(d.getTranDis());
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

			tmp = d.getCarType();
			if(tmp!=null){
				td = carTypeMap.get(d.getFuelType(),tmp);
				if(td==null)
				{
					td = new TypeData();
					td.setType(tmp);
				}
				td.addEng(d.getFuelCsption());
				td.addLen(d.getGoTurn());
				carTypeMap.put(d.getFuelType(),tmp,td);
			}

		}
		
		
		for(String et:monthMap.getXset()){
			etoi = new EngTypOtherItem();
			etoi.setEngTyp(et);
			etoi.setEngTypMo(new ArrayList<TypeData>(monthMap.getYMap(et).values()));
			etoi.setEngTypSs(new ArrayList<TypeData>(sitSizwMap.getYMap(et).values()));
			etoi.setEngTypEs(new ArrayList<TypeData>(entSMap.getYMap(et).values()));
			etoi.setEngTypLs(new ArrayList<TypeData>(tranDisMap.getYMap(et).values()));
			etoi.setEngTypCTs(new ArrayList<TypeData>(carTypeMap.getYMap(et).values()));
			engTypeOther.add(etoi);
		}
		
		return engTypeOther;
	}
	
	
	
	
	
}
