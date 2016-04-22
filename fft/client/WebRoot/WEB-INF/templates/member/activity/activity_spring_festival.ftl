<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通查询-我的活动</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/ajax_detail.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/pager.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/select.js"></script>
<script type="text/javascript" src="${base}/template/common/js/list.js"></script>
<script src="${base}/template/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/template/common/js/ajax_detail.js"></script>
<link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>
<link href="${base}/template/web/zhuanpan/zhuanpan.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/web/zhuanpan/lingjiang.js" ></script>
</head>
<body>
<style>
body{background:#ECECEC;}
</style>
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
<!-- 会员个人信息菜单开始 -->
<#include "/WEB-INF/templates/common/user_manage_menu.ftl">
<!-- 会员个人信息菜单结束 -->

<div class="rightBox" id="rightHeight">   
<form id="listForm" action="member_activity_spring.action" method="post">
<div class="clear pt10">
      <table width="99%" border="0" cellspacing="0" cellpadding="0" class="tableA">
        <tr>
          	<th width="15%">活动名称</th>
        	<th width="10%">中奖等级</th>
        	<th width="15%">奖品名称</th>
          	<!--<th width="15%">活动开始时间</th>-->
          	<th width="15%">活动截止日期</th>
          	<th width="15%">券号</th>
          	<th width="10%">状态</th>
          	<th width="6%">操作</th>
        </tr>
  		 <#list pager.list as list>
  		 	<tr>
  		 		<td>${(list.lotteryName?html)!""}</td>
  		 		<td>${(list.lotteryTerm?html)!""}</td>
  		 		<td>${(list.awardInfo?html)!""}</td>
  		 		<!--<td>${(list.startTime?html)!""}</td>-->
  		 		<td>${(list.endTime?html)!""}</td>
  		 		<td>${(list.lotteryWinnerDto.virtualCard.redemptionCodeDecrypt?html)!"-"}</td>
  		 		<#if list.state?exists && list.state=="award">
  		 			<td>已领奖
  		 				<#if list.isConsume?exists && list.isConsume=="0">
  		 				(未消费)
  		 				<#elseif list.isConsume?exists && list.isConsume=="1">
  		 				(已消费)
  		 				</#if> 		 			
  		 			</td>
  		 				<!--只有券才有重发短信的功能  -->
						<#if list.lotteryTerm?exists && (list.lotteryTerm=="一等奖" || list.lotteryTerm=="二等奖" || list.lotteryTerm=="三等奖") >
							<!--若为已消费的券，不需要展示重发短信的按钮  -->
							<#if list.isConsume?exists && list.isConsume=="1">
	  		 					<td>-</td>
	  		 				<#else>
	  		 					<td id="duanxin"><a href="#" onclick="reSendMsg(5,'${(list.lotteryWinnerDtoId)}')" ><img src="${base}/template/member/images/duanxin.png" title="重发验证券短信"></a></td>
	  		 				</#if> 	
  		 				<#else>
  		 					<td>-</td>
  		 				</#if>			
  		 		<#else>
  		 			<td>未领奖</td> 		 			
  		 			<td> 
  		 				<#if list.lotteryTerm?exists && list.lotteryTerm=="特等奖" >
  		 					<a href="#" onclick="reward(1,'${list.lotteryWinnerDtoId}')">领奖</a>
  		 				<#elseif list.lotteryTerm?exists && list.lotteryTerm=="四等奖">
  		 					<a href="#" onclick="reward(5,${list.lotteryWinnerDtoId})">领奖</a>					
  		 				<#else>
  		 					<a href="#" onclick="reward(3,${list.lotteryWinnerDtoId})">领奖</a>
  		 				</#if>
  		 			</td>
  		 			<!--
  		 				<td id="duanxin"><a href="#" onclick="reSendMsg()" ><img src="${base}/template/member/images/duanxin.png" title="重发验证券短信"></a></td>
  		 			-->
  		 		
  		 		</#if>

  		 	</tr>
  		 </#list>
      </table>
    </div>  
    <div class="page">
		<#if (pager.list?size > 0)>
         	<#include "/WEB-INF/templates/common/pager.ftl" />
       <#else>
         		没有找到交易!	
        </#if>
	</div>
	</from>
  </div>
</div>  
<script type="text/javascript" src="${base}/template/common/js/height.js"></script>
<!--底部开始-->
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->   
<script type="text/javascript"> 
	function reSendMsg(num,lotteryWinnerDtoId){	
			$.ajax({
				url : 'aldo_parisot_prize.action',
				type : 'post',
				dataType : 'json',
				data : {'prizeJson':'[{"prizeLevel":"'+(num-1)+'","winnerid":"'+lotteryWinnerDtoId+'"}]'},
				success : function (data){
					$.layer({
							title:'分分通提示您',
							area : ['auto','auto'],
							dialog : {msg:'<div style="color:#333; font:14px/20px Verdana">短信发送成功</div>',type : 9}
						});												
				},
				error : function (){
		
				}
			});	
	}

	function reward(num,lotteryWinnerDtoId){
		if(num == 1){
			//特等奖中奖人领取地址
			var uname="";
			var phone="";
			var idcard="";
			$.ajax({
				url : 'loterry_detail.action?loterryId='+lotteryWinnerDtoId,
				type : 'post',
				dataType : 'json',
				success :function(json){
					uname=json.uname;
					phone=json.phone;
					idcard=json.idcard;
					 var htmlElement ='<div class="validate" id="formlist" ><p>*请填写收货信息，我们将按您提供的地址，以快递或邮寄的形式将奖品寄送出。</p><form name="formregedit" action="#" method="post"><dl><dt>手机号码：</dt><dd><div><strong><input type="text" class="loginText" id="userbankCard" value="'+phone+'" maxlength="11"  /><u>*</u></strong></div></dd></dl><dl><dt>姓名：</dt><dd><div><strong><input type="text" class="loginText" id="userName" value="'+uname+'" maxlength="4" /><u>*</u></strong></div>';
		             htmlElement += '</dd></dl><dl><dt>收货地址：</dt><dd><div><strong><input type="text" class="loginText" id="userIdCard" value="'+idcard+'" maxlength="18" /><u>*</u></strong>';
		             htmlElement += '</div></dd></dl>';
		             htmlElement += '</form></div>';
					$.layer({
						title:'请输入个人信息',
						area : ['530','auto'],
						dialog : {
									msg : htmlElement,
									btns: 1,
									type: 9,
									btn : ['确定','取消'],
									yes : function(index){
											var spanerror = $("<span><h3>不能为空</h3></span>");
											$("#formlist input[type='text']").each(function(){
												 if($(this).val() == ""){
													$(this).parent().find("span").remove()
													$(this).parent().append(spanerror)	
													$(this).addClass("errorText");	
													return false;
												}
											});
											var subm = $('h3').length;  																
											if(subm){
												return;
											}																												
											$.ajax({
												url : 'aldo_parisot_prize.action',
												type : 'post',
												dataType : 'json',
												data : {'prizeJson':'[{"prizeLevel":"t","uname":"'+$("#userName").val()+'","idcard":"'+$("#userIdCard").val()+'","phone":"'+$("#userbankCard").val()+'","winnerid":"'+lotteryWinnerDtoId+'"}]'},
												success : function (data){
														layer.close(index);
														$.layer({
															title:'分分通提示您',
															area : ['auto','auto'],
															dialog : {msg:'<div style="color:#333; font:14px/20px Verdana">'+data.msg+'</div>',type : 9}
														});	
													},
												error : function (){
										
												}
											});
									},
									no: function (){
									
									}
								}
					});
				}
			});			
		}else{
			$.ajax({
				url : 'aldo_parisot_prize.action',
				type : 'post',
				dataType : 'json',
				data : {'prizeJson':'[{"prizeLevel":"'+(num-1)+'","winnerid":"'+lotteryWinnerDtoId+'"}]'},
				success : function (data){
					$.layer({
							title:'分分通提示您',
							area : ['auto','auto'],
							dialog : {msg:'<div style="color:#333; font:14px/20px Verdana">'+data.msg+'</div>',type : 9}
						});												
				},
				error : function (){
		
				}
			});				
		}													
	}
</script>
</body>
</html>
