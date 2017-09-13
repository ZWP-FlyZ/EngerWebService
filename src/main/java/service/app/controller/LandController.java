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

import service.app.server.LandDataService;
import service.app.tramodel.ErrCode;
import service.app.tramodel.RequestData;
import service.app.tramodel.items.BaseTypOtherItem;
import service.app.tramodel.items.CarTypOtherItem;
import service.app.tramodel.items.EngTypOtherItem;
import service.app.tramodel.items.EntTypOtherItem;
import service.app.tramodel.response.EngTypOthResponse;
import service.app.tramodel.response.RoadGoodsResponse;
import service.app.tramodel.response.RoadPassResponse;
import service.app.util.TimeTools;
import service.app.util.TypeGetter;

@Controller
public class LandController {
	
	@Autowired
	LandDataService lds;
	
	@Autowired
	TypeGetter tg;
	
	@Autowired
	service.app.server.n.LandDataService landServiceN;
	
	private final static Logger logger = LoggerFactory.getLogger(LandController.class);

	@SuppressWarnings("unchecked")
	@RequestMapping("/roadpassO.json")
	@ResponseBody
	public RoadPassResponse roadPassTrans(HttpServletResponse response,
										RequestData rd){

		RoadPassResponse rpr = new RoadPassResponse();
		rpr.setErrCode(ErrCode.DATA_OK);
		rpr.setRoleName(rd.getRoleName());
		rpr.setTimeRange(rd.getTimeRange());
		
		try {
			rpr.getXs().add(TimeTools.getYMlist(rd.getTimeRange()));
			rpr.getXs().add(tg.getLandEngers());
			rpr.getXs().add(tg.getRoadPassSitCotTypeAll());
			rpr.getXs().add(tg.getRoadPassEntSizeTypeAll());
			rpr.getXs().add(tg.getRoadPassDisTypeAll());//运距类型
			rpr.getXs().add(tg.getCarTypes());//车辆类型
			
			Map<String,Object> ds = landServiceN.getRoadPassTypOther(rd);
			rpr.setEngTypOther((List<EngTypOtherItem>) ds.get("engTypeOther"));
			rpr.setEntTypOther((List<EntTypOtherItem>) ds.get("entTypeOther"));
			rpr.setDisTypOther((List<BaseTypOtherItem>) ds.get("disTypOther"));
			rpr.setCarTypOther((List<CarTypOtherItem>) ds.get("carTypOther"));
		} catch (Exception e) {
			logger.error(e.toString(),e);
			// TODO: handle exception
			rpr.setErrCode(ErrCode.DATA_ERR);
		}
		return rpr;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/roadgoodsO.json")
	@ResponseBody
	public RoadGoodsResponse roadGoodsTrans(HttpServletResponse response,
										RequestData rd){


		RoadGoodsResponse rgr = new RoadGoodsResponse();
		
		rgr.setErrCode(ErrCode.DATA_OK);
		rgr.setRoleName(rd.getRoleName());
		rgr.setTimeRange(rd.getTimeRange());
		
		try {
			rgr.getXs().add(TimeTools.getYMlist(rd.getTimeRange()));
			rgr.getXs().add(tg.getLandEngers());
			rgr.getXs().add(tg.getRoadGoodsTonTypeAll());
			rgr.getXs().add(tg.getRoadGoodsEntSizeTypeAll());
			rgr.getXs().add(tg.getCarTypes());//车辆类型
			Map<String,Object> ds = landServiceN.getRoadGoodsTypOther(rd);
			rgr.setEngTypOther((List<EngTypOtherItem>) ds.get("engTypeOther"));
			rgr.setEntTypOther((List<EntTypOtherItem>) ds.get("entTypeOther"));
			rgr.setCarTypOther((List<CarTypOtherItem>) ds.get("carTypOther"));
		} catch (Exception e) {
			logger.error(e.toString(),e);
			rgr.setErrCode(ErrCode.DATA_ERR);
		}
		
		

		
		return rgr;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/bustranO.json")
	@ResponseBody
	public EngTypOthResponse busTrans(HttpServletResponse response,
										RequestData rd){
		

		
		EngTypOthResponse ir = new EngTypOthResponse();
		ir.setErrCode(ErrCode.DATA_OK);
		ir.setRoleName(rd.getRoleName());
		ir.setTimeRange(rd.getTimeRange());
	
		
		try {
			ir.getXs().add(TimeTools.getYMlist(rd.getTimeRange()));
			ir.getXs().add(tg.getLandEngers());
			ir.getXs().add(tg.getBusTranCarLenTypeAll());
			
			Map<String,Object> ds = landServiceN.getBusTranTypOther(rd);
			ir.setEngTypOther((List<EngTypOtherItem>) ds.get("engTypeOther"));
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ir.setErrCode(ErrCode.DATA_ERR);
		}
		
		return ir;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/taxitranO.json")
	@ResponseBody
	public EngTypOthResponse taxiTrans(HttpServletResponse response,
										RequestData rd){

		EngTypOthResponse ir = new EngTypOthResponse();
		ir.setErrCode(ErrCode.DATA_OK);
		ir.setRoleName(rd.getRoleName());
		ir.setTimeRange(rd.getTimeRange());
		
		try {
			ir.getXs().add(TimeTools.getYMlist(rd.getTimeRange()));
			ir.getXs().add(tg.getLandEngers());
			ir.getXs().add(tg.getTaxiTranDpTypeAll());
			
			Map<String,Object> ds = landServiceN.getTaxiTranTypOther(rd);
			ir.setEngTypOther((List<EngTypOtherItem>) ds.get("engTypeOther"));
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ir.setErrCode(ErrCode.DATA_ERR);
		}
		
		return ir;
	}
	
	
}
