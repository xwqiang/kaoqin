package com.lichuan.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 年假算法
 * @author Administrator
 *
 */
public class AnnualLeaveAlgorithm {
	
	private static final int JUNIOR = 5; //10年内5天年假
	private static final int MIDDLE = 10; //10-20年10天年假
	private static final int SENIOR = 15; //超过20年15天年假

	public static Map<String,Double> calculate(String start_work_time,String entry_time) {
		
		Map<String,Double> map = new LinkedHashMap<String,Double>();
		
		try {
			
			int number = 1;
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			
			
			int intervalYears = DateUtil.getIntervalYear(entry_time,sdf.format(Calendar.getInstance().getTime()), sdf);
			long intervalDays = DateUtil.getIntervalDate(entry_time,sdf.format(Calendar.getInstance().getTime()), sdf);
			
			double annualLeave = (intervalDays/365)*5;
			
			int currentYear = Calendar.getInstance().get(Calendar.YEAR);
			Calendar entryCal = Calendar.getInstance();
			entryCal.setTime(sdf.parse(entry_time));
			int entryYear = entryCal.get(Calendar.YEAR);
			
			int currentIntervalDays = DateUtil.getIntervalDate(currentYear+"-01-01",sdf.format(Calendar.getInstance().getTime()), sdf);//计算当前年份已过天数
			double currentAnnualLeave = (double)currentIntervalDays/365*5;//今年年假
			
			int entryYearIntervalDays = DateUtil.getIntervalDate(entry_time,String.valueOf(entryYear+"-12-31"), sdf);//计算入职年份截止到年尾在职天数
			double entryYearAnnualLeave = (double)entryYearIntervalDays/365*5;//入职年假
			
			if(intervalYears==0){
				
				number = getAnnualLeaveNumber(start_work_time,sdf.format(Calendar.getInstance().getTime()))/5;
				//今年入职
				map.put(String.valueOf(currentYear), handle(annualLeave*number));
			
			}else if(intervalYears==1){
				
				number = getAnnualLeaveNumber(start_work_time,String.valueOf(entryYear+"-12-31"))/5;
				//去年入职
				map.put(String.valueOf(currentYear-1), handle(entryYearAnnualLeave*number));
				
				number = getAnnualLeaveNumber(start_work_time,sdf.format(Calendar.getInstance().getTime()))/5;
				map.put(String.valueOf(currentYear), handle(currentAnnualLeave*number));
				
			}else{
				
				number = getAnnualLeaveNumber(start_work_time,String.valueOf(entryYear+"-12-31"))/5;
				map.put(String.valueOf(entryYear), handle(entryYearAnnualLeave*number));
				for(int i= 1;i<intervalYears;i++){
					number = getAnnualLeaveNumber(start_work_time,String.valueOf(entryYear+i+"-12-31"))/5;
					map.put(String.valueOf(entryYear+i), (double)5*number);
				}
				number = getAnnualLeaveNumber(start_work_time,sdf.format(Calendar.getInstance().getTime()))/5;
				map.put(String.valueOf(currentYear), handle(currentAnnualLeave*number));
			}
			
		} catch (Exception e) {
			System.out.println("年假计算异常！");
			e.printStackTrace();
		}
		
		
		return map;
	}
	
	public static void main(String[] args) throws ParseException {
		
		System.out.println(calculate("2010-07-10","2013-07-23" ));
		
	}
	
	private static double handle(double d){
		//4.44为4天
		//4.5以上到4.99999为4.5天
		String str = String.valueOf(d);
		try {
			String[] arr = str.split("\\.");
			String result = 0+"."+arr[1];
			double handleResult = Double.valueOf(result);
			if(handleResult>=0&&handleResult<0.5){
				return Double.valueOf(arr[0]);
				
			}else if(handleResult>=0.5&&handleResult<1){
				return Double.valueOf(arr[0]+".5");
			}
			
		} catch (Exception e) {
			
			return d;
		}
		return 0;
	}
	private static int getAnnualLeaveNumber(String start_work_time,String entry_time) throws ParseException{
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int workIntervalYears = DateUtil.getIntervalYear(start_work_time,entry_time, sdf);
		
		if(workIntervalYears>20){
			
			return SENIOR;
		}else if(workIntervalYears>10){
			
			return MIDDLE;
		}else if(workIntervalYears<=10){
			
			return JUNIOR;
		}
		return JUNIOR;
	}
}
