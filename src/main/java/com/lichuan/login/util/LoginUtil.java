package com.lichuan.login.util;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.dom4j.DocumentException;

import com.company.business.developInterface.Interactive;
import com.company.business.developInterface.impl.InteractiveImpl;
import com.company.business.developInterface.model.CheckedModel;
import com.company.business.developInterface.util.MsgEmptyException;

public class LoginUtil {

	public static boolean validateUserInfo(String user_id,String user_pwd) throws HttpException, IOException, DocumentException, MsgEmptyException {
		
		
		CheckedModel ck = new CheckedModel();
		ck.setAppId("kaoqin");
		ck.setApp_pwd("kaoqin123");
		ck.setClient_time(String.valueOf(System.currentTimeMillis()));
		ck.setUserId(user_id);
		ck.setUserPwd(user_pwd);
		ck.setSso_server_url("http://192.168.5.31:8081");
		Interactive interactive = new InteractiveImpl();
		return interactive.validateUserInfo(ck);
		
	}
	public static void main(String[] args) throws HttpException, IOException, DocumentException, MsgEmptyException {
		System.out.println(validateUserInfo("1007026", "lichuan6231932"));
	}
}
