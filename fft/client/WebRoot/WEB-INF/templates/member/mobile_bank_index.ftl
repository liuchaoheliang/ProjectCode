<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>手机银行卡认证</title>
<#include "/WEB-INF/templates/common/include.ftl">
<script type="text/javascript" src="${base}/template/common/js/form.js"></script>
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/inforbox.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/member/css/text.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/adminexplain.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/closeDialog.js"></script>



<script type="text/javascript"> 
$().ready( function() {

	var $myMsg = $("#myMsg");
			
			var senda = $("#send");
			function clickFn(){
				$('#show').hide();
				senda.attr("href","javascript:void(0);");
				senda.find("div").attr("class","gryBtn");	
				senda.find('B').html("请等待...");
				$(this).unbind("click");
				$.ajax({
		 			url:'mobile_bank_authentication.action',
					type: "POST",
					dataType: "json",
					success: function(data) {
						if (data.status == "success") {
								$myMsg.text(data.message);
								$('#show').show();
								$("#isAuth").css('display','none');
								$('#idright').show('display','block');
								return;
							}
							else{
								$myMsg.text(data.message+"，请您到珠海农商银行办理手机银行卡");
								$('#show').show();
							} 
							$("#send").click(clickFn);
							senda.find("div").attr("class","textBtn");	
					        senda.find('B').html("手机银行卡认证");
									        
					}
				});
			}
			
			$("#send").click(clickFn);
});	
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

<!--广告栏开始-->
<div class="ad"><img src="${base}/template/member/img/123213.png"></div>
<!--广告栏结束-->

  <!--错误提示开始-->
	<div class="miss" id="show" style="display:none;">
	 <h2 id="myMsg"></h2>
	 <a href="#" onclick="closed()"></a>
	</div>
<!--错误提示结束-->

<div class="middleBox">
<!--会员个人信息菜单开始-->
<#include "/WEB-INF/templates/common/user_manage_menu.ftl">
<!--会员个人信息菜单结束-->


  <div class="rightBox" id="rightHeight">
      
      <div class="inforBox validate" id="formlist">                              
        <div class="ml145 mt5" > 
        <#if isMobileBank=="true">
        	<dl>
	          <dt>手机号码：</dt>
	          <dd>
	            <div>${mobile!""} <span id="idright">手机银行卡已认证</span></div>
	          </dd>
        	</dl>
        <#else>
		     <dl>
		        <dt>手机号码：</dt>
		        <dd>
		          <div>${mobile!""} <span id="idright" style="display:none;">手机银行卡已认证</span></div>
		        </dd>
		     </dl>
		     <div style="margin-left:65px;" id="isAuth">
		     	 <a href="javascript:void(0);" id="send">
	          		<div   class="textBtn"><B>手机银行卡认证</B></div>
	          	   </a> 
        	 </div>
        </#if>
         
          
        </div>
        
        
      </div>
       <div class="adminexplain">
	     <dl>
	      <dt>为何需要进行手机银行卡认证？</dt>
	      <dd>•若您购物时选择现金支付，账单将推送到您的手机银行卡上，非手机银行卡用户将无法完成现金支付。因此若选择支付现金，请先进行手机银行卡认证</dd>
	      <dt>为什么会提示认证失败？</dt>
	      <dd>•认证失败，表示您还未到珠海农商银行办理过手机银行卡<a  href="http://www.zhnsb.com.cn/service/branches/" target="_blank">点击查看办理网点</a></dd>
	      <dt>办理手机银行卡有什么好处？</dt>
	      <dd>•无需上网，彻底避免网上银行的安全漏洞，更安全</dd>
		  <dd>•不用跑银行，可随时随地自助办理转账、汇款、缴费、查询等业务</dd>
		  <dd>•安全快捷的网购，网上购物，线下支付</dd>
		  <dd>•随身携带，二十四小时随时随地办理银行业务<a href="http://mobile.zhnsb.com.cn/html/sub-cptd.html" target="_blank" >点击查看详细介绍</a></dd>
	      </dd>
	     </dl>
    </div>      
  </div>
</div>

<script type="text/javascript" src="${base}/template/common/js/height.js"></script>

<!--底部开始-->
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
 	
</body>
</html>
