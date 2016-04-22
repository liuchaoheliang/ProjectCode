<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>注册</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/web/css/regedit.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/rightimg.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/explain.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>
<script type="text/javascript" src="${base}/template/common/js/login.js"></script>
<script type="text/javascript" src="${base}/template/common/js/phoneValidateCode.js"></script>
<style type="text/css">
.agreementWindow .dialogContent {
    margin: 0;
    overflow: hidden;
    padding: 5px;
}
.agreementWindow .agreementWindowContent {
    border: 1px solid #E0E5E9;
    clear: both;
    color: #6F6F6F;
    height: 260px;
    line-height: 20px;
    overflow-y: scroll;
    padding: 10px;
    text-align: left;
    width: 626px;
}

</style>
</head>
<script type="text/javascript" src="${base}/template/common/js/form.js"></script>
<script type="text/javascript" src="${base}/template/common/js/method.js"></script>
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


  
<!--注册开始-->
<div class="regeditBox">
  <div class="topReg"><B>已有账号？请直接<a href="${base}/toLogin.action">登录</a></B></div>
  <div class="regeditLeft  validate">
    <form  name="formregedit" action="${base}/addUser.action" method="post" id="formlist">
    	<input type="hidden" class="loginText" value="0" id="phoneMessageType">
     <dl>
       <dt>用户名：</dt>
       <dd>
         <div>
           <strong><input type="text" class="loginText" name="username" id="username"><u>*</u></strong>
         </div>
       </dd>
     </dl>
     
     <dl>
       <dt>密码：</dt>
       <dd>
         <div>
          <strong> <input type="password" name="password"  class="loginText" id="password"><u>*</u></strong>
         </div>       
       </dd>
     </dl> 
     
     <dl>
       <dt>确认密码：</dt>
       <dd>
         <div>
          <strong> <input type="password" name="password1"  class="loginText" id="password2"><u>*</u></strong>
         </div>       
       </dd>
     </dl> 
       
     <dl>
       <dt>手机号码：</dt>
       <dd>
         <div>
           <strong><input name="mobilephone" type="text"  class="loginText" id="phone" maxlength="11"><u>*</u></strong>
         </div>       
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

     <dl>
       <dt>邮箱：</dt>
       <dd>
         <div>
           <strong><input type="text" name="email" class="loginText" id="email"></strong>
         </div>       
       </dd>
     </dl>         
     
     <dl class="mb10">
       <dt>验证码：</dt>
       <dd>
         <div class="codeimg">
            <strong><input  type="text" class="loginText4" name="validateCode" id="code"/>
        	<img src="validateCode.action" onclick="javascript:changeValidateCode();"/>
         	<a href="javascript:void(0)" onclick="javascript:changeValidateCode();">看不清 换一张</a><u>*</u></strong>
          </div>       
       </dd>
     </dl>  
     </form>   
     <div class="regedittiaokuan fl ml50"><input type="checkbox" name="" id="xieyi" class="checkBox"/><a href="javascript:void(0);" onclick="javascript:showAgreementWindow();">《珠海农商银行服务条款》</a></div>
     <div class="fl">
       <a href="javascript:void(0);"><div id="send" class="textBtn"><B>同意协议并注册</B></div></a>  
       <!-- <a href="javascript:register();" id="send"><div class="textBtn"><B>同意协议并注册</B></div></a>   -->
     </div>  
                  
   </div> 
</div>
<!-- 注册开始  -->    
  
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
	  "请正确输入19位以622859开头的珠海农商银行卡号",
	  "姓名只能输入中文且不能含有空格",
	  "新密码不能和原密码相同",
	  "请正确输入您的身份证号"
  ]	
 	$(document).keyup(function(event){   
	     	//获取当前按键的键值   
	     	//jQuery的event对象上有一个which的属性可以获得键盘按键的键值   
	     	var keycode = event.which;  
	     	 
	     	//处理回车的情况   
	     	if(keycode==13){   
	         	register();
	    	}   
 		}); 
 	
