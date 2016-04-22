<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/buy/css/pay.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/select2.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/headsearch.js"></script>
<script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>
<script type="text/javascript" src="${base}/template/common/js/closeDialog.js"></script>


</head>
<body>
<!--
* Author:
* pengling@f-road.com.cn 
*/
--> 
<!--头部开始-->
<#include "/WEB-INF/templates/common/header.ftl">
<!--头部结束-->

<!--导航开始-->
<#include "/WEB-INF/templates/common/menu.ftl">
<!--导航结束-->

<div class="miss" style="display:none;" id="show">
 <div><h2 id="pointChackTd"></h2></div>
 <a href="#" onclick="javascript:closed();"></a>
</div>


<div class="box1010 pt10 clear"> 
  <!--付款详情开始-->
  <form id="groupBuy" name="groupBuy" method="post" action="group_order_new.action" onSubmit="return checkAndSubmit()" >
   <input type="hidden" id="submitTimes" value="0" />  
   <input type="hidden" name="tempTran.clientType" id="clientType" value="100" />  
   <input type="hidden" name="tempTran.goodsRackId" id="radio" value="${(tempTran.goodsRackId)!""}" />
   <input type="hidden" name="pager.groupPrice" id="radio" value="${(pager.groupPrice)!""}" />
   <input type="hidden" name="pager.cashPricing" id="cashPricing" value="${(pager.cashPricing)!""}" />
   <input type="hidden" name="tempTran.goodsName" id="radio" value="${(pager.goods.goodsName)!""}" />
   <input type="hidden" name="pager.goods.goodsName" id="radio" value="${(pager.goods.goodsName)!""}" />  
   <input type="hidden" id="cash" value="${cash!"0"}" />
   <input type="hidden" id="fftPointPricing" value="${fftPointPricing!"0"}" />
   <input type="hidden" id="bankPointPricing" value="${bankPointPricing!"0"}" />
   <input type="hidden" id="cashFftPointPricing" value="${cashFftPointPricing!"0"}" />
   <input type="hidden" id="cashFftPointPricingCash" value="${cashFftPointPricingCash!"0"}" />
   <input type="hidden" id="cashBankPointPricing" value="${cashBankPointPricing!"0"}" />  
   <input type="hidden" id="cashBankPointPricingCash" value="${cashBankPointPricingCash!"0"}" /> 
   <input type="hidden" name="tempTran.bankPointsValueRealAll" id="bankPointsValueRealAll" />
   <input type="hidden" name="tempTran.fftPointsValueRealAll" id="fftPointsValueRealAll" />
   <input type="hidden" id="fftPoints1" value="${fftPoint!""}" />
   <input type="hidden" id="bankPoints1" value="${bankPoint!""}" />
   <input type="hidden" id="bankPointsValueMin" value="${bankPointValueMin!""}" />
   <input type="hidden" id="fftPointsValueMin" value="${fftPointValueMin!""}" />
   <input type="hidden" id="bankPointsValueMax" value="${bankPointValueMax!""}" />
   <input type="hidden" id="fftPointsValueMax" value="${fftPointValueMax!""}" />
   <input type="hidden" id="cashFftPointsRatio" value="${cashFftPointsRatio!""}" />
   <input type="hidden" id="cashBankPointsRatio" value="${cashBankPointsRatio!""}" />
   <input type="hidden" id="perNumber" value="${perNumber!""}" />
   <input type="hidden" id="perminNumber" value="${perminNumber!""}" />
  <div class="paytable validate">    
    <div class="table-section mt5">    
    <div class="stepgroup"></div>
	    <div class="step">
	      <li class="colorRed step07">提交订单</li>
	      <li class="step02">生成订单</li>
	      <li class="step03">购买完成</li>
	    </div> 
      	${errorMsg?if_exists}
	      <table cellspacing="0">
	        <tbody>
	          <tr>
	            <td width="84">商品名称</td>
	            <td width="610" class="desc">${pager.goods.goodsName?if_exists}</td>
	          </tr>
	          <tr>
	            <td>商品数量</td>
	            <td align="left" class="quantity">
	              	<a href="javascript:void(0)" onclick="subNum();isPointDivOpen();">-</a>
	            	<input class="loginText5" name="tempTran.goodsNumber"  maxlength="2" value="1" type="text" id="value" Onchange="isPointDivOpen();"    onkeyup="value=value.replace(/[^0-9]*[^1-9][^0-9]*$/,'');" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^0-9]*[^1-9][^0-9]*$/,''))"     />
	            	<a href="javascript:void(0)" onclick="addNum();isPointDivOpen();">+</a>
	            </td>
	          </tr>
	          <tr id="spdj" style="">
	            <td width="84">商品单价</td>
	            <td width="610" class="desc" id="spdjContent">${cash!"0"}元</td>
	          </tr>
	          <tr>
	            <td>支付方式</td>
	            <td align="left">
	            <span class="fl" id="uboxstyle">
		     <!--		<select name="tempTran.payMethod" id="payMethod" tableindex="4" onchange="changePayMethod(this.value);"> -->
		         <select name="tempTran.payMethod" id="payMethod" onchange="isPointDivOpen();">
		         <!--	<option value="null" id="initSelect">请选择支付方式</option> -->
			           <option  value="09" selected="selected">现金（支付宝支付）</option>
			          <#if pager.isCash=="1" >
		             	 <option  value="02">现金（珠海农商手机银行卡支付）</option>
		              </#if>
		               <#if pager.isFftPoint=="1">
		             	<option  value="00" >分分通积分</option>
		              </#if>
		               <#if pager.isBankPoint=="1" >
		             	  <option  value="01">银行积分</option>
		              </#if>
		              <#if pager.isFftpointCash=="1" >
		             	<option  value="03">分分通积分+现金</option>
		              </#if>
		               <#if pager.isBankpointCash=="1" >
		             	<option  value="04">银行积分+现金</option>
		              </#if>
		               <#if pager.isFftpointcashRatioPricing=="1" >
		             	<option  value="05">分分通积分+现金（比例）</option>
		              </#if>
		               <#if pager.isBankpointcashRatioPricing=="1" >
		             	<option  value="06">银行积分+现金（比例）</option>
		              </#if>
			     </select>
			     </span>
	            </td>
	          </tr>     
	          <!--<tr id="pointChackTd" style="display:none; color:#F00"></tr>    -->
	          <tr id="formlist" style="display:none">
	            <td>使用积分</td>
	            <td align="left">    
	       <!--     <div id="showpoints"><strong><input name="" type="text" class="loginText4" id="points"/><i>可使用积分：1080-2100分，您还需要支付现金200元</i></strong></div> -->
	        	<div class="validate"> 
	               <strong>
			        <input name="" type="text" class="loginText4" id="inputPoints" onchange="isPointDivOpen();" value="">
			        <i>
			        	请使用<b id="usablePointsMin" style="display:none"></b>
				       	<b id="connectPoint" style="display:none">-</b>
				        <b id="usablePointsMax" style="display:none"></b>
				        <b id="zhijian" style="display:none">之间的</b>			        		        
			        	<b id="pointsType"></b>积分.
			        
				        <b id="usableCarry" style="display:none"></b>
				        <b id="warmTitle" style="margin-left: 12px; color:#F00; font:12px/20px "宋体";"></b>	
				        	        
				     </i>
			       	</strong>
			     </div> 
	            </td>
	          </tr>        
	        </tbody>
	      </table>     
       <div class="confirm">
         	<a href="javascript:checkAndSubmit();" class="fr mt10 mr15" id="twoClick"><div class="textBtn"><B>确认无误，购买</B></div></a>
      </div>
    </div>
  </div>
