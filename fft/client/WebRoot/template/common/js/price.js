/*
* Author:
* pengling@f-road.com.cn 
*/
//付款订单
var price = document.getElementById("price").innerHTML+"";
price=parseFloat(price);
var totle = document.getElementById("totleprice").innerHTML+"";
totle=parseFloat(totle);
var number = document.getElementById("value").value;
number=parseInt(number);
var fftPoints =document.getElementById("fftPoints1").value;

if(fftPoints.indexOf(",")>-1)
	fftPoints=fftPoints.replace(/,/g,"");
fftPoints=parseFloat(fftPoints);

document.getElementById("fftPoints1").value=fftPoints;
var bankPoints =document.getElementById("bankPoints1").value;
if(bankPoints.indexOf(",")>-1)
	bankPoints=bankPoints.replace(/,/g,"");
bankPoints=parseFloat(bankPoints);
document.getElementById("bankPoints1").value=bankPoints;

var bankPointsValueMax =0;
if(document.getElementById("bankPointsValueMax"))
	bankPointsValueMax=document.getElementById("bankPointsValueMax").value;
bankPointsValueMax=parseFloat(bankPointsValueMax);
bankPointsValueMax=bankPointsValueMax*0.01*price;
if(document.getElementById("currencyMinUsedBankPointsShow"))
	document.getElementById("currencyMinUsedBankPointsShow").innerHTML=price-bankPointsValueMax;

var bankPointsValueMin =0;
if(document.getElementById("bankPointsValueMin"))
	bankPointsValueMin=document.getElementById("bankPointsValueMin").value;
bankPointsValueMin=parseFloat(bankPointsValueMin);
bankPointsValueMin=bankPointsValueMin*0.01*price;
if(document.getElementById("currencyMaxUsedBankPointsShow"))
	document.getElementById("currencyMaxUsedBankPointsShow").innerHTML=price-bankPointsValueMin;

var fftPointsValueMax =0;
if(document.getElementById("fftPointsValueMax"))
	fftPointsValueMax=document.getElementById("fftPointsValueMax").value;
fftPointsValueMax=parseFloat(fftPointsValueMax);
fftPointsValueMax=fftPointsValueMax*0.01*price;  //Money[cash-fftPointsValueMin]min
if(document.getElementById("currencyMinUsedFftPointsShow"))
	document.getElementById("currencyMinUsedFftPointsShow").innerHTML=price-fftPointsValueMax;

var fftPointsValueMin =0;
if(document.getElementById("fftPointsValueMin"))
	fftPointsValueMin=document.getElementById("fftPointsValueMin").value;
fftPointsValueMin=parseFloat(fftPointsValueMin);
fftPointsValueMin=fftPointsValueMin*0.01*price;    //Money[cash-fftPointsValueMin]max
if(document.getElementById("currencyMaxUsedFftPointsShow"))
	document.getElementById("currencyMaxUsedFftPointsShow").innerHTML=price-fftPointsValueMin;

var cashFftPointsRatio =document.getElementById("cashFftPointsRatio").value;
cashFftPointsRatio=parseFloat(cashFftPointsRatio);
fftPointsValueMax=fftPointsValueMax/cashFftPointsRatio;
fftPointsValueMin=fftPointsValueMin/cashFftPointsRatio;
if(document.getElementById("usableFftPointsMinShow"))
	document.getElementById("usableFftPointsMinShow").innerHTML=fftPointsValueMin;
if(document.getElementById("usableFftPointsMaxShow"))
	document.getElementById("usableFftPointsMaxShow").innerHTML=fftPointsValueMax;


var cashBankPointsRatio =document.getElementById("cashBankPointsRatio").value;
cashBankPointsRatio=parseFloat(cashBankPointsRatio);
bankPointsValueMax=bankPointsValueMax/cashBankPointsRatio;
bankPointsValueMin=bankPointsValueMin/cashBankPointsRatio;
if(document.getElementById("usableBankPointsMinShow"))
	document.getElementById("usableBankPointsMinShow").innerHTML=bankPointsValueMin;
