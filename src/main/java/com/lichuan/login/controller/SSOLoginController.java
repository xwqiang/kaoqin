package com.lichuan.login.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.company.business.developInterface.Interactive;
import com.company.business.developInterface.impl.InteractiveImpl;
import com.company.business.developInterface.model.CheckedModel;
import com.lichuan.attendance.model.AdminUser;
import com.lichuan.attendance.service.AdminUserService;

@Controller
public class SSOLoginController {

	@Autowired
	private AdminUserService adminUserService;

	@RequestMapping("/ssoLogin.do")
	public String ssoLogin(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) throws Exception {
		String sso_time = request.getParameter("sso_time");
		String roleId = request.getParameter("roleId");
		String interactiveData = request.getParameter("interactiveData");
		String emp_id = request.getParameter("userId");
		CheckedModel ck = new CheckedModel();
		ck.setAppId("kaoqin");
		ck.setApp_pwd("kaoqin123");
		ck.setInteractiveData(interactiveData);
		ck.setTime_from_sso_server(sso_time);
		ck.setClient_time(String.valueOf(System.currentTimeMillis()));
		ck.setUserId(emp_id);
		ck.setSso_server_url("http://192.168.5.31:8081");
		Interactive interactive = new InteractiveImpl();
		boolean validate_result = interactive.reveivecheckMsg(ck);
		AdminUser adminUser = adminUserService.getInfoByEmpId(emp_id);
		if(validate_result){
			com.company.business.developInterface.model.User ssoUser = interactive.getUserById(ck);
			if(adminUser!=null){
				if(adminUser.getStatus()==1){

					modelMap.addAttribute("message", "OA账号不能为空")
					.addAttribute("用户已关闭，请联系管理员!")
					.addAttribute("flag", 2);
					return "login/message";

				}else {

					request.getSession().setAttribute("adminUser", adminUser);
					//装载adminList
					List<AdminUser> adminList=adminUserService.getList();
					request.getSession().setAttribute("adminList", adminList);
				}
			}else {
				
				modelMap.addAttribute("message", "OA账号不能为空")
				.addAttribute("message", "该系统找不到您的账户,请联系管理员!")
				.addAttribute("flag", 2);
				return "login/message";
			}
		}else {
			
			modelMap.addAttribute("message", "OA账号不能为空")
			.addAttribute("交互失败，请检查网络!")
			.addAttribute("flag", 2);
			return "login/message";
		}
		String adminName = adminUser.getAdmin_name();
		
		if("陆海茶".equals(adminName)||"李淑巾".equals(adminName)){
			
			return "forward:/getPersonChecking-inByAdmin.do";

		}else{
			
			return "forward:/getPersonChecking-in.do";
		}
	}
	
}
