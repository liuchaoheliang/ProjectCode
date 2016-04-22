$(document).ready(function (){
	var timeOut;
	var arg = 4;
	var bodyElem = $("body");
	var divCss="display: none;position: absolute;z-index: 999;top: 0px; left: 0px; background-color: #ccc;filter:alpha(Opacity=80);-moz-opacity:0.8;opacity: 0.6;width:"+bodyElem.width()+"px;height:"+bodyElem.height()+"px;";
	bodyElem.append("<div id=\"waiting\" style=\""+divCss+"\"><img id=\"imgwaiting\" style=\"position: absolute;\" src=\"template/web/img/loading.gif\" ></center></div>");	
	$("#imgwaiting").css("top","30%");	
	$("#imgwaiting").css("left",(bodyElem.width()-$("#imgwaiting").width())/2);	
	var expAction = ['expRebateFinance.action','expExchangeFinance.action','expGroupFinance.action','expPresellFinance.action'];
	var initAction = ['query_trans_finance.action','query_exchange_trans_finance.action','query_group_trans_finance.action','query_presell_trans_finance.action'];
	
	var isDownload = true;
	var expHref = $(".download");	//获取链接控件
	var form = $("#listForm");//获取表单
	expHref.click(subForm);//绑定事件
	var formAction = form.attr("action");

	var listVal = $.trim($("#resultListVal").text());
	if(listVal == "没有找到交易!"){//如果搜索条件返回空记录，则隐藏导出报表按钮
		isDownload = false;
	}
	
	function subForm(){				
		if(isDownload){
           	if(formAction == initAction[0]){
				form.attr("action",expAction[0]);//为导出积分返利页面在引用该js
			}else if(formAction == initAction[1]){
				form.attr("action",expAction[1]);//为导出积分兑换在引用该js
			}
            else if(formAction == initAction[2])
            {
                form.attr("action",expAction[2]);
            }
            else{
				form.attr("action",expAction[3]);
			}
			//if($("#txtStartDate").val() == '' || $("#txtEndDate").val() == ''){
			//	alert("下载报表需要选择具体的开始和结束时间");
			//}else{
				form.submit();
				$("#waiting").css("display","block");	
				timeOutFn();
			//}
			
			$("#listForm").attr("action",formAction);//还原表单原始action
		}else{
			$.layer({
				title:['分分通提示您',true],
				time:3,
				area : ['auto','auto'],
				dialog : {msg:'当前页面无结果，无需导出',type : 8}	
			});
		}		
	}
	function timeOutFn(){
		timeOut = setTimeout(function (){
			arg --;
			if(arg < 0){
				$("#waiting").css("display","none");
				clearTimeout(timeOut);
				arg = 2;								
			}else{
				timeOutFn();
			}			
		}, 1000);
	}
});