/*
* Author:
* pengling@f-road.com.cn 
*/
//表单验证
window.onload = function(e)
{
	try{
  eval(document.getElementsByTagName("body")[0].getAttribute("myload") );
  var oText =document.getElementById('formlist');
  var check = [
      /^[a-zA-Z][a-zA-Z0-9_-]{3,19}$/,         /*0用户名*/
      /^\w{6,15}$/,                    /*1密码*/  
      /^\w{6,15}$/,                    /*2确认密码*/
      /^1(30|31|32|55|56|85|86|45)\d{8}$/,                   /*3联通手机号*/ 
      /^[0-9]{6}$/,                    /*4手机验证码*/ 
	  /^\w+@\w+([-]\w+)*\.[a-z]+(\.[a-z]+)*$/,        /*5邮箱*/	 
      /^[0-9]{4}$/,                    /*6验证码*/
	  /^([1-9][0-9]+|0|[1-9])(\.\d{1,2})?$/,      /*7价格*/             
	  /^[0-9]*[1-9][0-9]*$/,           /*8数量*/
	  /^([1-9][0-9]+|0|[1-9])(\.\d{1,2})?$/,      /*9积分*/
	  /^[\u4e00-\u9fa5]+$/,             /*10姓名*/
	  /^622859[0-9]{13}$/,             /*11银行卡号*/
	  /^1(33|53|80|81|89)\d{8}$/,        /*12电信手机号*/ 
	  /^1(3|4|5|8)\d{9}$/,            /*13中国手机号*/ 
	  /^1(3[4-9]|47|5[012789]|8[278])\d{8}$/,     /*14移动手机号*/
	  /^[1-9][0-9]*0$/,               /*15积分提现*/
	  /^((\d{15})|(\d{17}[0-9Xx]))$/,    /*16身份证号码*/
	  /^(\d{16,19})$/               /*17验证银行卡为16-19位数字*/
	  ];
	  
  var message = [
      "4-20位字符，数字、英文及“-”、“_”组成",
	  "请使用6-15位英文、数字或下划线的组合更安全",
	  "请再次输入密码",
	  "请输入11位手机号码",
	  "请输入手机收到的验证码",
	  "请输入您的邮箱，也可以不填",
	  "请输入验证码",
	  "请输入联系人",
	  "请输入合作商户名称",	  
	  "请输入您的联系地址",
	  "请输入价格",
	  "请输入消费产品名称",
	  "请输入数量",
	  "请输入兑换积分",
	  "请输入充值金额",
	  "提现积分数为整数，且为10的整数倍",
	  "请输入手机号码或者注册邮箱",
	  "请输入银行账户名",
	  "请输入16至19位银行卡号",
	  "请输入您的中文姓名",
	  "请输入您原来的密码",
	  "请输入您的身份证号",
	  "请输入新密码",
	  "确认新密码",
	  "请输入您的密码"
  ]	  
  
  var error = [
      "不能为空",
	  "请输入6-15位英文、数字或下划线的组合",
	  "两次密码输入不一致",
	  "请正确输入11位手机号码",
	  "您输入的邮箱格式不正确",
	  "请输入4-20位字符，不能为纯数字，必须以字母开头",
	  "验证码是6位数字",
	  "两次输入的手机号不一样",
	  "请正确输入价格，精确到小数点后两位",
	  "请正确输入数量",
	  "请正确输入积分，精确到小数点后两位",
	  "请正确输入充值金额",
	  "请正确输入积分数，例如：10，20，130",
	  "请正确输入手机号或邮箱",
	  "对不起，你拥有的积分不足",
	  "请正确输16至19位银行卡号",
	  "姓名只能输入中文且不能含有空格",
	  "新密码不能和原密码相同",
	  "请正确输入您的证件号",
	  "密码输入错误，请重新输入",
	  "请正确输入您的证件号"
  ]	
  
  var ok = '<h2>' + "输入成功" + '</h2>';
  
 
 
//取得所有input		  
var oInput = oText.getElementsByTagName('input');

//创建span元素
var ospan = document.createElement('span');
for (i = 0; i < oInput.length; i++) {
	oInput[i].index = i;

	if (oInput[i].type == 'text' || oInput[i].type == 'password') {
		//获得焦点动作	
		oInput[i].onfocus = function() {
			var oid = this.id;
			switch (oid) {
			case "username":
				ospan.innerHTML = '<h4>' + message[0] + '</h4>';
				this.className = 'focusText';
				break;

			case "password":
				ospan.innerHTML = '<h4>' + message[1] + '</h4>';
				this.className = 'focusText';
				break;

			case "password2":
				ospan.innerHTML = '<h4>' + message[2] + '</h4>';
				this.className = 'focusText';
				break;

			case "phone":
				ospan.innerHTML = '<h4>' + message[3] + '</h4>';
				this.className = 'focusText';
				break;

			case "phone2":
				ospan.innerHTML = '<h4>' + message[3] + '</h4>';
				this.className = 'focusText';
				break;

			case "phonecode":
				ospan.innerHTML = '<h4>' + message[4] + '</h4>';
				this.className = 'focusText3';
				break;

			case "email":
				ospan.innerHTML = '<h4>' + message[5] + '</h4>';
				this.className = 'focusText';
				break;

			case "code":
				ospan.innerHTML = '<h4>' + message[6] + '</h4>';
				this.className = 'focusText4';
				break;
			
			case "people":
				ospan.innerHTML = '<h4>' + message[7] + '</h4>';
				this.className = 'focusText';
				break;	
				
			case "seller":
				ospan.innerHTML = '<h4>' + message[8] + '</h4>';
				this.className = 'focusText';
				break;	
				
			case "address":
				ospan.innerHTML = '<h4>' + message[9] + '</h4>';
				this.className = 'focusText';
				break;		
				
			case "price":
				ospan.innerHTML = '<h4>' + message[10] + '</h4>';
				this.className = 'focusText4';
				break;
				
			case "name":
				ospan.innerHTML = '<h4>' + message[11] + '</h4>';
				this.className = 'focusText';
				break;													

			case "number":
				ospan.innerHTML = '<h4>' + message[12] + '</h4>';
				this.className = 'focusText5';
				break;	

			case "points":
				ospan.innerHTML = '<h4>' + message[13] + '</h4>';
				this.className = 'focusText4';
				break;
				
			case "cash":
				ospan.innerHTML = '<h4>' + message[15] + '</h4>';
				this.className = 'focusText4';
				break;
				
			case "phonemail":
				ospan.innerHTML = '<h4>' + message[16] + '</h4>';
				this.className = 'focusText';
				break;					

			case "adminname":
				ospan.innerHTML = '<h4>' + message[17] + '</h4>';
				this.className = 'focusText2';
				break;
				
			case "banknumber":
				ospan.innerHTML = '<h4>' + message[18] + '</h4>';
				this.className = 'focusText';
				break;	

			case "fullname2":
				ospan.innerHTML = '<h4>' + message[19] + '</h4>';
				this.className = 'focusText2';
				break;	
				
			case "password3":
				ospan.innerHTML = '<h4>' + message[20] + '</h4>';
				this.className = 'focusText';
				break;
				
			case "password4":
				ospan.innerHTML = '<h4>' + message[22] + '</h4>';
				this.className = 'focusText';
				break;	
				
			case "password5":
				ospan.innerHTML = '<h4>' + message[23] + '</h4>';
				this.className = 'focusText';
				break;
				
			case "password6":
				ospan.innerHTML = '<h4>' + message[24] + '</h4>';
				this.className = 'focusText';
				break;									
				
			case "card":
				ospan.innerHTML = '<h4>' + message[21] + '</h4>';
				this.className = 'focusText';
				break;																																		
			
			
			case "idCard":
				ospan.innerHTML = '<h4>' + message[21] + '</h4>';
				this.className = 'focusText';
				break;																																		
			}

			this.parentNode.appendChild(ospan);
			if (this.parentNode.getElementsByTagName('span').length == 2) {
				this.parentNode.removeChild(this.parentNode.getElementsByTagName('span')[0])
			}

		}

		//失去焦点动作			
		oInput[i].onblur = function() {
			var ospan = document.createElement('span');
			var oid = this.id;
			if (this.value != '') {
				switch (oid) {

				case "username":
					if (check[0].test(this.value)) {
						ospan.innerHTML = ok;
						this.className = 'loginText';
						this.parentNode.appendChild(ospan);
					} else {
						ospan.innerHTML = '<h3>' + error[5] + '</h3>';
						this.className = 'errorText';
					}
					break;

				case "password":
					if (check[1].test(this.value)) {
						ospan.innerHTML = ok;
						this.className = 'loginText';
						this.parentNode.appendChild(ospan);
					} else {
						ospan.innerHTML = '<h3>' + error[1] + '</h3>';
						this.className = 'errorText';
					}
					break;

				case "password2":
					if (check[1].test(this.value)) {
						ospan.innerHTML = ok;
						this.className = 'loginText';
						this.parentNode.appendChild(ospan);
						if (this.value != document.getElementById("password").value) {
							ospan.innerHTML = '<h3>' + error[2] + '</h3>';
							this.className = 'errorText';
						}
					}

					else {
						ospan.innerHTML = '<h3>' + error[1] + '</h3>';
						this.className = 'errorText';
					}
					break;

				case "phone":
					if (check[13].test(this.value)&&(check[3].test(this.value)||check[12].test(this.value)||check[14].test(this.value))) {
						ospan.innerHTML = ok;
						this.className = 'loginText';
						this.parentNode.appendChild(ospan);
					} else {
						ospan.innerHTML = '<h3>' + error[3] + '</h3>';
						this.className = 'errorText';
					}
					break;

				case "phone2":
					if (check[13].test(this.value)&&(check[3].test(this.value)||check[12].test(this.value)||check[14].test(this.value))) {
						ospan.innerHTML = ok;
						this.className = 'loginText';
						this.parentNode.appendChild(ospan);
						if (this.value != document.getElementById("phone").value) {
							ospan.innerHTML = '<h3>' + error[7] + '</h3>';
							this.className = 'errorText';
						}
					} else {
						ospan.innerHTML = '<h3>' + error[3] + '</h3>';
						this.className = 'errorText';
					}
					break;

				case "phonecode":
					if (check[4].test(this.value)) {
						this.className = 'loginText3';
						this.parentNode.appendChild(ospan);
					} else {
						ospan.innerHTML = '<h3>' + error[6] + '</h3>';
						this.className = 'errorText3';
					}
					break;

				case "email":
					if (check[5].test(this.value)) {
						ospan.innerHTML = ok;
						this.className = 'loginText';
						this.parentNode.appendChild(ospan);
					} else {
						ospan.innerHTML = '<h3>' + error[4] + '</h3>';
						this.className = 'errorText';
					}
					break;


				case "code":
					this.className = 'loginText4';
					break;
					
				case "people":
				    ospan.innerHTML = ok;
					this.className = 'loginText';
					this.parentNode.appendChild(ospan);
					break;
					
				case "seller":
				    ospan.innerHTML = ok;
					this.className = 'loginText';
					this.parentNode.appendChild(ospan);
					break;		

				case "address":
					this.className = 'loginText';
					break;	
					
				case "price":
					if (check[7].test(this.value)) {
						ospan.innerHTML = ok;
						this.className = 'loginText4';
						this.parentNode.appendChild(ospan);
					} else {
						ospan.innerHTML = '<h3>' + error[8] + '</h3>';
						this.className = 'errorText4';
					}
					break;		
																
				case "name":
					this.className = 'loginText';
					break;
					
				case "number":
					if (check[8].test(this.value)) {
						ospan.innerHTML = ok;
						this.className = 'loginText5';
						this.parentNode.appendChild(ospan);
					} else {
						ospan.innerHTML = '<h3>' + error[9] + '</h3>';
						this.className = 'errorText5';
					}
					break;
					
				case "points":
					if (check[9].test(this.value)) {
						ospan.innerHTML = ok;
						this.className = 'loginText4';
						this.parentNode.appendChild(ospan);
					} else {
						ospan.innerHTML = '<h3>' + error[10] + '</h3>';
						this.className = 'errorText4';
					}
					break;
					
				case "cash":
					maxnum=document.getElementById("max").innerHTML;
					maxnum=parseFloat(maxnum);
					realPoints=this.value;
					realPoints=parseFloat(realPoints);
					if(!check[15].test(this.value)){
						ospan.innerHTML = '<h3>' + error[12] + '</h3>';
						this.className = 'errorText4';
					} 
					else if	(realPoints > maxnum){
							ospan.innerHTML = '<h3>' + error[14] + '</h3>';
						    this.className = 'errorText4';							
					}
					
					else {
						ospan.innerHTML = ok;
						this.className = 'loginText4';
						this.parentNode.appendChild(ospan);						
					}	
					break;				

				case "phonemail":
					if (check[3].test(this.value) || check[5].test(this.value) ) {
						ospan.innerHTML = ok;
						this.className = 'loginText';
						this.parentNode.appendChild(ospan);
					} else {
						ospan.innerHTML = '<h3>' + error[13] + '</h3>';
						this.className = 'errorText';
					}
										
					break;	
					
				case "adminname":
					if(check[10].test(this.value)){
						ospan.innerHTML = ok;
						this.className = 'loginText2';
						this.parentNode.appendChild(ospan);
					}else{
						//this.value="";
						ospan.innerHTML = '<h3>' + error[16] + '</h3>';
						this.className = 'errorText2';
					}

					break;
					
				case "banknumber":
					if (check[17].test(this.value)) {
						ospan.innerHTML = ok;
						this.className = 'loginText';
						this.parentNode.appendChild(ospan);
					} else {
						ospan.innerHTML = '<h3>' + error[15] + '</h3>';
						this.className = 'errorText';
					}
					break;	
					
				case "fullname2":
					if (check[10].test(this.value)) {
						ospan.innerHTML = ok;
						this.className = 'loginText2';
						this.parentNode.appendChild(ospan);
					} else {
						ospan.innerHTML = '<h3>' + error[16] + '</h3>';
						this.className = 'errorText2';
					}
					break;	
					
					
				case "password3":
					if (check[1].test(this.value)) {
						ospan.innerHTML = ok;
						this.className = 'loginText';
						this.parentNode.appendChild(ospan);
						if(this.value == document.getElementById("password4").value){	
						   ospan.innerHTML = '<h3>' + error[17] + '</h3>';
						   this.className = 'errorText';
						  }
					} else {
						ospan.innerHTML = '<h3>' + error[1] + '</h3>';
						this.className = 'errorText';
					}
					break;	
					
				case "password4":
					if (check[1].test(this.value)) {
						ospan.innerHTML = ok;
						this.className = 'loginText';
						this.parentNode.appendChild(ospan); 
						   if(this.value == document.getElementById("password3").value){	
						   ospan.innerHTML = '<h3>' + error[17] + '</h3>';
						   this.className = 'errorText';
						  }
											
					} else {
						ospan.innerHTML = '<h3>' + error[1] + '</h3>';
						this.className = 'errorText';
					}
					break;	
					
				case "password5":
					if (check[1].test(this.value)) {
						ospan.innerHTML = ok;
						this.className = 'loginText';
						this.parentNode.appendChild(ospan); 
						   if(this.value != document.getElementById("password4").value){	
						   ospan.innerHTML = '<h3>' + error[2] + '</h3>';
						   this.className = 'errorText';
						  }
											
					} else {
						ospan.innerHTML = '<h3>' + error[1] + '</h3>';
						this.className = 'errorText';
					}
					break;							
					
					
				case "card":
					if (jQuery.trim(this.value)!="") {
						ospan.innerHTML = ok;
						this.className = 'loginText';
						this.parentNode.appendChild(ospan);
					} else {
						ospan.innerHTML = '<h3>' + error[18] + '</h3>';
						this.className = 'errorText';
					}
										
					break;
					
				case "idCard":
					if (check[16].test(this.value)) {
						ospan.innerHTML = ok;
						this.className = 'loginText';
						this.parentNode.appendChild(ospan);
					} else {
						ospan.innerHTML = '<h3>' + error[18] + '</h3>';
						this.className = 'errorText';
					}
										
					break;
					
				case "password6":
					if (check[1].test(this.value)) {
						ospan.innerHTML = ok;
						this.className = 'loginText';
						this.parentNode.appendChild(ospan);
					} else {
						ospan.innerHTML = '<h3>' + error[19] + '</h3>';
						this.className = 'errorText';
					}
					break;												  
																													
				}
				
		this.parentNode.appendChild(ospan);
				if (this.parentNode.getElementsByTagName('span').length == 2) {
					this.parentNode.removeChild(this.parentNode.getElementsByTagName('span')[0])
				}
			}

			else {

				switch (oid) {
				case "username":
				    ospan.innerHTML = '<h3>' + error[0] + '</h3>';
					this.className = 'errorText';
					break;

				case "password":
				    ospan.innerHTML = '<h3>' + error[0] + '</h3>';
					this.className = 'errorText';
					break;

				case "password2":
				    ospan.innerHTML = '<h3>' + error[0] + '</h3>';
					this.className = 'errorText';
					break;

				case "phone":
				    ospan.innerHTML = '<h3>' + error[0] + '</h3>';
					this.className = 'errorText';
					break;

				case "phone2":
				    ospan.innerHTML = '<h3>' + error[0] + '</h3>';
					this.className = 'errorText';
					break;

				case "phonecode":
				    ospan.innerHTML = '<h3>' + error[0] + '</h3>';
					this.className = 'errorText3';
					break;

				case "email":
					this.className = 'loginText';
					break;

				case "code":
				    ospan.innerHTML = '<h3>' + error[0] + '</h3>';
					this.className = 'errorText4';
					break;
					
				case "people":
				    ospan.innerHTML = '<h3>' + error[0] + '</h3>';
					this.className = 'errorText';
					break;
					
				case "seller":
			      	ospan.innerHTML = '<h3>' + error[0] + '</h3>';
					this.className = 'errorText';
					break;		
									
				case "address":
					this.className = 'loginText';
					break;	
						
				case "price":
			      	ospan.innerHTML = '<h3>' + error[0] + '</h3>';
					this.className = 'errorText4';
					break;						

				case "name":
					this.className = 'loginText';
					break;
					
				case "number":
			      	ospan.innerHTML = '<h3>' + error[0] + '</h3>';
					this.className = 'errorText5';
					break;						

				case "points":
					this.className = 'loginText4';
					break;
					
				case "cash":
				    ospan.innerHTML = '<h3>' + error[0] + '</h3>';
					this.className = 'errorText4';
					break;	
					
				case "phonemail":
				    ospan.innerHTML = '<h3>' + error[0] + '</h3>';
					this.className = 'errorText';
					break;
					
				case "adminname":
				    ospan.innerHTML = '<h3>' + error[0] + '</h3>';
					this.className = 'errorText2';
					break;
					
				case "banknumber":
				    ospan.innerHTML = '<h3>' + error[0] + '</h3>';
					this.className = 'errorText';
					break;
					
				case "fullname2":
				    ospan.innerHTML = '<h3>' + error[0] + '</h3>';
					this.className = 'errorText2';
					break;
					
				case "password3":
				    ospan.innerHTML = '<h3>' + error[0] + '</h3>';
					this.className = 'errorText';
					break;	
	
				case "password4":
				    ospan.innerHTML = '<h3>' + error[0] + '</h3>';
					this.className = 'errorText';
					break;
					
				case "password5":
				    ospan.innerHTML = '<h3>' + error[0] + '</h3>';
					this.className = 'errorText';
					break;																																																							
				
				case "card":
				    ospan.innerHTML = '<h3>' + error[0] + '</h3>';
					this.className = 'errorText';
					break;	
					
				case "password6":
				    ospan.innerHTML = '<h3>' + error[0] + '</h3>';
					this.className = 'errorText';
					break;																																																	
								
				case "idCard":
				    ospan.innerHTML = '<h3>' + error[0] + '</h3>';
					this.className = 'errorText';
					break;					
				}
				
				this.parentNode.appendChild(ospan);
				if (this.parentNode.getElementsByTagName('span').length == 2) {
					this.parentNode.removeChild(this.parentNode.getElementsByTagName('span')[0])
				}

			}

		}
	}
}

//最终提交验证
/*
 var ob = oText.getElementsByTagName('u');
document.getElementById("send").onclick = function() {
	for (i = 0; i < ob.length; i++) {
		var oc = ob[i].parentNode.getElementsByTagName('input')[0];
		if (oc.value == '') {
			ospan.innerHTML = '<h3>' + error[0] + '</h3>';
			ob[i].parentNode.appendChild(ospan);
			if (ob[i].parentNode.getElementsByTagName('span').length == 2) {
				ob[i].parentNode.removeChild(ob[i].parentNode.getElementsByTagName('span')[0])
			}
			if (oc.id == "username" || oc.id == "password" || oc.id == "password2" || oc.id == "phone" || oc.id == "phone2" || oc.id == "people" || oc.id =="seller" || oc.id =="phonemail" || oc.id =="banknumber") {
				oc.className = 'errorText'
			} else if(oc.id == "adminname" || oc.id == "fullname2"){
				oc.className = 'errorText2'
				}
			  else if (oc.id == "phonecode") {
				oc.className = 'errorText3'
			} else if (oc.id == "code" || oc.id =="price" || oc.id =="points" || oc.id == "cash") {
				oc.className = 'errorText4'
			} else if (oc.id == "number" ) {
				oc.className = 'errorText5'
			} 
             var subm = oText.getElementsByTagName('h3').length;
			 if(subm){
				 return false;
				 }
			break;
					
		}
	}

}
*/

}catch(e){}}
