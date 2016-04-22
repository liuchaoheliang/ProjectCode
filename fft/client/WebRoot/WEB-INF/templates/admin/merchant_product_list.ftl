<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商家管理-商家产品</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css"/>
<link href="${base}/template/common/css/pager.css" rel="stylesheet" type="text/css"/>
<link href="${base}/template/common/css/select.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${base}/template/common/js/select.js"></script>
<script type="text/javascript" src="${base}/template/common/js/list.js"></script>
<script src="${base}/template/common/My97DatePicker/WdatePicker.js"></script>

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
<form id="listForm" action="merchant_product_list.action" method="post">
   <input type="hidden" name="merchantProduct.merchantId" id="merchantId2" value="${(pager.merchantId)!""}" />
 <!--  <input type="hidden" name="pager.state" id="selectValue" value="${pager.state!""}" /> --> 
    <div class="fromDiv">
         <span><b>开始时间：</b><input name="pager.beginTime" id="txtStartDate" type="text"   onClick="WdatePicker({maxDate:'#F{$dp.$D(\'txtEndDate\',{d:-1});}'})" class="loginTextDate" value="${(pager.beginTime?split('|')[0])!""}" /></span>
		<span><b>结束时间：</b><input name="pager.endTime" id="txtEndDate" type="text"  onClick="WdatePicker({minDate:'#F{$dp.$D(\'txtStartDate\',{d:0});}'})" class="loginTextDate" value="${(pager.endTime?split('|')[0])!""}" /></span>
         <span class="ml5" id="uboxstyle">
			<select name="pager.state" class="adminSelect" id="language_mac">
	        	<!--<option value="">请选择</option>-->
	        	<option value="20" <#if pager.state?exists && pager.state != '' && pager.state=="20">selected</#if>>审核中</option>
	        	<option value="30" <#if pager.state?exists && pager.state != '' && pager.state=="30">selected</#if>>启用</option>
		      	<option value="40" <#if pager.state?exists && pager.state != '' && pager.state=="40">selected</#if>>停用</option>
		      	<option value="50" <#if pager.state?exists && pager.state != '' && pager.state=="50">selected</#if>>删除</option>
			</select>
         </span> 

         <a href="#" class="fl"><div id="searchButton" class="adminBtn"><B>查询</B></div></a>
         <a href="#" class="fl"><div id="addProduct" class="adminBtn"><B>添加</B></div></a>
    </div>
    
