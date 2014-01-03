package com.lichuan.attendance.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichuan.attendance.mapper.ConfirmRecordMapper;
import com.lichuan.attendance.model.AdminUser;
import com.lichuan.attendance.model.ConfirmRecord;
import com.lichuan.attendance.model.PersonStatistic;

@Service
public class ConfirmRecordService {

	@Autowired
	ConfirmRecordMapper confirmRecordMapper;
	@Autowired
	AdminUserService adminUserService;
	@Autowired
	SalaryService salaryService;
	
	/**
	 * 确认考勤数据
	 * @param every_month
	 * @param oa
	 * @return
	 */
	public boolean confirmAttendanceData(String every_month,String oa){
		
		if(validateConfirm(every_month, oa)){
			
			Map<String,String> map  = new HashMap<String, String>();
			map.put("oa", oa);
			map.put("every_month", every_month);
			
			AdminUser adminUser = adminUserService.getInfoByEmpId(oa);
			map.put("user_name", adminUser.getAdmin_name());
			map.put("department", adminUser.getDepartment());
			map.put("type", "0");
			confirmRecordMapper.save(map);
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 校验考勤数据是否确认
	 *    有待处理数>0即false
	 * @param every_month
	 * @param oa
	 * @return
	 */
	public boolean validateConfirm(String every_month,String oa){
	
		boolean result = false;
		PersonStatistic perState = salaryService.getPersonStatistics(every_month, oa);
		if(perState!=null){

			if(null==perState.getMap().get("待处理")||perState.getMap().get("待处理")<=0){
				
				result = true;
			}
		}
		return result;
	}
	
	/**
	 * 按照oa查询确认记录
	 * @param every_month
	 * @param oa
	 * @return
	 */
	public List<ConfirmRecord> queryListByOAMonth(String every_month,String oa){
		
		Map<String,String> map  = new HashMap<String, String>();
		map.put("oa", oa);
		map.put("every_month", every_month);
		List<ConfirmRecord> list = confirmRecordMapper.queryListByOAMonth(map);
		if(null==list||list.size()<1){
			return null;
		}
		return list;
	}
	
	/**
	 * 按照oa查询确认记录
	 * @param every_month
	 * @param oa
	 * @return
	 */
	public List<ConfirmRecord> queryListByOAMonth(String every_month){
		
		Map<String,String> map  = new HashMap<String, String>();
		map.put("every_month", every_month);
		List<ConfirmRecord> list = confirmRecordMapper.queryListByMonth(map);
		if(null==list||list.size()<1){
			return null;
		}
		return list;
	}
}
