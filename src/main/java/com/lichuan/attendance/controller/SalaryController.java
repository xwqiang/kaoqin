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
import com.lichuan.attendance.model.PersonCalendar;
import com.lichuan.attendance.model.PersonStatistic;
import com.lichuan.attendance.model.Salary;
import com.lichuan.attendance.service.AdminUserService;
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
public class SalaryController {

	private Logger log = Logger.getLogger(SalaryController.class);

	@Autowired
	private AdminUserService adminService;
	@Autowired
	private SalaryService salaryService;

	/**
	 * 获取个人考勤记录
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/getPersonChecking-in.do")
	public String getPersonSalary(HttpServletRequest request, ModelMap modelMap) {

		String user_name = request.getParameter("user_name");
		String every_month = request.getParameter("every_month");
		String oa = request.getParameter("oa");
		String type = request.getParameter("type");

		System.out.println(user_name);
		System.out.println(oa);

		if (user_name == null) {
			user_name = "李川";
		}

		if (every_month == null) {

			every_month = DateUtil.getCurrentMonth();
		}

		if (type != null) {
			if (type.equals("prev")) {
				// 上一个月
				every_month = DateUtil.getLastMonth(every_month);
			} else if (type.equals("next")) {
				// 下一个月
				every_month = DateUtil.getNextMonth(every_month);

			} else if (type.equals("today")) {
				// 当前月
				every_month = DateUtil.getCurrentMonth();
			}
		}

		List<Salary> list = salaryService.getPersonSalary(every_month,
				user_name);
		List<PersonCalendar> calendars = salaryService
				.getPersonCalendarByName(user_name);

		AdminUser user = adminService.getInfoByAdminName(user_name);

		try {

			oa = user.getEmp_id();
		} catch (Exception e) {
			return "jump";
		}

		PersonStatistic statistic = salaryService.getPersonStatistic(
				every_month, every_month, oa);

		List<PersonCalendar> leaves = salaryService.getPersonAllLeaves(
				every_month, oa);

		String[] arr = every_month.split("-");

		modelMap.addAttribute("list", list)
				.addAttribute("user_name", user_name)
				.addAttribute("every_month", every_month)
				.addAttribute("oa", oa).addAttribute("year", arr[0])
				.addAttribute("month", (Integer.valueOf(arr[1]) - 1))
				.addAttribute("calendars", calendars)
				.addAttribute("statistic", statistic)
				.addAttribute("leaves", leaves);

		return "salary/personChecking-in";

	}

	private String getMapping(String str) {
		if (str == null) {
			return null;
		}

		if (str.equals("财务部")) {
			return "cwb";
		}
		if (str.equals("技术部")) {
			return "jsb";
		}
		if (str.equals("客服部")) {
			return "kfb";
		}
		if (str.equals("人资部")) {
			return "rzb";
		}
		if (str.equals("商务拓展部")) {
			return "swb";
		}
		if (str.equals("市场1部")) {
			return "scyb";
		}
		if (str.equals("市场2部")) {
			return "sceb";
		}
		if (str.equals("市场3部")) {
			return "scsb";
		}
		if (str.equals("产品运营部")) {
			return "cpyyb";
		}
		
		if (str.equals("无为部")) {
			return "wwb";
		}
		if (str.equals("总经办")) {
			return "zjb";
		}
		return null;
	}

	/**
	 * 获取个人考勤记录
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/getPersonChecking-inByAdmin.do")
	public String getPersonSalaryByAdmin(HttpServletRequest request,
			ModelMap modelMap, HttpServletResponse response) {

		String user_name = request.getParameter("user_name");
		String every_month = request.getParameter("every_month");
		String oa = request.getParameter("oa");
		String type = request.getParameter("type");

		String reason = request.getParameter("reason");// 无，原因列表
		String reason2 = request.getParameter("reason2");// 无，原因列表
		String number = request.getParameter("number");// 0.5,1
		String click_day = request.getParameter("click_day");
		String forget = request.getParameter("forget");// 无，忘签到，忘签退
		System.out.println(user_name);
		System.out.println(reason);
		System.out.println(reason2);
		System.out.println(number);
		System.out.println(click_day);
		System.out.println(forget);
		System.out.println(oa);
		if (reason != null && forget != null) {

			Salary salary = new Salary();
			salary.setOa(oa);
			salary.setEvery_date(click_day);

			StringBuffer sb_comment = new StringBuffer();
			if (!reason.equals("无")) {
				sb_comment.append(reason);
			}

			if (!reason2.equals("无")) {
				if (!sb_comment.toString().trim().equals("")) {
					sb_comment.append(" ");
				}
				sb_comment.append(reason2);
			}

			if (!sb_comment.toString().trim().equals("")) {

				String[] arr = sb_comment.toString().split(" ");

				if (arr.length > 1) {
					String str = null;
					if (arr[0].equals(arr[1])) {
						str = arr[0] + "一天";
					} else {

						str = arr[0] + "半天" + " " + arr[1] + "半天";
					}
					sb_comment = new StringBuffer();
					sb_comment.append(str);

				} else {

					sb_comment.append("");
					if (number.equals("0.5")) {

						sb_comment.append("半天");
					} else {
						sb_comment.append("一天");
					}
				}
			}

			String[] arr = sb_comment.toString().split(" ");

			if (arr.length < 2) {
				if (!arr[0].contains("一天")) {

					if (!forget.equals("无")) {

						if (!sb_comment.toString().trim().equals("")) {
							sb_comment.append(" ");
						}
						sb_comment.append(forget);

					}
				}
			}

			// 迟到-免，迟到-罚需要保留
			Salary tmp_salary = salaryService.querySalaryByDate(oa,
					salary.getEvery_date());

			if (tmp_salary != null) {

				String tmp_comment = tmp_salary.getComment();
				if (tmp_comment != null) {

					StringBuffer ss = new StringBuffer();
					if (tmp_comment.contains("迟到-免")) {

						ss.append("迟到-免").append(" ").append(sb_comment);
						sb_comment = ss;
					} else if (tmp_comment.contains("迟到-罚")) {

						ss.append("迟到-罚").append(" ").append(sb_comment);
						sb_comment = ss;
					}
				}
			}

			if (sb_comment.toString().trim().equals("")) {
				sb_comment.append("正常");
			}

			salary.setResult(sb_comment.toString());

			salaryService.updateResult(salary, click_day, click_day);

			/* RETURN VALUE */
			String[] json = new String[4];
			json[0] = "true"; // RETURN TRUE
			json[1] = oa;
			json[2] = click_day;
			json[3] = reason;
			JSONArray jsonArray = JSONArray.fromObject(json);
			response.setContentType("text/json; charset=UTF-8");
			PrintWriter out = null;

