package service.app.dataservice;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import service.app.dao.BusTranDao;
import service.app.dao.OceanGdsDao;
import service.app.dao.OceanPassDao;
import service.app.dao.PortProDao;
import service.app.dao.RelTimeCarDao;
import service.app.dao.RelTimeShipDao;
import service.app.dao.RiverTranDao;
import service.app.dao.RoadGdsDao;
import service.app.dao.RoadPassDao;
import service.app.dao.TaxiTranDao;
import service.app.model.AllSimData;
import service.app.model.RelTimeSelctParam;
import service.app.nosql.CacheData;
import service.app.nosql.ResultRepository;
import service.app.nosql.TYMRepository;
import service.app.util.MyLRU;
import service.app.util.TimeTools;
import service.app.util.TypeGetter;

@Service
public class LruDataService {
	

	
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
	
	@Autowired
	RelTimeCarDao carDao;
	
	@Autowired
	RelTimeShipDao shipDao;
	
	@Autowired
	ResultRepository resultRepo;
	
	@Autowired 
	TYMRepository tymRepo;
	
	
	private final static MyLRU<String, String> mTrYeMoCache = new MyLRU<>(8*3*12);//最多保存8种能耗3年数据
	private final static MyLRU<String, String> mTrYeMoDaCache = new MyLRU<>(32);//最多保存32个   月数据
	
	private final static MyLRU<String, String> mRelTimTrYeMoDaCache = new MyLRU<>(5*10);//最多保存5中能耗 10个      一天内实时数据
	private final static MyLRU<String, String> mRelTimTrYeMoDaHoCache = new MyLRU<>(25);//最多保存5中能耗 10个      一天内实时数据
	
	private final static Logger logger = LoggerFactory.getLogger(LruDataService.class);
	
	/**
	 * 
	 * @param tranType
	 * @param YM
	 * @return
	 * 
	 * YM 必须小于当前年月
	 * 
	 */
	public List<? extends AllSimData> getTYMCacheDataBefore(String tranType,String YM){
		if(tranType==null || YM==null ) return new ArrayList<>();
		List<? extends AllSimData> tmpData = null;
		String cacheName = tranType+"_"+YM;
		String tc = mTrYeMoCache.get(cacheName);
		if(tc!=null){
			CacheData tymd = tymRepo.findOne(cacheName.hashCode()+"");
			if(tymd!=null){
				tmpData = tymd.data;
				logger.debug("Get cache! Cache name =["+cacheName+"]");
				return tmpData;
			}
		}
		tmpData  = getDataFromMySql(tranType,YM);
		tymRepo.save(new CacheData(cacheName,tmpData));
		logger.debug("Don't have cache! name =["+cacheName+"]");
		tc = mTrYeMoCache.add(cacheName, cacheName);
		if(tc!=null){
			tymRepo.delete(tc.hashCode()+"");
			logger.debug("LRU(mTrYeMoCache) full! Cache["+tc+"] deleted");
		}
		return tmpData;
	}
	
	
	
	/**
	 * 
	 * @param tranType
	 * @param YMD
	 * @return 
	 *  YMD 必须小于当前日期
	 */
	public List<? extends AllSimData> getTYMCacheDataNow(String tranType,String YMD){
		if(tranType==null || YMD==null) return new ArrayList<>();
		List<? extends AllSimData> tmpData = null;
		String cacheName = tranType+"_"+YMD;
		String tc = mTrYeMoDaCache.get(cacheName);
		if(tc!=null){
			CacheData tymd = tymRepo.findOne(cacheName.hashCode()+"");
			if(tymd!=null){
				tmpData = tymd.data;
				logger.debug("Get cache! Cache name =["+cacheName+"]");
				return tmpData;
			}
		}
		tmpData  = getDataFromMySql(tranType,TimeTools.getYearMonth(YMD));
		tymRepo.save(new CacheData(cacheName,tmpData));
		logger.debug("Don't have cache! name =["+cacheName+"]");
		tc = mTrYeMoDaCache.add(cacheName, cacheName);
		if(tc!=null){
			tymRepo.delete(tc.hashCode()+"");
			logger.debug("LRU(mTrYeMoDaCache) full! Cache["+tc+"] deleted");
		}
		return tmpData;
	}
	
	
	public List<? extends AllSimData> getRelTimDataCacheBefore(String tranType,String YMD){
		if(tranType==null || YMD==null) return new ArrayList<>();
		List<? extends AllSimData> tmpData = null;
		String cacheName = tranType+"_"+YMD+"_R";
		String tc = mRelTimTrYeMoDaCache.get(cacheName);
		if(tc!=null){
			CacheData tymd = tymRepo.findOne(cacheName.hashCode()+"");
			if(tymd!=null){
				tmpData = tymd.data;
				logger.debug("Get cache! Cache name =["+cacheName+"]");
				return tmpData;
			}
		}
		tmpData  = getRelTimeDataFromMySql(tranType,YMD);
		tymRepo.save(new CacheData(cacheName,tmpData));
		logger.debug("Don't have cache! name =["+cacheName+"]");
		tc = mRelTimTrYeMoDaCache.add(cacheName, cacheName);
		if(tc!=null){
			tymRepo.delete(tc.hashCode()+"");
			logger.debug("LRU(mRelTimTrYeMoDaCache) full! Cache["+tc+"] deleted");
		}
		return tmpData;
	}
	
