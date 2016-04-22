<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>个人中心</title>
<link rel="icon" href="${base}/favicon.ico" type="image/x-icon" />
<#include "/include/sources.ftl">
<link href="${base}/resources/member/css/admin.css" rel="stylesheet" type="text/css" />
<script src="${base}/resources/common/plugin/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
window.location.href = '${base}/member/info/index.jhtml';
</script>
</head>
<body>
<#-- 头部 开始-->
<#include "/include/common/header.ftl">
<#-- 头部 结束-->

<div class="main">
  <div class="middleBox"> 
    
    <#-- 公共菜单  开始 -->
    <#include "/include/member/menu.ftl">
    <#-- 公共菜单  结束 -->
    
    <div class="rightBox" id="rightHeight">
    
    </div>
  </div>
  
  <!--清除浮动-->
  <div class="clear"></div>
  <!--清除浮动--> 
</div>

<#-- 底部 -->
<#include "/include/common/footer.ftl">
<#-- 底部 -->
</body>
</html>