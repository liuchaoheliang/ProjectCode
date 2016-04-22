<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/dropkick.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/points.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/rightimg.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/drop.js"></script>
<script type="text/javascript" src="${base}/template/common/js/form.js"></script>
<script type="text/javascript" src="${base}template/common/js/closeDialog.js"></script>
<!--
* Author:
* pengling@f-road.com.cn 
*/
-->

<script type="text/javascript">
function checkAndSubmit(){
var enteredPoints=document.getElementById("value").value;
	if(enteredPoints=="" || !/^[0-9]*[1-9][0-9]*$/.test(enteredPoints)){
		document.getElementById("errorMsg").innerHTML="<h2>请输入正确的数量！</h2>";
		document.getElementById("show").style.display="";
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
<#if pager?exists && pager.goods?exists>
	<form id="exch" name="exch" method="post" action="exchange_local_prodcut.action">
	 <input  name="transDetails.goodsRackId" value="#{pager.id}" type="hidden">
	 
	 		<#assign pointsIsUsable=false>
            <#assign bankPointsValueMax=0>
            <#assign fftPointsValueMax=0>
            <#assign bankPointsValueMin=0>
            <#assign fftPointsValueMin=0>
            <#assign pointsIsUsable="false">
            <#if pager.isFftpointcashRatioPricing?exists && pager.isFftpointcashRatioPricing=="1" && pager.fftpointcashRatioPricing?exists >
            	 <#assign pointsIsUsable="true">
            	  <#assign fftpointsMinMax=pager.fftpointcashRatioPricing?split('|')>
            	   <#assign fftPointsValueMin=fftpointsMinMax[0]>
            	 <#assign fftPointsValueMax=fftpointsMinMax[1]>
            </#if>
            <#if pager.isBankpointcashRatioPricing?exists && pager.isBankpointcashRatioPricing=="1" && pager.bankpointcashRatioPricing?exists>
            	 <#assign pointsIsUsable="true">
            	 <#assign bankPointsValueMax=0>
            	  <#assign bankPointsMinMax=pager.bankpointcashRatioPricing?split('|')>
            	  <#assign bankPointsValueMin=bankPointsMinMax[0]>
            	 <#assign bankPointsValueMax=bankPointsMinMax[1]>
            </#if>
            <#assign fftPoints=0>
            <#assign bankPoints="0">  
            <#if Session?exists && Session.pointsTypePointsAccountMap?exists && Session.pointsTypePointsAccountMap?exists>
            	<#if Session.pointsTypePointsAccountMap.FFTPlatform?exists && Session.pointsTypePointsAccountMap.FFTPlatform.points?exists>
              		<#assign fftPoints=Session.pointsTypePointsAccountMap.FFTPlatform.points>
            	</#if>
            	
	            <#if Session.pointsTypePointsAccountMap.ZHBank?exists && Session.pointsTypePointsAccountMap.ZHBank.points?exists>
	               <#assign bankPoints=Session.pointsTypePointsAccountMap.ZHBank.points>
	            </#if>
          </#if>
         
          <input type="hidden"    id="fftPoints1" value="${fftPoints?if_exists}" />
          <input type="hidden"   id="bankPoints1" value="${bankPoints?if_exists}" />
          
           <input type="hidden"   id="bankPointsValueMin" value="${bankPointsValueMin?if_exists}" />
              <input type="hidden"    id="fftPointsValueMin" value="${fftPointsValueMin?if_exists}" />
          
               <input type="hidden"   id="bankPointsValueMax" value="${bankPointsValueMax?if_exists}" />
              <input type="hidden"    id="fftPointsValueMax" value="${fftPointsValueMax?if_exists}" />
             <input type="hidden"   id="cashFftPointsRatio" value="${cashFftPointsRatio?if_exists}" />
              <input type="hidden"    id="cashBankPointsRatio" value="${cashBankPointsRatio?if_exists}" />
               <input type="hidden" name="transDetails.bankPointsValueRealAll" id="bankPointsValueRealAll" />
              <input type="hidden" name="transDetails.fftPointsValueRealAll" id="fftPointsValueRealAll" />
              
              
              <!-- hidden start-->
              <div style="display:none">
          		<span>积分：</span> -¥<b id="score">0</b>
          		<strong id="totleRMB">0</strong>
          		<span id="price">${pager.cashPricing?if_exists}</span>
            	<span id="totleprice">${pager.cashPricing?if_exists}</span>
          	</div>
          	 <div style="display:none">
	         	<input type="radio" name="pointsType" value="bank"  id="card">银行积分：
	    		<input type="radio" name="pointsType" value="fft">分分通积分
	    		<span id="totlePriceA">0</span>
	    		<span id="totlePriceB">0</span>
	    	</div>
              
              <!-- hidden end-->
              
              
              
              
  <div class="points fl">
     <div class="leffShow"><img src="${base}/template/web/img/002.png"></div> 
     <div class="rightInfor">
       <dl>
       <dt>${pager.goods.goodsName?if_exists}</dt>
       
        <#if pager.isCash=="1" >
       		<dd>现金兑换：￥${pager.cashPricing?if_exists} 元</dd>
        </#if>
        <#if pager.isBankPoint=="1" >
       		<dd>银行积分兑换：<b id="cardfen">${pager.bankPointPricing?if_exists}</b> 分</dd>
       	</#if>
       	 <#if pager.isFftPoint=="1">
       		<dd>分分通积分兑换：<b id="totle">${pager.fftPointPricing?if_exists}</b> 分</dd>
       </#if>
       <#if pager.isFftpointCash=="1" >
	        <#assign fftpointsAndCash=pager.fftpointCashPricing>
	        <#assign fftpointsAndCashArray=fftpointsAndCash?split('|')>
	       	<dd>分分通积分+现金兑换：<B>${fftpointsAndCashArray[0]?if_exists}</B>分 + <B>${fftpointsAndCashArray[1]?if_exists}</B>元</dd>
        </#if>
        <#if pager.isBankpointCash=="1" >
        	<#assign bankpointsAndCash=pager.bankpointCashPricing>
        	<#assign bankpointsAndCashArray=bankpointsAndCash?split('|')>
       		<dd>银行积分+现金兑换：<B>${bankpointsAndCashArray[0]?if_exists}</B>分 + <B>${bankpointsAndCashArray[1]?if_exists}</B>元</dd>
       	</#if>
       	
       	 <#if pager.isFftpointcashRatioPricing=="1" >
       		<dd>分分通积分+现金兑换：<b id="usableFftPointsMinShow">10</b>-<b id="usableFftPointsMaxShow">300</b>分+<b id="currencyMinUsedFftPointsShow">10</b>-<b id="currencyMaxUsedFftPointsShow">100</b>元</dd>
        </#if>
        <#if pager.isBankpointcashRatioPricing=="1" >
       		<dd>银行积分+现金兑换：<b id="usableBankPointsMinShow">10</b>-<b id="usableBankPointsMaxShow">300</b>分+<b id="currencyMinUsedBankPointsShow">10</b>-<b id="currencyMaxUsedBankPointsShow">100</b>元</dd>
       	</#if>
   <!--    
       <dd>手机银行卡兑换：<b id="totle">300</b>元</dd>
       <dd>分分通积分兑换：<b >300</b>分</dd>
       <dd>银行积分兑换：<b>300</b>分</dd>
       <dd>分分通积分+手机银行卡兑换：<b>300</b>分+<b>200</b>元</dd>
       <dd>银行积分+手机银行卡兑换：<b>10-300</b>分+<b>10-100</b>元</dd>   -->
       <dd>库存：<b id="stock">${pager.marketTotalNumber?if_exists}</b> 件</dd> 
       <dd>最近成交：0 笔</dd>
       <dd class="redFont">使用手机银行卡支付全额款项，更能享受9.8折优惠</dd>
       </dl>
     </div>   

       <div class="buyBox">
         <div class="mebuy">
             我要买：
             <a href="javascript:void(0)" onclick="numPoints();sure();">-</a>
             <input type="text" name="transDetails.goodsNumber" id="value" value="1" maxlength="2" class="loginText5" onkeyup="changePoints(this.value);">
	         <a href="javascript:void(0)" onclick="addPoints();sure();">+</a>
         </div>
         
         
         
         <div class="meSelect">
           <div class="fl mt9">支付方式：</div>   
          <form action="#" method="post" class="example_form fl">
	         <select name="trans.payMethod" id="payMethod" class="existing_event"  tableindex="4" onchange=changePayMethod(this.value);>
		          <#if pager.isCash=="1" >
	             	 <option  value="02">现金（珠海农商银行手机银行卡支付）</option>
	              </#if>
	               <#if pager.isFftPoint=="1">
	             	<option  value="00" >分分通积分</option>
	              </#if>
	               <#if pager.isBankPoint=="1" >
	             	  <option  value="01">银行积分</option>
	              </#if>
	              <#if pager.isFftpointCash=="1" >
	             	<option  value="03">分分通积分+现金/option>
	              </#if>
	               <#if pager.isBankpointCash=="1" >
	             	<option  value="04">银行积分+现金</option>
	              </#if>
	               <#if pager.isFftpointcashRatioPricing=="1" >
	             	<option  value="05">分分通积分+现金（比例）</option>
	              </#if>
	               <#if pager.isBankpointcashRatioPricing=="1" >
	             	<option  value="06">银行积分+现金（比例）</option>
	              </#if>
		     </select>
		     </form>
	      </div>
<!--
		 <div class="userpoints">使用积分：<b>50</b>分</div>
        
        <div class="userpoints">使用积分：<b>50</b>分 + <b>20</b>元</div>  
-->
        <div class="meinput validate" id="formlist" style="display:none"> <strong>使用积分：
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
            
         
       <a href="javascript:checkAndSubmit()"><img src="${base}/template/web/images/dhbtn.png"></a> 

       </div>  
        <script src="${base}/template/common/js/price.js"></script>   
<script src="${base}/template/web/js/points.js"></script>    
    <div class="contantInr"> 
    	${pager.goods.goodsDesc?if_exists}
    </div>                                 
  </div> 
<!--内容结束-->
</form>
<#else>
  	系统有误，不能显示商品信息
</#if>
  
 <#include "/WEB-INF/templates/common/exch_right.ftl">       
</div>

<!--底部开始-->  
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
</body>
</html>
