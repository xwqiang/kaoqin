package com.lichuan.attendance.schedule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lichuan.attendance.service.SalaryService;


/**
 *
 * Description:定时计算考勤结果
 *
 * @author lichuan<lichuan3992413@gmail.com>
 *
 * Create at:   2013-9-28 上午09:50:10 
 */
public class DataHandler {

	
	public static Map<Integer,Integer> customerLevelMap;//用户级别 Integer:customer_sn ,Integer:customer_trust
	
	@Autowired 
	private SalaryService salaryService;
	
	/**
	 * 计算更新打卡备注，统计
	 */
	public void initData(){
        	

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
		 
		String every_month = formatter.format(Calendar.getInstance().getTime());
		
		System.out.println("开始初始化 "+every_month+" 数据!");
        
		salaryService.processDataHandle(every_month);

		System.out.println(every_month+" 初始化完成");
    }

	/**
	 * 计算更新年假数据
	 */
	public void initAnnualLeave(){
		
	}
}
