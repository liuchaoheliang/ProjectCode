<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/buy/css/confirm.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/inforbox.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/rightimg.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/explain.css" rel="stylesheet" type="text/css" />

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
<div class="miss" style="display:none" id="show">
	<div id="errorMsg"><h2></h2></div>
 
	<a href="#" onclick="closed()"></a>
</div>
<#if errorMsg?exists>
<div class="miss">
	<div id="errorMsg"><h2>${errorMsg?if_exists} </h2></div>
	<a href="#" onclick="closed()"></a>
</div>
</#if>

<div class="box1010 pt10 clear">
<!--内容开始-->
<div style='color:red;'>${errorMsg?if_exists}</div>
<#--<#if pager?exists && pager.goods?exists>  -->
	<form id="exch" name="exch" method="post" action="exchange_currency_pay.action">
	   <div  class="put fl">
	   

    <div class="stepfen"></div>
     <div class="step">
      <li class="step01">输入积分</li>
      <li class="colorRed step08">核对信息</li>
      <li class="step03">申请结果</li>
    </div>     
<table>
  <tr>
    <th>提现订单号为：</th>
    <td><#if trans?exists>#{trans.id}</#if></td>
    </tr>
  <tr>
    <th>银行卡信息：</th>
    <td>珠海农商银行（<#if userCertification?exists>
      ${userCertification.accountNo?if_exists}</#if>）</td>
  </tr>
 <#--
   <#assign currencyValueAll=0>
   <#assign fftFactorage=0>
   <#assign getCurrency=0>
   <#if trans?exists && trans.currencyValueAll?exists && trans.fftFactorage?exists>
	  <#assign currencyValueAll=trans.currencyValueAll?c>
	  <#assign fftFactorage=trans.fftFactorage?c>
	  <#assign getCurrency=currencyValueAll-fftFactorage>
  </#if>
  -->
  <tr>
    <th>提现的积分为：</th>  
    <td><b id="points">${transDetails.goodsNumber?if_exists}</b> 分</td>
  </tr>
  <tr>
    <th>提现金额为：</th>  
    <td><b id="rmb"></b>${trans.currencyValueAll?if_exists}元</td>
  </tr>
  <tr>
    <th>手续费：</th>      
    <td ><b id="fee"><#if trans?exists>
	        	${trans.fftFactorage?if_exists} 元 
	        	</#if></b></td>
  </tr>
  <tr>
    <th>&nbsp;</th>    
    <td>&nbsp;</td>
  </tr>
   </table> 
	   
	   
	  <!-- 
	    <dl>
	      <dt>项目名称：</dt>
	      <dd>
	        <div> <span>分分通积分提现</span> </div>
	      </dd>
	    </dl>
	    <dl>
	      <dt>提现的分分通积分：</dt>
	      <dd>
	        <div> <span>${transDetails.goodsNumber?if_exists}</span> </div>
	      </dd>
	    </dl>
	    
	   
	    <dl>
	      <dt>提现的金额：</dt>
	      <dd>
	        <div> 
	        	<#if trans?exists>
	        	<span>${trans.currencyValueAll?if_exists} 元 
	        	</#if>
	        </div>
	      </dd>
	    </dl>
	    
	    <dl>
	      <dt>提现手续费：</dt>
	      <dd>
	        <div> 
	        	<#if trans?exists>
	        	<span>${trans.fftFactorage?if_exists} 元 
	        	</#if>
	        </div>
	      </dd>
	    </dl>
	   -->
	     <input type="hidden" name="trans.id" value="<#if trans?exists>#{trans.id}</#if>">
	    <div class="w100 abtn">
	      <a href='javascript:checkAndSubmit()'><div class="textBtn"><B>确认</B></div></a>
	    </div>  
	    
	      <div class="explain">
	      <li>1、手机号码用于接收短信通知，请认真填写。</li>
	      <li>2、服务费费率为输入金额的1%，最低收取2元，最高不超过50元。</li>
	      <li>3、交易号支付业务，请确保提供正确的支付宝交易号和相符的交易金额。</li>
	    </div> 
	  </div>
	
  </form>
<#--</#if>  -->
<!--内容结束-->


  <#include "/WEB-INF/templates/common/exch_right.ftl">                     
</div>
  
<!--清除浮动-->
<div class="clear"></div>
<!--清除浮动-->

<!--底部开始-->  
<#include "/WEB-INF/templates/common/footer.ftl">
</div>
</body>
</html>
