/*
* Author:
* pengling@f-road.com.cn 
*/
function onclickshow(){
  var o = document.getElementById("showDialog");
  var e = document.getElementById("pay_mask");
  var a = document.getElementById("newinfor");
  o.style.display = "block";  
  o.style.position ="absolute";
  o.style.zIndex = "9999";
  e.style.display = "block";
  e.style.width = document.body.clientWidth+'px'
  e.style.height = document.body.clientHeight+'px'
  a.style.left = (document.body.clientWidth-1047)/2+'px' 
  if(window.navigator.userAgent.indexOf("Chrome")!= -1){
	  
	a.style.position ="absolute"; 
    a.style.top=(document.body.clientHeight-a.clientHeight)/2+document.body.scrollTop+"px";
 
	  }
    a.style.top=(document.documentElement.clientHeight-a.clientHeight)/2+document.body.scrollTop+"px";

  }
 
function showDialogClose(){
		  
		  document.getElementById("showDialog").style.display = "none"
		  }


document.getElementById("pay_mask").onclick = function(){
	document.getElementById("showDialog").style.display = "none"   
}	