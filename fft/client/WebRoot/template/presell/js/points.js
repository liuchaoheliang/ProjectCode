/*
* Author:
* pengling@f-road.com.cn 
*/
//付款订单
var textnum = document.getElementById("number");
var number = textnum.value - 0;
var getnum = parseInt(document.getElementById("stock").innerHTML);
document.getElementById("boxnumber").innerHTML = getnum;

textnum.onkeyup =function (){	    
		this.value = this.value.replace(/\D/g,'');
		this.value.substring(0,1)=='0'?this.value='1':this.value=this.value
		this.value.substring(0,1)==''?this.value='1':this.value=this.value
		if(this.value > getnum){
			  wrong();
			}
}

function add(){
     var textnum = document.getElementById("number");
	 var number = textnum.value - 0;
	 if(number>=0 && number<getnum){
     number = number + 1;
	 textnum.value = number;
	 }
	}
	
	
function num(){
	var textnum = document.getElementById("number");
	var number = textnum.value - 0;
	if(number > 1){	
	number = number - 1;
	 textnum.value = number;
	}
}


function wrong(){	
	document.getElementById("show").style.display = "block"
	}

	

	
	
	
	
