package service.app.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.app.server.RelTimeDataService;
import service.app.tramodel.ErrCode;
import service.app.tramodel.RequestData;
import service.app.tramodel.items.EngTypOtherItem;
import service.app.tramodel.response.RelTimeDataResponse;
import service.app.util.TimeTools;
import service.app.util.TypeGetter;

@Controller
public class RelTimeController {
	
	@Autowired
	RelTimeDataService rtds;
	
	@Autowired
	TypeGetter tg;
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/reltimedata.json")
	@ResponseBody
	public RelTimeDataResponse getRelTimeData(HttpServletResponse response,
											RequestData rd){
		
//		rd.setUsername("zwp");
//		rd.setRoleName("enterprice");
//		rd.setRoleType(RoleType.ROLE_TRAFFIC);
//		rd.setTimeRange("2017-01-01 11:11:00&2017-01-01 23:11:00");
//		rd.setPlace1("杭州");
//		rd.setPlace2("江干区");
//		
//		rd.setTranType(TypeGetter.TT_LAND_BUS);
//		rd.setCarId("c1");
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		
		RelTimeDataResponse rtdr =new RelTimeDataResponse();
		
		rtdr.setErrCode(ErrCode.DATA_OK);
		rtdr.setRoleName(rd.getRoleName());
		rtdr.setTimeRange(rd.getTimeRange());
		rtdr.setTranType(rd.getTranType());
		rtdr.setCarId(rd.getCarId());
		rtdr.setShipId(rd.getShipId());
		rtdr.setCityType(rd.getCityType());
		rtdr.setCompanyId(rd.getCompanyId());
		
		rtdr.getXs().add(TimeTools.getTenMuList(rd.getTimeRange()));
		if(TypeGetter.TT_WATER_RIVER.equals(rd.getTranType()))
			rtdr.getXs().add(tg.getRelWaterEngers());
		else
			rtdr.getXs().add(tg.getRelLandEngers());
		
		Map<String,Object> ds = rtds.getBusRelTimeData(rd);
		rtdr.setEngTypOther((List<EngTypOtherItem>) ds.get("engTypeOther"));
		
		return rtdr;
	}
	
}
