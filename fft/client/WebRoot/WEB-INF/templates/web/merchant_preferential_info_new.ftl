<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商户信息</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/web/css/product.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/groupright.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/web/js/marquee.js"></script>
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
  <div class="fl">
   
  <!--优惠详情开始-->  
    <div class="productBox"> 
    <dl>
      <dt><B>优惠详情</B></dt>
      <dd class="inforfont">
		${(merchant.preferenceDesc)!""}
      </dd>
    </dl>
    </div>  
<!--优惠详情结束--> 
  
 
  <!--热推商品开始--> 
    <div class="hotProduct mt10 mb10">
      <dl>
        <dt><B>热推商品</B><span>
        	<#if (hotPushMerchantProductPager.list?size > 3)>
        		<a href="merchantProuct_by_merchantId_list.action?id=${(merchant.id?c)!""}">查看全部商品>></a>
        	</#if></span></dt>
        <dd>
        <#assign numCount = 0>
         <#list hotPushMerchantProductPager.list as merchantProduct>
         	 <#assign numCount = numCount + 1>
         	 <#if numCount <=3 >
	        <li>
	          <div class="hotTitle">${(merchantProduct.txt1?html)!""}</div>
	           <a href="merchant_product_info.action?id=${merchantProduct.id?c}">
					<img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(merchantProduct.photoUrl)!""}" onError="this.src='${base}/template/common/images/moren.gif'">
	           </a>
	        </li>   
	        </#if>
 		</#list>                 
      </dd>
      </dl>
    </div>
   <!--热推商品结束--> 

  <!--商家介绍开始-->  
    <div class="productBox"> 
    <dl>
      <dt><B>商家介绍</B></dt>
      <dd>
     <div class="marquee"> <a href="javascript:;" id="left"><img src="${base}/template/web/images/shqm_left_pic.gif"></a> </div>
      <div class="marqueeBox" id="marquee">
        <ul>
        <#list merchantPhotoList as merchantPhoto>
          <li><img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(merchantPhoto.photoUrl)!""}" onError="this.src='${base}/template/common/images/moren.gif'"/><span>${(merchantPhoto.txt1?html)!""}</span></li>
        </#list>
        </ul>
      </div>
      <div class="marquee"> <a href="javascript:;" id="right"><img src="${base}/template/web/images/shqm_right_pic.gif"></a> </div>
      </dd>
      <dd class="inforfontB">
      	<#if merchantPresent?exists && merchantPresent.txt?exists && merchantPresent.txt?length <= 160>	          		
				${(merchantPresent.txt?html)!""}
     	<#elseif merchantPresent?exists && merchantPresent.txt?exists >	         		
      			${merchantPresent.txt}
     	<#else>
      			-
        </#if>     
      </dd>
    </dl>
    </div>  
