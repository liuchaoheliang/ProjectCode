<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<link rel="icon" href="${base}/favicon.ico" type="image/x-icon" />
<title>密码修改</title>
<#include "/include/sources.ftl">
<link href="${base}/resources/member/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$(document).ready(function (){
	var msg = "${msg}";
	if(msg.length != 0){
		layer.msg(msg,2,-1);
	}
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
        <h2 class="pl50">提示：<font class="f13 grayFont">为了您的积分安全，更改密码需要先验证登录密码</font></h2>
        <form id="oldpwd" action="${base}/member/password/new_password.jhtml" method="post">
        <div class="inforBox validate psw" id="formlist">
          <dl>
            <dt>原手机号：</dt>
            <dd>
              <div><strong>${mobile}</strong></div>
            </dd>
          </dl>
          <dl>
            <dt>登录密码：</dt>
            <dd>
              <div><strong>
                <input type="password" name="password"  class="loginText" id="password4">
                <u>*</u></strong></div>
            </dd>
          </dl>
             <div class="ml150 mt5">
             	<input class="subBtn" value="&nbsp;&nbsp;下一步&nbsp;&nbsp;" onclick='javascript:$("#oldpwd").submit();' type="button">
             </div>
        </div>
         </form>
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