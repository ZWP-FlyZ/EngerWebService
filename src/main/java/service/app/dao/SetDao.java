package service.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import service.app.model.UserInfo;
import service.app.tramodel.RequestData;

@Mapper
public interface SetDao {
	
	//@Select("SELECT * FROM trafficenger.userinfo WHERE roleType != 'R_ADMIN'")
	@Select("SELECT * FROM trafficenger.userinfo WHERE roleType != ''")
	public List<UserInfo> getUsers();
	
	@Select("SELECT COUNT(username) FROM trafficenger.userinfo "
			+ "WHERE username = #{username} LIMIT 1;")
	public int isContainUser(@Param(value="username")String username);
		
	
	@Insert("INSERT INTO `trafficenger`.`userinfo` "
			+ "(`username`, `password`, `userAuth`, `name`, `phone`, `roleType`, `roleName`, `place1`, `place2`) "
			+ "VALUES (#{userinfo.username}, #{userinfo.password}, '0',"
			+ " #{userinfo.name}, #{userinfo.phone}, #{userinfo.roleType}, #{userinfo.roleName},"
			+ "#{userinfo.place1}, #{userinfo.place2})")
	public int reguser(@Param(value="userinfo")RequestData userinfo);
	
	
	@Delete("DELETE FROM `trafficenger`.`userinfo` WHERE username = #{username} AND roleType!= 'R_ADMIN'")
	public int deluser(@Param(value="username")String username);
	
	
	
	
	@Update("UPDATE `trafficenger`.`userinfo` "
			+ "SET `name`=#{userinfo.name}, `phone`=#{userinfo.phone}, `roleType`=#{userinfo.roleType}, "
			+ "`roleName`=#{userinfo.roleName}, `place1`=#{userinfo.place1}, `place2`=#{userinfo.place2} "
			+ " WHERE username = #{userinfo.username} AND roleType!= 'R_ADMIN'")
	public int edituser(@Param(value="userinfo")RequestData userinfo);
	
}