if(document.getElementById("usableBankPointsMaxShow"))
	document.getElementById("usableBankPointsMaxShow").innerHTML=bankPointsValueMax;

initEnterPointsInfo();


function initEnterPointsInfo(){
	payMethod=document.getElementById("payMethod").value;
	if(payMethod=="05"||payMethod=="06"){
		document.getElementById("formlist").style.display="block";
		getbankPointsValueMin(payMethod);
		getbankPointsValueMax(payMethod);
		getfftPointsValueMin(payMethod);
		getfftPointsValueMax(payMethod);
		if(payMethod=="06"){
			document.getElementById("pointsType").innerHTML="金海洋/信通卡";
		}
		if(payMethod=="05"){
			document.getElementById("pointsType").innerHTML="分分通";
		}
	}else{
		document.getElementById("formlist").style.display="none";
	}
}

function initShowPointsAndCurrencyScorp(){
	document.getElementById("currencyMaxUsedBankPointsShow").innerHTML=document.getElementById("currencyMaxUsedBankPoints").innerHTML;
	document.getElementById("usableBankPointsMinShow").innerHTML=document.getElementById("usableBankPointsMin").innerHTML;
	document.getElementById("currencyMinUsedBankPointsShow").innerHTML=document.getElementById("currencyMinUsedBankPoints").innerHTML;
	document.getElementById("usableBankPointsMaxShow").innerHTML=document.getElementById("usableBankPointsMax").innerHTML;
	document.getElementById("currencyMaxUsedFftPointsShow").innerHTML=document.getElementById("currencyMaxUsedFftPoints").innerHTML;
	document.getElementById("usableFftPointsMinShow").innerHTML=document.getElementById("usableFftPointsMin").innerHTML;
	document.getElementById("currencyMinUsedFftPointsShow").innerHTML=document.getElementById("currencyMinUsedFftPoints").innerHTML;
	document.getElementById("usableFftPointsMaxShow").innerHTML=document.getElementById("usableFftPointsMax").innerHTML;
}


function add(){
     number++;
	 document.getElementById("value").value = number;
	// totle = (number * (price*10)/10); 
	 totle = number * price; 
	 document.getElementById("totleprice").innerHTML = totle;
	 document.getElementById("totleRMB").innerHTML =  totle - document.getElementById("score").innerHTML;
	}
	
	
function num(){	
	 if(number >1){
		 number--;
	 document.getElementById("value").value = number
	 totle = number * price;
	 document.getElementById("totleprice").innerHTML = totle;
	 document.getElementById("totleRMB").innerHTML =  totle - document.getElementById("score").innerHTML;
	 }
}

function changeNum(){
	number = document.getElementById("value").value;
	number=parseInt(number);
	if(number<1){
		alert("数量必须大于等于1");
		return ;
	}
	totle = number * price;
	document.getElementById("totleprice").innerHTML = totle;
	document.getElementById("totleRMB").innerHTML =  totle - document.getElementById("score").innerHTML;
	sure();
}

