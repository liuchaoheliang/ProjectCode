<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>预售详情</title>
<link rel="icon" href="${base}/favicon.ico" type="image/x-icon" />
<#include "/include/sources.ftl">
<link href="${base}/resources/shop/css/booking.css" rel="stylesheet" type="text/css" />
<#if cantBuy!=null&&presellState==1>
	<script>
		$(document).ready(function(){
			$.layer({
				shade : [0.5 , '#000' , true],
				fix : true,
				closeBtn : [0 , false],
				move : ['.xubox_title' , false],
				title:'友情提示',
				dialog: {
				    btns : 1,
				    btn : ['确定'],
				    type : -1,
				    msg : '您购买此商品数量已达上限，赶快去挑选其他商品吧！',
				    yes : function(index){
				    	window.location.href=settings.baseUrl;
				     }
				}
			});
		});
	</script>
</#if>
<script type="text/javascript">
$(document).ready(function (){
	<#-- 增加、减少购买数量 -->
	$('#buyOrder').click(function (){
		var val = $('#number').val();
		val = parseInt(val,10);
		if(isNaN(val)){
			layer.msg('请输入有效的购买数量',2,-1);
			$('#number').val(1);
			return;
		}else if(val<=0){
			val = 1;
		}
		$('#number').val(val);
		$('#create_order').submit();
		
	});
});
</script>
<style>
	.presellInfo{
		height:255px;
		overflow:hidden;
	}
