package service.app.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.app.server.LadWatDataService;
import service.app.tramodel.ErrCode;
import service.app.tramodel.RequestData;
import service.app.tramodel.items.CitTypOtherItem;
import service.app.tramodel.items.EngTypOtherItem;
import service.app.tramodel.items.TraTypOtherItem;
import service.app.tramodel.response.CitTraTypOthResponse;
import service.app.tramodel.response.EngTypOthResponse;
import service.app.tramodel.response.TraTypOthResponse;
import service.app.util.TimeTools;
import service.app.util.TypeGetter;

@Controller
public class LadWatController {

	@Autowired
	LadWatDataService lwds;
	
	@Autowired
	TypeGetter tg;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/perdisengO.json")
	@ResponseBody
	public TraTypOthResponse perDisEng(HttpServletResponse response,
			RequestData rd){

		response.setHeader("Access-Control-Allow-Origin", "*");
		TraTypOthResponse ttr = new TraTypOthResponse();
		
		ttr.setErrCode(ErrCode.DATA_OK);
		ttr.setRoleName(rd.getRoleName());
		ttr.setTimeRange(rd.getTimeRange());
		ttr.getXs().add(tg.getTransTypes());
		ttr.getXs().add(tg.getAllEngersTypes());
		
		Map<String,Object> ds = lwds.getPerDisEngTypOther(rd);
		ttr.setTraTypOther((List<TraTypOtherItem>) ds.get("traTypeOther"));
		
		return ttr;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/engtyp3yearO.json")
	@ResponseBody
	public EngTypOthResponse engTyp3Year(HttpServletResponse response,
			RequestData rd){
//		rd.setUsername("zwp");
//		rd.setRoleName("enterprice");
//		rd.setRoleType(RoleType.ROLE_TRAFFIC);
//		rd.setTimeRange("2017-01-01:2017-12-30");
//		rd.setPlace1("杭州");
//		rd.setPlace2("江干");
		response.setHeader("Access-Control-Allow-Origin", "*");
		EngTypOthResponse ir = new EngTypOthResponse();
		
		ir.setErrCode(ErrCode.DATA_OK);
		ir.setRoleName(rd.getRoleName());
		ir.setTimeRange(rd.getTimeRange());
		ir.getXs().add(TimeTools.getYMlist(rd.getTimeRange()));
		ir.getXs().add(tg.getAllEngersTypes());
		
		Map<String,Object> ds = lwds.getEngTyp3YearTypOther(rd);
		ir.setEngTypOther((List<EngTypOtherItem>) ds.get("engTypeOther"));
		
		return ir;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/tratypperyearO.json")
	@ResponseBody
	public TraTypOthResponse traTypPerYear(HttpServletResponse response,
			RequestData rd){
//		rd.setUsername("zwp");
//		rd.setRoleName("enterprice");
//		rd.setRoleType(RoleType.ROLE_TRAFFIC);
//		rd.setTimeRange("2017-01-01:2017-12-30"); 
//		rd.setPlace1("杭州");
//		rd.setPlace2("江干");
		response.setHeader("Access-Control-Allow-Origin", "*");
		TraTypOthResponse ttr = new TraTypOthResponse();
		
		ttr.setErrCode(ErrCode.DATA_OK);
		ttr.setRoleName(rd.getRoleName());
		ttr.setTimeRange(rd.getTimeRange());
		ttr.getXs().add(TimeTools.getYMlist(rd.getTimeRange()));
		ttr.getXs().add(tg.getTransTypes());
		
		Map<String,Object> ds = lwds.getTraTypPerYearTypOther(rd);
		ttr.setTraTypOther((List<TraTypOtherItem>) ds.get("traTypeOther"));
		
		return ttr;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/tratypallyearO.json")
	@ResponseBody
	public TraTypOthResponse traTypAllYear(HttpServletResponse response,
			RequestData rd){
//		rd.setUsername("zwp");
//		rd.setRoleName("enterprice");
//		rd.setRoleType(RoleType.ROLE_TRAFFIC);
//		rd.setTimeRange("2017-01-01:2017-12-30");
//		rd.setPlace1("杭州");
//		rd.setPlace2("江干");
		response.setHeader("Access-Control-Allow-Origin", "*");
		TraTypOthResponse ttr = new TraTypOthResponse();
		
		ttr.setErrCode(ErrCode.DATA_OK);
		ttr.setRoleName(rd.getRoleName());
		ttr.setTimeRange(rd.getTimeRange());
		ttr.getXs().add(TimeTools.getYMlist(rd.getTimeRange()));
		ttr.getXs().add(tg.getTransTypes());
		Map<String,Object> ds = lwds.getTraTypPerYearTypOther(rd);
		ttr.setTraTypOther((List<TraTypOtherItem>) ds.get("traTypeOther"));
		
		return ttr;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/citytrantypengO.json")
	@ResponseBody
	public CitTraTypOthResponse cityTranTypEnger(HttpServletResponse response,
			RequestData rd){
//		rd.setUsername("zwp");
//		rd.setRoleName("enterprice");
//		rd.setRoleType(RoleType.ROLE_TRAFFIC);
//		rd.setTimeRange("2017-01-01:2017-12-30");
//		rd.setPlace1("杭州");
//		rd.setPlace2("江干");
//		rd.setTranType(TypeGetter.TT_LAND_PASS);
		response.setHeader("Access-Control-Allow-Origin", "*");
		CitTraTypOthResponse ctt = new CitTraTypOthResponse();
		
		ctt.setErrCode(ErrCode.DATA_OK);
		ctt.setRoleName(rd.getRoleName());
		ctt.setTimeRange(rd.getTimeRange());
		ctt.setTranType(rd.getTranType());
		ctt.getXs().add(tg.getCitiesTypes());
		Map<String,Object> ds = lwds.getCitTranTypOther(rd);
		ctt.setCitTypOther((List<CitTypOtherItem>) (ds.get("citTypeOther")));
		return ctt;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/tracitytypengO.json")
	@ResponseBody
	public TraTypOthResponse traCitTypeEng(HttpServletResponse response,
			RequestData rd){
//		rd.setUsername("zwp");
//		rd.setRoleName("enterprice");
//		rd.setRoleType(RoleType.ROLE_TRAFFIC);
//		rd.setTimeRange("2017-01-01:2017-12-30");
//		rd.setPlace1("杭州");
//		rd.setPlace2("江干");
//		rd.setTranType(TypeGetter.TT_LAND_PASS);
//		rd.setCityType("杭州");
		response.setHeader("Access-Control-Allow-Origin", "*");
		TraTypOthResponse ttr = new TraTypOthResponse();
		
		ttr.setErrCode(ErrCode.DATA_OK);
		ttr.setRoleName(rd.getRoleName());
		ttr.setTimeRange(rd.getTimeRange());
		ttr.setCityType(rd.getCityType());
		ttr.getXs().add(tg.getTransTypes());
		
		
		Map<String,Object> ds = lwds.getTranCitTypOther(rd);
		ttr.setTraTypOther((List<TraTypOtherItem>) ds.get("traTypeOther"));
		
		return ttr;
	}
	
	
	
	
}
