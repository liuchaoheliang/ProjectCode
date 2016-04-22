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
 
<#if orderVO?exists && orderVO.tran?exists>
交易总金额：${orderVO.tran.currencyValueAll?if_exists} <br/>

交易实际总金额：${orderVO.tran.currencyValueRealAll?if_exists}  <br/>

<#assign currencyValueAll=orderVO.tran.currencyValueAll?number/>
<#assign currencyValueRealAll=orderVO.tran.currencyValueRealAll?number/>
<#assign pre=currencyValueAll-currencyValueRealAll/>
优惠金额：${pre} <br/>

货币单位：${orderVO.tran.currency?if_exists}<br/>



<#list orderVO.buyerPayChannelList as buyerPayChannel>
买家支付渠道：${buyerPayChannel.fundsChannel.channelFullName}<br>
买家规则：${buyerPayChannel.buyerRule.ruleDesc}<br>
</#list>


<div>
<form action="/payTransaction.action" method="post">
交易号：<input type="text" name="orderVO.tran.id" value=#{orderVO.tran.id} /><br/>
交易跟踪号：<input type="text" name="orderVO.trackNo" value=${orderVO.tran.trackNo} /><br/>
<input type="submit" value="去支付"/>
</form>
</div>

</#if>