<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>安全中心-修改操作员</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/select2.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>
<script type="text/javascript" src="${base}/template/common/js/select.js"></script>
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
 <form id="addClerkForm" method="post" action="update_clerk.action">
 <input type="hidden" name="merchantUserSet.loginName" id="loginName" value="${(merchantUserSet.loginName)!""}" />
 <input type="hidden" name="merchantUserSet.merchantId" id="merchantId" value="${(merchantUserSet.merchantId)!""}" />
 <input type="hidden" name="merchantUserSet.beName" id="merchantId" value="${(merchantUserSet.beName)!""}" />
 <input type="hidden" name="merchantUserSet.beCode" id="beCode" value="${(merchantUserSet.beCode)!""}" />
      <div class="inforBox">
      	<dl>
          <dt>登录名：</dt>
          <dd>
            <div>${(merchantUserSet.loginName)!""}</div>
          </dd>
        </dl>
        <dl>
          <dt>操作员姓名：</dt>
          <dd>
            <div>${(merchantUserSet.beName)!""}</div>
          </dd>
        </dl>
        <dl>
          <dt>操作员工号：</dt>
          <dd>
            <div>${(merchantUserSet.beCode)!""}</div>
          </dd>
        </dl>
        <#assign isDisplay=false>
        
        <dl>
          <dt>操作员类型：</dt>
          <dd>
            <div>
            		<#if merchantUserSet.roleType?exists && (merchantUserSet.roleType=="1")>
	        			财务账户
	        		<#elseif merchantUserSet.isAdmin?exists && (merchantUserSet.isAdmin=="1")>
	        			管理员
	        		<#elseif merchantUserSet.isAdmin?exists && (merchantUserSet.isAdmin=="0")>
	        			普通操作员
	         		</#if>
	     			<input type="text" value="0" style="display:none;"  name="merchantUserSet.isAdmin" /><span style="color:red;"> *</span>
	     	</div>
          </dd>
        </dl>
         <dl>
        	<dt>所属门店：</dt>
        	<dd class="fl" id="uboxstyle">         		
				<select name="StoreId" id="selectStore">		
					<option value="" >全部门店</option>
					<#if (storeList)?exists && (storeList?size>0) >
						<#list storeList as store>										
							<option value="${(store.id)?c}"  <#if merchantUserSet.belongStoreId?exists && merchantUserSet.belongStoreId==store.id?c>selected="selected"</#if>>
								${store.shortName}
							</option>																												
						</#list>
					</#if>				
				</select>       		
        	</dd>
        </dl>
        <dl>
          <dt>密码：</dt>
          <dd>
            <div><input name="merchantUserSet.beCodepwd" id="beCodepwd" type="password" class="loginText" maxlength="15" ></div>
          </dd>
        </dl>
         <dl>
          <dt>备注：</dt>
          <dd>
            <div><input name="merchantUserSet.beSpec" id="beSpec" type="text" class="loginText"></div>
          </dd>
        </dl>                                     
        <div class="ml145 mt5"> 
          <a href="#">
          <div id="addClerk" class="textBtn"><B>修改</B></div>
          </a> 
        </div>       
      </div>
  </form> 
  </div>
</div>
<script type="text/javascript" src="${base}/template/common/js/height.js"></script>
<!--底部开始-->
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
<script type="text/javascript">	
$('#addClerk').click(function(){
	var beCodepwd = $.trim($('#beCodepwd').val());
	var reg = /^\w{6,15}$/;
	if(beCodepwd!="" && !reg.test(beCodepwd)){
		$.layer({
			title:['分分通提示您',true],
			time:3,
			area : ['auto','auto'],
			dialog : {msg:"密码请使用6-15位英文、数字或下划线的组合更安全",type : 8}	
		});
		return;
	}
	$("#addClerkForm").submit();	
});
</script>       
</body>
</html>