//验证表单数据并提示
$("#send").click(function(){
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
			if (oc.id == "username" || oc.id == "password" || oc.id == "password2" || oc.id == "phone" || oc.id == "phone2") {
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
		register();
	}		
})


/**
 * 注册
 */
function register(){
	var name = $.trim($('#username').val());
	var pwd = $.trim($('#password').val());
	var pwd1 = $.trim($('#password2').val());		
	var mobilephone = $.trim($('#phone').val());
	var phoneCode = $.trim($('#phonecode').val());
	var email = $.trim($('#email').val());
	var validateCode = $.trim($('#code').val());
	var $mobilePhone = $("#phone");
	var $valPhoneCode = $("#phonecode");
	var flag2 = validatePhoneCode($mobilePhone,$valPhoneCode);
	if(!flag2){
		return ;
	}
	var flagBox = $("input[type='checkbox']").attr("checked");

	if(!flagBox){
		$.layer({
			area : ['auto','auto'],
			dialog : {msg:'请确认是否同意《珠海农商银行服务条款》!',type : 4}	
		});
		return;
	}
	$.getJSON("resgiterValidate.action?username="+name+"&password="+pwd+"&password1="+pwd1+"&mobilephone="+mobilephone+"&email="+email+"&validateCode="+validateCode,function(data){
		if(data.reno == "0"){
			$("#formlist").submit();
		}else{
			if(data.msg !=''){
				$.layer({
					title:['分分通提示您',true],
					time:3,
					area : ['auto','auto'],
					dialog : {msg:data.msg,type : 3}	
				});
			}else{
				$.layer({
					title:['分分通提示您',true],
					time:3,
					area : ['auto','auto'],
					dialog : {msg:data.msg,type : 4}	
				});
			}
			
			
			$("#bind_mobile_btn").attr('disabled',false);		
		}
	});
}
	$(window).load(function(){ if("${(message)!''}"){
		//alert("${(message)!''}");
		var msg = "${(message)!''}";
		if(msg.indexOf("注册成功") != -1){
			//$('#regInfo').val(msg);
			//$('#formReg').submit();
		}else{
			if(msg !=''){
				$.layer({
					area : ['auto','auto'],
					dialog : {msg:msg,type : 3}	
				});
			}else{
				$.layer({
					area : ['auto','auto'],
					dialog : {msg:msg,type : 4}	
				});
			}
			
		}	
	} 
	$("#bind_mobile_btn").attr('disabled',false); });

	function showAgreementWindow() {
		var agreementWindowHtml = '<div id="agreementWindowContent" class="agreementWindowContent" style="overflow:auto;width:650px; height:300px;"></div>';

		//function agreeAgreement() {
		//	var $registerWindowIsAgreeAgreement = $("#registerWindowIsAgreeAgreement");
		//	$registerWindowIsAgreeAgreement.attr("checked", true);
		//} 

		//$.dialog({
			//title: "分分通", 
				//content: agreementWindowHtml, 
				//id: "agreementWindow", 
				//className: "agreementWindow", 
				//width: 660, 
				//modal : true
				//});	
			
			
			$.layer({
			   title:['分分通',true],
			    area : [720,400],
			    dialog : {
			        msg:agreementWindowHtml,
			        btns : 2, 
			        type : 2,
			        btn : ['确定','取消'],
			        yes : function(index){
			            layer.close(index);
			            $('#xieyi').attr('checked','checked');
			        },
			        no : function(index){
			        	layer.close(index);
			        }
			    }
			});
			
			
			
			
		var $agreementWindowContent = $("#agreementWindowContent");
			
		$.ajax({
				url:"show_agreement.action",
				type: "POST",
				dataType: "html",
				data:{"agreement.type": "0"},
				beforeSend: function(data) {
						$agreementWindowContent.html('加载中...');
				},
				success: function(data) {
						$agreementWindowContent.html(data);
				}
		}); 
	}
</script>
</body>
</html>
