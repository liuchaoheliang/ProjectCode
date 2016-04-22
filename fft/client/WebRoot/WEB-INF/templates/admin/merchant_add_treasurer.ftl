<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>安全中心-添加财务员</title>
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
 <form id="addClerkForm" method="post" action="addTreasurer.action">
 <input type="hidden" name="merchantUserSet.loginName" id="loginName" value="${(merchantUserSet.loginName)!""}" />
 <input type="hidden" name="merchantUserSet.merchantId" id="merchantId" value="${(merchantUserSet.merchantId)!""}" />
      <div class="inforBox">
      	<dl>
          <dt>登录名：</dt >
          <dd>
            <div>${(merchantUserSet.loginName)!""}</div>
          </dd>
        </dl>
        <dl>
          <dt>操作员姓名：</dt>
          <dd>
            <div><input name="merchantUserSet.beName" id="beName" type="text" class="loginText"><span style="color:red;"> *</span></div>
          </dd>
        </dl>
        <#assign isDisplay=false>
        <dl>
          <dt>操作员类型：</dt>
          <dd>
            <div>
	     		财务账户 <input type="text" value="0" style="display:none;"  name="merchantUserSet.isAdmin" /><span style="color:red;"> *</span>
	     	</div>
          </dd>
        </dl>
        <dl>
        	<dt>所属门店：</dt>
            	<dd class="fl" id="uboxstyle"> 		
				<select name="StoreId" id="selectStore">					
					<option value="" >全部门店</option>
					<#if (storeList?size>0) >
						<#list storeList as store>
							<option value="${(store.id)?c}">${store.shortName}</option>
						</#list>
					</#if>
				</select>       		
        	</dd>
        </dl>
        <dl>
          <dt>密码：</dt>
          <dd>
            <div><input name="merchantUserSet.beCodepwd" id="beCodepwd" type="password" class="loginText" maxlength="15" ><span style="color:red;" > *</span></div>
          </dd>
        </dl>
         <dl>
          <dt>财务员备注：</dt>
          <dd>
            <div><input name="merchantUserSet.beSpec" id="beSpec" type="text" class="loginText"></div>
          </dd>
        </dl>                                     
        <div class="ml145 mt5"> 
          <a href="#">
          <div id="addClerk" class="textBtn"><B>添加</B></div>
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
	var beName = $.trim($('#beName').val());
	var isAdmin = $.trim($('#language_mac').val());
	var beCodepwd = $.trim($('#beCodepwd').val());
	//var storeId=$("#selectStore option:selected").val();
	//$("#storeId").val(storeId);
	
	if(beName == ''){
		$.layer({
			title:['分分通提示您',true],
			time:3,
			area : ['auto','auto'],
			dialog : {msg:'请输入财务员昵称',type : 8}	
		});
		return;
	}
	/**if(isAdmin == ''){
		alert("请选择员工操作权限");
		return;
	}**/
	if(beCodepwd == ''){
		$.layer({
			title:['分分通提示您',true],
			time:3,
			area : ['auto','auto'],
			dialog : {msg:'请输入财务员密码',type : 8}	
		});
		return;
	}
	var reg = /^\w{6,15}$/;
	if(!reg.test(beCodepwd)){
		$.layer({
			title:['分分通提示您',true],
			time:3,
			area : ['auto','auto'],
			dialog : {msg:"密码请使用6-15位英文、数字或下划线的组合更安全",type : 8}	
		});
		return;
	}
	var checkinputbox = $('input[type="checkbox"]');
	var checkCount = 0;
	checkinputbox.each(function (i){
		if($(this).attr('checked') == 'checked'){
			checkCount ++;
		}
	})

	$("#addClerkForm").submit();	
});
</script>       
</body>
</html>
