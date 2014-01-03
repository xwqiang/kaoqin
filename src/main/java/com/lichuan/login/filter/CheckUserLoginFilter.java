package com.lichuan.login.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lichuan.attendance.model.AdminUser;
import com.lichuan.common.aop.ParameterRequestWrapper;
import com.lichuan.common.aop.SystemSession;

public class CheckUserLoginFilter implements Filter {

	protected FilterConfig filterConfig;
	
	private static ThreadLocal<HttpServletRequest> requestLocal= new ThreadLocal<HttpServletRequest>();

	public static ThreadLocal<HttpServletRequest> getRequestLocal() {
		return requestLocal;
	}

	public static void setRequestLocal(ThreadLocal<HttpServletRequest> requestLocal) {
		CheckUserLoginFilter.requestLocal = requestLocal;
	}

	public void init(FilterConfig arg0) throws ServletException {

		this.filterConfig = arg0;
	}

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		AdminUser adminUser = (AdminUser) ((HttpServletRequest) request)
				.getSession().getAttribute("adminUser");
		String strURL = ((HttpServletRequest) request).getRequestURL()
				.toString();
		
		HttpServletRequest req = (HttpServletRequest) request;  
		request.setCharacterEncoding("utf-8");
		HashMap map=new HashMap(request.getParameterMap());
		Set<String> key = map.keySet();
		for (Iterator it = key.iterator(); it.hasNext();) {
			String argments= (String) it.next();
			String[] argmentsVal=request.getParameterValues(argments);
			if(argmentsVal!=null && argmentsVal.length>0){
				String[] argmentsVal_new =new String[argmentsVal.length];
				for (int i = 0; i < argmentsVal.length; i++) {
					String argmentsValeach=argmentsVal[i].trim();
					if(argmentsVal[i]!=null){
						argmentsVal_new[i]=argmentsValeach;
					}
				}
				map.put(argments, argmentsVal_new);
			}
			
		}      
		ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(req,map);         
		if (strURL.indexOf("kaoqin.baiwutong.com") != -1
				|| strURL.indexOf("/login.do") != -1
				|| strURL.indexOf("/findPwdSubmit.do") != -1
				|| strURL.indexOf("/getCookieUser.do") != -1
				|| strURL.indexOf("/findPwd.do") != -1
				|| strURL.indexOf("/adminLogOut.do") != -1
				|| strURL.indexOf("/getPersonChecking-in.do") != -1
				|| strURL.indexOf("/showAllAttendance.do") != -1
				|| strURL.indexOf("/ssoLogin.do") != -1
				|| strURL.indexOf("/listen.do") != -1) {
			chain.doFilter(wrapRequest, response);//不拦截
		} else if (adminUser == null) {

			ServletContext sc = filterConfig.getServletContext();
			RequestDispatcher rd = sc
					.getRequestDispatcher("/WEB-INF/views/dealSession.jsp");
			try {
				rd.forward(request, response);
			} catch (ServletException sx) {
				filterConfig.getServletContext().log(sx.getMessage());
			} catch (IOException iox) {
				filterConfig.getServletContext().log(iox.getMessage());
			}

		} else {
			
//			SystemSession.setRequest((HttpServletRequest) wrapRequest);  
//			SystemSession.setResponse((HttpServletResponse) response);
//			HashSet<String> all_dutySet = (HashSet<String>) ((HttpServletRequest) request).getSession().getAttribute("all_dutySet");
//			// 权限
//			HashSet<String> dutySet = (HashSet<String>) ((HttpServletRequest) request).getSession().getAttribute("dutySet");
//			// request
//			String url_tem = ((HttpServletRequest) request).getRequestURI();
//			String[] strs = url_tem.split("/");
//			int length = 5;
//			if (strs != null && strs.length == 3) {
//				length = strs[1].length() + 2;
//			}
//
//			String url = url_tem.substring(length, url_tem.length());
//			if (!all_dutySet.contains(url)) {
//				System.out.println("<"+url + ">未放入权限系统");
//				chain.doFilter(wrapRequest, response);// 未放入权限系统
//			} else if (!dutySet.contains(url) && all_dutySet.contains(url)) {
//				System.out.println("<"+url + ">没有权限");
//				ServletContext sc = filterConfig.getServletContext();
//				RequestDispatcher rd = sc.getRequestDispatcher("/WEB-INF/views/dealRole.jsp");
//				try {
//					rd.forward(wrapRequest, response); // 转发到index.jsp让用户登录
//				} catch (ServletException sx) {
//					filterConfig.getServletContext().log(sx.getMessage());
//				} catch (IOException iox) {
//				filterConfig.getServletContext().log(iox.getMessage());
//				}
//			} else {
//				System.out.println("<"+url + ">有权限");
//				chain.doFilter(wrapRequest, response);
//			}
			
			
			chain.doFilter(wrapRequest, response);
		}

	}

	public void destroy() {
		this.filterConfig = null;
	}

}
