<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/pager.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/list.js"></script>
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
  <div class="rightBox" id="rightHeight">
	<div class="clear pt10">
      <table width="99%" border="0" cellspacing="0" cellpadding="0" class="tableA">
        <tr>
          <th width="100%" colspan="2">投诉</th>
        </tr>
        <tr>
          <td colspan="2">${(pager.content)!""}</td>
        </tr>
      </table>
    </div>

<div class="clear pt10">
 <table width="99%" border="0" cellspacing="0" cellpadding="0" class="tableA">
        <tr>
          <th width="11%">回复人</th>
          <th width="14%">回复时间</th>
          <th width="75%">回复内容</th>
        </tr>
        <#list pager.replyList as complaintReply>
        <tr>
          <td>${(pager.toUser.username)!""}</td>
          <td title="${(complaintReply.updateTime)!""}">${(complaintReply.updateTime)[0..9]!""}</td>
          <td>${(complaintReply.content)!""}</td>
        </tr>
        </#list>
      </table>     
    </div>          
    <div class="page">
    	<#if (pager.replyList?size > 0)>
         	<#include "/WEB-INF/templates/common/pager.ftl" />
       <#else>
         		没有找到回复记录!	
        </#if>
    </div>
    <div class="clear"></div>
    <div class="w150 abtn pt15">
    <a href="${base}/complaint_list.action"><div class="textBtn"><B>返回</B></div></a>  
    </div> 
    
  </div>
</div>  
<script type="text/javascript" src="${base}/template/common/js/height.js"></script>
<!--底部开始-->
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束--> 
</body>
</html>
