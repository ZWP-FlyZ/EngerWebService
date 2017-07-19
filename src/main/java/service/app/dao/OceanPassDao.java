package service.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import service.app.model.OceanPassData;

@Mapper
public interface OceanPassDao {

	public List<OceanPassData> getOceanPassAll(
			String startTime,
			String endTime,
			String enterprice,
			String place1,
			String place2
			);
	
}
