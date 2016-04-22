<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>
<#include "/WEB-INF/merchant/base/include.ftl">
<script type="text/javascript">


function checkAndSubmit(){
	create_order_form.submit();
}

function countRealCurrencyValue(currencyVal){
	
	if(currencyVal==""){
		$.layer({
			title:['分分通提示您',true],
			time:3,
			area : ['auto','auto'],
			dialog : {msg:'原价不能空',type : 8}	
		});
		return ;
	}
	var preRatio=document.getElementById('preRatio').value;
	
	currencyVal=currencyVal*preRatio;
	document.getElementById('realCurrencyVal').innerHTML=currencyVal;
	
}
</script>
</head>
<body>
<div class="adminTop">
  <div class="adminTopMiddle">
    <div class="adminTopLogo"><a href="#"><img src="images/adminTopLogo.png"></a></div>
    <div class="adminTopWelcome"><img src="images/adminTopIcon.png">你好：<B>凌统</B>，欢迎使用珠海农商银行管理系统。</div>
    <div class="adminToday">今天是：2012年12月6日 星期二</div>
  </div>
  
  <div class="adminTopRight">
    <div class="adminTopMenu"><a href="#">管理主页</a> | <a href="#">安全退出</a></div>
  </div>  
  
  <div class="adminMenu">
    <ul>
    <img src="images/syt.png">
    <li><a href="#" class="cure">基本信息</a></li>
    <li><a href="#">首页管理</a></li>
    <li><a href="#">信息管理</a></li>
    <li><a href="#">交易管理</a></li>
    <li><a href="#">客诉处理</a></li>
    <li><a href="#">安全中心</a></li>
    </ul>
  </div>
</div>


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
         <form id="create_order_form" action="/create_order.action" method="post">
         <#if errorMessage?exists>
         	<div class="colorRed">
					${errorMessage?if_exists}
         </div>
        </#if>
      <dl>
       <dt>手机号：</dt>
       <dd>
         <div>
         	<input type="text" class="loginText" name="orderVO.buyerPhone" value="" />
         </div>
       </dd>
     </dl>
      <dl>
       <dt>交易品名称：</dt>
       <dd>
         <div>
         	<input type="text" class="loginText" name="orderVO.tranDetail.tranGoods.transGoodsDisplay" value="" />
         </div>
       </dd>
     </dl>
      <dl>
       <dt>交易品数量：</dt>
       <dd>
         <div>
         <input type="text" class="loginText2" name="orderVO.tranDetail.tranGoods.transGoodsAmount" value="" />          
         </div>
       </dd>
     </dl>          
     <dl>
       <dt>原价：</dt>
       <dd>
         <div>
           <input type="text" class="loginText2" name="orderVO.tranDetail.currencyValue" value="" onBlur="countRealCurrencyValue(this.value)"/>
         </div>       
       </dd>
     </dl> 
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
           <input type="radio" name="orderVO.tran.payChannel" id="radio" value="01" />手机银行卡
           <input type="radio" name="orderVO.tran.payChannel" id="radio" value="02" />刷卡
           <input type="radio" name="orderVO.tran.payChannel" id="radio" value="03" />现金
           </span>
         </div>       
       </dd>
     </dl> 
     <dl>
       <dt>实收款：</dt>
       <dd>
         <div>
           <span id="realCurrencyVal">0</span>
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
       		<#list cashdesk.sellerDepositChannelList as sellerDepositChannel>
       			<#if sellerDepositChannel.sellerRule.sellerRuleDetails?exists&&sellerDepositChannel.sellerRule.sellerRuleDetails?has_content>
       				<#list sellerDepositChannel.sellerRule.sellerRuleDetails as sellerRuleDetail>
       					<#if sellerRuleDetail.ruleType=="010">
       						 <#assign pre=sellerRuleDetail.formula?number>
       					</#if>
       				</#list>
       			</#if>
			</#list>
			 <input type="hidden" id="preRatio" value="${pre}"/>

      <div class="textBtn"><input type="button" value="确定" onclick="checkAndSubmit()"/></div>
     
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

<div class="adminFoot">
  <div class="adminCopy"><span class="adminFootLogo"></span>Copyright 2012 Froad All Right reserved.</div>
</div>
</body>
</html>
