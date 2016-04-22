<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>创建订单</title>
<link rel="icon" href="${base}/favicon.ico" type="image/x-icon" />
<#include "/include/sources.ftl">
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.4"></script>
<link href="${base}/resources/shop/css/pay.css" rel="stylesheet" type="text/css" />
<#if cantBuy!=null>
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
<script type="text/javascript"><#-- 伪级联操作 -->
$(document).ready(function (){
	var areaJson = $.parseJSON('${areaJson}');
	var bussJson = $.parseJSON('${businessCircleJson}');
	var delvJson = $.parseJSON('${presellDeliveryJson}');
	<#-- 获取后台数据实际json字符串 -->
	var jObj;<#-- 用于遍历的temp对象 -->
	var tempOption = '';
	var areaID = ''; //当前被选中的区县ID
	var bussID = '';
	
	var delvMapArray=[];//地图显示数据
	
	$(areaJson).each(function (index){
		jObj = areaJson[index];
		if(index == 0){
			areaID = jObj.id;
		}
		tempOption += '<option value="'+jObj.id+'">'+jObj.name+'</option>';
	});
	$('#area').append(tempOption);
	tempOption = '';
	<#-- 初始化地区完毕 -->
	var first = true;
	$(bussJson).each(function (index){
		jObj = bussJson[index];
		if(areaID == jObj.areaId){
			if(first){
				bussID = jObj.id;
				first = false;
			}
			tempOption += '<option value="'+jObj.id+'">'+jObj.name+'</option>';
		}		
	});
	first = true;
	$('#buss').append(tempOption);
	tempOption = '';
	<#-- 初始化对应商圈完毕 -->
	$(delvJson).each(function (index){
		jObj = delvJson[index];
		if(bussID == jObj.businessCircleId){
			
			delvMapArray.push(jObj);//增加入地图显示数组中
			
			tempOption += '<li><a onmouseover="openMyWin(\''+jObj.name+'\',\''+jObj.address+'\',\''+jObj.telephone+'\',\''+jObj.coordinate+'\',\''+jObj.businessTime+'\')" class='+ jObj.id +' href="javascript:void(0);">'+jObj.name+'<br /></a><span>地址：'+jObj.address+'&nbsp; </span></br><span>自提点电话：'+ jObj.telephone +'</span></br><span>营业时间：'+ jObj.businessTime +'</span></li>';
		}
	});
	$('#uboxstyle').empty();
	$('#uboxstyle').append(tempOption);
	
	//地图标记
	addMarker(delvMapArray);
	delvMapArray.splice(0,delvMapArray.length);//清空数组 
	
	tempOption = '';
	
	<#-- 初始化对应提货点完毕 -->
	$('#buss').change(function (){
		$('#deliveryId').val('');
		var val = $('#buss').val();
		$("#bussId").val(val);
		$(delvJson).each(function (index){
			jObj = delvJson[index];
			if(val == jObj.businessCircleId){
			
				delvMapArray.push(jObj);//增加入地图显示数组中
				
				tempOption += '<li><a onmouseover="openMyWin(\''+jObj.name+'\',\''+jObj.address+'\',\''+jObj.telephone+'\',\''+jObj.coordinate+'\',\''+jObj.businessTime+'\')" class='+ jObj.id +' href="javascript:void(0);">'+jObj.name+'<br /></a><span>地址：'+jObj.address+'&nbsp; </span></br><span>自提点电话：'+ jObj.telephone +'</span><span>营业时间：'+ jObj.businessTime +'</span></li>';
			}
			$('#uboxstyle').empty();
			$('#uboxstyle').append(tempOption);
		});
		
		//地图标记
		addMarker(delvMapArray);
		delvMapArray.splice(0,delvMapArray.length);//清空数组 
		
		tempOption = '';
		bankSelected();
	});
	
	
	<#-- 商圈伪级联 -->
	$('#area').change(function (){
		$('#deliveryId').val('');
		var val = $('#area').val();
		$("#areaId").val(val);
		$(bussJson).each(function (index){
			jObj = bussJson[index];
			if(val == jObj.areaId){
				if(first){
					bussID = jObj.id;
					first = false;
				}
				tempOption += '<option value="'+jObj.id+'">'+jObj.name+'</option>';
			}
		});
		first = true;
		$('#buss').empty();
		$('#buss').append(tempOption);
		tempOption = '';
		$(delvJson).each(function (index){
			jObj = delvJson[index];
			if(bussID == jObj.businessCircleId){
				
				delvMapArray.push(jObj);//增加入地图显示数组中
				
				tempOption += '<li><a onmouseover="openMyWin(\''+jObj.name+'\',\''+jObj.address+'\',\''+jObj.telephone+'\',\''+jObj.coordinate+'\',\''+jObj.businessTime+'\')" class='+ jObj.id +' href="javascript:void(0);">'+jObj.name+'<br /></a><span>地址：'+jObj.address+'&nbsp; </span></br><span>自提点电话：'+ jObj.telephone +'</span><span>营业时间：'+ jObj.businessTime +'</span></li>';
			}
			$('#uboxstyle').empty();
			$('#uboxstyle').append(tempOption);
		});
		
		//地图标记
		addMarker(delvMapArray);
		delvMapArray.splice(0,delvMapArray.length);//清空数组 
		
		tempOption = '';
		bankSelected();//重新绑定银行选择事件。
	});
	<#-- 区县伪级联 -->
	
});
</script>
<script type="text/javascript"><#-- 支付组合交易相关验证逻辑及特效 -->
var usedPoints;
var buyCount;
$(document).ready(function (){
	
	<#-- 数据校验状态集合 -->
	var isNamePass = false;
	var isPointsPass = false;
	var isBuyNumberPass = false;
	var isDelvMobliePass = false;
	var isPayMobliePass = false;
	var useCQPhone = false;
	<#--重庆网银-->
	var useCQBank = false;
	var useAllPoints = false;
	var totalPrice = "";
	<#-- 约束用户积分输入 -->
	var froadPoints = parseFloat('${userEngineDto.froadPoints!'0'}',10);
	var unitprice = parseFloat('${productDto.price!'1'}',10);<#-- 单价 -->
	 buyCount =  parseFloat($('#number').val(),10);
	 usedPoints = toDecimal(parseFloat($('#fftpoints').val(),10) > unitprice * buyCount ? unitprice * buyCount : parseFloat($('#fftpoints').val(),10));
	$('#fftpoints').val(usedPoints);
	<#--购买数量默认显示最低购买数量，当最低购买数量小于等于0时，表示没有最低限购，则显示上个页面选择的数字-->
	<#-- 初始化总价 -->
	$('#totaPrice').html((unitprice * buyCount)+'元');
	<#-- 初始化积分使用量 -->
	$('#usedPoints').html(usedPoints+'分');
	
	<#-- 数据校验状态集合 -->

	<#-- 实现现金支付2选1 -->
	if($('#cqCard').attr('checked') == 'checked'){
		$('#payphone').show();
	}else{
		$('#payphone').hide();
	}
	
	<#--$('#cqCard').click(function (){
		useCQPhone = true;
		$("#alipay").attr('checked',false);
		var checked = false;
		$('input:checkbox').each(function (index){
			if($(this).attr('checked') == 'checked'){
				checked = true;
				return false;
			}
		});
		if(checked){
			$('#payphone').show();
		}else{
			$('#payphone').hide();
		}
		$('#usedCash').html(toDecimal((unitprice*buyCount)-usedPoints) +'元');
	});
	$('#alipay').click(function (){
		useCQPhone = false;
		$("#cqCard").attr('checked',false);
		if($('#cqCard').attr('checked') == 'checked'){
			$('#payphone').show();
		}else{
			$('#payphone').hide();
		}
		$('#usedCash').html(toDecimal((unitprice*buyCount)-usedPoints) +'元');
	});
	
	if($('#cqCard').attr('checked') == 'checked'){
		useCQPhone = true;
	}-->
	<#-- 现金支付方式特效完成 ,以下为新支付方式 -->
	$('#method_cqBank').click(function (){
		$('#payphone').hide();
		if($("#cqBank").is(":checked")){
			$("#cqBank").attr('checked',false);
			useCQBank = false;
		}else{
			$("#cqBank").attr('checked',true);
			useCQBank = true;
		}
		$("#cqCard").attr('checked',false);
		$("#alipay").attr('checked',false);
		useCQPhone = false;
		$('#usedCash').html(toDecimal((unitprice*buyCount)-usedPoints) +'元');
	});
	$('#method_cqCard').click(function (){
		$('#payphone').show();
		$("#cqBank").attr('checked',false);
		if($("#cqCard").is(":checked")){
			$("#cqCard").attr('checked',false);
			useCQPhone = false;
		}else{
			$("#cqCard").attr('checked',true);
			useCQPhone = true;
		}
		$("#alipay").attr('checked',false);
		useCQBank = false;
		$('#usedCash').html(toDecimal((unitprice*buyCount)-usedPoints) +'元');
	});
	
	$('#method_alipay').click(function (){
		$('#payphone').hide();
		$("#cqBank").attr('checked',false);
		$("#cqCard").attr('checked',false);
		if($("#alipay").is(":checked")){
			$("#alipay").attr('checked',false);
		}else{
			$("#alipay").attr('checked',true);
		}
		useCQPhone = false;
		useCQBank = false;
		$('#usedCash').html(toDecimal((unitprice*buyCount)-usedPoints) +'元');
	});
	
	
	
	<#-- 参数校验  | 开始 -->
	$('#buyerName').blur(checkName);
	function checkName(){
		var val = $('#buyerName').val();
		if(/^([\u4e00-\u9fa5]){2,4}$/.test(val)){
			isNamePass = true;
		}else{
			layer.msg('请输入有效的提货人姓名',2,-1);
			isNamePass = false;
		}
	}
	<#-- 增加、减少购买数量 -->
	$('#cut').click(function (){
		var val = $('#number').val();
		val = parseInt(val,10);
		if(isNaN(val)){
			$('#number').val(1);
			buyCount = 1;
		}else{
			if(val <= 1){
				$('#number').val(1);
				buyCount = 1;
			}else{
				$('#number').val(val - 1);
				buyCount = val - 1;
			}			
		}
		checkBuyNumber();
		if(!isBuyNumberPass){
			return;
		}		
		checkPoints();
		$('#totaPrice').html(toDecimal((unitprice * buyCount))+'元');
	});
	$('#add').click(function (){
		var val = $('#number').val();
		val = parseInt(val,10);
		if(isNaN(val)){
			$('#number').val(1);
			buyCount = 1;
		}else{
			$('#number').val(val + 1);
			buyCount = val + 1;
		}
		checkBuyNumber();
		if(!isBuyNumberPass){
			return;
		}
		checkPoints();
		$('#totaPrice').html(toDecimal((unitprice * buyCount))+'元');
	});
	<#-- 增加、减少购买数量 -->
	
	
	<#-- 校验提货人手机号码 -->
	$('#deliveryMobile').blur(checkDeliveryMob);
	function checkDeliveryMob(){
		if(regexp.phoneRE.test($('#deliveryMobile').val())){
			isDelvMobliePass = true;
		}else{
			layer.msg('请输入有效的的提货人手机号码',2,-1);
			isDelvMobliePass = false;
		}
	}
	
	<#-- 校验购买数量 -->
	$('#number').change(checkBuyNumber);
	function checkBuyNumber(){
		if(/^([1-9])+([0-9])*$/.test($('#number').val())){
			buyCount = parseInt($('#number').val(),10);
			isBuyNumberPass = true;
			$.Ajax({
				url : settings.baseUrl + '/member/pay/presell/validateQuantity.jhtml',
				datas : {'productId':'${productDto.id?c}','quantity':buyCount},
				successFn : function (data){
					if(!data.flag){
						isBuyNumberPass = false;
						var msg = data.msg;
						if(msg.indexOf('：')!=-1){
							var need = msg.substring(msg.indexOf('：')+1,msg.length);
							buyCount = need;
							$('#number').val(need);
						}
						layer.msg(msg,2,-1);
						$('#totaPrice').html(toDecimal((unitprice * buyCount))+'元');
					}else{
						if(isSubmit){
									isSubmit = false;
									<#-- 校验使用积分情况 -->
									checkPoints();
									if(!isPointsPass){
										return;
									}
									
									if($('#deliveryId').val() == ''){
										layer.msg('请选择自提点',2,-1);
										return;
									}
									
									if(!useAllPoints){<#-- 如果没有完全使用积分支付 -->
										if(useCQPhone){<#-- 如果使用的是贴膜卡支付 -->
											<#-- 校验手机贴膜卡号码 -->
											checkCQMob();
											if(isPayMobliePass){
												$('#confirmform').submit();
											}
										}else if(useCQBank){//网银支付
											$('#confirmform').submit();
										}else{
											if(!$('#alipay').is(":checked")){
												layer.msg('抱歉，您的积分不足以购买此商品，请添加现金支付或者使用足够的积分',4,-1);
												return;
											}else{
												$('#confirmform').submit();
											}
										}
									}else{
										$('#confirmform').submit();
									}								
						}
						isBuyNumberPass = true;
					}
				}
			});
		}else{
			buyCount = $("#perminNumber").val()==0?1:$("#perminNumber").val();
			$('#number').val(buyCount);
			isBuyNumberPass = true;
		}
		$('#totaPrice').html(toDecimal((unitprice * buyCount))+'元');
	}
	
	<#-- 校验使用的积分是否合法 -->
	$('#fftpoints').blur(checkPoints);
	function checkPoints(){
		var info = $('#info');
		var pointsEx = /^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/;
		var val = $('#fftpoints').val();
		if(val == '' || val == '0'){
			isPointsPass= true;
			useAllPoints = false;
			info.html('您未使用积分，请选择现金支付');
			$('#cqBank').removeAttr('disabled');
			$('#cqCard').removeAttr('disabled');
			$('#alipay').removeAttr('disabled');
			usedPoints = 0;
			$('#usedPoints').html(toDecimal(usedPoints)+'分');
		    $('#usedCash').html(toDecimal((unitprice*buyCount)-usedPoints) +'元');
			return;
		}
		if(pointsEx.test(val)){
			<#-- 校验积分余额 -->
			val = parseFloat(val,10);
			if(val <= froadPoints){
				isPointsPass= true;
				if(val >= (buyCount*unitprice)){
					info.html('恭喜积分已足以支付该商品');
					useAllPoints = true;
					$('#fftpoints').val(toDecimal(buyCount*unitprice));
					val = toDecimal(buyCount*unitprice);
					//移除已经选中的现金支付方式
					var cashMethod = $("input[name='cashmethod']:checked").attr("id");
					if(cashMethod!=null){
						var method = "#method_"+cashMethod;
						$(method).click();
					}
					<#-- 禁用现金支付 -->
					$('#cqBank').attr({'checked':false,'disabled':'disabled'});
					$('#cqCard').attr({'checked':false,'disabled':'disabled'});
					$('#alipay').attr({'checked':false,'disabled':'disabled'});
					$('#payphone').hide();
				}else{
					info.html('积分不足，您还需要支付现金');
					useAllPoints = false;
					$('#cqBank').removeAttr('disabled');
					$('#cqCard').removeAttr('disabled');
					$('#alipay').removeAttr('disabled');
				}
				isPointsPass = true;
			}else{
				isPointsPass= false;
				useAllPoints = false;
				info.html('抱歉，可用积分不足');
			}
		}else{
			info.html('请输入有效的积分值');
			isPointsPass= false;
			useAllPoints = false;
			return;
		}
		usedPoints = val;
		$('#usedPoints').html(toDecimal(usedPoints)+'分');
		$('#usedCash').html(toDecimal((unitprice*buyCount)-usedPoints) +'元');
	}
	
	
	
	<#-- 贴膜卡手机支付号码验证 -->
	$('#paymobile').blur(checkCQMob);
	function checkCQMob(){
		if(useAllPoints){
			isPayMobliePass = true;
			return;
		}
		if(regexp.phoneRE.test($('#paymobile').val())){
			isPayMobliePass = true;
		}else{
			if(useCQPhone){
				layer.msg('请输入有效的的支付手机号码',2,-1);
				isPayMobliePass = false;
			}else{
				isPayMobliePass = true;
			}
			
		}
	}
	
	var isSubmit = false;
	<#-- 表单总体验证 -->
	$('#submitForm').click(function (){
		checkName();
		if(!isNamePass){
			return;
		}
		<#-- 校验提货人手机号码 -->
		checkDeliveryMob();
		if(!isDelvMobliePass){
			return;
		}
		isSubmit = true;
		<#-- 校验提购买数量 -->
		checkBuyNumber();
	});
});
</script>
<script type="text/javascript">
function bankSelected(){
	$("#uboxstyle li").click(function(){
		if ( $(this).attr("class") != "cutbg") 
		{
		   $(this).siblings().removeClass("cutbg"),
		   $(this).addClass("cutbg");
		   $('#deliveryId').val($(this).find('a').attr('class'));
		} 
		else {
			$(this).removeClass("cutbg")
			 $('#deliveryId').val('');
		}		
			
		$("#select").html($(this).find("span:eq(0)").text().replace("地址",""))
	})
}
$(document).ready(function(){
	bankSelected();//此处默认绑定，在级联切换后需要重新绑定。
});
</script>

