<%@ page contentType="text/html;charset=gbk" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%response.setStatus(200);%>

<%
	Throwable ex = null;
	if (exception != null)
		ex = exception;
	if (request.getAttribute("javax.servlet.error.exception") != null)
		ex = (Throwable) request.getAttribute("javax.servlet.error.exception");

	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>500提示页面</title>
		<style type="text/css">
div#main1 {
	width: 800px;
	height: 400px;
	border: 1px solid #ccc;
	margin: 0 auto;
	margin-top: 100px;
}

div#main1 div.title1 {
	font-size: 18px;
	color: #333;
	height: 80px;
	font-weight: bold;
	text-align: center;
	line-height: 80px;
}

div#main1 div.img {
	float: left;
}

div#main1 div.info {
	margin-left: 20px;
	float: left;
	font-size: 18px;
	line-height: 240%;
}

div#main1 div.info a {
	color: #00c;
	text-decoration: underline;
}
</style>
	</head>
	<body>
		<div id="main1">
			<div class="title1">
				很抱歉，系统发生内部错误.
			</div>
			<div class="img">
				<img src="./images/error500.jpg" alt="" />
			</div>
			<div class="info">
				1、请检查您的操作是否正确。
				<br />
				2、感谢您的使用，我们会尽快解决此问题。
				
				<br />
			</div>
		</div>
	</body>
</html>