function getbankPointsValueMin(currentPayMethod){
	if(document.getElementById("payMethod").value!="05" && document.getElementById("payMethod").value!="06"){
		return 0;
	}
	var price = document.getElementById("price").innerHTML+"";
	price=parseFloat(price);
	var bankPointsValueMin =0;
	if(document.getElementById("bankPointsValueMin"))
		bankPointsValueMin=document.getElementById("bankPointsValueMin").value;
	bankPointsValueMin=parseFloat(bankPointsValueMin);
	bankPointsValueMin=bankPointsValueMin*0.01*price;
	var currencyMaxUsedBankPoints=price-bankPointsValueMin;
	
	var hasEnteredPoints=0;
	if(document.getElementById("points"))
		hasEnteredPoints=document.getElementById("points").value;
	if(!/^[0-9]*[1-9][0-9]*$/.test(hasEnteredPoints))
		hasEnteredPoints=0;
	
	number = document.getElementById("value").value;
	if(!/^[0-9]*[1-9][0-9]*$/.test(number))
		number=0;
	number=parseInt(number);
	
	
	if(currentPayMethod=="06"){
		if(hasEnteredPoints==0){
			document.getElementById("currencyMaxUsedBankPoints").innerHTML=currencyMaxUsedBankPoints*number;
			document.getElementById("connectionSign").style.display = "inline-block";
		}else{
			hasEnteredPoints=parseInt(hasEnteredPoints);
			var currencyOfEnteredPoints=hasEnteredPoints*cashBankPointsRatio;
			document.getElementById("currencyMaxUsedBankPoints").innerHTML=price*number-currencyOfEnteredPoints;
			document.getElementById("connectionSign").style.display = "none";
			document.getElementById("currencyMinUsedFftPoints").style.display =  "none";
			document.getElementById("currencyMinUsedBankPoints").style.display = "none";
		}
		document.getElementById("currencyMaxUsedFftPoints").style.display = "none";
		document.getElementById("currencyMaxUsedBankPoints").style.display = "inline-block";
	}
	bankPointsValueMin=bankPointsValueMin/cashBankPointsRatio;
	if(currentPayMethod=="06"){
		document.getElementById("usableBankPointsMin").innerHTML=bankPointsValueMin*number;
		document.getElementById("usableFftPointsMin").style.display = "none";
		document.getElementById("usableBankPointsMin").style.display = "inline-block";
	}
	return bankPointsValueMin;
}

function getbankPointsValueMax(currentPayMethod){
	
	if(document.getElementById("payMethod").value!="05" && document.getElementById("payMethod").value!="06"){
		return 0;
	}
	var price = document.getElementById("price").innerHTML+"";
	price=parseFloat(price);
	var bankPointsValueMax =0;
	if(document.getElementById("bankPointsValueMax"))
		bankPointsValueMax=document.getElementById("bankPointsValueMax").value;
	bankPointsValueMax=parseFloat(bankPointsValueMax);
	bankPointsValueMax=bankPointsValueMax*0.01*price;
	var currencyMinUsedBankPoints=price-bankPointsValueMax;
	
	var hasEnteredPoints=0;
	if(document.getElementById("points"))
		hasEnteredPoints=document.getElementById("points").value;
	if(!/^[0-9]*[1-9][0-9]*$/.test(hasEnteredPoints))
		hasEnteredPoints=0;
	
	number = document.getElementById("value").value;
	if(!/^[0-9]*[1-9][0-9]*$/.test(number))
		number=0;
	number=parseInt(number);
	
	
	if(currentPayMethod=="06"){
		if(hasEnteredPoints==0){
			document.getElementById("currencyMinUsedBankPoints").innerHTML=currencyMinUsedBankPoints*number;
			document.getElementById("connectionSign").style.display = "inline-block";
		}else{
			hasEnteredPoints=parseInt(hasEnteredPoints);
			var currencyOfEnteredPoints=hasEnteredPoints*cashBankPointsRatio;
			document.getElementById("currencyMinUsedBankPoints").innerHTML=price*number-currencyOfEnteredPoints;
			document.getElementById("connectionSign").style.display = "none";
			document.getElementById("currencyMaxUsedFftPoints").style.display =  "none";
			document.getElementById("currencyMaxUsedBankPoints").style.display = "none";
		}
		document.getElementById("currencyMinUsedFftPoints").style.display = "none";
		document.getElementById("currencyMinUsedBankPoints").style.display = "inline-block";
	}
	bankPointsValueMax=bankPointsValueMax/cashBankPointsRatio;
	if(currentPayMethod=="06"){
		document.getElementById("usableBankPointsMax").innerHTML=bankPointsValueMax*number;
		document.getElementById("usableFftPointsMax").style.display = "none";
		document.getElementById("usableBankPointsMax").style.display = "inline-block";
	}
	return bankPointsValueMax;
}

