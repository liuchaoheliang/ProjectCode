<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通-直接优惠首页</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/web/css/banner.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/imglist.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/seller.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/pager.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/list.js"></script>
<script type="text/javascript" src="${base}/template/common/js/borderUI.js"></script>
<link href="${base}/template/web/css/exchangelist.css" rel="stylesheet" type="text/css" />

<link href="${base}/template/common/css/select3.css" rel="stylesheet" type="text/css" />

</head>
<script type="text/javascript">
$(function(){
	$('.list').borderUI();
})
</script>
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


<!--幻灯片开始-->
  <#include "/WEB-INF/templates/common/preferential_advert.ftl">
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
	        	<option value="${tagDistrictB.id?c}" <#if merchantId?? && merchantId == tagDistrictB.id?c>selected="selected"</#if> >${(tagDistrictB.tagValue)!""}</option>
	        </#list>
        </select>
      </span>
    </li>

    <li><b>分类：</b>
      <span class="fl" id="uboxstyle">
        <select name="selectTagClassifyAId" id="selectTagClassifyAId" class="selectFrom">
          <option value="">所有分类</option>
          <#list allTagClassifyAList as tagClassifyA>
        	<option value="${tagClassifyA.id?c}" <#if sortId?? && sortId == tagClassifyA.id?c>selected="selected"</#if> >${(tagClassifyA.tagValue)!""}</option>
        </#list>
        </select>
      </span>
    </li>  
    
    <li><div class="searchGlass"></div><a href="javascript:searchMerchantIndex(1);"><img src="${base}/template/web/images/SchBtn.png"></a></li>    
  </div>
<!--搜索结束-->
</div>  

<div class="box1010 pt10 clear">
<!--列表开始-->
<form id="listForm" action="preferential_index.action" method="post">
	<input type="hidden" name="pager.tagValue" value="${(pager.tagValue)!""}"/>
	  <input type="hidden" name="pager.merchantPriority" value="${(pager.merchantPriority)!""}"/>
	  <input type="hidden" name="pager.preferentialType" value="${(pager.preferentialType)!""}"/>
	  <input type="hidden" name="pager.tagClassifyAId" value="${(pager.tagClassifyAId)!""}"/>
	  <input type="hidden" name="pager.tagDistrictBId" value="${(pager.tagDistrictBId)!""}"/>
  <div class="imgList fl">
  
  	<div class="local">本板块直接优惠服务仅限使用珠海农商银行卡刷卡消费时可享优惠。</div>
  	<div class="list">
   	<#list pager.list as merchant>
		<!--
		<#if merchant_index gt 11 >
       		<#break>
       	</#if>
       	-->	       	
		<li>
	       <a href="preferential_info.action?id=${(merchant.id)?c}" target="_blank"><img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${merchant.mstoreLogoUrl!""}" onError="this.src='${base}/template/common/images/moren.gif'"></a>
			<div class="hotTitle"><a href="merchant_info.action?id=${(merchant.id)?c}" target="_blank" >${(merchant.mstoreFullName?html)!""}</a></div>
	       <span>标签：
	       <#list merchant.tagClassifyAList as tagClassifyA>
	       	<a href="search_preferential_merchants.action?pager.tagClassifyAId=${tagClassifyA.id?c}">${tagClassifyA.tagValue!""}</a>	                     	    	
           </#list> 
	<!--	   <#list merchant.tagClassifyBList as tagClassifyB>
	       	<a href="searchIndex.action?pager.tagClassifyBId=${tagClassifyB.id}&pager.preferentialType=1">${tagClassifyB.tagValue}</a>
	       </#list> -->
	       <#list merchant.tagDistrictAList as tagDistrictA>
	       	<a href="search_preferential_merchants.action?pager.tagDistrictAId=${tagDistrictA.id?c}">${tagDistrictA.tagValue!""}</a>
	       </#list> 
	       <#list merchant.tagDistrictBList as tagDistrictB>
	       	<a href="search_preferential_merchants.action?pager.tagDistrictBId=${tagDistrictB.id?c}">${tagDistrictB.tagValue!""}</a>
	       </#list> 
	       </span>
	        <#if merchant?exists && merchant.preferentialType=="1">
	       		<span>直接优惠：<B><#if merchant?exists && merchant.preferentialRate?exists>${(merchant.preferentialRate)!""}<#else>--</#if>&nbsp;折</B></span>
	       	<#else>
	       		<span>返利积分：<B><#if merchant?exists && merchant.pointRate?exists>${(merchant.pointRate)!""}<#else>0</#if>&nbsp;%</B></span>
	       	</#if>      
	       <p>地址：${(merchant.mstoreAddress)!""}</p>
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
</from>                               
  </div> 
<!--列表结束-->

<!-- 热门商户和最新商户开始  -->
<#include "/WEB-INF/templates/common/hotAndNew_merchant.ftl">
</div>
<!-- 热门商户和最新商户结束  -->                    
 <script type="text/javascript" src="${base}/template/common/js/select.js"></script>
  
<!--底部开始-->  
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->  
</body>
</html>
