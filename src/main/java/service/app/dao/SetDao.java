package service.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import service.app.model.AllTypesItem;
import service.app.model.UserInfo;
import service.app.tramodel.RequestData;

@Mapper
public interface SetDao {
	
	//@Select("SELECT * FROM trafficenger.userinfo WHERE roleType != 'R_ADMIN'")
	@Select("SELECT * FROM userinfo WHERE roleType != 'R_ADMIN'")
	public List<UserInfo> getUsers();
	
	@Select("SELECT COUNT(username) FROM userinfo "
			+ "WHERE username = #{username} LIMIT 1;")
	public int isContainUser(@Param(value="username")String username);
		
	
	@Insert("INSERT INTO `userinfo` "
			+ "(`username`, `password`, `userAuth`, `name`, `phone`, `roleType`, `roleName`, `place1`, `place2`,`upAuth`) "
			+ "VALUES (#{userinfo.username}, #{userinfo.password}, '0',"
			+ " #{userinfo.name}, #{userinfo.phone}, #{userinfo.roleType}, #{userinfo.roleName},"
			+ "#{userinfo.place1}, #{userinfo.place2},#{userinfo.upAuth})")
	public int reguser(@Param(value="userinfo")RequestData userinfo);
	
	
	@Delete("DELETE FROM `userinfo` WHERE username = #{username} AND roleType!= 'R_ADMIN'")
	public int deluser(@Param(value="username")String username);
	
	
	
	
	@Update("UPDATE`userinfo` "
			+ "SET `name`=#{userinfo.name}, `phone`=#{userinfo.phone}, `roleType`=#{userinfo.roleType}, "
			+ "`roleName`=#{userinfo.roleName}, `place1`=#{userinfo.place1}, `place2`=#{userinfo.place2} "
			+ " WHERE username = #{userinfo.username} AND roleType!= 'R_ADMIN'")
	public int edituser(@Param(value="userinfo")RequestData userinfo);
	
	@Update("UPDATE`userinfo` "
			+ "SET `password`=#{userinfo.password} "
			+ " WHERE username = #{userinfo.username}")
	public int setpass(@Param(value="userinfo")RequestData userinfo);
	
	
	
	@Select("SELECT * FROM alltypes")
	public List<AllTypesItem> getalltypes();
	
	@Select("SELECT * FROM alltypes WHERE typeName = #{typeName}")
	public AllTypesItem gettypebyname(@Param(value="typeName")String typeName);
	
	
	@Update("UPDATE alltypes SET typeS = #{typeS} WHERE typeName = #{typeName}")
	public int settypebyname(@Param(value="typeName")String typeName,
								@Param(value="typeS")String typeS);
	
	
	
	
}
