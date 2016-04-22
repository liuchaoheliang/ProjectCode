<div>
<div> 卖家下单</div>
<div> 添加产品</div>
<div>


<#if errorMessage?exists>
	<div>${errorMessage}</div>
</#if>
<form action="/createOrder.action" method="post">
手机号：<input type="text" name="orderVO.buyerPhone" value="" /><br/>

交易品名称：<input type="text" name="orderVO.tranDetail.tranGoods.transGoodsDisplay" value="" /><br/>
交易品数量：<input type="text" name="orderVO.tranDetail.tranGoods.transGoodsAmount" value="" /><br/>
金额：<input type="text" name="orderVO.tranDetail.currencyValue" value="" /><br/>
货币单位：<input type="radio" name="orderVO.tranDetail.currency" value="RMB" checked="true" />RMB <br/>

卖家账户：
<#if cashdesk.sellerAccountList?has_content>
	<#list cashdesk.sellerAccountList as sellerAccount>
		<input type="radio" name="orderVO.sellerAccountIdOfCurrentUsed" value="#{sellerAccount.id}" />${sellerAccount.accountNumber}<br/>
	</#list>
</#if>

<input type="submit" value="确定"/>
</form>

</div>
</div>

