<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通-客诉处理</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css"/>
<link href="${base}/template/common/css/pager.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/select.css" rel="stylesheet" type="text/css" />
<script src="${base}/template/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/template/common/js/select.js"></script>
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
<form id="listForm" action="merchant_complaint_list.action" method="post">
	<div class="fromDiv">
         <span><b>开始时间：</b><input name="pager.beginTime" id="txtStartDate" type="text"   onClick="WdatePicker({maxDate:'#F{$dp.$D(\'txtEndDate\',{d:-1});}'})" class="loginTextDate"  value="${(pager.beginTime?split('|')[0])!""}" /></span>
		<span><b>结束时间：</b><input name="pager.endTime" id="txtEndDate" type="text"  onClick="WdatePicker({minDate:'#F{$dp.$D(\'txtStartDate\',{d:0});}'})" class="loginTextDate" value="${(pager.endTime?split('|')[0])!""}" /></span>
 		<span class="ml5" id="uboxstyle">
            <select name="pager.isReply" class="default" id="language_mac">
              	<option value="">请选择</option>
        		<option value="10" <#if pager.isReply?exists && pager.isReply != '' && pager.isReply=="10">selected</#if>>未回复</option>
        		<option value="20" <#if pager.isReply?exists && pager.isReply != '' && pager.isReply=='20'>selected</#if>>已回复</option>
            </select>
          </span>
         <a href="#" class="fl"><div id="searchButton" class="adminBtn"><B>查询</B></div></a>
    </div>
	<div class="clear pt10">
      <table width="99%" border="0" cellspacing="0" cellpadding="0" class="tableA">
        <tr>
          <th width="14%">订单号</th>
          <th width="48%">内容</th>
          <th width="16%">时间</th>
          <th width="11%">状态</th>
          <th width="11%">操作</th>
        </tr>
        <#list pager.list as complaint>
        <tr>
          <td>${(complaint.orderId)!""}</td>
          <td>                   
          	<#if complaint.content?exists && complaint.content?length <= 20>
          		${(complaint.content)!"-"}
          	<#elseif complaint.content?exists >
	         	${complaint.content[0..20]}...
         	<#else>
         		-
	        </#if>
	      </td>
          <td>${(complaint.createTime?date("yyyy-MM-dd"))!"-"}</td>
          <td>
          	<#if (complaint.isReply == '10')>
         		<span class="redFont">未回复</span>
         	<#elseif (complaint.isReply == '20')>
         		<span class="greenFont">已回复</span>
	        </#if>
          </td>
          <td>
          	<#if (complaint.isReply == '10')>
         		<a href="to_merchantComplaint_reply.action?complaint.id=${complaint.id?c}&complaint.toUserId=${(user.userID)!""}">投诉回复</a>
         	<#elseif (complaint.isReply == '20')>
         <!-- 		<a href="merchantComplaint_reply?complaint.id=${complaint.id?c}&complaint.toUserId=${user.userID!""}">查看</a> -->
         		<a href="to_merchantComplaint_reply.action?complaint.id=${complaint.id?c}&complaint.toUserId=${(user.userID)!""}">继续回复</a>
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
         		暂时没有投诉记录!	
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
