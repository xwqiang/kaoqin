package com.lichuan.mail.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lichuan.attendance.model.AdminUser;
import com.lichuan.attendance.service.AdminUserService;
import com.lichuan.attendance.service.ConfirmRecordService;
import com.lichuan.mail.service.MailSendService;

@Controller
public class MailController {
	
	@Autowired
	private AdminUserService adminUserService;
	@Autowired
	private MailSendService mailSendService;
	@Autowired
	ConfirmRecordService confirmRecordService;

	@RequestMapping("/sendMail.do")
	public String sendMail(HttpServletRequest request, ModelMap modelMap) {
		
		try {
			mailSendService.sendHtmlEmail("176300771@qq.com", "test",null,null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	@RequestMapping("/mailNotice.do")
	public String mailNotice(HttpServletRequest request, ModelMap modelMap) {
		
//		AdminUser adminUser = (AdminUser) request.getSession().getAttribute("adminUser");
//		try {
//			mailSendService.sendHtmlEmail("lichuan3992413@126.com", "请确认考勤数据",adminUser);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		String every_month = request.getParameter("every_month");
		confirmRecordService.emailNotice(every_month);
		
		return null;
	}
}
