package service.app.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.app.server.WaterDataService;
import service.app.tramodel.ErrCode;
import service.app.tramodel.RequestData;
import service.app.tramodel.RoleType;
import service.app.tramodel.items.BaseTypOtherItem;
import service.app.tramodel.items.EngTypOtherItem;
import service.app.tramodel.items.EntTypOtherItem;
import service.app.tramodel.items.WeiTypOtherItem;
import service.app.tramodel.response.OceanGoodsResponse;
import service.app.tramodel.response.OceanPassResponse;
import service.app.tramodel.response.PortProductResponse;
import service.app.tramodel.response.RiverTranResponse;
import service.app.util.TimeTools;
import service.app.util.TypeGetter;

@Controller
public class WaterController {

	@Autowired
	WaterDataService wds;
	
	@Autowired
	TypeGetter tg;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/rivertranO.json")
	@ResponseBody
	public RiverTranResponse riverTran(HttpServletResponse response,
										RequestData rd){
//		rd.setUsername("zwp");
//		rd.setRoleName("enterprice");
//		rd.setRoleType(RoleType.ROLE_TRAFFIC);
//		rd.setTimeRange("2017-01-01:2017-12-30");
//		rd.setPlace1("杭州");
//		rd.setPlace2("江干");
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		RiverTranResponse trt = new RiverTranResponse();
		
		trt.setErrCode(ErrCode.DATA_OK);
		trt.setRoleName(rd.getRoleName());
		trt.setTimeRange(rd.getTimeRange());
		trt.getXs().add(TimeTools.getYMlist(rd.getTimeRange()));
		trt.getXs().add(tg.getWaterEngers());
		trt.getXs().add(tg.getRiverTranTonTypeAll());
		trt.getXs().add(tg.getRiverTranEntSTypeAll());
		trt.getXs().add(tg.getShipTypes());//车辆类型
		
		Map<String,Object> ds = wds.getRiverTranTypeOther(rd);
		trt.setEngTypOther((List<EngTypOtherItem>) ds.get("engTypeOther"));
		trt.setEntTypOther((List<EntTypOtherItem>) ds.get("entTypeOther"));
		trt.setWeiTypOther((List<WeiTypOtherItem>) ds.get("weiTypOther"));
		return trt;
	}
	
	
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/oceangoodsO.json")
	@ResponseBody
	public OceanGoodsResponse oceanGoods(HttpServletResponse response,
										RequestData rd){
//		rd.setUsername("zwp");
//		rd.setRoleName("enterprice");
//		rd.setRoleType(RoleType.ROLE_TRAFFIC);
//		rd.setTimeRange("2017-01-01:2017-12-30");
//		rd.setPlace1("杭州");
//		rd.setPlace2("江干");
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		OceanGoodsResponse ogr = new OceanGoodsResponse();
		
		ogr.setErrCode(ErrCode.DATA_OK);
		ogr.setRoleName(rd.getRoleName());
		ogr.setTimeRange(rd.getTimeRange());
		ogr.getXs().add(TimeTools.getYMlist(rd.getTimeRange()));
		ogr.getXs().add(tg.getWaterEngers());
		ogr.getXs().add(tg.getOceanGoodsTonTypeAll());
		ogr.getXs().add(tg.getOceanGoodsEntSTypeAll());
		ogr.getXs().add(tg.getShipTypes());//车辆类型
		ogr.getXs().add(tg.getOceanGoodsTranDisTypeAll());//车辆类型
		
		Map<String,Object> ds = wds.getOceanGoodsTypeOther(rd);
		ogr.setEngTypOther((List<EngTypOtherItem>) ds.get("engTypeOther"));
		ogr.setEntTypOther((List<EntTypOtherItem>) ds.get("entTypeOther"));
		ogr.setWeiTypOther((List<WeiTypOtherItem>) ds.get("weiTypOther"));
		return ogr;
		

	}
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/oceanpassO.json")
	@ResponseBody
	public OceanPassResponse oceanPass(HttpServletResponse response,
										RequestData rd){
//		rd.setUsername("zwp");
//		rd.setRoleName("enterprice");
//		rd.setRoleType(RoleType.ROLE_TRAFFIC);
//		rd.setTimeRange("2017-01-01:2017-12-30");
//		rd.setPlace1("杭州");
//		rd.setPlace2("江干");
		response.setHeader("Access-Control-Allow-Origin", "*");
		OceanPassResponse opr = new OceanPassResponse();
		
		opr.setErrCode(ErrCode.DATA_OK);
		opr.setRoleName(rd.getRoleName());
		opr.setTimeRange(rd.getTimeRange());
		opr.getXs().add(TimeTools.getYMlist(rd.getTimeRange()));
		opr.getXs().add(tg.getWaterEngers());
		opr.getXs().add(tg.getOceanPassSitSizeTypeAll());
		opr.getXs().add(tg.getOceanPassEntSTypeAll());
		opr.getXs().add(tg.getOceanPassTranDisTypeAll());//运距类型
		
		Map<String,Object> ds = wds.getOceanPassTypeOther(rd);
		opr.setEngTypOther((List<EngTypOtherItem>) ds.get("engTypeOther"));
		opr.setEntTypOther((List<EntTypOtherItem>) ds.get("entTypeOther"));
		opr.setDisTypOther((List<BaseTypOtherItem>) ds.get("disTypOther"));
		return opr;

	}
	

	
	@SuppressWarnings("unchecked")
	@RequestMapping("/portproduceO.json")
	@ResponseBody
	public PortProductResponse portProduce(HttpServletResponse response,
										RequestData rd){
		rd.setUsername("zwp");
		rd.setRoleName("enterprice");
		rd.setRoleType(RoleType.ROLE_TRAFFIC);
		rd.setTimeRange("2017-01-01:2017-12-30");
		rd.setPlace1("杭州");
		rd.setPlace2("江干");
		
		PortProductResponse ppr = new PortProductResponse();
		
		ppr.setErrCode(ErrCode.DATA_OK);
		ppr.setRoleName(rd.getRoleName());
		ppr.setTimeRange(rd.getTimeRange());
		ppr.getXs().add(TimeTools.getYMlist(rd.getTimeRange()));
		ppr.getXs().add(tg.getWaterEngers());
		ppr.getXs().add(tg.getPortProEntSTypeAll());
		Map<String ,Object> ds = wds.getPortProductTypeOther(rd);
		ppr.setEngTypOther((List<EngTypOtherItem>)ds.get("engTypeOther"));
		ppr.setEntTypOther((List<EntTypOtherItem>)ds.get("entTypeOther"));
		
		return ppr;
	}
	
	
}
