		/**
		 * 手机验证码
		 * 1.生成验证码
		 * 2.发送短信验证码
		 * @param o
		 */

		var clear = false;
		function time(o) {
			if (wait == 0) {
				o.removeAttribute("disabled");
				o.value="获取手机验证码";
				wait = 90;
				$("#phonecode").val("");
				//code="11111";
			} else {
				o.setAttribute("disabled", true);
				o.value="重新获取(" + wait + ")";
				wait--;
				setTimeout(function(){time(o);},1000);
			}
		}
		
		var iswait= 300;
		function outmoded(o){
			if (iswait == 0) {
				clear = false;
				iswait = 300;
				code="11111";
			} else {
				iswait--;
				if(!clear){
					setTimeout(function(){outmoded(o);},1000);
				}else{
					iswait = 300;
				}
				
			}
		}
		
		function validateMobilephone(){
			var flagVali = true;
			var mobilephone = $.trim($('#phone').val());
			if(mobilephone != ''){
				$.ajax({
					url:"valChangePhone.action",
					data:{mobilephone:mobilephone},
					type:"post",
					dataType:"json",
					//cache : false,  
			        async : false, 
					success:function (data){
						if(data.reno == "1"){
							flagVali = false;
							var msg = "该手机号码已存在，请重新输入手机号码！";
							$.layer({
								area : ['auto','auto'],
								dialog : {msg:msg,type : 8}	
							});
						}
					}
				});
				return flagVali;
			}
		}
		
