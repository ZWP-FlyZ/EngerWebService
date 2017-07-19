package service.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import service.app.model.RiverTranData;

@Mapper
public interface RiverTranDao {
	public List<RiverTranData> getRiverTranAll(
			String startTime,
			String endTime,
			String enterprice,
			String place1,
			String place2
			);
}
