package service.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import service.app.model.TaxiTranData;

@Mapper
public interface TaxiTranDao {
	
	@Select("SELECT * FROM "
			+ "(SELECT * FROM "
			+ "(SELECT * FROM taxitran "
			+ "WHERE inTime >=  #{startTime} AND inTime <= #{endTime}) AS t1 "
			+ "WHERE companyId LIKE #{enterprice}) AS t2 "
			+ "WHERE place1 LIKE  #{place1} AND place2 LIKE #{place2} ")
	public List<TaxiTranData> getTaxiTranAll(
			@Param("startTime") String startTime,
			@Param("endTime")String endTime,
			@Param("enterprice")String enterprice,
			@Param("place1")String place1,
			@Param("place2")String place2
			);
}
