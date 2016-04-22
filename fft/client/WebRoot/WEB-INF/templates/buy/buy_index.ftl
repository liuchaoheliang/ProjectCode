<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>团购商品列表</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/buy/css/group.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/buy/css/hot.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/buy/css/latelook.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/pager.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/buy/js/group_goods.js"></script>
<script type="text/javascript" src="${base}/template/common/js/list.js"></script>
<link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>
<script type="text/javascript" src="${base}/template/common/js/borderUI.js" ></script>

</head>
<body>
<input type="text"  id="notfind" style="display:none;" value="${notfind}" />
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
<script type="text/javascript">
$(function(){
	$('.list').borderUI();
})
</script>
<div class="box1010 pt10 clear">
  <!--团购开始-->
  <form id="listForm" action="group_index.action" method="post">
  <div class="group fl">
     <div class="list">       	
    <#list pager.list as goodsGroupRack>
		<#if goodsGroupRack_index gt 11 >
       		<#break>
       	</#if>	
		<li>
		    <#if goodsGroupRack.isFftPoint?exists && goodsGroupRack.isFftPoint=="1">
		    	<div class="icon"><img src="${base}/template/buy/images/icon.png"></div>	
		    <#elseif goodsGroupRack.isBankPoint?exists && goodsGroupRack.isBankPoint=="1">
		    	<div class="icon"><img src="${base}/template/buy/images/icon.png"></div>
		    <#elseif goodsGroupRack.isFftpointCash?exists && goodsGroupRack.isFftpointCash=="1">
		    	<div class="icon"><img src="${base}/template/buy/images/icon.png"></div>
		    <#elseif goodsGroupRack.isBankpointCash?exists && goodsGroupRack.isBankpointCash=="1">
		    	<div class="icon"><img src="${base}/template/buy/images/icon.png"></div>
		    <#elseif goodsGroupRack.isFftpointcashRatioPricing?exists && goodsGroupRack.isFftpointcashRatioPricing=="1">
		    	<div class="icon"><img src="${base}/template/buy/images/icon.png"></div>
		    <#elseif goodsGroupRack.isBankpointcashRatioPricing?exists && goodsGroupRack.isBankpointcashRatioPricing=="1">
		    	<div class="icon"><img src="${base}/template/buy/images/icon.png"></div>	    
		    </#if>
			<div class="imgShow">
			<a href="group_goods_detail_new.action?goodsGroupRack.id=${goodsGroupRack.id?c}">
				 <#if (goodsGroupRack.goodsGroupRackImages?size>0)>
		        	<#assign ger=goodsGroupRack.goodsGroupRackImages[0]>
		        	<img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(ger.imagesUrl)!""}" onError="this.src='${base}/template/common/images/moren.gif'">
		        <#else>
	       	 		<img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(goodsGroupRack.goods.sourceUrl)!""}" onError="this.src='${base}/template/common/images/moren.gif'">
	       		</#if> 				
			</a>
			</div>
	        <dl>
	          <dt><a href="group_goods_detail_new.action?goodsGroupRack.id=${goodsGroupRack.id?c}">${(goodsGroupRack.seoTitle)!""}</a></dt>
	          <dd>${(goodsGroupRack.seoKeyword)!""}</dd>
	        </dl>
	        <div class="groupPrice">
	          <div class="firstPrice">￥${(goodsGroupRack.groupPrice)!""}</div>
	          <div class="secondPrice">原价：￥${(goodsGroupRack.goods.price)!""}</div>
	          <a href="group_goods_detail_new.action?goodsGroupRack.id=${goodsGroupRack.id?c}"><img src="${base}/template/buy/images/look.png"></a>
	        </div>
	        <div class="discount">
	          <div class="leftDiscount">
	   <!--         <P>折扣：${goodsGroupRack.cashPricingDesc!""}</P>  -->
	            <P>有效期：${(goodsGroupRack.beginTime?date("yyyy-MM-dd"))!""}至${(goodsGroupRack.endTime?date("yyyy-MM-dd"))!""}</P>
	          </div>
	          <div class="rightDiscount"><B>${(goodsGroupRack.marketTotalNumber)!""}</B> 人购买</div>
	        </div>
	    </li>
	</#list>  
	</div>
    <div class="page">
         <#if (pager.list?size > 0)>
         	<#include "/WEB-INF/templates/common/pager.ftl" />
       	<#else>
         		目前没有上架的团购商品!	
        </#if>     
      </div>    
  </div>
  </form>
  <!--团购结束-->
  
  <div class="fl ml10">  
    <!--右侧排行开始-->
    <div class="hot">
      <dl>
        <dt><B>热卖排行榜</B></dt>
        <div id="groupGoodsHotListDetail"></div>
      </dl>
    </div>
    <!--右侧排行结束--> 
   
    <!--最近浏览开始-->
    <div class="late mt10">
      <dl>
        <dt><B>最近浏览</B></dt>
        <div id="groupGoodsHistoryListDetail"></div>
      </dl>     
    </div>
    <!--最近浏览结束-->
  </div>
  
</div>
<!--底部开始-->  
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
</body>
</html>
<script type="text/javascript">
$(document).ready(function (){

	if($("#notfind").val() == 'notfind'){
		$.layer({
				title:['分分通提示您',true],
				time:3,
				area : ['auto','auto'],
				dialog : {msg:'抱歉此商品已下架',type : 8}	
			});
		}
});
</script>
<script type="text/javascript"> 
$().ready(function() {

	//热卖排行
		
	$.ajax({
		url:"group_goods_hot_list.action",
		dataType : "json",
		 beforeSend: function(data) {
			$("#groupGoodsHotListDetail").html('<div id="loading"><img src="${base}/template/common/images/ajax-loader.gif"></div>');
		}, 
		success: function(data) {
			var groupGoodsHotListHtml="";
			$.each(data, function(j) {
				var num=j+1;
				groupGoodsHotListHtml += '<dd><div class="title"><i class="redbg">'+num+'</i><a href="group_goods_detail_new.action?goodsGroupRack.id='+data[j].id+'">&nbsp;'+data[j].title+'</a></div><div class="imgInfor"><a href="group_goods_detail_new.action?goodsGroupRack.id='+data[j].id+'"><img src='+data[j].img+'></a><div class="inforPrice"><B>￥'+data[j].groupPrice+'</B><P>已卖出 '+data[j].marketTotalNumber+' 份</P></div></div> </dd>';
			});
			$("#groupGoodsHotListDetail").html(groupGoodsHotListHtml);
		}
	}); 
		
});
</script> 