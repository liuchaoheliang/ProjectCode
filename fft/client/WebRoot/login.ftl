<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<title>登录</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/web/css/login.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/rightimg.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/explain.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/oldValidate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/login.js"></script>
<script type="text/javascript" src="${base}/template/common/js/closeDialog.js"></script>
<script type="text/javascript">
	$(function() {
		flushLoginTicket();
	});
	
	var flushLoginTicket = function() {
		var _services = 'service='
				+ encodeURIComponent('${stack.findValue("@com.froad.sso.SSOCons@LOCAL_SSO_CFG")}index.action');
		var urlOfGetLt = '${stack.findValue("@com.froad.sso.SSOCons@SSO_SERVER")}login?' + _services
				+ '&get-lt=true&n=' + new Date().getTime();
				
		$.ajax({
			type : "get",
			async : false,
			url : urlOfGetLt,
			dataType : "jsonp",
			jsonp : "callback",
			jsonpCallback : "flightHandler",
			success : function(json) {
				$("#lt").val(json.lt);
				$("#execution").val(json.execution);
			},
			error : function() {
				alert("无法获取登录key");
			}
		});
	};
	
	
	var flightHandler = function(data){
		$("#lt").val(data.lt);
		$("#execution").val(data.execution);
	};
	
	
	
	
	
</script>
</head>
<body>
<!--
* Author:
* pengling@f-road.com.cn 
*/
-->
<!--头部开始-->
<#include "/WEB-INF/templates/common/header.ftl">
<!--头部结束-->

<!--导航开始-->
<#include "/WEB-INF/templates/common/menu.ftl">
<!--导航结束-->


  
<!--登录开始-->
<div class="loginBox">

  <!--错误提示开始-->
	<div style="height:30px"> 
	<div class="miss" id="show" style="display:none;">
	 <h2 id="wrongDiv"></h2>
	 <a href="#" onclick="closed()"></a>
	</div>
	</div>
<!--错误提示结束-->
<div class="all">

  <div class="loginLeft" id="tab">
    <ol>
     <li id="userClass" class="playRcut">个人登录</li> 
     <li id="merchantClass">商户登录</li>
    </ol>
    <div class="loginBord">
    <form id="loginUserForm" action="${stack.findValue("@com.froad.sso.SSOCons@SSO_SERVER")}login" method="post"  >  
    <input type="hidden" name="_eventId" value="submit" />
    <input type="hidden" name="lt" id="lt" value="">
    <input type="hidden" name="execution" id="execution" value="">
     <input type="hidden" name="loginUrl" value="${stack.findValue("@com.froad.sso.SSOCons@LOCAL_SSO_CFG")}index.action">
    
    
    <ul> 
     <dl>
       <dt>用户名/手机号：</dt>
       <dd>
         <div>
         	<input type="text" name="username" dataName="用户名" value="" class="loginText" id="txtUser"    /><span></span>
         </div>
       </dd>
     </dl>
     <dl>
       <dt>密码：</dt>
       <dd>
         <div>
         	<input type="password" name="password" id="txtPassword" class="loginText" dataName="密码"  />
         </div>       
       </dd>
     </dl>  
      <div class="login">
        <div class="ml100"><a href="javascript:void(0);" onclick="javascript:loginUser();"><div id="loginBtnUser" class="textBtn"><B>登录</B></div></a></div>
          <a href="${base}/toResgiter.action">注册账号</a> <a href="${base}/forget.action">找回密码</a> 
      </div>   
    </ul> 
     </form>
    <ul style="display:none"> 
    <form id="loginMerchantForm" action="${base}/merchant_login.action" method="post">  
     <dl>
       <dt>商家名：</dt>
       <dd>
         <div>
           <input type="text" name="loginName" dataName="用户名" value="" class="loginText" id="txt2User" />
         </div>
       </dd>
     </dl>
      <dl>
       <dt>工号：</dt>
       <dd>
         <div>
           <input type="text" name="beCode" value="" class="loginText" id="beCode" />
         </div>
       </dd>
     </dl>
     <dl>
       <dt>密码：</dt>
       <dd>
         <div>
           <input type="password" name="clerkPwd" id="txt2Password" class="loginText" dataName="密码"  />
         </div>       
       </dd>
     </dl>
     </form>
     <div class="login">
        <div class="ml100"><a href="javascript:void(0);" onclick="javascript:loginMerchant();"><div id="loginBtnMerchant" class="textBtn"><B>登录</B></div></a></div>
		<a href="${base}/merchant_apply_index.action">商户合作申请</a></div>   
    </ul>  
    </div>                   
   </div>    
    <script type="text/javascript" src="${base}/template/common/js/tab.js"></script>
   <div class="loginRight"><img src="${base}/template/web/images/denglu.png"></div>
   </div>
