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

import service.app.server.WaterDataService;
import service.app.tramodel.ErrCode;
import service.app.tramodel.RequestData;
import service.app.tramodel.items.BaseTypOtherItem;
import service.app.tramodel.items.EngTypOtherItem;
import service.app.tramodel.items.EntTypOtherItem;
import service.app.tramodel.items.WeiTypOtherItem;
import service.app.tramodel.response.OceanGoodsResponse;
import service.app.tramodel.response.OceanPassResponse;
import service.app.tramodel.response.PortProductResponse;
import service.app.tramodel.response.RiverTranResponse;
import service.app.util.TimeTools;
import service.app.util.TypeGetter;

@Controller
public class WaterController {

	@Autowired
	WaterDataService wds;
	
	@Autowired
	TypeGetter tg;
	
	private final static Logger logger = LoggerFactory.getLogger(WaterController.class);
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/rivertranO.json")
	@ResponseBody
	public RiverTranResponse riverTran(HttpServletResponse response,
										RequestData rd){

		

		RiverTranResponse trt = new RiverTranResponse();
		trt.setErrCode(ErrCode.DATA_OK);
		trt.setRoleName(rd.getRoleName());
		trt.setTimeRange(rd.getTimeRange());
		
		try {

			trt.getXs().add(TimeTools.getYMlist(rd.getTimeRange()));
			trt.getXs().add(tg.getWaterEngers());
			trt.getXs().add(tg.getRiverTranTonTypeAll());
			trt.getXs().add(tg.getRiverTranEntSTypeAll());
			trt.getXs().add(tg.getShipTypes());//车辆类型
			
			Map<String,Object> ds = wds.getRiverTranTypeOther(rd);
			trt.setEngTypOther((List<EngTypOtherItem>) ds.get("engTypeOther"));
			trt.setEntTypOther((List<EntTypOtherItem>) ds.get("entTypeOther"));
			trt.setWeiTypOther((List<WeiTypOtherItem>) ds.get("weiTypOther"));
		} catch (Exception e) {
			logger.error(e.toString());
			trt.setErrCode(ErrCode.DATA_ERR);
		}
		
		return trt;
	}
	
	
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/oceangoodsO.json")
	@ResponseBody
	public OceanGoodsResponse oceanGoods(HttpServletResponse response,
										RequestData rd){

		OceanGoodsResponse ogr = new OceanGoodsResponse();
		ogr.setErrCode(ErrCode.DATA_OK);
		ogr.setRoleName(rd.getRoleName());
		ogr.setTimeRange(rd.getTimeRange());
		
		try {
			ogr.getXs().add(TimeTools.getYMlist(rd.getTimeRange()));
			ogr.getXs().add(tg.getWaterEngers());
			ogr.getXs().add(tg.getOceanGoodsTonTypeAll());
			ogr.getXs().add(tg.getOceanGoodsEntSTypeAll());
			ogr.getXs().add(tg.getShipTypes());//车辆类型
			ogr.getXs().add(tg.getOceanGoodsTranDisTypeAll());//车辆类型
			
			Map<String,Object> ds = wds.getOceanGoodsTypeOther(rd);
			ogr.setEngTypOther((List<EngTypOtherItem>) ds.get("engTypeOther"));
			ogr.setEntTypOther((List<EntTypOtherItem>) ds.get("entTypeOther"));
			ogr.setWeiTypOther((List<WeiTypOtherItem>) ds.get("weiTypOther"));
		} catch (Exception e) {
			logger.error(e.toString());
			ogr.setErrCode(ErrCode.DATA_ERR);
		}
		


		return ogr;
		

	}
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/oceanpassO.json")
	@ResponseBody
	public OceanPassResponse oceanPass(HttpServletResponse response,
										RequestData rd){

		
		OceanPassResponse opr = new OceanPassResponse();
		opr.setErrCode(ErrCode.DATA_OK);
		opr.setRoleName(rd.getRoleName());
		opr.setTimeRange(rd.getTimeRange());
		
		try {
			opr.getXs().add(TimeTools.getYMlist(rd.getTimeRange()));
			opr.getXs().add(tg.getWaterEngers());
			opr.getXs().add(tg.getOceanPassSitSizeTypeAll());
			opr.getXs().add(tg.getOceanPassEntSTypeAll());
			opr.getXs().add(tg.getOceanPassTranDisTypeAll());//运距类型
			
			Map<String,Object> ds = wds.getOceanPassTypeOther(rd);
			opr.setEngTypOther((List<EngTypOtherItem>) ds.get("engTypeOther"));
			opr.setEntTypOther((List<EntTypOtherItem>) ds.get("entTypeOther"));
			opr.setDisTypOther((List<BaseTypOtherItem>) ds.get("disTypOther"));
		} catch (Exception e) {
			logger.error(e.toString());
			opr.setErrCode(ErrCode.DATA_ERR);
		}
		return opr;

	}
	

	
	@SuppressWarnings("unchecked")
	@RequestMapping("/portproduceO.json")
	@ResponseBody
	public PortProductResponse portProduce(HttpServletResponse response,
										RequestData rd){


		PortProductResponse ppr = new PortProductResponse();
		ppr.setErrCode(ErrCode.DATA_OK);
		ppr.setRoleName(rd.getRoleName());
		ppr.setTimeRange(rd.getTimeRange());
		try {
			ppr.getXs().add(TimeTools.getYMlist(rd.getTimeRange()));
			ppr.getXs().add(tg.getWaterEngers());
			ppr.getXs().add(tg.getPortProEntSTypeAll());
			Map<String ,Object> ds = wds.getPortProductTypeOther(rd);
			ppr.setEngTypOther((List<EngTypOtherItem>)ds.get("engTypeOther"));
			ppr.setEntTypOther((List<EntTypOtherItem>)ds.get("entTypeOther"));
		} catch (Exception e) {
			logger.error(e.toString());
			ppr.setErrCode(ErrCode.DATA_ERR);
		}
		
	
		return ppr;
	}
	
	
}
