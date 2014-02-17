package com.lichuan.attendance.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichuan.attendance.mapper.AdminUserMapper;
import com.lichuan.attendance.mapper.SalaryMapper;
import com.lichuan.attendance.mapper.StatisticInfoMapper;
import com.lichuan.attendance.model.AdminUser;
import com.lichuan.attendance.model.PersonCalendar;
import com.lichuan.attendance.model.PersonInfo;
import com.lichuan.attendance.model.PersonStatistic;
import com.lichuan.attendance.model.Salary;
import com.lichuan.attendance.model.SalaryDetail;
import com.lichuan.util.DateUtil;
import com.lichuan.util.PatternTool;
import com.lichuan.util.SalaryAlgorithm;

@Service
public class SalaryService {

	@Autowired
	private SalaryMapper salaryMapper;
	@Autowired
	private AdminUserMapper adminUserMapper;
	@Autowired
	private StatisticInfoMapper statisticInfoMapper;
	@Autowired
	private PersonInfoService personInfoService;
	
	/**
	 * 根据oa，月份更新状态为0
	 * @param every_month
	 * @param oa
	 */
	public void restoreByOnekey(String every_month,String oa){
		
		Map<String,String> map  = new HashMap<String, String>();
		map.put("oa", oa);
		map.put("every_month", every_month);
		//删除无打卡记录
		salaryMapper.deleteNUllRecord(map);
		salaryMapper.updateStatus(map);
	}
	
	
	/*********************************************
	 *	根据指定月份获取考勤列表
	 * @author Administrator<lichuan3992413@gmail.com>
	 * Create at:   2013-10-17 下午09:47:25 
	 * @param everyMonth
	 * @return
	 ********************************************/
	public List<Salary> getListByMonth(String everyMonth){
		
		if(everyMonth!=null){
			List<Salary> list = salaryMapper.queryListByMonth(everyMonth);
			Collections.sort(list);
			return list;
		}
		return null;
	}
	
	
	/*********************************************
	 *	根据指定月份，指定人名 获取考勤信息
	 * @author Administrator<lichuan3992413@gmail.com>
	 * Create at:   2013-10-17 下午09:47:55 
	 * @param every_month
	 * @param user_name
	 * @return
	 ********************************************/
	public List<Salary> getPersonSalary(String every_month,String user_name){
		
		if(every_month!=null&&user_name!=null){
			
			Map<String,String> map = new HashMap<String,String>();
			map.put("every_month", every_month);
			map.put("user_name", user_name);
			
			List<Salary> list = salaryMapper.queryPersonSalary(map);
//			List<PersonCalendar> calendars = getPersonCalendarByName(every_month, user_name);
			
			Collections.sort(list);
			//comment_type处理
			commentTypeHandle(list);
			return list;
		}
		return null;
	}
	
	/*********************************************
	 *	根据指定月份，指定人名 获取考勤信息
	 * @author Administrator<lichuan3992413@gmail.com>
	 * Create at:   2013-10-17 下午09:47:55 
	 * @param every_month
	 * @param user_name
	 * @return
	 ********************************************/
	public List<Salary> getPersonSalaryByOA(String every_month,String oa){
		
		if(every_month!=null&&oa!=null){
			
			Map<String,String> map = new HashMap<String,String>();
			map.put("every_month", every_month);
			map.put("oa", oa);
			
			List<Salary> list = salaryMapper.queryPersonSalaryByOA(map);
			return list;
		}
		return null;
	}
	/**
	 * 处理备注
	 * @param list
	 */
	private void commentTypeHandle(List<Salary> list){
		if(list!=null){
			for(Salary each:list){
				
				String comment = null;
				if(each.getStatus()==0){
					comment = each.getComment();
				}else{
					comment = each.getResult();
					each.setComment(comment);
				}

				SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");
				String current_date = formatterDate.format(Calendar.getInstance().getTime());
				String every_date = each.getEvery_date();
				Map<String,String> map = new HashMap<String,String>();
				map.put("every_month", each.getEvery_month());
				map.put("every_date", each.getEvery_date());
				map.put("oa", each.getOa());
				
				PersonCalendar calendar = salaryMapper.getPersonCalendarByOaDate(map);
				if(calendar!=null){
					if(calendar.getType()<=0){
						
						
						if(current_date.equals(every_date)){
							
							String end_time = calendar.getEnd_time();
							
							SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String current_time = formatter.format(Calendar.getInstance().getTime());
							
							if(each.getStatus()==0){
								
								if(current_time.compareTo(end_time)<0){
									comment="未到下班时间，待处理";
									each.setComment(comment);
								}
							}
						}
					}
				}
				if(comment!=null){
		
					if(comment.equals("迟到-免")||comment.equals("迟到-罚")){
						each.setComment_type(1);
					}else if(comment.contains("待处理")||comment.contains("早退")){
						each.setComment_type(2);
					}else if(each.getStatus()==1){
						each.setComment_type(3);
					}
				}
			}
		}
	}
	
