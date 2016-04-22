<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通</title>
<#include "/WEB-INF/templates/common/include.ftl">

<link href="${base}/template/web/css/table.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/rightimg.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/explain.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/select.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${base}/template/common/js/list.js"></script>
<script src="${base}/template/common/js/validate.js" type="text/javascript"></script>

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
<#if pager?exists>

	<form id="exch" name="exch" method="post" action="exchange_recharge_phone.action">
 <!--充值开始-->
  <div class="tableList fl">
   <table>
  <tr>
    <td colspan="2" class="titlePay">充值后一分钟内到账，支持中国移动、联通、电信充值。</td>
    </tr>
  <tr>
    <th>手机号码：</th>
    <td><input type="text" name="transAddtionalInfoVo.phone" id="mobile"  class="loginText"></td>
    </tr>
  <tr>
    <th>确认手机号码：</th>
    <td><input  type="text" name="textfield2" id="mobile" class="loginText" /></td>
  </tr>
   <input type="hidden" name="pager.goodsCategoryId"  value="100001006">
  <input type="hidden" name="transDetails.goodsNumber"  value="1">
  <tr>
    <th>面值：</th>  
    <td>
   <#--
    	<#list pager.list as goodsExchangeRack>
    	<input type="radio" name="transDetails.goodsRackId" id="radio" value="#{goodsExchangeRack.id?if_exists}" />
    	</#list>   
    -->	
     
		<input type="text" name="transDetails.goodsRackId" id="radio" value="#{pager.id?if_exists}"  /> ${pager.cashPricing?if_exists}元
		
		<#assign avalidatePayMethod="">
		<#assign avalidatePrice="">
			<#if pager.isCash=="1" >
             	 <#if avalidatePayMethod!="">
             		 <#assign avalidatePayMethod=avalidatePayMethod+",">
             	 </#if>
             	  <#assign avalidatePayMethod=avalidatePayMethod+"02">
             	  
             	  <#if avalidatePrice!="">
             		 <#assign avalidatePrice=avalidatePrice+" 或  ">
             	 </#if>
             	  <#assign avalidatePrice=avalidatePrice+pager.cashPricing>
             	   <#assign avalidatePrice=avalidatePrice+'元'>
              </#if>
               <#if pager.isFftPoint=="1">
             	 <#if avalidatePayMethod!="">
             	 	<#assign avalidatePayMethod=avalidatePayMethod+",">
             	 </#if>
             	  <#assign avalidatePayMethod=avalidatePayMethod+"00">
             	  
             	   <#if avalidatePrice!="">
             		 <#assign avalidatePrice=avalidatePrice+" 或  ">
             	 </#if>
             	  <#assign avalidatePrice=avalidatePrice+pager.fftPointPricing>
             	   <#assign avalidatePrice=avalidatePrice+'个分分通积分'>
              </#if>
               <#if pager.isBankPoint=="1" >
             	  <#if avalidatePayMethod!="">
             	 	<#assign avalidatePayMethod=avalidatePayMethod+",">
             	 </#if>
             	  <#assign avalidatePayMethod=avalidatePayMethod+"01">
             	  
             	   <#if avalidatePrice!="">
             		 <#assign avalidatePrice=avalidatePrice+" 或  ">
             	 </#if>
             	  <#assign avalidatePrice=avalidatePrice+pager.bankPointPricing>
             	   <#assign avalidatePrice=avalidatePrice+'个银行积分'>
              </#if>
              <#if pager.isFftpointCash=="1" >
             	 <#if avalidatePayMethod!="">
             	 	<#assign avalidatePayMethod=avalidatePayMethod+",">
             	 </#if>
             	  <#assign avalidatePayMethod=avalidatePayMethod+"03">
             	  
             	    <#if avalidatePrice!="">
             		 <#assign avalidatePrice=avalidatePrice+" 或  ">
             	 </#if>
             	 <#assign fftpointCashPricing=pager.fftpointCashPricing>
             	 <#assign fftpointCashPricingArray=fftpointCashPricing?split('|')>
             	 
             	  <#assign avalidatePrice=avalidatePrice+fftpointCashPricingArray[0]>
             	  <#assign avalidatePrice=avalidatePrice+'个分分通积分 +'>
             	  <#assign avalidatePrice=avalidatePrice+fftpointCashPricingArray[1]>
             	   <#assign avalidatePrice=avalidatePrice+'元'>
              </#if>
               <#if pager.isBankpointCash=="1" >
             	 <#if avalidatePayMethod!="">
             	 	<#assign avalidatePayMethod=avalidatePayMethod+",">
             	 </#if>
             	  <#assign avalidatePayMethod=avalidatePayMethod+"04">
             	  
             	  
             	   <#if avalidatePrice!="">
             		 <#assign avalidatePrice=avalidatePrice+" 或  ">
             	 </#if>
             	 
             	  <#assign bankpointCashPricing=pager.bankpointCashPricing>
             	 <#assign bankpointCashPricingArray=bankpointCashPricing?split('|')>
             	  <#assign bankpointCashPricingArrayp=bankpointCashPricingArray[0] >
             	  <#assign avalidatePrice=avalidatePrice+bankpointCashPricingArrayp >
             	  <#assign avalidatePrice=avalidatePrice+"个银行积分+">
             	  <#assign avalidatePrice=avalidatePrice+bankpointCashPricingArray[1]>
             	   <#assign avalidatePrice=avalidatePrice+"元">
              </#if>
			<input type="hidden"  id="firstPayMethod" value="${avalidatePayMethod?if_exists}"  /> 
    
   
    </td>
  </tr>
  <tr>
    <th>价格：</th>
    <td class="redFont"><span id="showPrice">${avalidatePrice?if_exists}</span></td>
  </tr>
  <input type="hidden"  name="trans.payMethod" id="payMethod" value="00"  /> 
  
   <tr>
    <th>支付方式：</th>
    <td class="redFont">
    
    
      
      <span class="selectfrom fl ml10" id="avalidatePayMethod">
         
         </span>
     <script type="text/javascript" src="${base}/template/web/js/phone.js"></script>  
  <!--
     	<select name="trans.payMethod">
           <option  value="00" >分分通积分付款</option>
         </select>
    -->     
        
    </td>
  </tr>
  
  
  <tr>
    <th>&nbsp;</th>
    <td class="grayFont">温馨提示：如遇月初月末充值高峰，到账时间可能稍有延迟，敬请谅解。</td>
  </tr>
   </table> 
    
    <div class="w120 abtn">
     <a href="javascript:checkAndSubmit()"><div class="textBtn"><B>确定</B></div></a>  
   </div> 
    
    <div class="explain">
      <li>1、分分通平台话费充值服务覆盖中国移动、中国联通、中国电信的全国范围内的全部品牌的手机号码，采用在线支付，实时充
值，无需等待。</li>
      <li>1、分分通平台话费充值服务覆盖中国移动、中国联通、中国电信的全国范围内的全部品牌的手机号码，采用在线支付，实时充值，无需等待。</li>
      <li>2、在线直充的话费充值商品没有发送卡号密码的过程，由方付通实时为您的手机号码进行充值。目前可选的充值金额有50元、100元及300元等。</li>
      <li>3、系统是实时提交话费充值的，所以请确认您的手机号码填写正确，若手机号码填写错误，款项无法退回。由于话费充值的特殊性，充值成功后不予退款。</li>
      <li>4、最终到账请以实际时间为准。如遇超时未到账，请检查您的订单中手机号码的填写是否有误，同时请及时联系客服处理，客服电话：0756-3827999</li>
    </div> 
  </div> 
<!--充值结束-->
  </form>
</#if>
<!--内容结束-->
  
  <#include "/WEB-INF/templates/common/exch_right.ftl">                    
</div>
  
<!--清除浮动-->
<div class="clear"></div>
<!--清除浮动-->

<!--底部开始-->  
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
</body>
</html>
