$(document).ready(function (){
	var index = -1;
	var linkAction = [
	                  'cashdesk_info.action',
	                  'home.action',
	                  'message_manage.action',
	                  'merchant_product_list.action',
	                  'merchant_photo_list.action',
	                  'merchant_preferential_list.action',
	                  'query_trans.action',
	                  'query_exchange_trans.action',
	                  'query_group_trans.action',
	                  'query_trans_finance.action',
	                  'query_exchange_trans_finance.action',
	                  'query_group_trans_finance.action',
	                  'merchant_complaint_list.action',
	                  'toAuthentication.action',
	                  'toChangPasswordMerchant.action',
	                  //'to_add_clerk.action',
	                  'merchant_clerk_list.action',
	                  'merchant_treasurer_list.action'
	                  ];
	var getUrl = window.location.href;
	var usedUrl = getUrl.split("/");
	usedUrl = usedUrl[usedUrl.length - 1];
	if(usedUrl == 'query_activity_trans.action'){
		$("#transMeg u").eq(3).attr("class","admincut");
		$("#transMeg u").eq(3).find("a").css("color","#FFF");
		return;
	}
	for(var i = 0 ; i < linkAction.length ; i ++){
		if(usedUrl == linkAction[i]){
			index = i;
			break;
		}
	}
	var evenId = "";
	if(index != -1){
		if(index == 0){
			evenId = "checkStand";
			$("#"+evenId+" u").eq(0).attr("class","admincut");
			$("#"+evenId+" u").eq(0).find("a").css("color","#FFF");
		}else if(index == 1){
			evenId = "baseInfo";
			$("#"+evenId+" u").eq(0).attr("class","admincut");
			$("#"+evenId+" u").eq(0).find("a").css("color","#FFF");
		}else if(index <= 5){
			evenId = "busyMeg";
			$("#"+evenId+" u").eq(index - 2).attr("class","admincut");
			$("#"+evenId+" u").eq(index - 2).find("a").css("color","#FFF");
		}else if(index <= 8){
			evenId = "transMeg";
			if($("#"+evenId+" u").size() == 3){
				$("#"+evenId+" u").eq(index - 9).attr("class","admincut");
				$("#"+evenId+" u").eq(index - 9).find("a").css("color","#FFF");
			}else{
				$("#"+evenId+" u").eq(index - 10).attr("class","admincut");
				$("#"+evenId+" u").eq(index - 10).find("a").css("color","#FFF");
			}
		}else if(index <= 11){
			evenId = "finance";
			$("#"+evenId+" u").eq(index - 9).attr("class","admincut");			
			$("#"+evenId+" u").eq(index - 9).find("a").css("color","#FFF");
		}else if(index == 12){
			evenId = "service";
			$("#"+evenId+" u").eq(0).attr("class","admincut");			
			$("#"+evenId+" u").eq(0).find("a").css("color","#FFF");
		}else if(index == 13 ){
			evenId = "approveCenter";
			$("#"+evenId+" u").eq(0).attr("class","admincut");
			$("#"+evenId+" u").eq(0).find("a").css("color","#FFF");
		}else {
			evenId = "softCenter";
			$("#"+evenId+" u").eq(index - 14).attr("class","admincut");
			$("#"+evenId+" u").eq(index - 14).find("a").css("color","#FFF");
		}
	}
});