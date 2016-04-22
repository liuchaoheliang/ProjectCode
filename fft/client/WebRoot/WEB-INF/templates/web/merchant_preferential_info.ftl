<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商户信息</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/web/css/product.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>
<script type="text/javascript" src="${base}/template/web/js/photo.js"></script>
<script type="text/javascript" src="${base}/template/web/js/productTab.js"></script>
<script type="text/javascript" src="${base}/template/web/js/marquee.js"></script>
<script type="text/javascript" src="${base}/template/web/js/collect_share.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.4"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.js"></script>
<link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.css" />

<style type="text/css">

.showmapWindow .dialogContent {
    margin: 0;
    padding: 5px;
}

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
  <div class="productBox">
    <ol id="dtMenu">
    <a href="javascript:void(0)" onmouseover="showRightDiv('divTab01');this.className='playcut'" class="playcut"><li><span class="litab01"></span>商家产品</li></a>
    <a href="javascript:void(0)" onmouseover="showRightDiv('divTab02');this.className='playcut'"><li><span class="litab02"></span>商家介绍</li></a>
    <a href="javascript:void(0)" onmouseover="showRightDiv('divTab03');this.className='playcut'"><li><span class="litab03"></span>商家相册</li></a>
    </ol> 
   <!--tab01开始--> 
   <div class="linkBlack" id="divTab01" style="display:block"> 
     <div id="container">
		<div id="products_example">
			<div id="products">
                <ul class="pagination">
					<#list newMerchantProductPager.list as merchantProduct>
						<li><a data="${merchantProduct.id?c}" href="merchant_product_info.action?id=${merchantProduct.id?c}"><img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(merchantProduct.photoUrl)!""}" onError="this.src='${base}/template/common/images/moren.gif'" width="55" alt="1144953 3 2x"></a>
						<input type="hidden" id="title_${merchantProduct.id?c}" value="${(merchantProduct.txt1?html)!""}"/>
						<input type="hidden" id="content_${merchantProduct.id?c}" value="${(merchantProduct.txt2?html)!""}"/>
						</li> 
					</#list>                   
				</ul>
				<div class="slides_container" id="pingyi">
					<#list newMerchantProductPager.list as merchantProduct>
						<a data="${merchantProduct.id?c}" href="merchant_product_info.action?id=${merchantProduct.id?c}" target="_blank"><img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(merchantProduct.photoUrl)!""}" onError="this.src='${base}/template/common/images/moren.gif'" width="366" alt="1144953 3 2x"></a>
					</#list>
				</div>	
		    </div>
      <div id="title" class="todayPro">${(merchantProduct.txt1?html)!""}</div>
      <div id="content" class="todayjieshao">${(merchantProduct.txt2?html)!""}</div>    
        </div>
     </div>   
   </div>  
   <!--tab01结束--> 
   
   <!--tab02开始--> 
    <div class="linkBlack" id="divTab02" style="display:none">
      <div class="ddInfLeft">
      	<#if merchantPresent?exists && merchantPresent.txt?exists && merchantPresent.txt?length <= 200>	          		
      			${(merchantPresent.txt?html)!""}
     	<#elseif merchantPresent?exists && merchantPresent.txt?exists >	         		
      			${merchantPresent.txt[0..7]}...
     	<#else>
      			-
        </#if>    
      </div>
      <div class="ddInfRight"><img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(merchantPresent.photoUrl)!""}" onError="this.src='${base}/template/common/images/moren.gif'"></div>
    </div>
   <!--tab02结束--> 
   
   <!--tab03开始-->  
    <div class="linkBlack" id="divTab03" style="display:none">
     <div class="marquee"> <a href="javascript:;" id="left"><img src="${base}/template/web/images/shqm_left_pic.gif"></a> </div>
      <div class="marqueeBox" id="marquee">
        <ul>
         <!-- 图片列表 begin -->
          <#list merchantPhotoList as merchantPhoto>
	        <li>
	          	<a href="#" target="_blank"><img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(merchantPhoto.photoUrl)!""}" onError="this.src='${base}/template/common/images/moren.gif'"/></a>
	          	<span>${(merchantPhoto.txt1?html)!""}</span>
	         </li>
         </#list>                                             
        <!-- 图片列表 end -->
        </ul>
      </div>
      <div class="marquee"> <a href="javascript:;" id="right"><img src="${base}/template/web/images/shqm_right_pic.gif"></a> </div>
    </div>  
    <!--tab03结束-->            
  </div>
  
  <!--热推商品开始--> 
    <div class="hotProduct mt10">
      <dl>
        <dt><B>热推商品</B><span><#if (hotPushMerchantProductPager.list?size > 0)><a href="merchantProuct_by_merchantId_list.action?id=${(merchant.id)?c}">更多>></#if></a></span></dt>
        <dd>
        <#list hotPushMerchantProductPager.list as merchantProduct>
	      <li>
	        <div class="hotTitle">${(merchantProduct.txt1?html)!""}</div>
	        <a href="merchant_product_info.action?id=${merchantProduct.id?c}"><img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(merchantProduct.photoUrl)!""}" onError="this.src='${base}/template/common/images/moren.gif'"></a>
	        <#if merchantProduct?exists && merchantProduct.price != "">
					<span>价格：${(merchantProduct.price)!"0"}元<B> &nbsp;&nbsp;
			<#else>
					<span><B>
			</#if>
	        	直接优惠${(merchantProduct.preferentialRate)!"100%"}</B></span>
	      </li>   
	   </#list>                        
      </dd>
      </dl>
    </div>
   <!--热推商品结束--> 
   </div>  
  
  
  <div class="fl ml10">
  <!--商户信息开始-->
  <div class="rightShanghu">
    <ol><B>商户信息</B></ol>
    <div class="showName">${(merchant.mstoreShortName?html)!""}</div>
    <div class="showInr">地址：${(merchant.mstoreAddress?html)!""}</div>
    <div class="showInr">优惠：<#if merchant?exists && merchant.preferentialType=="1">折扣优惠<#else>积分返利</#if></div>
    <div class="showInr" id="shareCount" >收藏人数：${(merchantTrain.collectes)!"0"}</div>
    <div class="showInr">点击人数：${(merchantTrain.clickes)!"0"}</div>
	<div class="shoufen">
		<#if memberCollectPager?exists>
			<#if (memberCollectPager.list?size > 0)><!-- 已收藏 -->
				<div id="yishoucang">
					<a href="javascript:void(0);"><img src="${base}/template/web/images/yishoucang.png"></a>
					<a href="javascript:void(0);" onclick="javascript:ShowShareMerchant(${(merchant.id?c)!""});"><img src="${base}/template/web/images/fenxiang.png"></a>					
				</div>
				<div id="weishoucang" style="display:none;" >
					<a href="javascript:void(0);" onclick="javascript:CollectionMerchant(${(merchant.id?c)!""});"><img src="${base}/template/web/images/shoucang.png"></a>
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
  
  <!--地图开始--> 
  <div class="mt10">
  	<div id="map_container" style="width:238px;height:213px;border:1px solid #c7c7c7;"></div>
    <a href="javascript:void(0);" onclick="javascript:showmapwindow('${(merchant.mstoreShortName?html)!""}','${(merchant.mstoreAddress?html)!""}','${(merchantTrafficMAP.coordinate)!""}');"><img src="${base}/template/web/images/wholemap.png"></a>
  </div>
  <!--地图结束--> 
  
  <!--广告开始> 
  <div class="mt10"><img src="img/ad01.png"></div>
  <!--广告结束--> 
  </div>
</div>  

<!--底部开始-->  
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->  

<script type="text/javascript">
	$(window).load(function(){
		var defaultA = $('#products a:first');
		var id= defaultA.attr('data');
		$("#title").html($('#title_'+id).val());
		$("#content").html($('#content_'+id).val());
	});

	//初始化地图
	var map = new BMap.Map("map_container");            // 创建Map实例
	map.enableScrollWheelZoom();                     //启用滚轮放大缩小
	map.addControl(new BMap.NavigationControl());	//添加了鱼骨

	var p_localhost="${(merchantTrafficMAP.coordinate)!"116.404,39.915"}";
	var p0 = p_localhost.split(",")[0];
	var p1 = p_localhost.split(",")[1];
	var point = new BMap.Point(p0,p1);
	
	var sContent="${(merchant.mstoreShortName)!""}";
	
	map.centerAndZoom(point,12);
	var marker = new BMap.Marker(point);//标注
	var infoWindow = new BMap.InfoWindow(sContent);  // 创建信息窗口对象
	map.addOverlay(marker);
	
	marker.addEventListener("click", function(){          
   		this.openInfoWindow(infoWindow);
	});
</script>
</body>
</html>
