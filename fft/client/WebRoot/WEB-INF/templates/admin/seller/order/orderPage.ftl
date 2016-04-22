<div>
<div> 卖家下单</div>
<div>
<form action="/order.action" method="post">
商户ID：<input type="text" name="orderVO.merchantId" value="" /><br/>
买家手机号：<input type="text" name="orderVO.buyerPhone" value="" /><br/>
交易品名称：<input type="text" name="orderVO.tranGoods.transGoodsDisplay" value="" /><br/>
交易品数量：<input type="text" name="orderVO.tranGoods.transGoodsAmount" value="" /><br/>
金额：<input type="text" name="orderVO.currencyValue" value="" /><br/>
 <input type="radio" name="state" value="ok" checked="true" />正常
<input type="radio" name="state" value="history" />历史	

<input type="submit" value="确定"/>
</div>
</div>