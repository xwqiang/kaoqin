<%@ page language="java" contentType="text/html; charset=utf-8"%>
		<div id="header">
			<div class="header">
			
				
			
			<c:if test="${not empty adminUser}">
			
		    	用户名称:${adminUser.admin_name }&nbsp;&nbsp;<a href="logout.do">退出</a>
			
			</c:if>
			<c:if test="${empty adminUser}">
			
		    	<a href="login.do">登录</a>
			
			</c:if>
			<c:if test="${adminUser.isEmpty()}">
			
		    	<a href="login.do">登录fa</a>
			
			</c:if>
			
			<c:if test="${adminUser!=null}">
			
		    	<a href="login.do">登录fafaffa</a>
			
			</c:if>
			
			
		   	 </div>
		</div>
