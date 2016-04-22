<div>
<div> 赠送积分</div>
<div>

手机号：<input type="text" name="orderVO.buyerPhone" value="${orderVO.buyerPhone?if_exists}" /><br/>

赠送的积分数：${orderVO.transPoints.pointsValueRealAll?if_exists}<br/>
购买赠送积分的金额：${orderVO.transPoints.sellPointCurreVal?if_exists}<br/>
购买赠送积分的手续费：${orderVO.transPoints.sellFacPointCurreVal?if_exists}<br/>
货币单位：${orderVO.transPoints.currency?if_exists} <br/>


<form action="/payTransPoints.action" method="post">
赠送积分交易号：<input type="text" name="orderVO.transPoints.id" value="${orderVO.transPoints.id?if_exists?c}" /><br/>
<input type="submit" value="赠送"/>
</form>
</div>
</div>

