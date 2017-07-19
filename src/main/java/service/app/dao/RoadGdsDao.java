package service.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import service.app.model.RoadGoodsData;


@Mapper
public interface RoadGdsDao {

	public List<RoadGoodsData> getRoadGdsAll(			
			String startTime,
			String endTime,
			String enterprice,
			String place1,
			String place2
			);
	
}
