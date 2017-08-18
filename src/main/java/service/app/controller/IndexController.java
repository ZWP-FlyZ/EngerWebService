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
	service.app.server.n.IndexService index2;
	
	@Autowired
	TypeGetter tg;
	
	@Autowired
	TimWinMap tokenMap;
	
	
	private final static Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/index.json")
	@ResponseBody
	public EngTypOthResponse getIndexData(HttpServletResponse response,
				RequestData rd){
				
		EngTypOthResponse ir = new EngTypOthResponse();
		Map<String,Object> ds = null;
		ir.setErrCode(ErrCode.DATA_OK);
		ir.setRoleName(rd.getRoleName());
		ir.setTimeRange(rd.getTimeRange());
		try {
			ir.getXs().add(TimeTools.getYMlist(rd.getTimeRange()));
			ir.getXs().add(tg.getAllEngersTypes());
			ds = index2.getEngTypOther(rd);
			ir.setEngTypOther((List<EngTypOtherItem>) ds.get("engTypeOther"));
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			ir.setErrCode(ErrCode.DATA_ERR);
		}
		return ir;
	}
	
	
	
}
