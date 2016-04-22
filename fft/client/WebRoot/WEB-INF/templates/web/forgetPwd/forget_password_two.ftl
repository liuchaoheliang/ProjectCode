<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>找回密码</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/web/css/find.css" rel="stylesheet" type="text/css" />
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

<div class="box1010 pt10 clear">
  
  <div class="find fl h400">
 
    <div class="stepfind"></div>
    <div class="step" id="find">
      <li class="step01">输入信息</li>
      <li class="colorRed  step08">找回成功</li>
    </div>  
    
      <div class="messageBox">
        <dl>
        <dt><#if message?exists>${message!""}<#else></#if></dt>
          <dd><a href="${base}/toLogin.action">重新登录</a></dd>
        </dl>  
      </div>

  </div> 
<!--充值结束-->
                 
</div>

<!--底部开始-->
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
</div>
</body>
</html>
