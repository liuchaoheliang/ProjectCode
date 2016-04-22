<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>"欢乐金秋"免费领取电影票</title>
<#include "/WEB-INF/templates/common/include.ftl">
<script type="text/javascript" src="${base}/template/web/ticket/ticket.js"></script>
<link href="${base}/template/web/ticket/ticket.css" rel="stylesheet" type="text/css" />
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


<!--活动开始-->
<table width="1083" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="${base}/template/web/ticket/00_01.jpg" width="1087" height="91" /></td>
  </tr>
  <tr>
    <td><img src="${base}/template/web/ticket/00_02.jpg" width="1087" height="76" /></td>
  </tr>
  <tr>
    <td><img src="${base}/template/web/ticket/00_03.jpg" width="1087" height="79" /></td>
  </tr>
  <tr>
    <td><img src="${base}/template/web/ticket/00_04.jpg" width="1087" height="97" /></td>
  </tr>
  <tr>
    <td><img src="${base}/template/web/ticket/00_05.jpg" width="1087" height="97" /></td>
  </tr>
  <tr>
    <td><img src="${base}/template/web/ticket/00_06.jpg" width="1087" height="115" /></td>
  </tr>
</table>
<table width="1025" border="0" align="center" cellpadding="0" cellspacing="0" class="abc">
  <tr>
    <td><img src="${base}/template/web/ticket/9_01.gif" width="775" height="139" /></td>
    <td width="242" rowspan="4" valign="top" class="nogif">
    
    <table width="75%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="54">&nbsp;</td>
        </tr>
    </table>
    <table width="75%" border="0" align="center" cellpadding="0" cellspacing="0" class="tableth">
      <tr>
        <td width="41%">姓名</td>
        <td width="59%">卡号</td>
        </tr>
    </table>
    <table width="75%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="16"></td>
        </tr>
    </table>
    
   <div class="scroll">  
    <table border="0" cellpadding="0" cellspacing="0" class="tablemarquee">
      <#if infoList?exists && (infoList?size != 0)>
      	<#list infoList as list>
	      <tr>
	        <td width="41%">${(list.accountName)!""}</td>
	        <td width="59%">${(list.identificationCard)!""}</td>
	      </tr>
      	</#list>      	
      <#else>
		<div style="font:12px/30px '宋体';text-align:center;">
      		暂无中奖名单，敬请期待
      	</div>
      </#if>                   
  </table>
</div>

  <div class="quanbu"><a href="allUserInfo.action" target="_blank">查看全部&gt;&gt;</a></div>
    </td>
  </tr>
  <tr>
    <td><img src="${base}/template/web/ticket/9_02.gif" width="775" height="133" /></td>
  </tr>
  <tr>
    <td><img src="${base}/template/web/ticket/9_03.gif" width="775" height="153" /></td>
  </tr>
  <tr>
    <td width="775"><img src="${base}/template/web/ticket/9_04.gif" width="775" height="143" /></td>
  </tr> 
  
</table>

<table width="1050" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td  height="120" align="center"> <!--id="toGetTicket" --><a id="toGetTicket"  style="cursor:pointer;" href="javascript:void(0)"><img src="${base}/template/web/ticket/11_11.jpg" width="442" height="83" /></a></td>
  </tr>
</table>
<!--活动结束-->

<script type="text/javascript" > 
function autoScroll(obj){  
    $(obj).find(".tablemarquee").animate({  
        marginTop : "-30px"  
    },500,function(){  
        $(this).css({marginTop : "0px"}).find("tr:first").appendTo(this);  
    })  
}  
$(function(){  
    setInterval('autoScroll(".scroll")',1000)  
})  
</script>
<script type="text/javascript" > 

