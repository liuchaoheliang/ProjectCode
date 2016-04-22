<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通-话费充值</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/select2.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>
<link href="${base}/template/web/css/table.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/rightimg.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/explain.css" rel="stylesheet" type="text/css" />
</head>
<script type="text/javascript" src="${base}/template/common/js/form.js"></script>
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
<!--充值开始-->
<form id="exchangeTelephonefeeViewForm" method="post" action="exchange_telephonefee_view.action">
	<input id="goodsExchangeRackId"  name="exchangeTempBean.goodsRackId" value="${(goodsExchangeRack.id?c)!""}" type="hidden">
	<input name="exchangeTempBean.goodsCategoryId" value="${(goodsExchangeRack.goodsCategoryId)!""}" type="hidden">
	<input id="usePointRaioValue" name="exchangeTempBean.usePointRaioValue" type="hidden">
	

  <div class="tableList fl validate" id="formlist"> 
   <table>
  <tr>
    <td colspan="2" class="titlePay">充值后一分钟内到账，支持中国移动、联通、电信充值。</td>
    </tr>
  <tr>
    <th>手机号码：</th>
    <td><strong><input name="textfield" type="text"  class="loginText" id="phone" maxlength="11"><u>*</u></strong></td>
    </tr>
  <tr>
    <th>确认手机号码：</th>
    <td><strong><input type="text" name="exchangeTempBean.mobile"  class="loginText" id="phone2" maxlength="11"><u>*</u></strong></td>
  </tr>
  <tr>
    <th>面值：</th>  
    <td>
    ${(goodsExchangeRack.cashPricing)!""}元
   </td>
  </tr>
  <tr>
    <th>&nbsp;</th>
    <td class="grayFont">温馨提示：如遇月初月末充值高峰，到账时间可能稍有延迟，敬请谅解。</td>
  </tr>
   </table> 
   
    <div class="payselect">
     <div class="heightmiddle">
      <div class="selectfont">请选择支付方式：</div>
      <span class="fl" id="uboxstyle"> 
            <select id="selectPayMethod" name="exchangeTempBean.payMethod">
            	<!--<option  value="">选择支付方式</option>-->
            	 	<option  value="09">现金（支付宝支付）</option>
             	  <#if goodsExchangeRack?exists && goodsExchangeRack.isCash=="1" >
	             	 <option  value="02">现金（珠海农商银行手机银行卡支付）</option>
	              </#if>
	               <#if goodsExchangeRack?exists && goodsExchangeRack.isFftPoint=="1">
	             	<option  value="00" >分分通积分</option>
	              </#if>
	               <#if goodsExchangeRack?exists && goodsExchangeRack.isBankPoint=="1" >
	             	  <option  value="01">银行积分</option>
	              </#if>
	              <#if goodsExchangeRack?exists && goodsExchangeRack.isFftpointCash=="1" >
	             	<option  value="03">分分通积分+现金</option>
	              </#if>
	               <#if goodsExchangeRack?exists && goodsExchangeRack.isBankpointCash=="1" >
	             	<option  value="04">银行积分+现金</option>
	              </#if>
	               <#if goodsExchangeRack?exists && goodsExchangeRack.isFftpointcashRatioPricing=="1" >
	             	<option  value="05">分分通积分+现金</option>
	              </#if>
	               <#if goodsExchangeRack?exists && goodsExchangeRack.isBankpointcashRatioPricing=="1" >
	             	<option  value="06">银行积分+现金</option>
	              </#if>
            </select>
            </span>
        </div>
        <center>
<div id="formlist" class="validate mb5"><div id="showPayMethod"></div></div>
  </center>
  <script type="text/javascript" src="${base}/template/common/js/select.js"></script>    
