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
			
			

			var TableFixed = function(table, options){
				this._oTable = $$(table);//原table
				this._nTable = this._oTable.cloneNode(false);//新table
				this._nTable.id = "";//避免id冲突
				
				this._oTablePos = {};//记录原table坐标参数
				this._oRowPos = {};//记录原tr坐标参数
				this._viewHeight = this._oTableHeight = this._nTableHeight = 0;//记录高度
				this._nTableViewTop = 0;//记录新table视框top
				this._selects = [];//select集合，用于ie6覆盖select
				
				this._setOptions(options);
				
				this._index = this.options.index;
				this._pos = this.options.pos;
				
				this.auto = !!this.options.auto;
				this.hide = !!this.options.hide;
				
				$$E.addEvent(window, "resize", $$F.bind( this.setPos, this ));
				$$E.addEvent(window, "scroll", $$F.bind( this.run, this ));
				
				this._oTable.parentNode.insertBefore(this._nTable, this._oTable);
				this.clone();
			};
			TableFixed.prototype = {
			  //chrome/safari透明用rgba(0, 0, 0, 0)
			  _transparent: $$B.chrome || $$B.safari ? "rgba(0, 0, 0, 0)" : "transparent",
			  //设置默认属性
			  _setOptions: function(options) {
				this.options = {//默认值
					index:	0,//tr索引
					auto:	true,//是否自动定位
					pos:	0,//自定义定位位置百分比(0到1)
					hide:	false//是否隐藏（不显示）
				};
				$$.extend(this.options, options || {});
			  },
			  //克隆表格
			  clone: function(index) {
				//设置table样式
				$$D.setStyle(this._nTable, {
					width: this._oTable.offsetWidth + "px",
					position: $$B.ie6 ? "absolute" : "fixed",
					zIndex: 99, borderTopWidth: 0, borderBottomWidth: 0
				});
				//设置index
				this._index = Math.max(0, Math.min(this._oTable.rows.length - 1, isNaN(index) ? this._index : index));
				//克隆新行
				this._oRow = this._oTable.rows[this._index];
				var oT = this._oRow, nT = oT.cloneNode(true);
				if( oT.parentNode != this._oTable ){
					nT = oT.parentNode.cloneNode(false).appendChild(nT).parentNode;
				}
				//插入新行
				if ( this._nTable.firstChild ) {
					this._nTable.replaceChild( nT, this._nTable.firstChild );
				} else {
					this._nTable.appendChild(nT);
				}
				//去掉table上面和下面的边框
				if ( this._oTable.border > 0 ) {
					switch (this._oTable.frame) {
						case "above" :
						case "below" :
						case "hsides" :
							this._nTable.frame = "void"; break;
						case "" :
						case "border" :
						case "box" :
							this._nTable.frame = "vsides"; break;
					}
				}
				//设置td样式
				var nTds = this._nTable.rows[0].cells, getStyle = $$D.getStyle;
				$$A.forEach(this._oRow.cells, $$F.bind(function(o, i){
					var style = nTds[i].style;
					//设置td背景
					style.backgroundColor = this._getBgColor(o);
					//设置td的width,没考虑ie8/chrome设scroll的情况
					style.width = (document.defaultView ? parseFloat(getStyle(o, "width"))
						: ( o.clientWidth - parseInt(getStyle(o, "paddingLeft")) - parseInt(getStyle(o, "paddingRight")) )) + "px";
				}, this));
				//获取table高度
				this._oTableHeight = this._oTable.offsetHeight;
				this._nTableHeight = this._nTable.offsetHeight;
				//设置坐标属性
				this._oTablePos = $$D.rect(this._oTable);//获取原table位置
				this._oRowPos = $$D.rect(this._oRow);//获取原tr位置
				
				this.setPos();
			  },
			  //获取背景色
			  _getBgColor: function(node) {
				var bgc = "";
				//不要透明背景（没考虑图片背景）
				while (bgc === this._transparent && (node = node.parentNode) != document) {
					bgc = $$D.getStyle(node, "backgroundColor");
				}
				return bgc === this._transparent ? "#fff" : bgc;
			  },
			  //设置新table位置属性
			  setPos: function(pos) {
				//设置pos
				this._pos = Math.max(0, Math.min(1, isNaN(pos) ? this._pos : pos));
				//获取位置
				this._viewHeight = document.documentElement.clientHeight;
				this._nTableViewTop = (this._viewHeight - this._nTableHeight) * this._pos;
				this.run();
			  },
			  //运行
			  run: function() {
				var tStyle = this._nTable.style;
				if(!this.hide){
					var top = $$D.getScrollTop(), left = $$D.getScrollLeft()
						//原tr是否超过顶部和底部
						,outViewTop = this._oRowPos.top < top, outViewBottom = this._oRowPos.bottom > top + this._viewHeight;
					//原tr超过视窗范围
					if ( outViewTop || outViewBottom ) {
						var viewTop = !this.auto ? this._nTableViewTop
								: (outViewTop ? 0 : (this._viewHeight - this._nTableHeight))//视窗top
							,posTop = viewTop + top;//位置top
						//在原table范围内
						if( posTop > this._oTablePos.top && posTop + this._nTableHeight < this._oTablePos.bottom ){
							//定位
							if( $$B.ie6 ){
								tStyle.top = posTop + "px";
								tStyle.left = this._oTablePos.left + "px";
								setTimeout($$F.bind(this._setSelect, this), 0);//iebug
							}else{
								tStyle.top = viewTop + "px";
								tStyle.left = this._oTablePos.left - left + "px";
							}
							return;
						}
					}
				}
				//隐藏
				tStyle.top = "-99999px";
				$$B.ie6 && this._resetSelect();
			  },
			  //设置select集合
			  _setSelect: function() {
				this._resetSelect();
				var rect = $$D.clientRect(this._nTable);
				//把需要隐藏的放到_selects集合
				this._selects = $$A.filter(this._oTable.getElementsByTagName("select"), $$F.bind(function(o){
					var r = $$D.clientRect(o);
					if(r.top <= rect.bottom && r.bottom >= rect.top){
						o._count ? o._count++ : (o._count = 1);//防止多个实例冲突
						//设置隐藏
						var visi = o.style.visibility;
						if(visi != "hidden"){ o._css = visi; o.style.visibility = "hidden"; }
						return true;
					}
				}, this))
			  },
			  //恢复select样式
			  _resetSelect: function() {
				$$A.forEach( this._selects, function(o){ !--o._count && ( o.style.visibility = o._css ); } );
				this._selects = [];
			  }
			};
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
				<li><a href="getPersonChecking-in.do">个人考勤</a></li>
				<li><a href="showAllAttendance.do" class="current">&nbsp;&nbsp;&nbsp;&nbsp;统计&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
			</ul>
		</div>
		<!--搜索部分 start-->
		<div id="toolbar">
			<form method="post" action="showAllAttendance.do" id="formID" accept-charset="utf-8">
				
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
					<c:if test="${totalStats!=null}">
						<tr>
							<td colspan="3"><strong>合计:</strong></td>
							<td>
									<c:if test="${totalStats.office_date_count!=0}">
									
										${totalStats.office_date_count}
									</c:if>
								</td>
								<td>
									<c:if test="${totalStats.waichu_count!=0}">
										${totalStats.waichu_count}
									</c:if>
								</td>
								<td>
									<c:if test="${totalStats.chuchai_count!=0}">
										${totalStats.chuchai_count}
									</c:if>
								</td>
								<td>
								<c:if test="${totalStats.tiaoxiu_count!=0}">
									${totalStats.tiaoxiu_count}
									</c:if>
								</td>
								<td>
									<c:if test="${totalStats.nianjia_count!=0}">
									
									${totalStats.nianjia_count}
									</c:if>
								</td>
								<td>
								<c:if test="${totalStats.hunjia_count!=0}">
									${totalStats.hunjia_count}
									</c:if>
								</td>
								<td>
								<c:if test="${totalStats.chanjia_count!=0}">
									${totalStats.chanjia_count}
									</c:if>
								</td>
								<td>
								<c:if test="${totalStats.sangjia_count!=0}">
									${totalStats.sangjia_count}
									</c:if>
								</td>
								<td>
								<c:if test="${totalStats.kuanggong_count!=0}">
									${totalStats.kuanggong_count}
									</c:if>
								</td>
								
								<td>
									<c:if test="${totalStats.late_fine!=0}">
									${totalStats.late_fine}
									</c:if>
								</td>
								<td>
									
								</td>
								<td>
									<c:if test="${totalStats.forget_fine!=0}">
										${totalStats.forget_fine}
									</c:if>
								</td>
								<td>
								<c:if test="${totalStats.real_working_date_count!=0}">
									${totalStats.real_working_date_count}
									</c:if>
								</td>
								<td>
									<c:if test="${totalStats.shijia_count!=0}">
										${totalStats.shijia_count}
									</c:if>
								</td>
								<td>
									<c:if test="${totalStats.bingjia_count!=0}">
									${totalStats.bingjia_count}
									</c:if>
								</td>
								<td>
								<c:if test="${totalStats.fanbu_date_count!=0}">
									${totalStats.fanbu_date_count}
									</c:if>
								</td>
								<td>
								<c:if test="${totalStats.workdate_overtime_count!=0}">
									${totalStats.workdate_overtime_count}
									</c:if>
								</td>
								<td>
								<c:if test="${totalStats.overtime_count!=0}">
									${totalStats.overtime_count}
									</c:if>
								</td>
								<td>
								<c:if test="${totalStats.holiday_overtime_count!=0}">
									${totalStats.holiday_overtime_count}
									</c:if>
								</td>
								<td>
								<c:if test="${totalStats.undetermined_count!=0}">
									<p style="color: red;font-weight: bold;">
										${totalStats.undetermined_count}
									</p>
									</c:if>
								</td>
						</tr>
						</c:if>
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