<div class="clear pt10">
      <table width="99%" border="0" cellspacing="0" cellpadding="0" class="tableA">
        <tr>
          <th width="11%">图片</th>
          <th width="30%">简介</th>
          <th width="10%">价格</th>
          <th width="9%">积分率</th>
          <th width="7%">折扣率</th>
          <th width="10%">状态</th>
          <th width="10%">更新时间</th>
          <th width="13%">操作</th>
        </tr>
       <#list pager.list as merchantProduct>
       		<input type="hidden" id="state_${merchantProduct.id?c}" value="${(merchantProduct.state?html)!""}" />
         <tr id="product_tr_${merchantProduct.id?c}">
	        <td width="11%" height="25"><B><a href="merchant_product_info.action?id=${merchantProduct.id?c}" target="_blank"><img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${merchantProduct.photoUrl!''}" onError="this.src='${base}/template/common/images/moren.gif'" width="960" height="720" /></a></B></td>
	        <td width="30%"><span>${(merchantProduct.txt1?html)!"-"}</span></td>
	        <td width="10%"><span>${(merchantProduct.price?html)!"-"}</span></td>
            <td width="9%"><span>${(merchantProduct.pointRate?html)!"-"}</span></td>
            <td width="7%"><span>${(merchantProduct.preferentialRate?html)!"-"}</span></td>
	        <td width="10%"><span id="product_${merchantProduct.id?c}" >
	        <#if (merchantProduct.state == '30')>
	        	启用
	        <#elseif (merchantProduct.state == '20')>
	        	审核中
	        <#elseif (merchantProduct.state == '40')>
	        	停用
	        <#elseif (merchantProduct.state == '50')>
	        	删除
	         </#if>	
	        </span></td>
            <td width="10%"><span>${(merchantProduct.updateTime?date("yyyy-MM-dd"))!"-"}</span></td>	        
	        <td width="13%">
	        <span id="product_ty_${merchantProduct.id?c}" style="display:none"><a href="javascript:void(0);" onClick="lineDownProduct(${merchantProduct.id?c})"  title="停用">停用</a></span>   
	         <span id="product_qy_${merchantProduct.id?c}" style="display:none"><a href="javascript:void(0);" onClick="onLineProduct(${merchantProduct.id?c})"  title="启用">启用</a></span> 
	        <#if (merchantProduct.state == '30')>
         		<span id="product_1_${merchantProduct.id?c}"><a href="javascript:void(0);" onClick="lineDownProduct(${merchantProduct.id?c})"  title="停用">停用</a></span>
         	<#elseif (merchantProduct.state == '40')>
         		<span id="product_2_${merchantProduct.id?c}"><a href="javascript:void(0);" onClick="onLineProduct(${merchantProduct.id?c})"  title="启用">启用</a></span>
         	<#elseif (merchantProduct.state == '50')>
         		<span id="product_3_${merchantProduct.id?c}"><a href="javascript:void(0);" onClick="onLineProduct(${merchantProduct.id?c})"  title="启用">启用</a></span>
	        </#if>	   
	         
	         <#if (merchantProduct.state == '20')>
	        	<span><a href="javascript:void(0);" title="等待审核通过">等待审核通过</a></span>
	        <#else>
	        	<span id="product_sh_${merchantProduct.id?c}" style="display:none"><a href="javascript:void(0);" title="等待审核通过">等待审核通过</a></span>
	        	<#if (merchantProduct.state != '50')>
	        		<span id="product_sc_${merchantProduct.id?c}"><a href="javascript:void(0);" onClick="deleteProduct(${merchantProduct.id?c})"  title="删除">删除</a></span>
	        	</#if>
	        	<span id="product_xg_${merchantProduct.id?c}"><a href="merchant_product_edit.action?id=${merchantProduct.id?c}"  title="修改">修改</a></span>
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
         		没有找到任何产品!	
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
	$("#addProduct").click(function(){
		window.parent.frames.location.href="merchant_product_add.action?";		
	});
	function deleteProduct(id){
		if(window.confirm("确认删除此商品！")){
			if(id!='' && id != null && id != 'undefined'){
				window.parent.frames.location.href="deleteProduct.action?merchantProduct.id="+id;							
			}
		}else{
			return;
		}
	}
function lineDownProduct(id){
	$.getJSON("lineDownProductJSON.action?merchantProduct.id="+id,function(data){
		if(data.reno == "0"){
			if(data.stateValue != '' || data.stateValue != null){
				$('#product_'+id).html(data.stateValue);
				$('#product_ty_'+id).hide();
				$('#product_1_'+id).hide();
				$('#product_qy_'+id).show();
				$('#product_tr_'+id).hide();
			}
		}
	});
}
function onLineProduct(id){
	var merchantId = $('#merchantId2').val();
	var state = $('#state_'+id).val();
	$.getJSON("onLineProductJSON.action?merchantProduct.id="+id+"&merchantProduct.merchantId="+merchantId+"&merchantProduct.state="+state,function(data){
		if(data.reno == "0"){
			if(data.stateValue != '' || data.stateValue != null){
				if(state=='50'){
					$('#state_'+id).val('20');
					$('#product_'+id).html(data.stateValue);
					$('#product_qy_'+id).hide();
					$('#product_2_'+id).hide();
					$('#product_3_'+id).hide();
					$('#product_sc_'+id).hide();
					$('#product_xg_'+id).hide();
					$('#product_sh_'+id).show();
				}else{
					$('#product_'+id).html(data.stateValue);
					$('#product_qy_'+id).hide();
					$('#product_2_'+id).hide();
					$('#product_3_'+id).hide();
					$('#product_ty_'+id).show();					
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
