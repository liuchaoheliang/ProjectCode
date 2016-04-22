<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>重庆-分分通</title>
<meta name="keywords" content="分分通-重庆首页" />
<meta name="description" content="欢迎来到分分通-重庆站首页" />
<link rel="icon" href="${base}/favicon.ico" type="image/x-icon" />
<#include "/include/sources.ftl">
<link href="${base}/resources/common/css/banner.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/shop/js/larry_timer.js" ></script>
<script type="text/javascript" src="${base}/resources/shop/js/presell_info.js" ></script>
<script type="text/javascript">
$(window).load(function(){
	var O2OBILL = O2OBILL||{};
	O2OBILL.ajax = function(o){
		if(!parent||!parent.document.getElementById('sso_hidden_iframe')){
			$('<iframe style="display:none;border:0;margin:0;padding:0;" src="'+o.url+'" id="sso_hidden_iframe" width="0" height="0"></iframe>').appendTo(document.body);
			$('#sso_hidden_iframe').bind('load',function(){
				$.ajax({
				    url:o.url,
				    global: false,
				    dataType:o.dataType||'html',
				    method:o.method||'POST',
				    success:function(res){
				       if(typeof o.success == 'function'){
				    	   o.success(res);
				       }
				    },
				    error: function(XMLHttpRequest, textStatus, errorThrown) { 
				    	if(typeof o.unloginFn == 'function'){
					    	o.unloginFn(XMLHttpRequest, textStatus, errorThrown);
					    }
				    }
				});
			});
		}
	}
	O2OBILL.ajax({
		url:'${base}/sso/core/support.jhtml?random=' + Math.random(),
		unloginFn:function(a,b,c){},
	    dataType:'html',
	    method:'POST',
	    success:function(res){
			$.Ajax({
	    		url : '${base}/sso/core/query_info.jhtml',
	    		datas : {'uri' : 'index'},
	    		useErrorMsg : false,
	    		useRandom : true,
	    		successFn : function (data){
	    			if(data.flag){
	    				window.location.reload();
	    				return;
	    			}
	    		}
	    	});
	    }
	});    
});
</script>
<script type="text/javascript">
$.Ajax({
	url : '${base}/common/index_header_info.jhtml',
	dataType : 'html',
	useErrorMsg : false,
	useRandom : true,
	successFn : function (data){
		$('#index_header').html(data);
	}
});
</script>
<script type="text/javascript">
$(document).ready(function (){
	$(".lxftime").larryTimer();
	$(".presellNum").larryPresell();
});
</script>

<style>
.group .list{ float:left; width:748px;}
.group .list li .icon{ left:7px; top:7px;}
.group .list li{width:748px; height:260px}
.group .list li .imgShow{width:748px;}
.group .list li .imgShow img{width:492px; height:245px; margin-top:7px; margin-left:7px;}
.group .bookingtext{width:245px; margin-left:5px;}
.group .list li dl{width:240px;}
.group .list li dl dt{width:240px;}
.group .list li dl dd{ height:20px;}
.group .list li .groupPrice{width:240px;}
.leftDiscount{width:240px; margin-left:5px; margin-top:5px;}
.group .list li .discount .btn{width:250px; margin-left:5px; margin-top:8px;}
.countdown{width:240px; margin-left:5px;}
.countdown span{ display:inline; padding:0 2px; font-size:11px;}
.countdown em{ font-size:11px;}
.group .list li .bookingnum{ margin-left:5px;}
</style>
</head>
<body>
<#-- 头部 开始-->
<div id="index_header"></div>
<#--<#include "/include/common/header.ftl">-->
<#-- 头部 结束-->

