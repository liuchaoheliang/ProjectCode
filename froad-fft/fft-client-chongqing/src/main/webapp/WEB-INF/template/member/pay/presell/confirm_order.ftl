<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>订单确认</title>
<link rel="icon" href="${base}/favicon.ico" type="image/x-icon" />
<#include "/include/sources.ftl">
<link href="${base}/resources/shop/css/pay.css" rel="stylesheet" type="text/css" />
<style>
.xubox_botton a.xubox_botton2{ margin-left:-116px;}
</style>
<script type="text/javascript">
$(document).ready(function (){
	var payMethod = $.trim('${cashmethod}');
	$('#pay').click(function (){
		if(payMethod == 'alipay'){
			openAlipay();
			var radom = new Date().getTime();
			document.getElementById("orderresult").target = "alipay_" + radom;
			try{
            	window.open("","alipay_" + radom);
            }catch(e){
            }
		}else if(payMethod == 'bank'){
			openAlipay();
			var radom = new Date().getTime();
			document.getElementById("orderresult").target = "alipay_" + radom;
			try{
            	window.open("","alipay_" + radom);
            }catch(e){
            }
		}
		document.getElementById("orderresult").submit();
	});
	function openAlipay(){
        var i = $.layer({
            title: '重庆农村商业银行',
            area:['400px','auto'],
            dialog: {
                btns: 2,
                btn: ['已完成付款', '付款遇到问题'],
                msg: '<div style="color:#087da3">付款完成前请不要关闭此窗口。完成付款后根据您的情况点击下面按钮。</div>',
                type: -1,
                yes: function (index)
                {
                	window.location.href = '${base}/member/pay/check_trans_state.jhtml';
                    layer.close(i);
                },
                no: function (index)
                {
                    layer.close(i);
                    self.location="${base}/shop/helper/index.jhtml";
                }
            }
        });
	}
});
</script>
</head>
<body>
<#-- 头部 开始-->
<#include "/include/common/header.ftl">

<#-- 头部 结束-->
<form id="orderresult" action="${base}/member/pay/presell/order_result.jhtml" method="post" onsubmit="return false">
<div class="main">
  <div class="confirm" >
    <div class="stepgroup " style="background-position:center center;"></div>
    <div class="conborder">
      <hr />
      <dl>
        <dt>商品名称：</dt>
        <dd>
          <div> <span>${productDto.name}</span> </div>
        </dd>
      </dl>
      <hr />
      <dl>
        <dt>商品数量：</dt>
        <dd>
          <div> <span>${buyNumber!'1'}</span> </div>
        </dd>
      </dl>
      <dl>
        <dt>单价：</dt>
        <dd>
          <div> <span>${productDto.price!'0'}元</span> </div>
        </dd>
      </dl>
      <dl>
        <dt>商品总价：</dt>
        <dd>
          <div> <span>${totalPrice!'0'}元</span> </div>
        </dd>
      </dl>
      <hr />
      	<input type="hidden" name="fftPoints" value="${fftpoints}" /><#-- 分分通积分 -->
		<input type="hidden" name="mobile" value="${deliveryMobile}" /><#-- 接收短信|提货人手机号码 -->
		<input type="hidden" name="filmMobile" value="${paymobile}" /><#-- 付款人手机号码|贴膜卡手机号 -->
		<input type="hidden" name="productId" value="${productId}" /><#-- 购买产品id -->
		<input type="hidden" name="buyNumber" value="${buyNumber}" /><#-- 购买数量 -->
		<input type="hidden" name="deliveryName" value="${buyerName}" /><#-- 提货人姓名 -->
		<input type="hidden" name="deliveryId" value="${presellDeliveryDto.id?c}" /><#-- 提货点id -->
		<input type="hidden" name="cashmethod" value="${cashmethod}" />
		<#include "/include/system/token/tokenkey.ftl">
      <dl>
        <dt>自提点名称：</dt>
        <dd>
          <div> <span>${presellDeliveryDto.name}</span> </div>
        </dd>
      </dl>
      <dl>
        <dt>自提点地址：</dt>
        <dd>
          <div> <span>${presellDeliveryDto.address}</span> </div>
        </dd>
      </dl>
      <dl>
        <dt>提货人姓名：</dt>
        <dd>
          <div><span>${buyerName}</span> </div>
        </dd>
      </dl>
      <dl>
        <dt>手机号：</dt>
        <dd>
          <div><span>${deliveryMobile}</span> </div>
        </dd>
      </dl>
      <hr />
      <dl>
        <dt>支付方式：</dt>
        <dd>
          <div> <span>${payMethodMsg}</span> </div>
        </dd>
      </dl>
      <dl>
        <dt>支付金额：</dt>
        <dd>
          <div> <span>${moneyMsg}</span> </div>
        </dd>
      </dl>
    </div>
    <#--<div class="conbtn fr mr50 mt10"> <a href="javascript:void(0);" id="pay" >
      <div class="textBtn"><B>确认订单，去付款</B></div>
      </a>
      <div class="fhuixiugai"><a onclick="goCreatePage();" href="javascript:;">返回修改订单</a></div>
    </div>-->
   <div class="mt10">
        <div class="fhuixiugai fr w100"><a href="javascript:;" onclick="goCreatePage();" >返回修改订单</a></div>
    	<input class="subBtn fr" value="确认订单，去付款" id="pay" type="submit"> 
    </div>
  </div>
  <script>
  	function goCreatePage(){
  		window.location.href="${base}/member/pay/presell/create_order.jhtml?productId=${productDto.productPresellId}";
  	}
  </script>
  <!--清除浮动-->
  <div class="clear"></div>
  <!--清除浮动--> 
</div>
</form>
<#-- 底部 -->
<#include "/include/common/footer.ftl">
<#-- 底部 -->

</body>
</html>