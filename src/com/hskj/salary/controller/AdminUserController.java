package com.hskj.salary.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hskj.model.AdminUser;
import com.hskj.salary.service.AdminUserService;


@Controller
public class AdminUserController {
	
	@Autowired
	private AdminUserService adminUserService;

	
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
