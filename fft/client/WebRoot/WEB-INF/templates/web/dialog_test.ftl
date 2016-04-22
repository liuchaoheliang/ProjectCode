<html>
<head>
<#include "/WEB-INF/templates/common/include.ftl">

<link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>

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
    height: 260px;
    line-height: 20px;
    overflow-y: scroll;
    padding: 10px;
    text-align: left;
    width: 486px;
}
.loginWindow .dialogContent {
    margin: 0;
    padding: 10px;
}
.loginWindow table {
    width: 100%;
}
.loginWindow th {
    font-weight: normal;
    line-height: 35px;
    padding-right: 10px;
    text-align: right;
    width: 100px;
}
.loginWindow td {
    line-height: 35px;
    text-align: left;
}
</style>

<script type="text/javascript"> 
$().ready(function() {
	
	var $body = $("body");

	var $test = $("#test");
	var $test1 = $("#test1");
	var $test2 = $("#test2");
	var $test3 = $("#test3");
	var $test4 = $("#test4");
	var $test5 = $("#test5");
	
	var $registerWindowShowAgreementWindow = $("#registerWindowShowAgreementWindow");

	$.showLoginWindow = function(redirectUrl) {
		var loginWindowHtml = '<form id="loginWindowForm"> '
								+'<table> '
									+'<tr> '
										+'<th>username: </th> '
										+'<td> <input type="text" id="loginWindowMemberUsername" name="member.username" class="formText" /> </td> '
									+'</tr>'
									+'<tr> '
										+'<th>password: </th> '
										+'<td> <input type="password" id="loginWindowMemberPassword" name="member.password" class="formText" /> </td> '
									+'</tr>' 
								+'</table> </form>';
							$.dialog( {
								title : "登录",
								content : loginWindowHtml,
								ok : "确  定",
								cancel : "取  消",
								id : "loginWindow",
								className : "loginWindow",
								width : 420,
								okCallback : login,
								modal : true,
							});

		function login() {
			alert("点击OK后执行 login 方法");
		}
	}

	$test.click( function() {
		$.showLoginWindow();
		return false;
	}) 



	$.showAgreementWindow = function() {
		var agreementWindowHtml = '<div id="agreementWindowContent" class="agreementWindowContent"></div>';

		function agreeAgreement() {
			var $registerWindowIsAgreeAgreement = $("#registerWindowIsAgreeAgreement");
			$registerWindowIsAgreeAgreement.attr("checked", true);
		} 

		$.dialog({
			title: "注册协议", 
				content: agreementWindowHtml, 
				ok: "同 意", 
				cancel: "取 消", 
				id: "agreementWindow", 
				className: "agreementWindow", 
				width: 520, 
				okCallback: agreeAgreement
				});	
			
			
		var $agreementWindowContent = $("#agreementWindowContent");
			
		$.ajax({
				url:"dialog_agreement.action",
				datatype: "html",
				beforeSend: function(data) {
						$agreementWindowContent.html('加载中...');
				},
				success: function(data) {
						$agreementWindowContent.html(data);
				}
}); 
		
	}

	$test1.click( function() {
		$.showAgreementWindow();
		return false;
	})
		


	$.showSuccessWindow = function() {
		$.dialog({
			title:"分分通",
			type: "success", 
			content: '操作成功！', 
			modal: true, 
			});
	}

	$test2.click( function() {
		var dcontent="d content!"
		$.showSuccessWindow();
		return false;
	}) 
	
	
	
	$.showErrorWindow = function(dcon) {
		
		$.dialog({
		title : "登录",
			type: "error", 
			content: dcon, 
			//autoCloseTime: 5000 
			});
	}

	$test3.click( function() {
		$.showErrorWindow('dcon');
		return false;
	}) 
	
	
	
	$test4.click( function() {
		$.cookie("test_cookie","http://test/test");
		return false;
	}) 
	
	$test5.click( function() {
		var cookie_value=$.cookie("test_cookie");
		alert(cookie_value);
		return false;
	}) 
	
	$.showErrorWindow('dcon');
	
});

/**
*说明
*使用时要引入dialog_common.css和dialog.js
*
*	$.dialog( {
*		title : "登录",
*		content : loginWindowHtml,
*		ok : "确  定",
*		cancel : "取  消",
*		id : "loginWindow",
*		className : "loginWindow",
*		width : 420,
*		height : 300,
*		type ："warn",
*		okCallback : login,
*		cancelCallback : cancel,
*		modal : true,
*		autoCloseTime : 5000
*		});
*
*title          弹出层的标题
*content		弹出层具体内容
*ok				弹出层是否有确定按钮，按钮上的文字
*cancel			弹出层是否有取消按钮，取消上的文字
*id				弹出层ID名称
*className      弹出层的 base类名  最后生成如 class="baseDialog loginWindow"
*width			弹出层宽度
*height			弹出层高度
*type			弹出层类型 warn|success|error
*okCallback		弹出层点击确定后执行的方法
*cancelCallback	弹出层点击取消后执行的方法
*modal			弹出层弹出后背景是否有遮照
*autoCloseTime	弹出层自动消失时间 如 5000就是5秒后自动消失
**/


</script>



</head>

<body>
<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="test" href="javascript:void(0);">弹出一个登录(背景操作禁用)</a>
<br>
<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="test1" href="javascript:void(0);">弹出一个注册协议，同意后选中</a>
<input id="registerWindowIsAgreeAgreement" type="checkbox" value="true" name="isAgreeAgreement">
<br>
<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="test2" href="javascript:void(0);">弹出一个带标题的成功提示</a>
<br>
<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="test3" href="javascript:void(0);">弹出一个不带标题的错误提示(5秒后自动关闭)</a>
<br>
<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="test4" href="javascript:void(0);">设置Cookie</a>
<br>
<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="test5" href="javascript:void(0);">获取Cookie</a>
</body>
</html>