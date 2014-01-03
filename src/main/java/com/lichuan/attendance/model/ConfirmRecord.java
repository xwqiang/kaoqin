package com.lichuan.attendance.model;

/**
 * 确认记录
 * @author Administrator
 *
 */
public class ConfirmRecord {

	private int sn;
	private String oa;
	private String user_name;
	private String department;
	private String confirm_time;
	private String every_month;
	private int type;
	public String getConfirm_time() {
		return confirm_time;
	}
	public String getDepartment() {
		return department;
	}
	public String getEvery_month() {
		return every_month;
	}
	public String getOa() {
		return oa;
	}
	public int getSn() {
		return sn;
	}
	public int getType() {
		return type;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setConfirm_time(String confirm_time) {
		this.confirm_time = confirm_time;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public void setEvery_month(String every_month) {
		this.every_month = every_month;
	}
	public void setOa(String oa) {
		this.oa = oa;
	}
	public void setSn(int sn) {
		this.sn = sn;
	}
	public void setType(int type) {
		this.type = type;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
}
