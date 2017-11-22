package service.app.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import service.app.server.SetService;

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
	public final static String TT_WATER_PORT = "港口生产";
	
	
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
	private final static String TN_CITY = "cities";
	
	
	public final static String TN_BUS_CL = "busCarLen";
	public final static String TN_TAXI_DP = "taxiDp";
	public final static String TN_ROAD_PASS_Sits = "roadPassSits";
	public final static String TN_ROAD_PASS_Ents = "roadPassEnts";
	public final static String TN_ROAD_PASS_Dis = "roadPassDis";
	
	public final static String TN_ROAD_GOODS_Ton = "roadGoodsTon";
	public final static String TN_ROAD_GOODS_Ents = "roadGoodsEnts";
	public final static String TN_ROAD_GOODS_Dis = "roadGoodsDis";

	public final static String TN_RIVER_Ents = "riverEnts";
	public final static String TN_RIVER_Ton = "riverTon";
	
	public final static String TN_OCEAN_GOODS_Ton = "oceanGoodsTon";
	public final static String TN_OCEAN_GOODS_Ents = "oceanGoodsEnts";
	public final static String TN_OCEAN_GOODS_Dis = "oceanGoodsDis";
	
	public final static String TN_OCEAN_PASS_Sits = "oceanPassSits";
	public final static String TN_OCEAN_PASS_Ents = "oceanPassEnts";
	public final static String TN_OCEAN_PASS_Dis = "oceanPassDis";
	
	public final static String TN_PORT_PRODUCT_Ents = "portProEnts";
	
	private final static String TN_REL_TIME_LAND_ENG = "relLandEngers";
	private final static String TN_REL_TIME_WAT_ENG = "relWatEngers";
	
	
	public final static String[] ALL_TRA_PP = {TT_LAND_PASS,TT_LAND_GOODS,TT_LAND_BUS,TT_LAND_TAXI,
            TT_WATER_RIVER,TT_WATER_PASS,TT_WATER_GOODS,TT_WATER_PORT};
	
	public final static String[] ALL_TRA = {TT_LAND_PASS,TT_LAND_GOODS,TT_LAND_BUS,TT_LAND_TAXI,
            TT_WATER_RIVER,TT_WATER_PASS,TT_WATER_GOODS};
	
	public final static String[] LAND_TRA = {TT_LAND_PASS,TT_LAND_GOODS,TT_LAND_BUS,TT_LAND_TAXI};
	
	public final static  String[] WATER_TRA = {TT_WATER_RIVER,TT_WATER_PASS,TT_WATER_GOODS};
	
	public final static  String[] WATER_TRA_PP = {TT_WATER_RIVER,TT_WATER_PASS,TT_WATER_GOODS,TT_WATER_PORT};
	
	
	
	@Autowired
	TypeChooser tc;
	
	@Autowired
	SetService ss;
	
	
	public boolean setTypeAll(String typeName,String typeS){
		return tc.put(typeName+":"+typeS);
	}
	
	
	public List<String> getLandEngers(){
		return tc.getAlltypes(TN_LAND_ENG);
	}
	
	public List<String> getWaterEngers(){
		return tc.getAlltypes(TN_WATER_ENG);
	}
	
	public List<String> getRelLandEngers(){
		return tc.getAlltypes(TN_REL_TIME_LAND_ENG);
	}
	
	public List<String> getRelWaterEngers(){
		return tc.getAlltypes(TN_REL_TIME_WAT_ENG);
	}
	
	
	
	public List<String> getTransTypesPP(){
		return tc.getAlltypes(TN_ALL_TRA_PP);
	}
	
	public List<String> getTransTypes(){
		return tc.getAlltypes(TN_ALL_TRA);
	}
	
	public List<String> getAllEngersTypes(){
		return tc.getAlltypes(TN_ALL_ENG);
	}
	
	
	public List<String> getCarTypes(){
		return tc.getAlltypes(TN_CAR);
	}
	
	public List<String> getShipTypes(){
		return tc.getAlltypes(TN_SHIP);
	}
	
	public List<String> getCitiesTypes(){
		return tc.getAlltypes(TN_CITY);
	}
	
	
	
	
	//道路客运客位数转客位类型
	public String getRoadPassSitCotType(int sitCot){
		//道路客运客位数转客位类型
		return tc.get(TN_ROAD_PASS_Sits, (double) sitCot);
	}
	
	public List<String> getRoadPassSitCotTypeAll(){
		return tc.getAlltypes(TN_ROAD_PASS_Sits);
	}
	

	
	
	
	//道路客运企业规模获取
	public String getRoadPassEntSizeType(int entS){
		return tc.get(TN_ROAD_PASS_Ents, (double) entS);
	}
	
	public List<String> getRoadPassEntSizeTypeAll(){
		return tc.getAlltypes(TN_ROAD_PASS_Ents);
	}
	

	
	
	
	public String getRoadPassDisType(Double tranDis){
		// TO-DO
		return tc.get(TN_ROAD_PASS_Dis, tranDis);
	}
	
	public List<String> getRoadPassDisTypeAll(){
		return tc.getAlltypes(TN_ROAD_PASS_Dis);
	}
	

	
	
	
	
	public String getRoadGoodsTonType(Double tonnage){
		return tc.get(TN_ROAD_GOODS_Ton, tonnage);
	}
	
	public List<String> getRoadGoodsTonTypeAll(){
		return tc.getAlltypes(TN_ROAD_GOODS_Ton);
	}
	

	
	
	
	public String getRoadGoodsEntSizeType(int entS){
		return tc.get(TN_ROAD_GOODS_Ents, (double) entS);
	}
	
	public List<String> getRoadGoodsEntSizeTypeAll(){
		return tc.getAlltypes(TN_ROAD_GOODS_Ents);
	}
	

	
	

	public String getRoadGoodsDisType(Double tranDis){
		//道路客运运距
		return tc.get(TN_ROAD_GOODS_Dis, tranDis);
	}
	
	public List<String> getRoadGoodsDisTypeAll(){
		return tc.getAlltypes(TN_ROAD_GOODS_Dis);
	}
	

	
	
	
	
	public String getBusTranCarLenType(Double carLength){
		//公交车车长
		return tc.get(TN_BUS_CL, carLength);
	}
	
	public List<String> getBusTranCarLenTypeAll(){
		return tc.getAlltypes(TN_BUS_CL);
	}
	

	
	
	
	public String getTaxiTranDpType(Double dpCot){
		//楚竹车排量
		return tc.get(TN_TAXI_DP, dpCot);
	}
	
	public List<String> getTaxiTranDpTypeAll(){
		return tc.getAlltypes(TN_TAXI_DP);
	}
	

	
	
	
	
	public String getRiverTranTonType(Double tonnage){
		return tc.get(TN_RIVER_Ton, tonnage);
	}
	
	public List<String> getRiverTranTonTypeAll(){
		return tc.getAlltypes(TN_RIVER_Ton);
	}
	

	
	
	
	
	public String getRiverTranEntSType(int entS){
		return tc.get(TN_RIVER_Ents, (double) entS);
	}
	
	
	public List<String> getRiverTranEntSTypeAll(){
		return tc.getAlltypes(TN_RIVER_Ents);
	}
	

	
	
	public String getOceanGoodsTonType(Double tonnage){
		return tc.get(TN_OCEAN_GOODS_Ton, tonnage);
	}
	
	public List<String> getOceanGoodsTonTypeAll(){
		return tc.getAlltypes(TN_OCEAN_GOODS_Ton);
	}
	

	

	
	public String getOceanGoodsEntSType(int entS){
		return  tc.get(TN_OCEAN_GOODS_Ents, (double) entS);
	}
	
	
	public List<String> getOceanGoodsEntSTypeAll(){
		return tc.getAlltypes(TN_OCEAN_GOODS_Ents);
	}
	

	
	
	
	public String getOceanGoodsTranDisType(Double tranDis){
		return tc.get(TN_OCEAN_GOODS_Dis, tranDis);
	}
	
	public List<String> getOceanGoodsTranDisTypeAll(){
		return tc.getAlltypes(TN_OCEAN_GOODS_Dis);
	}
	

	
	
	
	public String getOceanPassSitSizeType(int sitCot){
		return  tc.get(TN_OCEAN_PASS_Sits, (double) sitCot);
	}
	
	public List<String> getOceanPassSitSizeTypeAll(){
		return tc.getAlltypes(TN_OCEAN_PASS_Sits);
	}
	

	
	
	public String getOceanPassEntSType(int entS){
		return tc.get(TN_OCEAN_PASS_Ents, (double) entS);
	}
	
	public List<String> getOceanPassEntSTypeAll(){
		return tc.getAlltypes(TN_OCEAN_PASS_Ents);
	}
	

	
	public String getOceanPassTranDisType(Double tranDis){
		return  tc.get(TN_OCEAN_PASS_Dis, tranDis);
	}
	
	public List<String> getOceanPassTranDisTypeAll(){
		return tc.getAlltypes(TN_OCEAN_PASS_Dis);
	}
	

	
	
	
	public String getPortProEntSType(Double proTask){
		return tc.get(TN_PORT_PRODUCT_Ents, proTask);
	}
	
	public List<String> getPortProEntSTypeAll(){
		return tc.getAlltypes(TN_PORT_PRODUCT_Ents);
	}
	

	

	@Override
	public void afterPropertiesSet() throws Exception {
		
		setTypeAll(TN_BUS_CL,ss.getAllTypeByTypeName(TN_BUS_CL).getTypeS());
		setTypeAll(TN_TAXI_DP,ss.getAllTypeByTypeName(TN_TAXI_DP).getTypeS());
		
		//setBusTranCarLenTypeAll(TN_BUS_CL+":5-7,7-9,9-12,12-14,14-b");
		//setTaxiTranDpypeAll(TN_TAXI_DP+":a-1.6,1.6-2,2-2.5,2.5-3,3-b");
		
		setTypeAll(TN_ROAD_GOODS_Ents,ss.getAllTypeByTypeName(TN_ROAD_GOODS_Ents).getTypeS());
		setTypeAll(TN_ROAD_GOODS_Ton,ss.getAllTypeByTypeName(TN_ROAD_GOODS_Ton).getTypeS());
		setTypeAll(TN_ROAD_GOODS_Dis,ss.getAllTypeByTypeName(TN_ROAD_GOODS_Dis).getTypeS());

		
		
//		setRoadGoodsEntSizeTypeAll(TN_ROAD_GOODS_Ents+":5.2-7.2,7.2-9.2,9.2-12.2,12.2-14.2,14.2-b");
//		setRoadGoodsTonTypeAll(TN_ROAD_GOODS_Ton+":a-1.62,1.62-2.2,2.2-2.52,2.52-3.2,3.2-b");
//		setRoadGoodsDisTypeAll(TN_ROAD_GOODS_Dis+":a-40,40-80,80-120,120-b");
		
		setTypeAll(TN_ROAD_PASS_Ents,ss.getAllTypeByTypeName(TN_ROAD_PASS_Ents).getTypeS());
		setTypeAll(TN_ROAD_PASS_Sits,ss.getAllTypeByTypeName(TN_ROAD_PASS_Sits).getTypeS());
		setTypeAll(TN_ROAD_PASS_Dis,ss.getAllTypeByTypeName(TN_ROAD_PASS_Dis).getTypeS());

		
		
//		setTypeAll(TN_ROAD_PASS_Ents+":5.1-7.1,7.1-9.1,9.1-12.1,12.1-14.1,14.1-b");
//		setTypeAll(TN_ROAD_PASS_Sits+":5.11-7.11,7.11-9.11,9.11-12.11,12.11-14.11,14.11-b");
//		setTypeAll(TN_ROAD_PASS_Dis+":a-40,40-80,80-120,120-b");
		
		
		setTypeAll(TN_RIVER_Ents,ss.getAllTypeByTypeName(TN_RIVER_Ents).getTypeS());
		setTypeAll(TN_RIVER_Ton,ss.getAllTypeByTypeName(TN_RIVER_Ton).getTypeS());
		
//		setTypeAll(TN_RIVER_Ents+":5.3-7.3,7.3-9.3,9.3-12,12-14,14-b");
//		setTypeAll(TN_RIVER_Ton+":5.31-7.31,7.31-9.3,9.3-12,12-14,14-b");
		
		setTypeAll(TN_OCEAN_GOODS_Ents,ss.getAllTypeByTypeName(TN_OCEAN_GOODS_Ents).getTypeS());
		setTypeAll(TN_OCEAN_GOODS_Ton,ss.getAllTypeByTypeName(TN_OCEAN_GOODS_Ton).getTypeS());
		setTypeAll(TN_OCEAN_GOODS_Dis,ss.getAllTypeByTypeName(TN_OCEAN_GOODS_Dis).getTypeS());
		
//		setTypeAll(TN_OCEAN_GOODS_Ents+":5.4-7.4,7.4-9.4,9.4-12,12-14,14-b");
//		setTypeAll(TN_OCEAN_GOODS_Ton+":5.41-7.41,7.41-9,9-12,12-14,14-b");
//		setTypeAll(TN_OCEAN_GOODS_Dis+":a-40,40-80,80-120,120-b");
		
		setTypeAll(TN_OCEAN_PASS_Ents,ss.getAllTypeByTypeName(TN_OCEAN_PASS_Ents).getTypeS());
		setTypeAll(TN_OCEAN_PASS_Sits,ss.getAllTypeByTypeName(TN_OCEAN_PASS_Sits).getTypeS());
		setTypeAll(TN_OCEAN_PASS_Dis,ss.getAllTypeByTypeName(TN_OCEAN_PASS_Dis).getTypeS());
		
		
//		setTypeAll(TN_OCEAN_PASS_Ents+":5.5-7.5,7.5-9,9-12,12-14,14-b");
//		setTypeAll(TN_OCEAN_PASS_Sits+":5.51-7.51,7.51-9,9-12,12-14,14-b");
//		setTypeAll(TN_OCEAN_PASS_Dis+":a-40,40-80,80-120,120-b");
		
		
		setTypeAll(TN_PORT_PRODUCT_Ents,ss.getAllTypeByTypeName(TN_PORT_PRODUCT_Ents).getTypeS());
		
//		setTypeAll(TN_PORT_PRODUCT_Ents+":5.6-7.6,7.6-9.6,9.6-12,12-14,14-b");
		
		

		tc.setAllTypes(TN_ALL_TRA_PP, ALL_TRA_PP);
		
		tc.setAllTypes(TN_ALL_TRA, ALL_TRA);
		

		tc.setAllTypes(TN_LAND_TRA, LAND_TRA);
		
		tc.setAllTypes(TN_WATER_TRA, WATER_TRA_PP);
		
		
		String[] tmp4 = {FT_CHAI_YOU,FT_QI_YOU,FT_MEI_YOU,FT_DIAN_NENG,
						FT_HUN_HE,FT_QI_TA,FT_SHI_YOU_QI,FT_TIAN_RAN_QI};
		tc.setAllTypes(TN_ALL_ENG, tmp4);
		
		String[] tmp5 = {FT_CHAI_YOU,FT_QI_YOU,FT_DIAN_NENG,
						FT_HUN_HE,FT_SHI_YOU_QI,FT_TIAN_RAN_QI,FT_QI_TA};
		tc.setAllTypes(TN_LAND_ENG, tmp5);
		
		String[] tmp6 = {FT_CHAI_YOU,FT_QI_YOU,FT_MEI_YOU,FT_DIAN_NENG,
						FT_QI_TA};
		tc.setAllTypes(TN_WATER_ENG, tmp6);
		
		

		String[] tmp7 = readTypeFromFile("/carType");
		tc.setAllTypes(TN_CAR, tmp7);
		
		
		String[] tmp8 = readTypeFromFile("/shipType");		
		tc.setAllTypes(TN_SHIP, tmp8);
		
		String[] tmp9 = {"杭州","宁波","温州","嘉兴","湖州","绍兴","金华","衢州","舟山","台州","丽水"};
		tc.setAllTypes(TN_CITY, tmp9);
		
		String[] tmp10 = {FT_QI_YOU,FT_DIAN_NENG};
		tc.setAllTypes(TN_REL_TIME_LAND_ENG, tmp10);
		
		String[] tmp11 = {FT_QI_YOU};
		tc.setAllTypes(TN_REL_TIME_WAT_ENG, tmp11);
	}
	
	private String[] readTypeFromFile(String fileName){
		BufferedReader br;
		List<String> ss = new ArrayList<String>();
		try {
			br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(fileName),"UTF-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
			     if(!"".equals(line)) ss.add(line);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ss.toArray(new String[1] );
	}
	

}
