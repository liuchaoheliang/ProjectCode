<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>手机号码修改失败</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/inforbox.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/member/css/text.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/phoneValidateCode.js"></script>
</head>
<script type="text/javascript" src="${base}/template/common/js/form.js"></script>
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
<!--会员个人信息菜单开始-->
<#include "/WEB-INF/templates/common/user_manage_menu.ftl">
<!--会员个人信息菜单结束-->
  <div class="rightBox" id="rightHeight">  
    <div class="inforrightBox">
        <!--跳转内容begin-->
    <div class="failBoxA"> 
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class="jumpFail">修改手机号码失败</td>
 <!--        <td class=""><a href="${base}/toChangeMobilePhone.action">返回</a></td> -->  
          </tr>
          <tr>
          	<td id="failseDiv"></td>
          </tr>
      </table>  
    </div>          
   </div>
  </div>
</div>
<script type="text/javascript" src="${base}/template/common/js/height.js"></script>
<!--底部开始-->
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
<script type="text/javascript"> 
	$(window).load(function(){ 
		if("${message}"){
			//alert("${message}");
			var msg = "${message}";
			if(msg !=''){
				$('#failseDiv').html(msg+'<a href="${base}/toChangeMobilePhone.action">返回</a>');
			//	$.layer({
			//		area : ['auto','auto'],
			//		dialog : {msg:msg,type : 8}	
			//	});
			}			
		}
	})
</script>
</body>
</html>