<script type="text/javascript">
$(function(){
	$("#showpoints a").click(function(){
		 if ( $(this).attr("class") != "active") 
		  {
		   $(this).parent().siblings().children('a').removeClass("active");
		   $(this).parent().siblings().children('b').hide();
		   $(this).addClass("active");
		   $(this).next('b').show();
		 } 
		 
		else {
			$(this).removeClass("active")
			 $(this).next('b').hide();
			}		
			
		})
	})
</script>

</head>
<body>
<#-- 头部 开始-->
<#include "/include/common/header.ftl">
<#-- 头部 结束-->
<form id="confirmform" action="${base}/member/pay/presell/confirm_order.jhtml" method="post">
<input type="hidden" name="productId" value="${productPresellDto.id?c}" />
<input type="hidden" name="perNumber" id="perNumber" value="${productPresellDto.perNumber}" />
<input type="hidden" name="perminNumber" id="perminNumber" value="${productPresellDto.perminNumber}" />
<div class="main">
	<div class="paytable validate">
    	<div class="stepgroup"></div>
    	<div class="table-section mt5">
      	<table width="80%" cellspacing="0">
        	<tbody>
          	<tr>
          		<!--自提点：<select id="delv" name="deliveryId">
          		</select>-->
          		<input type="hidden" id="deliveryId" value="" name="deliveryId" /><#--自提点-->
          		<input type="hidden" id="areaId" value="" name="areaId" /><#--区县-->
          		<input type="hidden" id="bussId" value="" name="bussId" /><#--商圈-->
            	<td valign="top" align="right">自提点：
            	</td>
            	<td align="left" ><div class="w300 fl"><span class="fl mb5" >
            		行政区县：<select id="area">
	          		</select>
	          		商圈：<select id="buss">
	          		</select></span>
	          		 <p class="clear mb10">选择自提点地址：<b id="select"></b></p>
              		<div class="fl" id="uboxstyle">
              		</div>
              		</div>
              		<div class="map">
              			<div id="map_container" style="width:260px;height:309px;border:1px solid #c7c7c7;"></div>
              		</div>
              	</td>
            	<td align="left">&nbsp;</td>
          	</tr>
          	<tr>
            	<td align="right">提货人姓名：</td>
            	<td class="desc"><input type="text" id="buyerName" name="buyerName" value="${userEngineDto.uname!''}" class="loginText"/></td>
            	<td class="desc">&nbsp;</td>
          	</tr>
          	<tr>
	            <td align="right">手机号：</td>
	            <td class="desc"><input type="text" maxlength="11" name="deliveryMobile" id="deliveryMobile" value="${userEngineDto.mobile!''}" class="loginText"/></td>
	            <td class="desc">&nbsp;</td>
	          </tr>
          	<tr>
            	<td width="236" align="right">商品名称：</td>
            	<td width="654" class="desc">${productDto.name}</td>
            	<td width="82" class="desc">&nbsp;</td>
          	</tr>
          	<tr>
            	<td align="right">数量：</td>
            	<td align="left" class="quantity">
            		<a href="javascript:void(0)" id="cut">-</a>
              		<input class="loginText5" name="buyCount" maxlength="10" value="${buyNumber!'1'}" type="text" id="number" readonly="readonly">
              		<a href="javascript:void(0)" id="add">+</a>
              			<#--最低购买数量-->
            		<#--<#if (productPresellDto.perminNumber>0)>
            			每人最低购买：${productPresellDto.perminNumber}&nbsp;
            		</#if>-->
            		<#if (productPresellDto.perNumber>0)>
            			<#--<#if (productPresellDto.perminNumber>0)>
            				，
            			</#if>-->
            			现在最多还可以购买 ：${maxNum}
            		</#if>
              		</td>
            	<td align="left" class="quantity">&nbsp;</td>
          	</tr>
          <tr>
            	<td align="right">单价：</td>
            	<td align="left" class="quantity redFont">${productDto.price!'1'}元 </td>
            	<td align="left" class="quantity redFont">&nbsp;</td>
          </tr>
          
          <tr><#--支付方式-->
	        	<td nowrap="nowrap" valign="top" align="right" >
	        		<span class="mt20 fr">支付方式：</span>
	        	</td>
	        	<td align="left" id="formlist" >
	        		<div class="useponits">
	        			可用积分：
	        			<font class="redFont f16  mr100">
	        				${userEngineDto.froadPoints!'0'}分
	        			</font>
	        			我要使用
	            		<input id="fftpoints" name="fftpoints" value="0" maxlength="14" type="text" class="loginText5" />分
	            		<span id="info" style="color:green;"></span>
	            	</div>
	            	<#-- 现金支付 -->
	          		<#--<div id="cashMethod" class="usecash">
	          			使用现金：
                		<input name="cashmethod" id="cqCard" type="checkbox" value="card" />
                		<img src="${base}/resources/member/images/pay.gif"  border="1" >手机银行卡
                		<input name="cashmethod" id="alipay" type="checkbox" value="alipay" />
                		<img src="${base}/resources/member/images/pay1.gif"  border="1" />
                	</div>
                	<div id="payphone" style="display:none;"  class="usecash mt5" style="disabled:true">
                		<font class="mr20">
                			支付手机号:
                		</font>
                		<input type="text"  maxlength="11" name="paymobile" id="paymobile" value="${userEngineDto.mobile!''}" class="loginText" />
                	</div>-->
                <div id="showpoints" class="usecash" style="height:185px;">
                <p class="pt10">
                <input name="cashmethod" style="display:none;" id="cqBank" type="checkbox" value="bank" />
                <span>网上银行：</span>
                <a href="javascript:;" id="method_cqBank"><img src="${base}/resources/member/images/pay.gif"></a>
                <b><img src="${base}/resources/common/images/gou.png"></b>
                </p>
                
                <p>
                <#--选择支付方式后，对应的checkBox会选中-->
                <input name="cashmethod" style="display:none;" id="cqCard" type="checkbox" value="card" />
                <span>手机银行卡：</span>
                <a href="javascript:;" id="method_cqCard"><img src="${base}/resources/member/images/pay.gif"></a>
                <b><img src="${base}/resources/common/images/gou.png"></b>
                </p>
                
                <p>
                <#--选择支付方式后，对应的checkBox会选中-->
                <input name="cashmethod" style="display:none;" id="alipay" type="checkbox" value="alipay" />
                <span>支付宝：</span>
                <a href="javascript:;" id="method_alipay"><img src="${base}/resources/member/images/pay1.gif"></a>
                <b><img src="${base}/resources/common/images/gou.png"></b>
                </p>
                </div>
                <div id="payphone" style="display:none;"  class="usecash mt5" style="disabled:true">
	                		<font class="mr20">
	                			支付手机号:
	                		</font>
	                		<input type="text"  maxlength="11" name="paymobile" id="paymobile" value="${userEngineDto.mobile!''}" class="loginText" />
	             </div>
               </td>
            <td align="left" id="formlist" >&nbsp;</td>
          </tr>
          
          <tr >
            <td align="right">总价：</td>
            <td align="left" valign="middle">
            	<span class="fl">
            		<font class="redFont f20" id="totaPrice">元</font>
            	</span>
            	<span style="float:right">
            		积分抵扣：
            		<font class="redFont f20" id="usedPoints"></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;支付现金：
            		<font class="redFont f20" id="usedCash">0元</font>
            	</span>
            </td>
            <td align="left" id="formlist">&nbsp;</td>
          </tr>
        </tbody>
      </table>
      <div class="confirm"> <a href="javascript:void(0);" id="submitForm" class="fr mt10 mr15">
        <div class="textBtn"><B>确认无误，购买</B></div>
        </a> </div>
    </div>
  </div>
  
  <!--清除浮动-->
  <div class="clear"></div>
  <!--清除浮动--> 
