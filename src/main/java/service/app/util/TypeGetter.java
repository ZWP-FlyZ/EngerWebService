package service.app.util;

import org.springframework.stereotype.Component;

@Component
public class TypeGetter {
	
	public final static String CHAI_YOU = "f1";//柴油
	public final static String QI_YOU = "f2";//汽油
	public final static String MEI_YOU = "f4";//煤油
	public final static String DIAN_LI = "f6";//电力
	public final static String HUN_HE = "f13";//混合燃料
	public final static String QI_TA = "f7";//其他类型
	public final static String SHI_YOU_QI = "f8";//石油气
	public final static String TIAN_RAN_QI = "f9";//天然气
	
	
	public final static String LAND_PASS = "t1";
	public final static String LAND_GOODS = "t2";
	public final static String LAND_BUS = "t3";
	public final static String LAND_TAXI = "t4";
	public final static String WATER_RIVER = "t5";
	public final static String WATER_PASS = "t7";
	public final static String WATER_GOODS = "t6";
	public final static String WATER_PORT = "t8";
	
	
	
	//道路客运客位数转客位类型
	public String getRoadPassSitCotType(int sitCot){
		//道路客运客位数转客位类型
		return "sitCot1";
	}
	
	//道路客运企业规模获取
	public String getRoadPassEntSizeType(int entS){
		return "entS1";
	}
	
	public String getRoadPassDisType(Double tranDis){
		//道路客运运距
		return "tranDis1";
	}
	
	public String getRoadGoodsTonType(Double tonnage){
		return "tonnage1";
	}
	
	
	public String getRoadGoodsEntSizeType(int entS){
		return "entS1";
	}
	
	public String getRoadGoodsDisType(Double tranDis){
		//道路客运运距
		return "tranDis1";
	}
	
	
	public String getBusTranCarLenType(Double carLength){
		//公交车车长
		return "carLength1";
	}
	
	public String getTaxiTranDpType(Double dpCot){
		//楚竹车排量
		return "dpCot1";
	}
	
	public String getRiverTranTonType(Double tonnage){
		return "tonnage2";
	}
	
	public String getRiverTranEntSType(int entS){
		return "entS2";
	}
	
	public String getOceanGoodsTonType(Double tonnage){
		return "tonnage2";
	}
	
	public String getOceanGoodsEntSType(int entS){
		return "entS2";
	}
	
	public String getOceanGoodsTranDisType(Double tranDis){
		return "tranDis2";
	}
	
	public String getOceanPassSitSizeType(int sitCot){
		return "sitSize";
	}
	
	public String getOceanPassEntSType(int entS){
		return "entS2";
	}
	
	public String getOceanPassTranDisType(Double tranDis){
		return "tranDis2";
	}
	
	public String getPortProEntSType(Double proTask){
		return "proTask1";
	}
	
	
	
	
	
	
	
	
	
	
	

}
