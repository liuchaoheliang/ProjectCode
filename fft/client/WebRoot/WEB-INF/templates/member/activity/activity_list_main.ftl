<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通查询-活动列表</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/ajax_detail.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/pager.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/select.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${base}/template/common/js/select.js"></script>
<script type="text/javascript" src="${base}/template/common/js/list.js"></script>
<script src="${base}/template/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/template/common/js/ajax_detail.js"></script>
<link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>
</head>

<style>
 .page{ text-align:left; line-height:35px ;float:left; padding-left:10px;}
</style>

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

	<div class="rightBox" id="rightHeight">

   		 <div class="fromDiv">
        	 <span><b>活动列表：</b></span>
   		 </div>
		<div class="page"><a href="member_activity_spring">新春大礼现金送不停</a></div>
    	<div class="page"　style="text-align:left" ><a href="member_activity_movie">快乐金秋</a></div>
    	
  	</div>
  
</div>  

 	
</div>  
<script type="text/javascript" src="${base}/template/common/js/height.js"></script>
<!--底部开始-->
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->   
</body>
</html>
