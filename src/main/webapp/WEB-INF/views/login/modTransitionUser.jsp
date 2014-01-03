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
		<title>业务用户转正</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link type="text/css" rel="stylesheet" href="css/global.css" />
		<link type="text/css" rel="stylesheet" href="css/content.css" />
		<link rel="stylesheet" type="text/css"
			href="css/default/om-default.css" />
		<link rel="stylesheet" href="css/validationEngine.jquery.css"
			type="text/css" media="screen" title="no title" charset="gbk" />
		<script type="text/javascript" src="js/jquery/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="js/js/operamasks-ui.min.js"></script>
		<script src="js/jquery/jquery.validationEngine-cn.js"
			type="text/javascript"></script>
		<script src="js/jquery/jquery.validationEngine.js"
			type="text/javascript"></script>
		<script type="text/javascript" src="js/time/WdatePicker.js"></script>
		<script type="text/javascript">
		$(document).ready(function() {
			$("#formID").validationEngine();
});
		</script>


		<style>
/*到款详单
*/
.form-div {
	margin: 10px;
	width: 960px;
}

.form-div h3 {
	text-align: center;
	font-size: 14px;
	margin: 10px 0;
	color: #0066cc;
}

.form-table {
	color: #4D4D4D;
	border: 1px solid #EFEFEF;
	border-bottom: none;
	border-right: none;
}

.form-table td {
	border: 1px solid #EFEFEF;
	border-top: none;
	border-left: none;
	padding: 8px 0;
}

.form-table td.form-tbl-title {
	background: #FAFAFA;
	width: 65px;
	padding-left: 15px;
	color: #0066cc;
}

.form-btn {
	padding: 10px 10px 10px 95px;
	border-bottom: 1px solid #EFEFEF;
}

.user_ip {
	
}

.user_ip span {
	display: block;
	float: left;
	padding: 4px;
	margin: 4px;
	background: #E1F2FC;
	color: #0066cc;
	position: relative;
}

.user_ip span a {
	display: block;
	position: absolute;
	background: url(images/layout/tabs_close.gif) no-repeat center;
	width: 10px;
	height: 10px;
	top: -5px;
	right: -5px;
}
</style>
	</head>

	<body>
		<div id="container">
			<div class="add_top">
				<a href="#">
					&gt;&gt;返回</a>
			</div>
			<!--add_form start-->
			<form action="modTransitionUser_submit.do" method="post" id="formID">
				<div class="form-div">
					<h3>
						财务修改
					</h3>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="form-table">
						<tr>
							<td width="150" class="form-tbl-title">
								<label style="float: right;">
									客户编码：
								</label>
							</td>
							<td width="307">
								&nbsp;${allUserInfo.customer_code }
								<input type="hidden" name="customer_code" id="customer_code"
									value="${allUserInfo.customer_code }" />
							</td>
							<td width="257" class="form-tbl-title">
								<label style="float: right;">
									服务器ID：
								</label>
							</td>
							<td width="294">
								&nbsp;${allUserInfo.server_id }
								<input type="hidden" name="server_id" id="server_id"
									value="${allUserInfo.server_id }" />
							</td>
						</tr>
						<tr>
							<td width="101" class="form-tbl-title">
								<label style="float: right;">
									用户ID：
								</label>
							</td>
							<td width="307">
								&nbsp;${allUserInfo.user_id }
								<input id="user_id" type="hidden" name="user_id" value="${allUserInfo.user_id }"/>
							</td>
							<td width="101" class="form-tbl-title">
								<label style="float: right;">
									用户名称：
								</label>
							</td>
							<td width="307">
								&nbsp;${userInfo.user_name }
								
							</td>
						</tr>
						<tr>
							<td width="257" class="form-tbl-title">
								<label style="float: right;">
									市场人员：
								</label>
							</td>
							<td width="294">
								&nbsp;<c:forEach items="${sessionScope.adminList}"
									var="each">
									<c:if test="${allUserInfo.market_staff eq each.admin_id}">
									${each.admin_name }
								</c:if>
								</c:forEach>
							</td>
							<td width="101" class="form-tbl-title">
								<label style="float: right;">
									客服人员：
								</label>
							</td>
							<td width="307">
								&nbsp; <c:forEach items="${sessionScope.adminList}"
									var="each">
									<c:if test="${allUserInfo.service_staff eq each.admin_id}">
									${each.admin_name }
								</c:if>
								</c:forEach>
								
							</td>
							
						</tr>

						<tr>
							<td width="101" class="form-tbl-title">
								<label style="float: right;">
									价格：
								</label>
							</td>
							<td width="307">
								&nbsp;
								<c:if test="${userInfo.status==3}">
								0.01
								</c:if>
								<c:if test="${userInfo.status!=3}">
								<input name="price" type="text"
									class=" validate[onlyNumber9,length[1,6]] text-input field"
									id="price" value="${userInfo.price }" />
									</c:if>
								&nbsp;元
							</td>
							<td class="form-tbl-title">
								<label style="float: right;">
									计费方式：
								</label>
							</td>
							<td>
								&nbsp;
								<c:if test="${userInfo.charge_type==0}">
								<input type="radio" name="charge_type" value="0" checked="checked"/>
								成功计费
								<input type="radio" name="charge_type" value="1"/>
								提交计费
								</c:if>
								<c:if test="${userInfo.charge_type==1}">
								<input type="radio" name="charge_type" value="0" />
								成功计费
								<input type="radio" name="charge_type" value="1"
									checked="checked" />
								提交计费
								</c:if>
								
							</td>
						</tr>
						<tr>
						
							<td width="257" class="form-tbl-title">
								<label style="float: right;">
									用户类型：
								</label>
							</td>
							<td width="294">
								&nbsp;
								<c:if test="${userInfo.user_type==0}">
								<input type="radio" name="user_type" value="0" checked="checked" />
								预付费
								<input type="radio" name="user_type" value="1" />
								后付费
								</c:if>
								<c:if test="${userInfo.user_type==1}">
								<input type="radio" name="user_type" value="0" />
								预付费
								<input type="radio" name="user_type" value="1" checked="checked"/>
								后付费
								</c:if>
								
							</td>
							
						</tr>
						
					</table>

					<div class="form-btn">
						<input name="" type="submit" value="提交" />
						<input name="" type="reset" value="重置" onclick="" />
					</div>
				</div>
			</form>
			<!--add_form end -->
		</div>
	</body>
</html>
