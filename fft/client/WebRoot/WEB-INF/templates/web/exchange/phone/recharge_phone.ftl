<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通</title>
<#include "/WEB-INF/templates/common/include.ftl">

<link href="${base}/template/web/css/table.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/rightimg.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/explain.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/select.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${base}/template/common/js/list.js"></script>
<script src="${base}/template/common/js/phonecheck.js" type="text/javascript"></script>
<script type="text/javascript" src="${base}template/common/js/closeDialog.js"></script>
<link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>

<script type="text/javascript">
  var error = [
      "不能为空",
	  "请正确输入11位手机号码",
	  "两次输入的手机号不一样"
  ]		

function checkAndSubmit1() {
	var oText =document.getElementById('phonelist');
	var ospan=document.createElement("span");
	var formObject=document.getElementById("phonelist");
	var ob=formObject.getElementsByTagName("u");
	for (i = 0; i < ob.length; i++) {
		var oc = ob[i].parentNode.getElementsByTagName('input')[0];
		if (oc.value == '') {
			ospan.innerHTML = '<h3>' + error[0] + '</h3>';
			ob[i].parentNode.appendChild(ospan);
			if (ob[i].parentNode.getElementsByTagName('span').length == 2) {
				ob[i].parentNode.removeChild(ob[i].parentNode.getElementsByTagName('span')[0])
			}
			if (oc.id == "username" || oc.id == "password" ||  oc.id == "phone" || oc.id == "phone2" || oc.id == "people" || oc.id =="seller") {
				oc.className = 'errorText'
			} else if (oc.id == "phonecode") {
				oc.className = 'errorText3'
			} else if (oc.id == "cash") {
				oc.className = 'errorText4'
			}
			break;
					
		}
	}
 	var subm = oText.getElementsByTagName('h3').length;
	if(subm){
				 return ;
	}else{
		checkAndSubmit();
	}
}





