$(document).ready(function() {
	 if(navigator.userAgent.indexOf("Firefox")>0)//判断浏览器是否属于Mozilla,Sofari
     {
		$("#tableUtil").bind('click',function(){
			 ResizeTable_Init(this,true,true);
	     });	
     }else{
    	 $("#tableUtil").bind('mouseover',function(){
    		 ResizeTable_Init(this,true,true);
         });
     }
	 $("#tableUtil").bind('mousemove',function(){	                 
	     MouseMoveToResize(window.event || arguments.callee.caller.arguments[0]);
		 //MouseMoveToResize(event);
	 });
	 $("#tableUtil").bind('mouseup',function(){
         MouseUpToResize();
	 });			     
});   
var currentResizeTdObj=null;
function MouseDownToResize(event,obj){
obj=obj||this;
event=event||window.event;
currentResizeTdObj=obj;
obj.mouseDownX=event.clientX;
obj.mouseDownY=event.clientY;
obj.tdW=obj.offsetWidth;
obj.tdH=obj.offsetHeight;
if(obj.setCapture) obj.setCapture();
else event.preventDefault();
}
function MouseMoveToResize(event){
if(!currentResizeTdObj) return ;
var obj=currentResizeTdObj;
event=event||window.event ||arguments.callee.caller.arguments[0];
    if(!obj.mouseDownX) return false;
    if(obj.parentNode.rowIndex==0) {
      var newWidth=obj.tdW*1+event.clientX*1-obj.mouseDownX;
      if(newWidth>0) obj.style.width = newWidth;
    else obj.style.width =1;
}
if(obj.cellIndex==0){
      var newHeight=obj.tdH*1+event.clientY*1-obj.mouseDownY;
      if(newHeight>0) obj.style.height = newHeight;
    else obj.style.height =1;
}
}
function MouseUpToResize(){
if(!currentResizeTdObj) return;
if (currentResizeTdObj.releaseCapture) currentResizeTdObj.releaseCapture();
currentResizeTdObj=null;
}
//改变表格行列宽函数
function ResizeTable_Init(table,needChangeWidth,needChangeHeight)
{
if(!needChangeWidth && !needChangeHeight)
   return;
var oTh=table.rows[0];
if(needChangeWidth){
    for(var i=0;i<oTh.cells.length;i++)   {
       var cell=oTh.cells[i];
       cell.style.cursor="e-resize";
       cell.style.width=cell.offsetWidth;
       cell.onmousedown =MouseDownToResize;
    }
}
if(needChangeHeight){
    for(var j=0;j<table.rows.length;j++)   {
       var cell=table.rows[j].cells[0];
       cell.style.cursor="s-resize";
       cell.onmousedown =MouseDownToResize;
    }
}
if(needChangeWidth && needChangeHeight)
   oTh.cells[0].style.cursor="se-resize";
table.style.width=null;
table.style.tableLayout="fixed";
}
//函数块定义结束

