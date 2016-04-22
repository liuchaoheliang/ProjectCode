<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员信息管理</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/member/css/imglist.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/member/css/welcome.css" rel="stylesheet" type="text/css" />
<script src="${base}/template/common/My97DatePicker/WdatePicker.js"></script>
<link href="${base}/template/member/css/point.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
//$().ready( function() {
//	var flag=${flag};
//	if(flag){
//		$.dialog({title: "分分通",type: "warn", content: '<a href="mobile_bank_index.action"  style="color:#F00;">进入手机银行卡认证</a>', modal: true, autoCloseTime: 5000});
//	}
//});
$(window).load(function(){
	var flag=${flag};
	if(flag){
		//$.layer({
		//	title:['分分通提示您',true],
		//	time:3,
		//	area : ['auto','auto'],
		//	dialog : {msg:'<a href="mobile_bank_index.action"  style="color:#F00;">进入手机银行卡认证</a>',type : 9}	
		//});
		$('#cardRenzheng').addClass('cardphone');
		$('#cardRZpic').show();
	}else{
		$('#cardRenzheng').removeClass("cardphone");
		$('#cardRZpic').hide();
	}
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

<div class="middleBox">
<!--会员个人信息菜单开始-->
<#include "/WEB-INF/templates/common/user_manage_menu.ftl">
<!--会员个人信息菜单结束-->

<div class="rightBox imgList" id="rightHeight">
 <div class="point"  style="height:45px;" ><span>您好，<u id ="uName"></u>，您目前分分通积分为：<B id="fftRightPoint"></B>分，银行积分为：<b id="bankRightPoint"></b>分</span></div>
  <div class="wel">
    <a href="${base}/rebate_trans_list.action" class="w01">积分返利</a>
    <a href="${base}/exchange_trans_list.action" class="w02">积分兑换</a>
    <a href="${base}/drawCash_trans_list.action" class="w03">积分提现</a>
    <a href="${base}/group_trans_list.action" class="w04">我的团购</a>
    <a href="${base}/presell_trans_list.action" class="w04">精品预售</a>
   <!--   <a href="http://app1.o2obill.com:8080/o2obill_website/member/order/list.jhtml" class="w04">机票订单</a>      -->
    <a href="${base}/binding_bank_account_page.action" class="w12">提现认证</a>
    <a href="${base}/member_favorite_list.action" class="w05">我的收藏</a>
    <a href="${base}/toSuggestion.action" class="w06">意见与建议</a>
    <a href="${base}/complaint_list.action" class="w07">我的投诉</a>
    <a href="${base}/queryUserInfo.action" class="w09">用户信息</a>
<!--  <a href="${base}/toChangeMobilePhone.action" class="w10">手机号修改</a> -->  
    <a href="${base}/toChangPassword.action" class="w11">修改密码</a>
  <!--  <a href="${base}/help_center.action" class="w08">帮助信息</a>--> 
  </div>

  <div>
   <dl>
    <dd class="linkBlack pt10" id="divTab01" style="display:block">
    <#list newMerchantPager.list as merchant>	       	
		<li>
	       <a href="preferential_info.action?id=${(merchant.id)?c}"><img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(merchant.mstoreLogoUrl)!""}" onError="this.src='${base}/template/common/images/moren.gif'"></a>
			<div><a href="merchant_info.action?id=${(merchant.id)?c}">${(merchant.mstoreFullName?html)!""}</a></div>
	       <div class="fanli">折扣优惠：<B>${(merchant.preferentialRate)!""}&nbsp;折</B></div>
	    </li>
	</#list>      
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
