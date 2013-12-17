<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link type="text/css" rel="stylesheet" href="css/global.css" />
		<link type="text/css" rel="stylesheet" href="css/collect.css" />
		<link type="text/css" rel="stylesheet" href="css/content.css" />
		<link rel="stylesheet" href="css/validationEngine.jquery.css" type="text/css" media="screen" title="no title" charset="gbk" />
		<script type="text/javascript" src="js/jquery/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="js/time/WdatePicker.js" defer="defer"></script>	
		<script src="js/jquery/jquery.validationEngine-cn.js" type="text/javascript"></script>
		<script src="js/jquery/jquery.validationEngine.js" type="text/javascript"></script>
		<script src="js/CJL.0.1.min.js" type="text/javascript"></script>
		<title>汇总</title>

	</head>

	<body>
		<!--搜索区域 start-->
		<div id="toolbar" style="min-width: 1105px;">
			<form method="post" action="gateTdDataCompare.do"
				id="filterForm" name="filterForm">
				<span><label for="finance_id">
						月份：
					</label>
					<input  type="text" name="month" id="month"  value="${month }" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'})" class="inpstyle validate[required] text-input field"/>
				</span>
				<span><input class="grid-button" type="submit" value="查 询" />
				</span>
				<!--<span><a href="javascript:void(0);" class="link_red">导出查询数据</a></span>-->
			</form>
		</div>
		
		<div class="collect">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="collect_tbl" id="idTableFixed">
				
				<tr class="t_title" style="background-color: #A3C6FB">
					
					<th nowrap="nowrap">
						人员名称
					</th>
					<!--<th nowrap="nowrap">
						通道名称
					</th>  -->
					
					<%
						for(int i=1;i<=31;i++){
						
						out.print("<th nowrap=\"nowrap\">");
						out.print(i);
						out.print("</th>");
						}
					
					 %>
					
				</tr>
				
				<c:forEach items="${map}" var="entry">
						<tr class="collect_item">
								<td>
									<strong>${entry.key }</strong>
								</td>
							<c:forEach items="${entry.value}" var="each" varStatus="status">
								
								<!-- 
								<td>
									${each.every_date }
								</td>
								 -->
								<td>
									${each.checkin_time }
									<br/>
									${each.checkout_time }
									<br/>
									${each.comment}
								</td>
								
							</c:forEach>
						</tr>
						
				</c:forEach>
			</table>
		</div>
	</body>
</html>
