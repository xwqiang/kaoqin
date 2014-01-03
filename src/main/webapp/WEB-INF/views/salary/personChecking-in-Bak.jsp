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
		<title>个人日历</title>
		<link type="text/css" rel="stylesheet" href="css/global.css" />
		<link type="text/css" rel="stylesheet" href="css/button.css" />
		<link type="text/css" rel="stylesheet" href="css/fancybox.css" />
		<link type="text/css" rel="stylesheet" href="<%=path%>/css/content.css" />
		<link rel="stylesheet" type="text/css" href="css/default/om-default.css" />
		<link rel='stylesheet' href='cupertino/theme.css' />
		<link rel="stylesheet" type="text/css" href="css/default/om-default.css" />
		  <link rel="stylesheet" href="css/demos.css" />
		  <link href="css/div-common.css" rel="stylesheet" type="text/css" />
			<link href="css/div-main.css" rel="stylesheet" type="text/css" />

		<link href='fullcalendar/fullcalendar.css' rel='stylesheet' />
		<link href='fullcalendar/fullcalendar.print.css' rel='stylesheet' media='print' />
		<link type="text/css" rel="stylesheet" href="css/ui-bill.css" />
		<link rel="stylesheet" href="css/validationEngine.jquery.css" type="text/css" media="screen" title="no title" charset="gbk" />
		<script type="text/javascript" src="jquery/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="jquery/jquery.fancybox-1.3.1.pack.js"></script>
		<script src='fullcalendar/fullcalendar.min.js'></script>
		<script type="text/javascript" src="js/time/WdatePicker.js" defer="defer"></script>	
		<script src='jquery/jquery-ui-1.10.2.custom.min.js'></script>
		<script type="text/javascript" src="js/operamasks-ui.min.js"></script>
		<script src="jquery/jquery.validationEngine-cn.js" type="text/javascript"></script>
		<script src="jquery/jquery.validationEngine.js" type="text/javascript"></script>
		<script src="jquery/json2.js" type="text/javascript"></script>
		 <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
		  <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
  		 <script type="text/javascript" src="js/corl.js"></script>
		<script type="text/javascript">
		var box = new LightBox("idBox");
		
		function closeDiv(){ box.Close(); }
		function openDiv(){
			box.Over = true; //打开覆盖层
			box.OverLay.Color = "#666"; //颜色
			box.OverLay.Opacity = 50; //透明
			box.Fixed = false;
			box.Center = true;
			box.Box.style.left = box.Box.style.top = "50%";
			box.Box.style.marginTop = box.Box.style.marginLeft = "0";
			box.Show(); 
		}
		new Drag("idBox",{ Handle: "idBoxHead" });
    
			$(document).ready(function() {
			//dialog
	$( "#dialog-form" ).dialog({
      autoOpen: false,
      height: 300,
      width: 380,
      modal: true,
      buttons: {
        "确认": function() {
        
        var answer;

        $("input").each(function(){

            (this.checked == true) ? answer = $(this).val() : null;

        });

            var params = {
                  number: answer,
            	  reason: $("#reason").val(),
            	  reason2: $("#reason2").val(),
            	  forget: $("#forget").val(),
                  click_day: $( "#click_day" ).val(),
				  oa:'${oa}'
              	};
              	
				$.ajax({
				
                  url:"/attendance/getPersonChecking-in	ByAdmin.do",
                  type:"post",
                  data:params,
                  dataType: 'json',
                     success : function(items) {
			                //alert(items);
			                //因为无法定位覆盖备注 通过刷新起到更新作用
    				document.getElementById('formID').submit();
			        },
			        beforeSend : function() {
			                $("#formulamsg").html("查询中...");
			        },
			        error : function(XMLHttpRequest, textStatus, errorThrown) {
			                alert("内部错误，请重新操作！");
			                alert(errorThrown);
			        }
              });
              
             $( this ).dialog( "close" );
        },
        "取消": function() {
          $( this ).dialog( "close" );
        }
      },
      close: function() {
      }
    });
			
	$( "#dialog-login-form" ).dialog({
	      autoOpen: false,
	      height: 300,
	      width: 380,
	      modal: true,
	      buttons: {
	        "登录": function() {
	        
	        var answer;

	        $("input").each(function(){

	            (this.checked == true) ? answer = $(this).val() : null;

	        });

	            var params = {
	                  number: answer,
	            	  referrer: $("#referrer").val(),
	            	  emp_id: $("#emp_id").val(),
	            	  pwd: $("#login_pwd").val(),
	            	  check_code: $( "#check_code" ).val(),
					  oa:'${oa}'
	              	};
	              	
					$.ajax({
					
	                  url:"/attendance/ssoLogin.do",
	                  type:"post",
	                  data:params,
	                  dataType: 'json',
	                     success : function(items) {
				                //alert(items);
				                //因为无法定位覆盖备注 通过刷新起到更新作用
	    				document.getElementById('formID').submit();
				        },
				        beforeSend : function() {
				                $("#formulamsg").html("查询中...");
				        },
				        error : function(XMLHttpRequest, textStatus, errorThrown) {
				                alert("内部错误，请重新操作！");
				                alert(errorThrown);
				        }
	              });
	              
	             $( this ).dialog( "close" );
	        },
	      },
	      close: function() {
	      }
	    });
			
			
		 $("#formID").validationEngine();
		
		var calendar = $('#calendar').fullCalendar({
		
			 height: 640,
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
			selectable: false,
			selectHelper: false,
			//theme:true,
			editable: false,
			events: [
				
				<c:forEach var="each" items="${calendars}" varStatus="status">
					{
						
						title: '${each.type_desc}',
						start: '${each.start_date}',
						end: '${each.end_date}',
						<c:if test="${each.type=='-3'}">
						  textColor: '#7fb80e',
						  backgroundColor: '#feeeed',
						  borderColor:'#feeeed',
						</c:if>
						<c:if test="${each.type=='-2'}">
						  textColor: '#7fb80e',
						  backgroundColor: '#feeeed',
						  borderColor:'#feeeed',
						</c:if>
						<c:if test="${each.type=='-1'}">
						  textColor: '#7fb80e',
						  backgroundColor: '#feeeed',
						  borderColor:'#feeeed',
						</c:if>
						<c:if test="${each.type=='0'}">
						  textColor: '#7fb80e',
						  backgroundColor: '#feeeed',
						  borderColor:'#feeeed',
						</c:if>
						<c:if test="${each.type=='1'}">
						  textColor: '#f36c21',
						  backgroundColor: '#fedcbd',
						  borderColor:'#fedcbd',
						</c:if>
						<c:if test="${each.type=='2'}">
						  textColor: 'blue',
						   backgroundColor: '#fedcbd',
						   borderColor:'#fedcbd',
						</c:if>
						<c:if test="${each.type=='3'}">
						  textColor: '#f173ac',
						   backgroundColor: '#fedcbd',
						   borderColor:'#fedcbd',
						</c:if>
						<c:if test="${each.type=='4'}">
						    textColor: 'blue',
						   backgroundColor: '#fedcbd',
						   borderColor:'#fedcbd',
						</c:if>
						url: ''
					},
   	 			</c:forEach>
				<c:forEach var="each" items="${list}" varStatus="status">
					{
					
						 
						<c:if test="${each.comment_type!='0'}">
							
							<c:if test="${each.comment!=''}">
								<c:if test="${each.status=='0'}">
								<c:if test="${each.comment_type=='1'}">
								title: '${each.comment}',
								</c:if>
								<c:if test="${each.comment_type!='1'}">
								title: '${each.comment}      ×',
								</c:if>
								start: '${each.every_date}',
									<c:if test="${each.comment_type=='2'}">
									textColor: '#ffffe0',
								 	backgroundColor: 'red',
								 	borderColor: 'red',
								 	</c:if>
								 	<c:if test="${each.comment_type!='2'}">
									textColor: '#464547',
									 backgroundColor: '#ffd400',
									 borderColor:'#ffd400',
									 </c:if>
								</c:if>
							
								<c:if test="${each.status=='1'}">
								<c:if test="${each.comment!='正常'}">
								title: '${each.comment}     √ ',
								start: '${each.every_date}',
									<c:if test="${each.comment_type=='2'}">
									textColor: '#ffffe0',
								 	backgroundColor: 'red',
								 	borderColor: 'red',
								 	</c:if>
								 	<c:if test="${each.comment!='2'}">
									textColor: '#464547',
									 backgroundColor: '#ffd400',
									 borderColor:'#ffd400',
									 </c:if>
									</c:if> 
								</c:if>
								
							</c:if>
						</c:if>
					},
   	 			</c:forEach>
   	 			
				<c:forEach var="each" items="${list}" varStatus="status">
					{
						<c:if test="${not empty each.show_checkin_time}">
						
						<c:if test="${each.show_checkin_time==each.show_checkout_time}">
							title: '${each.show_checkin_time}',
							start: '${each.every_date}',
							textColor: '#fffffb',
							backgroundColor: '#33a3dc',
							borderColor:'#33a3dc',
						</c:if>	
						
						
						  <c:if test="${each.show_checkin_time!=each.show_checkout_time}">
							title: '${each.show_checkin_time} - ${each.show_checkout_time}',
							start: '${each.every_date}',
							textColor: '#fffffb',
							 backgroundColor: '#33a3dc',
							 borderColor:'#33a3dc',
							</c:if>	
						</c:if>	
					},
					
   	 			</c:forEach>
   	 			
				<c:forEach var="each" items="${leaves}" varStatus="status">
					{
						title: '未打卡，待处理      ×',
						start: '${each.every_date}',
						textColor: '#ffffe0',
						 backgroundColor: 'red',
						 borderColor: 'red',
					},
   	 			</c:forEach>

				{
					//title: '',
					//start: new Date(y, m, 30),
					//end: new Date(y, m, 31),
					//url: 'http://google.com/'
				}
			]
			
			 //eventColor: '#f47920'
			// eventColor: '#45b97c',
			// textColor: '#ef5b9c',
			 //backgroundColor:'#45b97c'

		});
		
		
		$('#calendar').fullCalendar( 'gotoDate',${year},${month});
		
		$('.fc-button-today').click(function() {
    		$("#type").attr("value" , 'today');
    		document.getElementById('formID').submit();
		});
		
		$('.fc-button-prev').click(function() {
    		//$('#calendar').fullCalendar('prev');
    		$("#type").attr("value" , 'prev');
    		document.getElementById('formID').submit();
		});
		
		$('.fc-button-next').click(function() {
		
			$("#type").attr("value" , 'next');
    		//$('#calendar').fullCalendar('next');
    		document.getElementById('formID').submit();
    		
		});
			
			$('#user_name').omCombo({
	                dataSource :'getAdminUser.do',
	                onValueChange:function(target,newValue,oldValue,event){ 
	 				$('#oa').val(newValue);
	 			  }
	            });
	        $("#showdiv").fancybox({'centerOnScroll':true});

			});
		</script>
		<style>
	body {
		text-align: center;
		font-size: 13px;
		font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
		background:#eee;
		}
	#Container{ width:1100px; margin:0 auto; text-align:left; overflow:hidden;}
	
	#calendar {
		width: 900px;
		margin: 0 auto;
		float:left;
		background:#fff;
		border:1px solid #eee;
		padding: 7px;
		}
	.fc-sat .fc-day-number, .fc-sun .fc-day-number{ color:#F60;}
	
	.collect{ width:auto; margin:0 0 0 930px; padding-bottom:10px;
		background:#FFF; font: 12px/1.7 "Microsoft YaHei",Arial,sans-serif;
		border:1px solid #CCC;}
	.collect h2{ margin:0; padding-left:20px; border:1px solid #A8CEE1; border-left:none; border-right:none;
		background:#C2EBFF; font: normal 14px/30px "微软雅黑";}
	.collect dl{ overflow:hidden; color:#444; margin:10px 0;}
	.collect dt, .collect dd{ line-height:26px;}
	.collect dt{ float:left; width:80px; padding:0 10px 0 6px; text-align:right;}
	.collect dd{ width:auto;  margin-left:80px; font-size:14px; color: #008caf;}
	
	.hover {
		background: #D0E5F5;
	}
	
	/*
    label, input { display:block; }
    input.text { margin-bottom:12px; width:95%; padding: .4em; }
   */
    fieldset { padding:0; border:0; margin-top:25px; }
    h1 { font-size: 1.2em; margin: .6em 0; }
    div#users-contain { width: 350px; margin: 20px 0; }
    div#users-contain table { margin: 1em 0; border-collapse: collapse; width: 100%; }
    div#users-contain table td, div#users-contain table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
    .ui-dialog .ui-state-error { padding: .3em; }
</style>
	</head>
	<body>
		<div id="header">
			<div class="header">
			
			<c:if test="${adminUser!=null}">
			
		    	你好,${adminUser.admin_name }&nbsp;&nbsp;<a href="logout.do">退出</a>
			
			</c:if>
			<c:if test="${empty adminUser}">
			
		    	<a href="login.do">登录</a>
			
			</c:if>
		   	 </div>
		</div>
		<div id="nav">
			<ul>
				<li><a href="getPersonChecking-in.do" class="current">个人考勤</a></li>
				<li><a href="showAllAttendance.do">&nbsp;统计&nbsp;</a></li>
			</ul>
		</div>
		<!--搜索部分 start-->
		<div id="toolbar">
			<form method="post" action="getPersonChecking-in.do" id="formID" accept-charset="utf-8">
				
				<span> <label for="user_name">
						员工姓名：
					</label>
					<input id="user_name" name="user_name" style="width: 60px" value="${user_name }" class=" validate[required,ajax[ajaxAdminUser]] text-input field"/> 
					<input	type="hidden" id="oa" name="oa" value="${oa }" /> 
					<input	type="hidden" id="type" name="type" value="query" /> 
					</span>
						<span><label for="every_month">
						月份：
						</label>
						<input  type="text" name="every_month" id="every_month" class=" validate[required] text-input field" value="${every_month }" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM',minDate:'2013-09',maxDate:'%y-%M'})" class="inpstyle validate[required] text-input field"/>
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
    	<h2></h2>	
    	  <div class="demo">
		     <p><a id="showdiv" href="#inline" title="系统计算规则">规则说明？</a></p>
		  </div>
    	<h2><b style="font: red">${user_name }</b> 出勤情况统计</h2>
        <dl>
        	<dt>应出勤天数:</dt><dd>${statistic.working_date_count }天</dd>
        	<dt>实出勤天数:</dt><dd>${statistic.real_working_date_count}天</dd>
        	<dt>办公室上班:</dt><dd>${statistic.office_date_count }天</dd>
            <c:forEach items="${statistic.map}" var="entry">  
            	<dt>${entry.key }:</dt><dd>${entry.value }天</dd>
			</c:forEach>  
           <!--  
            <dt>休息日:</dt><dd>${statistic.holidays_count }天 </dd>
            	<c:if test="${entry.key=='正常'}">
			  		<dt>正常上班天数：</dt><dd>${entry.value} </dd>
            	
            	</c:if>
           <dt>迟到超出次数</dt><dd>${statistic.map}</dd>
            <dt>早退次数</dt><dd>0</dd>
            <dt>忘打卡超出次数</dt><dd>0</dd>
            <dt>外出</dt><dd>0</dd>
            <dt>出差</dt><dd>0</dd>
            <dt>调休</dt><dd>0</dd>
            <dt>年假</dt><dd>0</dd>
            <dt>婚假</dt><dd>0</dd>
            <dt>产假</dt><dd>0</dd>
            <dt>丧假</dt><dd>0</dd>
            <dt>旷工</dt><dd>0</dd>
            	<c:forEach items="${statistic.map}" var="entry">  
            		<c:if test="${entry.key=='迟到-罚'}">
            <dt>迟到-罚次数:</dt><dd>
			  		${entry.value}
            </dd>
             -->
            	
            	</c:if>
			</c:forEach>  
            <!-- 
            <dt>迟到罚款:</dt><dd><a title="50元/每次">${statistic.fine }元</a></dd> -->
			<c:if test="${statistic.late_absolve!=0}">
			 <dt>迟到-免:</dt><dd>${statistic.late_absolve}次</dd>
			</c:if>
			
			<c:if test="${statistic.late_fine!=0}">
			
            <dt>迟到-罚:</dt><dd>${statistic.late_fine}次</dd>
			</c:if>
			<c:if test="${statistic.forget_checkin!=0}">
			
            <dt>忘签到:</dt><dd>${statistic.forget_checkin}次</dd>
			</c:if>
			<c:if test="${statistic.forget_checkout!=0}">
            <dt>忘签退:</dt><dd>${statistic.forget_checkout}次</dd>
			
			</c:if>
			<c:if test="${statistic.forget_fine!=0}">
            <dt>忘打卡-罚:</dt><dd>${statistic.forget_fine}次</dd>
			
			</c:if>
            <c:if test="${statistic.overtime_count!=0}">
            <dt>休息日加班:</dt><dd>${statistic.overtime_count}天</dd>
            
            </c:if>
            <c:if test="${statistic.holiday_overtime_count !=0}">
            <dt>节日加班:</dt><dd>${statistic.holiday_overtime_count }天</dd>
            
            </c:if>
            <dt>饭补天数:</dt><dd>${statistic.fanbu_date_count }天</dd>
        </dl>   
           
          <div class="c-operate" id="c-operate">
			
		  	 <p><span class="c-button c-button-main" onclick="$('#dialog-login-form' ).dialog( 'open' );">确认考勤</span></p>
		  	 <p><span class="c-button c-button-main" onclick="onclick="openDiv()">确认考勤</span></p>
		</div>
			
				
    </div>
		
</div>

	<div style="display:none">
       <div id="inline" style="width:430px; height:350px; overflow:auto;text-align: left">
			<h3>基于员工工作时间与打卡记录,系统计算规则如下：</h3>
			<br/>
		<ol>
		  <li><b> &nbsp; &nbsp;×：</b>人资未确认</li>
		  <li><b> &nbsp; &nbsp;√：</b>人资已确认</li>
		  <li><b> &nbsp; &nbsp;班：</b>上班时间：09:00--18:00</li>
		  <li><b> &nbsp; &nbsp;值：</b>上班时间：10:00--19:00</li>
		  <li><b> &nbsp; &nbsp;技术-早班：</b>上班时间：08:00--17:00</li>
		  <li><b> &nbsp; &nbsp;技术-晚班：</b>上班时间：12:00--21:00</li>
		  <li><b> &nbsp; &nbsp;休：</b>休息日</li>
		  <li><b> &nbsp; &nbsp;节假日：</b>国家法定节假日</li>
		  <li><b> &nbsp; &nbsp;节假日加班：</b>国家法定节假日加班</li>
		  <li><b> &nbsp; &nbsp;休息日加班：</b>休息日加班</li>
		  <li><b> &nbsp; &nbsp;平日加班：</b>2小时为最小计时单位，不超过3小时</li>
		  <li><b> &nbsp; &nbsp;时间显示：</b>取最早打卡与最晚打卡时间</li>
		  <li><b> &nbsp; &nbsp;迟到-免：</b>迟到20（含）分钟内，3次免罚</li>
		  <li><b> &nbsp; &nbsp;迟到-罚：</b>迟到60（含）分钟内，罚</li>
		  <li><b> &nbsp; &nbsp;旷工半天：</b>迟到超出1小时，按旷工半天处理</li>
		  <li><b> &nbsp; &nbsp;旷工一天：</b>迟到超出3小时，按旷工一天处理</li>
		  <li><b> &nbsp; &nbsp;早退：</b>最晚打卡时间早于下班时间</li>
		  <li><b> &nbsp; &nbsp;（忘签到/忘签退）-罚：</b>忘签到/忘签退超出3次，罚</li>
		 <li><b> &nbsp; &nbsp;应出勤天数=</b>月工作日天数</li>
		 <li><b> &nbsp; &nbsp;实出勤天数=</b>办公室上班+外出+出差+年假+调休假+婚假+产假+丧假-事假-病假</li>
		  <li><b> &nbsp; &nbsp;饭补天数=</b>办公室上班+外出+调休</li>
		  <li><b> &nbsp; &nbsp;待处理：</b>
		  	<ol>
				  <li>
				 	 &nbsp; &nbsp; &nbsp;&nbsp;只有一条打卡记录
				  </li>
				  <li>
				  	 &nbsp; &nbsp;&nbsp;&nbsp;无打卡记录
				  </li>
			  </ol>
		  </li>
		</ol>
		<br/>
		<h3>备注：系统考勤数据实时同步！</h3>
     </div>
     <div id="inline2" style="width:400px; height:120px">
        <p style="text-align:center"><a href="javascript:;" onclick="$.fancybox.close();">关闭</a></p>
     </div>
</div>	



	<div id="dialog-form" title="人资备注">
  <form method="post" action="getPersonChecking-inByAdmin.do" id="dialogFormID" accept-charset="utf-8">
  <fieldset>
  <label for="reason">上午：</label>
    <select id="reason" name="reason">
       <option value="无">无</option>
       <option value="事假">事假</option>
		<option value="病假">病假</option>
		<option value="外出">外出</option>
		<option value="出差">出差</option>
		<option value="调休">调休</option>
		<option value="年假">年假</option>
		<option value="婚假">婚假</option>
		<option value="产假">产假</option>
		<option value="丧假">丧假</option>
		<option value="旷工">旷工</option>
    <br/>
    </select>&nbsp;&nbsp;
  <label for="reason2">下午：</label>
    <select id="reason2" name="reason2">
       <option value="无">无</option>
       <option value="事假">事假</option>
		<option value="病假">病假</option>
		<option value="外出">外出</option>
		<option value="出差">出差</option>
		<option value="调休">调休</option>
		<option value="年假">年假</option>
		<option value="婚假">婚假</option>
		<option value="产假">产假</option>
		<option value="丧假">丧假</option>
		<option value="旷工">旷工</option>
    </select>&nbsp;&nbsp;
    
   <label for="yes">0.5天</label>
    <input type="radio" id="yes" value="0.5" name="number"/>
	 &nbsp;&nbsp;
    <label for="no">1天</label><input type="radio" id="no" value="1" name="number"  checked="checked"/>
	<input type="hidden" id="click_day" name="click_day"/>
	
	<br/>
	<br/>
	<br/>
	<!-- 
    <label for="zao">忘签到</label>
    <input type="radio" id="zao" value="0" name="forget"/>
	 &nbsp;&nbsp;
    <label for="wan">往签退</label><input type="radio" id="wan" value="1" name="forget" />
     -->
     <label for="forget">忘打卡：</label>
    <select id="forget" name="forget">
       <option value="无">无</option>
       <option value="忘签到">忘签到</option>
		<option value="忘签退">忘签退</option>
		<option value="迟到-免">迟到-免</option>
		<option value="迟到-罚">迟到-罚</option>
		
    </select>
  </fieldset>
  </form>
</div>

<div id="idBox" class="lightbox" style="diplay:block;">
  <div class="title" id="idBoxHead"><h4>我就点死你</h4><span><a href="javascript:void(0)" onclick="closeDiv()">关闭</a></span></div>
  <div class="content">
    <form method="post" action="login.do" id="dialogFormID" accept-charset="utf-8">
	 <div class="border-radius"><label class="l-user">OA账号:</label><input type="text" name="admin_id" id="admin_id"/></div>
	  <div class="border-radius">
	    <label class="l-psd">密&nbsp;&nbsp;码:</label><input type="password" name="admin_pwd" id="admin_pwd"  /></div>
	  <div class="border-radius border-code"><label>验证码:</label><input type="text" class="l-ipt-code" name="check_code" id="check_code"/></div>
      <div class="code"><img onclick="change(this);" title="点击刷新验证码！" src="chineseVal.jsp"/></div>
      <div class="clear"></div>
      <div class="login-cb" title="为了信息安全,请不要在网吧或者公用电脑上使用此功能"><input name="cookieLogin" type="checkbox" value="cookieLogin" class="input-check" />30天内免登录</div>
    	<div class="l-btn"><input type="submit" class="ipt-submit" value=""/>
        	<input type="reset" class="ipt-reset" value=""/></div>
  </form>
  </div>
</div>
<div id="dialog-login-form" title="登录">
  <form method="post" action="login.do" id="dialogFormID" accept-charset="utf-8">
	 <div class="border-radius"><label class="l-user">OA账号:</label><input type="text" name="admin_id" id="admin_id"/></div>
     <!-- 
      <div class="login-txt"><p><a href="findPwd.do">忘记密码了?</a></p></div>
	   -->
	  <div class="border-radius">
	    <label class="l-psd">密&nbsp;&nbsp;码:</label><input type="password" name="admin_pwd" id="admin_pwd"  /></div>
	  <div class="border-radius border-code"><label>验证码:</label><input type="text" class="l-ipt-code" name="check_code" id="check_code"/></div>
      <div class="code"><img onclick="change(this);" title="点击刷新验证码！" src="chineseVal.jsp"/></div>
      <div class="clear"></div>
      <div class="login-cb" title="为了信息安全,请不要在网吧或者公用电脑上使用此功能"><input name="cookieLogin" type="checkbox" value="cookieLogin" class="input-check" />30天内免登录</div>
    	<div class="l-btn">
    	<input type="submit" class="ipt-submit" value=""/>
        	<input type="reset" class="ipt-reset" value=""/>
        	</div>
  </form>
</div>
	</body>
</html>