</style>
</head>
<script type="text/javascript">
$(function(){
	$(".tishired a").click(function(){
      $(this).parent().hide();
	});
	
	var maxNum =${maxNum};//最大购买数量
	maxNum = maxNum==0?9999999:maxNum;
	var minNum = ${minNum};//最低购买数量
	$("#add").click(function(){
		if( maxNum> parseInt($("#number").val())){
        $("#number").val(parseInt($("#number").val()) + 1) ;
		$("#num").css("color","#999");
		$(".tishired:eq(1)").hide();  
		$("#num").find("span").html(maxNum);
	  }
	  else if(maxNum== parseInt($("#number").val())){
		  $(".tishired:eq(0)").show();
		   $("#add").css("color","#ccc");
		}			
	});			

	  $("#num").click(function(){
		  if( $("#number").val() > minNum){
           $("#number").val(parseInt($("#number").val()) - 1) ;	
		   	$("#add").css("color","#999");   
		     if (parseInt(maxNum) >= parseInt($("#number").val())){
			   $(".tishired:eq(0)").hide();  
		    }
		 }	
		 
		 else if($("#number").val() == minNum){
		 	$(".tishired:eq(1)").show();
			 $("#num").css("color","#ccc");
			 }  
	 });			
})
</script>
<body>
<#-- 头部 开始-->
<#include "/include/common/header.ftl">
<!--头部结束-->
<div class="main detailsie7">
  <div class="group">
    <div class="list">
      <li>
        <div class="icon"><img src="${base}/resources/common/images/icon_booking.png" /></div>
        <div class="imgShow" id="xiangqing">
         <span class="imgShow"><a href="${base}/shop/presell/details.jhtml?productId=${productPresellDto.id?c}"><img src="${systemConfigMethod().ftpUrl}${productPresellDto.detailsImage}" width="350" height="250" /></a></span>
	        <span class="bookingnum">
	        	<#--成团数：
	        	<b class="mr50">${productPresellDto.clusteringNumber!'0'} </b> -->
	        	已预订：
	        	<font class="redFont ml10"> ${productPresellDto.trueBuyerNumber + productPresellDto.virtualBuyerNumber}</font> 
	        	<font class=" mr50">件</font>
	        	<#--<#if (productPresellDto.clusteringNumber > (productPresellDto.trueBuyerNumber+productPresellDto.virtualBuyerNumber))>
	        		差<font class="redFont"> ${(productPresellDto.clusteringNumber!0) - ((productPresellDto.trueBuyerNumber!0) + productPresellDto.virtualBuyerNumber!'0')}</font>件成团
	        	<#else>
	        		<font class="redFont">√已成团</font>
	        	</#if> -->
	        </span>
        </div>
        <div class="bookingtext">
          <dl>
            <dt><a href="${base}/shop/presell/details.jhtml?productId=${productPresellDto.id?c}" title="${productPresellDto.title}">${productPresellDto.title}</a></dt>
            <dd title="${productPresellDto.summary}">${productPresellDto.summary}</dd>
           	<#if (presellState>1)>
           		<dd>&nbsp;</dd>
            <#elseif cantBuy==null&&maxNum!=0>
            	<dd>您还可以购买：<b class="redFont" id="totle"> ${maxNum}</b> 件</dd>
             <#elseif cantBuy==null&&maxNum==0>
             	<dd>此商品不限购</dd>
            <#elseif cantBuy!=null&&presellState==1>
            	<dd>您购买此商品数量已达上限</dd>
            </#if>
          </dl>
          <div class="groupPrice">
            <div class="firstPrice"><font class=" f12 grayFont">单价：</font><b class="f14">￥</b>${productDto.price}</div>
            <div class="secondPrice">市场价：<font>￥${productDto.marketPrice!'0'}</font></div>
          </div>
          <form action="${base}/member/pay/presell/create_order.jhtml" method="post" id="create_order">
          	<input type="hidden" name="productId" value="${productPresellDto.id?c}" />
         	<div class="mebuy"> 我要买： <a href="javascript:void(0)" id="num">-</a>
            <input type="text"  class="loginText5" id="number" readonly="readonly"  name="buyCount" value="${minNum}" />
	        <a href="javascript:void(0)" id="add" >+</a> </div>
	        <div >
	        	<#if presellState==0>
	        		<img src="${base}/resources/common/images/zwks.png"/>
	        	</#if>
	        	<#if presellState==1>
	        		<a href="javascript:void(0);" id="buyOrder"><img src="${base}/resources/common/images/buy.png"/></a>
	        	</#if>
	        	<#if (presellState>1)>
	        		<img src="${base}/resources/common/images/finish.png"/>
	        	</#if>
	        </div>
          </form>
          <div class="tishired">提示：不能超过<span>${maxNum}</span> 件<a href="javascript:void(0)">×</a></div>
          <div class="tishired">提示：不能低于<span>${minNum}</span> 件<a href="javascript:void(0)">×</a></div>
          <div class="discount">
            <div class="leftDiscount mt15">
              <p>预售期：${productPresellDto.startTime?string("yyyy-MM-dd")} 至 ${productPresellDto.endTime?string("yyyy-MM-dd")}</p>
              <p>提货期：${productPresellDto.deliveryStartTime?string("yyyy-MM-dd")} 至 ${productPresellDto.deliveryEndTime?string("yyyy-MM-dd")}</p>
            </div>
            <div class="time"><img src="${base}/resources/shop/images/step_0${presellState}.gif" width="373px" height="39px"/></div>
            <div><img src="${base}/resources/shop/images/payicon.png" width="378" height="46" /></div>
          </div>
        </div>
      </li>
    </div>
    <div class="details">
      <div>
        <h2>预售详情</h2> </div>
      <div class="info">
        <dt class="f18 fb ml5">购买须知</dt>
        <dd>
        	${productPresellDto.buyKnow!''}
        </dd>
        <dt class="f18 fb ml5">商品介绍</dt>
        <dd>
        	${productPresellDto.description!''}
        </dd>
      </div>
      <div class="blk f12">温馨提示:商品售出后，非质量问题，恕不退货。</div>
      <!--团购详情结束--> 
    </div>
  </div>
  <div class="fl">
    <div class="also">
      <dl>
        <dt><b>预售动态</b></dt>
        <div class="presellInfo">
        	<#if presellDynamics!=null>
	        	<#list presellDynamics as presellDynamic>
		        	<dd> <a href="javascript:;"><b>${presellDynamic[0]}</b><br />
		            ${presellDynamic[1]} 购买了${productPresellDto.title} ${presellDynamic[2]}</a></dd>
	        	</#list>
	        <#else>
	        		<dd>${productPresellDto.title} 无预售动态</dd>
        	</#if>
        </div>
      </dl>
    </div>
   <@ad_position id = 100000001 />
   <#--预售广告DOM-->
   <#--<div class="also fr"><#list adLists as ad><#if ad.type == '1'><#if ad.link??><a href="${ad.link}" <#if ad.isBlankTarge>target="_blank"</#if> ><img src="${systemConfigMethod().ftpUrl}${ad.path}" width="227" height="389" /></a><#else><img src="${systemConfigMethod().ftpUrl}${ad.path}" width="227" height="389" /></#if></#if></#list></div>-->
  </div>
</div>

<#-- 底部 -->
<#include "/include/common/footer.ftl">
<#-- 底部 -->
<#if presellDynamics!=null>
<!--滚动-->
<script>
        $(document).ready(function(){
            var wrap  =$(".presellInfo");//主元素
            var interval = 1000;//滚动间隔时间
            var moving;
            wrap.hover(function(){
                clearInterval(moving);
            },function(){
                moving = setInterval(function(){
                var first =wrap.find("dd:first");
                var height = first.height();
                first.animate({marginTop:-height+'px'},650,function(){
                    first.css('marginTop',0).appendTo(wrap);
                });
            },interval);
            }).trigger('mouseleave');
        });
    </script>
   </#if>
</body>
</html>
