package service.app.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.app.server.IndexService;
import service.app.server.LogService;
import service.app.tramodel.ErrCode;
import service.app.tramodel.IndexResponse;
import service.app.tramodel.RequestData;
import service.app.tramodel.RoleType;

@Controller
public class IndexController extends BaseController{

	@Autowired
	IndexService indexS;
	
	@RequestMapping("/index.json")
	@ResponseBody
	public IndexResponse getIndexData(HttpServletResponse response,
				RequestData rd){
		
		rd.setUsername("zwp");
		rd.setRoleType(RoleType.ROLE_LAND);
		rd.setTimeRange("2017-1-1:2017-12-31");
	
		
		IndexResponse ir = new IndexResponse();
	
		ir.setErrCode(ErrCode.DATA_OK);
		ir.setRoleName(rd.getRoleName());
		ir.setTimeRange(rd.getTimeRange());
		ir.setEngTypOther(indexS.getEngTypOther(rd));
		
		return ir;
	}
	
}
