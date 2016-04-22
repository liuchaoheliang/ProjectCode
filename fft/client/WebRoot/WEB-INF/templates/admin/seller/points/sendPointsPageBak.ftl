<div>
<div> 赠送积分</div>
<div>
<#if errorMessage?exists>${errorMessage?if_exists}</br></#if>
<form action="/createOrderOfSendPoints.action" method="post">
交易号：<input type="text" name="orderVO.tran.id" value="<#if orderVO?exists>#{orderVO.tran.id?if_exists}</#if>" /><br/>
手机号：<input type="text" name="orderVO.buyerPhone" value="<#if orderVO?exists>${orderVO.buyerPhone?if_exists}</#if>" /><br/>

赠送的积分数：<input type="text" name="orderVO.points" value="" /><br/>
购买赠送积分的金额：<input type="text" name="sellPointCurreVal" value="" /><br/>
购买赠送积分的手续费：<input type="text" name="sellFacPointCurreVal" value="" /><br/>
货币单位：<input type="text" name="pointsAdCurrency" value="RMB" /> <br/>

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

