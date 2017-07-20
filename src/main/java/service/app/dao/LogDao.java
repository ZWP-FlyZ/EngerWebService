package service.app.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import service.app.model.UserInfo;

@Mapper
public interface LogDao {

	@Select("select * from UserInfo where username = #{username}")
	public UserInfo getInfoForLogIn(@Param(value = "username") String username);
}
