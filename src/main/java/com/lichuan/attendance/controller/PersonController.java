package com.lichuan.attendance.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lichuan.attendance.model.AdminUser;
import com.lichuan.attendance.model.ConfirmRecord;
import com.lichuan.attendance.model.PersonCalendar;
import com.lichuan.attendance.model.PersonInfo;
import com.lichuan.attendance.model.PersonStatistic;
import com.lichuan.attendance.model.Salary;
import com.lichuan.attendance.service.AdminUserService;
import com.lichuan.attendance.service.ConfirmRecordService;
import com.lichuan.attendance.service.PersonInfoService;
import com.lichuan.attendance.service.SalaryService;
import com.lichuan.util.DateUtil;
import com.lichuan.util.ExcelUtils;

/**
 * 
 * Description: 考勤管理
 * 
 * @author Administrator<lichuan3992413@gmail.com>
 * 
 *         Create at: 2013-10-16 下午03:09:32
 */
@Controller
public class PersonController {

	private Logger log = Logger.getLogger(PersonController.class);

	@Autowired
	private AdminUserService adminService;
	@Autowired
	private SalaryService salaryService;
	@Autowired
	private PersonInfoService personInfoService;
	/**
	 * 初始化
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/getPersonInfo.do")
	public String getPersonInfo(HttpServletRequest request, ModelMap modelMap) {

		String oa = request.getParameter("oa");
		PersonInfo info = personInfoService.getInfoByOA(oa);

		return "/person/personProfile";

	}


	/**
	 *  初始化指定年份年假数据
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/initAnnualLeave.do")
	public String initAnnualLeave(HttpServletRequest request, ModelMap modelMap) {

		String every_year = request.getParameter("every_year");
		personInfoService.initAnnualLeave(every_year);

		return "初始化完成";

	}
}