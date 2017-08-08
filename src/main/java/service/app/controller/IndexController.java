package service.app.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.app.server.IndexService;
import service.app.timwin.TimWinMap;
import service.app.tramodel.ErrCode;
import service.app.tramodel.RequestData;
import service.app.tramodel.items.EngTypOtherItem;
import service.app.tramodel.response.EngTypOthResponse;
import service.app.util.TimeTools;
import service.app.util.TypeGetter;

@Controller
public class IndexController {

	@Autowired
	IndexService indexS;
	
	@Autowired
	TypeGetter tg;
	
	@Autowired
	TimWinMap tokenMap;
	
	private static Logger logger =  LoggerFactory.getLogger(IndexController.class);
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/index.json")
	@ResponseBody
	public EngTypOthResponse getIndexData(HttpServletResponse response,
				RequestData rd){
				
		EngTypOthResponse ir = new EngTypOthResponse();
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		if(rd.getToken()==null||!tokenMap.isContainData(rd.getToken())){
			ir.setErrCode(ErrCode.DATA_AUTH_ERR);
			ir.setRoleName(rd.getRoleName());
			ir.setTimeRange(rd.getTimeRange());
			
		}else{
			ir.setErrCode(ErrCode.DATA_OK);
			ir.setRoleName(rd.getRoleName());
			ir.setTimeRange(rd.getTimeRange());
			ir.getXs().add(TimeTools.getYMlist(rd.getTimeRange()));
			ir.getXs().add(tg.getAllEngersTypes());
			Map<String,Object> ds = indexS.getEngTypOther(rd);
			ir.setEngTypOther((List<EngTypOtherItem>) ds.get("engTypeOther"));
		}
		
		return ir;
	}
	
}
