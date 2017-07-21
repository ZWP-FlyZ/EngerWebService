package service.app.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.app.server.WaterDataService;
import service.app.tramodel.EngTypOtherItem;
import service.app.tramodel.EntTypOtherItem;
import service.app.tramodel.ErrCode;
import service.app.tramodel.IndexResponse;
import service.app.tramodel.PortProductResponse;
import service.app.tramodel.RequestData;
import service.app.tramodel.RoleType;

@Controller
public class WaterController {

	@Autowired
	WaterDataService wds;
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/rivertranO.json")
	@ResponseBody
	public IndexResponse riverTran(HttpServletResponse response,
										RequestData rd){
		rd.setUsername("zwp");
		rd.setRoleName("enterprice");
		rd.setRoleType(RoleType.ROLE_TRAFFIC);
		rd.setTimeRange("2017-01-01:2017-12-30");
		rd.setPlace1("杭州");
		rd.setPlace2("江干区");
		
		IndexResponse ir = new IndexResponse();
		
		ir.setErrCode(ErrCode.DATA_OK);
		ir.setRoleName(rd.getRoleName());
		Map<String,Object> ds = wds.getRiverTranTypeOther(rd);
		ir.setEngTypOther((List<EngTypOtherItem>) ds.get("engTypeOther"));
		
		return ir;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/oceanpassO.json")
	@ResponseBody
	public IndexResponse oceanPass(HttpServletResponse response,
										RequestData rd){
		rd.setUsername("zwp");
		rd.setRoleName("enterprice");
		rd.setRoleType(RoleType.ROLE_TRAFFIC);
		rd.setTimeRange("2017-01-01:2017-12-30");
		rd.setPlace1("杭州");
		rd.setPlace2("江干区");
		
		IndexResponse ir = new IndexResponse();
		
		ir.setErrCode(ErrCode.DATA_OK);
		ir.setRoleName(rd.getRoleName());
		ir.setTimeRange(rd.getTimeRange());
		Map<String,Object> ds = wds.getOceanPassTypeOther(rd);
		ir.setEngTypOther((List<EngTypOtherItem>) ds.get("engTypeOther"));
		
		return ir;

	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/oceangoodsO.json")
	@ResponseBody
	public IndexResponse oceanGoods(HttpServletResponse response,
										RequestData rd){
		rd.setUsername("zwp");
		rd.setRoleName("enterprice");
		rd.setRoleType(RoleType.ROLE_TRAFFIC);
		rd.setTimeRange("2017-01-01:2017-12-30");
		rd.setPlace1("杭州");
		rd.setPlace2("江干区");
		
		IndexResponse ir = new IndexResponse();
		
		ir.setErrCode(ErrCode.DATA_OK);
		ir.setRoleName(rd.getRoleName());
		ir.setTimeRange(rd.getTimeRange());
		Map<String,Object> ds = wds.getOceanGoodsTypeOther(rd);
		ir.setEngTypOther((List<EngTypOtherItem>) ds.get("engTypeOther"));
		
		return ir;
		

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
		rd.setPlace2("江干区");
		
		PortProductResponse ppr = new PortProductResponse();
		
		ppr.setErrCode(ErrCode.DATA_OK);
		ppr.setRoleName(rd.getRoleName());
		ppr.setTimeRange(rd.getTimeRange());
		Map<String ,Object> ds = wds.getPortProductTypeOther(rd);
		ppr.setEngTypOther((List<EngTypOtherItem>)ds.get("engTypeOther"));
		ppr.setEntTypOther((List<EntTypOtherItem>)ds.get("entTypeOther"));
		
		return ppr;
	}
	
	
}
