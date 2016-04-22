<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>注册</title>
<link rel="icon" href="${base}/favicon.ico" type="image/x-icon" />
<#include "/include/sources.ftl">
<link href="${base}/resources/common/css/validate.css" rel="stylesheet" type="text/css" />
<script src="${base}/resources/common/js/jquery.validate.js"></script>
<link href="${base}/resources/common/css/login.css" rel="stylesheet" type="text/css" />
<script src="${base}/resources/common/js/password.js"></script>
<style>
	.xuboxPageHtml{
		font-size:12px;
		padding:10px;
		line-height:20px;
		overflow-y:scroll;
		height:430px;
	}
	.getCode{
	    border-radius:5px;
	    height:26px;
	    width: 115px!important;
	}
</style>
<script type="text/javascript">
	jQuery.validator.addMethod("admin", function(value, element, param) {
	     var val = value.replace(/[\u4E00-\u9FA3]/g, '01');
	  return /^[0-9a-zA-Z_\\-]{4,20}$/.test(val);   
	}, $.validator.format("4-20英文、数字、汉字、-、_"));
</script>
<!--验证配置--> 
<script type="text/javascript">
$(document).ready(function(){
	$("#commentForm").validate({
		rules: {			
			loginID: {
				required: true,
				minlength: 2,
				admin: true
			},
			password: {
				required: true,
				minlength: 6,
				pattern:/^[^\u4e00-\u9fa5]{0,}$/
			},
			loginPwd: {
				required: true,
				minlength: 6,
				pattern: /^[^\u4e00-\u9fa5]{0,}$/,
				equalTo: "#password"
			},			
			mobile: {
				required: true,
				minlength: 11,
				pattern: /^1[3458][0-9]{9}$/
			},
			phonecode: {
				required: true,
				minlength: 4,
				pattern:  /^[0-9]{4}$/
			},
			email: {
				pattern:/^\w+@\w+([-]\w+)*\.[a-z]+(\.[a-z]+)*$/
			},
			recommend:{
			
			},
			code: {
				required: true,
				minlength: 4,
				pattern: /^[\w]{4}$/
			}
		},
		
		messages: {
			loginID: {
				required: '请输入您的用户名',
				minlength: '用户名长度为4-20位'
			},

			password: {
				required: '请输入您的密码',
				minlength: '密码长度为6-16位',
				pattern: '密码不能为特殊字符'
			},
			
			loginPwd: {
				required: '请输入您的密码',
				minlength: '密码长度为6-16位',
				pattern: '密码不能为特殊字符',
				equalTo: '两次密码输入不一致'
			},			
			
			mobile: {
				required: '请输入您的手机号码',
				minlength: '手机号码为11位数字',	
				pattern: '手机号输入错误'	
			},

			phonecode: {
				required: '请输入您收到的手机验证码',
				minlength: '手机验证码为4位数字',	
				pattern: '手机验证码为4位数字'
			},

			email: {
				pattern: '邮箱格式不正确'			
			},
			
			code:{
				required: '请输入您的验证码',
				minlength: '验证码为4位数字',	
				pattern: '验证码为4位数字'	
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

<script type="text/javascript">
$(document).ready(function (){
	var randomCode = null;
	var codeTimer = null;
	<#-- 发送短信验证码  开始-->
	$('#getcode').click(getCode);
	function getCode(){
		$('#phonecode').val('');
		var phone = $.trim($("#phone").val());
		if(phone==""){
			layer.msg('请先输入手机号', 2, -1);
			return;
		}
		if(!regexp.phoneRE.test(phone)){
			layer.msg('手机号码输入错误', 2, -1);
			return;
		}
		$.Ajax({
			url : settings.baseUrl + '/common/send_code_sms.jhtml',
			datas : {'smsType':'authcodeRegister','toPhoneNumber':phone},
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
				ele.val('重发手机验证码');
				$('#getcode').bind('click',getCode);
			}else{
				ele.val(codeTimer + '秒后可以重发');
			}
		},1000);
	}
	
	<#--手机验证码change，移除验证通过样式-->
	$("#phonecode").keyup(function(){
		$(this).parent().find("input").removeClass("right");
		$(this).parent().find("span").remove("span");
	});

	<#-- 随机验证码相关 开始-->
	var updateCode = false;
	var captchaId;
	$('#updateCode').click(updateValidateCode);
	
	<#-- 更新验证码 -->
	function updateValidateCode(auto) {
		if(auto.length == 0){
			$('#code').val('');
		}
		captchaId = new Date().getTime();  
		updateCode = true;
	    $('#captcha').attr('src',settings.baseUrl + '/common/captcha.jhtml?captchaId=' + captchaId);
	}
	

	$('#send').click(function (){	
		var flag=$('#commentForm').valid();	
		if(flag){	
			//验证是否同意协议
			if(!$("#agree").is(':checked')){
				layer.msg("您未同意协议，不允许注册。" ,2 ,-1);
				return;
			}
			<#-- 短信验证码验证-->	
				var val = $.trim($('#phonecode').val());
				$.Ajax({
					url : settings.baseUrl + '/common/validate_code_sms.jhtml',
					datas : {'smsCode':val},
					successFn : function (data){
						if(data.flag){
							<#-- 随机验证码验证-->	
							var val = $.trim($('#code').val());
							var captId;
							if(updateCode){
								captId = captchaId;
							}else{
								captId = '${captchaId}';
							}
							$.Ajax({
								url : settings.baseUrl + '/common/validate_code.jhtml',
								useRandom : true,
								datas : {'captchaId' : captId , 'captcha' : val , 'captchaType' : 'memberRegister'},
								successFn : function (data1){
									if(data1.flag){
										$.Ajax({
											url : '${base}/shop/register/submit.jhtml',
											datas : $('#commentForm').serialize(),
											lockScreen : true,
											successFn: function (data2){
												if(data2.flag){
													layer.msg('恭喜，注册成功！', 2, -1);
													setInterval(function (){
															window.location.href = settings.lackLoginPowerRedirectUrl;
														},2000);
												}else{
													layer.msg(data2.msg, 2, -1);
												}
											}
										});	
									}else{
										layer.msg('随机验证码错误', 2, -1);
										updateValidateCode('true');
										$("#code").val("");
										$("#code").focus();										
										return ;
									}
								} 
							});
							
						}else{
							$("#phonecode").val("");
							$("#phonecode").focus();	
							layer.msg(data.msg, 2, -1);
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
	function agreementView(){
			$.Ajax({
								url : settings.baseUrl + '/shop/register/agreement.jhtml',
								successFn : function (data){
									$.layer({
													offset : ['20' , '50%'],
													area : ['1000' ,'500'],
													type : 1,
													title : "协议内容",
													page : {html :data.data}
											  });	
								}
				});
		}
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
									<#--删除验证成功的样式-->
									$("#phonecode").trigger("keyup");
									<#--添加错误的样式-->
									$("#phonecode").addClass("error");
								}
							}
						});	
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
  <div class="loginbg">注册</div>
  <div class="loginBox" >
    <div class="all">
      <div class="regeditBox">
        <div class="topReg"><B>已有帐号？请直接<a href="${base}/shop/login/index.jhtml">登录</a></B></div>
        <div class="regeditLeft validate" id="formlist">
          <form id="commentForm" action="${base}/shop/register/submit.jhtml" method="post" onsubmit="return false;">
           <dl class="mt10">
               <dt ><u>*</u>用户名：</dt>
              <dd>
                <div> <strong>
                  <input type="text" class="loginText" name="loginID" maxlength="20" id="username">
                   </strong> <em>请输入您的用户名</em></div>
              </dd>
            </dl>
            <dl>
               <dt><u>*</u>密码：</dt>
              <dd>
                <div> <strong>
                  <input type="password" class="loginText" id="password"  name="password" maxlength="16">
                  </strong><em>请输入您的密码</em> </div>
                  <div class="pw-strength pw-weak">
                                <div class="pw-letter">
									<span class="tsl" >弱</span>
									<span>中</span>
										<span >强</span>
								</div>
                            </div> 
              </dd>
            </dl>
            <dl>
               <dt><u>*</u>确认密码：</dt>
              <dd>
                <div> <strong>
                  <input type="password" name="loginPwd"   class="loginText" maxlength="16" id="password2">
                  </strong><em>请确认您的密码</em> </div>
              </dd>
            </dl>
            <dl>
              <dt><u>*</u>手机号码：</dt>
              <dd>
                <div> <strong>
                  <input name="mobile" type="text"  class="loginText" id="phone" maxlength="11">
                  </strong><em>请输入您的手机号码</em> </div>
              </dd>
            </dl>
            <dl>
              <dt>手机验证码：</dt>
              <dd>
                <div> <strong>
                  <input  type="text" class="loginText3" name="phonecode" id="phonecode" maxlength="4">          
                 <input  name="获取验证码"  type="button" id="getcode" class="getCode"  value="获取手机验证码"/></input>
                 </strong><em>请输入手机验证码</em> </div>
              </dd>
            </dl>
            <dl>
              <dt>邮箱：</dt>
              <dd>
                <div> <strong>
                  <input type="text" name="email"  class="loginText" id="email">
                  </strong> <em>请输入您的邮箱</em></div>
              </dd>
            </dl>
            <dl>
              <dt>推荐人：</dt>
              <dd>
                <div> <strong>
                  <input  type="text" class="loginText2" name="introducer" maxlength="20">
                  </strong><em>请输入推荐人帐号/邮箱/手机号</em> </div>
              </dd>
            </dl> 
            <dl class="mb10">
              <dt><u>*</u>验证码：</dt>
              <dd>
                <div><strong>
                  <input id="code" type="text" class="loginText4"  name="code" maxlength="4"/>
                  <img id="captcha" src="${base}/common/captcha.jhtml?captchaId=${captchaId}"  width="65" height="24" alt="" />
                  <a id="updateCode">看不清 换一张</a> <u>*</u> 
                  </strong><em>请输入验证码</em> </div>
              </dd>
            </dl>
             <dl><dt>&nbsp;</dt>
	             <dd>
	            	<input class="subBtn" value="同意协议并注册" type="submit" id="send" onsubmit='javascript:return false;' name="">
	            	<dd class="text">    
	            		<input name="" type="checkbox" id="agree"  class="checkBox" checked="checked"/>            
	            		<a href="javascript:;" id="agreement" onclick="agreementView()">《重庆农村商业银行服务条款》</a>
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
<#include "/include/common/footer.ftl" >
</body>
</html>