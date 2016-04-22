<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>手机号修改</title>
<link rel="icon" href="${base}/favicon.ico" type="image/x-icon" />
<#include "/include/sources.ftl">
<link href="${base}/resources/member/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/common/css/login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$(document).ready(function (){
	<#-- 短信验证码相关 开始-->
	$('#getcode').click(getCode);
	function getCode(){
		$('#phonecode').val('');
		var phone = $.trim($("#phone").val());
		if(!regexp.phoneRE.test(phone)){
			layer.msg('请输入正确的手机号码',2,-1);
			return;
		}
		$.Ajax({
			url : settings.baseUrl + '/common/send_code_sms.jhtml',
			datas : {'smsType':'authcodeModifiedMobile','toPhoneNumber':phone},
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
	}
	
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
	
	<#-- 短信验证码校验 -->
	$('#next').click(function (){
		var val = $.trim($('#phonecode').val());
		$.Ajax({
			url : settings.baseUrl + '/common/validate_code_sms.jhtml',
			datas : {'smsCode':val},
			successFn : function (data){
				if(data.flag){
					$.Ajax({
						url : '${base}/member/phone/update.jhtml',
						datas : {'phone':$('#phone').val()},
						successFn : function (data){
							if(data.flag){
								layer.msg(data.msg, 2, -1);
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
	});
	<#-- 短信验证码相关 结束-->
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

<div class="main">
  <div class="middleBox" > 
    <#-- 头部 结束-->
    <#include "/include/member/menu.ftl">  
    <#-- 头部 结束-->
    
    <div class="rightBox" id="rightHeight">
      <div class="clear pt5 mt35 mb30">
        <div class="inforBox validate psw regeditLeft" id="formlist">
          <dl>
            <dt>请输入新手机号：</dt>
            <dd>
            	<form id="update" action="${base}/member/phone/update.jhtml" method="post">
              <div><strong><input type="text" name="textfield"  class="loginText" id="phone" maxlength="11"></strong></div>
              	</form>
            </dd>
          </dl>
          <dl>
            <dt>手机验证码：</dt>
            <dd>
              <div><strong>
               <input  type="text" class="loginText3" id="phonecode" maxlength="4">
                 <input type="button" id="getcode" class="getCode" value="获取手机验证码"/>
              </strong></div>
            </dd>
          </dl>
          <div class="ml150 mt5">
            <input class="subBtn" id="next" value="确认修改" type="button">
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<!--清除浮动-->
<div class="clear"></div>
<!--清除浮动--> 

<#-- 底部 -->
<#include "/include/common/footer.ftl">
<#-- 底部 -->
</body>
</html>
