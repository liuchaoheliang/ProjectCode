<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>自提点信息</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/web/css/product.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/groupright.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/branch.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/pager.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/list.js"></script>
<script type="text/javascript" src="${base}/template/web/js/collect_share.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.4"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.js"></script>
<link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.css" />

</style>

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

<div class="box1010 pt10">
 <form id="listForm" action="presell_deliver_list.action" method="post">
  <div class="fl">
    <div class="branch">
	    <#list pager.list as list>
	      <dl>
	        <dt title="${(list.preselldelivery.name)!"--"}">${(list.name)!"--"}</dt>
	        <dd title="${(list.address)!"--"}">地址：${(list.address)!"--"}</dd>
	        <dd title="${(list.telephone)!"--"}">电话：${(list.telephone)!"--"}</dd>
	      </dl>
	    </#list>
	    <div class="page">
	    <#if (pager.list?size > 0)>
	     	<#include "/WEB-INF/templates/common/pager.ftl" />
	   	<#else>
	     		抱歉，自提点信息没有找到!
	    </#if>     
		</div>   
     </div>
    	
	</div> 
  
    </form>
  
  <div class="fl ml10">
  <!--商户信息开始-->
  <div class="rightShanghu">
    <ol><B>商户信息</B></ol>
    <div class="showName">${(merchant.mstoreFullName?html)!""}</div>
    <div class="showInr">优惠：<#if merchant?? && merchant.preferentialType=="1">折扣优惠<#else>积分返利</#if></div>
    <div class="showInr" id="shareCount">收藏人数：${(merchantTrain.collectes)!"0"}</div>
    <div class="showInr">点击人数：${(merchantTrain.clickes)!"0"}</div>
	<div class="shoufen">
		<#if memberCollectPager?exists>
			<#if (memberCollectPager.list?size > 0)><!-- 已收藏 -->
				<div id="yishoucang">
					<a href="javascript:void(0);"><img src="${base}/template/web/images/yishoucang.png"></a>
					<a href="javascript:void(0);" onclick="javascript:ShowShareMerchant(${(merchant.id?c)!""});"><img src="${base}/template/web/images/fenxiang.png"></a>					
				</div>
				<!--<div id="weishoucang" style="display:none;" >
					<a href="javascript:void(0);" onclick="javascript:CollectionMerchant(${(merchant.id?c)!""});"><img src="${base}/template/web/images/shoucang.png"></a>
					<a href="javascript:void(0);" onclick="javascript:ShowShareMerchant(${(merchant.id?c)!""});"><img src="${base}/template/web/images/fenxiang.png"></a>
				</div>-->
			<#else><!-- 未收藏 -->
				<div id="yishoucang" style="display:none;">
					<a href="javascript:void(0);"><img src="${base}/template/web/images/yishoucang.png"></a>
					<a href="javascript:void(0);" onclick="javascript:ShowShareMerchant(${(merchant.id?c)!""});"><img src="${base}/template/web/images/fenxiang.png"></a>
				</div>
				<div id="weishoucang">
					<a href="javascript:void(0);"  onclick="javascript:CollectionMerchant(${(merchant.id?c)!""});"><img src="${base}/template/web/images/shoucang.png"></a>
					<a href="javascript:void(0);" onclick="javascript:ShowShareMerchant(${(merchant.id?c)!""});"><img src="${base}/template/web/images/fenxiang.png"></a>					
				</div>
			</#if>
		</#if>		
	</div>
  </div>
  <!--商户信息结束-->  
  
     <!--相关精品预售开始-->
   <#if presellRackList?exists && (presellRackList?size > 0)>
    <div class="also mt10">
    
      <dl>
      	<dt>
      		<b>商户精品预售</b>
      	</dt>
	      	<#list presellRackList as list>
		        <dd>
		          <a href="presell_goods_detail_new.action?goodsPresellRack.id=${(list.id?c)!""}" target="_blank" >
		          <#if list.goodsPresellRackImages?exists && (list.goodsPresellRackImages?size > 0)>
		          	<img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(list.goodsPresellRackImages[0].imagesUrl)!""}" onError="this.src='${base}/template/common/images/moren.gif'">
		          <#else>
		          	<img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(list.goods.sourceUrl)!""}" onError="this.src='${base}/template/common/images/moren.gif'">
		          </#if>
		          </a>
		          <p><a href="presell_goods_detail_new.action?goodsPresellRack.id=${(list.id?c)!""}" target="_blank" >${(list.seoTitle)!"--"}</a></p>
		          <div><b>￥${(list.groupPrice)!"--"}</b> <span><b>${(list.trueBuyerNum)!""}</b>人已购买</span></div>
		        </dd>
	        </#list>
      </dl>      
    </div>
   </#if>


