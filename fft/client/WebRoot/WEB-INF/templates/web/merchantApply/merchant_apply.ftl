<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商户在线申请</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/web/css/table.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/rightimg.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/explain.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/select.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/select.js"></script>
<script type="text/javascript" src="${base}/template/common/js/form.js"></script>
<link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>
<script type="text/javascript" src="${base}/template/web/js/rightImage.js"></script>

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
    height: 360px;
    line-height: 20px;
    overflow-y: scroll;
    padding: 10px;
    text-align: left;
    width: 586px;
}

</style>
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
  <div class="tableList fl validate" id="formlist">
  <form id="merchantApplyForm" name="merchantApplyForm" method="post" action="${base}/merchant_apply.action">
   <table cellpadding="0" cellspacing="0">
	  <tr>
	    <th>合作商户名：</th>
	    <td><strong><input type="text" name="merchantOnlineApply.merchantName" class="loginText" maxlength="30" id="seller"><u>*</u></strong></td>
	    </tr>
	  <tr>
	    <th>联系人：</th>
	    <td><strong><input type="text" name="merchantOnlineApply.linkman" class="loginText" maxlength="16" id="people"><u>*</u></strong></td>
	  </tr>
	  <tr>
	    <th>手机：</th>  
	    <td><strong><input name="merchantOnlineApply.mobile" type="text" class="loginText" id="phone" maxlength="11"><u>*</u></strong></td>
	  </tr>
	  <tr>
	    <th>地址：</th>  
	    <td><input name="merchantOnlineApply.address" type="text" class="loginText" id="address" maxlength="50"></td>
	  </tr>
	  <tr>
	    <th>行业：</th>
	     <td >
	     	<span class="ml5" id="uboxstyle">
	            <select name="merchantOnlineApply.mechantIndustryId"  id="language_mac">
	              <#list merchantIndustryList as merchantIndustry>
			        	<option value="${(merchantIndustry.id)?c}">${(merchantIndustry.name)!""}</option>
			        </#list>
	            </select>
            </span>
     	</td>
	  </tr>
	  <tr>
	    <th>意向合作模式：</th>    
	  	<td class="link"><input type="radio" name="merchantOnlineApply.cooperationModel" value="1" checked>分分通<a href="javascript:void(0);" onclick="javascript:showFFTWindow();">查看介绍</a></br><input type="radio" name="merchantOnlineApply.cooperationModel" value="2">直接优惠<a href="javascript:void(0);" onclick="javascript:showZJYHWindow();">查看介绍</a></br><input type="radio" name="merchantOnlineApply.cooperationModel" value="3">其他</td>
	  </tr>
	  <tr>
	    <th>合作意向：</th>      
	    <td ><textarea style="resize:none;" name="merchantOnlineApply.cooperativePurpose" maxlength="800" cols="" rows="" class="textArea500"></textarea></td>
	  </tr>
	  <tr>
	    <td colspan="2" class="pt30 pl30">珠海农商银行分分通平台市场合作联系方式：</td>      
	  </tr>
	  <tr>
	    <th>联系人：</th>      
	    <td >分分通平台市场部</td>
	  </tr>
	  <tr>
	    <th>联系电话：</th>      
	    <td >0756-3827999</td>
	  </tr>
	  <tr>
	    <th>邮箱：</th>      
	    <td >tianmin@f-road.com.cn</td>
	  </tr>
<!--	<tr>
	    <td colspan="2" class="f12 grayFont  pl30">注：商户提交以后，以邮件形式发送到相关人员邮箱中，同时从管理平台也有相关的查询页面。</td>      
	  </tr>  -->
  </table>  
     <div class="w200 abtn">
     <a href="#"><div id="tjsq" class="textBtn"><B>提交申请</B></div></a>  
   </div> 
   </form>   
  </div> 
