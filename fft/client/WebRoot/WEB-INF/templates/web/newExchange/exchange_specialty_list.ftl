<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通-农特产品积分兑换</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/web/css/banner.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/exchangelist.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/seller.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/rightimg.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/pager.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/list.js"></script>
<link href="${base}/template/common/css/select3.css" rel="stylesheet" type="text/css" />
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
<!--幻灯片开始-->
  <#include "/WEB-INF/templates/common/exchange_advert.ftl">
<!--幻灯片结束-->  

<!--搜索开始-->
  <div class="search fl ml10">
  <dl>
    <dt><i>商家</i>搜索：</dt>
  </dl>
    <li><b>商圈：</b>
      <span class="fl" id="uboxstyle">
        <select name="selectTagDistrictBId" id="selectTagDistrictBId" class="selectFrom">
          <option value="">所有商圈</option>
	        <#list allTagDistrictBList as tagDistrictB>
	        	<option value="${tagDistrictB.id}">${tagDistrictB.tagValue}</option>
	        </#list>
        </select>
      </span>
    </li>

    <li><b>分类：</b>
      <span class="fl" id="uboxstyle">
        <select name="selectTagClassifyAId" id="selectTagClassifyAId" class="selectFrom">
          <option value="">所有分类</option>
          <#list allTagClassifyAList as tagClassifyA>
        	<option value="${tagClassifyA.id}">${tagClassifyA.tagValue}</option>
        </#list>
        </select>
      </span>
    </li>  
    
    <li><div class="searchGlass"></div><a href="javascript:searchMerchantIndex(2);"><img src="${base}/template/web/images/SchBtn.png"></a></li>    
  </div>
<!--搜索结束-->
</div>  

<div class="box1010 pt10 clear">
<form id="listForm" action="exchange_specialty_list.action" method="post">
<!--列表开始-->
<!--列表开始-->
  <div class="exchange fl">
  	<div class="local">本商品在购买成功之后，系统会为您发送短信消费凭证，请至商家处验证后进行消费。</div>
  	<div class="list"  id="ashowlist">
  	<#list pager.list as goodsExchangeRack>
	  	<a href="exchange_specialty_info.action?id=${goodsExchangeRack.id?c}">
	      <li>
	      <dl>
	        <dt class="shower">
	        	<#if goodsExchangeRack.isCash=='1'>
	        		<p>现金：<b>${goodsExchangeRack.cashPricing!"0"}</b>元</p>
	        	</#if>
	        	
	        	<#if goodsExchangeRack.isFftPoint=='1'>
	        		<p>分分通积分：<b>${goodsExchangeRack.fftPointPricing!"0"}</b>分</p>
	        	</#if>
	        	
	        	<#if goodsExchangeRack.isBankPoint=='1'>
	        		<p>银行积分：<b>${goodsExchangeRack.bankPointPricing!"0"}</b>分</p>
	        	</#if>
	        	
	        	<#if goodsExchangeRack.isFftpointCash=='1'>
	        		 <#assign fftpointsAndCashArray=goodsExchangeRack.fftpointCashPricing?split('|')>
	        		 <p>分分通积分：<b>${fftpointsAndCashArray[0]}</b>分+现金<b>${fftpointsAndCashArray[1]}</b>元</p>
	        	</#if>
	        	
	        	<#if goodsExchangeRack.isBankpointCash=='1'>
	        		<#assign bankpointsAndCashArray=goodsExchangeRack.bankpointCashPricing?split('|')>
	        		<p>银行积分：<b>${bankpointsAndCashArray[0]}</b>分+现金<b>${bankpointsAndCashArray[1]}</b>元</p>
	        	</#if>
	        	
	        </dt>
	        </dl>
	        
	        <#if (goodsExchangeRack.goodsExchangeRackImages?size>0)>
	        	<#assign ger=goodsExchangeRack.goodsExchangeRackImages[0]>
	        	<img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(ger.imagesUrl)!""}" onError="this.src='${base}/template/common/images/moren.gif'">
	        	<!--
	        	<#list goodsExchangeRack.goodsExchangeRackImages as goodsExchangeRackImages>
	        		<#if goodsExchangeRackImages_index gt 0>
	        			<#break>
	        			<img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(goodsExchangeRackImages.imagesUrl)!""}"">
	        		</#if>
	        	</#list>
	        	-->
	        <#else>
       	 		<img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${goodsExchangeRack.goods.sourceUrl?if_exists}" onError="this.src='${base}/template/common/images/moren.gif'">
       		</#if> 
	        
	        <div class="hotTitle">${goodsExchangeRack.goodsRackName!""}</div>
	        <div class="boxInfor">${goodsExchangeRack.goodsRackDesc!""}</div>
	        <div class="btnImg"><img src="${base}/template/web/images/ljdh.png"></div>
	      </li> 
	     </a>
  	</#list>
    </div>    
    <div class="page">
       <#if (pager.list?size > 0)>
         	<#include "/WEB-INF/templates/common/pager.ftl" />
       <#else>
       	 	没有找到你想要的商品!	
       </#if>     
    </div>
    <script src="${base}/template/web/js/pointshow2.js"></script>                               
  </div> 
  </form> 
<!--列表结束-->

  <#include "/WEB-INF/templates/common/exch_right.ftl">  
                  
</div>

<script type="text/javascript" src="${base}/template/common/js/select.js"></script>

<!--底部开始-->  
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
</body>
</html>