<!--相关团购开始-->
<#if goodsGroupRackList?exists && (goodsGroupRackList?size > 0)>
<div class="also mt10">

  <dl>
  	<dt>
  		<b>商户团购</b>
  	</dt>
   	<#list goodsGroupRackList as list>
      <dd>
        <a href="group_goods_detail_new.action?goodsGroupRack.id=${(list.id?c)!""}" target="_blank" >
        <#if list.goodsGroupRackImages?exists && (list.goodsGroupRackImages?size > 0)>
        	<img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(list.goodsGroupRackImages[0].imagesUrl)!""}" onError="this.src='${base}/template/common/images/moren.gif'">
        <#else>
        	<img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(list.goods.sourceUrl)!""}" onError="this.src='${base}/template/common/images/moren.gif'">
        </#if>
        </a>
        <p><a href="group_goods_detail_new.action?goodsGroupRack.id=${(list.id?c)!""}" target="_blank" >${(list.goods.goodsName)!"--"}</a></p>
        <div><b>￥${(list.groupPrice)!"--"}</b><u>原价：<i>${(list.goods.price)!"--"}</i></u> <span><b>${(list.marketTotalNumber)!""}</b>人已购买</span></div>
      </dd>
     </#list>
  </dl>
</div>
</#if>
  <!--相关团购结束-->
  
  <!--相关积分兑换开始-->
    <#if goodsExchangeRackList?exists && (goodsExchangeRackList?size > 0)>
    <div class="also mt10">
    
      <dl>
      	<dt>
      		<b>商户积分兑换</b>
      	</dt>
	      	<#list goodsExchangeRackList as list>
		        <dd>
		        <p>
		          <#if list.goodsCategoryId=="100001009">
			  		<!--本地商品-->
			  		<a href="exchange_local_info.action?id=${list.id?c}" target="_blank">
			  	 </#if>
			  	
			  	 <#if list.goodsCategoryId=="100001011">
			  		<!--珠海专区商品-->
			  		<a href="exchange_bankPoint_info.action?id=${list.id?c}" target="_blank">
			  	 </#if>
			  	
			  	<!--话费充值-->
			  	 <#if list.goodsCategoryId=="100001006">
			  		<a href="exchange_telephonefee_info.action?id=${goodsExchangeRack.list?c}" target="_blank">
			  	 </#if>
			  	
			  	<!--彩票-->
			  	 <#if list.goodsCategoryId=="100001005">
			  		<a href="exchange_lottery_info.action?id=${list.id?c}" target="_blank">
			  	 </#if>
			  	
			  	<!--农特产品-->
			  	 <#if list.goodsCategoryId=="100001003">
			  		<a href="exchange_specialty_info.action?id=${list.id?c}" target="_blank">
			  	 </#if>
			  	
			  	<!--1元现金-->
			  	 <#if list.goodsCategoryId=="100001007">
			  		<a href="#">
			  	 </#if>
		          <#if list.goodsExchangeRackImages?exists && (list.goodsExchangeRackImages?size > 0)>		          
		          	<img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(list.goodsExchangeRackImages[0].imagesUrl)!""}" onError="this.src='${base}/template/common/images/moren.gif'">
		          <#else>
		          	<img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(list.goods.sourceUrl)!""}" onError="this.src='${base}/template/common/images/moren.gif'">
		          </#if>
		          </a>
		          <p>
					<#if list.goodsCategoryId=="100001009">
			  		<!--本地商品-->
			  		<a href="exchange_local_info.action?id=${list.id?c}" target="_blank">
			  	 </#if>
			  	
			  	 <#if list.goodsCategoryId=="100001011">
			  		<!--珠海专区商品-->
			  		<a href="exchange_bankPoint_info.action?id=${list.id?c}" target="_blank">
			  	 </#if>
			  	
			  	<!--话费充值-->
			  	 <#if list.goodsCategoryId=="100001006">
			  		<a href="exchange_telephonefee_info.action?id=${goodsExchangeRack.list?c}" target="_blank">
			  	 </#if>
			  	
			  	<!--彩票-->
			  	 <#if list.goodsCategoryId=="100001005">
			  		<a href="exchange_lottery_info.action?id=${list.id?c}" target="_blank">
			  	 </#if>
			  	
			  	<!--农特产品-->
			  	 <#if list.goodsCategoryId=="100001003">
			  		<a href="exchange_specialty_info.action?id=${list.id?c}" target="_blank">
			  	 </#if>
			  	
			  	<!--1元现金-->
			  	 <#if list.goodsCategoryId=="100001007">
			  		<a href="#">
			  	 </#if>
					${(list.goods.goodsName)!"--"}</a></p>
		          <#if list.isFftPoint?exists && list.isFftPoint=='1' >
		          	<p><b>${(list.fftPointPricing)!""}</b>分分通积分</p>
		          <#elseif list.isBankPoint?exists && list.isBankPoint=='1' >
		          	<p><b>${(list.bankPointPricing)!""}</b>银行积分</p>
		          </#if>
		        </dd>
	        </#list>
      </dl>      
    </div>
   </#if>  
  <!--相关积分兑换结束-->            
  

  
  <!--广告开始> 
  <div class="mt10"><img src="img/ad01.png"></div>
  <!--广告结束--> 
  </div>
</div>  

<!--底部开始-->  
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->  
</body>
</html>
