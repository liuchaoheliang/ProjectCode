<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通查询-积分返利</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/pager.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/ajax_detail.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/select.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${base}/template/common/js/select.js"></script>
<script type="text/javascript" src="${base}/template/common/js/ajax_detail.js"></script>
<script type="text/javascript" src="${base}/template/common/js/list.js"></script>
<script src="${base}/template/common/My97DatePicker/WdatePicker.js"></script>

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

<!--广告栏开始-->
<div class="ad"><img src="${base}/template/member/img/123213.png"></div>
<!--广告栏结束-->

<div class="middleBox">
<!-- 会员个人信息菜单开始 -->
<#include "/WEB-INF/templates/common/user_manage_menu.ftl">
<!-- 会员个人信息菜单结束 -->

<div class="rightBox" id="rightHeight">

  <form id="listForm" action="rebate_trans_list.action" method="post">
     <div class="fromDiv">
         <span><b>开始时间：</b><input name="pager.beginTime" id="txtStartDate" type="text"  onClick="WdatePicker({maxDate:'#F{$dp.$D(\'txtEndDate\',{d:-1});}'})" class="loginTextDate" value="${(pager.beginTime?split('|')[0])!""}" /></span>
		<span><b>结束时间：</b><input name="pager.endTime" id="txtEndDate" type="text"  onClick="WdatePicker({minDate:'#F{$dp.$D(\'txtStartDate\',{d:0});}'})" class="loginTextDate" value="${(pager.endTime?split('|')[0])!""}" /></span>
		 <span class="ml5" id="uboxstyle">
			<select name="pager.state" id="language_mac">
				<option value="">请选择</option>
	            <option value="02" <#if pager.state?exists && pager.state == '02'>selected</#if>>成功</option>
	            <option value="03" <#if pager.state?exists && pager.state == '03'>selected</#if>>失败</option>
	            <option value="04" <#if pager.state?exists && pager.state == '04'>selected</#if>>关闭</option>
	            <option value="01" <#if pager.state?exists && pager.state == '01'>selected</#if>>处理中</option>  
			</select>
         </span>
         <a href="#" class="fl"><div id="searchButton" class="adminBtn"><B>查询</B></div></a>
    </div>
       


<div class="clear pt10">

      <table width="99%" border="0" cellspacing="0" cellpadding="0" class="tableA">
        <tr>
        	<th width="24%">商户</th>
          <th width="12%">订单号</th>
          <th width="11%">下单时间</th>
          <th width="13%">支付方式</th>
          <th width="12%">支付现金</th>
   <!--       <th width="10%">返利比例</th> -->
          <th width="8%">返利积分</th>
          <th width="10%">状态</th>
          <th width="10%">操作</th>
        </tr>
       <#list pager.list as trans>
         <tr id="${trans.id?c}">
	       <td width="24%" title="商户">
	       		<#if trans.merchant?exists && trans.merchant.mstoreFullName?exists && trans.merchant.mstoreFullName?length <= 7>
	          		${(trans.merchant.mstoreFullName?html)!""}
	         	<#elseif trans.merchant?exists && trans.merchant.mstoreFullName?exists >
	         		${trans.merchant.mstoreFullName?html[0..7]}...
	         	<#else>
	         		-
		        </#if>
	       </td>
         	<td width="12%">${trans.id?c}</td>
         	<td width="11%" title="${(trans.createTime)!""}">${(trans.createTime?date("yyyy-MM-dd"))!"-"}</td>
         	<td width="13%">
         		<#if trans.payMethod == "00">
		        	分分通积分
		        <#elseif  trans.payMethod == "01">
		        	银行积分
		        <#elseif  trans.payMethod == "02">
		        	现金
		        <#elseif  trans.payMethod == "03">
		        	分分通积分+<br>现金
		        <#elseif trans.payMethod == "04">
		        	银行积分+<br>现金
		        <#else>
		        -
		        </#if>
         	</td>
         	<td width="12%">${(trans.currencyValueRealAll)!"-"}</td>
   <!--    	<td width="10%">返利比例</td> -->  
         	<td width="8%">${(trans.fftPointsValueRealAll)!"-"}</td>
         	<td width="10%">
         		<#if trans.state=="02">
		        	<span class="greenFont">成功</span>
		        <#elseif  trans.state=="03">
		        	<span class="redFont">失败</span>
		       <#elseif trans.state=="01">
		        	<span class="blueFont">处理中</span>
		        <#elseif trans.state=="04">
		        	<span class="">关闭</span>
		        <#else>
		        	-
		        </#if>
         	</td>
         	<td width="7%">
	         	<a href="toComplaint.action?complaint.orderId=${trans.id?c}&complaint.toUserId=${(trans.seller.userId)!""}">投诉</a>
         		<a class="ajaxlinkDetail" href="rabate_tran_detail_Info.action?trans.id=${trans.id?c}"  title="查看详情">详情</a>
         	</td>
      	</tr>
       </#list>    
      </table>
    </div>  
    <div class="page">
		<#if (pager.list?size > 0)>
         	<#include "/WEB-INF/templates/common/pager.ftl" />
       <#else>
         		没有找到交易!	
        </#if>
	</div>
	</form>
  </div>
</div>  
<script type="text/javascript" src="${base}/template/common/js/height.js"></script>
<!--底部开始-->
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->    
</body>
</html>
