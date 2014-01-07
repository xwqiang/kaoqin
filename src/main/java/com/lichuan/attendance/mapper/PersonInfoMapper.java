package com.lichuan.attendance.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.lichuan.attendance.model.AnnualLeaveInfo;
import com.lichuan.attendance.model.PersonInfo;

/**
 * 
 * @author 1202211 admin_user 操作
 */

@Repository
public interface PersonInfoMapper {

	@Select("SELECT * FROM person_info WHERE oa = #{oa}")
	PersonInfo getInfoByOA(String oa);
	
	@Select("SELECT * FROM annual_leave_info where oa=#{oa} and every_year=#{every_year}")
	AnnualLeaveInfo getInfoByOAYear(Map<String,String> map);
	
	@Select("SELECT * FROM annual_leave_info where oa=#{oa}")
	List<AnnualLeaveInfo> getAnnualLeaveListByOA(String oa);
	
	@Insert("insert into annual_leave_info values(null,#{oa},#{every_year},#{annual_leave},#{surplus_annual_leave},#{status})")
	int saveAnnualLeave(Map<String,String> map);
	
	@Delete("delete from annual_leave_info where oa=#{oa} and every_year=#{every_year}")
	void delateAnnualLeaveByOAYear(Map<String,String> map);
	
	//更新年假数据
	@Update("update annual_leave_info set annual_leave=#{annual_leave},surplus_annual_leave=#{surplus_annual_leave},status=#{status} where oa=#{oa} and every_year=#{every_year}")
	void updateAnnualLeave(Map<String,String> map);
		
		
	@Select("select count(*) from annual_leave_info where oa=#{oa} and every_year=#{every_year}")
	int isExist(Map<String,String> map);

}
