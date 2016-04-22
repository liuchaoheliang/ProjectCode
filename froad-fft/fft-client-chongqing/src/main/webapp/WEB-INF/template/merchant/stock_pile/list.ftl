<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>库存列表</title>
<link rel="icon" href="${base}/favicon.ico" type="image/x-icon" />
<#include "/include/sources.ftl">
<link href="${base}/resources/merchant/css/merchant.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/common/css/pager.css" rel="stylesheet" type="text/css" />
<script src="${base}/resources/common/plugin/My97DatePicker/WdatePicker.js"></script>
<script src="${base}/resources/common/js/pager.js"></script>
<script src="${base}/resources/common/js/list.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//下载查询结果
	$('#downLoad').click(function(){
		var oldaction = "${base}/merchant/stock_pile/list.jhtml";
		var action = "${base}/merchant/stock_pile/downLoad.jhtml";
		$('#listForm').attr("action", action).submit();
		$('#listForm').attr("action", oldaction);
	});
	
});
</script> 
</head>
<body>
<#-- 头部 开始-->
<#include "/include/common/header.ftl">
<#-- 头部 结束-->

<div class="main">
  <div class="middleBox"> 
    <!--左侧导航开始-->
    <#include "/include/merchant/menu.ftl">
    <!--左侧导航结束-->
    
   
  <div class="rightBox" id="rightHeight">
   <div class="tit">
      <div class="miss" id="show" style="display：block;">
         <dl>
         	<dd><u>若有任何疑问，请咨询客服热线：13761758526  </u></dd>
         </dl>
        <a href="#" onclick="closed()"></a> 
      </div>
   </div>
  
  <form id="listForm" action="${base}/merchant/stock_pile/list.jhtml" method="post">
      <div class="fromDiv"> 
      开始时间：
         <input  readonly="true" name="pageFilterDto.startTime" id="txtStartDate" type="text"   onClick="WdatePicker({maxDate:'#F{$dp.$D(\'txtEndDate\',{d:-1});}'})" class="loginTextDate" value="${(page.pageFilterDto.startTime?string('yyyy-MM-dd'))!"${.now?string('yyyy-MM-dd')}"}" /> 结束时间：
          <input  readonly="true" name="pageFilterDto.endTime" id="txtEndDate" type="text"  onClick="WdatePicker({minDate:'#F{$dp.$D(\'txtStartDate\',{d:0});}'})" class="loginTextDate" value="${(page.pageFilterDto.endTime?string('yyyy-MM-dd'))!"${.now?string('yyyy-MM-dd')}"}" />
         <#if merchant_outlet_list?exists && isAdmin >
                       网点列表：<select name="merchantOutletId" id="select" style="width:80px">
          		 <option value="">--全部--</option>
          		 <#list merchant_outlet_list as outletList>
          		 	<option <#if outletId?exists && outletId==outletList.id>selected</#if> value="${outletList.id}">${outletList.name}</option>
          		 </#list>
            </select>
         </#if> 
        <input class="subBtn " value="查询" type="submit">
        <a href="#" id="downLoad">下载查询结果</a> 
       </div>
      <div class="clear pt15">
        <table width="99%" border="0" cellspacing="0" cellpadding="0" class="tableA">
          <tr>
            <#--<th width="13%">入库单号</th>-->
            <th width="10%">商品名称</th>
            <th width="15%">最后入库时间</th>
            <th width="15%">最后出库时间</th>
            <th width="10%">网点名称</th>
            <th width="10%">实时库存数量</th>
            <th width="8%">总库存数量</th>
          </tr>
          <#list page.resultsContent as stock>
          <tr>
            <td>${stock.productDto.name}</td>
            <#--<td>${stock.id}</td>-->
            <td>${(stock.lastIncomeTime?string("yyyy-MM-dd"))!"-"}</td>
            <td>${(stock.lastOutcomeTime?string("yyyy-MM-dd"))!"-"}</td>
            <td>${stock.merchantOutletDto.name}</td>
            <td>${stock.quantity}</td>
            <td>${stock.totalQuantity}</td>
            
          </tr>
        </#list>        
        </table>
      </div>
      <div class="page">
      	<#if (page.resultsContent?size > 0)>
         	<#include "/include/pager.ftl" />
       <#else>
         		没有找到记录!	
        </#if>
     </div>
     </form>
    </div>
  </div>
  
  <!--清除浮动-->
  <div class="clear"></div>
  <!--清除浮动--> 
</div>

<#-- 底部 -->
<#include "/include/common/footer.ftl">
<#-- 底部 -->
</body>
</html>