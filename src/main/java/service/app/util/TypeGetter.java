package service.app.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TypeGetter implements InitializingBean{
	
	//enger type
	public final static String FT_CHAI_YOU = "柴油";//柴油
	public final static String FT_QI_YOU = "汽油";//汽油
	public final static String FT_MEI_YOU = "煤油";//煤油
	public final static String FT_DIAN_NENG = "电能";//电力
	public final static String FT_HUN_HE = "混合燃料";//混合燃料
	public final static String FT_QI_TA = "其他";//其他类型
	public final static String FT_SHI_YOU_QI = "液化石油气";//石油气
	public final static String FT_TIAN_RAN_QI = "液化天然气";//天然气
	
	//traffic type
	public final static String TT_LAND_PASS = "道路客运";
	public final static String TT_LAND_GOODS = "道路货运";
	public final static String TT_LAND_BUS = "公交客运";
	public final static String TT_LAND_TAXI = "出租客运";
	public final static String TT_WATER_RIVER = "内河运输";
	public final static String TT_WATER_PASS = "海洋客运";
	public final static String TT_WATER_GOODS = "海洋货运";
	public final static String TT_WATER_PORT = "港口企业";
	
	//car type
	public final static String CT_C1 = "c1";
	public final static String CT_C2 = "c2";
	public final static String CT_C3 = "c3";
	public final static String CT_C4 = "c4";
	public final static String CT_C5 = "c5";
	public final static String CT_C6 = "c6";
	public final static String CT_C7 = "c6";

	//ship type
	public final static String ST_S1 = "s1";
	public final static String ST_S2 = "s2";
	public final static String ST_S3 = "s3";
	public final static String ST_S4 = "s4";
	public final static String ST_S5 = "s5";
	public final static String ST_S6 = "s6";
	public final static String ST_S7 = "s7";

	
	//类型名称
	private final static String TN_LAND_ENG = "landEngers";
	private final static String TN_LAND_TRA = "landTraffic";
	
	private final static String TN_WATER_ENG = "waterEngers";
	private final static String TN_WATER_TRA = "waterTraffic";
	
	private final static String TN_ALL_ENG = "allEngers";
	private final static String TN_ALL_TRA = "allTraffic";	
	private final static String TN_ALL_TRA_PP = "allTrafficPP";	//附加港口生产
	private final static String TN_CAR = "cars";
	private final static String TN_SHIP = "ship";
	
	
	private final static String TN_BUS_CL = "busCarLen";
	
	
	@Autowired
	TypeChooser tc;
	
	
	public List<String> getLandEngers(){
		return tc.getAlltypes(TN_LAND_ENG);
	}
	
	
	//道路客运客位数转客位类型
	public String getRoadPassSitCotType(int sitCot){
		//道路客运客位数转客位类型
		return "sitCot1";
	}
	
	public List<String> getRoadPassSitCotTypeAll(){
		return null;
	}
	
	public boolean setRoadPassSitCotTypeAll(String ts){
		return false;
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
		return tc.get(TN_BUS_CL, carLength);
	}
	
	public List<String> getBusTranCarLenTypeAll(){
		return tc.getAlltypes(TN_BUS_CL);
	}
	
	public boolean setBusTranCarLenTypeAll(String ts){
		return tc.put(ts);
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

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		setBusTranCarLenTypeAll(TN_BUS_CL+":5-7,7-9,9-12,12-14,14-b");
		
				
		String[] tmp = {TT_LAND_PASS,TT_LAND_GOODS,TT_LAND_BUS,TT_LAND_TAXI,
                TT_WATER_RIVER,TT_WATER_PASS,TT_WATER_GOODS,TT_WATER_PORT};
		tc.setAllTypes(TN_ALL_TRA_PP, tmp);
		
		String[] tmp1 = {TT_LAND_PASS,TT_LAND_GOODS,TT_LAND_BUS,TT_LAND_TAXI,
                TT_WATER_RIVER,TT_WATER_PASS,TT_WATER_GOODS};
		tc.setAllTypes(TN_ALL_TRA, tmp1);
		
		String[] tmp2 = {TT_LAND_PASS,TT_LAND_GOODS,TT_LAND_BUS,TT_LAND_TAXI};
		tc.setAllTypes(TN_LAND_TRA, tmp2);
		
		String[] tmp3 = {TT_WATER_RIVER,TT_WATER_PASS,TT_WATER_GOODS,TT_WATER_PORT};
		tc.setAllTypes(TN_WATER_TRA, tmp3);
		
		
		String[] tmp4 = {FT_CHAI_YOU,FT_QI_YOU,FT_MEI_YOU,FT_DIAN_NENG,
						FT_HUN_HE,FT_QI_TA,FT_SHI_YOU_QI,FT_TIAN_RAN_QI};
		tc.setAllTypes(TN_ALL_ENG, tmp4);
		
		String[] tmp5 = {FT_CHAI_YOU,FT_QI_YOU,FT_DIAN_NENG,
						FT_HUN_HE,FT_SHI_YOU_QI,FT_TIAN_RAN_QI};
		tc.setAllTypes(TN_LAND_ENG, tmp5);
		
		String[] tmp6 = {FT_CHAI_YOU,FT_QI_YOU,FT_MEI_YOU,FT_DIAN_NENG,
						FT_QI_TA};
		tc.setAllTypes(TN_WATER_ENG, tmp6);
		
		
		
		
		

		


		
	}
	

}