</div>

</form>
<#-- 底部 -->
<#include "/include/common/footer.ftl">
<#-- 底部 -->

<#--返回订单创建页时，显示之前选择的信息 by larry-->
<#if history!=null>
	<script>
		$(document).ready(function(){
				$("#buyerName").val('${history.buyerName}');//提货人姓名
				$("#deliveryMobile").val('${history.deliveryMobile}');//提货人手机号
				$("#number").val('${history.buyNumber}');//购买数量
				$("#fftpoints").val('${history.fftpoints}');//使用积分
				usedPoints = $('#fftpoints').val();
				buyCount = $("#number").val();
				var cashmethod = '${history.cashmethod}';//支付方式
				if(cashmethod=='card'){
					$("#method_cqCard").click();
					$("#paymobile").val('${history.paymobile}');
					$("#payphone").show();
				}else if(cashmethod=='alipay'){
					$("#method_alipay").click();
				}else if(cashmethod=='bank'){
					$("#method_cqBank").click();
				}
				var h_totalPrice = ${history.totalPrice};
				$('#usedPoints').html(toDecimal(usedPoints)+'分');
		   		$('#usedCash').html(toDecimal(h_totalPrice-usedPoints) +'元');
				//自提点重新选择
				var deliveryId=${history.presellDeliveryDto.id?c};
				$('#area').val('${history.areaId}');
				$('#area').change();
				$('#buss').val('${history.bussId}');
				$('#buss').change();
				$("#uboxstyle li").find("a[class='"+deliveryId+"']").parent().click();
				//计算总价
				var unit = parseFloat('${productDto.price!'1'}',10);<#-- 单价 -->
				var count =  parseFloat($('#number').val(),10);
				$('#totaPrice').html(toDecimal((unit * count))+'元');
		})
	</script>
