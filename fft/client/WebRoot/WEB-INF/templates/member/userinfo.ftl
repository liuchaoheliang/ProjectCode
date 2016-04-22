<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>安全中心-用户信息</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/inforbox.css" rel="stylesheet" type="text/css" />
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
<!--会员个人信息菜单开始-->
<#include "/WEB-INF/templates/common/user_manage_menu.ftl">
<!--会员个人信息菜单结束-->
  <div class="rightBox" id="rightHeight">
      <div class="inforBox">
        <dl>
          <dt>用户名：</dt>
          <dd>
            <div> <span>${username!''}</span> </div>
          </dd>
        </dl>
        <dl>
          <dt>名字：</dt>
          <dd>
            <div> <span>${uname!''}</span> </div>
          </dd>
        </dl>        
        <dl>
          <dt>手机号：</dt>
          <dd>
            <div> <span>${mobilephone!''}</span> </div>
          </dd>
        </dl>
        <dl>
          <dt>邮箱：</dt>
          <dd>
            <div> <span>${email!''}</span> </div>
          </dd>
        </dl>
                        
        <div class="ml130 mt10"> 
          <a href="toChangInfo.action">
          <div class="textBtn"><B>修改资料</B></div>
          </a> 
        </div>
        
      </div>
  </div>
</div>
<script type="text/javascript" src="${base}/template/common/js/height.js"></script>
<!--底部开始-->
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
<script type="text/javascript">
$(window).load(function(){ if("${message}"){
$.layer({
	title:['分分通提示您',true],
	time:3,
	area : ['auto','auto'],
	dialog : {msg:'${message}',type : 9}	
});
			}});
</script>
</body>
</html>
