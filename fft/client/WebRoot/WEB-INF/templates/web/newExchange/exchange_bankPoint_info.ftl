<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通-珠海专区</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/select2.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>
<link href="${base}/template/web/css/points.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/rightimg.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/closeDialog.js"></script>

<!--
* Author:
* pengling@f-road.com.cn 
*/
-->

<script type="text/javascript">
$(document).ready( function() {
	
	var buyNumber = $("#number");
	//var stockNumber = $("#stock");//库存

	//输入数量
	$("#number").keyup(function(){
		
		var re = /^[1-9]\d*$/;
     	if (!re.test($(this).val())){
     		$(this).val(1);
     	}
     	
     	//if(parseInt(buyNumber.val())>parseInt(stockNumber.text())){
     	//	$(this).val(stockNumber.text());
     	//	$("#show").css("display","block"); 
     	//	$("#boxnumber").text(stockNumber.text());
     	//}
	});
	
	//增加点击
	$("#addNumber").click(function(){
		buyNumber.val(parseInt(buyNumber.val())+1);
	});
	
	//减少点击
	$("#minusNumber").click(function(){
		if(parseInt(buyNumber.val())>1){
			buyNumber.val(parseInt(buyNumber.val())-1);
		}
	});

	//选择支付方式
	$("#selectPayMethod").change(function(){ 
		var selectPayMethod = $("#selectPayMethod");
		var showPayMethod = $("#showPayMethod");
		var showPayMethodHtml = "";
		
		//现金
		if(selectPayMethod.val()=="02"){
			var cash_value = $("#cash_value");
			showPayMethodHtml = '<div class="userpoints">商品单价：<b>'+cash_value.text()+'</b>元</div>';
		}
		
		//分分通积分
		if(selectPayMethod.val()=="00"){
			var fftpoint_value = $("#fftpoint_value");
			showPayMethodHtml = '<div class="userpoints">商品单价：<b>'+fftpoint_value.text()+'</b>分</div>';
		}
		
		//银行积分
		if(selectPayMethod.val()=="01"){
			var bankpoint_value = $("#bankpoint_value");
			showPayMethodHtml = '<div class="userpoints">商品单价：<b>'+bankpoint_value.text()+'</b>分</div>';
		}
		
		//分分通积分+现金
		if(selectPayMethod.val()=="03"){
			var fftpoint_fix_value = $("#fftpoint_fix_value");
			var fftcash_fix_value = $("#fftcash_fix_value");
			showPayMethodHtml='<div class="userpoints">商品单价：<b>'+fftpoint_fix_value.text()+'</b>分 + <b>'+fftcash_fix_value.text()+'</b>元</div> ';
		} 
		
		//银行积分+现金
		if(selectPayMethod.val()=="04"){
			var bankpint_fix_value = $("#bankpint_fix_value");
			var bankcash_fix_value = $("#bankcash_fix_value");
			showPayMethodHtml='<div class="userpoints">商品单价：<b>'+bankpint_fix_value.text()+'</b>分 + <b>'+bankcash_fix_value.text()+'</b>元</div> ';
		} 
		
		//分分通积分+现金(比例)
		if(selectPayMethod.val()=="05"){
			showPayMethodHtml='<div class="meinput validate" id="formlist"> <strong>商品单价：<input type="text" class="loginText4" id="points"><i>可使用积分：<span id="default_fftpoint">1080-2100</span> 分，您还需要支付现金<span id="default_fftpointCash">200</span> 元</i><span id="myMessage"></span></strong></div>';
			showPayMethod.html(showPayMethodHtml);
			
			var fftpointsRatio_value = $("#fftpointsRatio_value").text();
			var fftpointsCashRatio_value = $("#fftpointsCashRatio_value").text();
			var fftpointsRatio = fftpointsRatio_value.split("-");
			var fftpointsCashRatio = fftpointsCashRatio_value.split("-");
			var default_fftpoint = $("#default_fftpoint");
			var default_fftpointCash = $("#default_fftpointCash");
			var total_cash=parseInt(fftpointsRatio[0])+parseInt(fftpointsCashRatio[0]);
			var myMessage = $("#myMessage");
			
			$("#points").val(fftpointsRatio[0]);//积分值
			default_fftpoint.text(fftpointsRatio_value);//使用范围
			default_fftpointCash.text(fftpointsCashRatio[0]);
			$("#usePointRaioValue").val(fftpointsRatio[0]);
			
			$("#points").focus(function(){
  				$(this).attr("className","focusText4");
  				myMessage.html("<h4>请输入兑换积分</h4>");
			});
			
			
			$("#points").bind("blur", function(){
				var points_value=$(this).val();
				
				if($.trim(points_value) == ""){
					$(this).val(fftpointsRatio[0]);
					return;
				}
				
				var regexp_value=/^[1-9]\d*$/;
				
				if(!regexp_value.test(points_value)){
					$(this).attr("className","errorText4");
  					myMessage.html("<h3>请正确输入积分</h3>");
  					return;
				}
				
  				if(parseInt(points_value)<parseInt(fftpointsRatio[0]) || parseInt(points_value)>parseInt(fftpointsRatio[1])){
  					$(this).attr("className","errorText4");
  					myMessage.html("<h3>请正确输入积分范围</h3>");
  					return;
  				}
  				else{
  					$(this).attr("className","loginText4");
  					myMessage.html("<h2>输入成功</h2>");
  					$("#usePointRaioValue").val(points_value);
  					default_fftpointCash.text(total_cash-parseInt(points_value));
  				}
			}); 
			return ;
		} 
		
		//银行积分+现金(比例)
		if(selectPayMethod.val()=="06"){
			showPayMethodHtml='<div class="meinput validate" id="formlist"> <strong>商品单价：<input type="text" class="loginText4" id="points"><i>可使用积分：<span id="default_bankpoint">1080-2100</span>分，您还需要支付现金<span id="default_bankpointCash">200</span>元</i><span id="myMessage"></span></strong></div>';
			showPayMethod.html(showPayMethodHtml);
			
			var bankpointsRatio_value = $("#bankpointsRatio_value").text();
			var bankpointsCashRatio_value = $("#bankpointsCashRatio_value").text();
			var bankpointsRatio = bankpointsRatio_value.split("-");
			var bankpointsCashRatio = bankpointsCashRatio_value.split("-");
			var default_bankpoint = $("#default_bankpoint");
			var default_bankpointCash = $("#default_bankpointCash");
			var total_cash=parseInt(bankpointsRatio[0])+parseInt(bankpointsCashRatio[0]);
			var myMessage = $("#myMessage");
			
			$("#points").val(bankpointsRatio[0]);//积分值
			default_bankpoint.text(bankpointsRatio_value);//使用范围
			default_bankpointCash.text(bankpointsCashRatio[0]);
			$("#usePointRaioValue").val(bankpointsRatio[0]);
			
			$("#points").focus(function(){
  				$(this).attr("className","focusText4");
  				myMessage.html("<h4>请输入兑换积分</h4>");
			});
			
			$("#points").bind("blur", function(){
				var points_value=$(this).val();
				
				if($.trim(points_value) == ""){
					$(this).val(bankpointsRatio[0]);
					return;
				}
				
				var regexp_value=/^[1-9]\d*$/;
				
				if(!regexp_value.test(points_value)){
					$(this).attr("className","errorText4");
  					myMessage.html("<h3>请正确输入积分</h3>");
  					return;
				}
				
  				if(parseInt(points_value)<parseInt(bankpointsRatio[0]) || parseInt(points_value)>parseInt(bankpointsRatio[1])){
  					$(this).attr("className","errorText4");
  					myMessage.html("<h3>请正确输入积分范围</h3>");
  					return;
  				}
  				else{
  					$(this).attr("className","loginText4");
  					myMessage.html("<h2>输入成功</h2>");
  					$("#usePointRaioValue").val(points_value);
  					default_bankpointCash.text(total_cash-parseInt(points_value));
  				}
			}); 
			return ;
		} 
		showPayMethod.html(showPayMethodHtml);
	});
	
	
	//立即兑换
	$('a.exchangeBut').click(function() {
	
		var formObject = document.getElementById('formlist');
		if(formObject!=null){
			var subm = formObject.getElementsByTagName('h3').length;
			if(subm){
				return;
			}
		}
		
		var goodsExchangeRackId = $("#goodsExchangeRackId");
		var selectPayMethod = $("#selectPayMethod");
		
		if($.trim(selectPayMethod.val()) == ""){
			$.layer({
			title:['分分通提示您',true],
			time:3,
			area : ['auto','auto'],
			dialog : {msg:'请选择支付方式',type : 8}	
		});
					
			return false;	
		}
		else{
			$('#exchangeSpecialtyViewForm').submit()
		}
		
		
		return false;
    });
	
})

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

