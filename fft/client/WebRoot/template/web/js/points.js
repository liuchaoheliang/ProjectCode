/*
* Author:
* pengling@f-road.com.cn 
*/
/*购买加减法*/
var price =  parseInt(document.getElementById("totle").innerHTML);
var cardfen = parseInt(document.getElementById("cardfen").innerHTML);
var number = document.getElementById("value");
var stock = parseInt(document.getElementById("stock").innerHTML);
var priceA =  parseInt(document.getElementById("totlePriceA").innerHTML);
var priceB =   parseInt(document.getElementById("totlePriceA").innerHTML);
document.getElementById("boxnumber").innerHTML = stock;
var totlepriceA = null;
var totlepriceB = null;
var totleonkeyA	= null;
var totleonkeyB = null;

/*初始状态大于0显示积分*/
if(number.value > 0 && number.value <= stock){	
  totlepriceA =  price * number.value;
  totlepriceB =  cardfen * number.value;
  document.getElementById("totlePriceA").innerHTML = totlepriceA;
  document.getElementById("totlePriceB").innerHTML = totlepriceB; 
}

/*手动输入计算积分*/
number.onkeyup=function (){
	this.value = this.value.replace(/\D/g,'');
	this.value.substring(0,1)=='0'?this.value='1':this.value=this.value
	if(this.value <= stock && this.value > 0){
	totleonkeyA = price * number.value;
	totleonkeyB = cardfen * number.value;
	document.getElementById("totlePriceA").innerHTML = totleonkeyA;
	document.getElementById("totlePriceB").innerHTML = totleonkeyB;				
	}
	
}

	
/*点击加1计算积分*/
function add(){
  var getsum = parseInt(document.getElementById("value").value);
  if(getsum < stock && getsum >= 0){
  document.getElementById("value").value = getsum + 1;  
  var totlepriceA =  price * getsum + price;
  document.getElementById("totlePriceA").innerHTML = totlepriceA;
  var totlepriceB = cardfen * number.value;
  document.getElementById("totlePriceB").innerHTML = totlepriceB;
  document.getElementById("showWrong").style.display = "none";
  }
}

/*点击减1计算积分*/
function num(){
  var getnum = parseInt(document.getElementById("value").value);
  if(getnum >1 && getnum <= stock){
  document.getElementById("value").value = getnum -1;
  var totlepriceA =  price * getnum - price; 
  document.getElementById("totlePriceA").innerHTML = totlepriceA;
  var totlepriceB = cardfen * number.value;
  document.getElementById("totlePriceB").innerHTML = totlepriceB;  
  }
}


	

	
	
	
	
ePriceB").innerHTML = totlepriceB;
  document.getElementById("showWrong").style.display = "none";
  }
}

/*点击减1计算积分*/
function num(){
  var getnum = parseInt(document.getElementById("value").value);
  if(getnum >1 && getnum <= stock){
  document.getElementById("value").value = getnum -1;
  var totlepriceA =  price * getnum - price; 
  document.getElementById("totlePriceA").innerHTML = totlepriceA;
  var totlepriceB = totlepriceA * 10;
  document.getElementById("totlePriceB").innerHTML = totlepriceB;  
  }
}


	

	
	
	
	
