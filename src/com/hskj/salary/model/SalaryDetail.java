package com.hskj.salary.model;

public class SalaryDetail {

	private int sn;
	private String id;
	private String each_time;
	private String machine_id;
	private String user_name;
	private String department;
	public String getDepartment() {
		return department;
	}
	public String getEach_time() {
		return each_time;
	}
	public String getId() {
		return id;
	}
	public String getMachine_id() {
		return machine_id;
	}
	
	public int getSn() {
		return sn;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public void setEach_time(String eachTime) {
		each_time = eachTime;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setMachine_id(String machineId) {
		machine_id = machineId;
	}
	public void setSn(int sn) {
		this.sn = sn;
	}
	public void setUser_name(String userName) {
		user_name = userName;
	}
}
