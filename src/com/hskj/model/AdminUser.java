package com.hskj.model;

import java.io.Serializable;

/**
 *
 * Description:  员工表
 *
 * @author Administrator<lichuan3992413@gmail.com>
 *
 * Create at:   2013-10-16 下午02:52:43 
 */
public class AdminUser{
	private int sn;
	private String admin_id;
	private String admin_name;
	private String admin_pwd;
	private String mobile;
	private String email;
	private int status;//0开启; 1关闭
	private String insert_time;
	private String update_time;
	private String admin_qp;
	private String user_id;
	private String check_id;//打卡号
	private AdminUser marketUser;
	private AdminUser serviceUser;
	private String emp_id;
	private String department;//部门
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	private String mail_type_sns;
	public int compareTo(AdminUser o) {
		return 0;
	}

	public String getAdmin_id() {
		return admin_id;
	}

	public String getAdmin_name() {
		return admin_name;
	}

	public String getAdmin_pwd() {
		return admin_pwd;
	}

	public String getAdmin_qp() {
		return admin_qp;
	}

	public String getCheck_id() {
		return check_id;
	}

	public String getEmail() {
		return email;
	}

	public String getEmp_id() {
		return emp_id;
	}

	public String getInsert_time() {
		return insert_time;
	}

	public String getMail_type_sns() {
		return mail_type_sns;
	}

	public AdminUser getMarketUser() {
		return marketUser;
	}

	public String getMobile() {
		return mobile;
	}

	public AdminUser getServiceUser() {
		return serviceUser;
	}

	public int getSn() {
		return sn;
	}

	public int getStatus() {
		return status;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setAdmin_id(String adminId) {
		admin_id = adminId;
	}

	public void setAdmin_name(String adminName) {
		admin_name = adminName;
	}

	public void setAdmin_pwd(String adminPwd) {
		admin_pwd = adminPwd;
	}

	
	public void setAdmin_qp(String adminQp) {
		admin_qp = adminQp;
	}
	public void setCheck_id(String checkId) {
		check_id = checkId;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}

	public void setInsert_time(String insertTime) {
		insert_time = insertTime;
	}

	public void setMail_type_sns(String mail_type_sns) {
		this.mail_type_sns = mail_type_sns;
	}

	public void setMarketUser(AdminUser marketUser) {
		this.marketUser = marketUser;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setServiceUser(AdminUser serviceUser) {
		this.serviceUser = serviceUser;
	}

	public void setSn(int sn) {
		this.sn = sn;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public void setUpdate_time(String updateTime) {
		update_time = updateTime;
	}

	public void setUser_id(String userId) {
		user_id = userId;
	}
	


}
