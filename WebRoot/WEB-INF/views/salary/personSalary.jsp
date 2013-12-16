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
		<link rel="stylesheet" href="css/mainstructure.css" /> 
		<link rel="stylesheet" href="css/maincontent.css" /> 
		<link rel="stylesheet" href="css/redmond/jquery-ui-1.8.1.custom.css" /> 
		<link rel='stylesheet' type='text/css' href='js/fullcal/css/fullcalendar.css' />
		<link rel="stylesheet" href="css/validationEngine.jquery.css" type="text/css" media="screen" title="no title" charset="gbk" />

		<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="js/jquery-ui-1.8.6.custom.min.js"></script>
		<script type="text/javascript" src="js/jquery-ui-timepicker-addon.js"></script>
		<script src="js/jquery/jquery.validationEngine-cn.js" type="text/javascript"></script>
		<script src="js/jquery/jquery.validationEngine.js" type="text/javascript"></script>
		<script type='text/javascript' src='js/fullcal/fullcalendar.js'></script>

		<script type="text/javascript">
		
						$(document).ready(function() {
				$("#reserveformID").validationEngine({
					validationEventTriggers:"keyup blur", 
					openDebug: true
				}) ;
			
				$("#start").timepicker({dateFormat:'yy-mm-dd', timeFormat:'hh:mm', hourMin: 5, hourMax: 24, hourGrid: 3, minuteGrid: 15}); 
				$("#end").timepicker({dateFormat:'yy-mm-dd', timeFormat:'hh:mm', hourMin: 5, hourMax: 24, hourGrid: 3, minuteGrid: 15}); 
			

				$("#addhelper").hide();		
				
				$('#calendar').fullCalendar({
				  header:{
						right: 'prev,next today',
						center: 'title',
						left: 'month,agendaWeek,agendaDay'
				  },
				  theme: true,
				  editable: true,
				  allDaySlot : false,
				  events:  function(start, end , callback){
				  
					var events = [];
					var now = new Date();
					for(var i=-10;i<60;i++){
						var evtstart = new Date(now.getFullYear() , now.getMonth() , (now.getDate()-i), now.getHours()-5, now.getMinutes(), now.getSeconds(), now.getMilliseconds());

						var evtend = new Date(now.getFullYear() , now.getMonth() , (now.getDate()-i), now.getHours(), now.getMinutes(), now.getSeconds(), now.getMilliseconds());							
					
						events.push({
							sid: 1,
							uid: 1,
							title: 'Daily Scrum meeting',
							start: evtstart,
							end: evtend,
							fullname: 'terry li',
							confname: 'Meeting 1',
							confshortname: 'M1',
							confcolor: '#ff3f3f',
							confid: 'test1',
							allDay: false,
							topic: 'Daily Scrum meeting',
							description : 'Daily Scrum meeting',
							id: 1,
						});				
					}						
					
					callback(events);
				  },
				  dayClick: function(date, allDay, jsEvent, view) {
							var selectdate = $.fullCalendar.formatDate(date, "yyyy-MM-dd");	
							
							$( "#reservebox" ).dialog({
								autoOpen: false,
								height: 450,
								width: 400,
								title: 'Reserve meeting room on ' + selectdate,
								modal: true,
								position: "center",
								draggable: false,
								beforeClose: function(event, ui) {
										$.validationEngine.closePrompt("#meeting");
										$.validationEngine.closePrompt("#start");
										$.validationEngine.closePrompt("#end");								
								},
								buttons: {
									"close": function() {
										$( this ).dialog( "close" );
									},
									"reserve": function() {				
										if($("#reserveformID").validationEngine({returnIsValid:true})){
											var startdatestr = $("#start").val();
											var enddatestr = $("#end").val();		
											var confid = $("#meeting").val();	
											var repweeks = $("#repweeks").val();	
											if(repweeks==null){
												repweeks=0;
											}
											var startdate =  $.fullCalendar.parseDate(selectdate+"T"+startdatestr); 
											var enddate =  $.fullCalendar.parseDate(selectdate+"T"+enddatestr);
											var schdata = {startdate:startdate, enddate:enddate, confid:confid, repweeks:repweeks};									
										}	
									}
								}
							});
							$( "#reservebox" ).dialog( "open" );
						return false;
				  },
				  timeFormat: 'HH:mm{ - HH:mm}',
				  eventClick: function(event) {
						var fstart  = $.fullCalendar.formatDate(event.start, "yyyy/MM/dd HH:mm");
						var fend  = $.fullCalendar.formatDate(event.end, "HH:mm");				  
						var schdata = {sid:event.sid, deleted:1, uid:event.uid};
						
						$( "#reserveinfo" ).dialog({
							autoOpen: false,
							height: 280,
							width: 400,
							modal: true,
							position: "center",
							draggable: false,
							buttons: {
								"close": function() {
									$( this ).dialog( "close" );
								}
							}
						});
														
						if(1==1||2==schdata.uid){
							$("#reserveinfo").dialog("option", "buttons", {
								"Close": function() {
									$( this ).dialog( "close" );
								},
								
								"Cancel the reservation": function() {
									var answer = confirm("Are you sure to cancel the reservation?");
									if(answer){
										identityService.updateScheduleDeleted(schdata, {
												callback:function(data) {
													alert("The reservation was canceled.");
													window.location.reload();
												}		
										});
									}									
								}
							});
						}
						
						var showtopic = '';
						
						if(event.topic.length>15){
							showtopic = event.topic.substring(0, 15) + '...';
						}else{
							showtopic = event.topic;
						}
						
						
						$("#revdesc").html('<div style="font-weight:bold;color:#5383c2;border-bottom: 1px dotted #5383c2; padding: 3px 0px 3px;">'  + showtopic   + "</div>" + '<div style="padding: 10px 0px 3px">'  + '<div style="width:128px;float:left;"><a href="/mrr/images/conf/' + event.confid +  '.jpg">' + event.confname +  '</a><div style="background:#A4C3E3; text-align:center; padding:5px;color:#1d5987;font-weight:bold;font-size:9px"><span style="background:'+ event.confcolor +';width:14px;height:14px;color:#E3E3E3;font-size:10px;position:relative;left:0;top:0;">' + event.confshortname + '</span>&nbsp;'+ event.confname + ' by ' + event.fullname +'</div></div><div style="float:right;width:220px; padding:0px ; margin:0px">' + event.description+ '</div><div style="clear:both"></div></div>');
						
						$( "#reserveinfo" ).dialog( 
							{ title:  fstart + "-" + fend + " " + showtopic }
						);
						
						$( "#reserveinfo" ).dialog( "open" );
						return false;
				  },
				  loading: function(bool) {
						if (bool) $('#loading').show();
						else $('#loading').hide();
				  },
				  eventMouseover: function(calEvent, jsEvent, view) {
						var fstart  = $.fullCalendar.formatDate(calEvent.start, "yyyy/MM/dd HH:mm");
						var fend  = $.fullCalendar.formatDate(calEvent.end, "HH:mm");	
						$(this).attr('title', fstart + " - " + fend + " " + calEvent.topic + " : " + calEvent.description);
						$(this).css('font-weight', 'normal');				
						$(this).tooltip({
							effect:'toggle',
							cancelDefault: true
						});
				  },
				  eventMouseout: function(calEvent, jsEvent, view) {
						$(this).css('font-weight', 'normal');
				  },
				  eventRender: function(event, element) {
				  	var fstart  = $.fullCalendar.formatDate(event.start, "HH:mm");
					var fend  = $.fullCalendar.formatDate(event.end, "HH:mm");	
					// Bug in IE8
					//element.html('<a href=#>' + fstart + "-" +  fend + '<div style=color:#E5E5E5>' +  event.title + "</div></a>");
				  },
				  eventAfterRender : function(event, element, view) {
				  	var fstart  = $.fullCalendar.formatDate(event.start, "HH:mm");
					var fend  = $.fullCalendar.formatDate(event.end, "HH:mm");		
					//element.html('<a href=#><div>Time: ' + fstart + "-" +  fend + '</div><div>Room:' + event.confname + '</div><div style=color:#E5E5E5>Host:' +  event.fullname + "</div></a>");
					
					
					var confbg='';
					if(event.confid==1){
						confbg = confbg + '<span class="fc-event-bg"></span>';
					}else if(event.confid==2){
						confbg = confbg + '<span class="fc-event-bg"></span>';
					}else if(event.confid==3){
						confbg = confbg + '<span class="fc-event-bg"></span>';
					}else if(event.confid==4){
						confbg = confbg + '<span class="fc-event-bg"></span>';
					}else if(event.confid==5){
						confbg = confbg + '<span class="fc-event-bg"></span>';
					}else if(event.confid==6){
						confbg = confbg + '<span class="fc-event-bg"></span>';
					}else{
						confbg = confbg + '<span class="fc-event-bg"></span>';
					}
					
					var titlebg =  '<span class="fc-event-conf" style="background:'+  event.confcolor +'">' + event.confshortname + '</span>';
					
					if(event.repweeks>0){
						titlebg = titlebg + '<span class="fc-event-conf" style="background:#fff;top:0;right:15;color:#3974BC;font-weight:bold">R</span>';
					}
					
					if(view.name=="month"){
						var evtcontent = '<div class="fc-event-vert"><a>';
						evtcontent = evtcontent + confbg;
						evtcontent = evtcontent + '<span class="fc-event-titlebg">' +  fstart + " - " +  fend + titlebg + '</span>';
						evtcontent = evtcontent + '<span>Room: ' +  event.confname + '</span>';
						evtcontent = evtcontent + '<span>Host: ' +  event.fullname + '</span>';
						evtcontent = evtcontent + '</a><div class="ui-resizable-handle ui-resizable-e"></div></div>';
						element.html(evtcontent);
					}else if(view.name=="agendaWeek"){
						var evtcontent = '<a>';
						evtcontent = evtcontent + confbg;
						evtcontent = evtcontent + '<span class="fc-event-time">' +  fstart + "-" +  fend + titlebg + '</span>';
						evtcontent = evtcontent + '<span>' +  event.confname + ' by ' + event.fullname + '</span>';
						//evtcontent = evtcontent + '<span>' +  event.fullname + '</span>';
						evtcontent = evtcontent + '</a><span class="ui-icon ui-icon-arrowthick-2-n-s"><div class="ui-resizable-handle ui-resizable-s"></div></span>';
						element.html(evtcontent);						
					}else if(view.name=="agendaDay"){
						var evtcontent = '<a>';
						evtcontent = evtcontent + confbg;
						evtcontent = evtcontent + '<span class="fc-event-time">' +  fstart + " - " +  fend + titlebg + '</span>';
						evtcontent = evtcontent + '<span>Room: ' +  event.confname + '</span>';
						evtcontent = evtcontent + '<span>Host: ' +  event.fullname + '</span>';
						evtcontent = evtcontent + '<span>Topic: ' +  event.topic + '</span>';
						evtcontent = evtcontent + '</a><span class="ui-icon ui-icon-arrow-2-n-s"><div class="ui-resizable-handle ui-resizable-s"></div></span>';
						element.html(evtcontent);								
					}
				  },
				  eventDragStart: function( event, jsEvent, ui, view ) {
					ui.helper.draggable("option", "revert", true);
				  },
				  eventDragStop: function( event, jsEvent, ui, view ) {
				  },
				  eventDrop: function( event, dayDelta, minuteDelta, allDay, revertFunc, jsEvent, ui, view ) { 

					if(1==1||2==event.uid){
						var schdata = {startdate:event.start, enddate:event.end, confid:event.confid, sid:event.sid};
					}else{
						revertFunc();
					}
					
				  },
				  eventResizeStart:  function( event, jsEvent, ui, view ) {
				  
					//alert('resizing');
				  
				  },
				  eventResize: function(event,dayDelta,minuteDelta,revertFunc) {

					if(1==1||2==event.uid){
						var schdata = {startdate:event.start, enddate:event.end, confid:event.confid, sid:event.sid};

					}else{
						revertFunc();
					}

				  }

				});
				
				//goto date function
				if($.browser.msie){
					$("#calendar .fc-header-right table td:eq(0)").before('<td><div class="ui-state-default ui-corner-left ui-corner-right" style="border-right:0px;padding:1px 3px 2px;" ><input type="text" id="selecteddate" size="10" style="padding:0px;"></div></td><td><div class="ui-state-default ui-corner-left ui-corner-right"><a><span id="selectdate" class="ui-icon ui-icon-search">goto</span></a></div></td><td><span class="fc-header-space"></span></td>');
				}else{
					$("#calendar .fc-header-right table td:eq(0)").before('<td><div class="ui-state-default ui-corner-left ui-corner-right" style="border-right:0px;padding:3px 2px 4px;" ><input type="text" id="selecteddate" size="10" style="padding:0px;"></div></td><td><div class="ui-state-default ui-corner-left ui-corner-right"><a><span id="selectdate" class="ui-icon ui-icon-search">goto</span></a></div></td><td><span class="fc-header-space"></span></td>');
				}
				
				$("#selecteddate").datepicker({
					dateFormat:'yy-mm-dd',
					beforeShow: function (input, instant) {  
						setTimeout(
							function () {
								$('#ui-datepicker-div').css("z-index", 15);
							}, 100
						);
					}
				});
								

				
				$("#selectdate").click(function() {
					var selectdstr = 	$("#selecteddate").val();	
					var selectdate = $.fullCalendar.parseDate(selectdstr, "yyyy-mm-dd");					
					$('#calendar').fullCalendar( 'gotoDate', selectdate.getFullYear(), selectdate.getMonth(), selectdate.getDate());
				});
				
				
				// conference function
				$("#calendar .fc-header-left table td:eq(0)").before('<td><div class="ui-state-default ui-corner-left ui-corner-right" id="selectmeeting"><a><span id="selectdate" class="ui-icon ui-icon-search" style="float: left;padding-left: 5px; padding-top:1px"></span>meeting room</a></div></td><td><span class="fc-header-space"></span></td>');
				

				
				
				
			});

		
			
			function validate2time(){ 
				//alert("debug");
				
				var cresult = compare2time($("#start").val(), $("#end").val());
				if(cresult==0){
					return false; 
				}else if(cresult==1){ 
					$.validationEngine.closePrompt("#start");
					return true; 
				} 

			}
		
			var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-19118450-1']);
  _gaq.push(['_trackPageview']);
  (function() {
	var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
	ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
	var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();
  
  var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3Fd999331ad5ea0c0930f3aa7c3bda9fc1' type='text/javascript'%3E%3C/script%3E"));		

		</script>
		<style>
#loading {
	TOP: 0px; RIGHT: 0px
}
.tooltip {
	PADDING-BOTTOM: 25px; PADDING-LEFT: 25px; WIDTH: 160px; PADDING-RIGHT: 25px; DISPLAY: none; BACKGROUND: url(images/black_arrow.png); HEIGHT: 70px; COLOR: #fff; FONT-SIZE: 12px; PADDING-TOP: 25px; z-order: 100
}
</style>
	</head>
	<body>
		
		<div id="wrap">
			
			
			<div id="calendar">
			</div>
			
		<div id="reserveinfo" title="Details">
			<div id="revtitle">
			</div>
			<div id="revdesc">
			</div>
		</div>
		 
		<div style="DISPLAY: none" id="reservebox" title="Reserve meeting room">
			
			<form id="reserveformID" method="post">
			
			<div class="sysdesc">&nbsp;
			</div>
			
		<div class="rowElem">
			<label>meeting room:</label> 
			<select id="meeting" class="validate[required]" name="meeting"></select> 
		</div>
		
		<div class="rowElem">
			<label>Repeated weeks:</label>
		 	<select id="repweeks" name="repweeks">
		 	 <option selected value="0">Not repeated</option> <option 
			  value="2">1 week</option> <option value="3">2 weeks</option> <option value="4">3 
			  weeks</option> <option value="5">4 weeks</option> <option value="9">8 
			  weeks</option> <option value="17">16 weeks</option> <option value="33">32 
			  weeks</option>
			 </select> 
		  </div>
		  
		<div class="rowElem">
			<label>start time:</label> 
			<input id="start" class="validate[required,funcCall[validate2time]]" name="start" />
		 </div>
		 
		<div class="rowElem">
			<label>end time:</label>
		 	<input id="end" class="validate[required,funcCall[validate2time]]" name="end" /> 
		 </div>
		 
		<div class="rowElem">
			<label>Title:</label> 
			<input id="title" name="title" /> 
		</div>
		
		<div class="rowElem">
			<label>Details:</label> 
			<texarea id="details" rows="3" cols="43" name="details"></texarea> 
		</div>
		
		<div class="rowElem"> 
		</div>
		
		<div class="rowElem">
		 </div>
		 
			<div id="addhelper" class="ui-widget">
				<div style="PADDING-BOTTOM: 5px; PADDING-LEFT: 5px; PADDING-RIGHT: 5px; PADDING-TOP: 5px" class="ui-state-error ui-corner-all">
					<div id="addresult">
					</div>
				</div>
			</div>
			
		</form>
		 
		</div>
		</div>
	</body>
</html>
