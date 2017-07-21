package service.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import service.app.model.RoadPassData;

@Mapper
public interface RoadPassDao {
	
	
	@Select("select * from "+
		"(SELECT * FROM trafficenger.roadpass "+
        "where inTime >= '2017-01-01' and inTime <= '2017-04-30') as t1 "+
        " where companyId like '%%' ; ")
	public List<RoadPassData> getRoadPassAll(
			String startTime,
			String endTime,
			String enterprice,
			String place1,
			String place2
			);
	
}