function checkAndSubmit(){
	var phone=document.getElementById("phone").value;
	var phone1=document.getElementById("phone2").value;
	if(typeof(phone) == undefined || phone==null||phone==""){
		$.layer({
			title:['分分通提示您',true],
			time:3,
			area : ['auto','auto'],
			dialog : {msg:'手机号码不能为空',type : 8}	
		});
		return ;
	}
	if(!/^[0-9]*[1-9][0-9]*$/.test(phone)||!/^[0-9]*[1-9][0-9]*$/.test(phone1)){
		$.layer({
			title:['分分通提示您',true],
			time:3,
			area : ['auto','auto'],
			dialog : {msg:'请输入正确的手机号码',type : 8}	
		});
		return ;
	}
	if(phone.length!=11 || phone1.length!=11){
		$.layer({
			title:['分分通提示您',true],
			time:3,
			area : ['auto','auto'],
			dialog : {msg:'请确认手机号码位数',type : 8}	
		});
		return ;
	}
	if(phone!=phone1){
		$.layer({
			title:['分分通提示您',true],
			time:3,
			area : ['auto','auto'],
			dialog : {msg:'两次号码不一致',type : 8}	
		});
		return ;
	}
	if(submitCheck()){
		exch.submit();
	}else{
		return ;
	}
	
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

<#if errorMsg?exists>
<div class="miss">
<div id="errorMsg"><h2>${errorMsg?if_exists} </h2></div>
 
	<a href="#" onclick="closed()"></a>
 
</div>
</#if>
<div class="miss" style="display:none" id="show">
	<div id="errorMsg"><h2>抱歉，数量有限，您最多只能购买 <b id="boxnumber"></b> 件</h2></div>
 
	<a href="#" onclick="closed()"></a>
</div>

<div class="box1010 pt10 clear">
<!--内容开始-->
<#if pager?exists && pager.list?exists>

	<form id="exch" name="exch" method="post" action="exchange_recharge_phone.action">
 <!--充值开始-->
  <div class="tableList fl validate" id="phonelist">
   <table>
  <tr>
    <td colspan="2" class="titlePay">充值后一分钟内到账，支持中国移动、联通、电信充值。</td>
    </tr>
  <tr>
    <th>手机号码：</th>
    <td><strong><input type="text" name="transAddtionalInfoVo.phone" id="phone" maxlength="11"  class="loginText"><u>*</u></strong></td>
    </tr>
  <tr>
    <th>确认手机号码：</th>
    <td><strong><input  type="text" name="transAddtionalInfoVo.confirmedPhone"  id="phone2" maxlength="11" class="loginText" /><u>*</u></strong></td>
  </tr>
   <input type="hidden" name="pager.goodsCategoryId"  value="100001006">
  <input type="hidden" name="transDetails.goodsNumber"  value="1" id="value">
  
  
  <tr>
    <th>面值：</th>  
    <td>

    <#assign isFirstGoods=true>
    <#assign firstGoodsId="">
    <#assign firstGoodsPrice="">
    <#assign firstPayMethod="">
      <#list pager.list as aGood>
      	
		<input type="radio" name="transDetails.goodsRackId" id="aPhoneGoodsId" value="#{aGood.id?if_exists}"   onclick="changePriceShowAndPayMethod('#{aGood.id?if_exists}')"   <#if isFirstGoods>checked</#if> /> ${aGood.cashPricing}元
		<#if isFirstGoods>
		<#assign firstGoodsId="#{aGood.id?if_exists}">
		</#if> 
		 <#assign aGoodsPriceId=aGood.id?c+'-price'>
		 <#assign aGoodsOnlyPriceId=aGood.id?c+'-onlyprice'>
		  <input type="hidden"  id="${aGoodsOnlyPriceId?if_exists}" value="${aGood.cashPricing?if_exists}"  /> 
		<#assign avalidatePayMethod="">
		<#assign avalidatePrice="">
			<#if aGood.isCash=="1" >
             	 <#if avalidatePayMethod!="">
             		 <#assign avalidatePayMethod=avalidatePayMethod+",">
             	 </#if>
             	  <#assign avalidatePayMethod=avalidatePayMethod+"02">
             	  
             	  <#if avalidatePrice!="">
             		 <#assign avalidatePrice=avalidatePrice+" 或  ">
             	 </#if>
             	
             	  <#assign avalidatePrice=avalidatePrice+aGood.cashPricing>
             	   <#assign avalidatePrice=avalidatePrice+'元'>
              </#if>
               <#if aGood.isFftPoint=="1">
             	 <#if avalidatePayMethod!="">
             	 	<#assign avalidatePayMethod=avalidatePayMethod+",">
             	 </#if>
             	  <#assign avalidatePayMethod=avalidatePayMethod+"00">
             	  
             	   <#if avalidatePrice!="">
             		 <#assign avalidatePrice=avalidatePrice+" 或  ">
             	 </#if>
             	 
             	  <#assign avalidatePrice=avalidatePrice+aGood.fftPointPricing>
             	   <#assign avalidatePrice=avalidatePrice+'个分分通积分'>
              </#if>
               <#if aGood.isBankPoint=="1" >
             	  <#if avalidatePayMethod!="">
             	 	<#assign avalidatePayMethod=avalidatePayMethod+",">
             	 </#if>
             	  <#assign avalidatePayMethod=avalidatePayMethod+"01">
             	  
             	   <#if avalidatePrice!="">
             		 <#assign avalidatePrice=avalidatePrice+" 或  ">
             	 </#if>
             	  <#assign avalidatePrice=avalidatePrice+aGood.bankPointPricing>
             	   <#assign avalidatePrice=avalidatePrice+'个银行积分'>
              </#if>
              <#if aGood.isFftpointCash=="1" >
             	 <#if avalidatePayMethod!="">
             	 	<#assign avalidatePayMethod=avalidatePayMethod+",">
             	 </#if>
             	  <#assign avalidatePayMethod=avalidatePayMethod+"03">
             	  
             	    <#if avalidatePrice!="">
             		 <#assign avalidatePrice=avalidatePrice+" 或  ">
             	 </#if>
             	 <#assign fftpointCashPricing=aGood.fftpointCashPricing>
             	 <#assign fftpointCashPricingArray=fftpointCashPricing?split('|')>
             	 
             	  <#assign avalidatePrice=avalidatePrice+fftpointCashPricingArray[0]>
             	  <#assign avalidatePrice=avalidatePrice+'个分分通积分 +'>
             	  <#assign avalidatePrice=avalidatePrice+fftpointCashPricingArray[1]>
             	   <#assign avalidatePrice=avalidatePrice+'元'>
              </#if>
               <#if aGood.isBankpointCash=="1" >
             	 <#if avalidatePayMethod!="">
             	 	<#assign avalidatePayMethod=avalidatePayMethod+",">
             	 </#if>
             	  <#assign avalidatePayMethod=avalidatePayMethod+"04">
             	  
             	  
             	   <#if avalidatePrice!="">
             		 <#assign avalidatePrice=avalidatePrice+" 或  ">
             	 </#if>
             	 
             	  <#assign bankpointCashPricing=aGood.bankpointCashPricing>
             	 <#assign bankpointCashPricingArray=bankpointCashPricing?split('|')>
             	  <#assign bankpointCashPricingArrayp=bankpointCashPricingArray[0] >
             	  <#assign avalidatePrice=avalidatePrice+bankpointCashPricingArrayp >
             	  <#assign avalidatePrice=avalidatePrice+"个银行积分+">
             	  <#assign avalidatePrice=avalidatePrice+bankpointCashPricingArray[1]>
             	   <#assign avalidatePrice=avalidatePrice+"元">
              </#if>
              
            <#assign bankPointsValueMax=0>
            <#assign fftPointsValueMax=0>
            <#assign bankPointsValueMin=0>
            <#assign fftPointsValueMin=0>
            <#assign pointsIsUsable="false">
            <#if aGood.isFftpointcashRatioPricing?exists && aGood.isFftpointcashRatioPricing=="1" && aGood.fftpointcashRatioPricing?exists >
            	 <#assign pointsIsUsable="true">
            	  <#assign fftpointsMinMax=aGood.fftpointcashRatioPricing?split('|')>
            	   <#assign fftPointsValueMin=fftpointsMinMax[0]>
            	 <#assign fftPointsValueMax=fftpointsMinMax[1]>
            	 
            	 <#if avalidatePayMethod!="">
             	 	<#assign avalidatePayMethod=avalidatePayMethod+",">
             	 </#if>
             	  <#assign avalidatePayMethod=avalidatePayMethod+"05">
             	  
            </#if>
            <#if aGood.isBankpointcashRatioPricing?exists && aGood.isBankpointcashRatioPricing=="1" && aGood.bankpointcashRatioPricing?exists>
            	 <#assign pointsIsUsable="true">
            	 <#assign bankPointsValueMax=0>
            	  <#assign bankPointsMinMax=aGood.bankpointcashRatioPricing?split('|')>
            	  <#assign bankPointsValueMin=bankPointsMinMax[0]>
            	 <#assign bankPointsValueMax=bankPointsMinMax[1]>
            	 
            	  <#if avalidatePayMethod!="">
             	 	<#assign avalidatePayMethod=avalidatePayMethod+",">
             	 </#if>
             	  <#assign avalidatePayMethod=avalidatePayMethod+"06">
            </#if>
            <#assign fftPoints=0>
            <#assign bankPoints="0">  
           
         
	      <#if isFirstGoods>
			<#assign firstGoodsPrice=avalidatePrice>
			<#assign firstPayMethod=avalidatePayMethod>
		  </#if>    
		 
		 <#assign aGoodsBankPointsValueMinId=aGood.id?c+'-bankPointsValueMin'>
		  <#assign aGoodsFftPointsValueMinId=aGood.id?c+'-fftPointsValueMin'>
		   <#assign aGoodsBankPointsValueMaxId=aGood.id?c+'-bankPointsValueMax'>
		    <#assign aGoodsFftPointsValueMaxId=aGood.id?c+'-fftPointsValueMax'>
		 
          
           <input type="hidden"   id="${aGoodsBankPointsValueMinId?if_exists}" value="${bankPointsValueMin?if_exists}" />
           <input type="hidden"    id="${aGoodsFftPointsValueMinId?if_exists}" value="${fftPointsValueMin?if_exists}" />
           <input type="hidden"   id="${aGoodsBankPointsValueMaxId?if_exists}" value="${bankPointsValueMax?if_exists}" />
           <input type="hidden"    id="${aGoodsFftPointsValueMaxId?if_exists}" value="${fftPointsValueMax?if_exists}" />
		
		<input type="hidden"  id="#{aGood.id?if_exists}" value="${avalidatePayMethod?if_exists}"  /> 
		<input type="hidden"  id="${aGoodsPriceId?if_exists}" value="${avalidatePrice?if_exists}"  /> 
		 <#if isFirstGoods>
			<input type="hidden"  id="firstPayMethod" value="${firstPayMethod?if_exists}"  /> 
		</#if>
		  <#assign isFirstGoods=false>
		  	<#assign avalidatePayMethod="">
		<#assign avalidatePrice="">
	    </#list>
    
    
     		<#if Session?exists && Session.pointsTypePointsAccountMap?exists && Session.pointsTypePointsAccountMap?exists>
            	<#if Session.pointsTypePointsAccountMap.FFTPlatform?exists && Session.pointsTypePointsAccountMap.FFTPlatform.points?exists>
              		<#assign fftPoints=Session.pointsTypePointsAccountMap.FFTPlatform.points>
            	</#if>
            	
	            <#if Session.pointsTypePointsAccountMap.ZHBank?exists && Session.pointsTypePointsAccountMap.ZHBank.points?exists>
	               <#assign bankPoints=Session.pointsTypePointsAccountMap.ZHBank.points>
	            </#if>
          </#if>
          
          <input type="hidden" id="firstGoodsId" value="${firstGoodsId?if_exists}" />
    		<input type="hidden"    id="fftPoints1" value="${fftPoints?if_exists}" />
          <input type="hidden"   id="bankPoints1" value="${bankPoints?if_exists}" />
          
           <input type="hidden"   id="cashFftPointsRatio" value="${cashFftPointsRatio?if_exists}" />
              <input type="hidden"    id="cashBankPointsRatio" value="${cashBankPointsRatio?if_exists}" />
               <input type="hidden" name="transDetails.bankPointsValueRealAll" id="bankPointsValueRealAll" />
              <input type="hidden" name="transDetails.fftPointsValueRealAll" id="fftPointsValueRealAll" />
              
              
                 <input type="hidden"   id="bankPointsValueMin" value="${bankPointsValueMin?if_exists}" />
              <input type="hidden"    id="fftPointsValueMin" value="${fftPointsValueMin?if_exists}" />
          
               <input type="hidden"   id="bankPointsValueMax" value="${bankPointsValueMax?if_exists}" />
             <input type="hidden"    id="fftPointsValueMax" value="${fftPointsValueMax?if_exists}" />
             
    </td>
  </tr>
  
  <tr>
    <th>价格：</th>
    <td class="redFont"><span id="showPrice">${firstGoodsPrice?if_exists}</span></td>
  </tr>
  
  <tr>
    <th>支付方式：</th>
    <td> 
    	<div class="payselect">
    	<div class="mt10 mb10" id="avalidatePayMethod"></div>
    	
         <div id="ta" style="display:none" >
   			<div>
         		 <strong>
		        <input name="" type="text" class="loginText4" id="points" onchange="sure();">
		        <i>
		        	需使用<b id="pointsType"></b>积分：
		        	<b id="usableFftPointsMin" style="display:none"></b>
			        <b id="usableBankPointsMin" style="display:none"></b>
			       <b>-</b>
			        <b id="usableFftPointsMax" style="display:none"></b>
			        <b id="usableBankPointsMax" style="display:none"></b>
			        	分，您还需要支付现金
			        <b id="currencyMinUsedFftPoints" style="display:none"></b>
			        <b id="currencyMinUsedBankPoints" style="display:none"></b>
			       <b id="connectionSign" > - </b>
			        <b id="currencyMaxUsedFftPoints" style="display:none"></b>
			        <b id="currencyMaxUsedBankPoints" style="display:none"></b>
			       	元
			       	</i>
		       	</strong>
         	</div>
   		</div>
   	</div>
    </td>
 <!--  hidden elements     start  -->
    <div style="display:none">
          		<span>积分：</span> -¥<b id="score">0</b>
          		<strong id="totleRMB">0</strong>
          		<span id="price">0</span>
            	<span id="totleprice">0</span>
     </div>
      <div style="display:none">
      	<input type="radio" name="pointsType" value="bank"  id="card">银行积分：
    	<input type="radio" name="pointsType" value="fft">分分通积分
    	<dd>分分通积分+现金兑换：
	       		<b id="usableFftPointsMinShow">10</b>-<b id="usableFftPointsMaxShow">300</b>分+<b id="currencyMinUsedFftPointsShow">10</b>-<b id="currencyMaxUsedFftPointsShow">100</b>元</dd>
	       		<dd>银行积分+现金兑换：<b id="usableBankPointsMinShow">10</b>-<b id="usableBankPointsMaxShow">300</b>分+<b id="currencyMinUsedBankPointsShow">10</b>-<b id="currencyMaxUsedBankPointsShow">100</b>元</dd>
   			
    </div>
   <!--  hidden elements     end  -->
  <input type="hidden"  name="trans.payMethod" id="payMethod" value=""  /> 
  
  <script type="text/javascript" src="${base}/template/web/js/phone.js"></script>  
   <script type="text/javascript" src="${base}/template/common/js/price.js"></script>
  
  <tr>
    <th>&nbsp;</th>
    <td class="grayFont">温馨提示：如遇月初月末充值高峰，到账时间可能稍有延迟，敬请谅解。</td>
  </tr>
   </table> 
   
    <div class="w120 abtn">
   <!--  <a href="javascript:checkAndSubmit()" id="send"><div class="textBtn"><B>确定</B></div></a>   --> 
     
     <a href="javascript:checkAndSubmit1()">
          <div id="send"  class="textBtn"><B>确定</B></div> 
          </a>  
   </div> 
    
    <div class="explain">
      <li>1、分分通平台话费充值服务覆盖中国移动、中国联通、中国电信的全国范围内的全部品牌的手机号码，采用在线支付，实时充值，无需等待。</li>
      <li>2、在线直充的话费充值商品没有发送卡号密码的过程，由方付通实时为您的手机号码进行充值。目前可选的充值金额有50元、100元及300元等。</li>
      <li>3、系统是实时提交话费充值的，所以请确认您的手机号码填写正确，若手机号码填写错误，款项无法退回。由于话费充值的特殊性，充值成功后不予退款。</li>
      <li>4、最终到账请以实际时间为准。如遇超时未到账，请检查您的订单中手机号码的填写是否有误，同时请及时联系客服处理，客服电话：0756-3827999</li>
    </div> 
<!--充值结束-->
 </div> 
  </form>
</#if>
<!--内容结束-->

   <#include "/WEB-INF/templates/common/exch_right.ftl">                    
</div>

<!--底部开始-->  
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
</body>
</html>
