<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>找回密码</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/web/css/find.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>
<link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/login.js"></script>
<script type="text/javascript" src="${base}/template/common/js/oldValidate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/form.js"></script>
<script type="text/javascript" src="${base}/template/common/js/phoneValidateCode.js"></script>
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

<div class="box1010 pt10 clear">

<!--充值开始-->
  <div class="find">
  	<div class="stepfind"></div>
    <div class="step" id="find">
      <li class="colorRed  step07">输入信息</li>
      <li class="step02">找回成功</li>
    </div> 
    <form id="forgetPwdForm" action="forgetPwd.action" class="form" method="post">
    <input type="hidden" class="loginText" value="1" id="phoneMessageType">
    <div class="findBox validate" id="formlist">    
        <dl>
          <dt>手机号码：</dt>
          <dd>
             <div><strong><input name="loginKey" type="text" class="loginText" maxlength="11" id="phone"/><u>*</u></strong></div>
          </dd>
        </dl>       
	      <dl>
	       <dt>手机验证码：</dt>
	       <dd>
	         <div>
	           <strong><input type="text" class="loginText3" name="valPhoneCode" id="phonecode" maxlength="6"><u>*</u>
				<input id="bind_mobile_btn" type="button" value="获取手机验证码"/></strong>
	   <!--      <a href="#">点击获取</a>
	         </strong>  -->
	          </div>       
	       </dd>
	     </dl>            
<!--     <dl>
          <dt>验证码：</dt>
          <dd>
            <div class="link222 f12 codeimg rowElem">
	            <strong>
			         <input type="text" class="loginText4" id="code" name="validateCode"  size="8"  class="loginText4" />
			         <img src="validateCode.action" onclick="javascript:changeValidateCode();"/><u>*</u>
			         <a href="javascript:void(0)" onclick="javascript:changeValidateCode();">看不清 换一张</a>
		         </strong>
	        </div>
          </dd>
        </dl> -->		                 
        <div class="ml145 mt10"> 
          <a href="#">
          <div id="forgetBtn" class="textBtn"><B>下一步</B></div>
          </a> 
        </div>
    </div>
    </form> 

  </div> 
<!--充值结束-->               
</div>
<!--底部开始-->
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
<script type="text/javascript">
	var wait=90;
 	var code = "";
 	var valSuccessPhone="";
 	var samePhoneVal = "";
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

$('#forgetBtn').click(function(){
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
			if (oc.id == "username" || oc.id == "password" || oc.id == "password2" || oc.id == "phone" || oc.id == "phone2" || oc.id == "people" || oc.id =="seller" || oc.id =="phonemail" || oc.id =="banknumber") {
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
		forgetPwd();
	}		
});
function forgetPwd(){
	var $mobilePhone = $("#phone");
	var $valPhoneCode = $("#phonecode");
	var flag2 = validatePhoneCode($mobilePhone,$valPhoneCode);
	if(!flag2){
		return ;
	}
	$("#forgetPwdForm").submit();
}
</script> 
</body>
</html>
