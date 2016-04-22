
lance = {
	base : ""
};

$().ready(function() {
	var $body = $("body");

	var $test = $("#test");
	var $test1 = $("#test1");
	var $test2 = $("#test2");
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
								title : "title",
								content : loginWindowHtml,
								ok : "OK",
								cancel : "Cancel",
								id : "loginWindow",
								className : "loginWindow",
								width : 420,
								okCallback : login,
								modal : true
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
				ok: "同意", 
				cancel: "取消", 
				id: "agreementWindow", 
				className: "agreementWindow", 
				width: 520, 
				okCallback: agreeAgreement});	
	}


	$test1.click( function() {
		$.showAgreementWindow();
		return false;
	})
		


	$.showPromptWindow = function() {
		var showPromptWindowHtml = '<div class="promptWindowRight">操作成功！</div>';

		

		$.dialog({
			title: "分分通", 
				content: showPromptWindowHtml, 
				ok: "确定", 
				id: "promptWindow", 
				className: "promptWindow", 
				width: 300, 
				});	
	}


	$test2.click( function() {
		$.showPromptWindow();
		return false;
	}) 
});
							
