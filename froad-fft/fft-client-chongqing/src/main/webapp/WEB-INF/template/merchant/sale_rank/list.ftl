<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>提货排行榜</title>
<link rel="icon" href="${base}/favicon.ico" type="image/x-icon" />
<#include "/include/sources.ftl">
<link href="${base}/resources/merchant/css/merchant.css" rel="stylesheet" type="text/css" />
</head>
<body>
<#-- 头部 开始-->
<#include "/include/common/header.ftl">
<#-- 头部 结束-->

<div class="main">
  <div class="middleBox"> 
    <!--左侧导航开始-->
    <#include "/include/merchant/menu.ftl">
    <!--左侧导航结束-->
    <div class="rightBox" id="rightHeight">     
	  <div class="clear pt15">
        <table width="99%" border="0" cellspacing="0" cellpadding="0" class="tableA">
          <tr>
            <th width="10%">排行名次</th>
            <th width="10%">网点名称</th>
            <th width="10%">提货总金额 </th>
            <th width="10%">提货商品总量</th>
          </tr>
          <#assign order=0>
		  <#if rankList?exists && rankList?size gt 0 >
		  	<#list rankList as list >
		  	 <#assign order=order+1>
		  	 	
				  <tr <#if outletId?exists && outletId==list.outLetId>class="redFont"</#if>>
		          	<td>${order}</td>
		          	<td>${list.outletName}</td>
		          	<td>${currency(list.price!"0",false,true)}</td>
		            <td>${list.quantity}件</td>
		          </tr>
		  	</#list>
		  <#else>
		  	<tr>
		  		<td colspan="4" style="border:solid #ffffff 1px;">没有记录!</td>
		  	</tr>
		  </#if>
        </table>
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