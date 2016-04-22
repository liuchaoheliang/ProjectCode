<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>退换货列表</title>
<link rel="icon" href="${base}/favicon.ico" type="image/x-icon" />
<#include "/include/sources.ftl">
<link href="${base}/resources/merchant/css/merchant.css" rel="stylesheet" type="text/css" />
<script src="${base}/resources/common/plugin/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//下载查询结果
	$('#downLoad').click(function(){
		var oldaction = "${base}/merchant/return_sale/list.jhtml";
		var action = "${base}/merchant/return_sale/downLoad.jhtml";
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
     <form id="listForm" action="${base}/merchant/return_sale/list.jhtml" method="post">
      <div class="fromDiv"> 
      	开始时间：
        <input  readonly="true" name="pageFilterDto.startTime" id="txtStartDate" type="text"   onClick="WdatePicker({maxDate:'#F{$dp.$D(\'txtEndDate\',{d:-1});}'})" class="loginTextDate" value="${(page.pageFilterDto.startTime?string('yyyy-MM-dd'))!"${.now?string('yyyy-MM-dd')}"}" />
        结束时间：
        <input  readonly="true" name="pageFilterDto.endTime" id="txtEndDate" type="text"  onClick="WdatePicker({minDate:'#F{$dp.$D(\'txtStartDate\',{d:0});}'})" class="loginTextDate" value="${(page.pageFilterDto.endTime?string('yyyy-MM-dd'))!"${.now?string('yyyy-MM-dd')}"}" />
         <#if merchant_outlet_list?exists && isAdmin >
                     网点列表：<select name="merchantOutletId" id="select" style="width:80px">
          		 <option value="">--全部--</option>
          		 <#list merchant_outlet_list as outletList>
          		 	<option <#if outletId?exists && outletId==outletList.id>selected</#if>  value="${outletList.id}">${outletList.name}</option>
          		 </#list>
          </select>
         </#if>
        类型：
        
        <select name="type" id="select" style="width:80px">
          <option value="">--全部--</option>
          <option <#if  type?exists && type=='sale_return'>selected</#if> value="sale_return">退货</option>
          <option <#if  type?exists && type=='sale_swap'>selected</#if> value="sale_swap">换货</option>
        </select>
         <input class="subBtn " value="查询" type="submit">
         <a href="#" id="downLoad">下载查询结果</a>
      </div>
      <div class="clear pt15">
        <table width="99%" border="0" cellspacing="0" cellpadding="0" class="tableA">
          <tr>
            <th width="10%">编号</th>
            <th width="10%">创建时间</th>
            <th width="10%">类型</th>
            <th width="10%">数量</th>
            <th width="15%">门店</th>
            <th width="10%">操作员</th>
            <th width="25%">原因</th>
          </tr>
		<#list page.resultsContent as returnSale>
          <tr>
          	<td>${returnSale.sn}</td>
            <td>${(returnSale.createTime?string("yyyy-MM-dd"))!"-"}</td>
            <td>
            <#if returnSale.type="sale_return">
            	退货
            <#elseif returnSale.type="sale_swap">
            	换货
            <#else>
            	未知
            </#if>
            </td>
            <td>${returnSale.returnSaleDetailDto.quantity!'-'}</td>
            <td>${returnSale.merchantOutletDto.name!'-'}</td>
            <td>${returnSale.sysUserDto.username}</td>
            <td>${returnSale.reason}</td>

          </tr>
        </#list>    
        </table>
      </div>
      <div class="page">
	      	<#if (page.resultsContent?size > 0)>
	         	<#include "/include/pager.ftl" />
	       <#else>
	         	没有记录!	
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