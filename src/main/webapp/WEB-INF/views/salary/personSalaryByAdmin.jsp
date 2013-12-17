<%@ page language="java" contentType="text/html;charset=utf-8"%>
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
		<title>网关账户列表</title>
		<link type="text/css" rel="stylesheet" href="css/global.css" />
		<link type="text/css" rel="stylesheet" href="<%=path%>/css/content.css" />
		<link rel="stylesheet" type="text/css" href="css/default/om-default.css" />
		<link rel='stylesheet' href='cupertino/theme.css' />
		<link rel="stylesheet" type="text/css" href="css/default/om-default.css" />
		<link href='fullcalendar/fullcalendar.css' rel='stylesheet' />
		<link href='fullcalendar/fullcalendar.print.css' rel='stylesheet' media='print' />

		<link type="text/css" rel="stylesheet" href="css/global.css" />
		<link type="text/css" rel="stylesheet"
			href="<%=path%>/css/content.css" />
		<link type="text/css" rel="stylesheet" href="css/ui-bill.css" />
		<link rel="stylesheet" type="text/css"
			href="css/default/om-default.css" />
		<link rel="stylesheet" href="css/validationEngine.jquery.css" type="text/css" media="screen" title="no title"/>
		<!-- 
		<script src='jquery/jquery-1.9.1.min.js'></script>
		<script type="text/javascript" src="js/js/operamasks-ui.min.js"></script>
		 -->
		<script type="text/javascript" src="js/jquery/jquery-1.7.1.min.js"></script>
		<script src='fullcalendar/fullcalendar.min.js'></script>
		<script type="text/javascript" src="js/time/WdatePicker.js" defer="defer"></script>	
		<script src='jquery/jquery-ui-1.10.2.custom.min.js'></script>
		<script type="text/javascript" src="js/js/operamasks-ui.min.js"></script>
		<script src="js/jquery/jquery.validationEngine-cn.js" type="text/javascript"></script>
		<script src="js/jquery/jquery.validationEngine.js" type="text/javascript"></script>
		<script type="text/javascript">
		
			$(document).ready(function() {
			
			 $("#formID").validationEngine();
			
			/*var date = new Date();
		var d = date.getDate();
		var m = date.getMonth();
		var y = date.getFullYear();*/
		
		var calendar = $('#calendar').fullCalendar({

			header: {
				left: 'prev,next today',
				center: 'title',
				right: 'month,agendaWeek,agendaDay'
			},
			// monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
             monthNames: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
              dayNames: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
              dayNamesShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
              //today: ["今天"],
              firstDay: 1,
              buttonText: {
                  today: '今天',
                  month: '月',
                  week: '周',
                  day: '日',
                  prev: '<',
                  next: '>'
             },
             
	        dayClick: function(date, allDay, jsEvent, view) { 
	        
	            var selDate =$.fullCalendar.formatDate(date,'yyyy-MM-dd');//格式化日期 
	            var setAllDay =$.fullCalendar.formatDate(allDay,'yyyy-MM-dd');//格式化日期 
	            alert(selDate);
	            alert(setAllDay);
	            /*$.fancybox({//调用fancybox弹出层 
	                'type':'ajax', 
	                'href':'event.php?action=add&date='+selDate 
	            }); */
	        } ,
			selectable: true,
			selectHelper: true,
			select: function(start, end, allDay) {
			
				var title = prompt('人资登记:');
				if (title) {
				
					var params = {title: title,
	                  start: start,
	                  end: end,
	                  allDay: allDay,
					  oa:${oa}
	              	};
              	
					$.ajax({
					
	                  url:"/salary/getPersonChecking-inByAdmin.do",
	                  type:"post",
	                  data:params,
	                    dataType: 'json',
	                    success: function(res){
	                    },
	                    err:function(res){
	                        $("#err1").html("<strong>操作有误!</strong>");
	                        $("#err1").show();
	                        setTimeout(function(){$("#err1").hide();},3000);
	                    }
	              });//insert
              
              
					calendar.fullCalendar('renderEvent',
						{
							title: title,
							start: start,
							end: end,
							allDay: allDay
						},
						true // make the event "stick"
					);
				}
				calendar.fullCalendar('unselect');
			},
			editable: true,
						events: [
				
				<c:forEach var="each" items="${calendars}" varStatus="status">
					{
						title: '${each.type_desc}',
						start: '${each.start_date}',
						end: '${each.end_date}'
					},
   	 			</c:forEach>
   	 			
				<c:forEach var="each" items="${list}" varStatus="status">
					{
						title: '${each.comment}',
						start: '${each.every_date}'
					},
   	 			</c:forEach>
   	 			
				<c:forEach var="each" items="${list}" varStatus="status">
					{
						title: '${each.show_checkin_time} - ${each.show_checkout_time}',
						start: '${each.every_date}'
					},
					
   	 			</c:forEach>
   	 			
				<c:forEach var="each" items="${leaves}" varStatus="status">
					{
						title: '未打卡，待处理',
						start: '${each.every_date}'
					},
   	 			</c:forEach>

				{
					//title: '',
					//start: new Date(y, m, 30),
					//end: new Date(y, m, 31),
					//url: 'http://google.com/'
				}
			]
		});
		$('#calendar').fullCalendar( 'gotoDate',${year},${month});
				
				$('#user_name').omCombo({
	                dataSource :'getAdminUser.do',
	                onValueChange:function(target,newValue,oldValue,event){ 
	 				$('#oa').val(newValue);
	 			  }
	            });
			});
		</script>
		<style>
	
	body {
		margin-top: 40px;
		text-align: center;
		font-size: 13px;
		font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
		}
	#Container{ width:1100px; margin:0 auto; text-align:left; overflow:hidden;}
	
	#calendar {
		width: 900px;
		margin: 0 auto;
		float:left;
		}
	.fc-sat .fc-day-number, .fc-sun .fc-day-number{ color:#F60;}
	
	.collect{ width:auto; margin:39px 0 0 905px; padding-bottom:10px;
		background:#F2F2F2; font: 12px/1.7 "Microsoft YaHei",Arial,sans-serif;}
	.collect h2{ margin:0; padding-left:20px; border:1px solid #A8CEE1; border-left:none; border-right:none;
		background:#C2EBFF; font: normal 14px/30px "微软雅黑";}
	.collect dl{ overflow:hidden; color:#444; margin:10px 0;}
	.collect dt, .collect dd{ line-height:26px;}
	.collect dt{ float:left; width:90px; padding:0 10px 0 6px; text-align:right;}
	.collect dd{ width:auto;  margin-left:106px; font-size:14px; color: #008caf;}
	
	.hover {
		background: #D0E5F5;
	}
</style>
	</head>
	<body>
		<!--搜索部分 start-->
		<div id="toolbar">
			<form method="post" action="getPersonSalaryByAdmin.do?type=query" id="formID" accept-charset="utf-8">
				
				<span> <label for="user_name">
						员工姓名：
					</label>
					<input id="user_name" name="user_name" style="width: 60px" value="${user_name }" class=" validate[required] text-input field"/> <input
						type="hidden" id="oa" name="oa" value="${oa }" /> </span>
						<span><label for="every_month">
						月份：
						</label>
						<input  type="text" name="every_month" id="every_month" class=" validate[required] text-input field" value="${every_month }" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'})" class="inpstyle validate[required] text-input field"/>
					</span>
				<span><input class="grid-button" type="submit" value="查 询" />
						<!-- 
					&nbsp;&nbsp;<a href="" class="link_red">
						导出excel数据</a> 
						 -->
						</span>
			</form>
			<!--按钮-->
		</div>
			<!--搜索部分 end-->
		<div id="Container">
	<div id="calendar">
	</div>
    <div class="collect">
    	<h2><b style="font: red">${user_name }</b> 本月出勤情况统计</h2>
        <dl>
        	<dt>办公室上班:</dt><dd>${statistic.working_date_count }天</dd>
            <dt>休息日:</dt><dd>${statistic.holidays_count }天 </dd>
            <c:forEach items="${statistic.map}" var="entry">  
            	<dt>${entry.key }:</dt><dd>${entry.value }次</dd>
			</c:forEach>  
           <!--  
            <dt>外出</dt><dd>0</dd>
            <dt>出差</dt><dd>0</dd>
            <dt>调休</dt><dd>0</dd>
            <dt>年假</dt><dd>0</dd>
            <dt>婚假</dt><dd>0</dd>
            <dt>产假</dt><dd>0</dd>
            <dt>丧假</dt><dd>0</dd>
            <dt>旷工</dt><dd>0</dd>
            	<c:if test="${entry.key=='正常'}">
			  		<dt>正常上班天数：</dt><dd>${entry.value} </dd>
            	
            	</c:if>
           <dt>迟到超出次数</dt><dd>${statistic.map}</dd>
            <dt>早退次数</dt><dd>0</dd>
            <dt>忘打卡超出次数</dt><dd>0</dd>
             -->
        </dl>
        <h2>薪资统计</h2>
        <dl>
        	<dt>应出勤天数:</dt><dd>${statistic.working_date_count }</dd>
        	<dt>实出勤天数:</dt><dd>${statistic.working_date_count- statistic.unknown_count}</dd>
        	<dt>待处理天数:</dt><dd>${statistic.unknown_count}</dd>
            <dt>事假天数:</dt><dd>0</dd>
            <dt>病假天数:</dt><dd>0</dd>
            	<c:forEach items="${statistic.map}" var="entry">  
            		<c:if test="${entry.key=='迟到-罚'}">
            <dt>迟到-罚次数:</dt><dd>
			  		${entry.value}
            </dd>
            	
            	</c:if>
			</c:forEach>  
            <!-- 
            <dt>迟到罚款:</dt><dd><a title="50元/每次">${statistic.fine }元</a></dd> -->

            <dt>饭补天数:</dt><dd>${statistic.working_date_count }</dd>
            <dt>加班天数:</dt><dd>0</dd>
        </dl>        
    </div>
		
</div>
			
	</body>
</html>
