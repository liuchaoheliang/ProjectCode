<style>
	.orderdetails{
		width:400px;
		height:195px;
		margin:0px 35px;
	}
	.productName{
		padding-top:5px;
	}
</style>
<script>
	$("#resell").click(function(){
		var reason = $.trim($("#reason").val());
		if(reason==""){
			layer.msg("请输入退款原因",2,-1);
			return;
		}
		$.Ajax({
			url : '${base}/member/orderlist/refunds/return.jhtml',
			datas:{'reason':reason},
			successFn : function (data){
					if(data.flag){
						layer.msg(data.msg,2,-1,function(){
							//退款成功，移除退款申请链接
							$("a.return[transId='"+${trans.id?c}+"']").after("<span>退款待审核</span>");
							$("a.return[transId='"+${trans.id?c}+"']").remove();
							layer.closeAll();
						});
					}else{
						layer.msg(data.msg,2,-1);
					}
			}
		});
	});
</script>
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
				<td class="productName f12"> 
				商品名称：${trans.transDetailDto[0].productName}
				</td>
			</tr>
			<tr>
				<td class="f12">
					<textarea id="reason" name="reason" cols="" rows="6" class="newtextarea"></textarea>
				</td>
			</tr>
			<tr>
				<td><input class="subBtn" id="resell" value="申请退款" type="submit"></td>
			</tr>
		</table>
	</div>
	<div class="windowBottom"></div>
</div>
<script>
</script>