
<html>   
<head><title>跳转页面</title>   
<script language='javascript' type='text/javascript'>   
var secs =3; //倒计时的秒数   
var URL ;   
function Load(url){   
URL =url;   
for(var i=secs;i>=0;i--)   
{   
window.setTimeout('doUpdate(' + i + ')', (secs-i) * 1000);   
}   
}   
function doUpdate(num)   
{   
document.getElementById('ShowDiv').innerHTML = '请输入正确人员姓名，将在'+num+'秒后自动返回初始界面！' ;   
if(num == 0) { window.location=URL; }   
}   
</script>   
</head>   
<body>   
<div id="ShowDiv" style="text-align: center;line-height: 260px">
	
</div>   
<script language="javascript">   

Load("http://localhost:8080/attendance/getPersonChecking-in.do"); //要跳转到的页面   
</script>   
</body>   
</html>  

