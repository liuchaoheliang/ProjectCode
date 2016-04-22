<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>客户服务-我的投诉</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>
<!--
<link href="${base}/template/common/css/inforbox.css" rel="stylesheet" type="text/css" />
20130608: 由于该js在页面上没有找到，所以暂时注释掉
-->
<link href="${base}/template/common/css/select.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${base}/template/common/js/select.js"></script>
<#if !complaint.id??>
	<#assign isAdd = true />
<#else>
	<#assign isEdit = true />
</#if>
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
  <form id="complaintForm" action="add_complaint.action" method="post">
	<input type="hidden" name="complaint.orderId" id="orderId" value="${(complaint.orderId)!""}"/>
	<input type="hidden" name="complaint.toUserId" id="toUserId" value="${(complaint.toUserId)!""}"/>
      <div class="inforBox">
        <dl>
          <dt>涉及单号：</dt>
          <dd>
            <div> 
              <span>${(complaint.orderId)!""}</span> 
            </div>
          </dd>
        </dl>
        <dl>
          <dt>投诉类别：</dt>
          <dd>
            <div><span class="ml5 fl" id="uboxstyle">
            <#if complaint.content?exists && complaint.content != "">
	           <#list complaintCategoryList as category>
		        	<#if  complaint.complaintCategoryId?exists && complaint.complaintCategoryId=="${category.id?c}">
		        		<b>${(category.name)!""}</b>
		        	</#if>
		       	 </#list>
            <#else>
            	 <select name="complaintCategory.id"  id="language_mac">
		          <option value="">请选择</option>
		          <#list complaintCategoryList as category>
		        	<option value="${category.id?c}" <#if  complaint.complaintCategoryId?exists && complaint.complaintCategoryId=="${category.id?c}">selected</#if>>${(category.name)!""}</option>
		       	 </#list>
		        </select>
            </#if>
	     	</span>
		     </div>
          </dd>
        </dl>
        <dl id="dl100">
          <dt>投诉内容：</dt>
          <dd>
            <div> 
            <#if complaint.content?exists && complaint.content != "">
              <span>
              	${(complaint.content?html)!""}
              </span>
            <#else> 
              <span>
              	<textarea style="resize:none;" name="complaint.content" id="content" class="textArea500" >${(complaint.content?html)!""}</textarea>
              </span>
            </#if>
            </div>
          </dd>
        </dl>
   		<#if isAdd??>	
	   		<div class="ml150 fl">
	     		<a href="javascript:void(0);"><div id="saveComplaint" class="textBtn"><B>提交</B></div></a>
	   		</div>
   		<#else>
   			<div class="ml150 fl">
	         	<a href="${base}/complaint_list.action?"><div class="textBtn"><B>返回</B></div></a>  
	         </div>
   		</#if>    		
       </div>
     </form>
  </div>
</div>
<script type="text/javascript" src="${base}/template/common/js/height.js"></script>
<!--底部开始 -->
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束 --> 
<script type="text/javascript"> 	
$("#saveComplaint").click(function(){	
	var content = $.trim($("#content").val());
	var ordid = $("#language_mac").val();
	if(content == ''){
		$.layer({
			title:['分分通提示您',true],
			time:3,
			area : ['auto','auto'],
			dialog : {msg:'请填写投诉内容',type : 8}	
		});
		return ;
	}
	if(ordid == ''){
		$.layer({
			title:['分分通提示您',true],
			time:3,
			area : ['auto','auto'],
			dialog : {msg:'请选择投诉类别',type : 8}	
		});
		return ;
	}
	$("#complaintForm").submit();		
});
</script>
</body>
</html>
