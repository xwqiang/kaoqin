package com.hskj.salary.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hskj.model.AdminUser;
import com.hskj.salary.model.PersonCalendar;
import com.hskj.salary.model.PersonStatistic;
import com.hskj.salary.model.Salary;
import com.hskj.salary.model.SalaryDetail;
import com.hskj.salary.service.AdminUserService;
import com.hskj.salary.service.SalaryService;

/**
 *
 * Description:  日历管理
 *
 * @author Administrator<lichuan3992413@gmail.com>
 *
 * Create at:   2013-11-13 下午04:39:16 
 */
@Controller
public class CalendarController {

	private Logger log = Logger.getLogger(CalendarController.class);
	
	@Autowired
	private AdminUserService adminService;
	@Autowired
	private SalaryService salaryService;

	@SuppressWarnings("unused")

	/**
	 * 定制模板
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/customTemplate.do")
	public String customTemplate(HttpServletRequest request, ModelMap modelMap) {

		String everyMonth = request.getParameter("every_month");
		List<Salary> list = salaryService.getListByMonth(everyMonth);
		
		modelMap.addAttribute("list", list);
		return "salary/customTemplate";

	}
}