	public List<? extends AllSimData> getRelTimDataCacheNow(String tranType,String YMDH){
		if(tranType==null || YMDH==null) return new ArrayList<>();
		List<? extends AllSimData> tmpData = null;
		String cacheName = tranType+"_"+YMDH+"_R";
		String tc = mRelTimTrYeMoDaHoCache.get(cacheName);
		if(tc!=null){
			CacheData tymd = tymRepo.findOne(cacheName.hashCode()+"");
			if(tymd!=null){
				tmpData = tymd.data;
				logger.debug("Get cache! Cache name =["+cacheName+"]");
				return tmpData;
			}
		}
		tmpData  = getRelTimeDataFromMySql(tranType,YMDH.substring(0,10));
		tymRepo.save(new CacheData(cacheName,tmpData));
		logger.debug("Don't have cache! name =["+cacheName+"]");
		tc = mRelTimTrYeMoDaHoCache.add(cacheName, cacheName);
		if(tc!=null){
			tymRepo.delete(tc.hashCode()+"");
			logger.debug("LRU(mRelTimTrYeMoDaHoCache) full! Cache["+tc+"] deleted");
		}
		return tmpData;
	}
	
	public  List<? extends AllSimData> getDataFromMySql(String tranType,String YM){
		String start = YM+"-01";
		String eng = YM+"-"+TimeTools.getMaxDayOfMonth(YM);
		List<? extends AllSimData> alld = null;
		if(tranType.equals(TypeGetter.TT_LAND_PASS)){
			alld = roadPassDao.getRoadPassAllN(start,eng);
		}else if(tranType.equals(TypeGetter.TT_LAND_GOODS)){
			alld = roadGdsDao.getRoadGdsAllN(start,eng);
		}else if(tranType.equals(TypeGetter.TT_LAND_BUS)){
			alld = busTranDao.getBusTranAllN(start,eng);
		}else if(tranType.equals(TypeGetter.TT_LAND_TAXI)){
			alld = taxiTranDao.getTaxiTranN(start,eng);
		}else if(tranType.equals(TypeGetter.TT_WATER_PASS)){
			alld = oceanPassDao.getOceanPassAllN(start,eng);
		}else if(tranType.equals(TypeGetter.TT_WATER_GOODS)){
			alld = oceanGdsDao.getOceanGdsAllN(start,eng);
		}else if(tranType.equals(TypeGetter.TT_WATER_RIVER)){
			alld = riverTranDao.getRiverTranAllN(start,eng);
		}else if(tranType.equals(TypeGetter.TT_WATER_PORT)){
			alld = portProDao.getProtProAllN(start,eng);
		}
		return alld;
	}
	
	public  List<? extends AllSimData> getRelTimeDataFromMySql(String tranType,String YMD){
		String TNAME_ROAD_PASS = "roadpassrel";
		String TNAME_ROAD_GOODS = "roadgoodsrel";
		String TNAME_BUS = "bustranrel";
		String TNAME_TAXI = "taxitranrel";
		String TNAME_RIVER = "rivertranrel";
		
		List<? extends AllSimData> list = null;
		RelTimeSelctParam param = new RelTimeSelctParam();
		param.setStartTime(YMD+" 00:00:00");
		param.setEndTime(YMD+" 23:59:59");
	
		if(tranType.equals(TypeGetter.TT_LAND_PASS)){
			list = carDao.getRelTimeDataN(TNAME_ROAD_PASS, param);
		}else if(tranType.equals(TypeGetter.TT_LAND_GOODS)){
			list = carDao.getRelTimeDataN(TNAME_ROAD_GOODS, param);
		}else if(tranType.equals(TypeGetter.TT_LAND_BUS)){
			list = carDao.getRelTimeDataN(TNAME_BUS, param);
		}else if(tranType.equals(TypeGetter.TT_LAND_TAXI)){
			list = carDao.getRelTimeDataN(TNAME_TAXI, param);
		}else if(tranType.equals(TypeGetter.TT_WATER_RIVER)){
			list = shipDao.getRelTimeDataN(TNAME_RIVER, param);
		}else
			list = new ArrayList<>();
		return list;
	}
	
	
	

}
