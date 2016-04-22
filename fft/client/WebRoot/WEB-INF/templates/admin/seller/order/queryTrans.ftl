<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>交易管理-积分返利</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
<script src="${base}/template/common/My97DatePicker/WdatePicker.js"></script>
<link href="${base}/template/common/css/pager.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/list.js"></script>
<link href="${base}/template/common/css/ajax_detail.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/ajax_detail.js"></script>
<script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>
<link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css" />
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
<form id="listForm" action="query_trans.action" method="post">
    <div class="fromDiv">
         <span><b>开始时间：</b><input name="pager.beginTime" id="txtStartDate" type="text"   onClick="WdatePicker({maxDate:'#F{$dp.$D(\'txtEndDate\',{d:-1});}'})" class="loginTextDate" value="${(pager.beginTime?split('|')[0])!""}" readonly="readonly" /></span>
 		<span><b>结束时间：</b><input name="pager.endTime" id="txtEndDate" type="text"  onClick="WdatePicker({minDate:'#F{$dp.$D(\'txtStartDate\',{d:0});}'})" class="loginTextDate" value="${(pager.endTime?split('|')[0])!""}" readonly="readonly" /></span>
         <#if isAdminSet?exists && isAdminSet == "1">
         	<span><b>操作员：</b><input name="beName" type="text" class="loginText4" value="${gongHao!""}"/></span>
         </#if>
         <span><b>手机号：</b><input name="pager.phone" type="text" class="loginText3" value="${phone!""}"/></span>
         <a href="#" class="fl"><div id="searchButton" class="adminBtn"><B>查询</B></div></a>
         <a href="#" class="download">下载查询结果</a>
    </div>
<div class="clear pt10">
      <table width="99%" border="0" cellspacing="0" cellpadding="0" class="tableA">
        <tr>
        	<th width="18%">商户</th>
          <th width="12%">订单号</th>
          <th width="11%">下单时间</th>
          <th width="15%">交易类型</th>
          <th width="8%">总价</th>
   <!--       <th width="10%">返利比例</th> -->
          <th width="8%">返利积分</th>
          <th width="11%">操作员姓名</th>
          <th width="7%">状态</th>
          <th width="10%">操作</th>
        </tr>
       <#list pager.list as trans>
         <tr id="${trans.id?c}">       	
         		<#if trans.merchant.mstoreFullName?exists && trans.merchant.mstoreFullName?length <= 7>	          		
	          		<td width="18%" title="${(trans.merchant.mstoreFullName?html)!""}">
	          			${(trans.merchant.mstoreFullName?html)!"-"}
	          		</td>
	         	<#elseif trans.merchant?exists && trans.merchant.mstoreFullName?exists >	         		
	         		<td width="18%" title="${(trans.merchant.mstoreFullName?html)!""}">
	          			${trans.merchant.mstoreFullName[0..7]}...
	          		</td>
	         	<#else>
	         		<td width="18%">
	          			-
	          		</td>
		        </#if>
         	<td width="12%">${trans.id?c}</td>
         	<td width="11%" title="${(trans.createTime)!""}">${(trans.createTime?date("yyyy-MM-dd"))!"-"}</td>
         	<td width="15%">
         		<#if trans.transType?exists && trans.transType == "01">
		        	积分兑换
		        <#elseif trans.transType?exists && trans.transType == "02">
		        	团购交易
		        <#elseif trans.transType?exists && trans.transType == "03">
		        	积分返利
		        <#elseif trans.transType?exists && trans.transType == "04">
		        	积分提现
		        <#elseif trans.transType?exists && trans.transType == "05">
		        	现金收款
		        <#elseif trans.transType?exists && trans.transType == "06">
		        	赠送积分
		        <#else>
		        	-
		        </#if>	
         	</td>
         	<td width="8%">${(trans.currencyValueRealAll)!"-"}</td>
   <!--    	<td width="10%">返利比例</td> -->  
         	<td width="8%">${(trans.fftPointsValueRealAll)!"-"}</td>
         	<td width="8%">
         	 	<#if trans.belongUserBecode?exists && trans.belongUserBecode != "">
	          		${(trans.belongUserBecode)!"-"}
	         	<#else>
	         		-
		        </#if>
         	</td>
         	<td width="10%">
         		<#if trans.state=="02">
		        	<span class="greenFont">成功</span>
		        <#elseif trans.state=="03">
		        	<span class="redFont">失败</span>
		        <#elseif trans.state=="01">
		        	<span class="blueFont">处理中</span>
		        <#else>
		        -
		        </#if>
         	</td>
         	<td width="7%">
         		<a class="ajaxlinkDetail" href="rabate_tran_detail_Info.action?trans.id=${trans.id?c}"  title="查看详情">详情</a>
         	</td>
      	</tr>
       </#list>    
      </table>
    </div>  
    <div class="page" id="resultListVal">
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
<script type="text/javascript" src="${base}/template/admin/js/expExcel.js"></script><!-- 导出excel功能 -->
<!--底部开始-->
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->       
</body>
</html>
