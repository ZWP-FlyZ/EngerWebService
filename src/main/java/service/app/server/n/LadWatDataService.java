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
import service.app.callables.CityTraDataWork;
import service.app.callables.EngYearDataWork;
import service.app.callables.TraCityDataWork;
import service.app.callables.TraPerDisDataWork;
import service.app.callables.TraTypPerYearWork;
import service.app.tramodel.RequestData;
import service.app.tramodel.TypeData;
import service.app.tramodel.items.CitTypOtherItem;
import service.app.tramodel.items.EngTypOtherItem;
import service.app.tramodel.items.TraTypOtherItem;
import service.app.util.TimeTools;
import service.app.util.TwoDecMap;
import service.app.worker.CalWorker;

@Service("lWService")
public class LadWatDataService {

	
	@Autowired
	CalWorker cw;
	
	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(LadWatDataService.class);
	
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> getPerDisEngTypOther(RequestData rd){
		
		List<BaseWork<RequestData,
			List<TwoDecMap<String, ? extends TypeData>>>> works = new ArrayList<>();

		List<String> YMs = TimeTools.getYMlist(rd.getTimeRange());
		RequestData tmp= null;
		List<List<TwoDecMap<String, ? extends TypeData>>> results = null;
		
		
		List<TraTypOtherItem> traTypeOther = new ArrayList<>();
		
		if(YMs.size()==0) throw new NullPointerException("年月列表为空");
		
		YMs.set(YMs.size()-1, TimeTools.sqlitTimeRange(rd.getTimeRange())[1]);
		for(int i=0;i<YMs.size()-1;i++){
			tmp = rd.clone();
			if(tmp==null) throw new NullPointerException("克隆失败！");
			tmp.setTargeDay(YMs.get(i)+"-15");
			works.add(new TraPerDisDataWork(tmp));
		}
		tmp = rd.clone();
		if(tmp==null) throw new NullPointerException("克隆失败！");
		tmp.setTargeDay(YMs.get(YMs.size()-1));
		works.add(new TraPerDisDataWork(tmp));
		
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
			TraTypOtherItem ttoi = null;	
			for(String et:om.getXset()){
				ttoi = new TraTypOtherItem();
				ttoi.setBaseTyp(et);
				ttoi.setTraTypEt(new ArrayList<TypeData>(om.getYMap(et).values()));
				traTypeOther.add(ttoi);
			}
		}//end
		
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("traTypeOther",traTypeOther);
		
