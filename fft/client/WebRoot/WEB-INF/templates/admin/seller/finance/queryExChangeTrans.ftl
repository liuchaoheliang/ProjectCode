<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>财务管理-积分兑换</title>
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

<div class="middleBox">
<!-- 商家管理菜单开始 -->
<#if merchantRole?exists && merchantRole=='1'>
<#include "/WEB-INF/templates/admin/seller/finance/merchant_manage_menu.ftl">
<#else>
<#include "/WEB-INF/templates/common/merchant_manage_menu.ftl">
</#if>
<!-- 商家管理菜单结束 -->
<div class="rightBox" id="rightHeight" style="min-height:500px;">
<form id="listForm" action="query_exchange_trans_finance.action" method="post">
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
<div class="clear pt15">
      <table width="99%" border="0" cellspacing="0" cellpadding="0" class="tableA">
        <tr>
          <th width="9%">订单号</th>
          <th width="9%">商品名称</th>
          <th width="9%">更新时间</th>
          <th width="9%">支付方式</th>
          <th width="5%">现金</th>
          <th width="9%">分分通积分</th>
          <th width="9%">银行积分</th>
          <th width="11%">操作员工号</th>
          <th width="9%">操作员姓名</th>         
          <th width="11%">所属门店</th>   
          <th width="5%">状态</th>
          <th width="9%">操作</th>
        </tr>
        <#list pager.list as trans>
         <tr id="${trans.id?c}">	        
	        <td>${trans.id?c}</td>
	        <td>${trans.goodsName!'-'}</td>
	        <td title="${(trans.updateTime)!""}">${(trans.updateTime?date("yyyy-MM-dd"))!"-"}</td>
	        <td>
	        	<#if trans.payMethod == "00">
		        	分分通积分
		        <#elseif  trans.payMethod == "01">
		        	银行积分
		        <#elseif  trans.payMethod == "02">
		        	现金
		        <#elseif  trans.payMethod == "03">
		        	分分通积分+<br>现金
		        <#elseif trans.payMethod == "04">
		        	银行积分+现金
		        <#elseif trans.payMethod == "05">
		       		 分分通积分+<br>现金(比例)
		        <#elseif trans.payMethod == "06">
		        	银行积分+<br>现金(比例)
		        <#else>
		        -
		        </#if>	
	        </td>
	        <td>${(trans.currencyValueRealAll)!"-"}</td>
	        <td>${(trans.fftPointsValueRealAll)!"-"}</td>
            <td>${(trans.bankPointsValueRealAll)!"-"}</td>
            <td>${trans.beCode!'-'}</td>
            <td>         	
	          	${(trans.beName)!"-"}
         	</td>
         	<td>
         		${trans.storeStortName!"-"}
         	</td>
            <td>
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
            <td>
            	<a class="ajaxlinkDetail" href="exchange_tran_detail_Info.action?trans.id=${trans.id?c}&authIdElse=${trans.authTicketId!""}"  title="查看详情">详情</a>
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