	/**
	 * 更新备注
	 * @param salary
	 */
	public void updateComment(Salary salary){
		
		Map<String,String> map  = new HashMap<String, String>();
//		map.put("id", salary.getId());
		map.put("oa", salary.getOa());
		map.put("user_name", salary.getUser_name());
		map.put("every_month", salary.getEvery_month());
		map.put("every_date", salary.getEvery_date());
		map.put("comment", salary.getComment());
		
		salaryMapper.updateComment(map);
	}
	
	
	/**
	 * 根据指定oa,指定日期,限制次数,，得出考勤结果，同时处理影响结果 
	 * @param oa
	 * @param every_date
	 * @param limit 迟到免-允许次数
	 * @return default/迟到-免/迟到-罚
	 */
	private String handleLate(String oa,String every_date,int limit){
		
		String every_month = PatternTool.getMatch(every_date, "(\\d{4}-\\d{2})-\\d{2}.*",1);
		PersonStatistic stats = getPersonStatistics(every_month, oa);
		
		int inex = 1;//当前日期位置计数
		//迟到-免次序，次数 会造成影响
		int late = stats.getLate_absolve();//迟到-免次数
		int late_fine = stats.getLate_fine();//迟到-罚
//		//需算上备注日期是否包含迟到情况
//		Salary salary = querySalaryByDate(oa, every_date);
//		if(null!=salary){
//			
//			String currentResult = null;
//			if(salary.getStatus()==0){
//				currentResult = salary.getComment();
//			}else {
//				currentResult = salary.getResult();
//			}
//			if(null!=currentResult&&currentResult.contains("迟到")){
//				late++;
//			}
//		}
		if((late+late_fine)>=limit){
			//超出3次，需根据次序进行处理
			List<Salary> salaryList = getPersonSalaryByOA(every_month, oa);
			if(null!=salaryList){
				
				int count=0;//从limit开始更新
				boolean isFirstTime = true;//第一次
				for(Salary each:salaryList){
					
					int status = each.getStatus();
					String result = null;
					if(status==0){
						result = each.getComment();
					}else{
						result = each.getResult();
					}
					
					if(null!=result&&result.contains("迟到-免")){

						count++;
						String each_every_date = each.getEvery_date();
						if(every_date.compareTo(each_every_date)<0){
							if(isFirstTime){
								count++;
								isFirstTime = false;
							}
							result = result.replace("迟到-免", "迟到-罚");
							//更新
							if(count>limit){
								
								Map<String,String> map  = new HashMap<String, String>();
								map.put("oa", oa);
								map.put("start_date", each_every_date);
								map.put("end_date", each_every_date);
//							    map.put("comment", "无打卡记录");
								map.put("result", result);
								
								salaryMapper.updateResult(map);
							}
							
						}else{
							inex++;
						}
					}
					
				}
			}
		}else{
			return "default";
		}
		
		if(inex>limit){
			return "迟到-罚";
		}else{
			return "迟到-罚";
		}
	}
			
		

