package service.app.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.app.server.IndexService;
import service.app.tramodel.ErrCode;
import service.app.tramodel.RequestData;
import service.app.tramodel.RoleType;
import service.app.tramodel.items.EngTypOtherItem;
import service.app.tramodel.response.EngTypOthResponse;

@Controller
public class IndexController extends BaseController{

	@Autowired
	IndexService indexS;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/index.json")
	@ResponseBody
	public EngTypOthResponse getIndexData(HttpServletResponse response,
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
		Map<String,Object> ds = indexS.getEngTypOther(rd);
		ir.setEngTypOther((List<EngTypOtherItem>) ds.get("engTypeOther"));
		
		return ir;
	}
	
}
