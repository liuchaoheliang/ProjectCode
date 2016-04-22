<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商家管理-商家优惠</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/pager.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/select.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${base}/template/common/js/select.js"></script>
<script src="${base}/template/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/template/common/js/list.js"></script>
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

<div class="middleBox mt10">
<!-- 商家管理菜单开始 -->
<#include "/WEB-INF/templates/common/merchant_manage_menu.ftl">
<!-- 商家管理菜单结束 -->

<div class="rightBox" id="rightHeight">
  <form id="listForm" action="merchant_preferential_list.action" method="post">
  <input type="hidden" name="merchantPreferential.merchantId" id="merchantId2" value="${(pager.merchantId)!""}" />
<!--    <input type="hidden" name="pager.state" id="selectValue" value="${pager.state!""}" /> -->
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
         <a href="#" class="fl"><div id="addPreferential" class="adminBtn"><B>添加</B></div></a> 
    </div>
       


<div class="clear pt10">
      <table width="99%" border="0" cellspacing="0" cellpadding="0" class="tableA">
        <tr>
          <th width="10%" height="25"><B>图片</B></th>
          <th width="27%">简介</th>
          <th width="8%"><B>积分率</B></th>
          <th width="8%"><B>折扣率</B></th>
          <th width="8%">状态</th>
          <th width="13%"><B>创建时间</B></th>
          <th width="13%">更新时间</th>
          <th width="13%">操作</th>
        </tr>
       <#list pager.list as merchantPreferential>
       	<input type="hidden" id="state_${merchantPreferential.id?c}" value="${(merchantPreferential.state)!""}" />
         <tr id="preferential_tr_${merchantPreferential.id?c}">
         	
	        <td width="10%" height="25"><B><a href="merchant_preferential_info.action?id=${merchantPreferential.id?c}" target="_blank"><img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(merchantPreferential.photoUrl)!""}" onError="this.src='${base}/template/common/images/moren.gif'" width="960" height="720" /></a></B></td>
	        <td width="27%"><span>${(merchantPreferential.txt1?html)!"-"}</span></td>
            <td width="8%"><span>${(merchantPreferential.pointRate?html)!"-"}</span></td>
            <td width="8%"><span>${(merchantPreferential.preferentialRate)!"-"}</span></td>
	        <td width="8%"><span id="preferential_${merchantPreferential.id?c}" >	        	
	        <#if (merchantPreferential.state == '30')>
         		启用
         	<#elseif (merchantPreferential.state == '20')>
	        	审核中
         	<#elseif (merchantPreferential.state == '40')>
         		停用
         	<#elseif (merchantPreferential.state == '50')>
         		删除
	        </#if>
	        </span></td>
	        <td width="13%"><span>${(merchantPreferential.createTime?date("yyyy-MM-dd"))!"-"}</span></td>
            <td width="13%"><span>${(merchantPreferential.updateTime?date("yyyy-MM-dd"))!"-"}</span></td>	        
	        <td width="13%">
	        	<span id="preferential_ty_${merchantPreferential.id?c}" style="display:none"><a href="javascript:void(0);" onClick="lineDownPreferential(${merchantPreferential.id?c})"  title="停用">停用</a></span>   
	        	<span id="preferential_qy_${merchantPreferential.id?c}" style="display:none"><a href="javascript:void(0);" onClick="onLinePreferential(${merchantPreferential.id?c})"  title="启用">启用</a></span> 	       
	        <#if (merchantPreferential.state == '30')>
         		<span id="preferential_1_${merchantPreferential.id?c}">
         			<a href="javascript:void(0);" onClick="lineDownPreferential(${merchantPreferential.id?c})"  title="停用">
         				停用
         			</a>
         		</span>
         	<#elseif (merchantPreferential.state == '40')>
         		<span id="preferential_2_${merchantPreferential.id?c}">
         			<a href="javascript:void(0);" onClick="onLinePreferential(${merchantPreferential.id?c})"  title="启用">
         				启用
         			</a>
         		</span>
         	<#elseif (merchantPreferential.state == '50')>
         			<span id="preferential_3_${merchantPreferential.id?c}">
         				<a href="javascript:void(0);" onClick="onLinePreferential(${merchantPreferential.id?c})"  title="启用">
         				启用
         				</a>
         			</span>
	        </#if>	   
	         
	        <#if (merchantPreferential.state == '20')>
	        	<span><a href="javascript:void(0);" title="等待审核通过">等待审核通过</a></span>
	        <#else>
	        	<span id="preferential_sh_${merchantPreferential.id?c}" style="display:none"><a href="javascript:void(0);" title="等待审核通过">等待审核通过</a></span>
	        	 <#if (merchantPreferential.state != '50')>
	        	 	<span id="preferential_sc_${merchantPreferential.id?c}"><a href="javascript:void(0);" onClick="deletePreferential(${merchantPreferential.id?c})"  title="删除">删除</a></span>
	        	 </#if>
	        	<span id="preferential_xg_${merchantPreferential.id?c}"><a href="merchant_preferential_edit.action?menu_flag=message_manage&id=${merchantPreferential.id?c}"  title="修改">修改</a></span>
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
         	没有找到任何优惠信息!	
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
	$("#addPreferential").click(function(){
		window.parent.frames.location.href="merchant_preferential_add.action?";		
	});
	function deletePreferential(id){
		if(window.confirm("确认删除此优惠信息！")){
			if(id!='' && id != null && id != 'undefined'){
				window.parent.frames.location.href="deletePreferential.action?merchantPreferential.id="+id;							
			}
		}else{
			return;
		}
	}
function lineDownPreferential(id){
	$.getJSON("lineDownPreferentialJSON.action?merchantPreferential.id="+id,function(data){
		if(data.reno == "0"){
			if(data.stateValue != '' || data.stateValue != null){
				$('#preferential_'+id).html(data.stateValue);
				$('#preferential_ty_'+id).hide();
				$('#preferential_1_'+id).hide();
				$('#preferential_qy_'+id).show();
				$('#preferential_tr_'+id).hide();
			}
		}
	});
}
function onLinePreferential(id){
	var merchantId = $('#merchantId2').val();
	var state = $('#state_'+id).val();
	$.getJSON("onLinePreferentialJSON.action?merchantPreferential.id="+id+"&merchantPreferential.merchantId="+merchantId+"&merchantPreferential.state="+state,function(data){
		if(data.reno == "0"){
			if(data.stateValue != '' || data.stateValue != null){
				if(state=='50'){
					$('#state_'+id).val('20');
					$('#preferential_'+id).html(data.stateValue);
					$('#preferentialt_qy_'+id).hide();
					$('#preferential_2_'+id).hide();
					$('#preferential_3_'+id).hide();
					$('#preferential_sc_'+id).hide();
					$('#preferential_xg_'+id).hide();
					$('#preferential_sh_'+id).show();
				}else{
					$('#preferential_'+id).html(data.stateValue);
					$('#preferentialt_qy_'+id).hide();
					$('#preferential_2_'+id).hide();
					$('#preferential_3_'+id).hide();
					$('#preferential_ty_'+id).show();
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
