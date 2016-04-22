<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>购买排行榜</title>
<link rel="icon" href="${base}/favicon.ico" type="image/x-icon" />
<#include "/include/sources.ftl">
<link href="${base}/resources/common/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/common/css/public.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/common/css/ranking.css" rel="stylesheet" type="text/css" />
</head>
<body>
<#-- 头部 开始-->
<#include "/include/common/header.ftl">
<#-- 头部 结束-->
<!--排行开始-->
<div class="rank">
  <div class="rank01"></div>
  <div class="rank02"></div>
  <div class="rank03"></div>
  <div class="rank04"></div>
   <a name="money"></a>
  <div class="rank05"></div>
  <div class="rank06">
    <ul>
       <#list moneyBoard as map>
      		<li>
      			<#if (map_index+1)==1>
      				<span class="red">
      			<#elseif (map_index+1)==2||(map_index+1)==3>
      				<span class="orange">
      			<#else>
      				<span>
      			</#if>
      		${map_index+1}</span><b><#if map.get('mobile')==null>${map.get('memberCode')?substring(0,3)}xxxx${map.get('memberCode')?substring(7)}<#else>${map.get('mobile')?substring(0,3)}xxxx${map.get('mobile')?substring(7)}</#if></b><u>购买金额：<p>${map.get('totalPrice')}</p>元</u></li>
      </#list>
    </ul>
  </div>
  <div class="rank07"></div>
  <div class="rank08">
  	<a name="time"></a>
    <ul>
    <#list timeBoard as map>
      	<li>
		 <#if (map_index+1)==1>
      				<span class="red">
      			<#elseif (map_index+1)==2||(map_index+1)==3>
      				<span class="orange">
      			<#else>
      				<span>
      	  </#if>
		${map_index+1}</span><b><#if map.get('mobile')==null>${map.get('memberCode')?substring(0,3)}xxxx${map.get('memberCode')?substring(7)}<#else>${map.get('mobile')?substring(0,3)}xxxx${map.get('mobile')?substring(7)}</#if></b><u>购买时间：<p>${map.get('orderTime')?string('MM月dd日')}</p>${map.get('orderTime')?string('HH:mm')}</u></li>
      </#list>
    </ul> 
  
  </div>
  <div class="rank09"></div>
</div>
<!--排行结束-->
<#-- 底部 -->
<#include "/include/common/footer.ftl">
<#-- 底部 -->

</body>
</html>