<script type="text/javascript">
$().ready( function() {
	var $shareConfirm= $(".shareConfirm");
	var $shareMobile = $(".shareMobile");
	var $myMsg = $("#myMsg");
	
	$shareConfirm.click( function() {
		if ($shareMobile.val().length <= 0) {
			$.layer({
			title:['分分通提示您',true],
			time:3,
			area : ['auto','auto'],
			dialog : {msg:'请输入分享手机号码',type : 9}	
		});
			return false;
		}
		var reg=/^[1][0-9]{10}$/;
		if(!reg.test($shareMobile.val())){
			$.layer({
			title:['分分通提示您',true],
			time:3,
			area : ['auto','auto'],
			dialog : {msg:'手机号码不正确',type : 8}	
		});
			return false;
		}
		
		$shareConfirm.attr("disabled", true);
		$this = $(this);
		var merchantId = $this.metadata().merchantId;
		var shareFlag = $this.metadata().shareFlag;
		
		$.ajax({
			url: "${base}/member_share_merchant_confirm.action",
			data: {"merchantId": merchantId,"shareFlag":shareFlag,"shareMobile":$shareMobile.val()},
			dataType: "json",
			beforeSend: function() {
				$shareConfirm.attr("disabled", true);
			},
			success: function(data) {
				$myMsg.text("");
			
				if (data.status == "success") {
					$myMsg.text(data.message);
				} else {
					$myMsg.text.text(data.message);
				}
				//$shareConfirm.attr("disabled", false);
			}
		});
		return false;
	});
});
</script>
<div id="ajaxLinkWindow" class="ajaxLinkWindow">
		<div class="windowTop">
			<div class="windowTitle">分享商户</div>
			<a class="windowClose ajaxLinkWindowClose" href="javascript:void(0)" hidefocus="true"></a>
		</div>
		<div class="windowMiddle">
			<table>
				<tr>
					<th>&nbsp;</th>
					<td>
					<b>分享手机号：</b><input type="text" class="shareMobile"/>&nbsp;&nbsp;
					<input type="button" class="shareConfirm {merchantId:'${merchantId}',shareFlag:'${shareFlag}'}" value="分 享" <#if shareFlag!='1'> disabled="true"</#if>/>
					&nbsp;
					<span id="myMsg"><#if shareFlag=='2'>请先登录！<#elseif shareFlag=='3'>分享频繁,稍后再试！</#if></span>
					</td>
				</tr>
			
			</table>
		</div>
		<div class="windowBottom"></div>
</div>