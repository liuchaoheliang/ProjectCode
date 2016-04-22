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
<script type="text/javascript" src="${base}/template/common/js/list.js"></script>
<script type="text/javascript" src="${base}/template/common/js/form.js"></script>
<script src="${base}/template/common/My97DatePicker/WdatePicker.js"></script>
<link href="${base}/template/common/css/adminexplain.css" rel="stylesheet" type="text/css" />

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

  <!--错误提示开始-->
   <#if errorMsg?exists && errorMsg !=''>
   		<div class="miss" id="show" style="display:block">
   <#else>
   		<div class="miss" id="show" style="display:none">
   </#if>
  
	
	 <h2><#if errorMsg?exists>${errorMsg!""}</#if></h2>
	 <a href="#" onclick="closed()"></a>
	</div>
<!--错误提示结束-->

<div class="middleBox">
<!-- 会员个人信息菜单开始 -->
<#include "/WEB-INF/templates/common/user_manage_menu.ftl">
<!-- 会员个人信息菜单结束 -->

<div class="rightBox" id="rightHeight">
<div class="text">您还未设置珠海农商银行提现账户，请尽快完善信息。</div>
<div class="inforBox validate" id="formlist">
  <form id="bingbankaccountform" action="binding_bank_account.action" method="post">
  		<dl>
          <dt>银行：</dt>
          <dd>
            <div>  	
		       <input name="userCertification.channelId" type="hidden" id="id_${(fundsChannel.id)?c}" value="${(fundsChannel.id)?c}">
		       ${(fundsChannel.channelFullName)!""}              
	<!--	     <span>
	            <select name="userCertification.channelId" class="default">
	              	<#list fundsChannelList as fundsChannel>
			           <option  value="#{fundsChannel.id?if_exists}" >${fundsChannel.channelFullName?if_exists}</option>
			        </#list>
	            </select>
	          </span>	 -->    
	     	</div>
          </dd>
        </dl>
  
        <dl>
          <dt>实名：</dt>
          <dd>
            <div> <strong><input name="userCertification.accountName" type="text" class="loginText2" id="adminname" value="${(userCertification.accountName)!""}"><u>*</u></strong></div>
          </dd>
        </dl>
        
        <dl>
          <dt>银行卡号：</dt>
          <dd>
            <div> <strong><input maxlength="19" name="userCertification.accountNo" type="text" class="loginText" id="banknumber" value="${(userCertification.accountNo)!""}"><u>*</u></strong></div>
          </dd>
        </dl>  
        
        <dl>
          <dt>身份证号：</dt>
          <dd>
            <div> <strong><input maxlength="18" name="userCertification.userIdCard" type="text" class="loginText" id="idCard" value="${(userCertification.userIdCrad)!""}"><u>*</u></strong></div>
          </dd>
        </dl>    

                               
        <div class="ml145 mt5"> 
          <a href='javascript:bindingBankAccount()' id="twoClick"><div id="bindBank" class="textBtn"><B>确定</B></div></a>  
        </div>
         </form> 
      </div>
 
 
    <div class="adminexplain">
     <dl>
      <dt>为何要进行提现认证？</dt>
      <dd>若将分分通积分提现，首先需要有一个珠海农商银行的银行账户，因此第一次进行积分提现操作时需要先进行提现认证；</dd>
      <dd>若您在分分通购买彩票，也需要绑定一个珠海农商银行账户用于接收彩票中奖奖金。</dd>
      <dt>可以绑定其他银行的账户吗？</dt>
      <dd>分分通积分或彩票奖金只支持提现到珠海农商银行，若您没有珠海农商银行的银行账户，可先到当地办理开户。<a href="http://www.zhnsb.com.cn/service/branches/"  target="_blank">查看珠海农商银行营业网点</a></dd>
     </dl>
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
	  "不能输入中文",
	  "两次密码输入不一致",
	  "请正确输入11位手机号码",
	  "您输入的邮箱格式不正确",
	  "请输入4-20位字符，不能输入中文",
	  "验证码是6位数字",
	  "两次输入的手机号不一样",
	  "请正确输入价格",
	  "请正确输入数量",
	  "请正确输入积分",
	  "请正确输入充值金额",
	  "请正确输入提现金额，例如：10，20，30，50，100，150……",
	  "请正确输入手机号或邮箱",
	  "对不起，你拥有的积分不足"
  ]	

//验证表单数据并提示
function bindingBankAccount(){
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
			}else if (oc.id == "phonecode") {
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
		$(this).unbind("click");
		checkAndSubmit();
	}		
}

function checkAndSubmit(){
	var senda = $("#twoClick");
	senda.attr("href","javascript:void(0);");
	senda.find("div").attr("class","gryBtn");	
	senda.find('B').html("请等待...");
	bingbankaccountform.submit();
}
</script>
</body>
</html>
