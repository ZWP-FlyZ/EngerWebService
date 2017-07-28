package service.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.app.server.LandDataService;
import service.app.tramodel.ErrCode;
import service.app.tramodel.RequestData;
import service.app.tramodel.RoleType;
import service.app.tramodel.items.BaseTypOtherItem;
import service.app.tramodel.items.CarTypOtherItem;
import service.app.tramodel.items.EngTypOtherItem;
import service.app.tramodel.items.EntTypOtherItem;
import service.app.tramodel.response.EngTypOthResponse;
import service.app.tramodel.response.RoadGoodsResponse;
import service.app.tramodel.response.RoadPassResponse;
import service.app.util.TimeTools;

@Controller
public class LandController {
	
	@Autowired
	LandDataService lds;
	

	@SuppressWarnings("unchecked")
	@RequestMapping("/roadpassO.json")
	@ResponseBody
	public RoadPassResponse roadPassTrans(HttpServletResponse response,
										RequestData rd){
		rd.setUsername("zwp");
		rd.setRoleName("enterprice");
		rd.setRoleType(RoleType.ROLE_TRAFFIC);
		rd.setTimeRange("2017-01-01:2017-12-30");
		rd.setPlace1("杭州");
		rd.setPlace2("江干");
		
		RoadPassResponse rpr = new RoadPassResponse();
		
		rpr.setErrCode(ErrCode.DATA_OK);
		rpr.setRoleName(rd.getRoleName());
		rpr.setTimeRange(rd.getTimeRange());
		rpr.getXs().add(TimeTools.getYMlist(rd.getTimeRange()));
		Map<String,Object> ds = lds.getRoadPassTypOther(rd);
		rpr.setEngTypOther((List<EngTypOtherItem>) ds.get("engTypeOther"));
		rpr.setEntTypOther((List<EntTypOtherItem>) ds.get("entTypeOther"));
		rpr.setDisTypOther((List<BaseTypOtherItem>) ds.get("disTypOther"));
		rpr.setCarTypOther((List<CarTypOtherItem>) ds.get("carTypOther"));
		return rpr;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/roadgoodsO.json")
	@ResponseBody
	public RoadGoodsResponse roadGoodsTrans(HttpServletResponse response,
										RequestData rd){
		rd.setUsername("zwp");
		rd.setRoleName("enterprice");
		rd.setRoleType(RoleType.ROLE_TRAFFIC);
		rd.setTimeRange("2017-01-01:2017-12-30");
		rd.setPlace1("杭州");
		rd.setPlace2("江干");
		
		RoadGoodsResponse rgr = new RoadGoodsResponse();
		
		rgr.setErrCode(ErrCode.DATA_OK);
		rgr.setRoleName(rd.getRoleName());
		rgr.getXs().add(TimeTools.getYMlist(rd.getTimeRange()));
		Map<String,Object> ds = lds.getRoadGoodsTypOther(rd);
		rgr.setEngTypOther((List<EngTypOtherItem>) ds.get("engTypeOther"));
		rgr.setEntTypOther((List<EntTypOtherItem>) ds.get("entTypeOther"));
		rgr.setCarTypOther((List<CarTypOtherItem>) ds.get("carTypOther"));
		
		return rgr;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/bustranO.json")
	@ResponseBody
	public EngTypOthResponse busTrans(HttpServletResponse response,
										RequestData rd){
		rd.setUsername("zwp");
		rd.setRoleName("enterprice");
		rd.setRoleType(RoleType.ROLE_TRAFFIC);
		rd.setTimeRange("2017-01-01:2017-12-30");
		rd.setPlace1("杭州");
		rd.setPlace2("江干");
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		EngTypOthResponse ir = new EngTypOthResponse();
		ir.getXs().add(TimeTools.getYMlist(rd.getTimeRange()));
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
	public EngTypOthResponse taxiTrans(HttpServletResponse response,
										RequestData rd){
		rd.setUsername("zwp");
		rd.setRoleName("enterprice");
		rd.setRoleType(RoleType.ROLE_TRAFFIC);
		rd.setTimeRange("2017-01-01:2017-12-30");
		rd.setPlace1("杭州");
		rd.setPlace2("江干");
		
		EngTypOthResponse ir = new EngTypOthResponse();
		
		ir.setErrCode(ErrCode.DATA_OK);
		ir.setRoleName(rd.getRoleName());
		ir.setTimeRange(rd.getTimeRange());
		ir.getXs().add(TimeTools.getYMlist(rd.getTimeRange()));
		Map<String,Object> ds = lds.getTaxiTranTypOther(rd);
		ir.setEngTypOther((List<EngTypOtherItem>) ds.get("engTypeOther"));
		
		return ir;
	}
	
	
	
	
	
	
	
}
