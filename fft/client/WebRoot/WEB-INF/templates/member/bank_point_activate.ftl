<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>激活积分</title>
<#include "/WEB-INF/templates/common/include.ftl">
<script type="text/javascript" src="${base}/template/common/js/form.js"></script>
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/inforbox.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/member/css/text.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/closeDialog.js"></script>
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

<div class="miss" style="display:none;" id="show">
 <div><h2 id="activatePointId"></h2></div>
 <a href="#" onclick="javascript:closed();"></a>
</div>

<div class="middleBox">
<!--会员个人信息菜单开始-->
<#include "/WEB-INF/templates/common/user_manage_menu.ftl">
<!--会员个人信息菜单结束-->

<form id="tempPointForm">
  <div class="rightBox" id="rightHeight">
      <div class="text">
  		2013年的银行积分可以激活使用啦！分分通上已有超过         
		<b>300,000,000</b>银行积分，快来领取属于您的积分吧！</div>
        <div class="adminexplain" style="margin-top:0px;">
	     <dl> 
      <dd>您是否从未关注过珠海农商银行各种积分的用处呢？您是否为了积分换购商品的类型太少而发愁呢？</dd>
			<dd>从现在起，珠海农商银行的积分可以在分分通使用啦，请填写您在珠海农商银行的卡号、开户名及证件号码激活</dd>
		</dl>
		</div>
      <div class="inforBox validate" id="formlist">
 <!--   <dl>
          <dt>卡号：</dt>
          <dd>
            <div> <strong><input name="tempPoint.accountNumber"  type="text" class="loginText" id="banknumber"><u>*</u></strong></div>
          </dd>
        </dl> -->
        
        <dl>
          <dt>珠海农商银行卡号：</dt>
          <dd>
            <div> <strong><input maxlength="19" name="tempPoint.accountNumber" type="text" class="loginText" id="banknumber" ><u>*</u></strong> </div>
          </dd>
        </dl>  
        
        <dl>
          <dt>账户开户名：</dt>
          <dd>
            <div> <strong><input name="tempPoint.accountName" type="text" class="loginText2" id="adminname"><u>*</u></strong> </div>
          </dd>
        </dl>  
        
        
		
		<dl>
          <dt>证件号码：</dt>
          <dd>
            <div> <strong><input name="tempPoint.identificationCard" type="text" maxlength="18" class="loginText" id="card"><u>*</u></strong></div>
          </dd>
        </dl> 
                               
        <div class="ml145 mt5"> 
          <a href="javascript:void(0);" id="send" >
          <div  class="textBtn"><B>激活积分</B></div>
          </a> 
          
        </div>
        
        <div id="myMsg" class="fail"> </div>
        
      </div>

      <div class="adminexplain">
	     <dl>
	      <dt>为什么我有珠海农商银行积分，但是却提示激活失败呢？</dt>
	      <dd>•请确保您的卡号、户名及证件号码输入正确，若仍然提示激活积分失败，则是因为您的珠海农商银行的银行卡积分不够最低激活标准，最少需要5000积分</dd>
	      <dd>•另外，银行积分不是实时获取的最新数据，本次可激活的积分为2013年度的积分数据，因此若您在这之后达到激活标准，仍然会提示激活失败，我们将会定期更新积分激活数据</dd>
	      <dt>为什么激活积分后我的积分值和原银行积分的积分值不同？</dt>
	      <dd>•我们会按照一定的比例（10000:8）将珠海农商银行的银行卡积分换算成分分通上的银行积分，因此您激活后的积分值会少于原银行积分的积分值</dd>
	      <dd>•若您的多个珠海农商银行账户均达到积分激活的标准，则激活积分时我们会将其全部激活，因此您激活后的积分值为所有符合条件账户的积分值总和</dd>
	      <dt>为什么我的其他类型的银行积分无法完成激活？</dt>
	      <dd>•本年度（2013年度）珠海农商银行的可激活的积分类型为珠海农商银行的银行卡积分，其他种类的银行积分暂时无法激活，请谅解</dd>
	     </dl>
	    </div>   
  </div>
</div>
</form>
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
var sender = $('#send');
$("#send").click(sendMsg);
function sendMsg(){
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
			if (oc.id == "username" || oc.id == "password" || oc.id == "password2" || oc.id == "phone" || oc.id == "phone2" || oc.id == "people" || oc.id =="seller" || oc.id =="phonemail" || oc.id == "card") {
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
	
		sender.attr("href","javascript:void(0);");
		sender.find("div").attr("class","gryBtn");	
		sender.find('B').html("请等待...");
		sender.unbind("click");
				
		var $bankPoints = $("#bankPoints");
		var $xsbankPoint = $("#xsbankPoint");
		var $tempPointForm = $("#tempPointForm");
		var $myMsg = $("#myMsg");
 		var $bankRightPoint = $("#bankRightPoint");
		
		 $.ajax({
				url: "bank_temp_point_Activate.action",
				data: $tempPointForm.serialize(),
				type: "POST",
				dataType: "json",
				cache: false,
				success: function(data) {
					if (data.status == "success") {
						//$(data.message);
						$('#activatePointId').html(data.message);
						$('#show').show();
						$bankPoints.text(data.zhbank);
						$xsbankPoint.text(data.zhbank);
						$bankRightPoint.text(data.zhbank);
					}
					else{
						//$myMsg.text(data.message);
						$('#activatePointId').html(data.message)
						$('#show').show();
					} 
					sender.find("div").attr("class","textBtn");	
				    sender.find('B').html("激活积分");
				    
				    $("#send").click(sendMsg);
				    $("#adminname").val('');
				    $("#card").val('');
				    $('#banknumber').val('');
	 			}
		});
	}		
}

</script>       
</body>
</html>
