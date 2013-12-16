package com.hskj.util;

import com.hskj.salary.model.PersonCalendar;
import com.hskj.salary.model.Salary;

public class SalaryAlgorithm {

	public static String calculate(Salary salary,PersonCalendar calendar){
		
		String result = null;
		String checkin_time = salary.getCheckin_time();
		String checkout_time = salary.getCheckout_time();
		String every_date = salary.getEvery_date();
		
		String cal_start_time = calendar.getStart_time();
		String cal_end_time = calendar.getEnd_time();
		String cal_every_date = calendar.getEvery_date();
		
		String type_desc = calendar.getType_desc();
		
		if(checkin_time==null){
			result = "未打卡，待处理";
			return result;
		}
		
		if(every_date.equals(cal_every_date)){
			
			if(type_desc!=null){
				
				if(type_desc.equals("早班")||type_desc.contains("节日加班-")){
					//打卡时间晚于下班时间
					result = compareCheckingoutTime(checkout_time, cal_end_time);
					
				}else if(type_desc.equals("夜班")){
					//打卡时间早于上班时间
					result = compareCheckinginTime(checkin_time, cal_start_time);
				}else{
					
					if(checkout_time==null||checkin_time.equals(checkout_time)){
						result = "一条打卡记录，待处理";
						
					}else{
						
						String checkin_result = compareCheckinginTime(checkin_time, cal_start_time);
						String checkout_result = compareCheckingoutTime(checkout_time, cal_end_time);
						
						if(checkin_result!=null){
							result = checkin_result;
						}else if(checkout_result!=null){
							result = checkout_result;
						}
						
						if(checkin_result!=null&&checkout_result!=null){
							
							if(result.equals(checkout_result)){
								
								result = checkin_result;
							}else{
								
								result = checkin_result+"  "+checkout_result;
							}
						}
					}
				}
			}
			
				
			}else{
				System.out.println("&&&&&&&&&&&&&&&&数据异常！");
			}
			
		if(result==null){
			result = "正常";
		}
		return result;
	}
	
	

	/*********************************************
	 * 比较签到时间
	 * @author Administrator<lichuan3992413@gmail.com>
	 * Create at:   2013-10-28 下午03:55:40 
	 * @param checkin_time
	 * @param cal_start_time
	 * @return
	 ********************************************/
	private static String compareCheckinginTime(String checkin_time,String cal_start_time){
		
		String result = null;
		//打卡时间早于上班时间
		if(checkin_time.compareTo(cal_start_time)>0){
			
			if(DateUtil.getTwoDay(checkin_time, cal_start_time).equals("0")){
				
				int minutes = DateUtil.getTwoMinutes(checkin_time, cal_start_time);
				
				if(minutes<=20){
					
					
					result = "迟到-免";
					//迟到-免  3次内免
				}else if(20<minutes&&minutes<=60){
					//迟到
					result = "迟到-罚";
					
				}else if(60<minutes&&minutes<=240){
					
//					result= "旷工半天";
					result= "考勤异常，待处理";
				}else{
					
					//旷工
//					result = "旷工一天";
					result = "考勤异常，待处理";
				}
				
			}
			
			}
		return result;
	}

	/*********************************************
	 *  比较签退时间
	 * @author Administrator<lichuan3992413@gmail.com>
	 * Create at:   2013-10-28 下午03:55:34 
	 * @param checkout_time
	 * @param cal_end_time
	 * @return
	 ********************************************/
	private static String compareCheckingoutTime(String checkout_time,String cal_end_time){
		
		String result = null;
		if(cal_end_time!=null&&checkout_time!=null){
			
			if(checkout_time.compareTo(cal_end_time)<0){
				
				result = "考勤异常，待处理";	
			}
		}else if(cal_end_time==null){
			result = "日历数据有异常，请联系管理员!";
			
		}else{
			result = "签退数据有问题，请联系管理员!";
		}
		return result;
	}
}
