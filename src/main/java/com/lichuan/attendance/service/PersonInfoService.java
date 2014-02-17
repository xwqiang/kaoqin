package com.lichuan.attendance.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichuan.attendance.mapper.PersonInfoMapper;
import com.lichuan.attendance.model.AdminUser;
import com.lichuan.attendance.model.AnnualLeaveInfo;
import com.lichuan.attendance.model.PersonInfo;
import com.lichuan.util.AnnualLeaveAlgorithm;
import com.lichuan.util.PatternTool;

@Service
public class PersonInfoService {
		
	@Autowired
	private PersonInfoMapper personInfoMapper;
	@Autowired
	private AdminUserService adminUserService;
	@Autowired
	private SalaryService salaryService;
	// 通过admin_id获取用户信息
	public PersonInfo getInfoByOA(String oa) {
		
		PersonInfo personInfo = personInfoMapper.getInfoByOA(oa);
		Map<String,Double> map = AnnualLeaveAlgorithm.calculate(personInfo.getStart_work_time(), personInfo.getEntry_time());
		personInfo.setMap(map);
		List<AnnualLeaveInfo> list = getListByOA(oa);
		personInfo.setList(list);
		return personInfo;
	}

	/**
	 * 通过oa获取年假列表
	 * @param oa
	 * @return
	 */
	public List<AnnualLeaveInfo> getListByOA(String oa){
		return personInfoMapper.getAnnualLeaveListByOA(oa);
	}
	
	
	/**
	 * 初始化指定年份年假数据
	 * @param every_year
	 */
	public void initAnnualLeave(String every_year){
		
		//说明：强制要求只能计算包含2014年及以后年假情况，因为数据从2014年开始做，只有2013年剩余年假数据
		
		List<AdminUser> list = adminUserService.getListByInService(every_year);
		if(null!=list){
			for(AdminUser each:list){
				String oa = each.getEmp_id();
				handleAnnualLeave(oa, every_year);
			}
		}
	}

	/**
	 * 处理指定oa，指定年份年假
	 * @param oa
	 * @param every_year
	 */
	private void handleAnnualLeave(String oa,String every_year){
		String year = "2014";
		String tmp = PatternTool.getMatch(every_year, "(\\d{4}).*",1);
		if(null!=tmp){
			year = tmp;
		}
		
		PersonInfo personInfo = personInfoMapper.getInfoByOA(oa);
		Map<String,Double> annualLeaveMap = AnnualLeaveAlgorithm.calculate(personInfo.getStart_work_time(), personInfo.getEntry_time());
		if(null!=annualLeaveMap){
			Map map  = new HashMap();
			map.put("oa", oa);
			map.put("every_year", year);
			
			if(personInfoMapper.isExist(map)>0){
				//此处需要动态计算剩余年假，status
				AnnualLeaveInfo info = personInfoMapper.getInfoByOAYear(map);
				map.put("annual_leave", annualLeaveMap.get(year));
				map.put("surplus_annual_leave", info.getSurplus_annual_leave());
				map.put("status", info.getStatus());
				
				personInfoMapper.updateAnnualLeave(map);
			}else{
				
				map.put("annual_leave", annualLeaveMap.get(year));
				map.put("surplus_annual_leave", 0);
				map.put("status", 0);
				personInfoMapper.saveAnnualLeave(map);
			}
		}
	}
	

	/**
	 * 根据指定oa，指定日期更新年假
	 * 去年年假截止到3.31号，之后清空status=1
	 * @param oa
	 * @param every_date
	 * @param AnnualLeaveCount //已使用年假
	 */
	public boolean updateAnnualLeave(String oa,String every_date,double AnnualLeaveCount){
		
		boolean updateResult = false;
		String year = PatternTool.getMatch(every_date, "(\\d{4}).*",1);
		
		if(null!=year){
			PersonInfo info = getInfoByOA(oa);
			if(null!=info){
				//徐限定剩余总年假，是否满足
				double total = info.getTotalSurPlusAnnualLeave();
				
				if(total<AnnualLeaveCount&&AnnualLeaveCount>0){
					return updateResult;
				}
				
				AnnualLeaveInfo lastYearAli = info.getAnnualLeaveInfoByYear(String.valueOf(Integer.valueOf(year)-1));
				AnnualLeaveInfo ali = info.getAnnualLeaveInfoByYear(year);
				
				if(null!=lastYearAli){
					
//					if(every_date.compareTo(year+"03-31")<=0){
//						//update status=1
//						
//					}
					
					int status = lastYearAli.getStatus();
					
					if(status==0){
						//去年有剩余年假可以优先支配
						double surplus = lastYearAli.getSurplus_annual_leave();
						double result = surplus-AnnualLeaveCount;
						
						Map map  = new HashMap();
						map.put("oa", oa);
						map.put("every_year", String.valueOf(Integer.valueOf(year)-1));
						map.put("annual_leave", lastYearAli.getAnnual_leave());
						
						if(result==0){
							
							
							map.put("surplus_annual_leave", 0);
							map.put("status", 1);
							personInfoMapper.updateAnnualLeave(map);
							
						}else if(0<result&&result<lastYearAli.getAnnual_leave()){
							
							map.put("surplus_annual_leave", result);
							map.put("status", 0);
							
						}else if(result==lastYearAli.getAnnual_leave()){
							
							map.put("surplus_annual_leave", result);
							map.put("status", 0);
							
						}else if(result<0){
							map.put("surplus_annual_leave", 0);
							map.put("status", 1);
							
							Map yearMap  = new HashMap();
							yearMap.put("oa", oa);
							yearMap.put("every_year", year);
							yearMap.put("annual_leave", ali.getAnnual_leave());
							yearMap.put("surplus_annual_leave", ali.getAnnual_leave()+result);
							yearMap.put("status", 0);
							personInfoMapper.updateAnnualLeave(yearMap);//更新指定年份年假情况
						}
						
						personInfoMapper.updateAnnualLeave(map);//更新去年年假情况
						
					}else{
						
						Map yearMap  = new HashMap();
						yearMap.put("oa", oa);
						yearMap.put("every_year", year);
						yearMap.put("annual_leave", ali.getAnnual_leave());
						if(ali.getAnnual_leave()<(ali.getSurplus_annual_leave()-AnnualLeaveCount)){
							Map map  = new HashMap();
							map.put("oa", oa);
							map.put("every_year", String.valueOf(Integer.valueOf(year)-1));
							map.put("annual_leave", lastYearAli.getAnnual_leave());
							map.put("status", 0);
							map.put("surplus_annual_leave", ali.getSurplus_annual_leave()-AnnualLeaveCount-ali.getAnnual_leave());
							personInfoMapper.updateAnnualLeave(map);//更新去年年假情况
							
							yearMap.put("status", 0);
							yearMap.put("surplus_annual_leave", ali.getAnnual_leave());
							
						}else{
							
							yearMap.put("surplus_annual_leave", ali.getSurplus_annual_leave()-AnnualLeaveCount);
						}
						
						if((ali.getSurplus_annual_leave()-AnnualLeaveCount)==0){
							yearMap.put("status", 1);
						
						}else{
							yearMap.put("status", 0);
						}
						
						personInfoMapper.updateAnnualLeave(yearMap);//更新指定年份年假情况
					}
					updateResult = true;;
				}
			}
			
		}else{
			System.out.println("日期是格式有问题："+every_date);
		}
		return updateResult; 
	}
}
