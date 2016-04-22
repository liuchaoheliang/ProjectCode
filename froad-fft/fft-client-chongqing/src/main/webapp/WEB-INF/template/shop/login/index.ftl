<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>登录</title>
<link rel="icon" href="${base}/favicon.ico" type="image/x-icon" />
<#include "/include/sources.ftl">
<link href="${base}/resources/common/css/validate.css" rel="stylesheet" type="text/css" />
<script src="${base}/resources/common/js/jquery.validate.js"></script>
<link href="${base}/resources/common/css/login.css" rel="stylesheet" type="text/css" />
<#-- SSO登录信息获取 -->
<#include "/include/system/sso/sso_login_ticket.ftl">
<script type="text/javascript">
var currLoc = window.location.href;
if(currLoc.indexOf('common/logout.jhtml') != -1){
	window.location.href = '${base}/shop/login/index.jhtml';
}
$.Ajax({
	url : '${base}/sso/core/query_info.jhtml',
	useErrorMsg : false,
	useRandom : true,
	successFn : function (data){
		if(data.flag){
			if(window.location.href.indexOf('shop/login/index.jhtml') != -1){
				window.location.href = '${base}';
				reteurn;
			}
		}
	}
});
</script>

<script type="text/javascript">
$(document).ready(function (){
	var loginID = getCookie('loginID');
	if(loginID != null){
		$("#checkName").attr('checked',true);
		$("#loginId").val(loginID);
	}
	$('#login').click(login);
	$('body').keyup(function (event){
		var keycode = event.which;  
		if (keycode == 13) {  
			login();
		}  
	});
	function login(){
		//表单未通过验证直接返回
		var flag=$('#commentForm').valid();	
		if(!flag){
			return ;
		}
		
		var loginId = $("#loginId").val();
		var loginPwd = $('#loginPwd').val();
		$.Ajax({
			url : '${base}/shop/login/login.jhtml',
			datas : {'loginID' : loginId , 'loginPwd' : loginPwd},
			lockScreen : false,
			successFn : function (data){
				if(data.flag){
					if($.trim($("input:checked").val()).length == 0){<#-- 如果没有选中记住密码 -->
						removeCookie('loginID');
					}else{
						if(loginID != loginId){
							addCookie('loginID',loginId,{expires: 7 * 24 * 60 * 60});
						}
					}
					
					$('#commentForm').submit();
					<#-- sso
					var curr_url = window.location.href;
					try{
						if(curr_url.indexOf('login/index.jhtml') != -1 || curr_url.indexOf('loginout.jhtml') != -1){
							window.location.href = settings.loginDefaultReturnUrl;
							return;
						}
						var last_url = document.referrer;
						if(last_url != null){
							window.location.href = last_url;
							return;
						}
						window.location.href = settings.loginDefaultReturnUrl;
						return;
					}catch(e){
						window.location.href = settings.loginDefaultReturnUrl;
						return;
					} -->
				}else{
					$("#msg").text(data.msg);
					$("#show").show("slow");
					setTimeout(function(){
						$("#show").hide("slow");
					},3000);
				}
			}
		});
	}
});
</script>
<script type="text/javascript">
jQuery.validator.addMethod("username", function(value, element, param) {
  var val = value.replace(/[\u4E00-\u9FA3]/g, '01');
  return /^[0-9a-zA-Z_\\-]{4,20}$/.test(val);   
}, $.validator.format("格式输入错误"));
</script>
<script type="text/javascript">
  $(document).ready(function(){
	$("#commentForm").validate({
		rules: {			
			username: {
				required: true,
				minlength: 2,
				username: true
			},

			password: {
				required: true,
				minlength: 6,
				pattern:/^[^\u4e00-\u9fa5]{0,}$/
			},

		},
		
		messages: {
			username: {
				required: '请填写帐号',
				minlength: '格式输入错误'
			},

			password: {
				required: '请输入密码',
				minlength: '密码输入错误',
				pattern: '密码输入错误'
			},
			
		},	
		 
		errorElement: "span",
		focusInvalid:false, 		
		focusCleanup:true,
		onkeyup:false,
		errorPlacement:function(error,element) {		   	   
	   		error.appendTo(element.parent());		
	   }	
	 });
  });
</script> 

</head>
<body>
<#-- 头部 开始-->
<#include "/include/common/header.ftl">
<#-- 头部 结束-->
<!--登录开始-->
<div class="main">
  <div class="loginbg">登录</div>
    <div class="miss" id="show" style="display: none">
    	<h2 id="msg"></h2>
    	<a href="#" onclick="closed()"></a> 
    </div>
  <div class="loginBox" >
    <div class="all">
      <div class="loginLeft validate"id="tab">
      <form name="formregedit" action="${SSO_SERVER_URL}login" method="post" id="commentForm">
  		<input type="hidden" name="_eventId" value="submit" />
		<input type="hidden" name="lt" id="lt" value="" />
		<input type="hidden" name="execution" id="execution" value="" />
        <div class="loginBord">
          <ul>
            <dl>
              <dt>用户名/手机号：</dt>
              <dd>
                <div>
               	 <strong>
                  		<input type="text" name="username" id="loginId"  class="loginText">
                  </strong>
                </div>
              </dd>
            </dl>
            <dl>
              <dt>密码：</dt>
              <dd>
                <div>
                	<strong>
                 		 <input type="password" name="password" id="loginPwd" class="loginText"/>
                  </strong>
                </div>
              </dd>
            </dl>
            <div class="forgot">
              <input name="" id="checkName" type="checkbox" value="true" class="checkBox"/>记住用户名/手机号
            </div>             
            <div class="login">
           	  <input id="login" class="subBtn fl w150" value="登录" type="button">
           	  <a href="${base}/shop/password/index.jhtml" class="uline">忘记密码？</a> 
           	</div>
          </ul>
        </div>
        </form>
      </div>
    </div>
  </div>
</div>

<#-- 底部 -->
<#include "/include/common/footer.ftl">
<#-- 底部 -->

</body>
</html>