<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>手机号码修改</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/inforbox.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/member/css/text.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/phoneValidateCode.js"></script>
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

<!--广告栏开始-->
<div class="ad"><img src="${base}/template/member/img/123213.png"></div>
<!--广告栏结束-->

<div class="middleBox">
<!--会员个人信息菜单开始-->
<#include "/WEB-INF/templates/common/user_manage_menu.ftl">
<!--会员个人信息菜单结束-->
  <div class="rightBox" id="rightHeight">
  <div class="text" style="height:50px;"><b>提示：</b>您的分分通账户拥有银行积分，为了您的积分安全，更改手机号码需要先验证激活银行积分时的账户开户名及身份证号码</div>  								
    <div class="inforBox validate">
    <form  name="formregedit" action="${base}/validateChangeMobilePhone.action" method="post" id="formlist">
      <dl>
        <dt>原手机号：</dt>
        <dd>
          <div>${mobilephone!""}</div>
        </dd>
      </dl>
      <dl>
        <dt>登录密码：</dt>
        <dd>
          <div>
            <strong><input name="password" type="password" class="loginText" id="password6"><u>*</u></strong>
          </div>
        </dd>
      </dl>
      
      <dl>
        <dt>账户开户名：</dt>
        <dd>
          <div>
            <strong><input name="tempPoint.accountName" type="text" class="loginText2" maxlength="15" id="adminname"><u>*</u></strong>
          </div>
        </dd>
      </dl>
      
      <dl>
        <dt>身份证号码：</dt>
        <dd>
          <div>
            <strong><input type="text" name="tempPoint.identificationCard" class="loginText" id="card"><u>*</u></strong>
          </div>
        </dd>
      </dl>
       </form>  
      <div class="ml130 mt5"> 
        <a href="javascript:void(0);">
          <div class="textBtn" id="sendNext"><B>下一步</B></div>
        </a> 
      </div>
      
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
	  "请输入6-15位英文、数字或下划线的组合",
	  "两次密码输入不一致",
	  "请正确输入11位手机号码",
	  "您输入的邮箱格式不正确",
	  "请输入4-20位字符，不能为纯数字，必须以字母开头",
	  "验证码是6位数字",
	  "两次输入的手机号不一样",
	  "请正确输入价格，精确到小数点后两位",
	  "请正确输入数量",
	  "请正确输入积分，精确到小数点后两位",
	  "请正确输入充值金额",
	  "请正确输入积分数，例如：10，20，130",
	  "请正确输入手机号或邮箱",
	  "对不起，你拥有的积分不足",
	  "请正确输入19位以622859开头的珠海银行卡号",
	  "姓名只能输入中文且不能含有空格",
	  "新密码不能和原密码相同",
	  "请正确输入您的身份证号",
	  "密码输入错误，请重新输入"
  ]	
 	$(document).keyup(function(event){   
	     	//获取当前按键的键值   
	     	//jQuery的event对象上有一个which的属性可以获得键盘按键的键值   
	     	var keycode = event.which;  
	     	 
	     	//处理回车的情况   
	     	if(keycode==13){   
	         	//register();
	    	}   
 		}); 
 	
//验证表单数据并提示
$("#sendNext").click(function(){
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
			if (oc.id == "password6" || oc.id == "adminname" || oc.id == "card") {
				oc.className = 'errorText'
			} else if (oc.id == "phonecode") {
				oc.className = 'errorText3'
			} else if (oc.id == "code") {
				oc.className = 'errorText4'
			}
			break;
		}
	}
    var subm = formObject.getElementsByTagName('h3').length;
	if(subm){
		return;
	}else{
		validateChangeMemPwd();
	}		
})


/**
 * 注册
 */
function validateChangeMemPwd(){
	$("#formlist").submit();
}
	$(window).load(function(){ 
		if("${message}"){
			//alert("${message}");
			var msg = "${message}";

			if(msg !=''){
				$.layer({
					area : ['auto','auto'],
					dialog : {msg:msg,type : 8}	
				});
			}
		}
	})
</script>
</body>
</html>
