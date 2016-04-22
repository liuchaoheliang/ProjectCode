<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通-确认信息</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/buy/css/confirm.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
$(document).ready( function() {

	//确认下单
	$('a.exchangeAffirmBut').click(function() {
		$('#exchangeLocalAffirmForm').submit()
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


<div class="box1010 pt10 clear">
<!--付款确认开始 exchange_local_affirm.action -->
<form id="exchangeLocalAffirmForm" method="post" action="transExchange.action">
  <input name="exchangeTempBean.goodsRackId" value="${(exchangeTempBean.goodsRackId)!""}" type="hidden">
  <input name="exchangeTempBean.goodsCategoryId" value="${(exchangeTempBean.goodsCategoryId)!""}" type="hidden">
  <input name="exchangeTempBean.payMethod" value="${(exchangeTempBean.payMethod)!""}" type="hidden">
  <input name="exchangeTempBean.sellerId" value="${(goodsExchangeRack.sellerId?c)!""}" type="hidden">
  <input name="exchangeTempBean.buyNumber" value="${(exchangeTempBean.buyNumber)!""}" type="hidden">
  <input name="exchangeTempBean.usePointRaioValue" value="${(exchangeTempBean.usePointRaioValue)!""}" type="hidden">
  <input name="transId" value="${(trans.id)?c!''}" type="hidden"/>
  <div class="confirm">
    <div class="stepgroup"></div>
    <div class="step">
      <li class="step01">输入信息</li>
      <li class="colorRed step08">生成订单</li>
      <li class="step03">购买成功</li>
    </div> 
    <div class="clear"></div>   
    <div class="conborder">
      <dl>
        <dt>商品名称：</dt>
        <dd>
          <div> <span>${(goodsExchangeRack.goodsRackName)!""}</span> </div>
        </dd>
      </dl>
      <dl>
        <dt>商品数量：</dt>
        <dd>
          <div> <span>${(exchangeTempBean.buyNumber)!"1"}</span> </div>
        </dd>
      </dl>
      <dl>
        <dt>支付方式：</dt>
        <dd>
          <div> 
          	<span>
          		  <#if exchangeTempBean.payMethod=="02" >
	             	现金（珠海银行手机银行卡支付）
	              </#if>
	               <#if exchangeTempBean.payMethod=="00">
	             	分分通积分
	              </#if>
	               <#if exchangeTempBean.payMethod=="01" >
	             	  银行积分
	              </#if>
	              <#if exchangeTempBean.payMethod=="03" >
	             	分分通积分+现金
	              </#if>
	               <#if exchangeTempBean.payMethod=="04" >
	             	银行积分+现金
	              </#if>
	               <#if exchangeTempBean.payMethod=="05" >
	             	分分通积分+现金（比例）
	              </#if>
	               <#if exchangeTempBean.payMethod=="06" >
	             	银行积分+现金（比例）
	              </#if>
          	</span> 
          </div>
        </dd>
      </dl>
      <dl>
        <dt>支付金额：</dt>
        <dd>
          <div> 
          	<span>
          		 <#assign excBuyNumber=exchangeTempBean.buyNumber?number>
          		 <#if exchangeTempBean.payMethod=="02" >
          		 	 <#assign excCashPricing=goodsExchangeRack.cashPricing?number>
	             	 ${excBuyNumber*excCashPricing} 元
	              </#if>
	               <#if exchangeTempBean.payMethod=="00">
	               	 <#assign excFftPointPricing=goodsExchangeRack.fftPointPricing?number>
	             	 ${excBuyNumber*excFftPointPricing} 分
	              </#if>
	               <#if exchangeTempBean.payMethod=="01" >
	               	  <#assign excBankPointPricing=goodsExchangeRack.bankPointPricing?number>
	             	  ${excBuyNumber*excBankPointPricing} 分
	              </#if>
	              <#if exchangeTempBean.payMethod=="03" >
	              	 <#assign fftpointsAndCashArray=goodsExchangeRack.fftpointCashPricing?split('|')>
	             	 <#assign excFftpointsAndCash1=fftpointsAndCashArray[0]?number>
	             	 <#assign excFftpointsAndCash2=fftpointsAndCashArray[1]?number>
	             	 
	             	 ${excBuyNumber*excFftpointsAndCash1} 分 + ${excBuyNumber*excFftpointsAndCash2} 元
	              </#if>
	               <#if exchangeTempBean.payMethod=="04" >
	               	 <#assign bankpointsAndCashArray=goodsExchangeRack.bankpointCashPricing?split('|')>
	             	 <#assign excBankpointsAndCash1=bankpointsAndCashArray[0]?number>
	             	 <#assign excBankpointsAndCash2=bankpointsAndCashArray[1]?number>
	             	
	             	${excBuyNumber*excBankpointsAndCash1} 分 + ${excBuyNumber*excBankpointsAndCash2} 元
	              </#if>
	               <#if exchangeTempBean.payMethod=="05" || exchangeTempBean.payMethod=="06">
	               	<#assign cashPricing_value=goodsExchangeRack.cashPricing?number>
	               	
	               	<#assign usePointRaio_value=exchangeTempBean.usePointRaioValue?number>
	               	
	               	<#assign remain_cash_value=cashPricing_value-usePointRaio_value>
	               	
	             	${excBuyNumber*usePointRaio_value} 分 + ${excBuyNumber*remain_cash_value} 元
	              </#if>
          	</span> 
          </div>
        </dd>
      </dl>
      <!--
      <dl>
        <dt>支付来源：</dt>
        <dd>
          <div> <span>珠海分数 或 分分通分数 + 现金 （珠海农商银行手机银行卡支付）</span> </div>
        </dd>
      </dl>
      -->
    </div>
    <div class="conbtn fr mr50 mt10"> 
    	<a href="javascript:void(0);" class="exchangeAffirmBut">
      	<div class="textBtn"><B>确认订单，去付款</B></div>
      </a>
    </div>
  </div>
</div>
</form>
<!--付款确认结束-->

</div>


<!--底部开始-->  
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
</body>
</html>
