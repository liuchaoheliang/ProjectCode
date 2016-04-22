<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>团购商品详细信息</title> 
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>
<link href="${base}/template/buy/css/details.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/buy/css/alsolook.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/buy/css/latelook.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/buy/js/group_goods.js"></script>
<script type="text/javascript" src="${base}/template/buy/js/group_goods_big_map.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.4"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.js"></script>
<link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.css" />

<style type="text/css">

.showmapWindow .dialogContent {
    margin: 0;
    padding: 5px;
}

</style>


<script type="text/javascript">
$().ready( function() {

	// 添加团购商品浏览记录
	$.addGroupGoodsHistory("group_goods_detail_new.action?goodsGroupRack.id=${goodsGroupRack.id?c}","${(goodsGroupRack.seoTitle)!""}", "${(goodsGroupRack.groupPrice)!""}" ,"${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(goodsGroupRack.goods.sourceUrl)!""}");
})



function checkAndSubmit(){
	groupBuy.submit();
}
</script>
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
  <div class="fl"> 
  <form id="groupBuy" name="groupBuy" method="post" action="trans_group.action">
    <!--团购展示开始-->
    <div class="show">
      <dl>
      
      	 <input type="hidden" name="tempTran.goodsRackId" id="radio" value="#{goodsGroupRack.id?if_exists}" />
           <input type="hidden" name="tempTran.goodsNumber"  value="1">
    <!--       <input type="hidden" name="tempTran.groupPrice" id="radio" value="${goodsGroupRack.groupPrice?if_exists}" />
            <input type="hidden" name="tempTran.groupPrice" id="radio" value="${goodsGroupRack.groupPrice?if_exists}" />
            <input type="hidden" name="pager.goods.goodsName" id="radio" value="${goodsGroupRack.goods.goodsName?if_exists}" />
           <select style="display:none" name="trans.payMethod">
	           <option  value="00" >分分通积分付款</option>
	         </select> -->
        <dt>${(goodsGroupRack.seoTitle)!""}</dt>
        <dd>${(goodsGroupRack.seoKeyword)!""}</dd>
      </dl>
      <div class="inforMiddle">
        <div class="leftInfor">
          <div class="leftFirst">￥${(goodsGroupRack.groupPrice)!""}</div>
          <div class="imgShow"><#if startFalg?exists && startFalg == "1"><a href="javascript:checkAndSubmit()"><img src="${base}/template/buy/images/lj-detail.gif"></a><#else><a href="javascript:void(0)"><img src="${base}/template/buy/images/lj-detailgray.gif"></a></#if></div>
          <div class="priceInror"><span>原价：￥${(goodsGroupRack.goods.price)!""}</span> <span>${(goodsGroupRack.cashPricingDesc)!""}</span></div>
          <div class="buyed"><B>${(goodsGroupRack.marketTotalNumber)!""}</B> 人已购买</div>
          <div class="over"></div>
          <div class="mobile">
		    <#if ((goodsGroupRack.isCash?exists && goodsGroupRack.isCash=="0") && (goodsGroupRack.fftpointCashPricing?exists && goodsGroupRack.fftpointCashPricing=="0") && 
		    (goodsGroupRack.bankpointCashPricing?exists && goodsGroupRack.bankpointCashPricing=="0") && (goodsGroupRack.bankpointcashRatioPricing?exists && goodsGroupRack.bankpointcashRatioPricing=="0") &&
		    (goodsGroupRack.fftpointcashRatioPricing?exists && goodsGroupRack.fftpointcashRatioPricing=="0")) && ((goodsGroupRack.isFftPoint?exists && goodsGroupRack.isFftPoint=="1") || (goodsGroupRack.isBankPoint?exists && goodsGroupRack.isBankPoint=="1"))> 
		    	<img src="${base}/template/buy/images/1.gif">
		    <#elseif (goodsGroupRack.isCash?exists && goodsGroupRack.isCash=="1") && ((goodsGroupRack.isFftPoint?exists && goodsGroupRack.isFftPoint=="0") && (goodsGroupRack.isBankPoint?exists && goodsGroupRack.isBankPoint=="0") && (goodsGroupRack.isFftpointCash?exists && goodsGroupRack.isFftpointCash=="0")
		    && (goodsGroupRack.isBankpointCash?exists && goodsGroupRack.isBankpointCash=="0") && (goodsGroupRack.isFftpointcashRatioPricing?exists && goodsGroupRack.isFftpointcashRatioPricing=="0") && (goodsGroupRack.isBankpointcashRatioPricing?exists && goodsGroupRack.isBankpointcashRatioPricing=="0"))> 
		    	<img src="${base}/template/buy/images/2.gif">
		    <#elseif (goodsGroupRack.isCash?exists && goodsGroupRack.isCash=="0") && ((goodsGroupRack.isFftPoint?exists && goodsGroupRack.isFftPoint=="0") && (goodsGroupRack.isBankPoint?exists && goodsGroupRack.isBankPoint=="0") && (goodsGroupRack.isFftpointCash?exists && goodsGroupRack.isFftpointCash=="0")
		    && (goodsGroupRack.isBankpointCash?exists && goodsGroupRack.isBankpointCash=="0") && (goodsGroupRack.isFftpointcashRatioPricing?exists && goodsGroupRack.isFftpointcashRatioPricing=="0") && (goodsGroupRack.isBankpointcashRatioPricing?exists && goodsGroupRack.isBankpointcashRatioPricing=="0"))>
		    	<img src="${base}/template/buy/images/3.gif">
		    <#else>
		      	<img src="${base}/template/buy/images/4.gif">
		    </#if>
          </div>
          <div class="date">有效期：${(goodsGroupRack.beginTime?date("yyyy-MM-dd"))!""}至${(goodsGroupRack.endTime?date("yyyy-MM-dd"))!""}</div>
        </div>
		<#if (goodsGroupRack.goodsGroupRackImages?size>0)>
        	<#assign ger=goodsGroupRack.goodsGroupRackImages[0]>
        	<img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(ger.imagesUrl)!""}" onError="this.src='${base}/template/common/images/moren.gif'">
        <#else>
   	 		<img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(goodsGroupRack.goods.sourceUrl)!""}" onError="this.src='${base}/template/common/images/moren.gif'">
   		</#if> 
      </div>
    </div>
    </form>
    <!--团购展示结束-->
     
    <!--团购详情开始-->
    <div class="details mt5">
      <div class="main">
        <h2>本单详情</h2>
        <div class="tips">
          <p>${(goodsGroupRack.seoDescription)!""}</p>
        </div>
        <div class="blk detail">
  <!--        <p class="standard-bar">美食相伴</p>
          <table class="deal-menu">
            <tbody>
              <tr>
                <th class="name">内容</th>
                <th class="price">单价</th>
                <th class="amount">数量/规格</th>
                <th class="subtotal">小计</th>
              </tr>
              <tr>
                <td colspan="4" class="subline"><span class="title">冷菜</span></td>
              </tr>
              <tr>
                <td class="name">炝莴笋</td>
                <td class="price">12元</td>
                <td class="amount">1份</td>
                <td class="subtotal">12元</td>
              </tr>
            </tbody>
          </table>
          <p class="deal-menu-summary">原价：<span class="inline-block worth">400</span> 元</p>
          <p class="deal-menu-summary">分分通团购价：<span class="inline-block worth price">158</span> 元</p>
          <ul class="list">
            <li>餐具免费</li>
            <li>服务免费</li>
            <li>酒水免费</li>
          </ul>
          -->
          <div class="term">
            <h5>购买须知</h5>
            ${(goodsGroupRack.buyKnow)!""}
          </div>
          <!--项目介绍-->
          <p class="standard-bar">商品介绍</p>
          <ul class="list">
            <li>${(goodsGroupRack.goodsRackDetail)!""}</li>
          </ul>
          <!--<img src="img/001.png" height="267" width="440" />-->
		 </div>
      </div>
      <div class="side"> 
      	<!--地图开始--> 
  		<div class="mt10">
  			<div id="map_container" style="width:204px;height:240px;border:1px solid #c7c7c7;"></div>
    		<a href="javascript:void(0);" onclick="javascript:showmapwindow('${(goodsGroupRack.goods.merchant.id?c)!""}');"><img src="${base}/template/buy/images/mapbottom.gif"></a>
    		<!--
    		<div class="map"><img src="img/ditu.png"></div>
    		<a href="#"><img src="${base}/template/buy/images/mapbottom.gif"></a>
    		-->
  		</div>
  		<!--地图结束-->
      	
      	<div id="storeListDiv">
      	</div>        
      </div>
      <div class="deal-buy-bottom"><span class="price num">￥${(goodsGroupRack.groupPrice)!""}</span><#if startFalg?exists && startFalg == "1"><a href="javascript:checkAndSubmit()"><img src="${base}/template/buy/images/sp-detailred.gif"></a><#else><a href="javascript:void(0)"><img src="${base}/template/buy/images/sp-detailgray.gif"></a></#if>
        <ul class="cf">
          <li>原价<br />
            <del class="num"><span>￥</span>${(goodsGroupRack.goods.price)!""}</del></li>
          <li>已购买<br />
            <span class="num">${goodsGroupRack.marketTotalNumber?if_exists}人</span></li>
        </ul>
      </div>
    </div>
    <!--团购详情结束--> 
  </div>
  
  <div class="fl ml10"> 
    <!--还看了开始-->
    <div class="also">
      <dl>
        <dt><b>最新团购</b></dt>
        <span id="groupGoodsNewListDetail">
        
        </span>

      </dl>      
    </div>
    <!--还看了结束--> 
    
    <!--最近浏览开始-->
    <div class="late mt10">
      <dl>
        <dt><b>最近浏览</b></dt>
        <span id="groupGoodsHistoryListDetail"></span>
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

	var map = new BMap.Map("map_container");
	var point = new BMap.Point(116.404, 39.915);
	map.centerAndZoom(point, 12);
	var point;
	var points=[];


	$().ready(function() {
	
		//最新上架团购
			
		$.ajax({
			url:"group_goods_new_list.action",
			dataType : "json",
			 beforeSend: function(data) {
				$("#groupGoodsNewListDetail").html('<div id="loading"><img src="${base}/template/common/images/ajax-loader.gif"></div>');
			}, 
			success: function(data) {
				var groupGoodsNewListHtml="";
				$.each(data, function(j) {
					//var num=j+1;
					groupGoodsNewListHtml += '<dd><a href="group_goods_detail_new.action?goodsGroupRack.id='+data[j].id+'"><img src="'+data[j].img+'"></a><p><a href="group_goods_detail_new.action?goodsGroupRack.id='+data[j].id+'">&nbsp;'+data[j].title+'</a></p><div><B>￥'+data[j].groupPrice+'</B><u>原价：<i>'+data[j].price+'</i></u> <span><b>'+data[j].marketTotalNumber+'</b> 人已购买</span></div></dd>';
				});
				$("#groupGoodsNewListDetail").html(groupGoodsNewListHtml);
			}
		}); 
	
	
		var storeListHtml="";
		
		$.ajax({
			url:"ajax_merchant_store_list.action",
			type: "POST",
			data: {"id": "${(goodsGroupRack.goods.merchant.id?c)!""}"},
			dataType : "json",
			beforeSend: function(data) {
				$("#storeListDiv").html('<img src="${base}/template/common/images/ajax-loader.gif">');
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
					storeListHtml += '<br><span><a href="merchant_annex_info.action?pager.merchantId='+data.merchantId+'&pager.pageNumber=1" target="_blank">查看全部<b>'+totalNumber+'</b>家分店>></a></span><br>';
				}
				
				$("#storeListDiv").html(storeListHtml);
				
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