//		function getBindPhoneCode(){
//			$("#phonecode").val("");
//			var bind_mobile = $('#phone').val();
//			var name = $.trim($('#username').val());
//			var phoneMessageType=$('#phoneMessageType').val();
//			$.getJSON('${base}/phoneValidateCode.action?mobilephone='+bind_mobile+"&username="+name+"&phoneMessageType="+phoneMessageType,function(d){
//				alert(d.msg);
//				if(d.msg != '该手机号码已存在,请直接登录'){
//					//可以发送短息
//					$('#bind_mobile_btn').attr('disabled',false);
//					code = d.code;
//				}else{
//					//号码存在不允许发送短信
//				}
//				
//			});
//		}
		$(function(){
			var isAllow = true;
			$('#bind_mobile_btn').click(function(){
				var phoneMessageType1=$('#phoneMessageType').val();
				if(phoneMessageType1 == '3'){
					var f = validateMobilephone();
					if(!f){
						return;
					}
				}
				if(!isAllow){
					return;
				}
				isAllow = false;
				var THIS = this;
				var bind_mobile = $.trim($('#phone').val());
				var partten = /^1[3458]\d{9}$/;
				if(!bind_mobile || !partten.test(bind_mobile) || bind_mobile == ""){
					$.layer({
						area : ['auto','auto'],
						dialog : {msg:'请输入正确的11位手机号码',type : 3}	
					});
					isAllow = true;
					return false;
				}
//				if($.trim($('#valPhoneCode').text())==bind_mobile){
//					$('#phoneValidateMsg').html('该号码已经验证');
//					return false;
//				}
				
				$.getJSON('mobilePhoneValidate.action?mobilephone='+bind_mobile,function(data){
					if(data.reno == 0){
						if(data.msg){
							//if(confirm(data.msg)){
								
								$("#phonecode").val("");
								var bind_mobile = $('#phone').val();
								var name = $.trim($('#username').val());
								var phoneMessageType=$('#phoneMessageType').val();
								$.getJSON('phoneValidateCode.action?mobilephone='+bind_mobile+"&username="+name+"&phoneMessageType="+phoneMessageType,function(d){
									var typeImg = 3;
									if(d.msg == '该手机号码已存在,请直接登录' || d.msg == '该手机号码未注册,请核对信息'|| d.msg.indexOf('尊敬')!= -1){
										typeImg = 3;
									}else{
										typeImg = 9;
									}
									$.layer({
										title:['分分通提示您',true],
										area : ['auto','auto'],
										dialog : {msg:d.msg,type : typeImg}	
									});
									isAllow = true;
									if(d.msg != '该手机号码已存在,请直接登录' && d.msg != '该手机号码未注册,请核对信息' && d.msg.indexOf('尊敬') == -1){
										//可以发送短息
										$('#bind_mobile_btn').attr('disabled',false);
										code = d.code;
										valSuccessPhone="";
										THIS.setAttribute("disabled", true);
										clear=false;
										time(THIS);
										outmoded(THIS);
									}else{
										//号码存在不允许发送短信
									}
									
								});
								
								
							//}else{
							//	$(THIS).attr('disabled',false);
							//}
						}else{
							
							$("#phonecode").val("");
							var bind_mobile = $('#phone').val();
							var name = $.trim($('#username').val());
							var phoneMessageType=$('#phoneMessageType').val();
							$.getJSON('phoneValidateCode.action?mobilephone='+bind_mobile+"&username="+name+"&phoneMessageType="+phoneMessageType,function(d){
								isAllow = true;
								var typeImg = 3;
								if(d.msg == '该手机号码已存在,请直接登录' || d.msg == '该手机号码未注册,请核对信息' || d.msg.indexOf('尊敬')!= -1){
									typeImg = 3;
								}else{
									typeImg = 9;
								}
								$.layer({
									title:['分分通提示您',true],
									area : ['auto','auto'],
									dialog : {msg:d.msg,type : typeImg}	
								});
								if(d.msg != '该手机号码已存在,请直接登录' && d.msg != '该手机号码未注册,请核对信息'&& d.msg.indexOf('尊敬') == -1){
									//可以发送短息
									$('#bind_mobile_btn').attr('disabled',false);
									code = d.code;
									valSuccessPhone="";
									THIS.setAttribute("disabled", true);
									clear=false;
									time(THIS);
									outmoded(THIS);
								}else{
									//号码存在不允许发送短信
								}
								
							});
						}
						samePhoneVal = bind_mobile;
					}else{
						$.layer({
							area : ['auto','auto'],
							dialog : {msg:d.msg,type : 3}	
						});
						$(THIS).attr('disabled',false);
					}
				});
				
			});
		}); 
		
		
		
		/**
		 * 页面手机验证码验证
		 * @param $mobilePhone 手机号码输入框jquery对象
		 * @param $validateCode  验证码输入框jquery对象
		 */
		function validatePhoneCode($mobilePhone,$valPhoneCode){
			var phone=$.trim($mobilePhone.val());
			var valPhoneCode = $.trim($valPhoneCode.val());
			if(valSuccessPhone==""){
				if(valPhoneCode == null || valPhoneCode == ""){
					$.layer({
						title:['分分通提示您',true],
						time:3,
						area : ['auto','auto'],
						dialog : {msg:'请输入手机验证号码',type : 9}	
					});
					return false;
				}else if(code == "11111"){
					$.layer({
						title:['分分通提示您',true],
						time:3,
						area : ['auto','auto'],
						dialog : {msg:'验证码失效请重新获取',type : 9}	
					});
					return false;
				}else if(code != valPhoneCode){
					$.layer({
						title:['分分通提示您',true],
						time:3,
						area : ['auto','auto'],
						dialog : {msg:'手机验证码错误',type : 9}	
					});
					return false;
				}else if(code == valPhoneCode){
					if(samePhoneVal != "" && samePhoneVal == phone){
						valSuccessPhone = valPhoneCode;
						return true;						
					}else{	
						$.layer({
							title:['分分通提示您',true],
							time:3,
							area : ['auto','auto'],
							dialog : {msg:'请输入新手机验证码',type : 9}	
						});
						return false;
					}
				}						
			}else if(valSuccessPhone == valPhoneCode){
				if(samePhoneVal != "" && samePhoneVal == phone){
					return true;						
				}else{
					$.layer({
						title:['分分通提示您',true],
						time:3,
						area : ['auto','auto'],
						dialog : {msg:'请输入新手机验证码',type : 9}	
					});
					return false;
				}
			}else{
				$.layer({
					title:['分分通提示您',true],
					time:3,
					area : ['auto','auto'],
					dialog : {msg:'手机验证码错误',type : 9}	
				});
				return false;
			}
		}