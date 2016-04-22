<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<link rel="icon" href="${base}/favicon.ico" type="image/x-icon" />
<title>输入新密码</title>
<#include "/include/sources.ftl">
<link href="${base}/resources/member/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$(document).ready(function (){
	$('#updatepwd').click(function (){
		var newpwd = $.trim($('#newpwd').val());
		var email = $('#email');
		if(email == undefined){
			email = '';
		}else{
			email = $.trim(email.val()); 
		}
		var reg = /^[^\u4e00-\u9fa5]{0,}$/;
		if(newpwd.length <= 6||reg.test(newpwd)){
			$.Ajax({
				url : '${base}/member/password/update_pwd.jhtml',
				datas : {'newpwd':newpwd,'email':email},
				mustLogin : true,
				successFn : function(data){
					if(data.flag){
						layer.msg(data.msg, 2, -1);
						setInterval(function (){
							window.location.href = '${base}/sso/core/logout.jhtml';
						},2000);
					}else{
						layer.msg(data.msg, 2, -1);
					}
				}
			});
		}else{
			layer.msg('密码长度为6-16位,不能为特殊字符', 2, -1);
		}
	});
});
</script>
</head>
<body>
<#-- 头部 开始-->
<#include "/include/common/header.ftl">
<#-- 头部 结束-->

<div class="main">
  <div class="middleBox" > 
    <#-- 公共菜单  开始 -->
    <#include "/include/member/menu.ftl">
    <#-- 公共菜单  结束 -->
    
    <div class="rightBox" id="rightHeight">
      <div class="clear pt5 mt35 mb30">
        <div class="inforBox validate psw" id="formlist">
          <dl>
            <dt>用户名：</dt>
            <dd>
              <div><strong>${userEngineDto.loginID}</strong></div>
            </dd>
          </dl>
          <dl>
            <dt>新密码：</dt>
            <dd>
              <div><strong>
                <input type="password" name="textfield" class="loginText" maxlength="16" id="newpwd">
                <u>*</u></strong></div>
            </dd>
          </dl>
          <dl>
            <dt>原手机号：</dt>
            <dd>
              <div><strong>${userEngineDto.mobile}</strong></div>
            </dd>
          </dl>
          <#--<#if userEngineDto.email?exists && userEngineDto.email!=''>
	          <dl>
	            <dt>邮箱：</dt>
	            <dd>
	              <div><strong>
	                <input type="password" name="textfield"  class="loginText" id="email">
	                <u>*</u></strong></div>
	            </dd>
	          </dl>
          </#if>-->
            <div class="ml150 mt5"> 
            	<input class="subBtn" value="&nbsp;&nbsp;下一步&nbsp;&nbsp;" type="button"  id="updatepwd"> 
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