			try {
				out = response.getWriter();
				// out.write("{jsonReturn:" + jsonArray + "}");
				out.write(jsonArray.toString());
				out.flush();
				out.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;

		}
		if (user_name == null) {
			user_name = "李川";
		}
		if (every_month == null) {

			every_month = DateUtil.getCurrentMonth();
		}

		if (type != null) {
			if (type.equals("prev")) {
				// 上一个月
				every_month = DateUtil.getLastMonth(every_month);
			} else if (type.equals("next")) {
				// 下一个月
				every_month = DateUtil.getNextMonth(every_month);

			} else if (type.equals("today")) {
				// 当前月
				every_month = DateUtil.getCurrentMonth();
			}
		}

		List<Salary> list = salaryService.getPersonSalary(every_month,
				user_name);
		List<PersonCalendar> calendars = salaryService
				.getPersonCalendarByName(user_name);

		AdminUser user = adminService.getInfoByAdminName(user_name);
		try {

			oa = user.getEmp_id();
		} catch (Exception e) {
			return "jump";
		}

		PersonStatistic statistic = salaryService.getPersonStatistic(
				every_month, every_month, oa);

		List<PersonCalendar> leaves = salaryService.getPersonAllLeaves(
				every_month, oa);

		String[] arr = every_month.split("-");

