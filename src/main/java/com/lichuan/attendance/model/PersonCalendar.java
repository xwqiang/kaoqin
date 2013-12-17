package com.lichuan.attendance.model;

public class PersonCalendar {

	private int sn;
	private String id;
	private String every_month;
	private String every_date;
	private String start_time;
	private String end_time;
	private int type;
	private String type_desc;
	private String start_date;
	private String oa;
	
	public String getOa() {
		return oa;
	}
	public void setOa(String oa) {
		this.oa = oa;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String startDate) {
		start_date = startDate;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String endDate) {
		end_date = endDate;
	}
	private String end_date;
	
	public String getType_desc() {
		return type_desc;
	}
	public void setType_desc(String typeDesc) {
		type_desc = typeDesc;
	}
	public String getEnd_time() {
		return end_time;
	}
	public String getEvery_date() {
		return every_date;
	}
	public String getEvery_month() {
		return every_month;
	}
	public String getId() {
		return id;
	}
	public int getSn() {
		return sn;
	}
	public String getStart_time() {
		return start_time;
	}
	public int getType() {
		return type;
	}
	public void setEnd_time(String endTime) {
		end_time = endTime;
	}
	public void setEvery_date(String everyDate) {
		every_date = everyDate;
	}
	public void setEvery_month(String everyMonth) {
		every_month = everyMonth;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setSn(int sn) {
		this.sn = sn;
	}
	public void setStart_time(String startTime) {
		start_time = startTime;
	}
	public void setType(int type) {
		this.type = type;
	}
}