</form>
  <!--付款详情结束-->
</div>
<script type="text/javascript" src="${base}/template/common/js/select.js"></script>   
<!--底部开始-->  
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
<script type="text/javascript">
var flagVaule = 0;
var cashPricing = $('#cashPricing').val();
var cash = $('#cash').val();
var fftPoints = $('#fftPoints1').val();
var bankPoint = $('#bankPoints1').val();
var bankPointValueMin = $('#bankPointsValueMin').val();
var bankPointValueMax = $('#bankPointsValueMax').val();
var fftPointsValueMin = $('#fftPointsValueMin').val();
var fftPointsValueMax = $('#fftPointsValueMax').val();
var cashFftPointsRatio = $('#cashFftPointsRatio').val();
var cashBankPointsRatio = $('#cashBankPointsRatio').val();
var perNumber = $('#perNumber').val();
var perminNumber = $('#perminNumber').val();

var cashPricing2=parseFloat(cashPricing);
var fftPoints2=parseFloat(fftPoints);
var bankPoint2=parseFloat(bankPoint);
var bankPointValueMin2=parseFloat(bankPointValueMin);
var bankPointValueMax2=parseFloat(bankPointValueMax);
var fftPointsValueMin2=parseFloat(fftPointsValueMin);
var fftPointsValueMax2=parseFloat(fftPointsValueMax);
var cashFftPointsRatio2=parseFloat(cashFftPointsRatio);
var cashBankPointsRatio2=parseFloat(cashBankPointsRatio);
var perNumber2=parseInt(perNumber);
var perminNumber2=parseInt(perminNumber);

var timeoutfn;
function initWarmTitle(){
	timeoutfn = setTimeout(function (){
		$("#warmTitle").html("");
	},2500);
}

