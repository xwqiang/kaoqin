package com.lichuan.attendance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichuan.attendance.mapper.AdminUserMapper;
import com.lichuan.attendance.model.AdminUser;

/**
 * 
 * @author 1202211 admin_user service
 */

@Service
public class AdminUserService {
		
	@Autowired
	private AdminUserMapper adminUserMapper;
	
	

	public List<AdminUser> queryByUserName(String user_name) {
		
		List<AdminUser> list=adminUserMapper.queryByUserName(user_name);
		
		return list;
	}
	
	public AdminUser getInfoByEmp_id(String emp_id) {

		return adminUserMapper.getInfoByEmp_id(emp_id);
	}
	
	// 通过SN获取用户信息
	public AdminUser getInfoBySn(String sn) {
		return adminUserMapper.getInfoBySn(Integer.parseInt(sn));
	}
	
	public AdminUser getInfoByUseId(String userId) {
		return adminUserMapper.getInfoByUseId(userId);
	}

	public boolean insert(AdminUser adminUser) {
		if (adminUserMapper.insert(adminUser) > 0) {
			return true;
		} else {
			return false;
		}
	}

	
	// 通过admin_id获取用户信息
	public AdminUser getInfoByAdminId(String id) {
		return adminUserMapper.getInfoByAdminId(id);
	}

	// 通过admin_name获取用户信息
	public AdminUser getInfoByAdminName(String admin_name) {
		if(admin_name==null){
			return null;
		}
		return adminUserMapper.getInfoByAdminName(admin_name);
	}
	
	// 通过department获取用户信息
	public List<AdminUser> getInfoByDepartment(String department) {
		if(department==null){
			return null;
		}
		return adminUserMapper.getInfoByDepartment(department);
	}
	
	// 通过admin_name获取用户信息
	public AdminUser getInfoByNAME(String admin_name) {
		return adminUserMapper.getInfoByNAME(admin_name);
	}

	// 获取Admin_user列表
	public List<AdminUser> getList() {
		return adminUserMapper.getList();
	}
	
	// 获取Admin_user列表
	public List<AdminUser> getDepartmentList() {
		return adminUserMapper.getDepartmentList();
	}
	
	// 获取Admin_user列表
	public List<AdminUser> getListBySort() {
		return adminUserMapper.getListBySort();
	}

	public boolean update(AdminUser adminUser) {
		int m = adminUserMapper.update(adminUser);
		if (m >= 0) {

			return true;
		} else {
			return false;

		}

	}

}
