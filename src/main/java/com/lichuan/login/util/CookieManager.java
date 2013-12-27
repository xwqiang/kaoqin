package com.lichuan.login.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 
 * @author zjf
 * @time 2:37:09 PM
 */
public class CookieManager {
	/** 保存cookie的map */
	private static Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
	public static final String USERNAME = "username";
	/** 密码key */
	public static final String PASSWORD = "password";

	/** 
	 * 设置cookie 
	 * @param response 
	 * @param name  cookie名字 
	 * @param value cookie值 
	 * @param maxAge cookie生命周期  以秒为单位 
	 */
	public static void addCookie(HttpServletResponse response, String name,
			String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

	/** 
	 * 根据名字获取cookie 
	 * @param name cookie名字 
	 * @return 
	 */
	public static Cookie getCookieByName(String name) {
		if (cookieMap.containsKey(name)) {
			Cookie cookie = (Cookie) cookieMap.get(name);
			return cookie;
		} else {
			return null;
		}
	}

	/** 
	 * 将cookie封装到Map里面 
	 * @param request 
	 * @return 
	 */
	public static void readCookieMap(HttpServletRequest request) {
		cookieMap.clear();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
	}

}
