package service.app.server;

import java.util.ArrayList;
import java.util.List;


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
import service.app.tramodel.EngTypOtherItem;
import service.app.tramodel.RequestData;
import service.app.tramodel.RoleType;
import service.app.tramodel.TypeData;
import service.app.util.TimeTools;
import service.app.util.TwoDecMap;

@Service
public class IndexService {

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
	
	
	public  List<EngTypOtherItem> getEngTypOther(RequestData rd){
		

		String[] times = 
				TimeTools.sqlitTimeRange(rd.getTimeRange());
		String[] places = new String[2];
		String enterprice=null;
		int k = 0x0;
		
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
			
		return selectAdd(times,places,enterprice,k);
	}
	
	
	
	private List<EngTypOtherItem> 
			selectAdd(String[] times,String[] places,String enterprice,int k ){
		List<AllSimData> allData = new ArrayList<>();
		
		
		if( (k&0x1)!=0 ){// land
//			List<RoadGoodsData> list1 = 
//					roadGdsDao.getRoadGdsAll(times[0], times[1], enterprice, places[0], places[1]);
//			if(list1!=null)
//				allData.addAll(list1);
//			List<RoadPassData> list2 = 
//					roadPassDao.getRoadPassAll(times[0], times[1], enterprice, places[0], places[1]);
//			if(list2!=null)
//				allData.addAll(list2);
//			List<BusTranData> list3 = 
//					busTranDao.getBusTranAll(times[0], times[1], enterprice, places[0], places[1]);
//			if(list3!=null)
//				allData.addAll(list3);
//			List<TaxiTranData> list4 = 
//					taxiTranDao.getTaxiTranAll(times[0], times[1], enterprice, places[0], places[1]);
//			if(list4!=null)
//				allData.addAll(list4);
			AllSimData ad1 = new AllSimData();
			ad1.setFuelType("et1");
			ad1.setGoTurn(1.0);
			ad1.setFuelCsption(1.0);
			ad1.setInTime("2017-01-01");
			allData.add(ad1);
			
			AllSimData ad2 = new AllSimData();
			ad2.setFuelType("et1");
			ad2.setGoTurn(1.0);
			ad2.setFuelCsption(1.0);
			ad2.setInTime("2017-01-01");
			allData.add(ad2);
			
			AllSimData ad3 = new AllSimData();
			ad3.setFuelType("et1");
			ad3.setGoTurn(1.0);
			ad3.setFuelCsption(1.0);
			ad3.setInTime("2017-01-01");
			allData.add(ad3);
			
			AllSimData ad4 = new AllSimData();
			ad4.setFuelType("et1");
			ad4.setGoTurn(1.0);
			ad4.setFuelCsption(1.0);
			ad4.setInTime("2017-01-01");
			allData.add(ad4);
		}
		
		if( (k&0x2)!=0 ){//water
			
//			List<RiverTranData> list1 = 
//					riverTranDao.getRiverTranAll(times[0], times[1], enterprice, places[0], places[1]);
//			if(list1!=null)
//				allData.addAll(list1);
//			List<OceanGoodsData> list2 = 
//					oceanGdsDao.getOceanGdsAll(times[0], times[1], enterprice, places[0], places[1]);
//			if(list2!=null)
//				allData.addAll(list2);
//			List<OceanPassData> list3 = 
//					oceanPassDao.getOceanPassAll(times[0], times[1], enterprice, places[0], places[1]);
//			if(list3!=null)
//				allData.addAll(list3);
//			List<PortProData> list4 = 
//					portProDao.getProtProAll(times[0], times[1], enterprice, places[0], places[1]);
//			if(list4!=null)
//				allData.addAll(list4);
			
			AllSimData ad1 = new AllSimData();
			ad1.setFuelType("et1");
			ad1.setGoTurn(2.0);
			ad1.setFuelCsption(1.5);
			ad1.setInTime("2017-01-01");
			allData.add(ad1);
			
			AllSimData ad2 = new AllSimData();
			ad2.setFuelType("et1");
			ad2.setGoTurn(2.0);
			ad2.setFuelCsption(1.5);
			ad2.setInTime("2017-01-01");
			allData.add(ad2);
			
			AllSimData ad3 = new AllSimData();
			ad3.setFuelType("et1");
			ad3.setGoTurn(2.0);
			ad3.setFuelCsption(1.5);
			ad3.setInTime("2017-01-01");
			allData.add(ad3);
			
			AllSimData ad4 = new AllSimData();
			ad4.setFuelType("et1");
			ad4.setGoTurn(2.0);
			ad4.setFuelCsption(1.5);
			ad4.setInTime("2017-01-01");
			allData.add(ad4);
			
		}
		
		
		List<EngTypOtherItem> engTypeOther = new ArrayList<>();
		TwoDecMap<String,TypeData> monthMap = new TwoDecMap<>();
		String tmp = null;
		TypeData td = null;
		EngTypOtherItem etoi = null;
		for(AllSimData d:allData){
			
			tmp = TimeTools.getYearMonth(d.getInTime());
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
		
		for(String et:monthMap.getXset()){
			etoi = new EngTypOtherItem();
			etoi.setEngTyp(et);
			etoi.setEngTypMo(
					new ArrayList<TypeData>(monthMap.getYMap(et).values()));
			engTypeOther.add(etoi);
		}
		
		return engTypeOther;
	}
	
	
	
	
	
	
	
	
}
