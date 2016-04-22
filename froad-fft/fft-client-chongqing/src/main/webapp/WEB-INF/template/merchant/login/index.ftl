<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>银行登录</title>
<link rel="icon" href="${base}/favicon.ico" type="image/x-icon" />
<#include "/include/sources.ftl">
<link href="${base}/resources/common/css/login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	$().ready( function() {
		
		var $loginButton = $("#loginButton");
		var $loginForm = $("#loginForm");
		var $username = $("#username");
		var $password = $("#password");
		var $isRememberUsername = $("#isRememberUsername");
		
		// 记住银行登录名
		if(getCookie("merchantUsername") != null) {
			$isRememberUsername.prop("checked", true);
			$username.val(getCookie("merchantUsername"));
			$password.focus();
		} else {
			$isRememberUsername.prop("checked", false);
			$username.focus();
		}
		
		// 点击登录
		$loginButton.click(function(e) {
			submit();
		});
		
		// 回车登录
		$(document).keydown(function(e) {
			if (e.keyCode == 13) {
				submit();
			}
		});
		
		// 表单提交
		function submit() {
			if ($username.val() == "") {
				layer.msg("银行登录名不能为空", 2, -1);
				return false;
			}
			
			if ($password.val() == "") {
				layer.msg("密码不能为空", 2, -1);
				return false;
			}
			
			if ($isRememberUsername.prop("checked")) {
				addCookie("merchantUsername", $username.val(), {expires: 7 * 24 * 60 * 60});
			} else {
				removeCookie("merchantUsername");
			}
			
			$.ajax({
                cache: false,
				dataType : "json",
                type: "POST",
                url:$loginForm.attr('action'),
                data:$loginForm.serialize(),
                async: false,
                success: function(data) {
                	if(data.type == "success"){
						window.location.href='${base}/merchant/index.jhtml';
					}
					else{
						$("#msg").text(data.content);
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
</head>
<body>
<#-- 头部 开始-->
<#include "/include/common/header.ftl">
<script type="text/javascript" >
var curr = window.location.href;
if(curr.indexOf('merchant') != -1){
	$('.center').attr('href','${base}/merchant/index.jhtml');
}
</script>
<#-- 头部 结束-->
<!--登录开始-->
<div class="main">
  <div class="loginbg">银行登录</div>
    <div class="miss" id="show" style="display: none">
    	<h2 id="msg"></h2>
    	<a href="#" onclick="closed()"></a> 
    </div>
  <div class="loginBox" >
    <form id="loginForm" action="${base}/merchant/login/login.jhtml" method="post">
    <div class="all">
      <div class="loginLeft" id="tab">
        <div class="loginBord">
          <ul>
            <dl>
              <dt>银行登录名：</dt>
              <dd>
                <div>
                  <input type="text" name="username" id="username"  class="loginText">
                </div>
              </dd>
            </dl>
            <dl>
              <dt>密码：</dt>
              <dd>
                <div>
                  <input type="password" name="password" id="password" class="loginText"/>
                </div>
              </dd>
            </dl>
            <#--
            <div class="forgot">
              <input name="isRememberUsername" id="isRememberUsername" type="checkbox" value="true" /> 记住银行登录名
            </div>   
            -->            
             <div class="login">
              <input class="subBtn fl w150 mt20" value="登录" type="button" id="loginButton" >
              <#--<a href="#" class="uline">忘记密码？</a> --> 
             </div>
          </ul>
        </div>
      </div>
    </div>
   </form>
    <!--登录结束--> 
  </div>
</div>

<#-- 底部 -->
<#include "/include/common/footer.ftl">
<#-- 底部 -->

</body>
</html>