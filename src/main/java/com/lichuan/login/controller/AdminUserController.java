package com.lichuan.login.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpException;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.company.business.developInterface.util.MsgEmptyException;
import com.lichuan.attendance.model.AdminUser;
import com.lichuan.attendance.service.AdminUserService;
import com.lichuan.login.util.CookieManager;
import com.lichuan.login.util.LoginUtil;


@Controller
public class AdminUserController {
	
	@Autowired
	private AdminUserService adminUserService;

	@RequestMapping(value = "/login.do")
	public String login(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) {

		String admin_id = request.getParameter("admin_id");
		
		if (null == admin_id) {
			return "/login/login";
		}
		
		String admin_pwd = request.getParameter("admin_pwd");
		String check_code = request.getParameter("check_code");
		String cookieLogin = request.getParameter("cookieLogin");
		
		
		if (admin_id == null || admin_id.trim().length() == 0) {
			
			modelMap.addAttribute("message", "OA账号不能为空")
			.addAttribute("url","login.do");
			return "forward";
		}
		if (admin_pwd == null || admin_pwd.trim().length() == 0) {
			
			modelMap.addAttribute("err", "密码不能为空");
			return "forward";
		}	
		
//		if(admin_id.equals("zjf")||admin_id.equals("hhb")||admin_id.equals("baisong")){
//			check_code=(String) request.getSession().getAttribute("code");
//		}

		if (check_code == null || check_code.trim().length() == 0) {
			
			modelMap.addAttribute("err", "验证码不能为空");
			return "forward";
		}
		if (!check_code.equals(request.getSession().getAttribute("code"))) {
			
			modelMap.addAttribute("err", "验证码不正确");
			return "forward";
		}
		
		
		AdminUser adminUser = adminUserService.getInfoByEmpId(admin_id);
		
		
		if (adminUser == null) {

			modelMap.addAttribute("err", "账户不存在");
			return "forward";
		}
		if (adminUser.getStatus() == 1) {
			
			modelMap.addAttribute("err", "账户已关闭");
			return "forward";
		}

		try {
			boolean loginResult = LoginUtil.validateUserInfo(admin_id,admin_pwd);
			if(!loginResult){
				modelMap.addAttribute("err", "密码错误请重试");
				return "forward";
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MsgEmptyException e) {
			e.printStackTrace();
		}
		
//		if (!admin_pwd.equals(adminUser.getAdmin_pwd())) {
//			
//			modelMap.addAttribute("err", "密码错误请重试");
//			return "forward";
//		} else {
			
			if(cookieLogin!=null && !cookieLogin.equals("")){
				CookieManager.addCookie(response, CookieManager.PASSWORD,admin_pwd,60*60*24*30);
				CookieManager.addCookie(response, CookieManager.USERNAME,admin_id,60*60*24*30);
			}
			
			request.getSession().setAttribute("adminUser", adminUser);
			
		
			//装载adminList
			List<AdminUser> adminList=adminUserService.getList();
			request.getSession().setAttribute("adminList", adminList);
			
//			request.setAttribute("oa", admin_id);
//			request.setAttribute("every_month", DateUtil.getCurrentMonth());
//			request.setAttribute("user_name", adminUser.getAdmin_name());
			String adminName = adminUser.getAdmin_name();
			
			if("陆海茶".equals(adminName)||"李淑巾".equals(adminName)){
				
				return "forward:/getPersonChecking-inByAdmin.do";

			}else{
				
				return "forward:/getPersonChecking-in.do";
			}

//		}

	}
	
	@RequestMapping(value = "/logout.do")
	public String logout(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) {
		
		request.getSession().invalidate();
		
		return "/login/logout";
	}
	
	/*
	 * 业务用户列表查询功能
	 * type:1、根据用户名查询
	 * 		2、根据用户ID查询
	 * 		3、根据客服查询
	 * 		4、根据市场查询
	 * 
	 * 什么都不不输入默认查询开启状态的用户
	 * */
	@RequestMapping(value = "/customerInfoQuery.do")
	protected String query(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		int type=Integer.parseInt(request.getParameter("type"));
		List<AdminUser> list=null;
		
		String user_name = request.getParameter("user_name");
		
		list=adminUserService.queryByUserName(user_name);
		
		modelMap.addAttribute("user_name",user_name );
		
		modelMap.addAttribute("list", list).addAttribute("size",list.size()).addAttribute("type", type);
		return "/customer/customerList";

	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getAdminUser.do")
	public void queryAdminUser(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) {
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//List<AdminUser> list=adminUserService.getList();
//		List<AdminUser> list=(List<AdminUser>)request.getSession().getAttribute("adminList");
		List<AdminUser> list = adminUserService.getList();
		
		 JSONObject jsonObj=null;
		 JSONArray result=new JSONArray();
		 
		for(AdminUser adminUser:list){
			
			jsonObj=new JSONObject();
			jsonObj.put("value", adminUser.getAdmin_id());
			jsonObj.put("text", adminUser.getAdmin_name());
			result.add(jsonObj);
		}
			
			response.reset();
	        response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=UTF-8");
	        PrintWriter writer = null;
			try {
				writer = response.getWriter();
				writer.write(result.toString());
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				writer.close();
			}
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getDepartment.do")
	public void getDepartment(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) {
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//List<AdminUser> list=adminUserService.getList();
//		List<AdminUser> list=(List<AdminUser>)request.getSession().getAttribute("adminList");
		List<AdminUser> list = adminUserService.getDepartmentList();
		
		JSONObject jsonObj=null;
		JSONArray result=new JSONArray();
		
		for(AdminUser adminUser:list){
			
			jsonObj=new JSONObject();
			jsonObj.put("value", adminUser.getDepartment());
			jsonObj.put("text", adminUser.getDepartment());
			result.add(jsonObj);
		}
		
		response.reset();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(result.toString());
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			writer.close();
		}
	}
	
}