function getfftPointsValueMin(currentPayMethod){
	if(document.getElementById("payMethod").value!="05" && document.getElementById("payMethod").value!="06"){
		return 0;
	}
	var price = document.getElementById("price").innerHTML+"";
	price=parseFloat(price);
	var fftPointsValueMin =0;
	if(document.getElementById("fftPointsValueMin"))
		fftPointsValueMin=document.getElementById("fftPointsValueMin").value;
	fftPointsValueMin=parseFloat(fftPointsValueMin);
	fftPointsValueMin=fftPointsValueMin*0.01*price;
	var currencyMaxUsedFftPoints=price-fftPointsValueMin;
	
	var hasEnteredPoints=0;
	if(document.getElementById("points"))
		hasEnteredPoints=document.getElementById("points").value;
	if(!/^[0-9]*[1-9][0-9]*$/.test(hasEnteredPoints))
		hasEnteredPoints=0;
	
	number = document.getElementById("value").value;
	if(!/^[0-9]*[1-9][0-9]*$/.test(number))
		number=0;
	number=parseInt(number);
	
	if(currentPayMethod=="05"){
		if(hasEnteredPoints==0){
			document.getElementById("currencyMaxUsedFftPoints").innerHTML=currencyMaxUsedFftPoints*number;
			document.getElementById("connectionSign").style.display = "inline-block";
		}else{
			hasEnteredPoints=parseInt(hasEnteredPoints);
			var currencyOfEnteredPoints=hasEnteredPoints*cashFftPointsRatio;
			document.getElementById("currencyMaxUsedFftPoints").innerHTML=price*number-currencyOfEnteredPoints;
			document.getElementById("connectionSign").style.display = "none";
			document.getElementById("currencyMinUsedFftPoints").style.display =  "none";
			document.getElementById("currencyMinUsedBankPoints").style.display = "none";
		}
		document.getElementById("currencyMaxUsedBankPoints").style.display = "none";
		document.getElementById("currencyMaxUsedFftPoints").style.display = "inline-block";
	}
	fftPointsValueMin=fftPointsValueMin/cashFftPointsRatio;
	if(currentPayMethod=="05"){
		document.getElementById("usableFftPointsMin").innerHTML=fftPointsValueMin*number;
		document.getElementById("usableBankPointsMin").style.display = "none";
		document.getElementById("usableFftPointsMin").style.display = "inline-block";
	}
	return fftPointsValueMin;
}

function getfftPointsValueMax(currentPayMethod){
	if(document.getElementById("payMethod").value!="05" && document.getElementById("payMethod").value!="06"){
		return 0;
	}
	var price = document.getElementById("price").innerHTML+"";
	price=parseFloat(price);
	var fftPointsValueMax =0;
	if(document.getElementById("fftPointsValueMax"))
		fftPointsValueMax=document.getElementById("fftPointsValueMax").value;
	fftPointsValueMax=parseFloat(fftPointsValueMax);
	fftPointsValueMax=fftPointsValueMax*0.01*price;
	var currencyMinUsedFftPoints=price-fftPointsValueMax;
	
	var hasEnteredPoints=0;
	if(document.getElementById("points"))
		hasEnteredPoints=document.getElementById("points").value;
	if(!/^[0-9]*[1-9][0-9]*$/.test(hasEnteredPoints))
		hasEnteredPoints=0;
	
	number = document.getElementById("value").value;
	if(!/^[0-9]*[1-9][0-9]*$/.test(number))
		number=0;
	number=parseInt(number);
	
	
	if(currentPayMethod=="05"){
		if(hasEnteredPoints==0){
			document.getElementById("currencyMinUsedFftPoints").innerHTML=currencyMinUsedFftPoints*number;
			document.getElementById("connectionSign").style.display = "inline-block";
		}else{
			hasEnteredPoints=parseInt(hasEnteredPoints);
			var currencyOfEnteredPoints=hasEnteredPoints*cashFftPointsRatio;
			document.getElementById("currencyMinUsedFftPoints").innerHTML=price*number-currencyOfEnteredPoints;
			document.getElementById("connectionSign").style.display = "none";
			document.getElementById("currencyMaxUsedFftPoints").style.display =  "none";
			document.getElementById("currencyMaxUsedBankPoints").style.display = "none";
		}
		document.getElementById("currencyMinUsedBankPoints").style.display = "none";
		document.getElementById("currencyMinUsedFftPoints").style.display = "inline-block";
	}
	fftPointsValueMax=fftPointsValueMax/cashFftPointsRatio;
	if(currentPayMethod=="05"){
		document.getElementById("usableFftPointsMax").innerHTML=fftPointsValueMax*number;
		document.getElementById("usableBankPointsMax").style.display = "none";
		document.getElementById("usableFftPointsMax").style.display = "inline-block";
	}
	return fftPointsValueMax;
}