	/**
	 * 更新人资标注结果
	 * @param salary
	 * @param start_date
	 * @param end_date
	 */
	public boolean updateResult(Salary salary,String start_date,String end_date){
		
		boolean updateResult = false;
		Map<String,String> map  = new HashMap<String, String>();
		String every_month = PatternTool.getMatch(start_date, "(\\d{4}-\\d{2})-\\d{2}.*",1);

		String result = salary.getResult();
		
		/********************************
		 * 正确动态计算迟到规则有点复杂，容后再写
		 * *****************************
		 */
//		if(null!=result){
//			//迟到-免次序，次数 会造成影响
//			//超出3次，需根据次序进行处理
//			String lateResult = handleLate(salary.getOa(), start_date, 3);
//			if(!"default".equals(lateResult)){
//				result = result.replace("迟到-免", lateResult);
//			}
//			
//		}
		map.put("oa", salary.getOa());
		map.put("start_date", start_date);
		map.put("end_date", end_date);
		map.put("comment", "无打卡记录");
		map.put("result", result);

			
		double beforeAnnualLeave = caluateAnnualLeave(salary.getOa(), start_date);
		double afterAnnualLeave = caluateAnnualLeaveByResult(result);
	//	afterAnnualLeave = caluateAnnualLeave(salary.getOa(), start_date);
		updateResult  = personInfoService.updateAnnualLeave(salary.getOa(), start_date, (afterAnnualLeave-beforeAnnualLeave));//更新年假数据
		if(!updateResult)
			return updateResult;
		
		int count = salaryMapper.updateResult(map);
		if(count==0){
			
			AdminUser user = adminUserMapper.getInfoByEmpId(salary.getOa());
			map.put("id", user.getCheck_id());
			map.put("every_month", every_month);
			map.put("every_date", start_date);
			map.put("user_name", user.getAdmin_name());
			map.put("department", user.getDepartment());
			//插入操作
			salaryMapper.insertResult(map);
			
		}
		System.out.println(result);
		updateResult = true;
		return updateResult;
	}
	
	/**
	 * 计算备注年假数量
	 * @param result
	 * @return
	 */
	public double caluateAnnualLeaveByResult(String result){
		
			
			if(null!=result){
				if(result.contains("年假一天")){
					return 1;
				}
				if(result.contains("年假半天")){
					return 0.5;
				}
			}
		return 0;
	}
	
	/**
	 * 计算备注年假数量
	 * @param result
	 * @return
	 */
	public double caluateAnnualLeave(String oa,String every_date){
		
		Salary tmpSalary = querySalaryByDate(oa, every_date);
		
		double annualLeave = 0;
		if(null!=tmpSalary){
			if(tmpSalary.getStatus()==0){
				
				annualLeave = caluateAnnualLeaveByResult(tmpSalary.getComment());
			}else{
				
				annualLeave = caluateAnnualLeaveByResult(tmpSalary.getResult());
			}
		}
			
			
		return annualLeave;
	}
	
	public List<SalaryDetail> getDetailListByName(String userName){
		
		if(userName!=null){
			
			List<SalaryDetail> list = salaryMapper.getDetailListByName(userName);
			
			return list;
		}
		return null;
	}
	
	public List<PersonCalendar> getPersonCalendarByName(String user_name){
		
		if(user_name!=null){
			
			Map<String,String> map = new HashMap<String,String>();
//			map.put("every_month", every_month);
			map.put("user_name", user_name);
			
			List<PersonCalendar> list = salaryMapper.getPersonCalendarByName(map);
			
			return list;
		}
		return null;
	}
	
	public PersonCalendar getPersonCalendarByOaDate(String every_month,String oa,String every_date){
		
		if(every_month!=null&&oa!=null){
			
			Map<String,String> map = new HashMap<String,String>();
			map.put("every_month", every_month);
			map.put("every_date", every_date);
			map.put("oa", oa);
			
			PersonCalendar calendar = salaryMapper.getPersonCalendarByOaDate(map);
			
			return calendar;
		}
		return null;
	}
	
	/*********************************************
	 * 获取指定月份，人员，上班期未打卡情况
	 * @author Administrator<lichuan3992413@gmail.com>
	 * Create at:   2013-10-21 下午02:57:43 
	 * @param every_month
	 * @param oa
	 * @return
	 ********************************************/
	public List<PersonCalendar> getPersonLeaves(String every_month,String oa){
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		 
		Map map = new HashMap();
		map.put("every_month", every_month);
		map.put("every_date", formatter.format(Calendar.getInstance().getTime()));
		map.put("oa", oa);
		
		return salaryMapper.getPersonLeaves(map);
	}
	
	
	public List<PersonCalendar> getPersonLeaves(String start_month,String end_month,String oa){
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		 
		Map map = new HashMap();
		map.put("start_month", start_month);
		map.put("end_month", end_month);
		map.put("every_date", formatter.format(Calendar.getInstance().getTime()));
		map.put("oa", oa);
		
		return salaryMapper.getPersonLeaves(map);
	}
	
