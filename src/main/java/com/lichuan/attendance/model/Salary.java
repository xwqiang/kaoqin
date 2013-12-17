package com.lichuan.attendance.model;

public class Salary implements Comparable<Salary>{

	private int sn;
	private String id;
	private String every_date;
	private String checkin_time;
	private String checkout_time;
	private String every_month;
	private int type;
	private String comment;
	private int comment_type;//0:正常 1：迟到免，迟到罚 2：需要人资处理（待处理） 3：人资已经处理
	public int getComment_type() {
		return comment_type;
	}
	public void setComment_type(int commentType) {
		comment_type = commentType;
	}
	private String user_name;
	private String show_checkin_time;//只保留时分秒
	private String oa;
	private int status;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getOa() {
		return oa;
	}
	public void setOa(String oa) {
		this.oa = oa;
	}
	public String getShow_checkin_time() {
		return show_checkin_time;
	}
	public void setShow_checkin_time(String showCheckinTime) {
		show_checkin_time = showCheckinTime;
	}
	public String getShow_checkout_time() {
		return show_checkout_time;
	}
	public void setShow_checkout_time(String showCheckoutTime) {
		show_checkout_time = showCheckoutTime;
	}
	private String show_checkout_time;//只保留时分秒
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String userName) {
		user_name = userName;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	private String department;
	private AdminUser adminUser;
	
	public AdminUser getAdminUser() {
		return adminUser;
	}
	public void setAdminUser(AdminUser adminUser) {
		this.adminUser = adminUser;
	}
	public String getCheckin_time() {
		return checkin_time;
	}
	public String getCheckout_time() {
		return checkout_time;
	}
	
	public String getComment() {
		return comment;
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
	public int getType() {
		return type;
	}
	public void setCheckin_time(String checkinTime) {
		checkin_time = checkinTime;
	}
	public void setCheckout_time(String checkoutTime) {
		checkout_time = checkoutTime;
	}
	public void setComment(String comment) {
		this.comment = comment;
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
	public void setType(int type) {
		this.type = type;
	}

	public int compareTo(Salary o) {
		
		return (this.department + "_" + this.oa+ "-" +this.user_name + "_" + this.every_date).compareTo(o.getDepartment() + "_" + o.oa + "-" + o.user_name + "_" + o.getEvery_date());
	}
	
}
