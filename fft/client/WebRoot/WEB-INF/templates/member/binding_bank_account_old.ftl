<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>安全中心-提现认证</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/text.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/pager.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/select.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/dropkick.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/drop.js"></script>
<script type="text/javascript" src="${base}/template/common/js/form.js"></script>
<script src="${base}/template/common/My97DatePicker/WdatePicker.js"></script>
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
<div class="text greenFont"><#if errorMsg?exists>${errorMsg!""}</#if></div>
<div class="inforBox validate" id="formlist">
  <form id="bingbankaccountNewform" action="binding_bank_account_new.action" method="post">
  		<dl>
          <dt>银行：</dt>
          <dd>
            <div>  
		    <input name="userCertification.channelId" type="hidden" id="id_${(fundsChannel.id)?c}" value="${(fundsChannel.id)?c}">
		    ${(fundsChannel.channelFullName)!""}	     
	<!--	<span>
	         <form action="#" method="post">
	            <select name="userCertification.channelId" class="default">
	            <#if fundsChannelList?exists && fundsChannelList?has_content>
	              	<#list fundsChannelList as fundsChannel>
			           <option  value="#{fundsChannel.id?if_exists}" >${fundsChannel.channelFullName?if_exists}</option>
			        </#list>
			     </#if>
	            </select>
	          </form>
	          </span>	    --> 
	     	</div>
          </dd>
        </dl>
  
        <dl>
          <dt>实名：</dt>
          <dd>
            <div><#if userCertification?exists && userCertification.accountName?exists>${(userCertification.accountName)!""}<#else></#if></div>
          </dd>
        </dl>
        
        <dl>
          <dt>银行卡号：</dt>
          <dd>
            <div><#if userCertification?exists && userCertification.accountNo?exists>${(userCertification.accountNo)!""}<#else></#if></div>
          </dd>
        </dl>   
		<dl>
          <dt>身份证号：</dt>
          <dd>
            <div><#if userCertification?exists && userCertification.userIdCard?exists>${(userCertification.userIdCard)!""}<#else></#if></div>
          </dd>
        </dl>   
                               
        <div class="ml145 mt5"> 
          <a href="javascript:void(0)">
          <div id="bindBankNew" class="textBtn"><B>重新绑定</B></div>
          </a> 
        </div>
         </form> 
      </div>
  </div>
</div>  
<script type="text/javascript" src="${base}/template/common/js/height.js"></script>
<!--底部开始-->
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->    
<script type="text/javascript">

//验证表单数据并提示
$("#bindBankNew").click(function(){
		bingbankaccountNewform.submit();		
});
</script>
</body>
</html>