	public List<PersonCalendar> getPersonAllLeaves(String every_month,String oa){
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		Map map = new HashMap();
//		map.put("every_month", every_month);
		map.put("every_date", formatter.format(Calendar.getInstance().getTime()));
		map.put("oa", oa);
		
		return salaryMapper.getPersonAllLeaves(map);
	}
	
	public PersonStatistic getPersonStatistics(String every_month,String oa){
		
		return getPersonStatistic(every_month,every_month, oa);
	}

	
	/*********************************************
	 *	根据月份段查看统计
	 * @author Administrator<lichuan3992413@gmail.com>
	 * Create at:   2013-12-13 下午05:55:48 
	 * @param adminUsers
	 * @param start_month
	 * @param end_month
	 * @return
	 ********************************************/
	public List<PersonStatistic> getAllPersonStatistic(List<AdminUser> adminUsers,String start_month,String end_month){
		
		List<PersonStatistic> list = new ArrayList<PersonStatistic>();
		if(adminUsers!=null){
			
			for(AdminUser each:adminUsers){
				String oa = each.getEmp_id();
				PersonStatistic stat = getPersonStatistic(start_month,end_month, oa);
				if(stat!=null){
					
					stat.setUser_name(each.getAdmin_name());
					stat.setDepartment(each.getDepartment());
					processStatistic(stat);
					list.add(stat);
				}
			}
		}
		
		if(list.size()<1){
			return null;
		}
		return list;
	}