<div class="miss" style="display:none" id="show">
 <h2>抱歉，数量有限，您最多只能购买 <b id="boxnumber"></b> 件</h2>
 <a href="#" onclick="closed()"></a>
</div>

<div class="box1010 pt10 clear">
<!--内容开始-->
<form id="exchangeSpecialtyViewForm" method="post" action="exchange_bankPoint_view.action">
 	<input id="goodsExchangeRackId"  name="exchangeTempBean.goodsRackId" value="${(goodsExchangeRack.id?c)!""}" type="hidden">
	<input name="exchangeTempBean.goodsCategoryId" value="${(goodsExchangeRack.goodsCategoryId)!""}" type="hidden">
	<input id="usePointRaioValue" name="exchangeTempBean.usePointRaioValue" type="hidden">
 
  <div class="points fl">
     <div class="leffShow">
	<#if goodsExchangeRack?exists && goodsExchangeRack.goods.sourceUrl?exists>
		<img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(goodsExchangeRack.goods.sourceUrl)!""}">
	<#else>
		<img src="${base}/template/common/images/moren.gif">
     </#if>     
     </div> 
     <div class="rightInfor">
       <dl>
       <dt>${(goodsExchangeRack.goodsRackName?html)!""}</dt>
       <#if goodsExchangeRack?exists>
       <#assign cash=goodsExchangeRack.cashPricing>
       <#assign cashPricing=cash?number>
       <#if goodsExchangeRack?exists && goodsExchangeRack.isCash=="1" >
       		<dd>现金兑换：<b id="cash_value">${(goodsExchangeRack.cashPricing)!"0"}</b>元</dd>
       </#if>
       
       <#if goodsExchangeRack?exists && goodsExchangeRack?exists && goodsExchangeRack.isFftPoint=="1" >
       		<dd>分分通积分兑换：<b id="fftpoint_value">${(goodsExchangeRack.fftPointPricing)!"0"}</b>分</dd>
       </#if>
       
       <#if goodsExchangeRack?exists && goodsExchangeRack.isBankPoint=="1" >
       		<dd>银行积分兑换：<b id="bankpoint_value">${(goodsExchangeRack.bankPointPricing)!"0"}</b>分</dd>
       </#if>
       
       <#if goodsExchangeRack?exists && goodsExchangeRack.isFftpointCash=="1" >
       		<#assign fftpointsAndCashArray=goodsExchangeRack.fftpointCashPricing?split('|')>
       		<dd>分分通积分+现金兑换：<b id="fftpoint_fix_value">${(fftpointsAndCashArray[0])!""}</b>分+<b id="fftcash_fix_value">${(fftpointsAndCashArray[1])!""}</b>元</dd>
       </#if>
       
       <#if goodsExchangeRack?exists && goodsExchangeRack.isBankpointCash=="1" >
       		<#assign bankpointsAndCashArray=goodsExchangeRack.bankpointCashPricing?split('|')>
       		<dd>银行积分+现金兑换：<b id="bankpint_fix_value">${(bankpointsAndCashArray[0])!""}</b>分+<b id="bankcash_fix_value">${(bankpointsAndCashArray[1])!""}</b>元</dd>
       </#if>
       
       <#if goodsExchangeRack?exists && goodsExchangeRack.isFftpointcashRatioPricing=="1" >
       		<#assign fftpointsAndCashRatioArray=goodsExchangeRack.fftpointcashRatioPricing?split('|')>
       		<#assign fftpointsRatioMin=fftpointsAndCashRatioArray[0]>
       		<#assign fftpointsRatioMinPercent=fftpointsRatioMin?number>
       		<#assign fftpointsRatioMinPercentDivision=fftpointsRatioMinPercent/100>
       		<#assign fftpointsRatioMax=fftpointsAndCashRatioArray[1]>
       		<#assign fftpointsRatioMaxPercent=fftpointsRatioMax?number>
       		<#assign fftpointsRatioMaxPercentDivision=fftpointsRatioMaxPercent/100>
       		<#assign fftpointsMin=cashPricing*fftpointsRatioMinPercentDivision>
       		<#assign fftpointsMax=cashPricing*fftpointsRatioMaxPercentDivision>
       		<dd>分分通积分+现金兑换：<b id="fftpointsRatio_value">${(fftpointsMin)!""}-${(fftpointsMax)!""}</b>分+<b id="fftpointsCashRatio_value">${(cashPricing-fftpointsMin)!""}-${(cashPricing-fftpointsMax)!""}</b>元</dd>
       </#if>
       
       <#if goodsExchangeRack?exists && goodsExchangeRack.isBankpointcashRatioPricing=="1" >
       		<#assign bankpointsAndCashRatioArray=goodsExchangeRack.bankpointcashRatioPricing?split('|')>
       		<#assign bankpointsRatioMin=bankpointsAndCashRatioArray[0]>
       		<#assign bankpointsRatioMinPercent=bankpointsRatioMin?number>
       		<#assign bankpointsRatioMinPercentDivision=bankpointsRatioMinPercent/100>
       		<#assign bankpointsRatioMax=bankpointsAndCashRatioArray[1]>
       		<#assign bankpointsRatioMaxPercent=bankpointsRatioMax?number>
       		<#assign bankpointsRatioMaxPercentDivision=bankpointsRatioMaxPercent/100>
       		<#assign bankpointsMin=cashPricing*bankpointsRatioMinPercentDivision>
       		<#assign bankpointsMax=cashPricing*bankpointsRatioMaxPercentDivision>
       		<dd>银行积分+现金兑换：<b id="bankpointsRatio_value">${bankpointsMin}-${bankpointsMax}</b>分+<b id="bankpointsCashRatio_value">${cashPricing-bankpointsMin}-${cashPricing-bankpointsMax}</b>元</dd>
       </#if>
      </#if>
       </dl>
     </div>   

       <div class="buyBox">
         <div class="mebuy">
             我要买：
             <a href="javascript:void(0)" id="minusNumber">-</a>
             <input name="exchangeTempBean.buyNumber" type="text"  class="loginText5" id="number" value="1" maxlength="2" class="loginText5">
             <a href="javascript:void(0)" id="addNumber">+</a>
         </div>
         
         <div class="meSelect">
           <div class="fl mt9">支付方式：银行积分</div>  
           <span class="fl" id="uboxstyle" style="display:none">  
           <select id="selectPayMethod" name="exchangeTempBean.payMethod">
	               <#if goodsExchangeRack?exists && goodsExchangeRack.isBankPoint=="1" >
	             	  <option  value="01">银行积分</option>
	              </#if>
            </select>
            </span>
          </div>
		
		<div id="showPayMethod"></div>
         
       <a href="javascript:void(0);" class="exchangeBut"><img src="${base}/template/web/images/dhbtn.png"></a> 

       </div>     
		<script type="text/javascript" src="${base}/template/common/js/select.js"></script>        
    <div class="contantInr"> ${(goodsExchangeRack.goodsRackDetail)!""}</div>                                 
  </div> 
<!--内容结束-->
</form>

  
 <#include "/WEB-INF/templates/common/exch_right.ftl">       
</div>
<#if !goodsExchangeRack?exists>
<script type="text/javascript">
$(document).ready(function (){
	$.layer({
			title:['分分通提示您',true],
			area : ['auto','auto'],
			dialog : {msg:'参数异常！',type : 3}	
		});
});
</script>
</#if>
<!--底部开始-->  
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
</body>
</html>