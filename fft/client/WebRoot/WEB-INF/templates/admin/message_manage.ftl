<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商家管理-商户介绍</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/typefile.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>
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
  <form id="inputForm"  action="updPresentInfo.action" enctype="multipart/form-data" method="post">
	<input type="hidden" name="picType" id="picType" value="logo" />
	<input type="hidden" name="merchantPresent.merchantId" id="merchantId" value="${(merchantPresent.merchantId)!""}"/>
	<input type="hidden" name="message" id="message" value="${message!""}"/>
      <table width="99%" border="0" cellspacing="0" cellpadding="0" class="tableC">
        <tr>
          <th width="17%">商家简介</th>
          <td width="83%">
		    <input name="merchantPresent.txt1" id="txt1" cols="" rows="" maxlength="32" class="loginText" value="${(merchantPresent.txt1)!""}"></input>
		    <span>限制：不能超过32个字符</span>
          </td>
        </tr>
        <tr>
          <th>详细介绍</th>
          <td>
		    <textarea name="merchantPresent.txt" id="txt" cols="" rows="" maxlength="500" style="resize:none;" class="textArea500">${(merchantPresent.txt)!""}</textarea>
		    <span>限制：不能超过500个字符</span>
          </td>
        </tr>
        <tr>
          <th>图片</th>
          <td id="file"><a class="btn_addPic" href="javascript:void(0);"><span><em>+</em>添加图片</span>
            <input class="filePrew" title="支持jpg格式，文件小于1M" tabIndex=3 type=file size=3 id="imgFilePresent" name="imgFile">
            </a> <span>(图片大小不能超过1M)图片格式为*.jpg</span>
           </td>
        </tr>
        <tr>
          <th><B id="yulan" style="display:none">预览</B></th>
          <td>
          <div id="logoUrlDiv" style="display:none"><img id="logoUrl" src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(merchantPresent.photoUrl)!""}" onError="this.src='${base}/template/common/images/moren.gif'"/></div>
          </td>
        </tr>
      </table>
           
        <div class="ml145 mt10"> 
          	<a href="javascript:void(0);"><div id="preSave" class="textBtn"><B>保存</B></div></a>
        </div>      
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
	var liulanUrl = $('#logoUrl').attr("src");
	if(liulanUrl == '/communityBusiness_client' || liulanUrl ==null || liulanUrl == 'undefined'){
		
	}else{
		$('#logoUrlDiv').show();
		$('#yulan').show();
	}
	var message = $('#message').val();
	if(message != ''){
		$.layer({
				title:['分分通提示您',true],
				time:3,
				area : ['auto','auto'],
				dialog : {msg:message,type : 9}	
			});
	}
});
	
	//更新介绍	
	$("#preSave").click(function(){
		var txt1 = $('#txt1').val();
		if($.trim(txt1) == ''){
			$.layer({
				title:['分分通提示您',true],
				time:3,
				area : ['auto','auto'],
				dialog : {msg:'请输入商户介绍信息',type : 8}	
			});
			return;
		}
		var txt = $('#txt').val();
		if($.trim(txt) == ''){
			$.layer({
				title:['分分通提示您',true],
				time:3,
				area : ['auto','auto'],
				dialog : {msg:'请输入商户详细信息',type : 8}	
			});
			return;
		}
		$("#inputForm").submit();	
	});

</script> 
</body>
</html>
