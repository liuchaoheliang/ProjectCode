<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通-积分提现</title>
<#include "/WEB-INF/templates/common/include.ftl">

<link href="${base}/template/web/css/inforbox.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/rightimg.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/explain.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/form.js"></script>
<!--
<script type="text/javascript" src="${base}template/common/js/closeDialog.js"></script>
20130606 注释该js无法正确引用
-->
<script type="text/javascript">

//将手机号码修改为不完全显示


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
	  "请正确输入积分数，例如：10，20，130",
	  "请正确输入手机号或邮箱",
	  "对不起，您的积分不够"
  ]	
  


function checkAndSubmit1() {
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
			if (oc.id == "username" || oc.id == "password" ||  oc.id == "phone" || oc.id == "phone2" || oc.id == "people" || oc.id =="seller") {
				oc.className = 'errorText'
			} else if (oc.id == "phonecode") {
				oc.className = 'errorText3'
			} else if (oc.id == "cash") {
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
		checkAndSubmit();
	}
}




function checkAndSubmit(){
	var channelId=document.getElementById("channelId").value;
	if(channelId=="0" || channelId==0)
		return ;
	exch.submit();
	
}

</script> 
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
<div class="miss" style="display:none" id="show">
	<div id="errorMsg"><h2></h2></div>
 
	<a href="#" onclick="closed()"></a>
</div>
<#if errorMsg?exists>
<div class="miss">
	<div id="errorMsg"><h2>${errorMsg?if_exists} </h2></div>
	<a href="#" onclick="closed()"></a>
</div>
</#if>


<div class="box1010 pt10 clear">
  <#if pager??>
<!-- 充值开始 exchange_currency2 transCarry -->
<form id="exch" name="exch" method="post" action="confirmExch.action">
	<input type="hidden" name="trans.sellerId" value="${pager.sellerId?c}"/>
	<input type="hidden" name="transDetails.goodsRackId"  value="#{pager.id?if_exists}" />
   <input type="hidden" name="trans.payMethod"  value="02">
  <div class="put fl">
  
    <div class="stepfen"></div>
     <div class="step">
      <li class="colorRed step07">输入积分</li>
      <li class="step02">核对信息</li>
      <li class="step03">申请完成</li>
    </div>    
  <#if (Session?exists && Session.isMerchantFlag?exists && Session.isMerchantFlag=="0") >
   <table id="formlist">
  <tr>
    <th>您的分分通积分余额为：</th>
    <#if pointsAccount??>
    	<td class="redFont"><b id="max">${(pointsAccount.points)!"0"}</b></td>
    	<#else>
    	<td class="redFont"><b id="max">0</b></td>
    </#if>
    </tr>
  <tr>
    <th>您的手机号为：</th>
    <td id="mobilNum"><#if user?exists>${user.mobilephone?if_exists}</#if></td>
  </tr>
  <tr>
    <th>提现积分：</th>  
    <td class="validate"><strong><input id="cash" type="text" name="transDetails.goodsNumber" class="loginText4" maxlength="5"><u>*</u></strong></td>
  </tr>
  	
  
  <tr>
  
   
    
    <th>银行卡账号：</th>
      <td>
      ${accountNo!""}
      (珠海农商银行)</td>
       <input type="hidden" id="channelId" name="trans.channelId"  value=<#if userCert?exists>${userCert.channelId?if_exists}<#else>0</#if>>
  </tr>
    <#if !userCert?exists>
	  <tr>
	    <th>&nbsp;</th> 
	    <td  class="grayFont f12">还未绑定银行账户？ <a href="binding_bank_account_page.action" class="linkTopList">立即绑定</a></td>
	  </tr>
   </#if>
   </table> 
   <#if userCert?exists>
     <div class="w150 abtn">
     	<!-- <a href="javascript:checkAndSubmit()" onclick="empty()"><div  class="textBtn"><B>下一步</B></div></a>   -->
     	
     	<a href="javascript:checkAndSubmit1()" id="senda" >
          <div id="send"  class="textBtn"><B>下一步</B></div> 
          </a>  
     </div> 
     </#if>
   <#else>
     <table>
     <tr style="height:140px; background:#fdf8db;">
	    <td style="text-align:center;">您好！商家不能进行提现操作。</td>
	  </tr>
   </table>
  </#if>
      <!--<script type="text/javascript" src="${base}/template/web/js/cashcheck.js"></script>-->
    <div class="explain">
      <li>1、提现积分数为整数，且为10的整数倍，提现手续费按5%收取，最低1元，最高50元。</li>
    </div> 
  </div> 
  </form>
  
   </#if>
<!--充值结束-->

   <#include "/WEB-INF/templates/common/exch_right.ftl">                  
</div>

  </div>
<!--底部开始-->  
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
<script type="text/javascript">
try{
	var topNum = $("#mobilNum").html().substring(0,3);
	var endNum = $("#mobilNum").html().substring(8)
	$("#mobilNum").html(topNum+"*****"+endNum);
}catch(e){

}
	
</script><!--修改用户手机号码不完全显示-->
</body>
</html>
