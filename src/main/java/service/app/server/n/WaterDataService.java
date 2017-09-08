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
import service.app.callables.OceanGoodsDataWork;
import service.app.callables.OceanPassDataWork;
import service.app.callables.PortProduceDataWork;
import service.app.callables.RiverTranDataWork;
import service.app.tramodel.RequestData;
import service.app.tramodel.TypeData;
import service.app.tramodel.items.BaseTypOtherItem;
import service.app.tramodel.items.EngTypOtherItem;
import service.app.tramodel.items.EntTypOtherItem;
import service.app.tramodel.items.WeiTypOtherItem;
import service.app.util.TimeTools;
import service.app.util.TwoDecMap;
import service.app.worker.CalWorker;

@Service("waterService")
public class WaterDataService {
	
	@Autowired
	CalWorker cw;
	
	
	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(WaterDataService.class);
	
	@SuppressWarnings("unchecked")
	public Map<String ,Object> getRiverTranTypeOther(RequestData rd){
		
		List<BaseWork<RequestData,
				List<TwoDecMap<String, ? extends TypeData>>>> works = new ArrayList<>();

		List<String> YMs = TimeTools.getYMlist(rd.getTimeRange());
		RequestData tmp= null;
		List<List<TwoDecMap<String, ? extends TypeData>>> results = null;
		
		
		
		List<EngTypOtherItem> engTypeOther = new ArrayList<>();
		List<EntTypOtherItem> entTypeOther = new ArrayList<>();
		List<WeiTypOtherItem> weiTypOther = new ArrayList<>();

		if(YMs.size()==0) throw new NullPointerException("年月列表为空");
		
		YMs.set(YMs.size()-1, TimeTools.sqlitTimeRange(rd.getTimeRange())[1]);
		for(int i=0;i<YMs.size()-1;i++){
			tmp = rd.clone();
			if(tmp==null) throw new NullPointerException("克隆失败！");
			tmp.setTargeDay(YMs.get(i)+"-15");
			works.add(new RiverTranDataWork(tmp));
		}
		tmp = rd.clone();
		if(tmp==null) throw new NullPointerException("克隆失败！");
		tmp.setTargeDay(YMs.get(YMs.size()-1));
		works.add(new RiverTranDataWork(tmp));
		
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
			
			WeiTypOtherItem wtoi = null;
			for(String et:om.getXset()){
				wtoi = new WeiTypOtherItem();
				wtoi.setBaseTyp(et);
				wtoi.setWeiTypSt(new ArrayList<TypeData>(om.getYMap(et).values()));
				weiTypOther.add(wtoi);
			}
			
		}//end 
		
		
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("engTypeOther",engTypeOther);
		map.put("entTypeOther",entTypeOther);
		map.put("weiTypOther",weiTypOther);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String ,Object> getOceanGoodsTypeOther(RequestData rd){
	
		List<BaseWork<RequestData,
			List<TwoDecMap<String, ? extends TypeData>>>> works = new ArrayList<>();

		List<String> YMs = TimeTools.getYMlist(rd.getTimeRange());
		RequestData tmp= null;
		List<List<TwoDecMap<String, ? extends TypeData>>> results = null;
		
		List<EngTypOtherItem> engTypeOther = new ArrayList<>();
		List<EntTypOtherItem> entTypeOther = new ArrayList<>();
		List<WeiTypOtherItem> weiTypOther = new ArrayList<>();


		if(YMs.size()==0) throw new NullPointerException("年月列表为空");
		
		YMs.set(YMs.size()-1, TimeTools.sqlitTimeRange(rd.getTimeRange())[1]);
		for(int i=0;i<YMs.size()-1;i++){
			tmp = rd.clone();
			if(tmp==null) throw new NullPointerException("克隆失败！");
			tmp.setTargeDay(YMs.get(i)+"-15");
			works.add(new OceanGoodsDataWork(tmp));
		}
		tmp = rd.clone();
		if(tmp==null) throw new NullPointerException("克隆失败！");
		tmp.setTargeDay(YMs.get(YMs.size()-1));
		works.add(new OceanGoodsDataWork(tmp));
		
		results = cw.submitWorkList(works);//wait for finish
		
		List<TwoDecMap<String, ? extends TypeData>> one = results.get(0);
		List<TwoDecMap<String, ? extends TypeData>> two = null;
		TwoDecMap<String,   TypeData> om = null;
		TwoDecMap<String,   TypeData> tm = null;
		TwoDecMap<String,   TypeData> sm = null;
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
			sm = (TwoDecMap<String, TypeData>) one.get(2);
			
			EngTypOtherItem etoi = null;
			for(String et:om.getXset()){
				etoi = new EngTypOtherItem();
				etoi.setBaseTyp(et);
				etoi.setEngTypMo(new ArrayList<TypeData>(om.getYMap(et).values()));
				etoi.setEngTypWs(new ArrayList<TypeData>(tm.getYMap(et).values()));
				etoi.setEngTypLs(new ArrayList<TypeData>(sm.getYMap(et).values()));
				engTypeOther.add(etoi);
			}
			
			om = (TwoDecMap<String, TypeData>) one.get(3);
			
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
			
			
			om = (TwoDecMap<String, TypeData>) one.get(4);
			WeiTypOtherItem wtoi = null;
			for(String et:om.getXset()){
				wtoi = new WeiTypOtherItem();
				wtoi.setBaseTyp(et);
				wtoi.setWeiTypSt(new ArrayList<TypeData>(om.getYMap(et).values()));
				weiTypOther.add(wtoi);
			}
			
		}//end
			
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("engTypeOther",engTypeOther);
		map.put("entTypeOther",entTypeOther);
		map.put("weiTypOther",weiTypOther);
		return map;
		
	}
	
	@SuppressWarnings("unchecked")
	public Map<String ,Object> getOceanPassTypeOther(RequestData rd){

		List<BaseWork<RequestData,
			List<TwoDecMap<String, ? extends TypeData>>>> works = new ArrayList<>();

		List<String> YMs = TimeTools.getYMlist(rd.getTimeRange());
		RequestData tmp= null;
		List<List<TwoDecMap<String, ? extends TypeData>>> results = null;
		
		List<EngTypOtherItem> engTypeOther = new ArrayList<>();
		List<EntTypOtherItem> entTypeOther = new ArrayList<>();
		List<BaseTypOtherItem> disTypOther = new ArrayList<>();
		
		
		if(YMs.size()==0) throw new NullPointerException("年月列表为空");
		
		YMs.set(YMs.size()-1, TimeTools.sqlitTimeRange(rd.getTimeRange())[1]);
		for(int i=0;i<YMs.size()-1;i++){
			tmp = rd.clone();
			if(tmp==null) throw new NullPointerException("克隆失败！");
			tmp.setTargeDay(YMs.get(i)+"-15");
			works.add(new OceanPassDataWork(tmp));
		}
		tmp = rd.clone();
		if(tmp==null) throw new NullPointerException("克隆失败！");
		tmp.setTargeDay(YMs.get(YMs.size()-1));
		works.add(new OceanPassDataWork(tmp));
		
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
			TypeData td = null;
			EntTypOtherItem ettoi = null;
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
			
		}//one
		
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("engTypeOther",engTypeOther);
		map.put("entTypeOther",entTypeOther);
		map.put("disTypOther",disTypOther);
		return map;
		
	}
	
	@SuppressWarnings("unchecked")
	public Map<String ,Object> getPortProductTypeOther(RequestData rd){

		List<BaseWork<RequestData,
			List<TwoDecMap<String, ? extends TypeData>>>> works = new ArrayList<>();

		List<String> YMs = TimeTools.getYMlist(rd.getTimeRange());
		RequestData tmp= null;
		List<List<TwoDecMap<String, ? extends TypeData>>> results = null;
		
		
		List<EngTypOtherItem> engTypeOther = new ArrayList<>();
		List<EntTypOtherItem> entTypeOther = new ArrayList<>();

		if(YMs.size()==0) throw new NullPointerException("年月列表为空");
		
		YMs.set(YMs.size()-1, TimeTools.sqlitTimeRange(rd.getTimeRange())[1]);
		for(int i=0;i<YMs.size()-1;i++){
			tmp = rd.clone();
			if(tmp==null) throw new NullPointerException("克隆失败！");
			tmp.setTargeDay(YMs.get(i)+"-15");
			works.add(new PortProduceDataWork(tmp));
		}
		tmp = rd.clone();
		if(tmp==null) throw new NullPointerException("克隆失败！");
		tmp.setTargeDay(YMs.get(YMs.size()-1));
		works.add(new PortProduceDataWork(tmp));
		
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
			EntTypOtherItem ettoi = null;
			TypeData td = null;
			for(String et:om.getXset()){
				etoi = new EngTypOtherItem();
				etoi.setBaseTyp(et);
				etoi.setEngTypMo(new ArrayList<TypeData>(om.getYMap(et).values()));
				engTypeOther.add(etoi);
			}
			for(String et:tm.getXset()){
				ettoi = new EntTypOtherItem();
				ettoi.setBaseTyp(et);
				td = tm.get(et, et);
				ettoi.setBaseTypDatOfAllEng(td.getTypDatOfAllEng());
				ettoi.setBaseTypDatOfAllLen(td.getTypDatOfAllLen());
				entTypeOther.add(ettoi);
			}
			
		}//end
		
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("engTypeOther",engTypeOther);
		map.put("entTypeOther",entTypeOther);
		return map;
	}
	
	
}
