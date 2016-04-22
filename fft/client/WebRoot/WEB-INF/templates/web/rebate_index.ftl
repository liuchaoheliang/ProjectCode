<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通-返利积分首页</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/web/css/banner.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/${(sytleCssName)!"imglist"}.css" rel="stylesheet" type="text/css" id="link1" />
<link href="${base}/template/web/css/seller.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/select3.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/pager.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/borderUI.js" ></script>
<script type="text/javascript" src="${base}/template/common/js/list.js"></script>

</head>
<body>
<!--
* Author:
* pengling@f-road.com.cn 
*/
-->
<script type="text/javascript">
$(function(){
	$('.list').borderUI();
})
</script>
<script type="text/javascript">
$(function (){
	
	$(".smallmenu a:first").click(function(){
		$(this).addClass("a1click");
		$(".smallmenu a:last").removeClass("a2click")
		$("#link1").attr("href", "${base}/template/web/css/listimg.css");
		$("#styleCssName").val('listimg');	
	});
	
	$(".smallmenu a:last").click(function(){
		$(this).addClass("a2click");
		$(".smallmenu a:first").removeClass("a1click")
		$("#link1").attr("href", "${base}/template/web/css/imglist.css");	
		$("#styleCssName").val('imglist');	
	});
});


</script>
<!--头部开始-->
<#include "/WEB-INF/templates/common/header.ftl">
<!--头部结束-->

<!--导航开始-->
<#include "/WEB-INF/templates/common/menu.ftl">
<!--导航结束-->

<!--幻灯片开始-->
 <#include "/WEB-INF/templates/common/rebate_advert.ftl">
<!--幻灯片结束-->  

<!--搜索开始-->
  <div class="search fl ml10">
  <dl>
    <dt><i>商家</i>搜索：</dt>
  </dl>
    <li><b>商圈：</b>
    	<span class="fl" id="uboxstyle">
        <select name="selectTagDistrictBId" id="selectTagDistrictBId">
          <option value="">所有商圈</option>
	        <#list allTagDistrictBList as tagDistrictB>
	        	<option value="${tagDistrictB.id?c}" <#if merchantId?? && merchantId== tagDistrictB.id?c>selected="selected"</#if> >${(tagDistrictB.tagValue)!""}</option>
	        </#list>
        </select>
       </span>
    </li>

    <li><b>分类：</b>
    	<span class="fl" id="uboxstyle">
        <select name="selectTagClassifyAId" id="selectTagClassifyAId">
          <option value="">所有分类</option>
          <#list allTagClassifyAList as tagClassifyA>
        	<option value="${tagClassifyA.id?c}" <#if sortId?? && sortId == tagClassifyA.id?c>selected="selected"</#if>>${(tagClassifyA.tagValue)!""}</option>
          </#list>
        </select>
        </span>
    </li>  
    
    <li><div class="searchGlass"></div><a href="javascript:searchMerchantIndex(2);"><img src="${base}/template/web/images/SchBtn.png"></a></li>    
  </div>
<!--搜索结束-->
</div>  
<script type="text/javascript" src="${base}/template/common/js/select.js"></script>   
<div class="box1010 pt10 clear">
<!--列表开始-->
<form id="listForm" action="rebate_index.action" method="post">
<input type="text" name="sytleCssName" value="${(sytleCssName)!"imglist"}" id="styleCssName" style="display:none;" />
<input type="hidden" name="pager.tagValue" value="${(pager.tagValue)!""}"/>
	  <input type="hidden" name="pager.merchantPriority" value="${(pager.merchantPriority)!""}"/>
	  <input type="hidden" name="pager.preferentialType" value="${(pager.preferentialType)!""}"/>
	  <input type="hidden" name="pager.tagClassifyAId" value="${(pager.tagClassifyAId)!""}"/>
	  <input type="hidden" name="pager.tagDistrictBId" value="${(pager.tagDistrictBId)!""}"/>
  <div class="imgList fl">
  
    <div class="smallmenu">
  		<a class="a1" href="javascript:void(0)"></a>
		<a class="a2 a2click" href="javascript:void(0)"></a>
	  </div>
	 <div class="rebate_info">本板块商户为分分通平台合作商户，积分返利比例指客户至该商户消费时，报出手机号码，商户就会赠送您总消费金额中相应比例的积分，之后客户可用手机号登录本平台，将获赠的积分兑换为产品或提取现金。</div>
  	<div class="list">
   	<#list pager.list as merchant>
		<!--<#if merchant_index gt 11 >
       		<#break>
       	</#if>	
       	-->
       	       	
		<li>
		
	       <a href="merchant_info.action?id=${(merchant.id)?c}" target="_blank"><img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${merchant.mstoreLogoUrl!""}" onError="this.src='${base}/template/common/images/moren.gif'"></a>
	       <div class="listall">
			<div class="hotTitle"><a href="merchant_info.action?id=${(merchant.id)?c}" target="_blank"  ><#if merchant?exists && merchant.mstoreFullName?exists>${merchant.mstoreFullName?html!""}</#if></a></div>
	       <span>标签：
	       <#list merchant.tagClassifyAList as tagClassifyA>
	       	<a href="search_rebate_merchants.action?pager.tagClassifyAId=${tagClassifyA.id?c}"><#if tagClassifyA?exists && tagClassifyA.tagValue?exists>${tagClassifyA.tagValue?html!""}</#if></a>	                     	    	
           </#list> 
    <!--   <#list merchant.tagClassifyBList as tagClassifyB>
	       	<a href="searchIndex.action?pager.tagClassifyBId=${tagClassifyB.id}&pager.preferentialType=2">${tagClassifyB.tagValue}</a>
	       </#list> -->
	       <#list merchant.tagDistrictAList as tagDistrictA>
	       	<a href="search_rebate_merchants.action?pager.tagDistrictAId=${tagDistrictA.id?c}"><#if tagDistrictA?exists && tagDistrictA.tagValue?exists>${tagDistrictA.tagValue?html!""}</#if></a>
	       </#list> 
	       <#list merchant.tagDistrictBList as tagDistrictB>
	       	<a href="search_rebate_merchants.action?pager.tagDistrictBId=${tagDistrictB.id?c}"><#if tagDistrictB?exists && tagDistrictB.tagValue?exists>${tagDistrictB.tagValue?html!""}</#if></a>
	       </#list> 
	       </span>
	       <span>返利积分：<B><#if merchant?exists && merchant.pointRate?exists>${merchant.pointRate?html!""}</#if>&nbsp;%</B></span>
	       <p>地址：<#if merchant?exists && merchant.mstoreAddress?exists>${(merchant.mstoreAddress)!""}</#if></p>
	       </div>
	        <a href="merchant_info.action?id=${(merchant.id)?c}" target="_blank" class="enter"></a>
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
<!-- 热门商户和最新商户结束  -->                  
</div> 
<!--底部开始-->  
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
</body>
</html>
