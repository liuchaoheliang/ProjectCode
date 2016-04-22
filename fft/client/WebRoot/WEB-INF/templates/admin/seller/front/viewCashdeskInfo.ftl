<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通-收银台</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
<script src="${base}/template/common/My97DatePicker/WdatePicker.js"></script>
<link href="${base}/template/common/css/pager.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/list.js"></script>
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
  	<#if sellerList?exists>
  			
    <div class="mt10 ml5 mb5"> 
    	<#assign isDisplay=false>
    	<#list sellerInfo as seller>
	       <#if seller.sellerType?exists && seller.sellerType == "03" >
		    <a href="add_goods_page.action" target="_blank"><img src="${base}/template/admin/images/shoukuan.png"></a> 
		   </#if>
		   <#if seller.sellerType?exists && seller.sellerType == "06" >
		    <a href="send_points_page.action" target="_blank"><img src="${base}/template/admin/images/zsjf.png"></a>
		    </#if>
		   <#if seller.sellerType?exists && isDisplay==false && (seller.sellerType == "01" || seller.sellerType == "02" || seller.sellerType == "08") >
		    <#assign isDisplay=true>
		    <a href="toAuthentication.action"><img src="${base}/template/admin/images/tgrz.png"></a> 
		   </#if>
	    </#list>
    </div>
     <table width="99%" border="0" cellspacing="0" cellpadding="0" class="tableA">
        <tr>
          <th width="25%">收款渠道</th>
          <th width="21%">渠道规则</th>
          <th width="13%">积分返利比例</th>
          <th width="19%">银行收款账户名</th>
          <th width="22%">银行收款账户号</th>
        </tr>
	  		<#list sellerList as seller>
	  			<#if seller.sellerType?exists && ( seller.sellerType == "03" || seller.sellerType == "06") >
	  			<#list seller.sellerChannelList as sellerChannel>
			        <tr>
			          <td>${sellerChannel.fundsChannel.channelFullName?if_exists}</td>
			          <td>${seller.sellerTypeName}</td>
			          <td><#if sellerChannel.sellerRule?exists>${sellerChannel.sellerRule.fftPointsRule?if_exists}%</#if></td>
			          <td>${sellerChannel.accountName?if_exists}</td>
			          
			      	  <#assign userAccountNoShow="">
				      <#if sellerChannel.accountNumber?exists>
				      	<#assign userAccountNo=sellerChannel.accountNumber>
				      	<#assign userAccountNoBefore=sellerChannel.accountNumber?substring(0,4)>
				      	<#assign userAccountNoAfter=sellerChannel.accountNumber?substring(sellerChannel.accountNumber?length-4)>
				      	<#assign userAccountNoShow=userAccountNoBefore+"*******"+userAccountNoAfter>
				      </#if>
				      
			          <td>${userAccountNoShow?if_exists}</td>
			        </tr>
		        </#list>
		        </#if>
	        </#list>
      </table>
   <table width="99%" border="0" cellspacing="0" cellpadding="0"  class="tableB">
    <tr>
          <td colspan="6"><b class="redFont">最近10笔交易</b></td>
        </tr>
        <tr>
          <th width="15%">订单号</th>
          <th width="16%">手机号</th>
          <th width="10%">交易时间</th>
          <th width="14%">交易金额</th>
          <th width="12%">返利积分额</th>
          <th width="10%">状态</th>
          <!--
          <th width="10%">商品</th>
          <th width="13%">操作</th>
          
          -->
        </tr>
        <#if pager?exists && pager.list?has_content>
	       <#list pager.list as trans>
	         <tr id="${trans.id?c}">
		        <td width="15%" height="25">
		       <#--<B> <a href="#" target="_blank">${trans.id?c}</a></B> -->
		        ${trans.id?c}
		        </td>
		        <td width="16%"><span><#if trans.phone?exists>${trans.phone!''}</#if></span></td>
		        <td width="10%"><span>${trans.createTime?date("yyyy-MM-dd")!""}</span></td>
	            <td width="14%"><span>${trans.currencyValueRealAll!''}</span></td>
	            <td width="12%"><span>${trans.fftPointsValueRealAll!''}</span></td>
		        <td width="10%"><span >
		        <#if trans.state?exists && (trans.state == '02')>
		        	<span class="greenFont">成功</span>
		        <#elseif  trans.state?exists && (trans.state == '03')>
		        	<span class="redFont">失败</span>
		        <#elseif trans.state?exists && (trans.state == '01')>
		        	<span class="blueFont">处理中</span>
		        <#else>
		        -
		        </#if>
		        </span></td>
		        <!--
	            <td width="10%">
	            <span>
	            	<#list pager.transDetailsList as transDetail>
	            		<#if transDetail.goods?exists>
	            		${transDetail.goods.goodsName?if_exists}
	            		</#if>	
	            	</#list>
	            </span>
	            </td>	        
		        <td width="13%">
		       	详情     
		        </td>  -->
	      	</tr>
	       </#list>   
       </#if> 
   </table>
     </#if>
  </div>
 
</div>

<!-- <script type="text/javascript" src="${base}/template/common/js/height.js"></script> -->
<!--底部开始-->
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
</body>
</html>