</div> 

   <div class="w150 abtn pt15">
     <a href="javascript:void(0);" id="send"><div class="textBtn"><B>确定</B></div></a>   
   </div> 
    
    <div class="explain">
      <li>1、分分通平台话费充值服务覆盖中国移动、中国联通、中国电信的全国范围内的全部品牌的手机号码，采用在线支付，实时充值，无需等待。</li>
      <li>2、在线直充的话费充值商品没有发送卡号密码的过程，由方付通实时为您的手机号码进行充值。目前可选的充值金额有50元、100元及300元等。</li>
      <li>3、系统是实时提交话费充值的，所以请确认您的手机号码填写正确，若手机号码填写错误，款项无法退回。由于话费充值的特殊性，充值成功后不予退款。</li>
      <li>4、最终到账请以实际时间为准。如遇超时未到账，请检查您的订单中手机号码的填写是否有误，同时请及时联系客服处理，客服电话：0756-3827999</li>
    </div> 
  </div> 
<!--充值结束-->
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
<script type="text/javascript">
 	var error = [
      "不能为空",
	  "请输入6-15位英文或数字",
	  "两次密码输入不一致",
	  "请正确输入11位手机号码",
	  "您输入的邮箱格式不正确",
	  "请输入4-20位字符，不能为纯数字，必须以字母开头",
	  "验证码是6位数字",
	  "两次输入的手机号不一样",
	  "请正确输入价格，精确到小数点后两位",
	  "请正确输入数量",
	  "请正确输入积分，精确到小数点后两位",
	  "请正确输入充值金额",
	  "请正确输入积分数，例如：10，20，130",
	  "请正确输入手机号或邮箱",
	  "对不起，你拥有的积分不足",
	  "请正确输入19位以622859开头的珠海银行卡号",
	  "姓名只能输入中文且不能含有空格",
	  "新密码不能和原密码相同",
	  "请正确输入您的身份证号"
  ]	
 	


