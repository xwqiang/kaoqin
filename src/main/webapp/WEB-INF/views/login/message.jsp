<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
html,body{ margin:0; padding:0; text-align:center;}
.clear{ clear:both;}
#error{ margin:45px auto 0; width:686px; height:350px; background:url(images/error-bg.gif) no-repeat 0 0; text-align:left; color:#333;}
.e01, .e02{ display:inline-block; width:48px; height:48px; overflow:hidden; background:url(images/error-bg.gif) no-repeat 0 0; float:left;}
.e01{ background-position:0 -474px;}
.e02{ background-position:-62px -474px;}
.error{ padding:140px 0 0 143px;}
h3{ margin:0;
	float:left;
	font:normal 24px/1.2 "微软雅黑";
	color:#555;
	padding-left:50px;
}
</style>
<title>信息页面</title>
</head>

<body>
<div id="error">
	<div class="error">
    	<div class="e0${flag }"></div><h3>${message }</h3>
    	<div class="clear"></div>
    </div>
</div>
</body>
</html>
