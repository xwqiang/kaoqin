package com.lichuan.attendance.model;

import java.util.Map;

public class PersonStatistic{

	private String user_name;
	private String id;
	private String oa;
	private String department;
	private String comment;
	private String result;
	private int status;
	private int count;//次数
	private int late_absolve;//迟到-免次数
	private int late_fine;//迟到-罚次数
	private int forget_checkin;//忘签退次数
	private int forget_fine;//忘签退/忘签到处罚次数
	private int forget_checkout;//忘签到次数
	private String every_month;
	private double working_date_count;//工作天数
	private double holidays_count;//假日
	private double overtime_count;//休息日加班
	private double workdate_overtime_count;//工作日加班
	public double getWorkdate_overtime_count() {
		return workdate_overtime_count;
	}
	public void setWorkdate_overtime_count(double workdate_overtime_count) {
		this.workdate_overtime_count = workdate_overtime_count;
	}
	private double holiday_overtime_count;//假日加班
	private double unknown_count; //未知待处理
	private double office_date_count;//办公室工作天数
	private double real_working_date_count;//实际工作天数
	private double fanbu_date_count;//饭补天数
	private double undetermined_count;//待处理天数
	public double getUndetermined_count() {
		return undetermined_count;
	}
	public void setUndetermined_count(double undetermined_count) {
		this.undetermined_count = undetermined_count;
	}
	private int fine;//罚款
	private double waichu_count;
	private double shijia_count;
	private double bingjia_count;
	private double chuchai_count;
	private double tiaoxiu_count;
	private double nianjia_count;
	private double hunjia_count;
	private double chanjia_count;
	private double sangjia_count;
	private double kuanggong_count;
	private Map<String,Double> map;
	public double getBingjia_count() {
		return bingjia_count;
	}
	public double getChanjia_count() {
		return chanjia_count;
	}
	public double getChuchai_count() {
		return chuchai_count;
	}
	public String getComment() {
		return comment;
	}
	public int getCount() {
		return count;
	}
	public String getDepartment() {
		return department;
	}
	public String getEvery_month() {
		return every_month;
	}
	public double getFanbu_date_count() {
		return fanbu_date_count;
	}

	public int getFine() {
		return fine;
	}
	public int getForget_checkin() {
		return forget_checkin;
	}
	public int getForget_checkout() {
		return forget_checkout;
	}
	public int getForget_fine() {
		return forget_fine;
	}
	public double getHoliday_overtime_count() {
		return holiday_overtime_count;
	}
	public double getHolidays_count() {
		return holidays_count;
	}
	public double getHunjia_count() {
		return hunjia_count;
	}
	public String getId() {
		return id;
	}
	public double getKuanggong_count() {
		return kuanggong_count;
	}
	public int getLate_absolve() {
		return late_absolve;
	}
	public int getLate_fine() {
		return late_fine;
	}
	public Map<String, Double> getMap() {
		return map;
	}
	
	
	public double getNianjia_count() {
		return nianjia_count;
	}
	public String getOa() {
		return oa;
	}
	public double getOffice_date_count() {
		return office_date_count;
	}
	public double getOvertime_count() {
		return overtime_count;
	}
	public double getReal_working_date_count() {
		return real_working_date_count;
	}
	public String getResult() {
		return result;
	}
	public double getSangjia_count() {
		return sangjia_count;
	}
	public double getShijia_count() {
		return shijia_count;
	}
	public int getStatus() {
		return status;
	}
	
	public double getTiaoxiu_count() {
		return tiaoxiu_count;
	}
	public double getUnknown_count() {
		return unknown_count;
	}
	public String getUser_name() {
		return user_name;
	}
	public double getWaichu_count() {
		return waichu_count;
	}
	public double getWorking_date_count() {
		return working_date_count;
	}
	public void setBingjia_count(double bingjia_count) {
		this.bingjia_count = bingjia_count;
	}
	public void setChanjia_count(double chanjia_count) {
		this.chanjia_count = chanjia_count;
	}
	public void setChuchai_count(double chuchai_count) {
		this.chuchai_count = chuchai_count;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public void setEvery_month(String everyMonth) {
		every_month = everyMonth;
	}

	public void setFanbu_date_count(double fanbuDateCount) {
		fanbu_date_count = fanbuDateCount;
	}
	public void setFine(int fine) {
		this.fine = fine;
	}
	public void setForget_checkin(int forgetCheckin) {
		forget_checkin = forgetCheckin;
	}
	public void setForget_checkout(int forgetCheckout) {
		forget_checkout = forgetCheckout;
	}
	public void setForget_fine(int forgetFine) {
		forget_fine = forgetFine;
	}
	public void setHoliday_overtime_count(double holidayOvertimeCount) {
		holiday_overtime_count = holidayOvertimeCount;
	}
	public void setHolidays_count(double holidaysCount) {
		holidays_count = holidaysCount;
	}
	public void setHunjia_count(double hunjia_count) {
		this.hunjia_count = hunjia_count;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setKuanggong_count(double kuanggong_count) {
		this.kuanggong_count = kuanggong_count;
	}
	public void setLate_absolve(int lateAbsolve) {
		late_absolve = lateAbsolve;
	}
	
	public void setLate_fine(int lateFine) {
		late_fine = lateFine;
	}
	public void setMap(Map<String, Double> map) {
		this.map = map;
	}
	public void setNianjia_count(double nianjia_count) {
		this.nianjia_count = nianjia_count;
	}
	public void setOa(String oa) {
		this.oa = oa;
	}
	public void setOffice_date_count(double officeDateCount) {
		office_date_count = officeDateCount;
	}
	public void setOvertime_count(double overtimeCount) {
		overtime_count = overtimeCount;
	}
	public void setReal_working_date_count(double realWorkingDateCount) {
		real_working_date_count = realWorkingDateCount;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public void setSangjia_count(double sangjia_count) {
		this.sangjia_count = sangjia_count;
	}
	public void setShijia_count(double shijia_count) {
		this.shijia_count = shijia_count;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setTiaoxiu_count(double tiaoxiu_count) {
		this.tiaoxiu_count = tiaoxiu_count;
	}
	public void setUnknown_count(double unknownCount) {
		unknown_count = unknownCount;
	}
	public void setUser_name(String userName) {
		user_name = userName;
	}
	public void setWaichu_count(double waichu_count) {
		this.waichu_count = waichu_count;
	}
	public void setWorking_date_count(double workingDateCount) {
		working_date_count = workingDateCount;
	}

}
