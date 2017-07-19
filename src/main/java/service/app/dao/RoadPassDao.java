package service.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import service.app.model.RoadPassData;

@Mapper
public interface RoadPassDao {
	public List<RoadPassData> getRoadPassAll(
			String startTime,
			String endTime,
			String enterprice,
			String place1,
			String place2
			);
	
}
