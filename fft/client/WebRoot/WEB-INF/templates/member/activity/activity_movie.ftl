<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通查询-我的活动</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/ajax_detail.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/pager.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/select.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${base}/template/common/js/select.js"></script>
<script type="text/javascript" src="${base}/template/common/js/list.js"></script>
<script src="${base}/template/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/template/common/js/ajax_detail.js"></script>
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

<!--广告栏开始-->
<div class="ad"><img src="${base}/template/member/img/123213.png"></div>
<!--广告栏结束-->

<div class="middleBox">
<!-- 会员个人信息菜单开始 -->
<#include "/WEB-INF/templates/common/user_manage_menu.ftl">
<!-- 会员个人信息菜单结束 -->

<div class="rightBox" id="rightHeight">   
<form id="listForm" action="member_activity_list.action" method="post">
<div class="clear pt10">
      <table width="99%" border="0" cellspacing="0" cellpadding="0" class="tableA">
        <tr>
          <th width="20%">活动名称</th>
          	<th width="25%">商户名称</th>
          	<th width="12%">活动起始日期</th>
          	<th width="12%">活动截止日期</th>
          	<th width="15%">兑换码</th>
          	<th width="10%">状态</th>
          	<th width="6%">操作</th>
        </tr>
  		 <#list pager.list as list>
  		 	<tr>
  		 		<td>${(list.activityCustInfo.name?html)!""}</td>
  		 		<td>${(list.merchant.mstoreFullName?html)!""}</td>
  		 		<td>${(list.activityCustInfo.activityStartTime?html)!""}</td>
  		 		<td>${(list.activityCustInfo.activityEndTime?html)!""}</td>
  		 		<td>${(list.securitiesNo?html)!""}</td>
  		 		<#if list.isConsume?exists && list.isConsume=="1">
  		 			<td>已兑换</td>
  		 			<td>--</td>
  		 		<#else>
  		 			<td>未兑换</td>
  		 			<td id="duanxin"><a href="#" onclick="reSendMsg(${(list.id)?c})" ><img src="${base}/template/member/images/duanxin.png" title="重发验证券短信"></a></td>
  		 		</#if>

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
	</from>
  </div>
</div>  
<script type="text/javascript" src="${base}/template/common/js/height.js"></script>
<!--底部开始-->
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->   
<script type="text/javascript"> 
	function reSendMsg(obj){
		$.getJSON("sendActivityMessage.action?lotteryResultInfoId="+obj,function(data){
			if(data.reno == "0"){
				$.layer({
					title:['分分通提示您',true],
					time:3,
					area : ['auto','auto'],
					dialog : {msg:data.message,type : 9}	
				});	
			}else{
				$.layer({
					title:['分分通提示您',true],
					time:3,
					area : ['auto','auto'],
					dialog : {msg:data.message,type : 8}	
				});
			}
		});
	}
</script>
</body>
</html>
