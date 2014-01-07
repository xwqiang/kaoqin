package com.lichuan.attendance.model;

public class AnnualLeaveInfo {

	private int sn;
	public int getSn() {
		return sn;
	}
	public void setSn(int sn) {
		this.sn = sn;
	}
	public String getOa() {
		return oa;
	}
	public void setOa(String oa) {
		this.oa = oa;
	}
	public double getAnnual_leave() {
		return annual_leave;
	}
	public void setAnnual_leave(double annual_leave) {
		this.annual_leave = annual_leave;
	}
	public double getSurplus_annual_leave() {
		return surplus_annual_leave;
	}
	public void setSurplus_annual_leave(double surplus_annual_leave) {
		this.surplus_annual_leave = surplus_annual_leave;
	}
	public String getEvery_year() {
		return every_year;
	}
	public void setEvery_year(String every_year) {
		this.every_year = every_year;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	private String oa;
	private double annual_leave;
	private double surplus_annual_leave;
	private String every_year;
	private int status;
}