</#if>
</body>
</html>
<script type="text/javascript">
	//初始化地图
	var map = new BMap.Map("map_container");    // 创建Map实例
	map.enableScrollWheelZoom();  //启用滚轮放大缩小
	map.centerAndZoom("重庆",12);  // 初始化地图,设置中心点坐标和地图级别
	
	var point;
	var points=[];
	
	//地图标记
	function addMarker(delvDataJson){
	
		map.clearOverlays();//清除
		
		$.each(delvDataJson, function(i, temp) { 
			//alert(i);
			if(temp.coordinate == null || temp.coordinate == ""){
				return true;
			}
			
			//放入数组
			var p0 = temp.coordinate.split(",")[0];
			var p1 = temp.coordinate.split(",")[1];
			point = new BMap.Point(p0,p1);
			points.push(point);
			
			//创建标注
			var marker = new BMap.Marker(point);
			
			var ico=marker.getIcon();
	        ico.setInfoWindowAnchor(new BMap.Size(17, 7));
		
			map.addOverlay(marker);//将标注添加到地图中
			
			(function(){
				var _iw = createInfoWindow(temp.name,temp.address,temp.telephone,temp.businessTime);
				var _marker = marker;
			    			
			    //移动
				_marker.addEventListener("mouseover",function(){
					this.openInfoWindow(_iw);
				});
			})()
		});
		
		map.setViewport(points);//调整地图的最佳视野为显示标注数组point
	}
	
	function createInfoWindow(fullName,address,telephone,businessTime){
		var opts = {offset : new BMap.Size(-5, -5), title : '<span style="font-size:14px;color:#0A8021">'+fullName+'</span>'};
	    var infoWindow = new BMap.InfoWindow("<div style='line-height:1.8em;font-size:12px;'><b>地址:</b>"+address+"</br><b>自提点电话:</b>"+telephone+"</br><b>营业时间:</b>"+ businessTime +"</div>", opts);  // 创建信息窗口对象
	    return infoWindow;   		
	}

	function openMyWin(fullName,address,telephone,coordinate,businessTime){
		var opts = {offset : new BMap.Size(5, -5), title : '<span style="font-size:14px;color:#0A8021">'+fullName+'</span>'};
	    var infoWindow = new BMap.InfoWindow("<div style='line-height:1.8em;font-size:12px;'><b>地址:</b>"+address+"</br><b>自提点电话:</b>"+telephone+"</br><b>营业时间:</b>"+ businessTime +"</div>", opts);  // 创建信息窗口对象
		
		var p0 = coordinate.split(",")[0];
        var p1 = coordinate.split(",")[1];
        var point = new BMap.Point(p0,p1);
		
    	map.openInfoWindow(infoWindow,point);  
	}

</script>