<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>安全中心-修改密码</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/typefile.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>
<#if !id??>
	<#assign isAdd = true />
<#else>
	<#assign isEdit = true />
</#if>
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

<div class="middleBox mt10">
<!-- 商家管理菜单开始 -->
<#if merchantRole?exists && merchantRole=='1'>
<#include "/WEB-INF/templates/admin/seller/finance/merchant_manage_menu.ftl">
<#else>
<#include "/WEB-INF/templates/common/merchant_manage_menu.ftl">
</#if><!-- 商家管理菜单结束 -->
  <div class="rightBox" id="rightHeight">
    <div class="clear pt5">
 <!--   <form id="changPwdForm" method="post" action="changeClerkPassword.action"> -->
      <div class="inforBox validate" id="formlist">
        <dl>
          <dt>原密码：</dt>
          <dd>
          	<div><strong><input name="oldpassword" type="password" class="loginText" id="password3"><u>*</u></strong></div>
          </dd>
        </dl>       
        <dl>
          <dt>新密码：</dt>
          <dd>
         	<div><strong> <input type="password" name="password"  class="loginText" id="password"><u>*</u></strong></div>
          </dd>
        </dl>   
                    
        <dl>
          <dt>确认新密码：</dt>
          <dd>
         	<div><strong> <input type="password" name="password1"  class="loginText" id="password2"><u>*</u></strong></div>
          </dd>
        </dl>                                 
        <div class="ml145 mt5"> 
         <a href="javascript:void(0);"> 
          <div id="pwdChangeDiv" class="textBtn"><B>确定</B></div>
         </a> 
        </div>       
      </div>
 <!--     </form>  -->
      <div class="myFloatRight"></div>
    </div>
  </div>
</div>
<script type="text/javascript" src="${base}/template/common/js/height.js"></script>
<!--底部开始-->
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
<script type="text/javascript">
var error = [
      "不能为空",
	  "请输入6-15位英文或数字",
	  "两次密码输入不一致",
	  "请正确输入11位手机号码",
	  "您输入的邮箱格式不正确",
	  "请输入4-20位字符，不能输入中文",
	  "验证码是6位数字",
	  "两次输入的手机号不一样",
	  "请正确输入价格，精确到小数点后两位",
	  "请正确输入数量",
	  "请正确输入积分，精确到小数点后两位",
	  "请正确输入充值金额",
	  "请正确输入提现金额，例如：10，20，30，50，100，150……",
	  "请正确输入手机号或邮箱",
	  "对不起，你拥有的积分不足",
	  "请正确输入银行卡号",
	  "姓名只能输入中文且不能含有空格"
  ]			

$('#pwdChangeDiv').click(function(){
	var ospan  = document.createElement('span')	;
	var formObject = document.getElementById('formlist');
	var ob = formObject.getElementsByTagName('u');
	for (i = 0; i < ob.length; i++) {
		var oc = ob[i].parentNode.getElementsByTagName('input')[0];
		if (oc.value == '') {
			ospan.innerHTML = '<h3>' + error[0] + '</h3>';
			ob[i].parentNode.appendChild(ospan);
			if (ob[i].parentNode.getElementsByTagName('span').length == 2) {
				ob[i].parentNode.removeChild(ob[i].parentNode.getElementsByTagName('span')[0])
			}
			if (oc.id == "username" || oc.id == "password" || oc.id == "password2" || oc.id == "password3" || oc.id == "phone" || oc.id == "phone2" || oc.id == "people" || oc.id =="seller" || oc.id =="phonemail" || oc.id =="banknumber") {
				oc.className = 'errorText'
			} else if(oc.id == "adminname" || oc.id == "fullname"){
				oc.className = 'errorText2'
				}
			  else if (oc.id == "phonecode") {
				oc.className = 'errorText3'
			} else if (oc.id == "code" || oc.id =="price" || oc.id =="points" || oc.id == "cash") {
				oc.className = 'errorText4'
			} else if (oc.id == "number" ) {
				oc.className = 'errorText5'
			} 
			break;
		}
	}
    var subm = formObject.getElementsByTagName('h3').length;
	if(subm){
		return;
	}else{
		var oldpwd = $.trim($('#password3').val());
		var newpwd = $.trim($('#password').val());
		var newpwd2 = $.trim($('#password2').val());
		if(oldpwd == ''){
			$.layer({
				title:['分分通提示您',true],
				time:3,
				area : ['auto','auto'],
				dialog : {msg:'请输入原密码',type : 8}	
			});
			return;
		}
		if(newpwd == ''){
			$.layer({
				title:['分分通提示您',true],
				time:3,
				area : ['auto','auto'],
				dialog : {msg:'请输入新密码',type : 8}	
			});
			return;
		}
		if($('#password').val() == $('#password3').val()){
			$.layer({
				title:['分分通提示您',true],
				time:3,
				area : ['auto','auto'],
				dialog : {msg:'密码不可修改成为相同',type : 8}	
			});
			return;
		}
		if(newpwd2 == ''){
			$.layer({
				title:['分分通提示您',true],
				time:3,
				area : ['auto','auto'],
				dialog : {msg:'请确认新密码',type : 8}	
			});
			return;
		}
		$.getJSON("changeClerkPassword.action?oldpassword="+oldpwd+"&password="+newpwd+"&password1="+newpwd2,function(data){
			$.layer({
				title:['分分通提示您',true],
				time:3,
				area : ['auto','auto'],
				dialog : {msg:data.desc,type : 9}	
			});
			if(data.reno=='0'){
				if(data.userType=='1'){
					//window.parent.frames.location.reload();
					window.parent.frames.location.href="${base}/toLoginOut.action";
				}
				if(data.userType=='2'){
					window.parent.frames.location.href="${base}/toLoginOut.action";
					//Window.location.href="${base}/index.html";
				}			
			}else{
				$('#password3').val('');
			}
		});				
	}			
});
</script>       
</body>
</html>