	/**
	 * 合计
	 * @param adminUsers
	 * @param start_month
	 * @param end_month
	 * @return
	 */
	public PersonStatistic getTotalStats(List<AdminUser> adminUsers,String start_month,String end_month){
		
		Map map = new HashMap();
		map.put("start_month", start_month);
		map.put("end_month", end_month);

		
		if(adminUsers!=null){
			
			List<String> oaList = new ArrayList<String>();
			for(AdminUser each:adminUsers){
				String oa = each.getEmp_id();
				oaList.add(oa);
			}
			
			String str_oaList = StringUtils.join(oaList.toArray(), "','");
			map.put("oaList", "'"+str_oaList+"'");
			PersonStatistic stats = statisticInfoMapper.fetchTotalDatas(map);
			
			return stats;
		}
		
		return null;
	}
	/**
	 * 根据月份段查看统计
	 * @param adminUsers
	 * @param start_month
	 * @param end_month
	 * @return
	 */
	public List<PersonStatistic> getStatistics(List<AdminUser> adminUsers,String start_month,String end_month){
	
		Map map = new HashMap();
		map.put("start_month", start_month);
		map.put("end_month", end_month);

		
		if(adminUsers!=null&&adminUsers.size()>0){
			
			List<String> oaList = new ArrayList<String>();
			for(AdminUser each:adminUsers){
				String oa = each.getEmp_id();
				oaList.add(oa);
			}
			
			String str_oaList = StringUtils.join(oaList.toArray(), "','");
			map.put("oaList", "'"+str_oaList+"'");
			List<PersonStatistic> list  = null;
			if(!start_month.equals(end_month)){
				list = statisticInfoMapper.fetchDatas(map);
			}else{
				
				list = statisticInfoMapper.fetchData(map);
			}
			if(list.size()<1){
				return null;
			}
			return list;
		}
		
		return null;
	}
	
//	private void addConfirmTime(list){
//		
//	}
	/**
	 * 统计加工
	 * @param stat
	 */
	private void processStatistic(PersonStatistic stat){
		
		if(stat==null){
			return;
		}
		Map<String, Double> map = stat.getMap();
		if(map==null){
			return;
		}
		
		Map<String, Double> statMap = new LinkedHashMap<String, Double>();
		
		statMap.put("办公室上班", stat.getOffice_date_count());
		
		if(!map.containsKey("办公室上班")){
			
			map.put("办公室上班", stat.getOffice_date_count());
		}
		if(!map.containsKey("外出")){
			
			statMap.put("外出", 0d);
			stat.setWaichu_count(0d);
		}else{
			statMap.put("外出", map.get("外出"));
			stat.setWaichu_count(map.get("外出"));
		}
		
		if(!map.containsKey("出差")){
			
			statMap.put("出差", 0d);
			stat.setChuchai_count(0d);
		}else{
			statMap.put("出差", map.get("出差"));
			stat.setChuchai_count(map.get("出差"));
		}
		if(!map.containsKey("调休")){
			
			statMap.put("调休", 0d);
			stat.setTiaoxiu_count(0d);
		}else{
			statMap.put("调休", map.get("调休"));
			stat.setTiaoxiu_count(map.get("调休"));
		}
		if(!map.containsKey("年假")){
			
			statMap.put("年假", 0d);
			stat.setNianjia_count(0d);
		}else{
			statMap.put("年假", map.get("年假"));
			stat.setNianjia_count(map.get("年假"));
		}
		if(!map.containsKey("婚假")){
			
			statMap.put("婚假", 0d);
			stat.setHunjia_count(0d);
		}else{
			statMap.put("婚假", map.get("婚假"));
			stat.setHunjia_count(map.get("婚假"));
		}
		if(!map.containsKey("产假")){
			
			statMap.put("产假", 0d);
			stat.setChanjia_count(0d);
		}else{
			statMap.put("产假", map.get("产假"));
			stat.setChanjia_count(map.get("产假"));
		}
		if(!map.containsKey("丧假")){
			
			statMap.put("丧假", 0d);
			stat.setSangjia_count(0d);
		}else{
			statMap.put("丧假", map.get("丧假"));
			stat.setSangjia_count(map.get("丧假"));
		}
		if(!map.containsKey("旷工")){
			
			statMap.put("旷工", 0d);
			stat.setKuanggong_count(0d);
		}else{
			statMap.put("旷工", map.get("旷工"));
			stat.setKuanggong_count(map.get("旷工"));
		}
		
		statMap.put("迟到超出次数", (double)stat.getLate_fine());
		
		if(!map.containsKey("早退次数")){
			
			statMap.put("早退次数", 0d);

		}else{
			statMap.put("早退次数", map.get("早退次数"));
		}
		
//		if(!map.containsKey("忘打卡超出次数")){
//			
//			statMap.put("忘打卡超出次数", 0d);
//		}else{
//			statMap.put("忘打卡超出次数", map.get("忘打卡超出次数"));
//		}
		statMap.put("忘打卡超出次数", Double.valueOf(stat.getForget_fine()));
		statMap.put("出勤天数", stat.getReal_working_date_count());
		
		if(!map.containsKey("事假")){
			
			statMap.put("事假天数", 0d);
			stat.setShijia_count(0d);
		}else{
			statMap.put("事假天数", map.get("事假"));
			stat.setShijia_count(map.get("事假"));
		}
		if(!map.containsKey("病假")){
			
			statMap.put("病假天数", 0d);
			stat.setBingjia_count(0d);
		}else{
			statMap.put("病假天数", map.get("病假"));
			stat.setBingjia_count(map.get("病假"));
		}
		
		statMap.put("饭补天数", stat.getFanbu_date_count());
		
		if(!map.containsKey("工作日")){
			
			statMap.put("工作日", 0d);
			stat.setWorkdate_overtime_count(0d);
		}else{
			statMap.put("工作日", map.get("工作日"));
			stat.setWorkdate_overtime_count(map.get("工作日"));
		}
		statMap.put("休息日", stat.getOvertime_count());
		
		statMap.put("法定假日", stat.getHoliday_overtime_count());
		
		if(!map.containsKey("待处理")){
			
			statMap.put("待处理", 0d);
			stat.setUndetermined_count(0d);
		}else{
			statMap.put("待处理", map.get("待处理"));
			stat.setUndetermined_count(map.get("待处理"));
		}
		stat.setMap(statMap);
	}
	
