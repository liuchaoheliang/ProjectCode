$(document).ready(function (){
	var aLink = $("#menuAClick a");
	var index = -1;
	var action = [
	              'index.action',
	              'presell_index.action',
	              'new_exchange_index.action',//积分返利
	              'queryBeforeExch.action',//积分提现
	              'group_index.action',//积分兑换
	              'rebate_index.action',//团购
	              'preferential_index.action'//直接优惠
	              ];
	var actionRebate = [''];//积分返利action组
	var actionBeforeExch = [''];//积分提现action组
	var actionExchange = [''];//积分兑换action组
	var actionGroup = [''];//团购组action组
	var actionPrefer = [''];//直接优惠action组
	                    
	var getAction = window.location.href;               
	var actionUrl = getAction.split("/");
	var userd = actionUrl[actionUrl.length -1];
	for(var i = 0 ; i < action.length ; i ++){
		if(userd == action[i]){
			index = i ;
			break;
		}
	}
	if(index != -1){
		aLink.eq(index).attr("class","menuClick");
	}
	
});