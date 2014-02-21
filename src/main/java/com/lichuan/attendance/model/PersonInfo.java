package com.lichuan.attendance.model;

import java.util.List;
import java.util.Map;

public class PersonInfo {

	private int sn;
	private String oa;
	private String user_name;
	private String department;
	private String entry_time; //入职时间
	private String turnover_time; //离职时间
	private int status;
	private String start_work_time;//参加工作时间
	private Map<String,Double> map;//key:every year values:年假天数
	private List<AnnualLeaveInfo> list;
	private int sequence;//排序
	
	/**
	 * 获取总剩余年假
	 * @return
	 */
	public double getTotalSurPlusAnnualLeave(){
		
		double totalSurplus = 0;
		if(null!=this.list){
			
			for(AnnualLeaveInfo each:list){
				if(each.getStatus()==1)
					continue;
				totalSurplus += each.getSurplus_annual_leave();
			}
		}
		return totalSurplus;
	}
	public AnnualLeaveInfo getAnnualLeaveInfoByYear(String year){
		
		if(year!=null&&null!=this.list){
			
			for(AnnualLeaveInfo each:list){
				String every_year = each.getEvery_year();
				if(year.equals(every_year)){
					return each;
				}
			}
		}
		return null;
	}
	public List<AnnualLeaveInfo> getList() {
		return list;
	}
	public void setList(List<AnnualLeaveInfo> list) {
		this.list = list;
	}
	public Map<String, Double> getMap() {
		return map;
	}
	public void setMap(Map<String, Double> map) {
		this.map = map;
	}
	public String getStart_work_time() {
		return start_work_time;
	}
	public void setStart_work_time(String start_work_time) {
		this.start_work_time = start_work_time;
	}
	public int getSn() {
		return sn;
	}
	public void setSn(int sn) {
		this.sn = sn;
	}
	public String getOa() {
		return oa;
	}
	
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public void setOa(String oa) {
		this.oa = oa;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getEntry_time() {
		return entry_time;
	}
	public void setEntry_time(String entry_time) {
		this.entry_time = entry_time;
	}
	public String getTurnover_time() {
		return turnover_time;
	}
	public void setTurnover_time(String turnover_time) {
		this.turnover_time = turnover_time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
