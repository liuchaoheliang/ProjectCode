firstPayMethod=document.getElementById("firstPayMethod").value ;
firstGoodsId=document.getElementById("firstGoodsId").value ;
var bankPointsValueMin=0;
var bankPointsValueMax=0;
var fftPointsValueMin=0;
var fftPointsValueMax=0;

showPayMethod(firstPayMethod,firstGoodsId);
initMaxMinAndPrice(firstGoodsId);

function changePriceShowAndPayMethod(goodId){
   
	 price=document.getElementById(goodId+"-"+"price").value ;
	 document.getElementById("showPrice").innerHTML = price;
	 avalidatePayMethod=document.getElementById(goodId).value;
	 
	 bankPointsValueMax1=document.getElementById(goodId+"-"+"bankPointsValueMax").value;
		bankPointsValueMin1=document.getElementById(goodId+"-"+"bankPointsValueMin").value;
		fftPointsValueMax1=document.getElementById(goodId+"-"+"fftPointsValueMax").value;
		fftPointsValueMin1=document.getElementById(goodId+"-"+"fftPointsValueMin").value;
		
		 document.getElementById("bankPointsValueMax").value=bankPointsValueMax1;
		document.getElementById("bankPointsValueMin").value=bankPointsValueMin1;
		document.getElementById("fftPointsValueMax").value=fftPointsValueMax1;
		document.getElementById("fftPointsValueMin").value=fftPointsValueMin1;
		
		currentprice=document.getElementById(goodId+"-"+"onlyprice").value;
		 document.getElementById("price").innerHTML=currentprice;
			 document.getElementById("totleprice").innerHTML=currentprice; 
	 
	 showPayMethod(avalidatePayMethod,goodId);
	}

function initMaxMinAndPrice(goodId){
	 bankPointsValueMax1=document.getElementById(goodId+"-"+"bankPointsValueMax").value;
		bankPointsValueMin1=document.getElementById(goodId+"-"+"bankPointsValueMin").value;
		fftPointsValueMax1=document.getElementById(goodId+"-"+"fftPointsValueMax").value;
		fftPointsValueMin1=document.getElementById(goodId+"-"+"fftPointsValueMin").value;
		
		 document.getElementById("bankPointsValueMax").value=bankPointsValueMax1;
		document.getElementById("bankPointsValueMin").value=bankPointsValueMin1;
		document.getElementById("fftPointsValueMax").value=fftPointsValueMax1;
		document.getElementById("fftPointsValueMin").value=fftPointsValueMin1;
		
		currentprice=document.getElementById(goodId+"-"+"onlyprice").value;
		 document.getElementById("price").innerHTML=currentprice;
			 document.getElementById("totleprice").innerHTML=currentprice; 
	
}

function showPayMethod(avalidatePayMethod,goodId){
	 allAvalidatePayMethod="<select class='existing_event'  tableindex='4' onchange=aGoodsChangePayMethod(this.value,'');>";
	 avalidatePayMethodArray=avalidatePayMethod.split(",");
	 isFirstOne=true;
	 for(i=0;i<avalidatePayMethodArray.length;i++){
	 if(avalidatePayMethodArray[i]=="02"){
		 if(isFirstOne){
			 allAvalidatePayMethod=allAvalidatePayMethod+"<option value='02' checked >现金</option>";
			 document.getElementById("payMethod").value="02" ;
		 }else{
			 allAvalidatePayMethod=allAvalidatePayMethod+"<option value='02' ><span>现金</option>";
		 }
		isFirstOne=false;
	 }
	 if(avalidatePayMethodArray[i]=="00"){
		 if(isFirstOne){
	    	 allAvalidatePayMethod=allAvalidatePayMethod+"<option value='00' checked><span>分分通积分</option>";
	    	 document.getElementById("payMethod").value="00" ;
		 }else{
			 allAvalidatePayMethod=allAvalidatePayMethod+"<option value='00'><span>分分通积分</option>";
		 }
		 isFirstOne=false;
	}
    if(avalidatePayMethodArray[i]=="01"){
   	 if(isFirstOne){
			 allAvalidatePayMethod=allAvalidatePayMethod+"<option value='01' checked><span>银行积分</option>";
			 document.getElementById("payMethod").value="01" ;
		 }else{
			 allAvalidatePayMethod=allAvalidatePayMethod+"<option value='01'><span>银行积分</option>";
		 }
   }
    if(avalidatePayMethodArray[i]=="03"){
   	 if(isFirstOne){
		 allAvalidatePayMethod=allAvalidatePayMethod+"<option value='03' checked><span>分分通积分+现金</option>";
		 document.getElementById("payMethod").value="03" ;
		 }else{
			 allAvalidatePayMethod=allAvalidatePayMethod+"<option value='03'><span>分分通积分+现金</option>";
		 }
   }
    if(avalidatePayMethodArray[i]=="04"){
   	 if(isFirstOne){
		 allAvalidatePayMethod=allAvalidatePayMethod+"<option value='04' checked ><span>银行积分+现金</option>";
		 document.getElementById("payMethod").value="04" ;
		 }else{
			 allAvalidatePayMethod=allAvalidatePayMethod+"<option value='04'><span>银行积分+现金</option>";
		 }
   }
    if(avalidatePayMethodArray[i]=="05"){
   	 if(isFirstOne){
		 allAvalidatePayMethod=allAvalidatePayMethod+"<option value='05' checked ><span>分分通积分+现金（比例）</option>";
		 document.getElementById("payMethod").value="05" ;
		 document.getElementById("formlist").style.display="";
		 }else{
			 allAvalidatePayMethod=allAvalidatePayMethod+"<option value='05'><span>分分通积分+现金（比例）</option>";
		 }
   }
    
    if(avalidatePayMethodArray[i]=="06"){
   	 if(isFirstOne){
		 allAvalidatePayMethod=allAvalidatePayMethod+"<option value='06' checked ><span>银行积分+现金（比例）</option>";
		 document.getElementById("payMethod").value="06" ;
		 document.getElementById("formlist").style.display="";
		 }else{
			 allAvalidatePayMethod=allAvalidatePayMethod+"<option value='06'>银行积分+现金（比例）</option>";
		 }
    }
	}
	 document.getElementById("avalidatePayMethod").innerHTML = allAvalidatePayMethod;
}


function aGoodsChangePayMethod(payMethod,goodId){
	 document.getElementById("payMethod").value=payMethod ;
	if(payMethod=="05" || payMethod=="06")
	 document.getElementById("ta").style.display="block";
	else
		document.getElementById("ta").style.display="none";
	
	 
	if(payMethod=="06")
		document.getElementById("card").checked = "checked";
	else if(payMethod=="05")
		document.getElementById("card").checked = "";
	
	
//	bankPointsValueMax1=document.getElementById(goodId+"-"+"bankPointsValueMax").value;
//	bankPointsValueMin1=document.getElementById(goodId+"-"+"bankPointsValueMin").value;
//	fftPointsValueMax1=document.getElementById(goodId+"-"+"fftPointsValueMax").value;
//	fftPointsValueMin1=document.getElementById(goodId+"-"+"fftPointsValueMin").value;
//	initBeforeSure(bankPointsValueMax1,bankPointsValueMin1,fftPointsValueMax1,fftPointsValueMin1);
//	bankPointsValueMax=getBankPointsValueMax();
//	bankPointsValueMin=getBankPointsValueMin();
//	fftPointsValueMax=getFftPointsValueMax();
//	fftPointsValueMin=getFftPointsValueMin();
//	sure2(bankPointsValueMax,bankPointsValueMin,fftPointsValueMax,fftPointsValueMin);
	sure();

}