//验证表单数据并提示
$("#toGetTicket").click(function(){		
		$.ajax({
		url:'isLogin.action',
		dataType:'xml',
		type:'post',
		success:function (data){
			jsonObj = eval($(data).find('root').text());
			if(jsonObj[0].state == '01'){
				//请登录
				window.location.href="toLogin.action";
			}else if(jsonObj[0].state == '02'){
				//登录角色为商户
				$.layer({
					title :['分分通提示您',true],
					area : ['auto','auto'],
					dialog : {msg:'对不起，商户不能参与该活动。',type : 8}	
				});
			}else if(jsonObj[0].state == '04'){
			//活动未开起
				$.layer({
					title:['分分通提示您',true],
					area : ['auto','auto'],
					dialog : {msg:jsonObj[0].msg,type : 9}	
				});
			}else if(jsonObj[0].state == '03'){
				var htmlElement ='<div class="validate" id="formlist" ><p>*若您满足参与活动的条件，请填写您的珠海农商银行账户信息验证！</p><form name="formregedit" action="#" method="post"><dl><dt>银行卡号：</dt><dd><div><strong><input type="text" class="loginText" id="userbankCard" value="请输入您常用的珠海农商银行卡号" maxlength="19"  /><u>*</u></strong></div></dd></dl><dl><dt>姓名：</dt><dd><div><strong><input type="text" class="loginText" id="userName" value="请输入您的中文姓名" maxlength="4" /><u>*</u></strong></div>';
                    htmlElement += '</dd></dl><dl><dt>证件号码：</dt><dd><div><strong><input type="text" class="loginText" id="userIdCard" value="请输入您的证件号码" maxlength="18" /><u>*</u></strong>';
                    htmlElement += '</div></dd></dl>';
                    htmlElement += '</form></div>';
				 $.layer({
				   title:['请填写您的信息',true],
				    area : ['530','auto'],
				    dialog : {
				        msg:htmlElement,
				        btns : 2, 
				        type : 9,
				        btn : ['确定','取消'],
				        yes : function(index){
				        		 var spanerror = $("<span><h3>不能为空</h3></span>");
								 $("input[type='text']").each(function(){
									 if($(this).val() == this.defaultValue){
										 $(this).parent().find("span").remove()
										 $(this).parent().append(spanerror)	
										 $(this).addClass("errorText");	
										 return false;
										}
								   });
								 var subm = $('h3').length;  	
								  if(subm){
										return;
									} else{
									var userName = $('#userName').val();
									var userId = $('#userIdCard').val();
									var userBank = $('#userbankCard').val();
									if(confirm){
										
									}
					 			$.ajax({
					 				url:'togetTicket.action',
					 				type:'post',
					 				data:{'user.accountName':userName,'user.accountNumber':userBank,'user.identificationCard':userId},
					 				dataType:'xml',
					 				success:function(data){
					 					jsonObj = eval($(data).find('root').text());
					 					if(jsonObj[0].state == 'true'){
					 						var htmlyingyuan = jsonObj[0].html;					 						
											$.layer({
											   title:['<span style="color:red">*请选择电影院</span>',true],
											   area : ['660','auto'],
											   dialog : {
											        msg:htmlyingyuan,
											        btns : 2, 
											        type : 7,
											        btn : ['确定','取消'],
											        yes : function(index){
											        	var merchantId = $("#merchantId").val();
											        	layer.close(index);
											 			$.ajax({
											 				url:'getTicket.action',
											 				type:'post',
											 				data:{'user.accountName':userName,'user.accountNumber':userBank,'user.identificationCard':userId,'mertchatId':merchantId},
											 				dataType:'xml',
											 				success:function(data){
											 					jsonObj = eval($(data).find('root').text());
											 					if(jsonObj[0].state == 'login'){
																	//请登录
																	window.location.href="toLogin.action";
																}else if(jsonObj[0].state == 'success'){
																$.layer({
																		area : ['500','auto'],
																		dialog : {msg:'<div class="divright"><span><b>领取成功</b></span><P>· 系统已下发包含兑换码的短信，请凭此兑换码到影院去换取影票；</P><P>· 活动截止日期为<font style="color:red;">10月31日</font>，请及时消费，祝您观影愉快！</P><P>· 若您遗忘兑换码，可到我的活动中查询或者发送至手机。</P></div>',type : 7}	
																	});
																  
																}else if(jsonObj[0].state == 'error'){
																	layer.msg('系统繁忙，请稍后再试！',4,8);
																}else if(jsonObj[0].state == 'used'){
																	$.layer({
																		title:['分分通提示您',true],
																		time:3,
																		area : ['auto','auto'],
																		dialog : {msg:'您已领取该兑换券！',type : 8}	
																	});
																}else if(jsonObj[0].state == 'caution'){
																	layer.msg('数据错误，请勿更改！',4,8);
																}
											 				}
											 			});
											        },
											        no : function(index){
											        	layer.close(index);
											        }
												}
											});
					 					}else if(jsonObj[0].state == 'false'){
					 						//输入信息错误或者没有资格领取
					 						$.layer({
												title:['分分通提示您',true],
												area : ['auto','auto'],
												dialog : {msg:jsonObj[0].msg,type : 8}	
											});
					 					}else if(jsonObj[0].state == 'warm'){
					 						//已领取
					 						$.layer({
												title:['分分通提示您',true],
												area : ['auto','auto'],
												dialog : {msg:jsonObj[0].msg,type : 8}	
											});
					 					}else if(jsonObj[0].state == 'overdue'){
					 						$.layer({
												title:['分分通提示您',true],
												area : ['auto','auto'],
												dialog : {msg:jsonObj[0].msg,type : 8}	
											});
					 					}
					 				}	 				
					 			});
					 			/////
					 			}
				        },
				        no : function(index){
				        	layer.close(index);
				        }
					}
				});								
			}
		}
	});	
})
</script>
<!--清除浮动-->
<div class="clear"></div>
<!--清除浮动-->

<!--底部开始-->  
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
</body>
</html>
