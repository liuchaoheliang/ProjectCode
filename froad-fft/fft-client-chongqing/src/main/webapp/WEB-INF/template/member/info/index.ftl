<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>用户信息</title>
<link rel="icon" href="${base}/favicon.ico" type="image/x-icon" />
<#include "/include/sources.ftl">
<link href="${base}/resources/member/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>

<script type="text/javascript">

	//展示用户信息
	function toUpdateInfo(){
		$("#rightHeight").hide();
		$("#rightHeight1").show();
	}
	
	//修改用户信息
	function updateUserInfo(){
		var newName = $.trim($('#newName').val());
		//var newEmail = $('#newEmail').val();
		//alert(newName);
		$.Ajax({
			url : '${base}/member/info/update_info.jhtml',
			datas : {'newUsername':newName},
			mustLogin : true,
			successFn : function(data){
				if(data.flag){
					layer.msg(data.msg, 2, -1);
					window.location.href ='${base}/member/info/index.jhtml' ;
				}else{
					layer.msg(data.msg,2,-1);
				}
			}
			
		});
	}
</script>

<!--
* Author:
* pengling@f-road.com.cn 
*/
--> 
<#-- 头部 开始-->
<#include "/include/common/header.ftl">
<#-- 头部 结束-->

<div class="main">
  <div class="middleBox" > 
    <!--左侧导航开始-->
    <#include "/include/member/menu.ftl">  
    <!--左侧导航结束-->
    
     <!--用户信息展示-->
    <div class="rightBox" id="rightHeight">
      <div class="clear pt5 mt35 mb30">
      	<h2 class="pl50 f14">您当前拥有积分：${login_identification.froadPoints!'0'}分</h2>
        <div class="inforBox validate psw" id="formlist">
          <dl>
            <dt>用户名：</dt>
            <dd>
              <div><strong>${user.loginID}</strong></div>
            </dd>
          </dl>
          <dl>
            <dt>名字：</dt>
            <dd>
              <div><strong>${user.uname}</strong></div>
            </dd>
          </dl>
          <dl>
            <dt>手机号：</dt>
            <dd>
              <div><strong>${user.mobileEncrypt}</strong></div>
            </dd>
          </dl>
           <#--<#if user.eamil?exists && user.eamil != null>
	          <dl>
	            <dt>邮箱：</dt>
	            <dd>
	              <div><strong>${user.email}</strong></div>
	            </dd>
	          </dl>          
            </#if>-->
        	<div class="ml150 mt5"> 
          		<input class="subBtn" value="&nbsp;&nbsp;修改资料&nbsp;&nbsp;" type="button" onclick="toUpdateInfo()">
          	</div>
        
        </div>        
      </div>
    </div>
     <!--用户信息展示-->
    
     <!--用户信息修改-->
    <div class="rightBox" id="rightHeight1"  style="display: none" >
      <div class="clear pt5 mt35 mb30">
        <div class="inforBox validate psw" id="formlist">
          <dl>
            <dt>用户名：</dt>
            <dd>
              <div><strong>${user.loginID}</strong></div>
            </dd>
          </dl>
          <dl>
            <dt>姓名：</dt>
            <dd>
              <div><strong>
                <input type="text" name="textfield"  value="${login_identification.uname}" class="loginText" id="newName">
                <u>*</u></strong></div>
            </dd>
          </dl>
          <dl>
            <dt>手机号：</dt>
            <dd>
              <div><strong>${user.mobile}</strong></div>
            </dd>
          </dl>
          <!--
          <dl>
            <dt>邮箱：</dt>
            <dd>
              <div><strong>
                <input type="text" name="textfield"  class="loginText" id="newEmail">
                <u>*</u></strong></div>
            </dd>
          </dl>
          -->
            <div class="ml150 mt5"> 
            	<input class="subBtn" value="&nbsp;&nbsp;确定&nbsp;&nbsp;" type="button" onclick="updateUserInfo()">        
            </div>
        </div>
      </div>
    </div>
     <!--用户信息修改-->
    
    
    
  </div>
</div>

<!--清除浮动-->
<div class="clear"></div>
<!--清除浮动--> 

<#-- 底部 -->
<#include "/include/common/footer.ftl">
<#-- 底部 -->
</body>
</html>
