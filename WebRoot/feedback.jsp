<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

		<link type="text/css" rel="stylesheet" href="css/jquery.feedback_me.css" />
		<link type="text/css" rel="stylesheet" href="css/jquery.feedback_me.rtl.css" />
		
		<script type="text/javascript" src="jquery/jquery-1.9.1.min.js"></script>
		<script src='jquery/jquery-ui-1.10.2.custom.min.js'></script>
		<script type="text/javascript" src="js/jquery.feedback_me.js"></script>
		<script src="jquery/json2.js" type="text/javascript"></script>
  <script type="text/javascript">

	$(document).ready(function(){
    //set up some basic options for the feedback_me plugin
    fm_options = {
        position: "left-bottom",
        name_required: true,
        message_placeholder: "Go ahead, type your feedback here...",
        message_required: true,
        show_asterisk_for_required: true,
        feedback_url: "send_feedback_clean",
        custom_params: {
            csrf: "my_secret_token",
            user_id: "john_doe",
            feedback_type: "clean"
        }
    };
    //init feedback_me plugin
    fm.init(fm_options);
});
  </script>
  </head>
  <body> 
    <br><br>This is my JSP page. <br>
  </body>
</html>
