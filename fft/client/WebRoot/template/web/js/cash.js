/*
* Author:
* pengling@f-road.com.cn 
*/
//积分提现
if(document.getElementById("points").innerHTML == 10){
	document.getElementById("fee").innerHTML = 1
	}
	
else if(document.getElementById("points").innerHTML >= 1000){
	document.getElementById("fee").innerHTML = 50
	}
	
else{
	 document.getElementById("fee").innerHTML = ((document.getElementById("points").innerHTML)/100)*5
	}
	
document.getElementById("rmb").innerHTML = document.getElementById("points").innerHTML - document.getElementById("fee").innerHTML;
