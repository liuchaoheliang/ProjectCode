<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>安全中心-添加操作员</title>
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
 <form id="addClerkForm" method="post" action="addClerk.action">
 <input type="hidden" name="merchantUserSet.loginName" id="loginName" value="${(merchantUserSet.loginName)!""}" />
 <input type="hidden" name="merchantUserSet.merchantId" id="merchantId" value="${(merchantUserSet.merchantId)!""}" />
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
            <div><input name="merchantUserSet.beName" id="beName" type="text" class="loginText"><span style="color:red;"> *</span></div>
          </dd>
        </dl>
        <#assign isDisplay=false>
        <#if sellerList?exists>
	        <dl>
	        	<dt>收银台：</dt >
	        	<dd>
	        		<div>      
	        			<#list sellerList as seller>
					       <#if seller.sellerType?exists && seller.sellerType == "03" >
						    <input type="checkbox" checked="checked" name="clerkSellerType" value="03" checked>手机银行卡 
						   </#if>
						   <#if seller.sellerType?exists && seller.sellerType == "06" >
						    <input type="checkbox" checked="checked" name="clerkSellerType" value="06">赠送积分
						    </#if>
						   <#if seller.sellerType?exists && (seller.sellerType == "02" || seller.sellerType == "01") && isDisplay==false >
						  	 <#assign isDisplay=true>
							<input type="checkbox" checked="checked" name="clerkSellerType" value="01or02">积分及团购认证
						   </#if>
					    </#list>	
					    <span style="color:red;">*</span>
	        		<div>
	        	</dd>
	        </dl>
        </#if>
        <dl>
          <dt>操作员类型：</dt>
          <dd>
            <div>
            <!--<span class="ml5" id="uboxstyle">
	     	<select name="merchantUserSet.isAdmin" id="language_mac">
	         <option value="">选择权限</option>
	        	<option value="0">普通操作员</option>
	     	<option value="1">管理员</option> 
        	</select>
	     	</span>-->
	     		普通操作员 <input type="text" value="0" style="display:none;"  name="merchantUserSet.isAdmin" /><span style="color:red;"> *</span>
	     	</div>
          </dd>
        </dl>
         <dl>
        	<dt>所属门店：</dt>
        	<dd class="fl" id="uboxstyle">       		
				<select name="StoreId" id="selectStore">					
					<option value="" >全部门店 </option>
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
          <dt>操作员备注：</dt>
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
	
	if(beName == ''){
		$.layer({
			title:['分分通提示您',true],
			time:3,
			area : ['auto','auto'],
			dialog : {msg:'请输入操作员昵称',type : 8}	
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
			dialog : {msg:'请输入操作员密码',type : 8}	
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
	if(checkCount <=0){
		$.layer({
			title:['分分通提示您',true],
			time:3,
			area : ['auto','auto'],
			dialog : {msg:'请至少分配一个权限',type : 8}	
		});
		return;
	}
	$("#addClerkForm").submit();	
});
</script>       
</body>
</html>