		modelMap.addAttribute("list", list)
				.addAttribute("user_name", user_name)
				.addAttribute("every_month", every_month)
				.addAttribute("oa", oa).addAttribute("year", arr[0])
				.addAttribute("month", (Integer.valueOf(arr[1]) - 1))
				.addAttribute("calendars", calendars)
				.addAttribute("statistic", statistic)
				.addAttribute("leaves", leaves);
		return "salary/personChecking-inByAdmin";

	}

	@RequestMapping(value = "/showAllAttendance.do")
	public String showAllAttendance(HttpServletRequest request,
			ModelMap modelMap) {

		String start_month = request.getParameter("start_month");
		String end_month = request.getParameter("end_month");
		String department = request.getParameter("department");
		String emp_id = request.getParameter("emp_id");
		String user_name = request.getParameter("user_name");
		String flag = request.getParameter("flag");
		List<AdminUser> adminUsers = new ArrayList<AdminUser>();

		if (department != null && !department.equals("")) {
			// 按照部门查询
			adminUsers = adminService.getInfoByDepartment(department);
		} else {

			if (user_name != null && !user_name.equals("")) {

				adminUsers.add(adminService.getInfoByAdminName(user_name));

			} else {

				adminUsers = adminService.getListBySort();
			}
		}

		if (start_month == null) {

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
			start_month = formatter.format(Calendar.getInstance().getTime());
			end_month = formatter.format(Calendar.getInstance().getTime());
		}

		// List<PersonStatistic> list =
		// salaryService.getAllPersonStatistic(adminUsers,start_month,end_month);
		List<PersonStatistic> list = salaryService.getStatistics(adminUsers,
				start_month, end_month);
		// 合计数据
		String tag = getMapping(department);
		if (tag == null) {
			tag = "quanti";
		}
		modelMap.addAttribute("list", list)
				.addAttribute("user_name", user_name)
				.addAttribute("start_month", start_month)
				.addAttribute("end_month", end_month)
				.addAttribute("every_month", end_month)
				.addAttribute("department", department)
				.addAttribute("tag", tag);
		if (flag != null) {

			return "salary/showAllAttendanceByAdmin";
		} else {
			return "salary/showAllAttendance";
		}

	}

	/**
	 * 导出网关发送在某月内的详细数据 Description:
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportAllAttendance.do")
	public String exportgateDataListEverydayBymonth(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String start_month = request.getParameter("start_month");
		String end_month = request.getParameter("end_month");
		String department = request.getParameter("department");
		String emp_id = request.getParameter("emp_id");
		String user_name = request.getParameter("user_name");
		List<AdminUser> adminUsers = new ArrayList<AdminUser>();

		if (department != null && !department.equals("")) {
			// 按照部门查询
			adminUsers = adminService.getInfoByDepartment(department);
		} else {

			if (user_name != null && !user_name.equals("")) {

				adminUsers.add(adminService.getInfoByAdminName(user_name));

			} else {

				adminUsers = adminService.getListBySort();
			}
		}

		if (start_month == null) {

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
			start_month = formatter.format(Calendar.getInstance().getTime());
			end_month = start_month;
		}

		// List<PersonStatistic> list =
		// salaryService.getAllPersonStatistic(adminUsers,start_month,end_month);
		List<PersonStatistic> list = salaryService.getStatistics(adminUsers,
				start_month, end_month);

		if (list != null && list.size() > 0) {
			int num = list.size() + 1;
			String queryData[][] = new String[num][24]; // [行][列]
			queryData[0][0] = "序号";
			queryData[0][1] = "部门";
			queryData[0][2] = "员工姓名";
			queryData[0][3] = "oa工号";
			queryData[0][4] = "办公室上班天数";
			queryData[0][5] = "外出天数";
			queryData[0][6] = "出差天数";
			queryData[0][7] = "调休天数";
			queryData[0][8] = "年假天数";
			queryData[0][9] = "婚假天数";
			queryData[0][10] = "产假天数";
			queryData[0][11] = "丧假天数";
			queryData[0][12] = "旷工天数";
			queryData[0][13] = "迟到-罚次数";
			queryData[0][14] = "早退次数";
			queryData[0][15] = "忘打卡-罚次数";
			queryData[0][16] = "出勤天数";
			queryData[0][17] = "事假天数";
			queryData[0][18] = "病假天数";
			queryData[0][19] = "饭补天数";
			queryData[0][20] = "工作日加班天数";
			queryData[0][21] = "休息日加班天数";
			queryData[0][22] = "法定假日加班天数";
			queryData[0][23] = "待处理天数";
			for (int i = 1; i <= list.size(); i++) {
				PersonStatistic tmp = list.get(i - 1);
				queryData[i][0] = i + "";
				queryData[i][1] = tmp.getDepartment();
				queryData[i][2] = tmp.getUser_name();
				queryData[i][3] = tmp.getOa();
				queryData[i][4] = String.valueOf(tmp.getOffice_date_count());
				queryData[i][5] = String.valueOf(tmp.getWaichu_count());
				queryData[i][6] = String.valueOf(tmp.getChuchai_count());
				queryData[i][7] = String.valueOf(tmp.getTiaoxiu_count());
				queryData[i][8] = String.valueOf(tmp.getNianjia_count());
				queryData[i][9] = String.valueOf(tmp.getHunjia_count());
				queryData[i][10] = String.valueOf(tmp.getChanjia_count());
				queryData[i][11] = String.valueOf(tmp.getSangjia_count());
				queryData[i][12] = String.valueOf(tmp.getKuanggong_count());
				queryData[i][13] = String.valueOf(tmp.getLate_fine());
				queryData[i][14] = String.valueOf("");
				queryData[i][15] = String.valueOf(tmp.getForget_fine());
				queryData[i][16] = String.valueOf(tmp
						.getReal_working_date_count());
				queryData[i][17] = String.valueOf(tmp.getShijia_count());
				queryData[i][18] = String.valueOf(tmp.getBingjia_count());
				queryData[i][19] = String.valueOf(tmp.getFanbu_date_count());
				queryData[i][20] = String.valueOf(tmp
						.getWorkdate_overtime_count());
				queryData[i][21] = String.valueOf(tmp.getOvertime_count());
				queryData[i][22] = String.valueOf(tmp
						.getHoliday_overtime_count());
				queryData[i][23] = String.valueOf(tmp.getUndetermined_count());
			}
			ExcelUtils.exportExcelData(
					start_month + "--" + end_month + " 统计列表", queryData,
					"exportDatas.xls", request, response);
		} else {
			modelMap.addAttribute("err", "没有数据!");
			return "forward";
		}
		return null;

	}

	/**
	 * 初始化
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/init.do")
	public String init(HttpServletRequest request, ModelMap modelMap) {

		String every_month = request.getParameter("every_month");
		salaryService.processDataHandle(every_month);

		return "初始化完成";

	}

	/**
	 * 一键恢复考勤数据，修改状态status=0
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/restoreByOnekey.do")
	public String restoreByOnekey(HttpServletRequest request,
			ModelMap modelMap, HttpServletResponse response) {

		String every_month = request.getParameter("every_month");
		String oa = request.getParameter("oa");
		String user_name = request.getParameter("user_name");

		if (every_month != null && oa != null) {

			salaryService.restoreByOnekey(every_month, oa);

			request.setAttribute("oa", oa);
			request.setAttribute("every_month", every_month);
			request.setAttribute("user_name", user_name);
			return getPersonSalaryByAdmin(request, modelMap, response);

		} else {
			return null;
		}

	}
}