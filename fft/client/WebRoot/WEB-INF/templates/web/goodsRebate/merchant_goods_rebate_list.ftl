<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/web/css/seller.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/productlist.css" rel="stylesheet" type="text/css" />
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
  <div class="productList fl">
    
    <div class="list">
     <#list pager.list as goodRebateRack>
		<#if goodRebateRack_index gt 13 >
       		<#break>
       	</#if>	       	
		<li>
	       <a href="merchant_goods_rebate_info.action?id=${(goodRebateRack.goodsId)!""}"><img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(goodRebateRack.goods.sourceUrl)!""}" onError="this.src='${base}/template/common/images/moren.gif'"></a>
			<div class="hotTitle"><a href="merchant_goods_rebate_info.action?id=${(goodRebateRack.goodsId)!""}">
				<#if goodRebateRack.goods?exists>
					${(goodRebateRack.goods.goodsName)!""}
	           </#if>
			</a></div>
	       <span>返利积分：<B>${(goodsRebateRack.rebateValue)!"0"}</B></span>
	    </li>
	</#list>     
    </div> 
    
    <div class="page">
    	 <#if (pager.list?size > 0)>
         	<#include "/WEB-INF/templates/common/pager.ftl" />
       	<#else>
         		暂时没有上架的返利商品信息!	
        </#if>  
    </div>
                               
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
