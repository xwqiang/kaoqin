<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<link rel="stylesheet" type="text/css" href="css/default/om-default.css"> 
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/operamasks-ui.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {

var err="${err}";
var message="${message}";
var back="${back}";
var re_message="${re_message}";

if(back!=""){
       $.omMessageBox.alert({
                content:'${back}',
                 onClose:function(v){
                   history.back();
                   //history.go(-1);
                }
                
            });
      
       }
if(err!=""){

       $.omMessageBox.alert({
                content:'${err}',
                 onClose:function(v){
                   history.back();
                }
                
            });
      
       }
 if(message!=""){
        $.omMessageBox.alert({
                content:'${message}',
                 onClose:function(v){
                    var url = "${url}";
                    if(url=='logout.do'){
	                   top.location.href='${url}';
                    }else{
	                   location.href='${url}';
                    }
                 }
            });
}
});
</script>