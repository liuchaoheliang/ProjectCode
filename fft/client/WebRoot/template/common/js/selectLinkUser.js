$(document).ready(function (){
	//class="admincut"
	var index = -1;
	var linkAction = [
	                  'rebate_trans_list.action'
	                  ,'exchange_trans_list.action'
	                  ,'drawCash_trans_list.action'
	                  ,'group_trans_list.action'
	                  ,'presell_trans_list.action'
	                  ,'member_favorite_list.action'
	                  ,'member_activity_list.action'
	                  ,'queryUserInfo.action'
	                  ,'toChangPassword.action'
	                  ,'toChangeMobilePhone.action'
	                  ,'binding_bank_account_page.action'
	                  ,'mobile_bank_index.action'
	                  ,'complaint_list.action'
	                  ,'toSuggestion.action'];
	var getUrl = window.location.href;
	var usedUrl = getUrl.split("/");
	usedUrl = usedUrl[usedUrl.length - 1];
	for(var i = 0 ; i < linkAction.length ; i ++){
		if(usedUrl == linkAction[i]){
			index = i;
			break;
		}
	}
	var evenId = "";
		if(index != -1){
		if(index <= 6 && index != -1){
			evenId = "fftSelect";
			$("#"+evenId+" u").eq(index).attr("class","admincut");
			$("#"+evenId+" u").eq(index).find("a").css("color","#FFF");
		}else if(index <= 11){
			evenId = "softCenter";
			$("#"+evenId+" u").eq(index - 7).attr("class","admincut");
			$("#"+evenId+" u").eq(index - 7).find("a").css("color","#FFF");
		}else{
			evenId = 'service';
			$("#"+evenId+" u").eq(index - 12).attr("class","admincut");
			$("#"+evenId+" u").eq(index - 12).find("a").css("color","#FFF");
		}
	}
	//alert(index);
});