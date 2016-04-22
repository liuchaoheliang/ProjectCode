<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>收银台-手机银行卡收款</title>
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
      <li class="step03">收款完成</li>
    </div>
   <form id="formlist" action="create_order.action" method="post" >
    <div class="inforBox validate" id="admin">
    
    
        <dl>
          <dt>支付方式：</dt>
          <dd>
            <div>现金（珠海农商银行手机银行卡支付）</div>
          </dd>
        </dl>
        <dl>
          <dt>手机号：</dt>
          <dd>
            <div> <strong><input  id="phone" name="buyerPhone" type="text" class="loginText" maxlength="11"><u>*</u>
            </strong> </div>
          </dd>
        </dl> 
        <input id="mobilecheckResult"  type="hidden" class="loginText" value="false">       
        <dl>
          <dt>价格：</dt>
          <dd>
            <div> <strong>
            <input id="price" name="tranDetail.goods.price" type="text" maxlength="11" class="loginText4" onChange="countRealCurrencyValue()" />
            <u>*</u>
            </strong>
            </div>
          </dd>
        </dl>
        <dl>
          <dt>消费名称：</dt>
          <dd>
            <div><strong><input id="name" name="tranDetail.goods.goodsName" type="text"  maxlength="15" class="loginText grayFont" value="<#if merchant.mstoreShortName?exists>${(merchant.mstoreShortName?html)!''}</#if>"></strong></div>
          </dd>
        </dl>
        
        
        <input id="number" name="tranDetail.goodsNumber" type="hidden" class="loginText5" value=1 />
        
        <!--
        <dl>
          <dt>数量：</dt>
          <dd>
            <div><strong>
            <input id="number" name="tranDetail.goodsNumber" type="hidden" class="loginText5" value=1 />
            <u>*</u>
            </strong>
           
            </div>
          </dd>
        </dl> -->
        
        <dl>
          <dt>返利积分：</dt>
          <dd>
            <div ><b class="redFont" id="toSendPoints"> 0</b> </div>
          </dd>
        </dl>        
        <dl>
          <dt>收款账户：</dt>
          <dd>
          	<#list seller.sellerChannelList as sellerChannel >
					<#assign userAccountNoShow="">
					<#if sellerChannel.accountNumber?exists>
				      	<#assign userAccountNo=sellerChannel.accountNumber>
				      	<#assign userAccountNoBefore=sellerChannel.accountNumber?substring(0,4)>
				      	<#assign userAccountNoAfter=sellerChannel.accountNumber?substring(sellerChannel.accountNumber?length-4)>
				      	<#assign userAccountNoShow=userAccountNoBefore+"*******"+userAccountNoAfter>
			       </#if>
					${sellerChannel.accountName?if_exists}(${userAccountNoShow?if_exists})
					<input type="text" value="#{sellerChannel.id}" name="trans.sellerChannelId" style="display:none;" />
			</#list>
          <!--
      		 <select name="trans.sellerChannelId" class="existing_event"  tableindex="4">
				<#list seller.sellerChannelList as sellerChannel >
					<#assign userAccountNoShow="">
					<#if sellerChannel.accountNumber?exists>
				      	<#assign userAccountNo=sellerChannel.accountNumber>
				      	<#assign userAccountNoBefore=sellerChannel.accountNumber?substring(0,4)>
				      	<#assign userAccountNoAfter=sellerChannel.accountNumber?substring(sellerChannel.accountNumber?length-4)>
				      	<#assign userAccountNoShow=userAccountNoBefore+"*******"+userAccountNoAfter>
			       </#if>
					<option value="#{sellerChannel.id}" />${sellerChannel.accountName?if_exists}(${userAccountNoShow?if_exists})<br/>
				</#list>
			</select>-->
          </dd>
        </dl>
        
        
          <#assign pre=seller.sellerChannelList[0].sellerRule.fftPointsRule >
			 <input type="hidden" id="preRatio" value="${pre}"/>
			 
        
         <!-- 支付方式：-->
        <div class="ml145 mt5"> 
          <a href="javascript:checkAndSubmit()" id ="senda"  >
          	<div id="send"  class="textBtn"><B>确定</B></div> 
          </a> 
        </div>               
    </div>
	</form>
  </div>
  </div>
</body>
</html>