		return map;
	}
	@SuppressWarnings("unchecked")
	public Map<String,Object> getEngTyp3YearTypOther(RequestData rd){
		
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
			works.add(new EngYearDataWork(tmp));
		}
		tmp = rd.clone();
		if(tmp==null) throw new NullPointerException("克隆失败！");
		tmp.setTargeDay(YMs.get(YMs.size()-1));
		works.add(new EngYearDataWork(tmp));
		
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
			
			EngTypOtherItem ttoi = null;
			for(String et:om.getXset()){
				ttoi = new EngTypOtherItem();
				ttoi.setBaseTyp(et);
				ttoi.setEngTypMo(new ArrayList<TypeData>(om.getYMap(et).values()));
				engTypeOther.add(ttoi);
			}	
		}//end
		
	
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("engTypeOther",engTypeOther);
		
		return map;
	}
	
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> getTraTypPerYearTypOther(RequestData rd){
		
	
		List<BaseWork<RequestData,
			List<TwoDecMap<String, ? extends TypeData>>>> works = new ArrayList<>();

		List<String> YMs = TimeTools.getYMlist(rd.getTimeRange());
		RequestData tmp= null;
		List<List<TwoDecMap<String, ? extends TypeData>>> results = null;
		
		List<TraTypOtherItem> traTypeOther = new ArrayList<>();
	
		
		if(YMs.size()==0) throw new NullPointerException("年月列表为空");
		
		YMs.set(YMs.size()-1, TimeTools.sqlitTimeRange(rd.getTimeRange())[1]);
		for(int i=0;i<YMs.size()-1;i++){
			tmp = rd.clone();
			if(tmp==null) throw new NullPointerException("克隆失败！");
			tmp.setTargeDay(YMs.get(i)+"-15");
			works.add(new TraTypPerYearWork(tmp));
		}
		tmp = rd.clone();
		if(tmp==null) throw new NullPointerException("克隆失败！");
		tmp.setTargeDay(YMs.get(YMs.size()-1));
		works.add(new TraTypPerYearWork(tmp));
		
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
			
			TraTypOtherItem ttoi = null;
			for(String et:om.getXset()){
				ttoi = new TraTypOtherItem();
				ttoi.setBaseTyp(et);
				ttoi.setTraTypMo(new ArrayList<TypeData>(om.getYMap(et).values()));
				traTypeOther.add(ttoi);
			}	
		}//end one
		
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("traTypeOther",traTypeOther);
		
		return map;
	}
	@SuppressWarnings("unchecked")
	public Map<String,Object> getCitTranTypOther(RequestData rd){
		
	
		List<BaseWork<RequestData,
			List<TwoDecMap<String, ? extends TypeData>>>> works = new ArrayList<>();

		List<String> YMs = TimeTools.getYMlist(rd.getTimeRange());
		RequestData tmp= null;
		List<List<TwoDecMap<String, ? extends TypeData>>> results = null;
		
		List<CitTypOtherItem> citTypeOther = new ArrayList<>();
		
		if(YMs.size()==0) throw new NullPointerException("年月列表为空");
		
		YMs.set(YMs.size()-1, TimeTools.sqlitTimeRange(rd.getTimeRange())[1]);
		for(int i=0;i<YMs.size()-1;i++){
			tmp = rd.clone();
			if(tmp==null) throw new NullPointerException("克隆失败！");
			tmp.setTargeDay(YMs.get(i)+"-15");
			works.add(new CityTraDataWork(tmp));
		}
		tmp = rd.clone();
		if(tmp==null) throw new NullPointerException("克隆失败！");
		tmp.setTargeDay(YMs.get(YMs.size()-1));
		works.add(new CityTraDataWork(tmp));
		
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
			
			CitTypOtherItem ctoi = null;
			for(String et:om.getXset()){
				ctoi = new CitTypOtherItem();
				ctoi.setBaseTyp(et);
				ctoi.setBaseTypDatOfAllEng(om.get(et, et).getTypDatOfAllEng());
				citTypeOther.add(ctoi);
			}	
			
		}//end
		
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("citTypeOther",citTypeOther);
		
		return map;
	}
	@SuppressWarnings("unchecked")
	public Map<String,Object> getTranCitTypOther(RequestData rd){
		
		List<BaseWork<RequestData,
			List<TwoDecMap<String, ? extends TypeData>>>> works = new ArrayList<>();

		List<String> YMs = TimeTools.getYMlist(rd.getTimeRange());
		RequestData tmp= null;
		List<List<TwoDecMap<String, ? extends TypeData>>> results = null;
	
		List<TraTypOtherItem> traTypeOther = new ArrayList<>();

		if(YMs.size()==0) throw new NullPointerException("年月列表为空");
		
		YMs.set(YMs.size()-1, TimeTools.sqlitTimeRange(rd.getTimeRange())[1]);
		for(int i=0;i<YMs.size()-1;i++){
			tmp = rd.clone();
			if(tmp==null) throw new NullPointerException("克隆失败！");
			tmp.setTargeDay(YMs.get(i)+"-15");
			works.add(new TraCityDataWork(tmp));
		}
		tmp = rd.clone();
		if(tmp==null) throw new NullPointerException("克隆失败！");
		tmp.setTargeDay(YMs.get(YMs.size()-1));
		works.add(new TraCityDataWork(tmp));
		
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
			TraTypOtherItem ttoi = null;
			for(String et:om.getXset()){
				ttoi = new TraTypOtherItem();
				ttoi.setBaseTyp(et);
				ttoi.setBaseTypDatOfAllEng( om.get(et,et).getTypDatOfAllEng());
				traTypeOther.add(ttoi);
			}
		}//end
		
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("traTypeOther",traTypeOther);
		
		return map;
	}
	
	
	

	
	
}
