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
	
	private final static Logger logger = LoggerFactory.getLogger(LadWatController.class);
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/perdisengO.json")
	@ResponseBody
	public TraTypOthResponse perDisEng(HttpServletResponse response,
			RequestData rd){

		
		TraTypOthResponse ttr = new TraTypOthResponse();
		ttr.setErrCode(ErrCode.DATA_OK);
		ttr.setRoleName(rd.getRoleName());
		ttr.setTimeRange(rd.getTimeRange());
		try {
			ttr.getXs().add(tg.getTransTypes());
			ttr.getXs().add(tg.getAllEngersTypes());
			
			Map<String,Object> ds = lwds.getPerDisEngTypOther(rd);
			ttr.setTraTypOther((List<TraTypOtherItem>) ds.get("traTypeOther"));
		} catch (Exception e) {
			logger.error(e.toString());
			ttr.setErrCode(ErrCode.DATA_ERR);
		}
		return ttr;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/engtyp3yearO.json")
	@ResponseBody
	public EngTypOthResponse engTypnAnyYear(HttpServletResponse response,
			RequestData rd){


		EngTypOthResponse ir = new EngTypOthResponse();
		ir.setErrCode(ErrCode.DATA_OK);
		ir.setRoleName(rd.getRoleName());
		ir.setTimeRange(rd.getTimeRange());
		
		try {
			ir.getXs().add(TimeTools.getYMlist(rd.getTimeRange()));
			ir.getXs().add(tg.getAllEngersTypes());
			
			Map<String,Object> ds = lwds.getEngTyp3YearTypOther(rd);
			ir.setEngTypOther((List<EngTypOtherItem>) ds.get("engTypeOther"));
		} catch (Exception e) {
			logger.error(e.toString());
			ir.setErrCode(ErrCode.DATA_ERR);
		}

		return ir;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/tratypperyearO.json")
	@ResponseBody
	public TraTypOthResponse traTypPerYear(HttpServletResponse response,
			RequestData rd){


		TraTypOthResponse ttr = new TraTypOthResponse();
		
		ttr.setErrCode(ErrCode.DATA_OK);
		ttr.setRoleName(rd.getRoleName());
		ttr.setTimeRange(rd.getTimeRange());
		try {
			ttr.getXs().add(TimeTools.getYMlist(rd.getTimeRange()));
			ttr.getXs().add(tg.getTransTypes());
			
			Map<String,Object> ds = lwds.getTraTypPerYearTypOther(rd);
			ttr.setTraTypOther((List<TraTypOtherItem>) ds.get("traTypeOther"));
		} catch (Exception e) {
			logger.error(e.toString());
			ttr.setErrCode(ErrCode.DATA_ERR);
		}
		

		
		return ttr;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/tratypallyearO.json")
	@ResponseBody
	public TraTypOthResponse traTypAllYear(HttpServletResponse response,
			RequestData rd){

		
		TraTypOthResponse ttr = new TraTypOthResponse();
		
		ttr.setErrCode(ErrCode.DATA_OK);
		ttr.setRoleName(rd.getRoleName());
		ttr.setTimeRange(rd.getTimeRange());
		
		try {
			ttr.getXs().add(TimeTools.getYMlist(rd.getTimeRange()));
			ttr.getXs().add(tg.getTransTypes());
			Map<String,Object> ds = lwds.getTraTypPerYearTypOther(rd);
			ttr.setTraTypOther((List<TraTypOtherItem>) ds.get("traTypeOther"));
		} catch (Exception e) {
			logger.error(e.toString());
			ttr.setErrCode(ErrCode.DATA_ERR);
		}

		
		return ttr;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/citytrantypengO.json")
	@ResponseBody
	public CitTraTypOthResponse cityTranTypEnger(HttpServletResponse response,
			RequestData rd){

	
		CitTraTypOthResponse ctt = new CitTraTypOthResponse();
		ctt.setErrCode(ErrCode.DATA_OK);
		ctt.setRoleName(rd.getRoleName());
		ctt.setTimeRange(rd.getTimeRange());
		ctt.setTranType(rd.getTranType());
		try {
			ctt.getXs().add(tg.getCitiesTypes());
			Map<String,Object> ds = lwds.getCitTranTypOther(rd);
			ctt.setCitTypOther((List<CitTypOtherItem>) (ds.get("citTypeOther")));
		} catch (Exception e) {
			logger.error(e.toString());
			ctt.setErrCode(ErrCode.DATA_ERR);
		}
		return ctt;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/tracitytypengO.json")
	@ResponseBody
	public TraTypOthResponse traCitTypeEng(HttpServletResponse response,
			RequestData rd){


		TraTypOthResponse ttr = new TraTypOthResponse();
		ttr.setErrCode(ErrCode.DATA_OK);
		ttr.setRoleName(rd.getRoleName());
		ttr.setTimeRange(rd.getTimeRange());
		ttr.setCityType(rd.getCityType());
		try {
			ttr.getXs().add(tg.getTransTypes());
			Map<String,Object> ds = lwds.getTranCitTypOther(rd);
			ttr.setTraTypOther((List<TraTypOtherItem>) ds.get("traTypeOther"));
			
		} catch (Exception e) {
			logger.error(e.toString());
			ttr.setErrCode(ErrCode.DATA_ERR);
		}
		
		return ttr;
	}
	
	
	
	
}
