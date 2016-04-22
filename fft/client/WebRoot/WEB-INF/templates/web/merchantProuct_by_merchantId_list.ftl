<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商品列表</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/web/css/seller.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/productlist.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/pager.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/list.js"></script>
<script type="text/javascript" src="${base}/template/common/js/borderUI.js"></script>
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
<!--头部开始-->
<#include "/WEB-INF/templates/common/header.ftl">
<!--头部结束-->

<!--导航开始-->
<#include "/WEB-INF/templates/common/menu.ftl">
<!--导航结束-->

<div class="box1010 pt10 clear">
<!--列表开始-->
  <div class="productList fl">
	<form id="listForm" action="merchantProuct_by_merchantId_list.action" method="post">
   <input name="id" value="${(merchant.id?c)!""}" type="hidden">   
    <div class="list">
     <#list pager.list as merchantProduct>		     	
		<li>
	       <a href="merchant_product_info.action?id=${merchantProduct.id?c}"><img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(merchantProduct.photoUrl)!""}" onError="this.src='${base}/template/common/images/moren.gif'"></a>
			<div class="hotTitle"><a href="merchant_info.action?id=${(merchant.id?c)!""}">${(merchantProduct.txt1?html)!""}</a></div>
			<#if merchant?exists>
				<#if merchant.preferentialType == "1">
					
					<#if merchantProduct.price != "">
						<span>价格：${(merchantProduct.price)!"0"}元<B> &nbsp;&nbsp;
					<#else>
						<span><B>
					</#if>
					直接优惠：${(merchantProduct.preferentialRate)!"0"}折</B></span>
		    	<#else>
		    		<#if merchantProduct.price != "">
						<span>价格：${(merchantProduct.price)!"0"}元<B> &nbsp;&nbsp;
					<#else>
						<span><B>
					</#if>
		    		返利积分：${(merchantProduct.pointRate)!"0"}%</B></span>
		    	</#if>
		    </#if>
	    </li>
	</#list>     
    </div> 
    
    <div class="page">
    	 <#if (pager.list?size > 0)>
         	<#include "/WEB-INF/templates/common/pager.ftl" />
       	<#else>
         		没有找到商品信息!	
        </#if>  
    </div>
 </form>                              
  </div> 
<!--列表结束-->
<!-- 热门商户和最新商户开始   -->
<#include "/WEB-INF/templates/common/hotAndNew_merchant.ftl">
<!-- 热门商户和最新商户结束     -->     
</div>

<!--底部开始-->
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束--> 
</body>
</html>
