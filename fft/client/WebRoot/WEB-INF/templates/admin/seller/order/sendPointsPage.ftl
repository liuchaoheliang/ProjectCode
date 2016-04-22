<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商家商品页面</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
<script src="${base}/template/common/My97DatePicker/WdatePicker.js"></script>
<!-- <script type="text/javascript" src="${base}/template/common/js/height.js"></script> -->
<link href="${base}/template/common/css/pager.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/list.js"></script>
<link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>
<script type="text/javascript">


function checkAndSubmit(){
var currencyVal=document.getElementById('price').value;
document.getElementById('goodsPrice').value=currencyVal;

	create_order_form.submit();

//	var mobilecheckResult=document.getElementById('mobilecheckResult').value;
//	var namecheckResult=document.getElementById('namecheckResult').value;
//	var numebercheckResult=document.getElementById('numebercheckResult').value;
//	var pricecheckResult=document.getElementById('pricecheckResult').value;
//	var checkResult=false;
//	if(mobilecheckResult=="true" && namecheckResult=="true" && numebercheckResult=="true" && pricecheckResult=="true" ){
//		checkResult=true;
//	}
//	if(checkResult)
//		create_order_form.submit();
//	else
//		alert("请把信息填写正确!");
}

function countRealCurrencyValue(currencyVal){
	
	if(currencyVal==""){
		//alert("原价不能为空!");
		document.getElementById('realCurrencyVal').value=0;
		return ;
	}
	var num=document.getElementById('numeber').value;
	var preRatio=document.getElementById('preRatio').value;
	
	var toSendPoints=currencyVal*num*preRatio/100;
 	document.getElementById('toSendPoints').innerHTML=toSendPoints;
	
}

function countRealCurrencyValueOnNumChange(num){
	
	if(num==0){
		//alert("原价不能为空!");
		document.getElementById('toSendPoints').innerHTML=0;
		return ;
	}
	var currencyVal=document.getElementById('price').value;
	var preRatio=document.getElementById('preRatio').value;
	
	var toSendPoints=currencyVal*num*preRatio/100;
 	document.getElementById('toSendPoints').innerHTML=toSendPoints;
	
}

function changeRealCurrencyValWritable(payChannelVal){
	//var payChannelVal=document.getElementById('payChannel').value;
	if(payChannelVal=="1"){
		document.getElementById('realCurrencyVal').disabled= true ;
		var currencyVal=document.getElementById('price').value;
		countRealCurrencyValue(currencyVal);
	}else{
		document.getElementById('realCurrencyVal').disabled= false ;
	}
}


</script>

</head>
<body>
<!--
* Author:
* pengling@f-road.com.cn 
*/
--> 
<!--头部开始-->
<#include "/WEB-INF/templates/common/header.ftl">
<!--头部结束-->

<!--导航开始-->
<#include "/WEB-INF/templates/common/menu.ftl">
<!--导航结束-->

<div class="middleBox mt10">
<!-- 商家管理菜单开始 -->
<#include "/WEB-INF/templates/common/merchant_manage_menu.ftl">
<!-- 商家管理菜单结束 -->
  <div class="rightBox" id="rightHeight">
    <div class="step">
      <li class="colorRed step07">输入信息</li>
      <li class="step02">生成订单</li>
      <li class="step03">成功收款</li>
    </div>   
   <form id="create_order_form" action="create_order.action" method="post">
    <div class="inforBox" id="admin">
    
    <#if errorMessage?exists>
    	${errorMessage?if_exists}
    </#if>
        <dl>
          <dt>支付方式：</dt>
          <dd>
            <div> 
            	<span>
            		<input id="payChannel" name="orderVO.payChannel" type="radio" value="2" /> 刷卡
            		<input id="payChannel" name="orderVO.payChannel" type="radio" value="3" />现金
            	</span> 
            </div>
          </dd>
        </dl>
        <dl>
          <dt>手机号：</dt>
          <dd>
            <div> <span><input id="mobile" name="orderVO.buyerPhone" type="text" class="loginText"></span> </div>
          </dd>
        </dl>        
        <dl>
          <dt>价格：</dt>
          <dd>
            <div> <span><input id="price" name="orderVO.goodsGatherRack.cashPricing" type="text" class="loginText4" onBlur="countRealCurrencyValue(this.value)"></span></div>
          </dd>
        </dl>
        <input type="hidden" name="orderVO.tranDetail.currency" value="RMB" checked="true" />  
        <dl>
          <dt>商品名称：</dt>
          <dd>
            <div><span><input id="name" name="orderVO.goodsGatherRack.goods.goodsName" type="text" class="loginText"></span></div>
          </dd>
        </dl>
        <dl>
        <input id="goodsPrice" name="orderVO.goodsGatherRack.goods.price" type="hidden" class="loginText">
        
        <#if cashdesk.seller?exists && cashdesk.seller.merchantId?exists>
        	<#assign merchantId=cashdesk.seller.merchantId>
        </#if>
         <input  name="orderVO.goodsGatherRack.goods.merchantId" type="hidden" class="loginText" value="${merchantId?if_exists}" >  
         <input  name="orderVO.goodsGatherRack.isRack" type="hidden" class="loginText" value="1"> 
          <dt>数量：</dt>
          <dd>
            <div><span><input id="numeber" name="orderVO.tranDetail.goodsNumber" type="text" class="loginText5" value=1 onBlur="countRealCurrencyValueOnNumChange(this.value)"></span></div>
          </dd>
        </dl>
        <dl>
          <dt>返利积分：</dt>
          <dd>
            <div> <span id="toSendPoints">0</span> </div>
          </dd>
        </dl>        
        <dl>
          <dt>收款账户：</dt>
          <dd>
            <div> 
            <span>
          		 <select name="orderVO.sellerAccountIdOfCurrentUsed">
			         <#if cashdesk.sellerDepositChannelList?has_content>
						<#list cashdesk.sellerDepositChannelList as sellerDepositChannel>
							<option value="#{sellerDepositChannel.id}" />${sellerDepositChannel.accountNumber}<br/>
						</#list>
					</#if>
				</select>
            </span> 
            </div>
          </dd>
        </dl>
        
        
          <#assign pre=1>
       		<#list cashdesk.sellerDepositChannelList as sellerDepositChannel>
       			<#if cashdesk.sellerRule?exists>
       				<#assign pre=cashdesk.sellerRule.pointsFormula?number>
       			</#if>
			</#list>
			 <input type="hidden" id="preRatio" value="${pre}"/>
			 
 			 <input type="hidden" name="orderVO.isPresentPoints" value="1"/>
        
         
        <div class="ml145 mt5"> 
          <a href="javascript:checkAndSubmit()">
          <div class="textBtn"><B>确定</B></div>
          </a> 
        </div>               
    </div>
	</form>
  </div>
</div>
<!--清除浮动-->
<div class="clear"></div>
<!--清除浮动--> 

<!--底部开始-->
<div class="foot mt10">
  <div class="footAdd">运营商：珠海农商银行 上海方付通商务服务有限公司 电话：96138 沪ICP备:xxxxxxxxx号 地址：珠海市香洲兴业路223号</div>
</div>
<!--底部结束-->
</body>
</html>
