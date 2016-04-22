<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/buy/css/confirm.css" rel="stylesheet" type="text/css" />


<script type="text/javascript" src="${base}/template/common/js/list.js"></script>

<script type="text/javascript">
function checkAndSubmit(){
	exch.submit();
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


<div class="box1010 pt10 clear">
<!--内容开始-->
<div style='color:red;'>${errorMsg?if_exists}</div>
<#if pager?exists && pager.goods?exists>
	<form id="exch" name="exch" method="post" action="exchange_pay.action">
	   <div class="confirm">
	    <dl>
	      <dt>商品名称：</dt>
	      <dd>
	        <div> <span>${pager.goods.goodsName?if_exists}</span> </div>
	      </dd>
	    </dl>
	    <dl>
	      <dt>商品数量：</dt>
	      <dd>
	        <div> <span>${transDetails.goodsNumber?if_exists}</span> </div>
	      </dd>
	    </dl>
	    <dl>
	      <dt>面值：</dt>
	      <dd>
	        <div> <span>${pager.cashPricing?if_exists}</span> </div>
	      </dd>
	    </dl>
	    <dl>
	      <dt>充值手机号码：</dt>
	      <dd>
	        <div> <span>${transAddtionalInfoVo.phone?if_exists}</span> </div>
	      </dd>
	    </dl>
	    
	    <dl>
	      <dt>支付方式：</dt>
	      <dd>
	        <div> 
	        	<#if trans?exists && trans.payMethod?exists>
	        	<#if trans.payMethod="00">
	        	<span>分分通积分</span> 
	        	</#if>
	        	<#if trans.payMethod="01">
	        	<span>银行积分</span> 
	        	</#if>
	        	<#if trans.payMethod="02">
	        	<span>现金（珠海农商银行手机银行卡支付）</span> 
	        	</#if>
	        	<#if trans.payMethod="03" || trans.payMethod="05" >
	        	<span>分分通积分+现金</span> 
	        	</#if>
	        	<#if trans.payMethod="04" || trans.payMethod="06">
	        	<span>银行积分+现金</span> 
	        	</#if>
	        </#if>
	        </div>
	      </dd>
	    </dl>
	    <dl>
	      <dt>支付金额：</dt>
	      <dd>
	        <div> 
	        			<#assign hasOne=0>
			        	<#if trans?exists>
				        	<span>
					        	<#if trans.currencyValueRealAll?exists && trans.currencyValueRealAll != "0">
					        	${trans.currencyValueRealAll?if_exists} 元 
					        	<#assign hasOne=1>
					        	</#if>
					        	<#if trans.fftPointsValueRealAll?exists && trans.fftPointsValueRealAll != "0">
					        		<#if hasOne != 0>
					        			+
					        		</#if>
					        	${trans.fftPointsValueRealAll?if_exists} 分分通积分 </span> 
					        	</#if>
					        	<#if trans.bankPointsValueRealAll?exists && trans.bankPointsValueRealAll != "0">
					        	<#if hasOne != 0>
					        			+
					        		</#if>
					        	${trans.bankPointsValueRealAll?if_exists} 银行积分
					        	</#if>
				        	 </span> 
			        	</#if>
	        </div>
	      </dd>
	    </dl>
	    <!--
	    <dl>
	      <dt>支付来源：</dt>
	      <dd>
	        <div> <span>珠海分数 或 分分通分数 + 现金 （珠海农商银行手机银行卡支付）</span> </div>
	      </dd>
	    </dl>
	    
	    -->
	     <input type="hidden" name="trans.id" value="<#if trans?exists>#{trans.id}</#if>">
	    <div class="conbtn fr mr20">
	      <a href='javascript:checkAndSubmit()'><div class="textBtn"><B>确认订单，去付款</B></div></a>
	    </div>  
	  </div>
	</div>
  </form>
</#if>
<!--内容结束-->
<!--
  <#include "/WEB-INF/templates/common/exch_right.ftl">      
  -->               
</div>
  
<!--清除浮动-->
<div class="clear"></div>
<!--清除浮动-->

<!--底部开始-->  
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
</body>
</html>