<div class="main">
	<#-- 首页广告 开始-->
	<@ad_position id = 100000000 />
	
	<#-- <div class="ad"><div class="main_view"><div class="window"><div class="image_reel" id="image_reel"><#list adLists as ad><#if ad.type == '1'><#if ad.link??><a href="${ad.link}" <#if ad.isBlankTarge>target="_blank"</#if> ><img src="${systemConfigMethod().ftpUrl}${ad.path}" alt="" width="1000" height="360"/></a><#else><img src="${systemConfigMethod().ftpUrl}${ad.path}" alt="" width="1000" height="360"/></#if></#if></#list></div></div><div class="paging" id="paging"><a href="#" rel="1">1</a><a href="#" rel="2">2</a><a href="#" rel="3">3</a><a href="#" rel="4">4</a></div></div></div> -->
	
	<script type="text/javascript">
	
	$(document).ready(function() {
	
		//Set Default State of each portfolio piece
		$(".paging").show();
		$(".paging a:first").addClass("active");
			
		//Get size of images, how many there are, then determin the size of the image reel.
		var imageWidth = $(".window").width();
		var imageSum = $(".image_reel img").size();
		var imageReelWidth = imageWidth * imageSum;
		
		//Adjust the image reel to its new size
		$(".image_reel").css({'width' : imageReelWidth});
		
		//Paging + Slider Function
		rotate = function(){	
			var triggerID = $active.attr("rel") - 1; //Get number of times to slide
			var image_reelPosition = triggerID * imageWidth; //Determines the distance the image reel needs to slide
	
			$(".paging a").removeClass('active'); //Remove all active class
			$active.addClass('active'); //Add active class (the $active is declared in the rotateSwitch function)
			
			//Slider Animation
			$(".image_reel").animate({ 
				left: -image_reelPosition
			}, 500 );
			
		}; 
		
		//Rotation + Timing Event
		rotateSwitch = function(){		
			play = setInterval(function(){ //Set timer - this will repeat itself every 3 seconds
				$active = $('.paging a.active').next();
				if ( $active.length === 0) { //If paging reaches the end...
					$active = $('.paging a:first'); //go back to first
				}
				rotate(); //Trigger the paging and slider function
			}, 3000); //Timer speed in milliseconds (3 seconds)
		};
		
		rotateSwitch(); //Run function on launch
		
		//On Hover
		$(".image_reel a").hover(function() {
			clearInterval(play); //Stop the rotation
		}, function() {
			rotateSwitch(); //Resume rotation
		});	
		
		//On mouseover
		$(".paging a").mouseover(function() {
			
			$(".image_reel").stop(true,true);	
			$active = $(this); //Activate the clicked paging
			//Reset Timer
			clearInterval(play); //Stop the rotation
			rotate(); //Trigger rotation immediately
			rotateSwitch(); // Resume rotation
			return false; //Prevent browser jump to link anchor
		});	
		
	});
	</script>
	<#-- 首页广告 结束-->
	<div class="group fl">
		<div class="list">
		<@product_presell_list>
		<#list productPresells as product>
				<li>
					<div class="icon"><img src="${base}/resources/common/images/icon_booking.png"></div>
					<div class="imgShow"><a href="${base}/shop/presell/details.jhtml?productId=${product.productPresell.id?c}"><img src="${systemConfigMethod().ftpUrl}${product.productPresell.generalizeImage}" ></a></div>
					<div class="bookingtext">
						<dl>
							<dt><a href="${base}/shop/presell/details.jhtml?productId=${product.productPresell.id?c}" title="${product.name}">${product.name}</a></dt>
							<dd title="${product.productPresell.summary}">${product.productPresell.summary}</dd>
						</dl>
						<div class="groupPrice">
							<div class="firstPrice">￥${product.price}</div><div class="secondPrice"></div>
						</div>
						<div class="bookingnum">已预订：<font class="redFont presellNum" pk="${product.productPresell.id?c}" ></font> 件</div>
						<div class="discount">
							<div class="leftDiscount">
								<P>预售期：${product.productPresell.startTime?string('yyyy-MM-dd')} 至 ${product.productPresell.endTime?string('yyyy-MM-dd')}</P>
								<P>提货期：${product.productPresell.deliveryStartTime?string('yyyy-MM-dd')} 至 ${product.productPresell.deliveryEndTime?string('yyyy-MM-dd')}</P>
							</div>
							<div class="countdown">
								<font>预售剩余时间：</font>
		             			<p class="lxftime" endtime=""  id="timercontainer${product_index}" pk="${product.productPresell.id?c}"></p>
		            		</div>
		            		<div class="btn">
		            		<#--<a href="${base}/member/pay/presell/create_order.jhtml?productId=${product.productPresell.id?c}" class="buyNow"><img src="${base}/resources/common/images/buy.png"></a>-->
		            		<a href="${base}/shop/presell/details.jhtml?productId=${product.productPresell.id?c}"><img src="${base}/resources/common/images/view.png"></a>
		            		</div>
		          		</div>
		          	</div>
	      		</li> 				
		</#list>
		</@product_presell_list>
      	</div> 
	    <!--新增广告开始-->
	    	<@ad_position id = 100000002 /><#--金额排行-->
	    	<@ad_position id = 100000003 /><#--时间排行-->
		    <#--原DOM <div class="fl mt10 ml10">
		    	<a href="#"><img src="${base}/resources/common/images/ad01.jpg"></a>
		    </div>
		    
		    <div class="fl mt10 ml10">
		    <a href="#"><img src="${base}/resources/common/images/ad02.jpg"></a>
		    </div>  -->
	    <!--新增广告结束-->
    </div>
</div>
<#-- 底部 -->
<#include "/include/common/footer.ftl">
<#-- 底部 -->
</body>
</html>