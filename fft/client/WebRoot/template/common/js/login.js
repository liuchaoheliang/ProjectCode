 	/**
	 * 切换验证码图片
	 */
	function changeValidateCode() {  
        var timenow = new Date().getTime();  
        $('.codeimg img').attr('src',"validateCode.action?d="+timenow);
    }
	
	
	/*
	* Author:
	* pengling@f-road.com.cn 
	*/
	//表单验证
	window.onload = function()
	{
	  var oText =document.getElementById('tab');
	  var check = [
	      /^[a-zA-Z0-9_-]{4,20}$/,           /*用户名*/
	      /^\w{6,15}$/,                     /*密码*/
	      /^[0-9]{4}$/,                    /*验证码、工号*/
		  ];
		  
	  var message = [
	      "请输入您的用户名或手机号登录",
		  "请输入您的密码",
		  "请输入您的工号",
	  ]	  
	  
	  var error = [
	      "不能为空",
		  "密码为6-15位英文或数字",
		  "工号为4位数字",

	  ]	
	  
	  var ok = '<h2>' + "输入成功" + '</h2>';
	  
	 
	 
	//取得所有input		  
	var oInput = oText.getElementsByTagName('input');
	for (i = 0; i < oInput.length; i++) {
		  oInput[i].index = i;
			//获得焦点动作	
		  oInput[i].onfocus = function() {
	      this.className = 'focusText';			
		}
		
		    //失去焦点动作		
		  oInput[i].onblur = function() {
		  this.className = 'loginText';	
		}	
	}
			
	}

