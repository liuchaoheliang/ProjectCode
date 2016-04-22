<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>意见与建议</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/pager.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/select.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${base}/template/common/js/select.js"></script>
<script type="text/javascript" src="${base}/template/common/js/list.js"></script>
<script src="${base}/template/common/My97DatePicker/WdatePicker.js"></script>
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

<div class="rightBox pt5" id="rightHeight">
<form id="listForm" action="suggestion_list.action?" method="post">
<input type="hidden" name="suggestion.userId" id="userId" value="${(user.userID)!""}"/>
    <div class="fromDiv">
         <span><b>开始时间：</b><input name="pager.beginTime" id="txtStartDate" type="text"   onClick="WdatePicker({maxDate:'#F{$dp.$D(\'txtEndDate\',{d:-1});}'})" class="loginTextDate" value="${(pager.beginTime?split('|')[0])!""}" /></span>
		<span><b>结束时间：</b><input name="pager.endTime" id="txtEndDate" type="text"  onClick="WdatePicker({minDate:'#F{$dp.$D(\'txtStartDate\',{d:0});}'})" class="loginTextDate" value="${(pager.endTime?split('|')[0])!""}" /></span>
         <span class="ml5" id="uboxstyle">
            <select name="pager.isReply" class="default" id="language_mac">
              	<option value="">请选择</option>
        		<option value="0" <#if pager.isReply?exists && pager.isReply != '' && pager.isReply=="0">selected</#if>>未回复</option>
        		<option value="1" <#if pager.isReply?exists && pager.isReply != '' && pager.isReply=='1'>selected</#if>>已回复</option>
            </select>
          </span>
         <a href="#" class="fl"><div id="searchButton" class="adminBtn"><B>查询</B></div></a>
         <a href="#" class="fl"><div id="addSuggest" class="adminBtn"><B>添加</B></div></a>
    </div>
    
<div class="clear pt10">
      <table width="99%" border="0" cellspacing="0" cellpadding="0" class="tableA">
        <tr>
          <th width="19%">发表时间</th>
          <th width="51%">意见与建议</th>
          <th width="10%">状态</th>
          <th width="20%">操作</th>
        </tr>
        <#list pager.list as suggestion>
        <tr>
          <td>${(suggestion.createTime?date("yyyy-MM-dd"))!"-"}</td>
          
          	<#if (suggestion.content?length <= 15)!>
         		<td>${(suggestion.content?html)!""}</td>
         	<#else>
         		<td>${(suggestion.content[0..15]?html)!""}...</td>
	        </#if>
          
          <td>
          	<#if (suggestion.isReply == '0')>
         		未回复
         	<#elseif (suggestion.isReply == '1')>
         		已回复
	        </#if>
          </td>
          <td>
          	<#if (suggestion.isReply == '0')>
         		<a href="get_suggestion_info.action?suggestion.id=${suggestion.id?c}&suggestion.userId=${(user.userID)!""}">查看</a>
         	<#elseif (suggestion.isReply == '1')>
         <!-- 		<a href="get_suggestion_info.action?suggestion.id=${suggestion.id?c}&suggestion.userId=${user.userID!""}">查看</a> -->
         		<a href="get_suggestionReply_info.action?suggestion.id=${suggestion.id?c}&suggestion.userId=${(user.userID)!""}">查看回复</a>
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
         		暂时没有意见和建议记录!	
        </#if>
    </div>
    </form>
  </div>
</div>  
<script type="text/javascript" src="${base}/template/common/js/height.js"></script>
<!--底部开始-->
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束--> 
<script type="text/javascript"> 
$("#addSuggest").click(function(){
	//window.parent.frames.location.href="toSuggestion.action?";	
	window.location.href="toSuggestion.action";		
});
</script>          
</body>
</html>
