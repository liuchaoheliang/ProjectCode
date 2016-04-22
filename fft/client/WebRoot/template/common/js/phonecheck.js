/*
* Author:
* pengling@f-road.com.cn 
*/
//手机充值验证
window.onload = function()
{
  var oText =document.getElementById('phonelist');
  var check = [
      /^[0-9]{11}$/,                   /*手机号*/ 
	  ];
	  
  var message = [
	  "请输入11位手机号码"
  ]	  
  
  var error = [
      "不能为空",
	  "请正确输入11位手机号码",
	  "两次输入的手机号不一样"
  ]	
  
  var ok = '<h2>' + "输入成功" + '</h2>';
  
 
//
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
			case "phone":
				ospan.innerHTML = '<h4>' + message[0] + '</h4>';
				this.className = 'focusText';
				break;

			case "phone2":
				ospan.innerHTML = '<h4>' + message[0] + '</h4>';
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
				case "phone":
					if (check[0].test(this.value)) {
						ospan.innerHTML = ok;
						this.className = 'loginText';
						this.parentNode.appendChild(ospan);
					} else {
						ospan.innerHTML = '<h3>' + error[1] + '</h3>';
						this.className = 'errorText';
					}
					break;

				case "phone2":
					if (check[0].test(this.value)) {
						ospan.innerHTML = ok;
						this.className = 'loginText';
						this.parentNode.appendChild(ospan);
						if (this.value != document.getElementById("phone").value) {
							ospan.innerHTML = '<h3>' + error[2] + '</h3>';
							this.className = 'errorText';
						}
					} else {
						ospan.innerHTML = '<h3>' + error[1] + '</h3>';
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
				case "phone":
				    ospan.innerHTML = '<h3>' + error[0] + '</h3>';
					this.className = 'errorText';
					break;

				case "phone2":
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
			if (oc.id == "username" || oc.id == "password" || oc.id == "password2" || oc.id == "phone" || oc.id == "phone2" || oc.id == "people" || oc.id =="seller" || oc.id =="phonemail") {
				oc.className = 'errorText'
			} else if (oc.id == "phonecode" || oc.id == "cash") {
				oc.className = 'errorText3'
			} else if (oc.id == "code" || oc.id =="price" || oc.id =="points") {
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

}