<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<#include "/WEB-INF/templates/common/admin_include.ftl">
<script type="text/javascript" src="${base}/template/admin/js/from.js"></script> 
<link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>
<script type="text/javascript">


function checkAndSubmit(){
	var mobilecheckResult=document.getElementById('mobilecheckResult').value;
	var namecheckResult=document.getElementById('namecheckResult').value;
	var numebercheckResult=document.getElementById('numebercheckResult').value;
	var pricecheckResult=document.getElementById('pricecheckResult').value;
	var checkResult=false;
	if(mobilecheckResult=="true" && namecheckResult=="true" && numebercheckResult=="true" && pricecheckResult=="true" ){
		checkResult=true;
	}
	if(checkResult){
		create_order_form.submit();
	}else{
		$.layer({
			area : ['auto','auto'],
			dialog : {msg:'您输入的信息不完善',type : 8}	
		});
	}
}

function countRealCurrencyValue(currencyVal){
	
	if(currencyVal==""){
		//alert("原价不能为空!");
		document.getElementById('realCurrencyVal').value=0;
		return ;
	}
	var preRatio=document.getElementById('preRatio').value;
	
	var realCurrencyValue=currencyVal*preRatio;
	document.getElementById('realCurrencyVal').value=realCurrencyValue;
	
 	var toSendPointsRatio=document.getElementById('toSendPointsRatio').value;
 	var toSendPoints=currencyVal*toSendPointsRatio;
 	document.getElementById('toSendPoints').value=toSendPoints;
	
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

<!--头部开始-->
<#--<#include "/WEB-INF/templates/common/admin_top.ftl"> -->
<!--头部结束-->

<!--导航开始-->
<#--<#include "/WEB-INF/templates/common/admin_menu.ftl">-->
<!--导航结束-->

<div class="adminRight">
  <div class="adminBorderTop">
    <div class="adminBorderTopLeft"></div>
    <div class="adminBorderTopTitle">
      <div class="adminBorderTopTitleLeft"></div>
      <div class="adminBorderTopTitleMiddle">收银台</div>
      <div class="adminBorderTopTitleRight"></div>
    </div>
    <div class="adminBorderTopRight"></div>
  </div>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#F7F8F9">
  <tr>
    <td class="adminBorderMiddleLeft"></td>
    <td>
     <div class="discountBox">
         <div class="step">
           <li class="colorRed step07">输入信息</li>
           <li class="step02">生成订单</li>
           <li class="step03">成功收款</li>
         </div>
         
         <div class="discountFloat">
         <form id="create_order_form" action="create_order.action" method="post">
         <#if errorMessage?exists>
         	<div style="color:red">
					${errorMessage?if_exists}
         	</div>
        </#if>
      <dl>
       <dt>手机号：</dt>
       <dd>
         <div>
         	<input type="text" class="loginText"  id="mobile" name="orderVO.buyerPhone" value="" />
         </div>
       </dd>
     </dl>
      <input type="hidden" id="mobilecheckResult" value="false"/>
      <dl>
       <dt>交易品名称：</dt>
       <dd>
         <div>
         	<input type="text" class="loginText" id="name"  name="orderVO.tranDetail.tranGoods.transGoodsDisplay" value="" />
         </div>
       </dd>
     </dl>
     <input type="hidden" id="namecheckResult" value="false"/>
      <dl>
       <dt>交易品数量：</dt>
       <dd>
         <div>
         <input type="text" class="loginText2" id="numeber" name="orderVO.tranDetail.tranGoods.transGoodsAmount" value="1" />          
         </div>
       </dd>
     </dl>  
      <input type="hidden" id="numebercheckResult" value="true"/>        
     <dl>
       <dt>原价：</dt>
       <dd>
         <div>
           <input type="text" class="loginText2" id="price" name="orderVO.tranDetail.currencyValue" value="" onBlur="countRealCurrencyValue(this.value)"/>
         </div>       
       </dd>
     </dl> 
     <input type="hidden" id="pricecheckResult" value="false"/>     
     <dl>
       <dt>货币单位：</dt>
       <dd>
         <div>
           <span>
           <input type="hidden" name="orderVO.tranDetail.currency" value="RMB" checked="true" />
           RMB
           </span>
         </div>       
       </dd>
     </dl>      
     <dl>
       <dt>优惠方式：</dt>
       <dd>
         <div>
           <span>
           <#list cashdesk.sellerDepositChannelList as sellerDepositChannel>
				${sellerDepositChannel.sellerRule.ruleDesc}<br>
			</#list>
           </span>
         </div>       
       </dd>
     </dl>   
     <dl>
       <dt>支付方式：</dt>
       <dd>
         <div>
           <span>
           <input type="radio" name="orderVO.payChannel" id="payChannel" value="1" checked onClick='javascript:changeRealCurrencyValWritable(this.value);' />手机银行卡
           <input type="radio" name="orderVO.payChannel" id="payChannel" value="2" onClick='javascript:changeRealCurrencyValWritable(this.value);'/>刷卡
           <input type="radio" name="orderVO.payChannel" id="payChannel" value="3" onClick='javascript:changeRealCurrencyValWritable(this.value);' />现金
           </span>
         </div>       
       </dd>
     </dl> 
     <dl>
       <dt>实收款：</dt>
       <dd>
         <div>
           <span>
            <input type="text"  id="realCurrencyVal" name="orderVO.tranRealCurrencyVal" value="0" disabled="true"/>
           </span>
         </div>       
       </dd>
     </dl>  
     <dl>
       <dt>卖家账户：</dt>
       <dd>
         <div>
	         <select name="orderVO.sellerAccountIdOfCurrentUsed">
		         <#if cashdesk.sellerAccountList?has_content>
					<#list cashdesk.sellerAccountList as sellerAccount>
						<option value="#{sellerAccount.id}" />${sellerAccount.accountNumber}<br/>
					</#list>
				</#if>
			</select>
         </div>       
       </dd>
     </dl>       
     
    <#assign pre=1>
    <#assign toSendPointsRatio=0>
       		<#list cashdesk.sellerDepositChannelList as sellerDepositChannel>
       			<#if sellerDepositChannel.sellerRule.sellerRuleDetails?exists&&sellerDepositChannel.sellerRule.sellerRuleDetails?has_content>
       				<#list sellerDepositChannel.sellerRule.sellerRuleDetails as sellerRuleDetail>
       					<#if sellerRuleDetail.ruleType=="010">
       						 <#assign pre=sellerRuleDetail.formula?number>
       					</#if>
       					<#if sellerRuleDetail.ruleType=="100" && sellerRuleDetail.paramOfFormula=="getCurrencyValueAll">
       						 <#assign toSendPointsRatio=sellerRuleDetail.formula?number>
       					</#if>
       				</#list>
       			</#if>
			</#list>
			 <input type="hidden" id="preRatio" value="${pre}"/>
			 
 			<input type="hidden" id="toSendPointsRatio" value="${toSendPointsRatio}"/>
 			<input type="hidden" id="toSendPoints" name="orderVO.toSendPoints" value="0"/>
		      <div class="textBtn" >
		      <B> <a href='javascript:checkAndSubmit()' >确定</a></B>
		    <!--  	<input type="button" value="确定" onclick="checkAndSubmit()"/>     -->
		      </div>
     
         </form>            
     </div>          
    </td>
    <td class="adminBorderMiddleRight"></td>
  </tr>
</table>

<div class="adminBorderBottom">
  <div class="adminBorderBottomLeft"></div>
  <div class="adminBorderBottomRight"></div>
</div>
</div>

<div><!--数据图表--></div>

<!--底部-->
<#include "/WEB-INF/templates/common/admin_footer.ftl">
<!--底部-->
</body>
</html>
