<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>"欢乐金秋"中奖名单</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/web/ticket/ticket.css" rel="stylesheet" type="text/css" />
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


<!--活动开始-->
<table width="1083" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="${base}/template/web/ticket/00_01.jpg" width="1087" height="91" /></td>
  </tr>
  <tr>
    <td><img src="${base}/template/web/ticket/00_02.jpg" width="1087" height="76" /></td>
  </tr>
  <tr>
    <td><img src="${base}/template/web/ticket/00_03.jpg" width="1087" height="79" /></td>
  </tr>
  <tr>
    <td><img src="${base}/template/web/ticket/00_04.jpg" width="1087" height="97" /></td>
  </tr>
  <tr>
    <td><img src="${base}/template/web/ticket/00_05.jpg" width="1087" height="97" /></td>
  </tr>
  <tr>
    <td><img src="${base}/template/web/ticket/00_06.jpg" width="1087" height="115" /></td>
  </tr>
</table>
<div class="morekan">
<table  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <th width="90">姓名</th>
    <th width="172">珠海农商银行卡号</th>
    <th width="90">姓名</th>
    <th width="172">珠海农商银行卡号</th>
    <th width="90">姓名</th>
    <th width="172">珠海农商银行卡号</th>
    <th width="90">姓名</th>
    <th width="174">珠海农商银行卡号</th>
  </tr>
 
  <#assign  index = 0 />
  
  <#if infoList?exists && (infoList?size != 0)>
  		 <tr>
	  <#list infoList as list>	
  		<td>${(list.accountName)!""}</td>
		<td>${(list.identificationCard)!""}</td>					
	  <#assign index = index + 1 />
	  
	   <#if index == 4> 
		</tr>
		<tr>
	  </#if>
	  <#if index == 5>
	  	<#assign index = 1 />
	  </#if>
	  </#list> 
  <#else>
  	<tr>
  		<td colspan="8" >暂无中奖名单，敬请期待</td>
  	</tr>
  	
  </#if>
  </tr>
</table>
</div>
<!--活动结束-->
<!--清除浮动-->
<div class="clear"></div>
<!--清除浮动-->

<!--底部开始-->  
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
</body>
</html>
