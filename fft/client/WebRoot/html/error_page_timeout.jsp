<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${base}无效操作</title>
<c:set var="base" value="${pageContext.request.contextPath}"/>
<link href="${base}/template/common/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/public.css" rel="stylesheet" type="text/css" />

</head>
<body>
<!--
* Author:
* pengling@f-road.com.cn 
*/
-->
<!--头部开始-->
<div class="head100">
    <div class="head">
	    <div class="logo">
		    <a href="index.action">
		    	<img src="${base}/template/common/images/logo.png">
		    </a>
	    </div>
	    <div class="rightList">
	    
	      <!--<div class="leftmember">
	      	<!-- <a href="toLogin.action">登陆</a><span>|</span> 
	      	<a href="toResgiter.action">注册</a><span>|</span>
	        <a href="bankPoint_activate.action">激活积分</a><span>|</span>
	        <a href="merchant_apply_index.action">商户合作申请</a><span>|</span>
	        <a href="help_center.action">用户帮助</a><span>|</span>
	        <a href="app_download_index.action">手机版下载</a>
	      </div>	-->
	    
	     
	     <!--  <ul>
	        <li><a href="toLogin.action">登陆</a><span>|</span></li>
	        <li><a href="toResgiter.action">注册</a><span>|</span></li>
	        <li><a href="bankPoint_activate.action">激活积分</a><span>|</span></li>
	        <li><a href="merchant_apply_index.action">商户合作在线申请</a><span>|</span></li>
	        <li><a href="help_center.action">用户帮助</a><span>|</span></li>
	        <li><a href="app_download_index.action">App下载</a></li>
	      </ul>
	       -->
	     <!-- 
	      <dl>
	        <dd>您目前的金海洋/信通卡积分为：<B>20000</B> 分，分分通积分为：<B>18000</B> 分</dd>
	      </dl>
	       -->
	    </div>
  </div>
</div>
<!--头部结束-->

<!--导航开始-->
<div class="menu100">
  <div class="menu">
   <div class="leftLiMenu" id="menuAClick">
            <li><a href="index.action">首页</a></li>
            <li><a href="presell_index.action">精品预售</a></li>
            <li><a href="new_exchange_index.action">积分兑换</a></li>
            <li><a href="queryBeforeExch.action">积分提现</a></li>
            <li><a href="group_index.action">团购</a></li>
            <li><a href="rebate_index.action">积分返利</a></li>
            <li><a href="preferential_index.action">直接优惠</a></li>
   </div>
    <!-- 
    <div class="rightSearch"><input name="" type="text" class="headSearch"></div>
    <div class="rightBtn"><a href="#"><img src="template/common/images/sousuoBtn.png"></a></div>
   -->
  </div>
</div>
<script type="text/javascript" src="${base}/template/common/js/menu.js"></script>
<!--导航结束-->

<div class="box1010 pt10 clear">
  
<!--帮助开始-->
  <div class="onerror"><img src="${base}/template/common/images/wrongimg.png"></div> 
<!--帮助结束-->
                 
</div>
  
<!--清除浮动-->
<div class="clear"></div>
<!--清除浮动-->

<!--底部开始-->
<div class="foot mt10">
  <div class="footAdd">运营商：珠海农商银行 上海方付通商务服务有限公司 电话：96138 沪ICP备:08021520号-4 地址：珠海市香洲兴业路223号</div>
</div>
<!--底部结束-->
</body>
</html>