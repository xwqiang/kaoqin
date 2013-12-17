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
		<title>定制日历模板</title>
		<link href='fullcalendar/fullcalendar.css' rel='stylesheet' />
<link href='fullcalendar/fullcalendar.print.css' rel='stylesheet' media='print' />
<script src='jquery/jquery-1.9.1.min.js'></script>
<script src='jquery/jquery-ui-1.10.2.custom.min.js'></script>
<script src='fullcalendar/fullcalendar.min.js'></script>
<script>

	$(document).ready(function() {
	
	
		/* initialize the external events
		-----------------------------------------------------------------*/
	
		$('#external-events div.external-event').each(function() {
		
			// create an Event Object (http://arshaw.com/fullcalendar/docs/event_data/Event_Object/)
			// it doesn't need to have a start or end
			var eventObject = {
				title: $.trim($(this).text()) // use the element's text as the event title
			};
			
			// store the Event Object in the DOM element so we can get to it later
			$(this).data('eventObject', eventObject);
			
			// make the event draggable using jQuery UI
			$(this).draggable({
				zIndex: 999,
				revert: true,      // will cause the event to go back to its
				revertDuration: 0  //  original position after the drag
			});
			
		});
	
	
		/* initialize the calendar
		-----------------------------------------------------------------*/
		
		$('#calendar').fullCalendar({

			
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
			
			editable: true,
			droppable: true, // this allows things to be dropped onto the calendar !!!
			drop: function(date, allDay) { // this function is called when something is dropped
			
				// retrieve the dropped element's stored Event Object
				var originalEventObject = $(this).data('eventObject');
				
				// we need to copy it, so that multiple events don't have a reference to the same object
				var copiedEventObject = $.extend({}, originalEventObject);
				
				// assign it the date that was reported
				copiedEventObject.start = date;
				copiedEventObject.allDay = allDay;
				
				// render the event on the calendar
				// the last `true` argument determines if the event "sticks" (http://arshaw.com/fullcalendar/docs/event_rendering/renderEvent/)
				$('#calendar').fullCalendar('renderEvent', copiedEventObject, true);
				
				// is the "remove after drop" checkbox checked?
				if ($('#drop-remove').is(':checked')) {
					// if so, remove the element from the "Draggable Events" list
					$(this).remove();
				}
				
			}
		});
		
		
	});

</script>
<style>

	body {
		margin-top: 40px;
		text-align: center;
		font-size: 14px;
		font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
		}
		
	#wrap {
		width: 1100px;
		margin: 0 auto;
		}
		
	#external-events {
		float: left;
		width: 150px;
		padding: 0 10px;
		border: 1px solid #ccc;
		background: #eee;
		text-align: left;
		}
		
	#external-events h4 {
		font-size: 16px;
		margin-top: 0;
		padding-top: 1em;
		}
		
	.external-event { /* try to mimick the look of a real event */
		margin: 10px 0;
		padding: 2px 4px;
		background: #3366CC;
		color: #fff;
		font-size: .85em;
		cursor: pointer;
		}
		
	#external-events p {
		margin: 1.5em 0;
		font-size: 11px;
		color: #666;
		}
		
	#external-events p input {
		margin: 0;
		vertical-align: middle;
		}

	#calendar {
		float: right;
		width: 900px;
		}

</style>

	</head>
	<body>
	
	<div id='wrap'>

<div id='external-events'>
<h4>排班分类</h4>
<div class='external-event'><b> &nbsp; &nbsp;班：</b>上班时间：09:00--18:00</div>
<div class='external-event'><b> &nbsp; &nbsp;值：</b>上班时间：10:00--19:00</div>
<div class='external-event'><b> &nbsp; &nbsp;技术-早班：</b>上班时间：08:00--17:00</div>
<div class='external-event'><b> &nbsp; &nbsp;技术-晚班：</b>上班时间：12:00--21:00</div>
<div class='external-event'><b> &nbsp; &nbsp;休：</b>休息日</div>

<div class='external-event'><b> &nbsp; &nbsp;节假日：</b>国家法定节假日</div>
<div class='external-event'><b> &nbsp; &nbsp;节假日加班：</b>国家法定节假日加班</div>
<div class='external-event'><b> &nbsp; &nbsp;休息日加班：</b>休息日加班</div>
<div class='external-event'><b> &nbsp; &nbsp;平日加班：</b>2小时为最小计时单位，不超过3小时</div>
<p>
<input type='checkbox' id='drop-remove' /> <label for='drop-remove'>remove after drop</label>
</p>
</div>

<div id='calendar'></div>

<div style='clear:both'></div>
</div>
	</body>
</html>