$(document).ready( function() {
	$("#selectPayMethod").change(function(){ 
		var selectPayMethod = $("#selectPayMethod");
		var showPayMethod = $("#showPayMethod");
		var showPayMethodHtml = "";
		
		//支付宝
		if(selectPayMethod.val()=="09"){
			var cash_value = $("#cash_value");
			showPayMethodHtml = '<div class="userpoints">商品单价：<b>'+cash_value.text()+'</b>元</div>';
		}
		
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
	
	
	

	function telePhoneSend(){
		var selectPayMethod = $("#selectPayMethod");
		var phone2 = $("#phone2");
			
		if($.trim(selectPayMethod.val()) == ""){
				$.layer({
					title:['分分通提示您',true],
					time:3,
					area : ['auto','auto'],
					dialog : {msg:'请选择支付方式',type : 8}	
				});
						
				return;	
		}else if($.trim(phone2.val()) == ""){
				$.layer({
					title:['分分通提示您',true],
					time:3,
					area : ['auto','auto'],
					dialog : {msg:'请再次输入手机号码',type : 8}	
				});
				return;
				
		}else{
			if($.trim(phone2.val()) != $.trim($('#phone').val())){
				$.layer({
					title:['分分通提示您',true],
					time:3,
					area : ['auto','auto'],
					dialog : {msg:'手机号码不一致',type : 8}	
				});
				return;
			}
			$(this).unbind("click");
			$("div",this).attr("class","gryBtn");	
			$('B',this).html("请等待...");
			$('#exchangeTelephonefeeViewForm').submit()
		}
		
		return;		
	}
	
	//验证表单数据并提示
	$("#send").click(function(){
		var ospan  = document.createElement('span')	;
		var formObject = document.getElementById('formlist');
		var ob = formObject.getElementsByTagName('u');
		for (i = 0; i < ob.length; i++) {
			var oc = ob[i].parentNode.getElementsByTagName('input')[0];
			if (oc.value == '') {
				ospan.innerHTML = '<h3>' + error[0] + '</h3>';
				ob[i].parentNode.appendChild(ospan);
				if (ob[i].parentNode.getElementsByTagName('span').length == 2) {
					ob[i].parentNode.removeChild(ob[i].parentNode.getElementsByTagName('span')[0])
				}
				if (oc.id == "username" || oc.id == "password" || oc.id == "password2" || oc.id == "phone" || oc.id == "phone2") {
					oc.className = 'errorText'
				} else if (oc.id == "phonecode") {
					oc.className = 'errorText3'
				} else if (oc.id == "code") {
					oc.className = 'errorText4'
				}
				break;
			}
		}
	    var subm = formObject.getElementsByTagName('h3').length;
		if(subm){
			return;
		}else{
			telePhoneSend();
		}		
	})
})

</script>
</body>
</html>
<!---->
<div id="myDiv" style="display:none">
<#if goodsExchangeRack?exists>
<#assign cash=goodsExchangeRack.cashPricing>
       <#assign cashPricing=cash?number>
       <#if goodsExchangeRack?exists&& goodsExchangeRack.isCash=="1" >
       		<dd>现金兑换：<b id="cash_value">${(goodsExchangeRack.cashPricing)!"0"}</b>元</dd>
       </#if>
       
       <#if goodsExchangeRack.isFftPoint=="1" >
       		<dd>分分通积分兑换：<b id="fftpoint_value">${(goodsExchangeRack.fftPointPricing)!"0"}</b>分</dd>
       </#if>
       
       <#if goodsExchangeRack.isBankPoint=="1" >
       		<dd>银行积分兑换：<b id="bankpoint_value">${(goodsExchangeRack.bankPointPricing)!"0"}</b>分</dd>
       </#if>
       
       <#if goodsExchangeRack.isFftpointCash=="1" >
       		<#assign fftpointsAndCashArray=goodsExchangeRack.fftpointCashPricing?split('|')>
       		<dd>分分通积分+现金兑换：<b id="fftpoint_fix_value">${fftpointsAndCashArray[0]}</b>分+<b id="fftcash_fix_value">${fftpointsAndCashArray[1]}</b>元</dd>
       </#if>
       
       <#if goodsExchangeRack.isBankpointCash=="1" >
       		<#assign bankpointsAndCashArray=goodsExchangeRack.bankpointCashPricing?split('|')>
       		<dd>银行积分+现金兑换：<b id="bankpint_fix_value">${bankpointsAndCashArray[0]}</b>分+<b id="bankcash_fix_value">${bankpointsAndCashArray[1]}</b>元</dd>
       </#if>
       
       <#if goodsExchangeRack.isFftpointcashRatioPricing=="1" >
       		<#assign fftpointsAndCashRatioArray=goodsExchangeRack.fftpointcashRatioPricing?split('|')>
       		<#assign fftpointsRatioMin=fftpointsAndCashRatioArray[0]>
       		<#assign fftpointsRatioMinPercent=fftpointsRatioMin?number>
       		<#assign fftpointsRatioMinPercentDivision=fftpointsRatioMinPercent/100>
       		<#assign fftpointsRatioMax=fftpointsAndCashRatioArray[1]>
       		<#assign fftpointsRatioMaxPercent=fftpointsRatioMax?number>
       		<#assign fftpointsRatioMaxPercentDivision=fftpointsRatioMaxPercent/100>
       		<#assign fftpointsMin=cashPricing*fftpointsRatioMinPercentDivision>
       		<#assign fftpointsMax=cashPricing*fftpointsRatioMaxPercentDivision>
       		<dd>分分通积分+现金兑换：<b id="fftpointsRatio_value">${fftpointsMin}-${fftpointsMax}</b>分+<b id="fftpointsCashRatio_value">${cashPricing-fftpointsMin}-${cashPricing-fftpointsMax}</b>元</dd>
       </#if>
       
       <#if goodsExchangeRack.isBankpointcashRatioPricing=="1" >
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
</div>
</#if>