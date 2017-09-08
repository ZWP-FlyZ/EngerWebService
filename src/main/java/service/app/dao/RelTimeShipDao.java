package service.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import service.app.model.RelTimeData;
import service.app.model.RelTimeSelctParam;

@Mapper
public interface RelTimeShipDao {

	@Select("SELECT * FROM("
			+ "SELECT * FROM("
			+ "SELECT * FROM ${tName} WHERE inTime >= #{param.startTime} "
			+ "AND inTime <= #{param.endTime}) AS T1"
			+ " WHERE shipId like #{param.shipId}) AS T2 "
			+ "WHERE  companyId like #{param.companyId} AND place1 like #{param.cityType};")
	public List<RelTimeData>getRelTimeData(
			@Param("tName")String tName,
			@Param("param")RelTimeSelctParam param			
			);
	
	@Select("SELECT * FROM ${tName} WHERE inTime >= #{param.startTime} "
			+ "AND inTime <= #{param.endTime}")
	public List<RelTimeData>getRelTimeDataN(
			@Param("tName")String tName,
			@Param("param")RelTimeSelctParam param			
			);
	
}