//验证积分
function sure(){
	var currentPayMethod=document.getElementById("payMethod").value;
	if(document.getElementById("payMethod").value!="05" && document.getElementById("payMethod").value!="06"){
		return true;
	}
	var enteredPoints=document.getElementById("points").value;
	if(!/^[0-9]*[1-9][0-9]*$/.test(enteredPoints))
		enteredPoints=0;
	enteredPoints=parseInt(enteredPoints);
	if(enteredPoints<0){
		document.getElementById("errorMsg").innerHTML="<h2>您使用积分数应大于或等于0</h2>";
		document.getElementById("show").style.display="";
//		alert("您使用积分数应大于或等于0");
		return false;
	}
	bankPointsValueMin=getbankPointsValueMin(currentPayMethod);
	bankPointsValueMax=getbankPointsValueMax(currentPayMethod);
	fftPointsValueMin=getfftPointsValueMin(currentPayMethod);
	fftPointsValueMax=getfftPointsValueMax(currentPayMethod);
	number = document.getElementById("value").value;
	if(!/^[0-9]*[1-9][0-9]*$/.test(number))
		number=0;
	number=parseInt(number);
	if(number<=0){
		document.getElementById("errorMsg").innerHTML="<h2>购买的数量应大于0</h2>";
		document.getElementById("show").style.display="";
//		alert("您使用积分数应大于或等于0");
		return false;
	}
	var bankPointsValueMinT=bankPointsValueMin*number;
	var bankPointsValueMaxT=bankPointsValueMax*number;
	var fftPointsValueMinT=fftPointsValueMin*number;
	var fftPointsValueMaxT=fftPointsValueMax*number;
	if(document.getElementById("card").checked == true){
		document.getElementById("pointsType").innerHTML="金海洋/信通卡";
		if(enteredPoints<bankPointsValueMinT){
//			alert("您最少必须使用"+bankPointsValueMinT+"个金海洋/信通卡积分");
			document.getElementById("errorMsg").innerHTML="<h2>您最少必须使用"+bankPointsValueMinT+"个金海洋/信通卡积分</h2>";
			document.getElementById("show").style.display="";
			return false;
		}
		if(enteredPoints>bankPointsValueMaxT){
//			alert("您最多只能使用"+bankPointsValueMaxT+"个金海洋/信通卡积分");
			document.getElementById("errorMsg").innerHTML="<h2>您最多只能使用"+bankPointsValueMaxT+"个金海洋/信通卡积分</h2>";
			document.getElementById("show").style.display="";
			return false;
		}
		if(enteredPoints>bankPoints){
//			alert("您只有"+bankPoints+"个金海洋/信通卡积分");
			document.getElementById("errorMsg").innerHTML="<h2>您只有"+bankPoints+"个金海洋/信通卡积分</h2>";
			document.getElementById("show").style.display="";
			return false;
		}
		document.getElementById("bankPointsValueRealAll").value=enteredPoints*1;
		document.getElementById("totleRMB").innerHTML =  totle - document.getElementById("points").value*cashBankPointsRatio;
		document.getElementById("score").innerHTML =document.getElementById("points").value*cashBankPointsRatio;
		document.getElementById("payMethod").value="06";
	}else{
		document.getElementById("pointsType").innerHTML="分分通";
		if(enteredPoints<fftPointsValueMinT){
//			alert("您最少必须使用"+fftPointsValueMinT+"个分分通积分");
			document.getElementById("errorMsg").innerHTML="<h2>您最少必须使用"+fftPointsValueMinT+"个分分通积分</h2>";
			document.getElementById("show").style.display="";
			return false;
		}
		if(enteredPoints>fftPointsValueMaxT){
//			alert("您最多只能使用"+fftPointsValueMaxT+"个分分通积分");
			document.getElementById("errorMsg").innerHTML="<h2>您最多只能使用"+fftPointsValueMaxT+"个分分通积分</h2>";
			document.getElementById("show").style.display="";
			return false;
		}
		if(enteredPoints>fftPoints){
//			alert("您只有"+fftPoints+"个分分通积分");
			document.getElementById("errorMsg").innerHTML="<h2>您只有"+fftPoints+"个分分通积分</h2>";
			document.getElementById("show").style.display="";
			return false;
		}
		document.getElementById("fftPointsValueRealAll").value=enteredPoints*1;
		document.getElementById("payMethod").value="05";
		document.getElementById("totleRMB").innerHTML =  totle - document.getElementById("points").value*cashFftPointsRatio;
		document.getElementById("score").innerHTML = document.getElementById("points").value*cashFftPointsRatio;
	}
	return true;
//	document.getElementById("showDialog").style.display = "none";
//	document.getElementById("pay_mask").style.display = "none";
	
	//document.getElementById("totleRMB").innerHTML =  (totle*10)/10 - ((document.getElementById("score").innerHTML)*10)/10;
}


