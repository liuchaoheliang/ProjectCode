<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>财务管理-积分返利</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
<script src="${base}/template/common/My97DatePicker/WdatePicker.js"></script>
<link href="${base}/template/common/css/pager.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/list.js"></script>
<link href="${base}/template/common/css/ajax_detail.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/ajax_detail.js"></script>
<script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>
<link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/select2.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/select.js"></script>


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
<#if merchantRole?exists && merchantRole=='1'>
<#include "/WEB-INF/templates/admin/seller/finance/merchant_manage_menu.ftl">
<#else>
<#include "/WEB-INF/templates/common/merchant_manage_menu.ftl">
</#if>
<!-- 商家管理菜单结束 -->

<div class="rightBox" id="rightHeight" style="min-height:500px;">
<form id="listForm" action="query_trans_finance.action" method="post">
    <div class="fromDiv">
        <span><b>开始时间：</b><input name="pager.beginTime" id="txtStartDate" type="text"   onClick="WdatePicker({maxDate:'#F{$dp.$D(\'txtEndDate\',{d:-1});}'})" class="loginTextDate" value="${(pager.beginTime?split('|')[0])!""}" readonly="readonly" /></span>
 		<span><b>结束时间：</b><input name="pager.endTime" id="txtEndDate" type="text"  onClick="WdatePicker({minDate:'#F{$dp.$D(\'txtStartDate\',{d:0});}'})" class="loginTextDate" value="${(pager.endTime?split('|')[0])!""}" readonly="readonly" /></span>
         <span><b>手机号：</b><input name="pager.phone" type="text" class="loginText3" value="${phone!""}"/></span>
<a href="#" class="fl"><div id="searchButton" class="adminBtn"><B>查询</B></div></a>
    <a href="#" class="download">下载查询结果</a>

    </div>
    
    <div class="fromDiv">
 <span>所属门店：</span>
          <span class="ml5" id="uboxstyle">
          
             <select name="storeId" class="default" id="language_mac">
            	<#if merchantRole=="-1" || (merchantRole=="1" && belongStoreId=="")>
	              	<option value="">分店：不限</option>
		              	<#list stores as st>
		              		<option value="${st.id?c}" <#if storeId?exists && (st.id?c)==(storeId) >selected="selected"</#if> />${st.shortName}</option>
		              	</#list>
              	<#else>
	              		<option value="${belongStoreId}"  />${belongStoreShortName}</option>
	            </#if>
            </select>
          </span>
       
    </div> 
<div class="clear pt10">
      <table width="99%" border="0" cellspacing="0" cellpadding="0" class="tableA">
        <tr>
          <th width="10%">订单号</th>
          <th width="11%">更新时间</th>
          <th width="8%">交易类型</th>
          <th width="8%">总金额</th>
          <th width="8%">返利积分</th>
          <th width="10%">操作员工号</th>
          <th width="10%">操作员姓名</th>
           <th width="18%">所属门店</th>
          <th width="7%">状态</th>
          <th width="10%">操作</th>
        </tr>
       <#list pager.list as trans>
         <tr id="${trans.id?c}">       	
         		
         	<td >${trans.id?c}</td>
         	<td title="${(trans.updateTime)!""}">${(trans.updateTime?date("yyyy-MM-dd"))!"-"}</td>
         	<td >
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
         	<td>${(trans.currencyValueRealAll)!"-"}</td>
         	<td>${(trans.fftPointsValueRealAll)!"-"}</td>
         	<td>
         	 	${(trans.beCode)!"-"}
         	</td>
         	<td>
         	 	${(trans.beName)!"-"}
         	</td>
         	<td>
         	 	${(trans.storeStortName)!"-"}
         	</td>
         	<td>
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
         	<td>
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
<script type="text/javascript" src="${base}/template/admin/js/financeExcel.js"></script><!-- 导出excel功能 -->
<!--底部开始-->
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->       
</body>
</html>
