<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<link rel="icon" href="${base}/favicon.ico" type="image/x-icon" />
<title>忘记密码</title>
<#include "/include/sources.ftl">
<link href="${base}/resources/common/css/validate.css" rel="stylesheet" type="text/css" />
<script src="${base}/resources/common/js/jquery.validate.js"></script>
<link href="${base}/resources/common/css/login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$(document).ready(function (){
	var msg = "${msg}";
	if(msg.length != 0){
		layer.msg(msg,2,-1);
	}
});
</script>
<!--验证配置--> 
<script type="text/javascript">
  $(document).ready(function(){
	$("#commentForm").validate({
		rules: {			
			mobile: {
				required: true,
				minlength: 7,
				pattern: /^1[3|4|5|7|8][0-9]\d{4,8}$/
			},
			phonecode: {
				required: true,
				minlength: 4,
				pattern: /^\d{4}$/
			}

		},
		
		messages: {
			mobile: {
				required: '请填写手机号码',
				minlength: '手机号码输入错误',
				pattern: '手机号码输入错误'
			},
			phonecode: {
				required: '请填写短信验证码',
				minlength: '短信验证码输入错误',
				pattern: '短信验证码输入错误'
			}
		},	
		 
		errorElement: "em",
		focusInvalid:false, 		
		focusCleanup:true,
		onkeyup:false,
		validClass: "success" ,
		 
		success: function(span) {					
			span.addClass("success").text("格式正确!");
			span.prev().addClass("right")
		},	
		
	   errorPlacement:function(error,element) {		   	   
	   		error.appendTo(element.parent());		
	   }	
		
	 });
  });
</script> 
<script>
	$(document).ready(function(){
		var randomCode = null;
		var codeTimer = null;
		<#-- 发送短信验证码  开始-->
		$('#getcode').click(getCode);
		function getCode(){
			$('#phonecode').val('');
			var phone = $.trim($("#phone").val());
			if(phone==""){
				layer.msg('请先输入手机号码', 2, -1);
				return;
			}
			if(!regexp.phoneRE.test(phone)){
				layer.msg('请输入正确的手机号码',2,-1);
				return;
			}
			//发送验证码之前，先校验手机是否存在
				$.Ajax({
					url:settings.baseUrl + '/shop/password/validateUserPhoneExists.jhtml',
					datas:{'mobile':phone},
					successFn : function (data){
						if(data.flag){
								$.Ajax({
									url : settings.baseUrl + '/common/send_code_sms.jhtml',
									datas : {'smsType':'authcodeResetPwd','toPhoneNumber':phone},
									lockScreen : true,
									successFn : function (data){
										if(data.flag){
											layer.msg(msgInfo.sendSMSSuccess, 2, -1);
											randomCode = data.randomCode;
											timerDown('getcode');
											codeTimer = settings.countDownTimer;
										}else{
											layer.msg(data.msg, 2, -1);
										}
									}
								});
						}else{
							layer.msg(data.msg, 2, -1);
						}
					}
				});
			//
			
		}//getCode end;
		<#-- 跑秒函数 -->
		function timerDown(eleId){
			var ele = $("#" + eleId);
			ele.unbind();
			var interval;
			interval = setInterval(function() {
				codeTimer --;
				if(codeTimer <= 0){
					clearInterval(interval);
					ele.val('获取手机验证码');
					$('#getcode').bind('click',getCode);
				}else{
					ele.val(codeTimer + '秒后可以重发');
				}
			},1000);
		}
	
		$('#send').click(function (){		
			var flag=$('#commentForm').valid();	
			if(flag){	
				<#-- 短信验证码验证-->	
					var val = $.trim($('#phonecode').val());
					var phone = $.trim($("#phone").val());
					$.Ajax({
						url : settings.baseUrl + '/common/validate_code_sms.jhtml',
						datas : {'smsCode':val},
						lockScreen : true,
						successFn : function (data){
							if(data.flag){
								$("#commentForm").attr("onsubmit","");
								$("#commentForm").submit();
								//短信验证码验证成功。
							}else{
								$("#phonecode").val("");
								$("#phonecode").focus();	
								if(data.msg!=null){
									layer.msg(data.msg, 2, -1);
								}else{
									layer.msg(msgInfo.operateFailureMsg, 2, -1);
								}
							}
						}
					});
			}else{
				return;
			}
		
		});		
	});
</script>
<script>
	$(document).ready(function(){
				$("#phonecode").blur(function(){
					var val = $.trim($('#phonecode').val());
					if(val!=""){
						$.Ajax({
							url : settings.baseUrl + '/common/validate_code_sms.jhtml',
							datas : {'smsCode':val},
							successFn : function (data){
								if(!data.flag){
									layer.msg("手机验证码不正确",2,-1);
									$('#phonecode').val("");
								}
							}
						});	
					}	
				});
	});
</script>
<style>
	.getCode{
	    border-radius:5px;
	    height:26px;
	    width: 115px!important;
	}
</style>
</head>
<body>
<!--
* Author:
* pengling@f-road.com.cn 
*/
--> 
<#-- 头部 开始-->
<#include "/include/common/header.ftl">
<#-- 头部 结束-->
<style>
.regeditBox .regeditLeft dl{
	clear: both;
    padding: 20px 0 5px 240px;
}
.regeditBox .regeditLeft{
	height: 300px;
}
</style>
<!--登录开始-->
<div class="main">
  <div class="loginbg"></div>
  <div class="loginBox" >
    <div class="all">
      <div class="regeditBox" id="find">
        <div class="topReg"><B>已有帐号？请直接<a href="${base}/shop/login/index.jhtml">登录</a></B></div>
        <div class="regeditLeft validate" id="formlist" style="height:334px;">
          <form name="formregedit" action="${base}/shop/password/reset_password.jhtml" method="post" id="commentForm" onsubmit='return false;' >
            <dl><h5 class="center">找回密码</h5>
              <dt>通过手机号找回：</dt>
              <dd>
                <div> <strong>
                  <input type="text" class="loginText" id="phone" name="mobile" maxlength="11">
                  </strong></div>
              </dd>
            </dl>
            <dl>
              <dt>手机验证码：</dt>
              <dd>
                <div> <strong>
                  <input  type="text" class="loginText3" id="phonecode" name="phonecode" maxlength="4" />
                  <input  name="getcode"  type="button" id="getcode" class="getCode"  value="获取手机验证码"/></strong>
                </div>
              </dd>
            </dl>

            <dl class="mt2">
              <dt>&nbsp; </dt>
              <dd class="mt2">
                <dd >
                <input class="subBtn" value="重置密码"  id="send" type="submit">
              </dd>
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