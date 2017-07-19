package service.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import service.app.model.OceanGoodsData;


@Mapper
public interface OceanGdsDao {
	public List<OceanGoodsData> getOceanGdsAll(
			String startTime,
			String endTime,
			String enterprice,
			String place1,
			String place2
			);
}


