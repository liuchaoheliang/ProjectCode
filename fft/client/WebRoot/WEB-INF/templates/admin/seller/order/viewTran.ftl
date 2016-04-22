<div>交易信息</div>
====================================<br>
<div>卖家收款资金渠道信息：</div>
<div>
<#list cashdesk.sellerDepositChannelList as sellerDepositChannel>
渠道名称：${sellerDepositChannel.fundsChannel.channelFullName}<br>
渠道规则：${sellerDepositChannel.sellerRule.ruleDesc}<br>
</#list>
</div>

 交易品：
 ==================================<br>
 
 <div>
	<#if orderVO?exists>
		<#if orderVO.allTranDetails?has_content>
			<#list orderVO.allTranDetails as tranDetail>
				交易品名称：${tranDetail.tranGoods.transGoodsDisplay}<br/>
				交易品数量：${tranDetail.tranGoods.transGoodsAmount}<br/>
				总金额：${tranDetail.currencyValue}<br/>
				货币单位：${tranDetail.currency}<br/>
			</#list>
			</#if>
		</#if>
 </div>
 
交易总金额：${orderVO.tran.currencyValueAll} <br/>

交易实际总金额：${orderVO.tran.currencyValueRealAll}  <br/>

其中${orderVO.orderVO.fftToPay}金额的优惠由方付通支付   <br/>

货币单位：${orderVO.tran.currency}<br/>

买家支付渠道：
==================================<br>
${buyerPayChannel.fundsChannel.channelFullName}  <br>
买家规则：
==================================<br>
${buyerPayChannel.buyerRule.ruleDesc}  <br>

<#list orderVO.buyerPayChannelList as buyerPayChannel>
买家支付渠道：${buyerPayChannel.fundsChannel.channelFullName}<br>
买家规则：${buyerPayChannel.buyerRule.ruleDesc}<br>
</#list>




<div>
<form action="/pay.action" method="post">
交易跟踪号：<input type="text" name="orderVO.trackNo" value=${orderVO.trackNo} /><br/>
<input type="submit" value="提交"/>
</form>
</div>