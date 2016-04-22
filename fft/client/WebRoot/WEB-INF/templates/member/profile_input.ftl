<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<script type="text/javascript" src="${base}/template/common/js/form.js"></script>
<link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>
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
<!--会员个人信息菜单开始-->
<#include "/WEB-INF/templates/common/user_manage_menu.ftl">
<!--会员个人信息菜单结束-->
  
  <div class="rightBox" id="rightHeight">
  <form method="post" action="changeUserInfo.action"> 
  	<input type="hidden" id="username" name="username" value="${username!''}" />	
      <div class="inforBox validate" id="formlist">
        <dl>
          <dt>用户名：</dt>
          <dd>
           <div>${username!''}</div>
          </dd>
        </dl>
        <dl>
          <dt>名字：</dt>
          <dd>
	         <div><strong><input type="text" class="loginText2" id="fullname2" name="uname"  maxlength="10" value="${uname!''}" /><u>*</u></strong></div>
          </dd>
        </dl>        
        <dl>
          <dt>手机号：</dt>
          <dd>
            <div>${mobilephone!''}</div>
          </dd>
        </dl>
        <dl>
          <dt>邮箱：</dt>
          <dd>
	       <div>${email!''}</div>
          </dd>
        </dl>
                        
        <div class="ml130 mt5"> 
          <a href="#">
          <div id="saveInfo" class="textBtn"><B>确定</B></div>
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
<script type="text/javascript" src="${base}/template/common/js/oldValidate.js"></script>
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
	  "请正确输入您的银行卡号",
	  "姓名只能输入中文且不能含有空格"
  ];

$('#saveInfo').click(function(){
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
			if (oc.id == "password" || oc.id == "password2" || oc.id == "phone" || oc.id == "phone2" || oc.id == "people" || oc.id =="seller" || oc.id =="phonemail" || oc.id =="banknumber") {
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
		var email = $.trim($('#email').val());
		var uname=$.trim($("#fullname2").val());
		if(email != ''){
			if(/^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(email)){
				
			}else{
				
				$.layer({
					title:['分分通提示您',true],
					time:3,
					area : ['auto','auto'],
					dialog : {msg:'请输入正确的邮箱地址',type : 8}	
				});
				return;
			}
		}
		if(email=="${email}" && uname=="${uname}"){
				$.layer({
					title:['分分通提示您',true],
					time:3,
					area : ['auto','auto'],
					dialog : {msg:'您未修改任何信息！',type : 8}	
				});
				return;
		}
		$('form').submit();	
	}		
});
</script>   
</body>
</html>
