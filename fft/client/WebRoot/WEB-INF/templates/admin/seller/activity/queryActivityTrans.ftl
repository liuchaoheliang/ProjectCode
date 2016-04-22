<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>交易管理-活动详情</title>
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
<form id="listForm" action="query_activity_trans.action" method="post">
<div class="clear pt10">
      <table width="99%" border="0" cellspacing="0" cellpadding="0" class="tableA">
        <tr>
        	<th width="10%">商户名称</th>
          	<th width="10%">活动名称</th>
          	<th width="10%">兑换时间</th>
          	<th width="10%">操作员</th>
          	<th width="10%">状态</th>
        </tr>
 		<#list pager.list as activity>
 			<tr>
 				<td>${(activity.merchant.mstoreFullName?html)!""}</td>
 				<td>${(activity.activityCustInfo.name?html)!""}</td>
 				<td>${(activity.consumeTime?html)!""}</td>
 				<td>${(activity.beName?html)!""}</td>
 				<td>${(activity.isConsume?html)!""}</td>
 			</tr>
 		</#list>
      </table>
    </div>  
	<div class="page">
    	<#if (pager.list?size > 0)>
         	<#include "/WEB-INF/templates/common/pager.ftl" />
       	<#else>
         		没有找到相关记录!	
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
