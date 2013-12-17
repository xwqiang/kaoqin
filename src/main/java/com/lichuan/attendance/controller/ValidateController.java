package com.lichuan.attendance.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lichuan.attendance.model.AdminUser;
import com.lichuan.attendance.service.AdminUserService;

/**
 * 
 * Descirption:jquery 校验控制器
 * 
 * @author 1202211
 * 
 */
@Controller
public class ValidateController {

	protected final transient Log log = LogFactory
			.getLog(ValidateController.class);

	@Autowired
	private AdminUserService adminUserService;

	@RequestMapping(value = "/validate.do")
	public String validate(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		PrintWriter out = response.getWriter();
		String validateError = request.getParameter("validateError");
		if (null != validateError) {
			String validateValue = request.getParameter("validateValue");
			System.out.println("validateValue before:"+validateValue);
			if(validateValue!=null){
				//中文乱码处理
//				validateValue = new String(validateValue.getBytes("iso8859-1"),"utf-8");
			}
			
			String validateId = request.getParameter("validateId");
			System.out.println("validateValue:"+validateValue);
			System.out.println("validateId:"+validateId);
			/* RETURN VALUE */
			String[] json = new String[3];
			json[0] = validateId;
			json[1] = validateError;

			if (validateValue == null || validateValue.trim().length() < 1) {
				json[2] = "false"; // RETURN TRUE
				JSONArray jsonArray = JSONArray.fromObject(json);
				out.write("{jsonValidateReturn:" + jsonArray + "}");
				return null;
			}
			validateValue = validateValue.trim();
			if (validateError.equalsIgnoreCase("ajaxAdminUser")) {
				// 人员验证
				// 财务账户校验
				// AdminUser model =
				// adminUserService.getInfoByAdminId(validateValue);
				AdminUser model = adminUserService.getInfoByAdminName(validateValue);
				if (model != null) { // validate

					json[2] = "true"; // RETURN TRUE
					JSONArray jsonArray = JSONArray.fromObject(json);
					out.write("{jsonValidateReturn:" + jsonArray + "}");
				} else {
					json[2] = "false";
					JSONArray jsonArray = JSONArray.fromObject(json);
					out.write("{jsonValidateReturn:" + jsonArray + "}");
				}
			}

		}
		return null;

	}
}