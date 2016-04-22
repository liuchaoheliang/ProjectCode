/*
* Author:
* pengling@f-road.com.cn 
*/
/*头部*/
function getCookie(name) //取cookies函数         
{
    var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
     if(arr != null) return unescape(arr[2]); return null;
 
}

var cookname = decodeURIComponent(getCookie('username').split('$')[0]);


if(cookname == null || cookname == "") {
	
	document.getElementById("jifenshow").style.display = 'block';
}

//0是个人帐号
else if(cookname == 0){
	document.getElementById("jifenshow").style.display = 'none';
	document.getElementById("memeberlogin").style.display = 'none';
	document.getElementById("loginout").style.display = 'block';
	document.getElementById("regedit").style.display = 'none';
	document.getElementById("busylogin").style.display = 'none';
	document.getElementById("hello").style.display = 'block';
	document.getElementById("name").innerHTML = cookname;	
}

//1是商户账号
else if(cookname == 1){
	 document.getElementById("jifenshow").style.display = 'none';
	 document.getElementById("memeberlogin").style.display = 'none';
	 document.getElementById("loginout").style.display = 'block';
	 document.getElementById("regedit").style.display = 'none';
	 document.getElementById("busylogin").style.display = 'none';
	 document.getElementById("hello").style.display = 'block';
	 document.getElementById("name").innerHTML = cookname;
	}