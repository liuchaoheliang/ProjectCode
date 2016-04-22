<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>我的抽奖</title>
<link rel="icon" href="${base}/favicon.ico" type="image/x-icon" />
<#include "/include/sources.ftl">
<link href="${base}/resources/member/css/admin.css" rel="stylesheet" type="text/css" />
<script src="${base}/resources/common/plugin/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/resources/common/js/luck.js"></script>
<script type="text/javascript">
	$(document).ready(function (){
	
	});

	//领奖
	function reward(lotteryWinnerDtoId){
		if(lotteryWinnerDtoId != null &&　lotteryWinnerDtoId != ""){
			//调用派奖接口
			aldoParisotPrize(lotteryWinnerDtoId);	
		}
	}
	
</script>
</head>
<body>
<#-- 头部 开始-->
<#include "/include/common/header.ftl">
<#-- 头部 结束-->
<div class="main">
	<div class="middleBox"> 
	<#-- 公共菜单  开始 -->
	<#include "/include/member/menu.ftl">
	<#-- 公共菜单  结束 -->
    <div class="rightBox" id="rightHeight">
    	<form id="listForm" action="${base}/member/activity/list.jhtml" method="post">
	      	<div class="fromDiv"> 
	      	</div>
	      	<div class="clear pt15">
	        	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="tableA">
		        <tr>
		          	<th width="15%">活动名称</th>
		        	<th width="10%">中奖等级</th>
		        	<th width="15%">奖品名称</th>
		          	<th width="15%">活动开始时间</th>
		          	<th width="15%">活动截止日期</th>
		          	<#--<th width="15%">券号</th>-->
		          	<th width="10%">状态</th>
		          	<th width="6%">操作</th>
		        </tr>
			<#list page.resultsContent as list>
	  		 	<tr>
	  		 		<td>${(list.lotteryName?html)!""}</td>
	  		 		<td>${(list.lotteryTerm?html)!""}</td>
	  		 		<td>${(list.awardInfo?html)!""}</td>
	  		 		<td>${(list.startTime?html)!""}</td>
	  		 		<td>${(list.endTime?html)!""}</td>
	  		 		<#--<td>${(list.lotteryWinnerDto.virtualCard.redemptionCodeDecrypt?html)!"-"}</td>-->
		  		 		<#if list.state?exists && list.state=="award">
		  		 			<td>已领奖</td>
		  		 			<td>-</td>		
		  		 		<#else>
		  		 			<td>未领奖</td> 		 			
		  		 			<td> 
		  		 				<a href="#" onclick="reward('${list.lotteryWinnerDtoId}')">领奖</a>
		  		 			</td>
		  		 		</#if>
		  		 	</tr>
		        </#list> 
	       	   </table>
	      </div>
		  <div class="page">
		       <#if (page.resultsContent?size > 0)>
		         	<#include "/include/pager.ftl" />
		       <#else>
		         	暂无抽奖记录!	
		        </#if>
       	   </div>
    </form>
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