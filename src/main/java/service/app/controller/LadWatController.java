package service.app.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.app.server.LadWatDataService;
import service.app.tramodel.EngTypOtherItem;
import service.app.tramodel.ErrCode;
import service.app.tramodel.IndexResponse;
import service.app.tramodel.PerDisEngResponse;
import service.app.tramodel.RequestData;
import service.app.tramodel.RoleType;
import service.app.tramodel.TraTypOtherItem;

@Controller
public class LadWatController {

	@Autowired
	LadWatDataService lwds;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/perdisengO.json")
	@ResponseBody
	public PerDisEngResponse perDisEng(HttpServletResponse response,
			RequestData rd){
		rd.setUsername("zwp");
		rd.setRoleName("enterprice");
		rd.setRoleType(RoleType.ROLE_TRAFFIC);
		rd.setTimeRange("2017-01-01:2017-12-30");
		rd.setPlace1("杭州");
		rd.setPlace2("江干区");
		
		PerDisEngResponse pde = new PerDisEngResponse();
		
		pde.setErrCode(ErrCode.DATA_OK);
		pde.setRoleName(rd.getRoleName());
		pde.setTimeRange(rd.getTimeRange());
		
		Map<String,Object> ds = lwds.getPerDisEngTypOther(rd);
		pde.setTraTypOther((List<TraTypOtherItem>) ds.get("traTypeOther"));
		
		return pde;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/engtyp3yearO.json")
	@ResponseBody
	public IndexResponse engTyp3Year(HttpServletResponse response,
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
		Map<String,Object> ds = lwds.getEngTyp3YearTypOther(rd);
		ir.setEngTypOther((List<EngTypOtherItem>) ds.get("engTypeOther"));
		
		return ir;
	}
	
	
	
	
	
}
