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
import service.app.tramodel.items.EngTypOtherItem;
import service.app.util.TimeTools;
import service.app.util.TwoDecMap;
import service.app.util.TypeGetter;

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
	
	
	public  Map<String,Object> getEngTypOther(RequestData rd){
		

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
			
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("engTypeOther",selectAdd(times,places,enterprice,k));
		
		return map;
	}
	
	
	
	private List<EngTypOtherItem> 
			selectAdd(String[] times,String[] places,String enterprice,int k ){
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
			//td.addLen(d.getGoTurn());
			monthMap.put(d.getFuelType(),tmp,td);
		}
		
		
		if( (k&0x2)!=0 ){// 处理港口企业数据
			List<PortProData> lp = 
					portProDao.getProtProAll(times[0], times[1], enterprice, places[0], places[1]);
					if(lp!=null)
						allData.addAll(lp);
					
			for(PortProData d:lp){
				//month
				tmp = TimeTools.getYearMonth(d.getInTime());
				if(tmp!=null){
					td = monthMap.get(TypeGetter.CHAI_YOU,tmp);//柴油
					if(td==null)
					{
						td = new TypeData();
						td.setType(tmp);
					}
					td.addEng(d.getDiesel());

					monthMap.put(TypeGetter.CHAI_YOU,tmp,td);
					
					td = monthMap.get(TypeGetter.QI_YOU,tmp);
					if(td==null)
					{
						td = new TypeData();
						td.setType(tmp);
					}
					td.addEng(d.getGasoline());

					monthMap.put(TypeGetter.QI_YOU,tmp,td);
					
					td = monthMap.get(TypeGetter.MEI_YOU,tmp);
					if(td==null)
					{
						td = new TypeData();
						td.setType(tmp);
					}
					td.addEng(d.getCoal());

					monthMap.put(TypeGetter.MEI_YOU,tmp,td);
					
					td = monthMap.get(TypeGetter.DIAN_LI,tmp);
					if(td==null)
					{
						td = new TypeData();
						td.setType(tmp);
					}
					td.addEng(d.getPower());

					monthMap.put(TypeGetter.DIAN_LI,tmp,td);
					
					td = monthMap.get(TypeGetter.QI_TA,tmp);
					if(td==null)
					{
						td = new TypeData();
						td.setType(tmp);
					}
					td.addEng(d.getOther());
					monthMap.put(TypeGetter.QI_TA,tmp,td);
					
				}
			}
		}
		

		
		for(String et:monthMap.getXset()){
			etoi = new EngTypOtherItem();
			etoi.setBaseTyp(et);
			etoi.setEngTypMo(
					new ArrayList<TypeData>(monthMap.getYMap(et).values()));
			engTypeOther.add(etoi);
		}
		
		return engTypeOther;
	}
	
	
	
	
	
	
	
	
}
