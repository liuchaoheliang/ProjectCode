<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>支付中....</title>
<link rel="icon" href="${base}/favicon.ico" type="image/x-icon" />
<#include "/include/sources.ftl">
<link href="${base}/resources/shop/css/pay.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$(document).ready(function (){
	var temp = 0;
	var info = $('#info');
	var msg = "等待支付";
	var tempMsg ;
	setInterval(function (){
		tempMsg = '';
		temp = temp + 1;
		if(temp == 4){
			temp = 0;
		}
		for (var i = 0 ; i < temp ; i ++){
			tempMsg +='.';
		}
		info.html(msg + tempMsg);
	},1000);
	
	setTimeout(function (){
		$.Ajax({
			url : '${base}/member/pay/check_bank_card_state.jhtml',
			successFn : function (data){
				if(data.flag && data.msg == 'success'){
					window.location.href = '${base}/member/pay/pay_bank_card_result.jhtml';
				}else{
					var changes = 5; 
					setInterval(function (){
						changes --;
						if(changes <= 0){
							return;
						}
						$.Ajax({
							url : '${base}/member/pay/check_bank_card_state.jhtml',
							successFn : function (data){
								if(data.flag && data.msg == 'success'){
									window.location.href = '${base}/member/pay/pay_bank_card_result.jhtml';
									return;
								}
							}
						});
					},5 * 1000);
				}
			}
		});
	},30 * 1000);
	
	
	$('#checkResult').click(function (){
		$.Ajax({
			url : '${base}/member/pay/check_bank_card_state.jhtml',
			successFn : function (data){
				if(data.flag && data.msg == 'success'){
					window.location.href = '${base}/member/pay/pay_bank_card_result.jhtml';
					return;
				}else{
					layer.msg('还未完成支付，若您已完成支付请稍后重试',2,-1);
				}
			}
		});
	});
});
</script>
</head>
<body>
<#-- 头部 开始-->
<#include "/include/common/header.ftl">
<#-- 头部 结束-->
<div class="main" >
<div class="confirm" >
    <div class="stepgroup " style="background-position:center bottom;"></div>
    <div class="result"> 
      <div class="waitBox">
        <table border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="jumpwait" id="info" style="width:200px">等待支付</td>
          </tr>
          <tr>
            <td>请勿刷新页面</td>
          </tr>
          <tr>
            <td style="font-size:12px;">您的手机贴膜卡将会收到一条下推信息，请在手机上确认订单支付后，本页面会自动刷新。</td>
          </tr>
          <tr>
            <td>
	            <input class="subBtn" id="checkResult" value="已完成支付" type="button" name="" style="float:none;"/>
	            <#--<input class="subBtn" value="重新支付" onclick="history.back();" type="button" name="" style="float:none; background:#FFECEC; color:#F60; border-color:#F33"/>-->
            </td>
          </tr>
        </table>
      </div>
    </div>
  </div>
<#-- 底部 -->
<#include "/include/common/footer.ftl">
<#-- 底部 -->
</body>
</html>