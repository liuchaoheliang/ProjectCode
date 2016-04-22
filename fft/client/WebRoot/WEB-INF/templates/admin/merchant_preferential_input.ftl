<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商家管理-商家优惠</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/typefile.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/select.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>
<script type="text/javascript" src="${base}/template/common/js/select.js"></script>
<#if !id??>
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

<div class="middleBox mt10">
<!-- 商家管理菜单开始 -->
<#include "/WEB-INF/templates/common/merchant_manage_menu.ftl">
<!-- 商家管理菜单结束 -->
  <div class="rightBox" id="rightHeight">
    <div class="clear pt5">
    <form id="inputForm"  action="<#if isAdd??>merchant_preferential_save.action?<#else>merchant_preferential_update.action?</#if>" enctype="multipart/form-data" method="post">
	<input type="hidden" name="merchantPreferential.id" id="id" value="${id!""}" />
	<input type="hidden" name="merchantId" id="merchantId" value="${merchantId!""}" />
	<input type="hidden" name="merchantPreferential.merchantId" id="merchantId2" value="${merchantId!""}" />
	<input type="hidden" name="picType" id="picType" value="preferential" />
	<input type="hidden" name="message" id="message" value="${message!""}"/>
      <table width="99%" border="0" cellspacing="0" cellpadding="0" class="tableC">
        <tr>
          <th width="17%">简介</th>
          <td width="83%">
		    <input type="text" id="txt1" name="merchantPreferential.txt1" class="loginText" maxlength="32" value="${(merchantPreferential.txt1)!""}" />
		    <span>限制：不能超过32个字符</span>
          </td>
        </tr>
        <tr>
          <th>详细介绍</th>
          <td>
          <textarea id="textarea_txt2" maxlength="500" name="merchantPreferential.txt2" style="resize:none;" class="textArea500"><#if merchantPreferential?exists>${(merchantPreferential.txt2)!""}</#if></textarea>
		    <span>限制：不能超过500个字符</span>
          </td>
        </tr>
         <#if preferentialType?exists && preferentialType == '1'>
       <tr>
		    <th>直接优惠</td>
		    <td>
		    <!--<span class="ml5" id="uboxstyle">
	          	<select name="merchantPreferential.preferentialRate" id="language_mac">
			    	  <option value="${(merchantPreferential.preferentialRate)!""}"><#if merchantPreferential?exists>${(merchantPreferential.preferentialRate)!""}</#if></option>
			    	  
				</select>				
		    </span>-->
		    <#if merchantPreferential?exists>${(merchantPreferential.preferentialRate)!""}</#if>
			   	<input type="text" value="${(merchantPreferential.preferentialRate)!""}" name="merchantPreferential.preferentialRate" style="display:none;" />
		    </td>
		  </tr>
		    <#elseif preferentialType?exists &&  preferentialType== '2'>
		  <tr>
		    <th>返利积分</td>
		    <td>
		    <!--<span class="ml5" id="uboxstyle">
	          	<select name="merchantPreferential.pointRate" id="language_mac">
			      	<option value="${(merchantPreferential.pointRate)!""}">${(merchantPreferential.pointRate)!""}</option>
				</select>				
		    </span> -->
		    ${(merchantPreferential.pointRate)!""}%
				<input type="text" value="${(merchantPreferential.pointRate)!""}" name="merchantPreferential.pointRate" style="display:none;" />
		    
		    </td>
		  </tr>
		  </#if>
		  <tr>
          	<th>优先级</th>
          	<td>
	         <input type="text" id="productArray" name="merchantPreferential.array" class="loginText4" maxlength="3"  value="${(merchantPreferential.array)!"0"}" />
	          <span>限制：数字 0-100；说明：数字越大，等级越高</span>
	          </td>
	      </tr>
		  
		  <tr>
          <th>图片</th>
          <td id="file"><a class="btn_addPic" href="javascript:void(0);"><span><em>+</em>添加图片</span>
            <input class="filePrew" title="支持jpg格式，文件小于1M" tabIndex=3 type=file size=3 id="imgFilePre" name="imgFile">
            </a> <span>(图片大小不能超过1M)图片格式为*.jpg</span>
           </td>
        </tr>
        <tr>
          <th>当前图片</th>
          <td>
          <div id="logoUrlDiv" ><img id="logoUrl" src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}<#if merchantPreferential?exists>${(merchantPreferential.photoUrl)!""}</#if>" onError="this.src='${base}/template/common/images/moren.gif'"/></div>
          </td>
        </tr>
      </table>
           
        <div class="ml145 mt10"> 
          	<a href="javascript:void(0);"><div id="savePreferential" class="textBtn"><B id="bctj"><#if isAdd??>保存<#else>保存更新</#if></B></div></a>     	
        </div>  
        <div id="returnPreferentialList" class="fhuixiugai"><a href="#">返回优惠列表</a></div>      
      </from>
    </div>
  </div>
