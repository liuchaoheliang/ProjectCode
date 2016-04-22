<div>${cashdesk.seller.userId}，您好！</div>
<div>您的收款资金渠道信息：</div>

<div>您的收款资金渠道信息：</div>

<#list cashdesk.sellerDepositChannelList as sellerDepositChannel>
渠道名称：${sellerDepositChannel.fundsChannel.channelFullName}<br>
渠道规则：${sellerDepositChannel.sellerRule.ruleDesc}<br>
</#list>
<span><a href="/addGoodsPage.action" class="BL" title="下单" target="_blank" >卖家下单</a></span>
<span><a href="/sendPointsPage.action" class="BL" title="下单" target="_blank" > 赠送积分</a></span>