	/**
	 * 根据指定oa，月份段统计
	 * @param start_month
	 * @param end_month
	 * @param oa
	 * @return
	 */
	public PersonStatistic getPersonStatistic(String start_month,String end_month,String oa){
		
		if(start_month!=null&&end_month!=null&&oa!=null){
			
			Map map = new HashMap();
			map.put("start_month", start_month);
			map.put("end_month", end_month);
			map.put("oa", oa);
			map.put("type", 0);
			
			List<PersonStatistic> statistics = salaryMapper.getPersonStatistic(map);
			//工作时间
			int working_date_count = salaryMapper.getPersonWorkingDate(map);
			//休息时间
			int holidays_count = salaryMapper.getPersonHolidays(map);
			//加班时间
			int overtime_count = salaryMapper.getPersonOvertimes(map);
			int holiday_overtime_count = salaryMapper.getPersonHolidayOvertimes(map);
			
			int xiuChanJia_count = salaryMapper.getPersonChanjia(map);//休产假
			
			int remainder = salaryMapper.getPersonCalendarRemander(map);//当月剩余天数
			
			List<PersonCalendar> leaves = getPersonLeaves(start_month,end_month, oa);
			
			if(statistics!=null){
				
				PersonStatistic ps = new PersonStatistic();
				ps.setOa(oa);
//				ps.setEvery_month(every_month);
				ps.setHolidays_count(holidays_count);
				ps.setWorking_date_count(working_date_count);
				ps.setOvertime_count(overtime_count);
				ps.setHoliday_overtime_count(holiday_overtime_count);
				
				Map<String,Double> kv = new HashMap<String, Double>();
				
				for(PersonStatistic each:statistics){
					
					String department = each.getDepartment();
					ps.setDepartment(department);
					ps.setId(each.getId());
					statistic(each, kv);
				}
				
				if(kv.containsKey("待处理")){
					
					kv.put("待处理", Double.valueOf(kv.get("待处理")+leaves.size()));
					
				}else{
					if(leaves.size()>0){
						
						kv.put("待处理", Double.valueOf(leaves.size()));
					}
				}

				double count = 0;
				
				List<String> keys = new ArrayList<String>();
				for(String key:kv.keySet()){
					
					if(key.contains("待处理")){
						
					   count += kv.get(key);
					   keys.add(key);
					}
				}
				
				for(String key:keys){
					kv.remove(key);
				}
				
				if(count>0){
					
					kv.put("待处理", Double.valueOf(count));
				}
				//去掉正常
				if(kv.containsKey("正常")){
					kv.remove("正常");
				}
				//去掉迟到-免，迟到-罚
				if(kv.containsKey("迟到-免")){
					ps.setLate_absolve(kv.get("迟到-免").intValue());
					kv.remove("迟到-免");
				}
				if(kv.containsKey("迟到-罚")){
					ps.setLate_fine(kv.get("迟到-罚").intValue());
					kv.remove("迟到-罚");
				}
				if(kv.containsKey("忘签到")){
					ps.setForget_checkin(kv.get("忘签到").intValue());
					kv.remove("忘签到");
				}
				if(kv.containsKey("忘签退")){
					
					ps.setForget_checkout(kv.get("忘签退").intValue());
					kv.remove("忘签退");
				}
				if(ps.getForget_checkin()+ps.getForget_checkout()>=3){
					
					ps.setForget_fine(ps.getForget_checkin()+ps.getForget_checkout()-3);
				}
				ps.setMap(kv);

//				ps.setUnknown_count((Integer)(kv.get("待处理")));

				//计算实出勤天数，办公室上班天数
				//办公室上班+外出+出差+年假+调休假+婚假+产假+丧假-事假-病假=实际出勤天数
				//出勤天数+事假+病假=当月应出勤天数
				//饭补天数=办公室上班+外出+调休
				double shijia_count = 0;
				double bingjia_count = 0;
				if(kv.containsKey("事假")){
					
					shijia_count = kv.get("事假");
				}
				if(kv.containsKey("病假")){
					
					bingjia_count = kv.get("病假");
				}
				
				//由于实时计算查看，需要减去当月上月天数
				
				double real_working_date_count = working_date_count-shijia_count-bingjia_count-remainder;
				ps.setReal_working_date_count(real_working_date_count);
				
				double sangjia_count = 0;
				if(kv.containsKey("丧假")){
					
					sangjia_count = kv.get("丧假");
				}
				
				double chanjia_count = 0;
				if(kv.containsKey("产假")){
					
					chanjia_count = kv.get("产假");
				}
				if(xiuChanJia_count>0){
					kv.put("休产假", Double.valueOf(xiuChanJia_count));
				}
				
				
				double hunjia_count = 0;
				if(kv.containsKey("婚假")){
					
					hunjia_count = kv.get("婚假");
				}
				
				double tiaoxiujia_count = 0;
				if(kv.containsKey("调休")){
					
//					tiaoxiujia_count = kv.get("调休假");
					tiaoxiujia_count = kv.get("调休");
				}
				
				double nianjia_count = 0;
				if(kv.containsKey("年假")){
					
					nianjia_count = kv.get("年假");
				}
				
				double chuchai_count = 0;
				if(kv.containsKey("出差")){
					
					chuchai_count = kv.get("出差");
				}
				
				double waichu_count = 0;
				if(kv.containsKey("外出")){
					
					waichu_count = kv.get("外出");
				}
				
				double office_date_count = real_working_date_count-sangjia_count-chanjia_count-hunjia_count-tiaoxiujia_count-nianjia_count-chuchai_count-waichu_count;
				//饭补天数=办公室上班+外出+调休
				ps.setOffice_date_count(office_date_count);
				double fanbu_date_count = office_date_count+waichu_count+tiaoxiujia_count;
				ps.setFanbu_date_count(fanbu_date_count);
				return ps;
			}
			
			return null;
		}
		return null;
	}
	