function changePayMethod(payMethod){
	document.getElementById("payMethod").value=payMethod;
	if(payMethod=="06")
		document.getElementById("card").checked = "checked";
	else if(payMethod=="05")
		document.getElementById("card").checked = "";
	if(payMethod=="05"||payMethod=="06"){
		document.getElementById("formlist").style.display="";
		sure();
	}else{
		document.getElementById("formlist").style.display="none";
	}
}

function submitCheck(){
	payMethod=document.getElementById("payMethod").value;
	if(payMethod=="05"||payMethod=="06"){
		return sure();
	}else
		return true;
}





//显示弹出框
function show() {
	var o = document.getElementById("showDialog");
	o.style.display = "block";
	o.style.position = "absolute";
	o.style.zIndex = "9999";
	o.style.left = (document.documentElement.clientWidth - o.clientWidth) / 2 + document.documentElement.scrollLeft + "px";
	o.style.top = (document.documentElement.clientHeight - o.clientHeight) / 2 + document.documentElement.scrollTop - 50 + "px";
	document.getElementById("pay_mask").style.display = "block";
}

//关闭弹出框	
function closebox() {
	document.getElementById("showDialog").style.display = "none";
	document.getElementById("pay_mask").style.display = "none";

}


var textnum = document.getElementById("value");

textnum.onkeyup =function (){	    
		this.value = this.value.replace(/\D/g,'');
		//this.value.substring(0,1)=='0'?this.value='1':this.value=this.value;
		//this.value.substring(0,1)==''?this.value='0':this.value=this.value;
		sure();
}

var textPoints= document.getElementById("points");

textPoints.onkeyup =function (){	    
	this.value = this.value.replace(/\D/g,'');
	//this.value.substring(0,1)=='0'?this.value='1':this.value=this.value;
	//this.value.substring(0,1)==''?this.value='0':this.value=this.value;
	sure();
}