<div id="ajaxLinkWindow" class="ajaxLinkWindow">
	<div class="windowTop">
		<!--<div class="windowTitle">
			<dt>查看详情</dt>
		</div>-->
		<a class="windowClose ajaxLinkWindowClose" href="javascript:void(0)" hidefocus="true"></a>
	</div>
	<div class="windowMiddle">
		<table width="546" cellpadding="0" cellspacing="0"  class="orderdetails">
			<tr>
				<input type="hidden" id="transId" value="${trans.id?c}"/>
				<input type="hidden" id="isConsume" value="${ticket.isConsume}"/>
				<input type="hidden" id="smsNumber" value="${ticket.smsNumber}"/>
				<th width="292" align="left">订单号：<b>${trans.sn}</b></th>
				<th width="252" align="left">下单时间：<b>${trans.createTime?string('yyyy-MM-dd HH:mm:ss')}</b></th>
			</tr>
			<tr>
				<th align="left">商品名称：<b>${trans.transDetailDto[0].productName}</b></th>
				<th align="left">商品数量：<b>${trans.transDetailDto[0].quantity}</b></th>
			</tr>
			<tr>
				<th align="left">实付金额：<b>${trans.totalPrice}元</b></th>
				<th align="left">积分：<b>${trans.fftPoints!'0'}分</b></th>
			</tr>
			<tr>
				<th height="21" align="left">支付方式：<b>${trans.payMethod.describe}</b></th>
				<th align="left">现金支付：<b>${trans.realPrice}元</b></th>
			</tr>
			<tr>
				<th align="left">提货人：<b>${trans.deliveryName}</b></th><th align="left">手机号：<b>${trans.phone}</b></th>
			</tr>
			<tr >
				<th align="left">提货券：<b>${ticket.securitiesNo!'--'}</b></th>
				<th align="left">
				<#if ticket.securitiesNo!=null>
					<#if presellState=='已成团，提货中'>
						<a href="javascript:;" id="retrySms">重发提货码</a>
					</#if>
				</#if>
				</th>
			</tr>
			<tr >
				<th align="left">提货地点：<b>${delivery.name}</b></th>
				<th align="left">状态：<b> ${presellState}</b></th>
			</tr>
			<tr >
                  <th height="50" colspan="2" class="tc"><input class="subBtn " value="关闭" type="button" id="closed"></th>
                </tr> 
		</table>
	</div>
	<div class="windowBottom"></div>
</div>
<script>
	$("#retrySms").click(function(){
		var transId= $("#transId").val();
		if($.trim(transId)==""){
			layer.msg("交易ID为空，无法重发",2,-1);
			return false;
		}
		var isConsume = $("#isConsume").val();
		if(isConsume){
			layer.msg("商品已被消费，不能重发",2,-1);
			return false;
		}
		var smsNumber = $("#smsNumber").val();
		if(smsNumber>=5){
			layer.msg("发送次数已达上限，不能重发",2,-1);
			return false;
		}
		$.Ajax({
			url : '${base}/member/retrysms/retry_presell_sms.jhtml',
			datas : {'transId' : transId},
			successFn : function (data){
					if(data.flag){
						layer.msg("短信发送成功",2,-1);
						$('#closed').click();
					}else{
						layer.msg(data.msg,2,-1);
					}
			}
		});
	});
</script>