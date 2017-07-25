package service.app.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.app.server.LandDataService;
import service.app.tramodel.EngTypOtherItem;
import service.app.tramodel.ErrCode;
import service.app.tramodel.IndexResponse;
import service.app.tramodel.RequestData;
import service.app.tramodel.RoleType;

@Controller
public class LandController {
	
	@Autowired
	LandDataService lds;
	

	@SuppressWarnings("unchecked")
	@RequestMapping("/roadpassO.json")
	@ResponseBody
	public IndexResponse roadPassTrans(HttpServletResponse response,
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
		Map<String,Object> ds = lds.getRoadPassTypOther(rd);
		ir.setEngTypOther((List<EngTypOtherItem>) ds.get("engTypeOther"));
		
		return ir;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/roadgoodsO.json")
	@ResponseBody
	public IndexResponse roadGoodsTrans(HttpServletResponse response,
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
		Map<String,Object> ds = lds.getRoadGoodsTypOther(rd);
		ir.setEngTypOther((List<EngTypOtherItem>) ds.get("engTypeOther"));
		
		return ir;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/bustranO.json")
	@ResponseBody
	public IndexResponse busTrans(HttpServletResponse response,
										RequestData rd){
		rd.setUsername("zwp");
		rd.setRoleName("enterprice");
		rd.setRoleType(RoleType.ROLE_TRAFFIC);
		rd.setTimeRange("2017-01-01:2017-12-30");
		rd.setPlace1("杭州");
		rd.setPlace2("江干区");
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		IndexResponse ir = new IndexResponse();
		
		ir.setErrCode(ErrCode.DATA_OK);
		ir.setRoleName(rd.getRoleName());
		ir.setTimeRange(rd.getTimeRange());
		Map<String,Object> ds = lds.getBusTranTypOther(rd);
		ir.setEngTypOther((List<EngTypOtherItem>) ds.get("engTypeOther"));
		
		return ir;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/taxitranO.json")
	@ResponseBody
	public IndexResponse taxiTrans(HttpServletResponse response,
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
		Map<String,Object> ds = lds.getTaxiTranTypOther(rd);
		ir.setEngTypOther((List<EngTypOtherItem>) ds.get("engTypeOther"));
		
		return ir;
	}
	
	
	
	
	
	
	
}