<!--充值结束-->

  <div class="fl ml10">
    <div class="rightImg">
   	  <a href="exchange_local_list.action"><img src="${base}/template/web/images/right001.png"></a>
      <a href="exchange_bankPoint_list.action"><img src="${base}/template/web/images/right007.png" ></a>
      <a href="exchange_lottery_list.action"><img src="${base}/template/web/images/right005.png"></a>
      <a href="exchange_telephonefee_list.action"><img src="${base}/template/web/images/right002.png"></a>
      <!--<a href="chargeMoney_page.action"><img src="${base}/template/web/images/right003.png"></a>
      <a href="alipay_page.action"><img src="${base}/template/web/images/right004.png"></a>
      <a href="exchange_specialty_list.action"><img src="${base}/template/web/images/right006.png"></a>       -->
    </div>
  </div>                  
</div>

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
$("#tjsq").click(function(){
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
			if (oc.id == "username" || oc.id == "password" || oc.id == "password2" || oc.id == "phone" || oc.id == "phone2" || oc.id == "people" || oc.id =="seller" || oc.id =="phonemail") {
				oc.className = 'errorText'
			} else if (oc.id == "phonecode") {
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
		merchantApply();
	}		
})

	function merchantApply(){
		var name = $.trim($('#seller').val());
		var linkname = $.trim($('#people').val());
		var mobile = $.trim($('#phone').val());		
		var mechantIndustryId = $.trim($('#language_mac').val());
		if(name == ''){
			$.layer({
			title:['分分通提示您',true],
			time:3,
			area : ['auto','auto'],
			dialog : {msg:'商户合作人不能为空',type : 8}	
		});
			return ;
		}
		if(linkname == ''){
			$.layer({
			title:['分分通提示您',true],
			time:3,
			area : ['auto','auto'],
			dialog : {msg:'联系人不能为空',type : 8}	
		});
			return ;
		}
		if(mobile == ''){
			$.layer({
			title:['分分通提示您',true],
			time:3,
			area : ['auto','auto'],
			dialog : {msg:'手机号码不能为空',type : 8}	
		});
			return ;
		}
		if(mechantIndustryId == ''){
			$.layer({
			title:['分分通提示您',true],
			time:3,
			area : ['auto','auto'],
			dialog : {msg:'请选择行业',type : 8}	
		});
			return ;
		}
	
		$("#merchantApplyForm").submit();
	}
	
	//分分通合作介绍
	function showFFTWindow() {
		var agreementWindowHtml = '<div id="agreementWindowContent" class="agreementWindowContent" style="overflow:auto;width:650px; height:300px;" ></div>';
		
//		$.dialog({
//			title: "分分通合作介绍", 
//				content: agreementWindowHtml, 
//				id: "agreementWindow", 
//				className: "agreementWindow", 
//				width: 620, 
//				modal : true
//				});	
			
			
			$.layer({
			   title:['分分通合作介绍',true],
			    area : [720,400],
			    dialog : {
			        msg:agreementWindowHtml,
			        btns : 2, 
			        type : 2,
			        btn : ['确定','取消'],
			        yes : function(index){
			            layer.close(index);
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
				data:{"agreement.type": "1"},
				beforeSend: function(data) {
						$agreementWindowContent.html('加载中...');
				},
				success: function(data) {
					$agreementWindowContent.html(data);
				}
		}); 
		
	}
	
	//直接优惠合作介绍
	function showZJYHWindow() {
		var agreementWindowHtml = '<div id="agreementWindowContent" class="agreementWindowContent" style="overflow:auto;width:650px; height:300px;"></div>';
		
//		$.dialog({
//			title: "直接优惠合作介绍", 
//				content: agreementWindowHtml, 
//				id: "agreementWindow", 
//				className: "agreementWindow", 
//				width: 620, 
//				modal : true
//				});	
			
			$.layer({
			   title:['分分通合作介绍',true],
			    area : [720,400],
			    dialog : {
			        msg:agreementWindowHtml,
			        btns : 2, 
			        type : 2,
			        btn : ['确定','取消'],
			        yes : function(index){
			            layer.close(index);
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
				data:{"agreement.type": "2"},
				beforeSend: function(data) {
						$agreementWindowContent.html('加载中...');
				},
				success: function(data) {
						$agreementWindowContent.html(data);
				}
		}); 
	}
	
	
</script>
</div>
</body>
</html>
