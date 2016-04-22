<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通查询-积分提现</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/pager.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/ajax_detail.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/select.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${base}/template/common/js/select.js"></script>
<script src="${base}/template/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/template/common/js/list.js"></script>
<script type="text/javascript" src="${base}/template/common/js/ajax_detail.js"></script>
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

  <form id="listForm" action="drawCash_trans_list.action" method="post">
    <div class="fromDiv">
         <span><b>开始时间：</b><input name="pager.beginTime" id="txtStartDate" type="text"   onClick="WdatePicker({maxDate:'#F{$dp.$D(\'txtEndDate\',{d:-1});}'})" class="loginTextDate" value="${(pager.beginTime?split('|')[0])!""}" /></span>
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
<#if isLotteryCertified?exists && isLotteryCertified == "00">
	  <div class="redmessage"><p>·请尽快绑定一张珠海农商银行卡，用于接收彩票中奖奖金或将积分提现！<a href="binding_bank_account_page.action">立即绑定</a></p></div>
</#if>
      <table width="99%" border="0" cellspacing="0" cellpadding="0" class="tableA">
        <tr>
        	<th width="18%">交易号</th>
          <th width="16%">提现积分</th>
          <th width="17%">提现金额</th>
          <th width="15%">手续费</th>
          <th width="19%">交易时间</th>
          <th width="15%">交易状态</th>
        </tr>
       <#list pager.list as trans>
         <tr id="${trans.id?c}">
         	<td width="18%">${trans.id?c}</td>
         	<td width="16%">${(trans.fftPointsValueRealAll)!"-"}</td>
         	<td width="17%">${(trans.currencyValueRealAll)!"-"}</td>
         	<td width="15%">${(trans.fftFactorage)!"-"}</td>
         	<td width="19%" title="${(trans.createTime)!""}">${(trans.createTime?date("yyyy-MM-dd"))!"-"}</td>
         	<td width="15%">
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
