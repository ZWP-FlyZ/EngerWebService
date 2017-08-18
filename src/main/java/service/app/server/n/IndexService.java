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
import service.app.callables.IndexDataWork;
import service.app.tramodel.RequestData;
import service.app.tramodel.TypeData;
import service.app.tramodel.items.EngTypOtherItem;
import service.app.util.TimeTools;
import service.app.util.TwoDecMap;
import service.app.worker.CalWorker;


@Service("index2")
public class IndexService {
	
	@Autowired
	CalWorker cw;
	
	private final static Logger logger = LoggerFactory.getLogger(IndexService.class);
	
	@SuppressWarnings("unchecked")
	public  Map<String,Object> getEngTypOther(RequestData rd){
		logger.debug("IndexService rd=["+rd.getAllSimMessage()+"]");
		
		List<BaseWork<RequestData,
			List<TwoDecMap<String, ? extends TypeData>>>> works = new ArrayList<>();
		
		List<String> YMs = TimeTools.getYMlist(rd.getTimeRange());
		RequestData tmp= null;
		List<List<TwoDecMap<String, ? extends TypeData>>> results = null;
		
		Map<String ,Object> map = new HashMap<String ,Object>();//return data
		List<EngTypOtherItem> engTypeOther = new ArrayList<>();
		
		
		if(YMs.size()>0) {
			YMs.set(YMs.size()-1, TimeTools.sqlitTimeRange(rd.getTimeRange())[1]);
			for(int i=0;i<YMs.size()-1;i++){
				tmp = rd.clone();
				if(tmp==null) throw new NullPointerException("克隆失败！");
				tmp.setTargeDay(YMs.get(i)+"-15");
				works.add(new IndexDataWork(tmp));
			}
			tmp = rd.clone();
			if(tmp==null) throw new NullPointerException("克隆失败！");
			tmp.setTargeDay(YMs.get(YMs.size()-1));
			works.add(new IndexDataWork(tmp));
			
			results = cw.submitWorkList(works);//wait for finish
			
			//logger.debug("results before"+new Gson().toJson(results));
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
			//logger.debug("One after"+new Gson().toJson(one));
			
			
			if(one.size()>0){
				om = (TwoDecMap<String, TypeData>) one.get(0);
				EngTypOtherItem etoi = null;
				
				for(String et:om.getXset()){
					etoi = new EngTypOtherItem();
					etoi.setBaseTyp(et);
					etoi.setEngTypMo(
							new ArrayList<TypeData>(om.getYMap(et).values()));
					engTypeOther.add(etoi);
				}
			}

		}// end YMs>0
		
		map.put("engTypeOther",engTypeOther);
		return map;
	}
		
	
	
}
