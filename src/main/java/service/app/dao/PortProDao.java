package service.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import service.app.model.PortProData;

@Mapper
public interface PortProDao {
	
	
	
	@Select(" SELECT * from" +
	 "(SELECT * FROM trafficenger.portproduct "+ 
			"where inTime >= '2017-01-01' and inTime < '2017-05-01') as t1 "+ 
	" where companyId like '%%' ;")
	public List<PortProData> getProtProAll(
			String startTime,
			String endTime,
			String enterprice,
			String place1,
			String place2
			);
	
}
