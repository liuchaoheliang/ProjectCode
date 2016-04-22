<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通-商户基本信息</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/infor.css" rel="stylesheet" type="text/css" />
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
<div class="middleBox mt10">
<!-- 商家管理菜单开始 -->
<#include "/WEB-INF/templates/common/merchant_manage_menu.ftl">
<!-- 商家管理菜单结束 -->

  <div class="rightBox" id="rightHeight">
    <div class="grayBox mt20 ml20 fl">
      <dl>
        <dt><B><#if merchant.mstoreFullName?exists>${(merchant.mstoreFullName?html)!""}</#if></B></dt>
        <dd>地址：<#if merchant.mstoreAddress?exists>${(merchant.mstoreAddress)!""}</#if></dd>
   <!--    <dd>标签：火锅 唐家湾 自助餐火锅</dd> --> 
        <dd>优惠：<#if merchant?exists && merchant.preferentialType=="1">折扣优惠<#else>积分返利</#if></dd>
        <dd>收藏人数：${(merchantTrain.collectes)!"0"} 点击人数：${(merchantTrain.clickes)!"0"}</dd>
        <dd>认证信息：<img src="${base}/template/admin/images/renzheng.png" width="131" height="29" /></dd>  
      </dl>        
    </div>
      
    <div class="grayBox mt20 ml20 fl">
      <dt><B>商家信息</B></dt>
      <dd>上次登录时间：${(user.lastTime)!""}</dd>
  <!--   <dd>登录次数：32</dd> --> 
      <dd>安全问候语：分分通</dd>
      <dd class="orangeColor">如果不是你的问候语，请马上离开</dd>
      <dd><a href="#">有何风险？</a> <a href="${base}/j_spring_security_logout">立即离开</a></dd>        
    </div>   
      
    <div class="grayBox mt20 ml20 fl">
      <dt><B>广告位运营情况</B></dt>
      <dd>地址：<#if merchant.mstoreAddress?exists>${(merchant.mstoreAddress)!""}</#if></dd>
   <!--  <dd>标签：火锅 唐家湾 自助餐火锅</dd> --> 
      <dd>优惠：<#if merchant?exists && merchant.preferentialType=="1">折扣优惠<#else>积分返利</#if></dd>
      <dd>收藏人数：${(merchantTrain.collectes)!"0"} 点击人数：${(merchantTrain.clickes)!"0"}</dd>      
    </div> 
      
    <div class="grayBox mt20 ml20 fl">
      <dt><B>热度与排名</B></dt>
      <dd>地址：<#if merchant.mstoreAddress?exists>${(merchant.mstoreAddress)!""}</#if></dd>
 <!--   <dd>标签：火锅 唐家湾 自助餐火锅</dd> -->  
      <dd>优惠：<#if merchant?exists && merchant.preferentialType=="1">折扣优惠<#else>积分返利</#if></dd>
      <dd>收藏人数：${(merchantTrain.collectes)!"0"} 点击人数：${(merchantTrain.clickes)!"0"}</dd>       
    </div>                
  </div>
</div>  
<script type="text/javascript" src="${base}/template/common/js/height.js"></script>
<!--底部开始-->
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->       
</body>
</html>
