<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<link rel="icon" href="${base}/favicon.ico" type="image/x-icon" />
<title>重置密码</title>
<#include "/include/sources.ftl">
<link href="${base}/resources/common/css/validate.css" rel="stylesheet" type="text/css" />
<script src="${base}/resources/common/js/jquery.validate.js"></script>
<script src="${base}/resources/common/js/password.js"></script>
<link href="${base}/resources/common/css/login.css" rel="stylesheet" type="text/css" />
</head>
<body>

</script>
<!--验证配置--> 
<script type="text/javascript">
  $(document).ready(function(){
	$("#commentForm").validate({
		rules: {			
			password: {
				required: true,
				minlength: 6,
				pattern: /^[^\u4e00-\u9fa5]{0,}$/,
			},
			repassword: {
				required: true,
				minlength: 6,
				pattern: /^[^\u4e00-\u9fa5]{0,}$/,
				equalTo: "#password"
			}
		},
		messages: {
			password: {
				required: '请输入您的密码',
				minlength: '密码不能小于6位',
				pattern: '密码输入错误'
			},
			repassword: {
				required: '请确认您的密码',
				minlength: '密码不能小于6位',
				pattern: '密码输入错误',
				equalTo: '两次输入密码不一致'
			}
		},	
		errorElement: "span",
		focusInvalid:false, 		
		focusCleanup:true,
		onkeyup:false,
		validClass: "success" ,
		success: function(span) {					
			span.addClass("success").text("验证通过!");
			span.parent().find('input').addClass("right");
		},	
	   errorPlacement:function(error,element) {		   	   
	   		error.appendTo(element.parent());		
	   }	
	 });
  });
</script>
<script type="text/javascript">
 $(function(){
	 $("#commentForm em").hide();
	 $("#commentForm input").focus(function(){		 
		 $(this).parent().parent().find("em").show();
		 })
		.blur(function(){		 
		 $(this).parent().parent().find("em").hide();
		 if($(this).parent().find('span').hasClass('success')== false){
			 $(this).parent().find('input').removeClass("right");
			 }		 
		 })
	 })	  
</script> 
<script>
	$(document).ready(function(){
		$("#subBtn").click(function(){
			var flag = $("#commentForm").valid();
			var password = $("#repassword").val();
			if(flag){
				$.Ajax({
						url : settings.baseUrl + '/shop/password/reset_pwd.jhtml',
						datas : {'password':password},
						lockScreen : true,
						successFn : function (data){
							if(data.flag){
								//重置成功
								layer.msg(data.msg, 2, 1,function(){
									window.location.href=settings.baseUrl+"/shop/login/index.jhtml";
								});
							}else{
								layer.msg(data.msg, 2, -1);
							}
						}
					});
			}
		});
	});
</script>
<!--
* Author:
* pengling@f-road.com.cn 
*/
--> 
<#-- 头部 开始-->
<#include "/include/common/header.ftl">
<#-- 头部 结束-->
<!--登录开始-->
<div class="main">
  <div class="loginbg" ></div>
  <div class="loginBox" >
    <div class="all" >
      <div class="regeditBox" >
        <div class="topReg"><B>已有帐号？请直接<a href="${base}/shop/login/index.jhtml">登录</a></B></div>
        <div class="regeditLeft validate nobg pl30" id="formlist" >
          <form name="formregedit"  id="commentForm" method="post" onsubmit="return false;">
            <dl>
              <h5 class="center">重置密码</h5>
              <dt>请输入新密码：</dt>
              <dd>
                <div> <strong>
                  <input type="password" class="loginText" name="password" id="password">
                  </strong> <em>请输入您的密码</em></div><div class="pw-strength pw-weak">
                                <div class="pw-letter">
									<span class="tsl">弱</span>
									<span>中</span>
									<span>强</span>
								</div>
                            </div>
              </dd>
            </dl>
            <dl>
              <dt>确认密码：</dt>
              <dd>
	                <div> 
		                <strong>
		                  	<input type="password" class="loginText" name="repassword" id="repassword">
		                 </strong><em>请输入您的密码</em>
	                 </div>
              </dd>
            </dl>
            <!--<dl>
              <dt>请输入右侧数字：</dt>
              <dd>
                <div> <strong>
                  <input type="text" class="loginText" id="username">
                </strong><em>填写错误</em> </div>
              </dd>
            </dl>-->
           <dl >
              <dt>&nbsp; </dt>
              <dd >
                <input class="subBtn" id="subBtn" value="修改密码" type="submit">
              </dd>
            </dl>
          </form>
        </div>
      </div>
    </div>
    <!--登录结束--> 
  </div>
</div>

<#-- 底部 -->
<#include "/include/common/footer.ftl">
<#-- 底部 -->
</body>
</html>