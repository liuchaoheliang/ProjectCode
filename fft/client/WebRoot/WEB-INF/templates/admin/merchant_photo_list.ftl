<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商家管理-商家相册</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/pager.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/select.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>
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

<div class="middleBox mt10">
<!-- 商家管理菜单开始 -->
<#include "/WEB-INF/templates/common/merchant_manage_menu.ftl">
<!-- 商家管理菜单结束 -->

<div class="rightBox" id="rightHeight">
<form id="listForm" action="merchant_photo_list.action" method="post">
  <input type="hidden" name="merchantPhoto.merchantId" id="merchantId2" value="${pager.merchantId!""}" />
 <!-- <input type="hidden" name="pager.state" id="selectValue" value="${pager.state!""}" /> --> 
    <div class="fromDiv">
         <span><b>开始时间：</b><input name="pager.beginTime" id="txtStartDate" type="text"   onClick="WdatePicker({maxDate:'#F{$dp.$D(\'txtEndDate\',{d:-1});}'})" class="loginTextDate" value="${(pager.beginTime?split('|')[0])!""}" /></span>
		<span><b>结束时间：</b><input name="pager.endTime" id="txtEndDate" type="text"  onClick="WdatePicker({minDate:'#F{$dp.$D(\'txtStartDate\',{d:0});}'})" class="loginTextDate" value="${(pager.endTime?split('|')[0])!""}" /></span>
     	 <span class="ml5" id="uboxstyle">
			 <select name="pager.state" id="language_mac">
	       		<!--<option value="">请选择</option>-->
	        	<option value="20" <#if pager.state?exists && pager.state != '' && pager.state=="20">selected</#if>>审核中</option>
	        	<option value="30" <#if pager.state?exists && pager.state != '' && pager.state=="30">selected</#if>>启用</option>
		      	<option value="40" <#if pager.state?exists && pager.state != '' && pager.state=="40">selected</#if>>停用</option>
		      	<option value="50" <#if pager.state?exists && pager.state != '' && pager.state=="50">selected</#if>>删除</option>
			 </select>
		</span>	
	
         <a href="#" class="fl"><div id="searchButton" class="adminBtn"><B>查询</B></div></a>
         <a href="#" class="fl"><div id="addPhoto" class="adminBtn"><B>添加</B></div></a> 
    </div>
       