</div>
<script type="text/javascript" src="${base}/template/common/js/height.js"></script>
<!--底部开始-->
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
<script type="text/javascript"> 
$(window).load(function(){
	var message = $('#message').val();
	if(message != ''){
		$.layer({
				title:['分分通提示您',true],
				time:3,
					area : ['auto','auto'],
					dialog : {msg:message,type : 9}	
				});
	}
	//var liulanUrl = $('#logoUrl').attr("src");
	//if(liulanUrl == '/communityBusiness_client' || liulanUrl ==null || liulanUrl == 'undefined'){
		
	//}else{
	//	$('#bctj').html("保存更新");
	//	$('#logoUrlDiv').show();
	//}
});
$("#returnPreferentialList").click(function(){
	window.history.back();return false;			
});
	
	//修改或添加优惠
	$("#savePreferential").click(function(){
		var id=$('#id').val();
		var merchantId=$('#merchantId').val();
		if(merchantId != '' && merchantId != null && merchantId != 'undefined' ){
			$('#merchantId2').val(merchantId);
		}
		if(id == '' || id=='undefined'){
			//var productImgUrl=$('#productImgUrl').val();
			var imgFilePre = $.trim($("#imgFilePre").val());
			var txt1 = $('#txt1').val();
			var txt2 = $('#textarea_txt2').val();
			
			if(txt1==''){
				$.layer({
					title:['分分通提示您',true],
						time:3,
					area : ['auto','auto'],
					dialog : {msg:'请填写介绍信息',type : 8}	
				});
				return;
			}
			if(txt2==''){
				$.layer({
				title:['分分通提示您',true],
				time:3,
					area : ['auto','auto'],
					dialog : {msg:'请填写详细信息',type : 8}	
				});
				return;
			} 
			if(imgFilePre == ''){
				$.layer({
				title:['分分通提示您',true],
				time:3,
					area : ['auto','auto'],
					dialog : {msg:'请选择图片',type : 8}	
				});
				return;
			}
			
			var arrayVal = $("#productArray").val();
			var reg = /^\d{1,3}$/
			
			if(!reg.test(arrayVal)){
				$.layer({
					title:['分分通提示您',true],
					time:3,
					area : ['auto','auto'],
					dialog : {msg:'请输入正确的数字',type : 8}	
				});
				return ;
			}
						
			if(reg.test(arrayVal)){
				if(eval(arrayVal) > 100){
					$.layer({
						title:['分分通提示您',true],
						time:3,
						area : ['auto','auto'],
						dialog : {msg:'优先级不能大于100',type : 8}	
					});
				return;
				}
			}
			$("#inputForm").submit();		
		}else{
		
		var imgFilePre = $.trim($("#imgFilePre").val());
			var txt1 = $('#txt1').val();
			var txt2 = $('#textarea_txt2').val();
			
			if(txt1==''){
				$.layer({
						title:['分分通提示您',true],
							time:3,
						area : ['auto','auto'],
						dialog : {msg:'请填写介绍信息',type : 8}	
					});
				return;
			}
			if(txt2==''){
				$.layer({
						title:['分分通提示您',true],
						time:3,
						area : ['auto','auto'],
						dialog : {msg:'请填写详细信息',type : 8}	
					});
				return;
			} 
			
			var arrayVal = $("#productArray").val();
			var reg = /^\d{1,3}$/
			
			if(!reg.test(arrayVal)){
				$.layer({
						title:['分分通提示您',true],
						time:3,
						area : ['auto','auto'],
						dialog : {msg:'请输入正确的数字',type : 8}	
					});
				return ;
			}
						
			if(reg.test(arrayVal)){
				if(eval(arrayVal) > 100){
					$.layer({
						title:['分分通提示您',true],
						time:3,
						area : ['auto','auto'],
						dialog : {msg:'优先级不能大于100',type : 8}	
					});
				return;
				}
			}
			$("#inputForm").submit();
		}		
	});
</script> 
</body>
</html>