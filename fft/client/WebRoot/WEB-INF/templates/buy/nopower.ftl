<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>未获得该访问权限</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/dropkick.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/pager.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/list.js"></script>
<script src="${base}/template/common/js/form.js" type="text/javascript"></script>
<script type="text/javascript" src="${base}/template/common/js/drop.js"></script>
<script type="text/javascript" src="${base}/template/common/js/closeDialog.js"></script>
<script src="${base}/template/common/My97DatePicker/WdatePicker.js"></script>

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
	  "请正确输入积分"
  ]	


function checkAndSubmit() {
	var oText =document.getElementById('formlist');
	var ospan=document.createElement("span");
	var formObject=document.getElementById("formlist");
	var ob=formObject.getElementsByTagName("u");
	for (i = 0; i < ob.length; i++) {
		var oc = ob[i].parentNode.getElementsByTagName('input')[0];
		if (oc.value == '') {
			ospan.innerHTML = '<h3>' + error[0] + '</h3>';
			ob[i].parentNode.appendChild(ospan);
			if (ob[i].parentNode.getElementsByTagName('span').length == 2) {
				ob[i].parentNode.removeChild(ob[i].parentNode.getElementsByTagName('span')[0])
			}
			if (oc.id == "username" || oc.id == "password" || oc.id == "password2" || oc.id == "phone" || oc.id == "phone2" || oc.id == "people" || oc.id =="seller") {
				oc.className = 'errorText'
			} else if (oc.id == "phonecode") {
				oc.className = 'errorText3'
			} else if (oc.id == "code") {
				oc.className = 'errorText4'
			}
			break;
					
		}
	}
 	var subm = oText.getElementsByTagName('h3').length;
	if(subm){
				 return ;
	}else{
		var senda = $("#senda");
		senda.attr("href","javascript:void(0);");
		senda.find("div").attr("class","gryBtn");	
		senda.find('B').html("请等待...");
		formlist.submit();
	}
}


function countRealCurrencyValue(){
	var price = document.getElementById('price').value,
	number = document.getElementById('number').value,
	preRatio=document.getElementById('preRatio').value,toSendPoints,dotPosition;
	
	if(price == "" || number == "" || preRatio == "" ) {
		document.getElementById('toSendPoints').innerHTML = 0;
		return;
	}
	
	toSendPoints = (price * number * preRatio / 100).toString();
	dotPosition = toSendPoints.search(/[.]/);
 	document.getElementById('toSendPoints').innerHTML = (dotPosition == -1 ? toSendPoints : toSendPoints.substring(0,dotPosition+3)) ;
}

</script>

</head>
<body myload="countRealCurrencyValue()">
<!--
* Author:
* pengling@f-road.com.cn 
*/
--> 


<!-- 商家管理菜单开始 -->
<!-- 商家管理菜单结束 -->
<div class="moneybg">

<#if errorMessage?exists>
<div id ="show">
<div class="miss" id="missadmin">
<div id="errorMsg"><h2>${errorMessage?if_exists} </h2></div>
 
	<a href="#" onclick="closed()"></a>
 
</div>
</div>
</div>

</#if>

  <div class="money" id="rightHeight">
    <div class="stepadmin"></div>
    <div class="step">
      <li class="colorRed step07">输入信息</li>
      <li class="step02">生成订单</li>
      <li class="step03">收款结果</li>
    </div>
   <form id="formlist" action="create_order.action" method="post" >    
    <div class="inforBox validate" id="admin" style="height:120px;"> 
	    <center>
	    	<b style="color:red; line-height:70px;">对不起，您没有权限访问该页面</b><br/>
	    	<a href="javascript:history.go(-1);" style="color:green;font-size:14px;">返回</a>
	    <center>    	           
    </div>
    </form>
  </div>
  </div>
</body>
</html>