<div class="clear pt10">
      <table width="99%" border="0" cellspacing="0" cellpadding="0" class="tableA">
        <tr>
          <th width="11%">图片</th>
          <th width="37%">简介</th>
          <th width="14%">创建时间</th>
          <th width="13%">更新时间</th>
          <th width="11%">状态</th>
          <th width="14%">操作</th>
        </tr>
       <#list pager.list as merchantPhoto>
       	<input type="hidden" id="state_${merchantPhoto.id?c}" value="${(merchantPhoto.state)!""}" />
         <tr id="photo_tr_${merchantPhoto.id?c}">
	        <td width="11%" height="25"><B><img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(merchantPhoto.photoUrl)!""}" onError="this.src='${base}/template/common/images/moren.gif'" width="960" height="720" /></B></td>
	        <td width="37%"><span><#if merchantPhoto?exists>${(merchantPhoto.txt1?html)!"-"}<#else>-</#if></span></td>
	        <td width="14%"><span><#if merchantPhoto?exists>${(merchantPhoto.createTime?date("yyyy-MM-dd"))!"-"}<#else>-</#if></span></td>
	        <td width="13%"><span><#if merchantPhoto?exists>${(merchantPhoto.updateTime?date("yyyy-MM-dd"))!"-"}<#else>-</#if></span></td>
	        <td width="11%"><span id="photo_${merchantPhoto.id?c}">
	        <#if (merchantPhoto.state == '30')>
         		启用
         	<#elseif (merchantPhoto.state == '20')>
	        	审核中
         	<#elseif (merchantPhoto.state == '40')>
         		停用
         	<#elseif (merchantPhoto.state == '50')>
         		删除
	        </#if>
	        </span></td>	        
	        <td width="14%">
	         <span id="photo_qy_${merchantPhoto.id?c}" style="display:none"><a href="javascript:void(0);" onClick="onLinePhoto(${merchantPhoto.id?c})"  title="启用">启用</a></span> 	        
	        
	         <span id="photo_ty_${merchantPhoto.id?c}" style="display:none"><a href="javascript:void(0);" onClick="lineDownPhoto(${merchantPhoto.id?c})"  title="停用">停用</a></span>   
	         <#if (merchantPhoto.state == '30')>
         		<span id="photo_1_${merchantPhoto.id?c}"><a href="javascript:void(0);" onClick="lineDownPhoto(${merchantPhoto.id?c})"  title="停用">停用</a></span>
         	<#elseif (merchantPhoto.state == '40')>
         		<span id="photo_2_${merchantPhoto.id?c}"><a href="javascript:void(0);" onClick="onLinePhoto(${merchantPhoto.id?c})"  title="启用">启用</a></span>
         	<#elseif (merchantPhoto.state == '50')>
         		<span id="photo_3_${merchantPhoto.id?c}"><a href="javascript:void(0);" onClick="onLinePhoto(${merchantPhoto.id?c})"  title="启用">启用</a></span>
	        </#if>	   
	        
	        <#if (merchantPhoto.state == '20')>
	        	<span><a href="javascript:void(0);" title="等待审核通过">等待审核通过</a></span>
	        <#else>
	        	<span id="photo_sh_${merchantPhoto.id?c}" style="display:none"><a href="javascript:void(0);" title="等待审核通过">等待审核通过</a></span>
	        	 <#if (merchantPhoto.state != '50')>
	        	 	<span id="photo_sc_${merchantPhoto.id?c}"><a href="javascript:void(0);" onClick="deletePhoto(${merchantPhoto.id?c})"  title="删除">删除</a></span>
	        	</#if>	
	        	<span id="photo_xg_${merchantPhoto.id?c}"><a href="merchant_photo_edit.action?id=${merchantPhoto.id?c}"  title="修改">修改</a></span>
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
         		没有找到任何图片!	
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
$("#addPhoto").click(function(){
	window.parent.frames.location.href="merchant_photo_add.action?";			
});
function deletePhoto(id){
	if(window.confirm("确认删除此图片！")){
		if(id!='' && id != null && id != 'undefined'){
			window.parent.frames.location.href="deletePhoto.action?merchantPhoto.id="+id;							
		}else{
			return;
		}						
	}else{
		return;
	}
}
function lineDownPhoto(id){
	$.getJSON("lineDownPhotoJSON.action?merchantPhoto.id="+id,function(data){
		if(data.reno == "0"){
			if(data.stateValue != '' || data.stateValue != null){
				$('#photo_'+id).html(data.stateValue);
				$('#photo_ty_'+id).hide();
				$('#photo_1_'+id).hide();
				$('#photo_qy_'+id).show();
				$('#photo_tr_'+id).hide();
			}
		}
	});
}
function onLinePhoto(id){
	var merchantId = $('#merchantId2').val();
	var state = $('#state_'+id).val();
	$.getJSON("onLinePhotoJSON.action?merchantPhoto.id="+id+"&merchantPhoto.merchantId="+merchantId+"&merchantPhoto.state="+state,function(data){
		if(data.reno == "0"){
			if(data.stateValue != '' || data.stateValue != null){
				if(state=='50'){
					$('#state_'+id).val('20');
					$('#photo_'+id).html(data.stateValue);
					$('#photo_qy_'+id).hide();
					$('#photo_2_'+id).hide();
					$('#photo_3_'+id).hide();
					$('#photo_sc_'+id).hide();
					$('#photo_xg_'+id).hide();
					$('#photo_sh_'+id).show();
				}else{
					$('#photo_'+id).html(data.stateValue);
					$('#photo_qy_'+id).hide();
					$('#photo_2_'+id).hide();
					$('#photo_3_'+id).hide();
					$('#photo_ty_'+id).show();
				}
			}
		}
		if(data.reno == "2"){
			$.layer({
					title:['分分通提示您',true],
					time:3,
					area : ['auto','auto'],
					dialog : {msg:data.msg,type : 9}	
			});
		}
	});
}
</script>    
</body>
</html>
