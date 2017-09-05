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
import service.app.callables.BusTranDataWork;
import service.app.callables.RoadGoodsDataWork;
import service.app.callables.RoadPassDataWork;
import service.app.callables.TaxiTranDataWork;
import service.app.tramodel.RequestData;
import service.app.tramodel.TypeData;
import service.app.tramodel.items.BaseTypOtherItem;
import service.app.tramodel.items.CarTypOtherItem;
import service.app.tramodel.items.EngTypOtherItem;
import service.app.tramodel.items.EntTypOtherItem;
import service.app.util.TimeTools;
import service.app.util.TwoDecMap;
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

	@SuppressWarnings("unchecked")
	public  Map<String,Object> getRoadGoodsTypOther(RequestData rd){
	
		List<BaseWork<RequestData,
		List<TwoDecMap<String, ? extends TypeData>>>> works = new ArrayList<>();
	
		List<String> YMs = TimeTools.getYMlist(rd.getTimeRange());
		RequestData tmp= null;
		List<List<TwoDecMap<String, ? extends TypeData>>> results = null;
		
		List<EngTypOtherItem> engTypeOther = new ArrayList<>();
		List<EntTypOtherItem> entTypeOther = new ArrayList<>();
		List<CarTypOtherItem> carTypOther = new ArrayList<>();
		
		if(YMs.size()==0) throw new NullPointerException("年月列表为空");
		
		YMs.set(YMs.size()-1, TimeTools.sqlitTimeRange(rd.getTimeRange())[1]);
		for(int i=0;i<YMs.size()-1;i++){
			tmp = rd.clone();
			if(tmp==null) throw new NullPointerException("克隆失败！");
			tmp.setTargeDay(YMs.get(i)+"-15");
			works.add(new RoadGoodsDataWork(tmp));
		}
		tmp = rd.clone();
		if(tmp==null) throw new NullPointerException("克隆失败！");
		tmp.setTargeDay(YMs.get(YMs.size()-1));
		works.add(new RoadGoodsDataWork(tmp));
		
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
				etoi.setEngTypWs(new ArrayList<TypeData>(tm.getYMap(et).values()));
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
			CarTypOtherItem ctoi = null;
			for(String et:om.getXset()){
				ctoi = new CarTypOtherItem();
				ctoi.setBaseTyp(et);
				ctoi.setCarTypWs(new ArrayList<TypeData>(om.getYMap(et).values()));
				carTypOther.add(ctoi);
			}
			
		}//end size
		
		
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("engTypeOther",engTypeOther);
		map.put("entTypeOther",entTypeOther);
		map.put("carTypOther",carTypOther);
		return map;
	}






	@SuppressWarnings("unchecked")
	public  Map<String,Object> getBusTranTypOther(RequestData rd){
	
	
		List<BaseWork<RequestData,
			List<TwoDecMap<String, ? extends TypeData>>>> works = new ArrayList<>();
	
		List<String> YMs = TimeTools.getYMlist(rd.getTimeRange());
		RequestData tmp= null;
		List<List<TwoDecMap<String, ? extends TypeData>>> results = null;
		
	
		List<EngTypOtherItem> engTypeOther = new ArrayList<>();
	
		
		if(YMs.size()==0) throw new NullPointerException("年月列表为空");
		
		YMs.set(YMs.size()-1, TimeTools.sqlitTimeRange(rd.getTimeRange())[1]);
		for(int i=0;i<YMs.size()-1;i++){
			tmp = rd.clone();
			if(tmp==null) throw new NullPointerException("克隆失败！");
			tmp.setTargeDay(YMs.get(i)+"-15");
			works.add(new BusTranDataWork(tmp));
		}
		tmp = rd.clone();
		if(tmp==null) throw new NullPointerException("克隆失败！");
		tmp.setTargeDay(YMs.get(YMs.size()-1));
		works.add(new BusTranDataWork(tmp));
		
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
				etoi.setEngTypCLs(new ArrayList<TypeData>(tm.getYMap(et).values()));
				engTypeOther.add(etoi);
			}
		}//end one
		
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("engTypeOther",engTypeOther);
		return map;
	}

	@SuppressWarnings("unchecked")
	public  Map<String,Object> getTaxiTranTypOther(RequestData rd){
		
		
		List<BaseWork<RequestData,
				List<TwoDecMap<String, ? extends TypeData>>>> works = new ArrayList<>();
		
		List<String> YMs = TimeTools.getYMlist(rd.getTimeRange());
		RequestData tmp= null;
		List<List<TwoDecMap<String, ? extends TypeData>>> results = null;
		
		List<EngTypOtherItem> engTypeOther = new ArrayList<>();
	
		if(YMs.size()==0) throw new NullPointerException("年月列表为空");
		
		YMs.set(YMs.size()-1, TimeTools.sqlitTimeRange(rd.getTimeRange())[1]);
		for(int i=0;i<YMs.size()-1;i++){
			tmp = rd.clone();
			if(tmp==null) throw new NullPointerException("克隆失败！");
			tmp.setTargeDay(YMs.get(i)+"-15");
			works.add(new TaxiTranDataWork(tmp));
		}
		tmp = rd.clone();
		if(tmp==null) throw new NullPointerException("克隆失败！");
		tmp.setTargeDay(YMs.get(YMs.size()-1));
		works.add(new TaxiTranDataWork(tmp));
		
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
				etoi.setEngTypPs(new ArrayList<TypeData>(tm.getYMap(et).values()));
				engTypeOther.add(etoi);
			}
			
		}//end one

		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("engTypeOther",engTypeOther);
		return map;
	}

	
	
	
}