	public void processDataHandle(String every_month){
		
		init(every_month);
		List<PersonStatistic> list = getAllPersonStatistic(adminUserMapper.getListBySort(),every_month,every_month);
		
		if(list!=null){

			System.out.println("current time: "+DateUtil.getNow()+" list size:"+list.size());
			
			statisticInfoMapper.clearDataByMonth(every_month);
			for(PersonStatistic each:list){
				
				each.setEvery_month(every_month);
				statisticInfoMapper.save(each);
			}
		}                 
	}
	
	public void init(String every_month){
		
		//更新oa号
		adminUserMapper.updateOA();
		
		List<Salary> list = getListByMonth(every_month);	
		if(list!=null){
			
			int count = 0;
			String tmp_oa = null;
			
			for(Salary each:list){
				
				String oa = each.getOa();
				//重置次数
				if(tmp_oa!=null){
					
					if(!tmp_oa.equals(oa)){
						
						count = 0;
					}
				}

				tmp_oa = oa;
				
				String user_name = each.getUser_name();
				String every_date = each.getEvery_date();
				int status = each.getStatus();
//				System.out.println(user_name+each.getOa()+every_month+every_date);
				PersonCalendar calendar = getPersonCalendarByOaDate(every_month,each.getOa(), every_date);
				if(calendar!=null){
					
					if(calendar.getType()<=0){
						
						String result = null;
						if(status==0){
							
							result  = SalaryAlgorithm.calculate(each, calendar);
						
						}else{
							
							result = each.getComment();
//							System.out.println(each.getOa()+user_name+every_date+result);
						}
						
						if(result!=null){
							
							if(result.contains("迟到-免")){
								
								if(count>=3){
									
									result = result.replace("迟到-免","迟到-罚" );
								}
								count++;
								
							}
							
//							System.out.println(user_name+" : "+each.getCheckin_time()+"-----"+each.getCheckout_time()+":"+result);
							each.setComment(result);
						}
					}else{
//						System.out.println(user_name+each.getOa()+every_month+every_date);
//						System.out.println("*****************非工作时间打卡记录！*****************");
					}
				}else{
					
					System.out.println(user_name+each.getOa()+every_month+every_date);
					System.out.println("*****************"+user_name+" 日历数据有异常，请联系管理员!");
					each.setComment("日历数据有异常，请联系管理员！");
				}
				
				//保持数据到数据库
				updateComment(each);
			}
		}
	}
	
	private void statistic(PersonStatistic statistic,Map<String,Double> kv){
		
		//事假半天 5次，事假一天 2次,事假半天 年假半天 3次
		int status = statistic.getStatus();
		String comment = statistic.getComment();
		
		if(status!=0){
			comment = statistic.getResult();
		}
			
		int time = statistic.getCount();//次数
		if(comment!=null&&!"".equals(comment)){
			
			//多种情况组合
			String[] arr  = comment.split("\\s");
			for(String cm : arr){
				
				cm = cm.trim();
				if(cm.equals("")){
					continue;
				}
				//数量：半天，一天
				double count = time*1;
				
				if(cm.contains("半天")){
					
					count = time*0.5;
				}
				
				if(cm.contains("一天")){
					count = time*1;
				}
				cm = cm.replaceAll("半天", "").replaceAll("一天", "");
				
				if(kv.containsKey(cm)){
					
					count += kv.get(cm);
				}
				kv.put(cm, count);
				
			}

		}
	}
	
	
	public Salary querySalaryByDate(String oa,String every_date){
		
		Map map = new HashMap();
		map.put("every_date", every_date);
		map.put("oa", oa);
		
		return salaryMapper.querySalaryByDate(map);
		
	}	
}
