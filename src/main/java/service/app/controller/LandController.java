package service.app.controller;

import java.util.List;

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
	@RequestMapping("/rodpasO.json")
	@ResponseBody
	public IndexResponse roadPassTrans(HttpServletResponse response,
										RequestData rd){
		rd.setUsername("zwp");
		rd.setRoleType(RoleType.ROLE_LAND);
		rd.setTimeRange("2017-1-1:2017-12-31");
		rd.setPlace1("杭州");
		rd.setPlace2("江干区");
		
		IndexResponse ir = new IndexResponse();
		
		ir.setErrCode(ErrCode.DATA_OK);
		ir.setRoleName(rd.getRoleName());
		ir.setTimeRange(rd.getTimeRange());

		ir.setEngTypOther((List<EngTypOtherItem>) lds.getRoadPassTypOther(rd).get("engTypeOther"));
		
		
		
		return ir;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/rodgodO.json")
	@ResponseBody
	public IndexResponse roadGoodsTrans(HttpServletResponse response,
										RequestData rd){
		rd.setUsername("zwp");
		rd.setRoleType(RoleType.ROLE_LAND);
		rd.setTimeRange("2017-1-1:2017-12-31");
		rd.setPlace1("杭州");
		rd.setPlace2("江干区");
		
		IndexResponse ir = new IndexResponse();
		
		ir.setErrCode(ErrCode.DATA_OK);
		ir.setRoleName(rd.getRoleName());
		ir.setTimeRange(rd.getTimeRange());
		ir.setEngTypOther((List<EngTypOtherItem>) lds.getRoadPassTypOther(rd).get("engTypeOther"));
		
		return ir;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/busO.json")
	@ResponseBody
	public IndexResponse busTrans(HttpServletResponse response,
										RequestData rd){
		rd.setUsername("zwp");
		rd.setRoleType(RoleType.ROLE_LAND);
		rd.setTimeRange("2017-1-1:2017-12-31");
		rd.setPlace1("杭州");
		rd.setPlace2("江干区");
		
		IndexResponse ir = new IndexResponse();
		
		ir.setErrCode(ErrCode.DATA_OK);
		ir.setRoleName(rd.getRoleName());
		ir.setTimeRange(rd.getTimeRange());
		ir.setEngTypOther((List<EngTypOtherItem>)lds.getRoadPassTypOther(rd).get("engTypeOther"));
		
		return ir;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/texiTrans.json")
	@ResponseBody
	public IndexResponse taxiTrans(HttpServletResponse response,
										RequestData rd){
		rd.setUsername("zwp");
		rd.setRoleType(RoleType.ROLE_LAND);
		rd.setTimeRange("2017-1-1:2017-12-31");
		rd.setPlace1("杭州");
		rd.setPlace2("江干区");
		
		IndexResponse ir = new IndexResponse();
		
		ir.setErrCode(ErrCode.DATA_OK);
		ir.setRoleName(rd.getRoleName());
		ir.setTimeRange(rd.getTimeRange());
		ir.setEngTypOther((List<EngTypOtherItem>)lds.getRoadPassTypOther(rd).get("engTypeOther"));
		
		return ir;
	}
	
	
	
	
	
	
	
}
