<%@ page language="java" contentType="text/html;charset=GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
		<title>业务用户列表</title>
		<link type="text/css" rel="stylesheet"
			href="<%=path%>/css/global.css" />
		<link type="text/css" rel="stylesheet"
			href="<%=path%>/css/content.css" />
		<script type="text/javascript" src="js/jquery/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="js/util/tableStyle.js"></script>
		<script type="text/javascript">
</script>

	</head>
	<body>
		<div id="container">
			<!--搜索区域 start-->
			<div id="toolbar">

				<!--按钮-->
				
				<!--按钮-->
			</div>
			<!--搜索区域 end-->
			<!--数据 start-->
			<div class="datalist">
				<table cellpadding="0" cellspacing="0" class="tablestyle"
					id="tableStyle">
					<tbody>
						<tr>
							<th width="50" nowrap="nowrap">
								序号
							</th>
							<th nowrap="nowrap">
								人员名称
							</th>
							<th nowrap="nowrap">
								打卡日期
							</th>
							<th nowrap="nowrap">
								签到时间
							</th>
							<th nowrap="nowrap">
								签退时间
							</th>
							<th nowrap="nowrap">
								类型
							</th>
							<th nowrap="nowrap">
								备注
							</th>
							
							<th nowrap="nowrap">
								操作
							</th>
						</tr>
					</tbody>
					<tbody>
						<c:forEach var="salary" items="${list}" varStatus="status">
							<tr>
								<td>
									${status.index+1}&nbsp;										
								</td>
								<td>
									${salary.user_name}
								</td>
								<td>
									${salary.every_date}

								</td>
								<td>
									${salary.checkin_time}
								</td>
								<td>
									${salary.checkout_time}
								</td>
								<td>
									${salary.type}
								</td>
								<td>
									${salary.comment}
								</td>
								<td>
									<a href="modCustomerInfo.do?sn=${customerinfo.sn}">[修改]</a>
								</td>

							</tr>
						</c:forEach>

					</tbody>
				</table>
				<!--数据 end-->

			</div>

		</div>
	</body>
</html>