<!--商家介绍结束-->  

  <!--分店信息开始--> 
    <div class="hotProduct mt10 mb10">
      <dl>
        <dt><B>商家位置</B></dt>
        <dd>
        <!-- 地图信息 -->
            <div class="leftmap">
            	<!--
            		width: 710px; height: 350px;
            		width:298px;height:266px;
            	-->
            	<div id="map_container" style="width:298px;height:266px;border:1px solid #c7c7c7;"></div>
            	<p>
            	<a href="javascript:void(0);" onclick="javascript:showmapwindow('${(merchant.id?c)!""}');">
            		<img src="${base}/template/web/images/newmapmore.png">
            	</a>
            	</p>
            </div> 
        <!-- 地图信息 -->
            <div class="rightmapfen">

            </div>
        </dd>
      </dl>
    </div>
   <!--分店信息结束--> 


   </div>  
  
  
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
  
  <!--相关团购开始-->
   <#if goodsGroupRackList?exists && (goodsGroupRackList?size > 0)>
    <div class="also mt10">
    
      <dl>
      	<dt>
      		<b>商户团购商品</b>
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
<script type="text/javascript">

	var map = new BMap.Map("map_container");
	var point = new BMap.Point(116.404, 39.915);
	map.centerAndZoom(point, 14);
	var point;
	var points=[];

	$().ready(function() {
	
		var storeListHtml="";
		
		$.ajax({
			url:"ajax_merchant_store_list.action",
			type: "POST",
			data: {"id": "${(merchant.id?c)!""}"},
			dataType : "json",
			beforeSend: function(data) {
				$(".rightmapfen").html('<img src="${base}/template/common/images/ajax-loader.gif">');
			}, 
			success: function(data) {
				var totalNumber=data.storeTotalNumber;
				
				$.each(data.storeList, function(i, item) { 	
					if(i>3){
 						return false;
 					} 
 					
 					if(item.coordinate == null || item.coordinate == ""){
 						storeListHtml += "<dl><dt><a href=\"javascript:void(0);\" title="+item.fullName+">"+item.fullName+"</a></dt><dd title="+item.address+">地址："+item.address+"</dd><dd>电话："+item.telephone+"</dd></dl>";
 					}
 					else{
 						storeListHtml += "<dl><dt><a onmouseover='openMyWin(\""+item.fullName+"\",\""+item.address+"\",\""+item.telephone+"\",\""+item.coordinate+"\")' href=\"javascript:void(0);\" title="+item.fullName+">"+item.fullName+"</a></dt><dd title="+item.address+">地址："+item.address+"</dd><dd>电话："+item.telephone+"</dd></dl>";
 						
 						//放入数组
						var p0 = item.coordinate.split(",")[0];
            			var p1 = item.coordinate.split(",")[1];
            			point = new BMap.Point(p0,p1);
	        			points.push(point);
	        		
	        			//创建标注
	        			var marker = new BMap.Marker(point);
	        			
	        			var ico=marker.getIcon();
	        			ico.setInfoWindowAnchor(new BMap.Size(17, 7));
	        		
	        			//将标注添加到地图中
	        			map.addOverlay(marker);
	        		
		        		(function(){
			    			var _iw = createInfoWindow(item.fullName,item.address,item.telephone);
			    			var _marker = marker;
			    			
			    			//移动
			    			_marker.addEventListener("mouseover",function(){
			        			this.openInfoWindow(_iw);
			    			});
			    		})()
 					}
				});
				
				if(totalNumber>4){
					storeListHtml += '<br><div class="rightmore"><a href="merchant_annex_info.action?pager.merchantId='+data.merchantId+'&pager.pageNumber=1" target="_blank">查看全部<b>'+totalNumber+'</b>家分店>></a></div>';
				}
				
				$(".rightmapfen").html(storeListHtml);
				
				map.setViewport(points);         //调整地图的最佳视野为显示标注数组point
				
			}
 		});
	});
	
	function createInfoWindow(fullName,address,telephone){
	
		var opts = {offset : new BMap.Size(-5, -5), title : '<span style="font-size:14px;color:#0A8021">'+fullName+'</span>'};
	    var infoWindow = new BMap.InfoWindow("<div style='line-height:1.8em;font-size:12px;'><b>地址:</b>"+address+"</br><b>电话:</b>"+telephone+"</br><b></div>", opts);  // 创建信息窗口对象
	    
	    return infoWindow;   		
	}

	function openMyWin(fullName,address,telephone,coordinate){
	
		var opts = {offset : new BMap.Size(5, -5), title : '<span style="font-size:14px;color:#0A8021">'+fullName+'</span>'};
	    var infoWindow = new BMap.InfoWindow("<div style='line-height:1.8em;font-size:12px;'><b>地址:</b>"+address+"</br><b>电话:</b>"+telephone+"</br><b></div>", opts);  // 创建信息窗口对象
		
		var p0 = coordinate.split(",")[0];
        var p1 = coordinate.split(",")[1];
        var point = new BMap.Point(p0,p1);
		
    	map.openInfoWindow(infoWindow,point);  
    	
	}
</script>