<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>安全中心-我的财务员</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/typefile.css" rel="stylesheet" type="text/css" />
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

<div class="middleBox">
<!-- 商家管理菜单开始 -->
<#include "/WEB-INF/templates/common/merchant_manage_menu.ftl">
<!-- 商家管理菜单结束 -->
  <div class="rightBox" id="rightHeight">
  	<div class="clear pt5">
      <div class="inforBox">
        <dl>
          <dt>登录名：</dt>
          <dd>
            <div> <span>${(merchantUserSet.loginName)!""}</span> </div>
          </dd>
        </dl>
        <dl>
          <dt>操作员姓名：</dt>
          <dd>
            <div> <span>${(merchantUserSet.beName)!""}</span> </div>
          </dd>
        </dl>        
        <dl>
          <dt>操作员工号：</dt>
          <dd>
            <div> <span>${(merchantUserSet.beCode)!""}</span> </div>
          </dd>
        </dl> 
 <!--  	<dl>
          <dt>密码：</dt>
          <dd>
            <div> <span>${(merchantUserSet.beCodepwd)!""}</span> </div>
          </dd>
        </dl> 
        --> 
        <dl>
          <dt>状态：</dt>
          <dd>
            <div> <span>
            	<#if merchantUserSet.state?exists && (merchantUserSet.state=="30")>
	        		可用
	        	<#elseif merchantUserSet.state?exists && (merchantUserSet.state=="50")>
	        		不可用
	         	</#if>	
			</span> </div>
          </dd>
        </dl> 
        <dl>
          <dt>操作员类型：</dt>
          <dd>
            <div> <span>
            		<#if merchantUserSet.roleType?exists && (merchantUserSet.roleType=="1")>
	        			财务账户
	        		<#elseif merchantUserSet.isAdmin?exists && (merchantUserSet.isAdmin=="1")>
	        			管理员
	        		<#elseif merchantUserSet.isAdmin?exists && (merchantUserSet.isAdmin=="0")>
	        			普通操作员
	         		</#if>
            </span> </div>
          </dd>
        </dl> 
        <dl>
          <dt>备注：</dt>
          <dd>
            <div> <span>${(merchantUserSet.beSpec)!""}</span> </div>
          </dd>
        </dl>
   <!--     <div class="ml145 mt5"> 
          <a href="javascript:void(0);"><div id="returnClerkList" class="textBtn"><B>返回列表</B></div></a> 
        </div>  --> 
         <div class="ml100 mt5"> 
          	<a href="javascript:void(0);"><div id="returnClerkList" class="textBtn"><B>返回列表</B></div></a> 
        </div>      
      </div>
    </div>
  </div>
</div>
<script type="text/javascript" src="${base}/template/common/js/height.js"></script>
<!--底部开始-->
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
<script type="text/javascript"> 
$("#returnClerkList").click(function(){
	window.history.back();return false;			
});
</script> 
</body>
</html>