<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>页面加载失败</title>
<link rel="icon" href="${base}/favicon.ico" type="image/x-icon" />
<#include "/include/sources.ftl">
<link href="${base}/resources/common/css/common.css" rel="stylesheet" type="text/css" />
</head>
<body>
<#-- 头部 开始-->
<#include "/include/common/header.ftl">
<#-- 头部 结束-->

<div class="main">
<div class="nopage404">
<span>
<!--登录开始-->
<div class="main">
 <div class="nopage404"><span>${illMsg!'抱歉您访问的页面过期或者非法'}<br />
请点击<a href="${base}" class="back">这里</a>返回首页</a></span></div>
</div>

<#-- 底部 -->
<#include "/include/common/footer.ftl">
<#-- 底部 -->

</body>
</html>