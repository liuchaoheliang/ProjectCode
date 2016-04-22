<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通-返利积分首页</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/web/css/exchangelist.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/rightimg.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/pager.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/list.js"></script>
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
  
<!--列表开始-->
  <div class="exchange fl">
  	<div class="list">
  	 <#list pager.list as goodsExchangeRack>
      <li>
      <#if goodsExchangeRack.goods?exists> 
      
      <!--交易平台    图片   开始-->
      	<#if goodsExchangeRack.goods.goodsCategory.name=="彩票">
      	 <a href="exchange_lottery_page.action?pager.goodsCategoryId=#{goodsExchangeRack.goods.goodsCategory.id?if_exists}"><img src="${base}${goodsExchangeRack.goods.sourceUrl?if_exists}"></a>
      	</#if>
      	<#if goodsExchangeRack.goods.goodsCategory.name=="话费充值">
      	 <a href="exchange_recharge_phone_page.action?pager.goods.goodsCategoryId=#{goodsExchangeRack.goods.goodsCategory.id?if_exists}"><img src="${base}${goodsExchangeRack.goods.sourceUrl?if_exists}"></a>
      	</#if>
      	<#if goodsExchangeRack.goods.goodsCategory.name=="积分提现">
      	 <a href="carry_index.action?pager.goodsCategoryId=#{goodsExchangeRack.goods.goodsCategory.id?if_exists}"><img src="${base}${goodsExchangeRack.goods.sourceUrl?if_exists}"></a>
      	</#if>
      	<#if goodsExchangeRack.goods.goodsCategory.name=="农特产品">
         <a href="local_goods_exch_detail.action?pager.goodsId=${goodsExchangeRack.goodsId}"><img src="${base}${goodsExchangeRack.goods.sourceUrl?if_exists}"></a>
      	</#if>
      	 <!--交易平台    图片   开始-->
      	 
       	<div class="hotTitle">${goodsExchangeRack.goods.goodsName?if_exists}</div>
        <div class="boxInfor">${goodsExchangeRack.goods.remark?if_exists}</div>
       </#if>
        <!-- 价格开始-->
       <#if goodsExchangeRack.isFftPoint=="1" >
        <#assign fftpoints=goodsExchangeRack.fftPointPricing>
       	<span>分分通积分：<B>${fftpoints?if_exists}</B>分 </span>
       </#if>
       
        <#if goodsExchangeRack.isBankPoint=="1" >
        <#assign bankPoint=goodsExchangeRack.bankPointPricing>
       	<span>银行积分：<B>${bankPoint?if_exists}</B>分 </span>
       </#if>
      
       <#if goodsExchangeRack.isFftpointCash=="1" >${goodsExchangeRack.fftpointCashPricing?if_exists}
        <#assign fftpointsAndCash=goodsExchangeRack.fftpointCashPricing>
        <#assign fftpointsAndCashArray=fftpointsAndCash?split('|')>
       	<span>分分通积分：<B>${fftpointsAndCashArray[0]?if_exists}</B>分 + <B>${fftpointsAndCashArray[1]?if_exists}</B>RMB</span>
       </#if>
       
       <#if goodsExchangeRack.isBankpointCash=="1" >
        <#assign bankpointsAndCash=goodsExchangeRack.bankpointCashPricing>
        <#assign bankpointsAndCashArray=bankpointsAndCash?split('|')>
        <span>银行积分：<B>${bankpointsAndCashArray[0]?if_exists}</B>分 + <B>${bankpointsAndCashArray[1]?if_exists}</B>RMB</span>
       </#if>
       
       <#if goodsExchangeRack.goods.goodsCategory.name=="积分提现" && goodsExchangeRack.isCash=="1" >
        <span>分分通积分：<B>1分 可以提现  <B>${goodsExchangeRack.cashPricing?if_exists}</B>RMB</span>
       </#if>
        <!-- 价格结束-->
        
        <!-- 积分兑换 详情 的图片链接  开始-->
        <div class="btnImg">
	      	<#if goodsExchangeRack.goods.goodsCategory.name=="彩票">
	      	 <a href="exchange_lottery_page.action?pager.goodsCategoryId=#{goodsExchangeRack.goods.goodsCategory.id?if_exists}"><img src="${base}/template/web/images/ljdh.png"></a>
	      	</#if>
	      	
	      	<#if goodsExchangeRack.goods.goodsCategory.name=="话费充值">
	      	 <a href="exchange_recharge_phone_page.action?pager.goods.goodsCategoryId=#{goodsExchangeRack.goods.goodsCategory.id?if_exists}"><img src="${base}/template/web/images/ljdh.png"></a>
	      	</#if>
	      	<#if goodsExchangeRack.goods.goodsCategory.name=="积分提现">
	      	 <a href="carry_index.action?pager.goodsCategoryId=#{goodsExchangeRack.goods.goodsCategory.id?if_exists}"><img src="${base}/template/web/images/ljdh.png"></a>
	      	</#if>
	      	<#if goodsExchangeRack.goods.goodsCategory.name=="农特产品">
	         <a href="local_goods_exch_detail.action?pager.goodsId=${goodsExchangeRack.goodsId}"><img src="${base}/template/web/images/ljdh.png"></a>
	      </#if>
      	</div>
      	 <!-- 积分兑换 详情 的图片链接  结束 -->
      	
      </li>   
 	</#list>   
     </div>    
      <div class="page">
         <#if (pager.list?size > 0)>
         	<#include "/WEB-INF/templates/common/pager.ftl" />
       	<#else>
         		没有找到你想要的商家信息!	
        </#if>     
      </div>                                
  </div> 
<!--列表结束-->
<!--列表结束-->

<#include "/WEB-INF/templates/common/exch_right.ftl">                     
</div>
  
<!--清除浮动-->
<div class="clear"></div>
<!--清除浮动-->

<!--底部开始-->  
<#include "/WEB-INF/templates/common/footer.ftl">
</body>
</html>
