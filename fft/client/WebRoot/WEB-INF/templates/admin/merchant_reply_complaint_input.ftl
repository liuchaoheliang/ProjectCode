<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css"/>

<link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>
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
<#include "/WEB-INF/templates/common/merchant_manage_menu.ftl">
<!-- 商家管理菜单结束 -->
<div class="rightBox" id="rightHeight">
<form id="complaintReplyForm" action="merchantComplaint_reply.action" method="post">
	<input type="hidden" name="complaint.id" id="id" value="${complaint.id?c}"/>
<div class="clear pt10">
      <table width="99%" border="0" cellspacing="0" cellpadding="0" class="tableA">
        <tr>
          <th width="17%">订单号</th>
          <th width="56%">内容</th>
          <th width="16%">时间</th>
          <th width="11%">状态</th>
        </tr>
        <tr>
          <td>${(complaint.orderId)!"-"}</td>
          <td>${(complaint.content)!"-"}</td>
          <td>${(complaint.createTime?date("yyyy-MM-dd"))!"-"}</td>
          <td>
          	<#if complaint.isReply?exists>
	          	<#if (complaint.isReply == '10')>
	         		未回复
	         	<#elseif (complaint.isReply == '20')>
	         		已回复
		        </#if>
	        </#if>
          </td>
        </tr>
        <#if (complaint.replyList?size > 0)>
         	<#list complaint.replyList as complaintReply>
		        <tr>
		        	<td>${(complaintReply.createTime?date("yyyy-MM-dd"))!""} 回复：</td>
		        	<td colspan="3">${(complaintReply.content)!""}</td>
		        </tr>
	        </#list>
       <#else>
         	<tr>
         		<td colspan="4">没有回复信息</td>
         	</tr>	
        </#if>
      </table>
      <#if isAdmin?exists && isAdmin == '1'>
	      <textarea name="complaintReply.content" id="content" class="textAreaReply"></textarea>
	   <div class="ml150 abtn pt10 fl">
	     <a href="#"><div id="complaintReply" class="textBtn"><B>回复</B></div></a>  
	   </div> 
   	</#if>
   <div class="ml10 pt10 fl">
         <a href="${base}/merchant_complaint_list.action"><div class="textBtn"><B>查看所有投诉</B></div></a>  
         </div> 
    </div>  
   </form>
  </div>
</div>  
<script type="text/javascript" src="${base}/template/common/js/height.js"></script>
<!--底部开始-->
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束--> 
<script type="text/javascript"> 	
$("#complaintReply").click(function(){	
	var content = $("#content").val();
	var id = $("#id").val();
	if(content == ''){
		$.layer({
				title:['分分通提示您',true],
				time:3,
				area : ['auto','auto'],
				dialog : {msg:'请填写回复内容',type : 9}	
			});
		return ;
	}
	if(id == ''){
		$.layer({
				title:['分分通提示您',true],
				time:3,
				area : ['auto','auto'],
				dialog : {msg:'请选择一条投诉记录',type : 9}	
			});
		return ;
	}
	$("#complaintReplyForm").submit();		
});
</script>      
</body>
</html>
