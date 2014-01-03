package com.lichuan.attendance.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.lichuan.attendance.model.ConfirmRecord;
import com.lichuan.attendance.model.PersonCalendar;
import com.lichuan.attendance.model.PersonStatistic;
import com.lichuan.attendance.model.Salary;
import com.lichuan.attendance.model.SalaryDetail;

/**
 *
 * Description:  salary 表操作
 *
 * @author Administrator<lichuan3992413@gmail.com>
 *
 * Create at:   2013-10-16 下午03:45:05 
 */
@Repository
public interface ConfirmRecordMapper {

	@Insert("insert into confirm_record(sn,oa,user_name,department,every_month,confirm_time,type) values(null,#{oa},#{user_name},#{department},#{every_month},now(),#{type})")
	int save(Map<String,String> map);
	
	@Select("SELECT * FROM confirm_record WHERE oa=#{oa} and every_month = #{every_month}")
	List<ConfirmRecord> queryListByOAMonth(Map<String,String> map);
	
	@Select("SELECT * FROM confirm_record WHERE every_month = #{every_month}")
	List<ConfirmRecord> queryListByMonth(Map<String,String> map);
}
