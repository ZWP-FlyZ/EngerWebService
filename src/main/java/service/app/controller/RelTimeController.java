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

import service.app.server.RelTimeDataService;
import service.app.tramodel.ErrCode;
import service.app.tramodel.RequestData;
import service.app.tramodel.items.EngTypOtherItem;
import service.app.tramodel.response.RelTimMapDataResponse;
import service.app.tramodel.response.RelTimeDataResponse;
import service.app.util.TimeTools;
import service.app.util.TypeGetter;

@Controller
public class RelTimeController {
	
	@Autowired
	RelTimeDataService rtds;
	
	@Autowired
	TypeGetter tg;
	
	private final static Logger logger = LoggerFactory.getLogger(RelTimeController.class);
	/*-----实时数据的接口------*/
	@SuppressWarnings("unchecked")
	@RequestMapping("/reltimedata.json")
	@ResponseBody
	public RelTimeDataResponse getRelTimeData(HttpServletResponse response,
											RequestData rd){
		RelTimeDataResponse rtdr =new RelTimeDataResponse();
		rtdr.setErrCode(ErrCode.DATA_OK);
		rtdr.setRoleName(rd.getRoleName());
		rtdr.setTimeRange(rd.getTimeRange());
		rtdr.setTranType(rd.getTranType());
		rtdr.setCarId(rd.getCarId());
		rtdr.setShipId(rd.getShipId());
		rtdr.setCityType(rd.getCityType());
		rtdr.setCompanyId(rd.getCompanyId());
		try {
			rtdr.getXs().add(TimeTools.getTenMuList(rd.getTimeRange()));
			if(TypeGetter.TT_WATER_RIVER.equals(rd.getTranType()))
				rtdr.getXs().add(tg.getRelWaterEngers());
			else
				rtdr.getXs().add(tg.getRelLandEngers());
			
			Map<String,Object> ds = rtds.getBusRelTimeData(rd);
			rtdr.setEngTypOther((List<EngTypOtherItem>) ds.get("engTypeOther"));
		} catch (Exception e) {
			logger.error(e.toString());
			rtdr.setErrCode(ErrCode.DATA_ERR);
		}
		
		return rtdr;
	}
	
	/*-----热力图的接口------*/
	@RequestMapping("/reltimemap.json")
	@ResponseBody
	public RelTimMapDataResponse getRelTimMapData(HttpServletResponse response,
							RequestData rd){
		RelTimMapDataResponse rmdr = new RelTimMapDataResponse();
		rmdr.setErrCode(ErrCode.DATA_OK);
		rmdr.setRoleName(rd.getRoleName());
		rmdr.setTimeRange(rd.getTimeRange());
		rmdr.setCityType(rd.getCityType());
		rmdr.setContry(rd.getContryType());
		try {
			rmdr.setData(rtds.getRelTimMapData(rd));
		} catch (Exception e) {
			logger.error(e.toString(),e);
			rmdr.setErrCode(ErrCode.DATA_ERR);
		}
		return rmdr;
	}
	
	
}
