<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>安全中心-我的财务员</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
<script src="${base}/template/common/My97DatePicker/WdatePicker.js"></script>
<link href="${base}/template/common/css/pager.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/list.js"></script>
<link href="${base}/template/common/css/select.css" rel="stylesheet" type="text/css" />
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
<form id="listForm" action="merchant_treasurer_list.action" method="post">
  <input type="hidden" name="pager.state" id="selectValue" value="${(pager.state)!""}" />
    <div class="fromDiv">
         <span><b>开始时间：</b><input name="pager.beginTime" id="txtStartDate" type="text"   onClick="WdatePicker({maxDate:'#F{$dp.$D(\'txtEndDate\',{d:-1});}'})" class="loginTextDate" value="${(pager.beginTime?split('|')[0])!""}" /></span>
		<span><b>结束时间：</b><input name="pager.endTime" id="txtEndDate" type="text"  onClick="WdatePicker({minDate:'#F{$dp.$D(\'txtStartDate\',{d:0});}'})" class="loginTextDate" value="${(pager.endTime?split('|')[0])!""}" /></span>
         <span><b>工号：</b><input name="pager.beCode" type="text" class="loginText3" value=""/></span>
 <!--    <span class="ml10 mt2">
          <select name="pager.state">
       		<option value="">请选择</option>
        	<option value="20">审核中</option>
        	<option value="30">启用</option>
	      	<option value="40">停用</option>
	      	<option value="50">删除</option>
		</select>
		</span>	-->	
         <a href="#" class="fl"><div id="searchButton" class="adminBtn"><B>查询</B></div></a>
         <a href="#" class="fl"><div id="addClerk" class="adminBtn"><B>添加</B></div></a>
    </div>
       


<div class="clear pt10">
      <table width="99%" border="0" cellspacing="0" cellpadding="0" class="tableA">
        <tr>
          <th width="11%">操作员工号</th>
          <th width="11%">操作员姓名</th>
          <th width="20%">所属门店</th>
          <th width="15%">创建时间</th>
          <th width="15%">更新时间</th>
          <th width="10%">状态</th>
          <th width="18%">操作</th>
        </tr>
       <#list pager.list as clerk>
       	<input type="hidden" id="state_${clerk.id?c}" value="${(clerk.state)!""}" />
         <tr id="clerk_tr_${clerk.id?c}">
		    <td ><span><a href="treasurer_info.action?merchantUserSet.beCode=${(clerk.beCode)!""}" title="查看">${(clerk.beCode)!""}</a></span></td>
	        <td width="15%"><span>
	        	<#if (clerk.beName?exists && clerk.beName?length <= 8)!>
	          		${(clerk.beName)!""}
	         	<#elseif clerk.beName?exists>
	         		${clerk.beName[0..8]}...
	         	<#else>
	         		-
		        </#if>
	        </span></td>
	        <td ><span>
	        	<!--<#if (clerk.beSpec?exists && clerk.beSpec?length <= 8)!>
	          		${(clerk.beSpec)!""}
	         	<#elseif clerk.beSpec?exists>
	         		${clerk.beSpec[0..8]}...
	         	<#else>
	         		-
		        </#if>-->
		        <#if (clerk.beCode == '1000')>
		        	-
		        <#elseif (clerk.belongStoreId?exists && clerk.belongStoreId =="unabsorbed")>
		        	<span style="color:red;">未分配</span>
		        <#else>
		        	 ${clerk.belongStoreId}
		        </#if>
	        </span></td>
	        <td ><span>${(clerk.createTime?date("yyyy-MM-dd"))!"-"}</span></td>
	        <td ><span>${(clerk.updateTime?date("yyyy-MM-dd"))!"-"}</span></td>
	        <td ><span id="clerk_${clerk.id?c}">
	        <#if clerk?exists && clerk.state == '30'>
         		启用
         	<#elseif (clerk.state == '50')>
         		停用
	        </#if>
	        </span></td>	        
	        <td width="14%">
	         <#if clerk?exists && (clerk.state == '30')>
	         	<#if (clerk.beCode == '1000')>
	         		
	         	<#else>
	         		<span id="clerk_1_${clerk.id?c}"><a href="javascript:void(0);" onClick="lineDownClerk(${(clerk.beCode)!""})"  title="停用">停用</a></span>
		        </#if>
         	<#elseif (clerk.state == '50')>
         		<span id="clerk_3_${clerk.id?c}"><a href="javascript:void(0);" onClick="onLineClerk(${(clerk.beCode)!""})"  title="启用">启用</a></span>
	        </#if>	  
	        	 &nbsp;&nbsp;&nbsp;&nbsp;<span ><a href="javascript:void(0)" onClick="updateClerk(${(clerk.beCode)!""})"  title="修改">修改</a></span>	        
	        </td>
      	</tr>
       </#list>
      </table>
    </div>  
    <div class="page">
    	<#if (pager.list?size > 0)>
         	<#include "/WEB-INF/templates/common/pager.ftl" />
       	<#else>
         		没有找到财务员!	
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
$("#addClerk").click(function(){
	window.parent.frames.location.href="to_add_treasurer.action";			
});
function lineDownClerk(id){
	if(window.confirm("确认停用此财务员！")){
		if(id!='' && id != null && id != 'undefined'){
			window.parent.frames.location.href="delete_treasurer.action?merchantUserSet.beCode="+id;							
		}else{
			return;
		}						
	}else{
		return;
	}
}

function onLineClerk(id){
	if(window.confirm("确认启用此财务员！")){
		if(id!='' && id != null && id != 'undefined'){
			window.parent.frames.location.href="online_treasurer.action?merchantUserSet.beCode="+id;							
		}else{
			return;
		}						
	}else{
		return;
	}
}

function updateClerk(id){
	if(id!='' && id != null && id != 'undefined'){
			window.parent.frames.location.href="to_update_treasurer.action?merchantUserSet.beCode="+id;							
		}else{
			return;
		}	
}

</script>    
</body>
</html>
