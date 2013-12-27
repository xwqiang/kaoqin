<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<script language="javascript">

var  path="<%=path%>";
var nn = 2;
function num(){
 document.getElementById("nuber").innerHTML = nn;
 nn --;
 if(nn==0){
top.location.href=path+'/login.do';
}
}

function SetNum()
{
 setInterval("num()",1000);
}
</script>

<body onLoad="SetNum();">
<p>session失效，<span id="nuber" style="color:#FF0000;">3</span>秒后返回...</p>
<div></div>
</body>

</html>