//将相应的数据进行处理不产生小数
function disposeNum(){
	$("#usablePointsMin").html(Math.ceil($("#usablePointsMin").html()));
	$("#usablePointsMax").html(Math.floor($("#usablePointsMax").html()));
	if(Math.ceil($("#usablePointsMin").html())>Math.floor($("#usablePointsMax").html())){
		$("#usablePointsMin").html('0');
		$("#usablePointsMax").html('0');
	}
}

function isPointDivOpen(){	
	var method = $('#payMethod').val();
	var cash = $('#cash').val();
	if(method != '' && (method == '05' || method == '06')){
		$('#formlist').show();					
		$('#spdjContent').html(cash+'元');
		//$('#spdj').hide();
		flagVaule = 1;//支付凡是存在积分现金比率
		pointCheck();
	}else{
		flagVaule = 0;
		//$('#show').show();		
		$('#formlist').hide();
		pointCheck();
	}
	disposeNum();
}


function checkAndSubmit(){
	if($("#payMethod").val() == "null"){
		$.layer({
				title:['分分通提示您',true],
				time:3,
				area : ['auto','auto'],
				dialog : {msg:'请选择支付方式',type : 8}	
			});
		return;
	}else{
		var goodsNum=document.getElementById("value").value;
		if(!/^[0-9]*[1-9][0-9]*$/.test(goodsNum))
			return ;
		if(pointCheck()){
			disposeNum();
			var senda = $("#twoClick");
			senda.attr("href","javascript:void(0);");
			senda.find("div").attr("class","gryBtn");	
			senda.find('B').html("请等待...");
			groupBuy.submit();
		}else{
			//if($.trim($("#inputPoints").val()).length <= 0){
			//	$("#warmTitle").html("请输入积分");
		//	}else{
		//		$("#warmTitle").html("对不起，积分超出范围");
		//	}
			$("#warmTitle").html("请输入正确的积分数");
			initWarmTitle();
			//alert("输入正确的积分数,或者换其他支付方式");
			disposeNum();
			return ;
		}		
	}
}

	function addNum(){
		var number = $('#value').val();
     	if(number >= perNumber2){
     		return;
     	}
     	number++;
	 	$('#value').val(number);
	 	pointCheck()
	}
		
	function subNum(){	
		var number = $('#value').val();
		if(number > perminNumber2){
	     	number--;
		 	$('#value').val(number);
		}
		pointCheck()
	}
	
	function pointCheck(){
		var method = $('#payMethod').val();
		var number = $.trim($('#value').val());
		if(number == ''){
			$('#pointChackTd').html('商品数量不能为空');
			$('#show').show();
			return false;
		}
		number = parseInt(number);
		if(perminNumber != '' && perminNumber != '0' && number < perminNumber2){
			$('#pointChackTd').html('此商品每笔交易最低购买数为 '+perminNumber+'件');
			$('#show').show();		
			return false;
		}else if(perNumber != '' && perNumber != '0' && number > perNumber2){
			$('#pointChackTd').html('此商品每笔交易最多限购 '+perNumber2+'件');
			$('#show').show();		
			return false;
		}else if(perNumber != '' && perNumber != '0' && number == perNumber2){
			$('#pointChackTd').html('此商品每笔交易最多限购 '+perNumber2+'件');
			$('#show').show();	
		}else{
			$('#show').hide();
			//$('#pointChackTd').html('');
			//$('#show').show();		
		}
		if(flagVaule != null && flagVaule == 1){
			var inputPoints = $('#inputPoints').val();
			if(inputPoints ==null || inputPoints == '' || inputPoints =='undefined'){
				inputPoints = '0';
			}
			
			var regex = /^[1-9][0-9]*$/g;
			if(inputPoints != '0' && !regex.test(inputPoints)){
				$("#warmTitle").html("请输入正确的积分数");
				initWarmTitle();
				//alert("请输入整数积分");
				$('#inputPoints').val("");
				return false;
			}
			
			inputPoints = parseFloat(inputPoints);
			if(method == '05'){
				$('#pointsType').html("分分通");
				var fftMin = fftPointsValueMin2*number;
				var fftMax = fftPointsValueMax2*number;
				$('#usablePointsMin').html(fftMin);
				$('#usablePointsMax').html(fftMax);
				disposeNum();
				if(inputPoints == 0){
					$('#usablePointsMin').show();
					$('#connectPoint').show();
					$('#usablePointsMax').show();
					var cash = cashPricing2*number;
					$('#usableCarry').html(cash);
					$('#zhijian').show();
					$('#usableCarry').hide();
					return false;
				}else if(inputPoints < fftMin){
					$('#usablePointsMin').show();					
					$('#connectPoint').show();
					$('#usablePointsMax').show();
					$('#zhijian').show();
					$('#usableCarry').hide();
					return false;
				}else if(inputPoints > fftMax){
					$('#usablePointsMin').show();
					$('#connectPoint').show();
					$('#usablePointsMax').show();
					$('#zhijian').show();
					$('#usableCarry').hide();
					return false;
				}else{
					var cash = cashPricing2*number-inputPoints*cashFftPointsRatio2;
					$('#usableCarry').html("另需现金 "+parseFloat(cash).toFixed(2)+"元");
					$('#usableCarry').show();
					$('#fftPointsValueRealAll').val($('#inputPoints').val());
					return true;
				}
			}
			
			if(method == '06'){
				$('#pointsType').html("银行积分");
				var bankMin = bankPointValueMin2*number;
				var bankMax = bankPointValueMax2*number;
				$('#usablePointsMin').html(bankMin);
				$('#usablePointsMax').html(bankMax);
				disposeNum();
				if(inputPoints == 0){
					$('#usablePointsMin').show();
					$('#connectPoint').show();
					$('#usablePointsMax').show();
					var cash = cashPricing2*number;
					$('#usableCarry').html(cash);
					$('#zhijian').show();
					$('#usableCarry').hide();
					return false;
				}else if(inputPoints < bankMin){
					$('#usablePointsMin').show();					
					$('#connectPoint').show();
					$('#usablePointsMax').show();
					$('#zhijian').show();
					$('#usableCarry').hide();
					return false;
				}else if(inputPoints > bankMax){
					$('#usablePointsMin').show();
					$('#connectPoint').show();
					$('#usablePointsMax').show();
					$('#zhijian').show();
					$('#usableCarry').hide();
					return false;
				}else{ 
					var cash = cashPricing2*number-inputPoints*cashBankPointsRatio2;
					$('#usableCarry').html("另需现金 "+parseFloat(cash).toFixed(2)+"元");
					$('#usableCarry').show();
					$('#bankPointsValueRealAll').val($('#inputPoints').val());
					return true;
				}
			}
		}else{
			if(method == '00'){
				var fftPointPricing = $('#fftPointPricing').val();
				$('#spdjContent').html(fftPointPricing+'分分通积分');
				$('#spdj').show();
				var fftPointPricing2 = parseFloat(fftPointPricing);
				var allpoints = fftPointPricing2*number;
				if(fftPoints < allpoints){
					$('#pointChackTd').html('您的积分不够，请换其他支付方式');
					$('#show').show();		
					return false;
				}else{
					$('#show').hide();		
				}
			}
			if(method == '01'){
				var bankPointPricing = $('#bankPointPricing').val();
				$('#spdjContent').html(bankPointPricing+'银行积分');
				$('#spdj').show();
				var bankPointPricing2 = parseFloat(bankPointPricing);
				var allpoints = bankPointPricing2*number;
				if(bankPoint < allpoints){
					$('#pointChackTd').html('您的积分不够，请换其他支付方式');
					$('#show').show();		
					return false;
				}else{
					$('#show').hide();		
				}
			}
			
			if(method == '02'){
				var cash = $('#cash').val();
				$('#spdjContent').html(cash+'元');
				$('#spdj').show();
			}
			
			if(method == '03'){
				var cashFftPointPricingcash = $('#cashFftPointPricingCash').val();
				var cashFftPointPricing = $('#cashFftPointPricing').val();
				$('#spdjContent').html(cashFftPointPricing+'分分通积分 +'+parseFloat(cashFftPointPricingcash).toFixed(2)+'元');
				$('#spdj').show();
				var cashFftPointPricing2 = parseFloat(cashFftPointPricing);
				var allpoints = cashFftPointPricing2*number;
				if(fftPoints < allpoints){
					$('#pointChackTd').html('您的积分不够，请换其他支付方式');
					$('#show').show();
					return false;
				}else{
					$('#show').hide();		
				}
			}
			if(method == '04'){
				var cashBankPointPricingcash = $('#cashBankPointPricingCash').val();
				var cashBankPointPricing = $('#cashBankPointPricing').val();
				$('#spdjContent').html(cashBankPointPricing + '银行积分 +'+parseFloat(cashBankPointPricingcash).toFixed(2)+'元');
				$('#spdj').show();
				var cashBankPointPricing2 = parseFloat(cashBankPointPricing);
				var allpoints = cashBankPointPricing2*number;
				if(bankPoint < allpoints){
					$('#pointChackTd').html('您的积分不够，请换其他支付方式');
					$('#show').show();
					return false;
				}
			}
		}
		return true;
	}
	$("#initSelect").attr("selected","true");//控制下拉框默认选择	
</script> 

<script type="text/javascript">
var textnum = document.getElementById("value");

textnum.onkeyup =function (){	    
this.value = this.value.replace(/\D/g,'');
this.value.substring(0,1)=='0'?this.value='1':this.value=this.value
this.value.substring(0,1)==''?this.value='1':this.value=this.value
}
</script>
</body>
</html>
