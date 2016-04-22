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
	var senda = $("#senda");
	senda.attr("href","javascript:void(0);");
	senda.find("div").attr("class","gryBtn");	
	senda.find('B').html("请等待...");
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
	<form id="exch" name="exch" method="post" action="transCarry.action">
	<input type="hidden" name="trans.sellerId" value="${trans.sellerId}" />
	<input type="hidden" name="trans.channelId" value="${trans.channelId}" />
	<input type="hidden" name="transDetails.goodsRackId" value="${transDetails.goodsRackId}" />
	<input type="hidden" name="transDetails.goodsNumber" value="${transDetails.goodsNumber}" />
	   <div  class="put fl">
	   

    <div class="stepfen"></div>
     <div class="step">
      <li class="step01">输入积分</li>
      <li class="colorRed step08">核对信息</li>
      <li class="step03">申请结果</li>
    </div>     
<table>
    <th>银行卡信息：</th>
    <td>珠海农商银行（${accountNo!""}）</td>
  </tr>

  <tr>
    <th>提现的积分为：</th>  
    <td><b id="points">${transDetails.goodsNumber?if_exists}</b> 分</td>
  </tr>
  <tr>
    <th>提现金额为：</th>  
    <td><b id="rmb"></b>${trans.currencyValueRealAll?if_exists}元</td>
  </tr>
  <tr>
    <th>手续费：</th>      
    <td ><b id="fee"><#if trans?exists>
	        	${trans.fftFactorage?if_exists} 元 
	        	</#if></b></td>
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
	    <div class="w150 abtn">
	      <a href='javascript:checkAndSubmit()' id="senda"><div class="textBtn"><B>确认</B></div></a>
	    </div>  
	    
	      <div class="explain">
	     <li>1、手机号码用于接收短信通知，请认真填写。</li>
      <li>2、提现积分数为整数，且为10的整数倍，提现手续费按5%收取，最低1元，最高50元。</li>
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