</div>
<!--注册开始-->
               
<!--底部开始-->  
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束--> 
<script type="text/javascript">
	
	$(document).ready( function() {
		var regInfo = "${((registerInfo)!"")?html}";
		if($.trim(regInfo).length != 0){
			$("#wrongDiv").html(regInfo);
			$('#show').slideDown(200);
		}
		
		var $userClass = $("#userClass");
		var $merchantClass = $("#merchantClass");
		
		$(document).keyup(function(event){   
	     	//获取当前按键的键值   
	     	//jQuery的event对象上有一个which的属性可以获得键盘按键的键值   
	     	var keycode = event.which;  
	     	 
	     	//处理回车的情况   
	     	if(keycode==13){   
	         	var classvalue=$userClass.attr("class");
	         	if(classvalue=='playRcut'){
	         		loginUser();
	         	}
	         	else{
	         		loginMerchant();
	         	}
	    	}   
 		}); 
		
	});
	
	
	   

	/* 1: 个人登录 */
	function loginUser(){
		var $wrong = $("#wrongDiv");
		var name = "";
		var pwd = "";
		var type="1";
		name = $('#txtUser').val();
		pwd = $('#txtPassword').val();
		if(name == ''){
			$wrong.html("账号不能为空");
			$('#show').slideDown(200);
			return;
		}
		if(pwd == ''){
			$wrong.html("请输入密码");
			$('#show').slideDown(200);
			return;
		}
		
		$.ajax({
			url:"validateLogin.action",
			data:{username:name,password:pwd,loginType:type},
			type:"post",
			dataType:"json",
			success:function (data){
			
			
				if(data.reno == "0"){
				$("#username").val(name);
				$("#password").val(pwd);
				$("#loginUserForm").submit();
			}else{
				//$("#loginUserForm").submit();
				$wrong.html(data.msg);
				$('#show').slideDown(200);
				return;
			}
			}
		});
	}
	/* 2: 商户登录 */
	function loginMerchant(){
		var $wrong = $("#wrongDiv");
		var name = "";
		var pwd = "";
		var beCode = "";
		var type="2";
		name = $('#txt2User').val();
		beCode = $('#beCode').val();
		pwd = $('#txt2Password').val();
		if(name == ''){
			$wrong.html("账号不能为空");
			$('#show').slideDown(200);
			return;
		}		
		if(beCode == ''){
			$wrong.html("请输入您的商户工号");
			$('#show').slideDown(200);
			return;
		}
		if(pwd == ''){
			$wrong.html("请输入密码");
			$('#show').slideDown(200);
			return;
		}
		$.ajax({
			url:"validateLogin.action",
			data:{username:name,password:pwd,loginType:type,beCode:beCode},
			type:"post",
			dataType:"json",
			success:function (data){
				if(data.reno == "0"){
				//$("#username").val(name);
				//$("#password").val(pwd);
				$("#loginMerchantForm").submit();
				}else{
					$wrong.html(data.msg);
					$('#show').slideDown(200);
					return;
				}
			}
		});
	}
</script> 
<script type="text/javascript">
$(document).ready(function (){
	$('#merchantClass').click(clearInfo);
	$('#userClass').click(clearInfo);
	function clearInfo(){
		$('#wrongDiv').html('');
		$('#show').hide();
	}
});
</script>
</body>
</html>
