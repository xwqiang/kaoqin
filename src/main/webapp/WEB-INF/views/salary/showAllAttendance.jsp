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
		<title>汇总</title>
		<link type="text/css" rel="stylesheet" href="css/global.css" />
		<link type="text/css" rel="stylesheet" href="css/collect.css" />
		<link type="text/css" rel="stylesheet" href="css/content.css" />
		<link rel="stylesheet" type="text/css" href="css/default/om-default.css"/>
		<link rel='stylesheet' href='cupertino/theme.css' />
		<link rel="stylesheet" href="css/validationEngine.jquery.css" type="text/css" media="screen" title="no title" charset="gbk" />
		<script type="text/javascript" src="jquery/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="js/time/WdatePicker.js" defer="defer"></script>	
		<script type="text/javascript" src="js/operamasks-ui.min.js"></script>
		<script src="jquery/jquery.validationEngine-cn.js" type="text/javascript"></script>
		<script src="jquery/jquery.validationEngine.js" type="text/javascript"></script>
		<script src="js/CJL.0.1.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/CJL.0.1.min.js"></script>
		<script type="text/javascript">
		
			
    
			$(document).ready(function() {
				
				//部门菜单变色
				 $(".${tag}").addClass("current");
				//首先将#back-to-top隐藏
				 $("#back-to-top").hide();
				//当滚动条的位置处于距顶部100像素以下时，跳转链接出现，否则消失
				$(function () {
				$(window).scroll(function(){
				if ($(window).scrollTop()>100){
				$("#back-to-top").fadeIn(1500);
				}
				else
				{
				$("#back-to-top").fadeOut(1500);
				}
				});
				//当点击跳转链接后，回到页面顶部位置
				$("#back-to-top").click(function(){
				$('body,html').animate({scrollTop:0},1000);
				return false;
				});
				});
			
		 $("#formID").validationEngine();
			
			$('#department').omCombo({
	                dataSource :'getDepartment.do',
	                onValueChange:function(target,newValue,oldValue,event){ 
	 				$('#oa').val(newValue);
	 			  }
	            });
			$('#user_name').omCombo({
	                dataSource :'getAdminUser.do',
	                onValueChange:function(target,newValue,oldValue,event){ 
	 				$('#oa').val(newValue);
	 			  }
	            });
			$("tr").hover(
	  			function () {
	   				 $(this).addClass("hover");
	  			},
	  			function () {
	    			$(this).removeClass("hover");
	  			}
			  );
			 
			});
			
		</script>
		
		<style>
	body {
			text-align: center;
			font-size: 13px;
			font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
			background:#eee;
		}
	.hover {
		background: #D0E5F5;
	}
	
	.collect_tbl td a{ color:#0052aa;}
	.tablefixed{width:600px; border-collapse:collapse;}
	.tablefixed td{ border:5px solid #999; padding:10px;}
	.tablefixed thead, .tablefixed tfoot{ background:#CCC;}
	
		.menu{
	display:block;
	position:fixed;
	_position:absolute;
	z-index:999;
	left: 50%;
	margin-left: -670px;
	top: 108px;
	_top:auto;
	_margin-top:108px;
	width:100px;
	height:330px;
	overflow:hidden;
	border:1px solid #CCC;	
	background:#FFF;
	border-radius:0px;
	
	/*box-shadow:0 1px 4px 0 #CCC;*/
	}
.menu h3{ text-align:center; background:#9ad5f1; color:#1E5791; font-size:12px; line-height:30px; margin-bottom:6px;}
.menu li{ text-align:left;}
.menu li a{ display:block; padding:2px 0 2px 24px; color:#1E5791;}
.menu li a:hover{text-decoration: none;color:#FF5C00; }
.menu li .current{ background:#FEDCBD;}
	
	.shortcut-box{
	display:block;
	position:fixed;
	_position:absolute;
	z-index:999;
	right: 50%;
	margin-right: -600px;
	bottom: 140px;
	_bottom:auto;
	_top:expression(eval(document.documentElement.scrollTop+document.documentElement.clientHeight-this.offsetHeight-(parseInt(this.currentStyle.marginTop,10)||0)-(parseInt(this.currentStyle.marginBottom,10)||0)));
	 _margin-bottom:140px;
	width:40px;
	height:45px;
	overflow:hidden;
	}
.shortcut-box a{
	display:block;
	width:30px;
	height:36px;
	padding:3px 4px;
	border:1px solid #CCC;
	text-decoration:none;
	color:#666;
	font-size:12px;
	letter-spacing:3px;
	line-height:1.5;
	background:#FFF;
	}
.shortcut-box a:hover{
	border:1px solid #FFF;
	color:#FFF;
	background:#33A3DC;
	}
</style>
	</head>
	<body id="top">
	
		<div id="nav">
			<ul>
				<li><a href="getPersonChecking-in.do">个人考勤</a></li>
				<li><a href="showAllAttendance.do" class="current">&nbsp;&nbsp;&nbsp;&nbsp;统计&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
			</ul>
		</div>
		<!--搜索部分 start-->
		<div id="toolbar">
			<form method="post" action="showAllAttendance.do?flag=admin" id="formID" accept-charset="utf-8">
				
				<span> 
					<!-- 
				    <label for="department">
						部门：
					</label>
					<input id="department" name="department" style="width: 60px" value="${department }" class="text-input field"/> 
					<input	type="hidden" id="oa" name="oa" value="${oa }" /> 
					<input	type="hidden" id="type" name="type" value="query" /> 
					</span>
					 -->
				<span> <label for="user_name">
						员工姓名：
					</label>
					<input id="user_name" name="user_name" style="width: 60px" value="${user_name }" class="text-input field"/> 
					<input	type="hidden" id="oa" name="oa" value="${oa }" /> 
					<input	type="hidden" id="type" name="type" value="query" /> 
					</span>

						<span><label for="every_month">
						开始月份：
						</label>
						<input  type="text" name="start_month" id="start_month" value="${start_month}" class=" validate[required] text-input field" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM',minDate:'2013-09',maxDate:'%y-%M'})" class="inpstyle validate[required] text-input field"/>
					</span>
					<span><label for="every_month">
						截止月份：
						</label>
						<input  type="text" name="end_month" id="end_month" value="${end_month}" class=" validate[required] text-input field" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM',minDate:'#F{$dp.$D(\'start_month\')}',maxDate:'%y-%M'})" class="inpstyle validate[required] text-input field"/>
					</span>
				<span><input class="grid-button" type="submit" value="查 询" />
					
						</span>
			</form>
			<!--按钮-->
		</div>
			<!--搜索部分 end-->
		<div id="Container" style="margin-bottom: 50px;">
		<div class="collect" style="width:1120px; margin:0 auto;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="collect_tbl" id="idTableFixed" class="tablefixed">
				
				<tr class="t_title" style="background-color: #A3C6FB">
					
					<th nowrap="nowrap">
						部门
					</th>
					<th nowrap="nowrap">
						人员名称
					</th>
					<th nowrap="nowrap">
						oa工号
					</th> 
					<th width="45">
						办公室上班
					</th> 
					<th width="45">
						外出
					</th> 
					<th width="45">
						出差
					</th> 
					<th width="45">
						调休
					</th> 
					<th width="45">
						年假
					</th> 
					<th width="45">
						婚假
					</th> 
					<th width="45">
						产假
					</th> 
					<th width="45">
						丧假
					</th> 
					<th width="45">
						旷工
					</th> 
					<th width="45">
						迟到-罚
					</th> 
					<th width="45">
						早退
					</th> 
					<th width="45">
						忘打卡-罚
					</th> 
					<th width="45">
						出勤
					</th> 
					<th width="45">
						事假
					</th> 
					<th width="45">
						病假
					</th> 
					<th width="45">
						饭补
					</th> 
					<!-- 
					<th width="45">
						工作日加班
					</th> 
					 -->
					<th width="45">
						工作日加班
					</th> 
					<th width="45">
						休息日加班
					</th> 
					<th width="45">
						法定假日加班
					</th> 
					<th width="45">
						待处理
					</th> 
					
				</tr>
					<c:forEach var="statistic" items="${list}" varStatus="status">
						<tr class="collect_item">
								<td nowrap="nowrap">
									<strong>${statistic.department}</strong>
								</td>
								<td>
									<a title="查看明细" href="getPersonChecking-in.do?every_month=${every_month }&user_name=${statistic.user_name }">${statistic.user_name }</a>
								</td>
								
								<td>
									<a title="查看明细" href="getPersonChecking-in.do?every_month=${every_month }&user_name=${statistic.user_name }">${statistic.oa }</a>
								</td>
								<td>
									<c:if test="${statistic.office_date_count!=0}">
									
										${statistic.office_date_count}
									</c:if>
								</td>
								<td>
									<c:if test="${statistic.waichu_count!=0}">
										${statistic.waichu_count}
									</c:if>
								</td>
								<td>
									<c:if test="${statistic.chuchai_count!=0}">
										${statistic.chuchai_count}
									</c:if>
								</td>
								<td>
								<c:if test="${statistic.tiaoxiu_count!=0}">
									${statistic.tiaoxiu_count}
									</c:if>
								</td>
								<td>
									<c:if test="${statistic.nianjia_count!=0}">
									
									${statistic.nianjia_count}
									</c:if>
								</td>
								<td>
								<c:if test="${statistic.hunjia_count!=0}">
									${statistic.hunjia_count}
									</c:if>
								</td>
								<td>
								<c:if test="${statistic.chanjia_count!=0}">
									${statistic.chanjia_count}
									</c:if>
								</td>
								<td>
								<c:if test="${statistic.sangjia_count!=0}">
									${statistic.sangjia_count}
									</c:if>
								</td>
								<td>
								<c:if test="${statistic.kuanggong_count!=0}">
									${statistic.kuanggong_count}
									</c:if>
								</td>
								
								<td>
									<c:if test="${statistic.late_fine!=0}">
									${statistic.late_fine}
									</c:if>
								</td>
								<td>
									
								</td>
								<td>
									<c:if test="${statistic.forget_fine!=0}">
										${statistic.forget_fine}
									</c:if>
								</td>
								<td>
								<c:if test="${statistic.real_working_date_count!=0}">
									${statistic.real_working_date_count}
									</c:if>
								</td>
								<td>
									<c:if test="${statistic.shijia_count!=0}">
										${statistic.shijia_count}
									</c:if>
								</td>
								<td>
									<c:if test="${statistic.bingjia_count!=0}">
									${statistic.bingjia_count}
									</c:if>
								</td>
								<td>
								<c:if test="${statistic.fanbu_date_count!=0}">
									${statistic.fanbu_date_count}
									</c:if>
								</td>
								<td>
								<c:if test="${statistic.workdate_overtime_count!=0}">
									${statistic.workdate_overtime_count}
									</c:if>
								</td>
								<td>
								<c:if test="${statistic.overtime_count!=0}">
									${statistic.overtime_count}
									</c:if>
								</td>
								<td>
								<c:if test="${statistic.holiday_overtime_count!=0}">
									${statistic.holiday_overtime_count}
									</c:if>
								</td>
								<td>
								<c:if test="${statistic.undetermined_count!=0}">
									<a title="查看明细" style="color: red;font-weight: bold;" href="getPersonChecking-in.do?every_month=${every_month }&user_name=${statistic.user_name }">${statistic.undetermined_count}</a>
									</c:if>
								</td>
						</tr>
					</c:forEach>	
					<!-- 			
						<tr>
							<td colspan="3"><strong>合计</strong></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						 -->
			</table>
		</div>
		</div>
		
		<div id="menu" class="menu">
				<h3>部门分类</h3>
				<ul>
				<li><a class="cwb" href="showAllAttendance.do?department=财务部&start_month=${start_month }&end_month=${end_month }">财务部</a></li>
				<li><a class="jsb" href="showAllAttendance.do?department=技术部&start_month=${start_month }&end_month=${end_month }">技术部</a></li>
				<li><a class="kfb" href="showAllAttendance.do?department=客服部&start_month=${start_month }&end_month=${end_month }">客服部</a></li>
				<li><a class="rzb" href="showAllAttendance.do?department=人资部&start_month=${start_month }&end_month=${end_month }">人资部</a></li>
				<li><a class="swb" href="showAllAttendance.do?department=商务拓展部&start_month=${start_month }&end_month=${end_month }">商务拓展部</a></li>
				<li><a class="scyb" href="showAllAttendance.do?department=市场1部&start_month=${start_month }&end_month=${end_month }">市场1部</a></li>
				<li><a class="sceb" href="showAllAttendance.do?department=市场2部&start_month=${start_month }&end_month=${end_month }">市场2部</a></li>
				<li><a class="scsb" href="showAllAttendance.do?department=市场3部&start_month=${start_month }&end_month=${end_month }">市场3部</a></li>
				<li><a class="cpyyb" href="showAllAttendance.do?department=产品运营部&start_month=${start_month }&end_month=${end_month }">产品运营部</a></li>
				<li><a class="wwb" href="showAllAttendance.do?department=无为部&start_month=${start_month }&end_month=${end_month }">无为部</a></li>
				<li><a class="zjb" href="showAllAttendance.do?department=总经办&start_month=${start_month }&end_month=${end_month }">总经办</a></li>
				</ul>
		</div>
		
		<div id="shortcut-box" class="shortcut-box">
			<p id="back-to-top">
				<a href="#top">返回顶部</a>
			</p>
		</div>
	</body>
</html>
<script language="javascript">

	function senfe(o,a,b,c,d){ 
			var t=document.getElementById(o).getElementsByTagName("tr"); 
			for(var i=0;i<t.length;i++){ 
			t[i].style.backgroundColor=(t[i].sectionRowIndex%2==0)?a:b; 
			t[i].onclick=function(){ 
			if(this.x!="1"){ 
			this.x="1";//本来打算直接用背景色判断，FF获取到的背景是RGB值，不好判断 
			this.style.backgroundColor=d; 
			}else{ 
			this.x="0"; 
			this.style.backgroundColor=(this.sectionRowIndex%2==0)?a:b; 
			} 
			} 
			t[i].onmouseover=function(){ 
			if(this.x!="1")this.style.backgroundColor=c; 
			} 
			t[i].onmouseout=function(){ 
			if(this.x!="1")this.style.backgroundColor=(this.sectionRowIndex%2==0)?a:b; 
			} 
			} 
			} 
			
			
			
			//senfe("表格名称","奇数行背景","偶数行背景","鼠标经过背景","点击后背景"); 
			//senfe("idTableFixed","#FCFCFC","#FFFFFF","",""); 
			senfe("idTableFixed","#F4F9Fc","#FFFFFF","",""); 
			
			
			
			
			
			var tf = new TableFixed("idTableFixed");
tf.clone();//表格结构修改后应重新clone一次

$$("idPos").onclick = function(){
	tf.auto = false; tf.setPos(.5);
}

$$("idHide").onclick = function(){
	if(tf.hide){
		tf.hide = false;
		this.value = "取消定位";
	}else{
		tf.hide = true;
		this.value = "显示定位";
	}
	tf.run();
}
</script>