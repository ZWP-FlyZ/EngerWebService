package service.app.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BaseDao {
	@Insert("insert into test set name = #{name},age = #{age}")
	public int addUser(@Param("name")String name,@Param("age")int age);
}
