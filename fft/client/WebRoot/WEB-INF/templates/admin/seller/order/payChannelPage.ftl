选择支付渠道:
<form action="/createTran.action" method="post">
交易跟踪号：<input type="text" name="orderVO.trackNo" value=${orderVO.trackNo} /><br/>
买家手机号：<input type="text" name="orderVO.buyerPhone" value="" /><br/>

选择下单类型：
<#if orderTypeVOList?has_content>
	<#list orderTypeVOList as orderTypeVO>
		<input type="radio" name="orderVO.orderTypeId" value="${orderTypeVO.orderTypeId}" />${orderTypeVO.orderTypeDescript}<br/>
	</#list>
</#if>
<input type="submit" value="确定支付"/>
</form>
