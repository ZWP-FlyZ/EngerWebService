package service.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import service.app.model.PortProData;

@Mapper
public interface PortProDao {
	
	public List<PortProData> getProtProAll(
			String startTime,
			String endTime,
			String enterprice,
			String place1,
			String place2
			);
	
}
