package com.lichuan.attendance.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.lichuan.attendance.model.AdminUser;
import com.lichuan.attendance.model.PersonStatistic;

/**
 * 
 * @author 1202211 admin_user 操作
 */

@Repository
public interface AdminUserMapper {

	@Select("select * from admin_user" +
			" where status=0 and  admin_name like CONCAT('%',#{user_name},'%' )  group by user_name order by user_name,status")
	List<AdminUser> queryByUserName(String user_name);
	
	// 按照sn进行查询记录
	@Select("SELECT * FROM admin_user WHERE  status=0 and sn = #{sn}")
	AdminUser getInfoBySn(int sn);
	
	@Select("SELECT * FROM admin_user WHERE  status=0 and admin_id = #{name} or admin_name = #{name}")
	AdminUser getInfoByNAME(String name);
	
	@Select("SELECT * FROM admin_user WHERE  status=0 and user_id = #{user_id}")
	AdminUser getInfoByUseId(String user_id);

	@Insert("insert into admin_user(admin_name,admin_pwd,admin_id,mobile,email,status,insert_time,update_time,admin_qp,emp_id)"
			+ " values(#{admin_name},#{admin_pwd},#{admin_id},#{mobile}"
			+ " ,#{email},#{status},now(),now(),#{admin_qp},#{emp_id})")
	int insert(AdminUser adminUser);

	@Select("SELECT * FROM admin_user WHERE  status=0 and admin_id = #{admin_id}")
	AdminUser getInfoByAdminId(String admin_id);
	

	@Select("SELECT * FROM admin_user WHERE  status=0 and admin_name = #{admin_name} limit 1")
	AdminUser getInfoByAdminName(String admin_name);
	
	@Select("SELECT * FROM admin_user WHERE  status=0 and department like #{department}\"%\" order by department,admin_name")
	List<AdminUser> getInfoByDepartment(String department);

	// 获取Admin_User列表
	@Select("SELECT * FROM admin_user where status=0 order by admin_name")
	List<AdminUser> getList();
	
	// 获取Admin_User列表
	@Select("SELECT a.admin_id,a.admin_name,a.department,a.check_id,a.emp_id FROM admin_user a,person_info p where ((#{every_month}>=p.entry_time and p.status=0) or (#{every_month}>=p.entry_time and #{every_month}<=p.turnover_time and p.status=1)) and a.emp_id=p.oa order by a.admin_name")
	List<AdminUser> getListByInService(String every_month);
	// 获取部门列表
	@Select("SELECT department FROM admin_user where status=0 group by department")
	List<AdminUser> getDepartmentList();
	
	@Select("SELECT * FROM admin_user where status=0 order by department,admin_name")
	List<AdminUser> getListBySort();

	@Update("update admin_user set admin_id=#{admin_id},admin_name=#{admin_name},admin_pwd=#{admin_pwd},update_time=now(),mobile=#{mobile},"
			+ "email=#{email},status=#{status},admin_qp=#{admin_qp},emp_id=#{emp_id} WHERE sn=#{sn}")
	int update(AdminUser form);

	@Select("SELECT * FROM admin_user WHERE emp_id = #{emp_id}")
	AdminUser getInfoByEmpId(String emp_id);
	
	@Select("SELECT admin_id FROM admin_user WHERE emp_id = #{validateValue} or admin_id=#{validateValue}")
	AdminUser getInfoByAdminIdOrEmpId(String validateValue);
	
//	@Update("update admin_user a,salary s set s.oa=a.emp_id ,s.id=a.check_id where a.admin_name=s.user_name and (s.oa is null or id is null)")
	@Update("update salary s,admin_user a set s.oa=a.emp_id ,s.id=a.check_id where s.user_name = a.admin_name")
	int updateOA();

	@Select("SELECT  * FROM admin_user  where emp_id not in(select oa from confirm_record where every_month=#{every_month} )")
	List<AdminUser> getUnconfirmUsers(String every_month);
}
