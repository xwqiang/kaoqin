$(document).ready(function(){
  // 隔行换色\鼠标悬停变色

var t=document.getElementById("tableStyle").getElementsByTagName("tr"); 
for(var i=0;i<t.length;i++){ 
t[i].style.backgroundColor=(t[i].sectionRowIndex%2==0)?"#FFF":"#F4F9FC"; 
t[i].onmouseover=function(){ 
if(this.x!="1")this.style.backgroundColor="#D0E5F5"; 
} 
t[i].onmouseout=function(){ 
if(this.x!="1")this.style.backgroundColor=(this.sectionRowIndex%2==0)?"#FFF":"#F4F9FC"; 
} 
} 
});